SET search_path = audit, pg_catalog;

CREATE FUNCTION if_modified_func() RETURNS TRIGGER AS $body$
DECLARE
    audit_row audit.event;
    excluded_cols text[] = ARRAY[]::text[];
	
BEGIN
    IF TG_WHEN <> 'AFTER' THEN
        RAISE EXCEPTION 'audit.if_modified_func() may only run as an AFTER trigger';
    END IF;

    audit_row = ROW(
        nextval('audit.event_event_id_seq'),          -- event_id
        TG_TABLE_SCHEMA::text,                        -- schema_name
        TG_TABLE_NAME::text,                          -- table_name
        NULL, NULL,                                   -- log_data_id, Opus user_id
        substring(TG_OP,1,1),                         -- action
        NULL, NULL,                                   -- row_data, changed_fields
		'f',                                          -- statement_only
		session_user::text,                           -- session_user_name
		current_setting('application_name'),          -- client application
        inet_client_addr(),                           -- client_addr
        inet_client_port(),                           -- client_port
        current_query(),                              -- top-level query or queries (if multistatement) from client
		now()                                         -- registration timestamp
    );

    IF NOT TG_ARGV[0]::boolean IS DISTINCT FROM 'f'::boolean THEN
        audit_row.client_query = NULL;
    END IF;

    IF TG_ARGV[1] IS NOT NULL THEN
        excluded_cols = TG_ARGV[1]::text[];
    END IF;
    
	IF(TG_OP IN ('INSERT','UPDATE','DELETE','TRUNCATE')) THEN
		IF (TG_LEVEL = 'ROW') THEN
			audit_row.statement_only = 'f';
			
			IF (TG_OP = 'UPDATE') THEN
				audit_row.row_data = hstore(OLD.*);
				audit_row.changed_fields = (hstore(NEW.*) - audit_row.row_data) - excluded_cols;
				audit_row.log_data_id = NEW.log_data_id;
				audit_row.user_id = NEW.user_id;
				
				IF audit_row.changed_fields = hstore('') THEN
					-- All changed fields are ignored. Skip this update.
					RETURN NULL;
				END IF;
			ELSIF (TG_OP = 'DELETE' OR TG_OP = 'TRUNCATE') THEN
				audit_row.row_data = hstore(OLD.*) - excluded_cols;
				audit_row.log_data_id = OLD.log_data_id;
				audit_row.user_id = OLD.user_id;
			ELSIF (TG_OP = 'INSERT') THEN
				audit_row.row_data = hstore(NEW.*) - excluded_cols;
				audit_row.log_data_id = NEW.log_data_id;
				audit_row.user_id = NEW.user_id;
			END IF;
		
		ELSIF (TG_LEVEL = 'STATEMENT') THEN
			audit_row.statement_only = 't';
		ELSE
			RAISE EXCEPTION '[audit.if_modified_func] - Trigger func added as trigger for unhandled case: %, %',TG_OP, TG_LEVEL;
			RETURN NULL;
		END IF;
		
		INSERT INTO audit.event VALUES (audit_row.*);
	END IF;
	
    RETURN NULL;
END;
$body$
LANGUAGE plpgsql;


COMMENT ON FUNCTION if_modified_func() IS $body$
Track changes to a table at the statement and/or row level.

Optional parameters to trigger in CREATE TRIGGER call:

param 0: boolean, whether to log the query text. Default 't'.

param 1: text[], columns to ignore in updates. Default [].

         Updates to ignored cols are omitted from changed_fields.

         Updates with only ignored cols changed are not inserted
         into the audit log.

         Almost all the processing work is still done for updates
         that ignored. If you need to save the load, you need to use
         WHEN clause on the trigger instead.

         No warning or error is issued if ignored_cols contains columns
         that do not exist in the target table. This lets you specify
         a standard set of ignored columns.

There is no parameter to disable logging of values. Add this trigger as
a 'FOR EACH STATEMENT' rather than 'FOR EACH ROW' trigger if you do not
want to log row values.
$body$;


CREATE FUNCTION audit_table(target_table regclass, audit_rows boolean, audit_query_text boolean, ignored_cols text[]) RETURNS void AS $body$
DECLARE
  stm_targets text = 'INSERT OR UPDATE OR DELETE OR TRUNCATE';
  _q_txt text;
  _ignored_cols_snip text = '';
BEGIN
    EXECUTE 'DROP TRIGGER IF EXISTS audit_trigger_row ON ' || target_table;
    EXECUTE 'DROP TRIGGER IF EXISTS audit_trigger_stm ON ' || target_table;

    IF audit_rows THEN
        IF array_length(ignored_cols,1) > 0 THEN
            _ignored_cols_snip = ', ' || quote_literal(ignored_cols);
        END IF;
        _q_txt = 'CREATE TRIGGER audit_trigger_row AFTER INSERT OR UPDATE OR DELETE ON ' || 
                 target_table || 
                 ' FOR EACH ROW EXECUTE PROCEDURE audit.if_modified_func(' ||
                 quote_literal(audit_query_text) || _ignored_cols_snip || ');';
        RAISE NOTICE '%',_q_txt;
        EXECUTE _q_txt;
        stm_targets = 'TRUNCATE';
    END IF;

    _q_txt = 'CREATE TRIGGER audit_trigger_stm AFTER ' || stm_targets || ' ON ' ||
             target_table ||
             ' FOR EACH STATEMENT EXECUTE PROCEDURE audit.if_modified_func('||
             quote_literal(audit_query_text) || ');';
    RAISE NOTICE '%',_q_txt;
    EXECUTE _q_txt;

END;
$body$
language 'plpgsql';


COMMENT ON FUNCTION audit_table(regclass, boolean, boolean, text[]) IS $body$
Add auditing support to a table.

Arguments:
   target_table:     Table name, schema qualified if not on search_path
   audit_rows:       Record each row change, or only audit at a statement level
   audit_query_text: Record the text of the client query that triggered the audit event?
   ignored_cols:     Columns to exclude from update diffs, ignore updates that change only ignored cols.
$body$;


-- Pg doesn't allow variadic calls with 0 params, so provide a wrapper
CREATE FUNCTION audit_table(target_table regclass, audit_rows boolean, audit_query_text boolean) RETURNS void AS $body$
SELECT audit.audit_table($1, $2, $3, ARRAY[]::text[]);
$body$ LANGUAGE SQL;


-- And provide a convenience call wrapper for the simplest case
-- of row-level logging with no excluded cols and query logging enabled.
--
CREATE FUNCTION audit_table(target_table regclass) RETURNS void AS $$
SELECT audit.audit_table($1, BOOLEAN 't', BOOLEAN 't');
$$ LANGUAGE 'sql';


COMMENT ON FUNCTION audit_table(regclass) IS $body$
Add auditing support to the given table. Row-level changes will be logged with full client query text. No cols are ignored.
$body$;


SET search_path = content, pg_catalog;


CREATE FUNCTION get_content_label(p_data_id integer, p_type_id integer)
  RETURNS text AS
$$declare 
result text := null;
content_table_schema text;
content_table_name text;

begin
	SELECT table_schema, table_name INTO content_table_schema, content_table_name FROM content.type WHERE type_id = p_type_id;

	IF (select content.get_data_type_of_specified_column(content_table_schema, content_table_name, 'label') is not null) THEN
		EXECUTE 'SELECT label FROM ' || content_table_schema || '.' || content_table_name || ' WHERE log_data_id = ' || p_data_id INTO result;
	END IF;
	
	return result;
END;
$$ LANGUAGE plpgsql;


CREATE FUNCTION get_content_url( p_data_id integer, p_type_id integer)
  RETURNS text AS
$$declare 
application_url text;
table_url text;
content_url text;

begin
	SELECT a.url, b.url INTO application_url, table_url FROM public.application a JOIN content.type b ON a.application_id = b.application_id WHERE b.type_id = p_type_id;

	if p_type_id = 14 then
		content_url := application_url || table_url || p_data_id;
	elsif p_type_id = 12 then
		content_url := application_url || table_url || p_data_id;
	elsif p_type_id = 18 then
		content_url := application_url || table_url || p_data_id;
	elsif p_type_id = 15 then
		content_url := application_url || table_url || (SELECT pinboard_id FROM pinboard.pin WHERE log_data_id = p_data_id);
	elsif p_type_id = 3 then
		content_url := (SELECT url FROM content.url WHERE log_data_id = p_data_id);
	elsif p_type_id = 5 then
		content_url := '';
	end if;

	return content_url;
END;
$$ LANGUAGE plpgsql;


CREATE FUNCTION get_data_type_of_specified_column( p_table_schema text, p_table_name text, p_column_name text)
  RETURNS text AS
$$declare 
result text := null;

begin 

SELECT data_type INTO result FROM information_schema.columns WHERE table_schema = p_table_schema AND table_name = p_table_name AND column_name = p_column_name;

return result;

end;$$
LANGUAGE plpgsql;


CREATE FUNCTION get_search_result(
    p_label text,
    p_user_id integer,
    p_start_date timestamp with time zone,
    p_end_date timestamp with time zone,
    p_type_id integer)
  RETURNS SETOF t_content AS
$$declare
query text := '';
or_conditions text := '';
and_conditions text := '';
query_fields text := '';
first_or_condition_added boolean := false;
first_and_condition_added boolean := false;
list_fields content.v_field[];
current_field content.v_field;
current_field_data_type text := '';
current_table_schema text := '';
current_table_name text := '';
count_fields_done integer := 0;
count_fields_done_per_query integer := 0;
save_query boolean := false;

queries text[][];
query_to_send text := '';

begin
	query_fields := 'SELECT array_agg(a) FROM (SELECT * FROM content.v_field ORDER BY type_id) as a ';

	if p_type_id > 0 then
		query_fields := query_fields || 'WHERE a.type_id = ' || p_type_id;
	end if;

	execute query_fields into list_fields;

	if array_length(list_fields, 1) > 0 then
		foreach current_field in array list_fields loop
			SELECT * INTO current_field_data_type FROM content.get_data_type_of_specified_column(current_table_schema, current_table_name, current_field.field_id);

			if current_field_data_type = 'text' and p_label <> '' then
				if first_or_condition_added then
					or_conditions := or_conditions || 'OR ';
				end if;
				first_or_condition_added := true;

				or_conditions := or_conditions || '(a.' || current_field.field_id || ' LIKE ' || quote_literal('%' || p_label || '%') || ' OR (to_tsvector(' || quote_literal('pg_catalog.french') || ', a.' || current_field.field_id || ') @@ to_tsquery(' || quote_literal('pg_catalog.french') || ', ' || quote_literal(p_label) || '))) ';
			elsif current_field_data_type = 'timestamp with time zone' then
				if p_start_date is not null or p_end_date is not null then
					if p_start_date is null then
						p_start_date := timestamp '0-1-1 00:00';
					end if;
					if p_end_date is null then
						p_end_date := now();
					end if;
					if first_and_condition_added then
						and_conditions := and_conditions || 'AND ';
					end if;
					first_and_condition_added := true;

					and_conditions := and_conditions || '(a.' || current_field.field_id || ', a.' || current_field.field_id || ') OVERLAPS (' || quote_literal(p_start_date) || ', ' || quote_literal(p_end_date) || ') ' ;
				end if;
			elsif current_field.field_id = 'user_id' and p_user_id > 0 then
				if first_and_condition_added then
					and_conditions := and_conditions || 'AND ';
				end if;
				first_and_condition_added := true;

				and_conditions := and_conditions || '(a.' || current_field.field_id || ' = ' || p_user_id ||') ';
			end if;

			count_fields_done := count_fields_done + 1;
			count_fields_done_per_query := count_fields_done_per_query + 1;

			if array_length(list_fields, 1) > count_fields_done + 1 then
				if list_fields[count_fields_done + 1].type_id <> current_field.type_id then
					save_query := true;
				end if;
			elsif count_fields_done = array_length(list_fields, 1) then
				save_query := true;
			end if;

			if save_query then
				SELECT table_schema, table_name INTO current_table_schema, current_table_name FROM content.type WHERE type_id = current_field.type_id;
				query := 'SELECT DISTINCT ' || current_field.type_id || ' AS type_id, a.log_data_id AS log_data_id, content.get_content_label(a.log_data_id, ' || current_field.type_id || ') AS label, content.get_content_url(a.log_data_id, ' || current_field.type_id || ') AS url FROM ' || current_table_schema || '.' || current_table_name || ' a ';

				if count_fields_done_per_query > 0 then
					if and_conditions <> '' or or_conditions <> '' then
						query := query || 'WHERE ';
					end if;
				end if;
				
				queries := queries || ARRAY[[query, or_conditions, and_conditions]];

				first_or_condition_added := false;
				first_and_condition_added := false;
				or_conditions := '';
				and_conditions := '';
				count_fields_done_per_query := 0;
				save_query := false;
			end if;
		end loop;
	end if;

	if array_length(queries, 1) > 0 then
		for i in array_lower(queries, 1)..array_upper(queries, 1) loop	
			if queries[i][1] <> '' then
				query_to_send := queries[i][1];

				if queries[i][2] <> '' then
					query_to_send := query_to_send || '(' || queries[i][2] || ') ';
				end if;

				if queries[i][3] <> '' then
					if queries[i][2] <> '' then
						query_to_send := query_to_send || 'AND ';
					end if;
					query_to_send := query_to_send || '(' || queries[i][3] || ') ';
				end if;
				
				query_to_send := query_to_send || ';';

				return query execute query_to_send;
			end if;
		end loop;
	end if;
END;
$$ LANGUAGE plpgsql;
  

CREATE FUNCTION get_search_result_with_permission(
    p_seeker_user_id integer,
    p_label text,
    p_user_id integer,
    p_start_date timestamp with time zone,
    p_end_date timestamp with time zone,
    p_type_id integer,
    p_sort_by text)
  RETURNS SETOF t_content AS
$$declare
query text := '';
search_raw_result t_content;
permission_allowed_id integer[];
previous_type_id integer := 0;

begin
	query := 'SELECT DISTINCT * FROM content.get_search_result(' || quote_literal(p_label) || ', ' || p_user_id || ', ' || quote_literal(p_start_date) || ', ' || quote_literal(p_end_date) || ', ' || p_type_id || ') ';

	if p_sort_by = 'ALPHABETICAL' then
		query := query || 'ORDER BY label';
	elsif p_sort_by = 'LATEST_FIRST' then
		query := query || 'ORDER BY registration DESC';
	elsif p_sort_by = 'OLDEST_FIRST' then
		query := query || 'ORDER BY registration ASC';
	else
		query := query || 'ORDER BY type_id';
	end if;

	for search_raw_result in execute query loop
		if search_raw_result.type_id <> previous_type_id then
			if search_raw_result.type_id = 14 then
				permission_allowed_id := ARRAY(SELECT DISTINCT b.log_data_id FROM discussion.get_messages(p_seeker_user_id, -1, true, false, '') a JOIN discussion.message b ON a.message_id = b.message_id);
			elsif search_raw_result.type_id = 12 then
				permission_allowed_id := ARRAY(SELECT DISTINCT b.log_data_id FROM discussion.get_discussions(p_seeker_user_id, -1, false, '') a JOIN discussion.discussion b ON a.discussion_id = b.discussion_id);
			elsif search_raw_result.type_id = 18 then
				permission_allowed_id := ARRAY(SELECT DISTINCT b.log_data_id FROM pinboard.get_pinboards(p_seeker_user_id, -1, false, false, '', 'ALPHABETICAL') a JOIN pinboard.pinboard b ON a.pinboard_id = b.pinboard_id);
			elsif search_raw_result.type_id = 15 then
				permission_allowed_id := ARRAY(SELECT DISTINCT pinboard_id FROM pinboard.get_pinboards(p_seeker_user_id, -1, false, false, '', 'ALPHABETICAL'));
				permission_allowed_id := ARRAY(SELECT DISTINCT a.log_data_id FROM pinboard.pin a WHERE ARRAY[pinboard_id] <@ permission_allowed_id);
			elsif search_raw_result.type_id = 3 then
				permission_allowed_id := ARRAY(SELECT DISTINCT b.log_data_id FROM content.url a JOIN content.url b ON a.url_id = b.url_id WHERE a.user_id = p_seeker_user_id);
			elsif search_raw_result.type_id = 5 then
				permission_allowed_id := ARRAY(SELECT DISTINCT b.log_data_id FROM file.get_files(p_seeker_user_id, false, -1) a JOIN file.file b ON a.file_id = b.file_id);
			elsif search_raw_result.type_id = 24 then
				permission_allowed_id := ARRAY(SELECT DISTINCT b.log_data_id FROM public.get_administrated_groups_by_user(p_seeker_user_id) a JOIN public.groups b ON a.group_id = b.group_id UNION SELECT DISTINCT b.log_data_id FROM public.get_member_groups_of_user(p_seeker_user_id) a JOIN public.groups b ON a.group_id = b.group_id);
			else
				permission_allowed_id := ARRAY[search_raw_result.log_data_id];
			end if;
		end if;

		if permission_allowed_id @> ARRAY[search_raw_result.log_data_id] then
			return next search_raw_result;
		end if;

		previous_type_id := search_raw_result.type_id;
	end loop;
END;
$$ LANGUAGE plpgsql;
  

SET search_path = discussion, pg_catalog;


CREATE FUNCTION get_discussions(p_user_id integer, p_discussion_id integer, p_retrieve_only_can_edit boolean, p_sort_by text) RETURNS SETOF v_discussion
    AS $$declare
query text;

begin
	query := 'SELECT * FROM (SELECT DISTINCT a.discussion_id, a.label, a.user_id, a.registration FROM discussion.v_discussion a JOIN discussion.v_discussion_group b ON ((a.discussion_id = b.discussion_id) AND (b.group_id IN (SELECT group_id FROM public.get_member_groups_of_user(' || p_user_id || '))) ';

	query := query || 'AND (b.can_edit = true ';
	
	if not p_retrieve_only_can_edit then
		query := query || 'OR (b.can_edit = false AND now() >= b.start_valid) ';
	end if;
	
	query := query || ')) UNION (SELECT c.discussion_id, c.label, c.user_id, c.registration FROM discussion.v_discussion c WHERE c.user_id = ' || p_user_id || ')) AS z ';

	if p_discussion_id  > 0 then 
		query := query || ' WHERE z.discussion_id = ' || p_discussion_id || ' ';
	end if;
	
	if p_sort_by = 'ALPHABETICAL' then
		query := query || 'ORDER BY z.label';
	elsif p_sort_by = 'LATEST_FIRST' then
		query := query || 'ORDER BY z.registration DESC';
	elsif p_sort_by = 'OLDEST_FIRST' then
		query := query || 'ORDER BY z.registration ASC';
	end if;

	query := query || ';';

	return query execute query;
END;
$$ LANGUAGE plpgsql;


CREATE FUNCTION get_groups_to_notify_about_discussion(p_schema_and_table_name text, p_log_data_id integer) RETURNS integer[]
    LANGUAGE plpgsql
    AS $$DECLARE

list_group_id integer[];
related_discussion_id integer;

BEGIN
	EXECUTE 'SELECT discussion_id FROM ' || p_schema_and_table_name || ' WHERE log_data_id = ' || p_log_data_id INTO related_discussion_id;

	list_group_id := ARRAY(SELECT DISTINCT group_id
							FROM discussion.discussion_group
							WHERE discussion_id = related_discussion_id);

	RETURN list_group_id;
END;$$;


CREATE FUNCTION get_groups_to_notify_about_message(p_schema_and_table_name text, p_log_data_id integer) RETURNS integer[]
    LANGUAGE plpgsql
    AS $$DECLARE

list_group_id integer[];
related_message_id integer;

BEGIN
	EXECUTE 'SELECT message_id FROM ' || p_schema_and_table_name || ' WHERE log_data_id = ' || p_log_data_id INTO related_message_id;

	list_group_id := ARRAY(SELECT DISTINCT group_id
							FROM discussion.discussion_group a
							JOIN discussion.message b ON (a.discussion_id = b.discussion_id) 
							WHERE b.message_id = related_message_id);

	RETURN list_group_id;
END;$$;


CREATE FUNCTION get_messages(p_user_id integer, p_message_id integer, p_retrieve_children boolean, p_retrieve_only_user_messages boolean, p_sort_by text) RETURNS SETOF v_message
    AS $$declare
query text;

begin
	query := 'SELECT * FROM (SELECT DISTINCT b.node_id, b.parent_id, b.message_id, b.user_id, b.label, b.depth, b.path, b.nb_children, b.discussion_id, b.vote, b.registration FROM discussion.get_discussions(' || p_user_id || ', -1, false,' || quote_literal(p_sort_by) || ') a JOIN discussion.v_message b ON (b.discussion_id = a.discussion_id) ';

	if p_retrieve_only_user_messages then
		query := query || 'AND (b.user_id = ' || p_user_id || ') ';
	end if;

	query := query || ') AS z ';

	if p_message_id > 0 then
		if not p_retrieve_children then
			query := query ||  'WHERE z.message_id = ' || p_message_id || ' ';
		else
			query := query || 'WHERE z.node_id = ' || p_message_id || ' ';
		end if;
	end if;

	query := query || 'ORDER BY z.depth';

	if p_sort_by = 'ALPHABETICAL' then
		query := query || ', z.label';
	elsif p_sort_by = 'MOST_LIKED' then
		query := query || ', z.vote DESC';
	elsif p_sort_by = 'LATEST_FIRST' then
		query := query || ', z.registration DESC';
	elsif p_sort_by = 'OLDEST_FIRST' then
		query := query || ', z.registration ASC';
	end if;

	query := query || ';';

	return query execute query;
END;
$$ LANGUAGE plpgsql;


SET search_path = file, pg_catalog;


CREATE FUNCTION get_files(p_user_id integer, p_retrieve_can_edit boolean, p_file_id integer)
  RETURNS SETOF file.v_file AS
$$declare
query text;
result file.v_file%rowtype;

begin
	if p_user_id > 0 then
		query := 'SELECT * FROM (SELECT c.file_id, c.label, c.user_id, c.registration FROM file.v_file c WHERE (c.user_id = ' || p_user_id || ') UNION (SELECT DISTINCT a.file_id AS file_id, a.label, a.user_id, a.registration FROM file.v_file a JOIN file.v_file_group b ON ((a.file_id = b.file_id) AND (b.group_id IN (SELECT group_id FROM public.get_member_groups_of_user(' || p_user_id || '))) ';

		query := query || 'AND (b.can_edit = true ';
		
		if not p_retrieve_can_edit then
			query := query || 'OR (b.can_edit = false AND now() >= b.start_valid) ';
		end if;

		query := query || ')))) AS z ';

		if p_file_id > 0 then
			query := query || 'WHERE z.file_id = ' || p_file_id || ' ';
		end if;

		query := query || 'ORDER BY z.label';

		return query execute query;
	end if;
END;
$$ LANGUAGE plpgsql;


CREATE FUNCTION get_folders(p_user_id integer, p_retrieve_can_edit boolean, p_retrieve_subfolders boolean, p_folder_id integer, p_parent_id integer)
  RETURNS SETOF file.v_folder AS
$$declare
query text;

result file.v_folder%rowtype;
first_condition_added boolean := false;

begin
	if p_user_id > 0 then
		query := 'SELECT * FROM (SELECT c.folder_id, c.parent_id, c.label, c.user_id, c.registration AS registration FROM file.v_folder c WHERE (c.user_id = ' || p_user_id || ') UNION (SELECT DISTINCT a.folder_id, a.label, a.user_id, a.registration FROM file.v_folder a JOIN file.v_folder_group b ON ((a.folder_id = b.folder_id) AND (b.group_id IN (SELECT group_id FROM public.get_member_groups_of_user(' || p_user_id || '))) ';

		query := query || 'AND (b.can_edit = true ';
		
		if not p_retrieve_only_can_edit then
			query := query || 'OR (b.can_edit = false AND now() >= b.start_valid) ';
		end if;

		query := query || ')))) AS z ';
		
		if p_retrieve_subfolders then
			if p_parent_id > 0 then
				query := query || 'WHERE z.parent_id = ' || p_parent_id || ' ';
				first_condition_added := true;
			end if;
		else
			query := query || 'WHERE z.parent_id IS NULL ';
			first_condition_added := true;
		end if;
		
		if p_folder_id > 0 then
			if first_condition_added then
				query := query || 'AND ';
			else
				query := query || 'WHERE ';
			end if;
			
			query := query || 'z.folder_id = ' || p_folder_id || ' ';
		end if;

		query := query || 'ORDER BY z.label';

		return query execute query;
	end if;
END;
$$ LANGUAGE plpgsql;


CREATE FUNCTION get_groups_to_notify_about_file(p_schema_and_table_name text, p_log_data_id integer) RETURNS integer[]
    LANGUAGE plpgsql
    AS $$DECLARE

list_group_id integer[];
related_file_id integer;

BEGIN

EXECUTE 'SELECT file_id FROM ' || p_schema_and_table_name || ' WHERE log_data_id = ' || p_log_data_id INTO related_file_id;

list_group_id := ARRAY(SELECT DISTINCT group_id
						FROM file.file_group
						WHERE file_id = related_file_id);
						
RETURN list_group_id;

END;$$;


CREATE FUNCTION get_groups_to_notify_about_folder(p_schema_and_table_name text, p_log_data_id integer) RETURNS integer[]
    LANGUAGE plpgsql
    AS $$DECLARE

list_group_id integer[];
related_folder_id integer;

BEGIN

EXECUTE 'SELECT folder_id FROM ' || p_schema_and_table_name || ' WHERE log_data_id = ' || p_log_data_id INTO related_folder_id;

list_group_id := ARRAY(SELECT DISTINCT group_id
						FROM file.folder_group
						WHERE folder_id = related_folder_id);
						
RETURN list_group_id;

END;$$;


CREATE FUNCTION get_turn_in_folders(p_user_id integer)
  RETURNS SETOF file.v_turn_in_folder AS
$$declare
query text;
result file.v_turn_in_folder%rowtype;

begin
	if p_user_id > 0 then
		query := 'SELECT * FROM (SELECT c.folder_id, c.opening_time_id, c.closing_time, c.user_id FROM file.v_turn_in_folder c WHERE (c.user_id = ' || p_user_id || ') UNION (SELECT DISTINCT a.folder_id, a.opening_time, a.closing_time, a.user_id FROM file.v_turn_in_folder a JOIN file.v_folder_group b ON ((a.folder_id = b.folder_id) AND (b.group_id IN (SELECT group_id FROM public.get_member_groups_of_user(' || p_user_id || ')))))) AS z ORDER BY z.label';

		return query execute query;
	end if;
END;
$$ LANGUAGE plpgsql;


SET search_path = notification, pg_catalog;


CREATE FUNCTION get_user_notification(p_user_id integer, p_list_notification_id_already_loaded text, p_limit integer) RETURNS SETOF v_user_notification
    AS $$declare
query text;

begin
	query := 'SELECT * FROM notification.v_user_notification z WHERE (z.user_id = ' || p_user_id || ' ';

	if (p_list_notification_id_already_loaded <> '()') then
		query := query || 'AND z.event_id NOT IN ' || p_list_notification_id_already_loaded || ' ';
	end if;

	query := query || ') ORDER BY z.registration DESC ';

	if (p_limit > 0) then 
		query := query || 'LIMIT ' || p_limit || ' ';
	end if;

	query := query || ';';

	return query execute query;
END;
$$ LANGUAGE plpgsql;


CREATE FUNCTION notify_user(p_event_id bigint, p_user_id integer) RETURNS void
    AS $$

BEGIN
	IF(p_event_id IS NOT NULL AND p_user_id IS NOT NULL) THEN
		INSERT INTO notification.user_notification (user_id, event_id) (SELECT p_user_id AS user_id, p_event_id AS event_id
																			WHERE NOT EXISTS (SELECT 1 
																								FROM notification.user_notification 
																								WHERE user_id = p_user_id AND event_id = p_event_id));
	END IF;
END;
$$ LANGUAGE plpgsql;


SET search_path = pinboard, pg_catalog;


CREATE FUNCTION get_groups_to_notify_about_pin(p_schema_and_table_name text, p_log_data_id integer) RETURNS integer[]
    LANGUAGE plpgsql
    AS $$DECLARE

list_group_id integer[];
related_pin_id integer;

BEGIN

EXECUTE 'SELECT pin_id FROM ' || p_schema_and_table_name || ' WHERE log_data_id = ' || p_log_data_id INTO related_pin_id;

list_group_id := ARRAY(SELECT DISTINCT group_id
						FROM pinboard.pinboard_group a
						JOIN pinboard.pin_geometry b ON (a.pinboard_id = b.pinboard_id)
						JOIN pinboard.pin c ON (a.pinboard_id = c.pinboard_id) 
						WHERE c.pin_id = related_pin_id);
						
RETURN list_group_id;

END;$$;


CREATE FUNCTION get_groups_to_notify_about_pinboard(p_schema_and_table_name text, p_log_data_id integer) RETURNS integer[]
    LANGUAGE plpgsql
    AS $$DECLARE

list_group_id integer[];
related_pinboard_id integer;

BEGIN

EXECUTE 'SELECT pinboard_id FROM ' || p_schema_and_table_name || ' WHERE log_data_id = ' || p_log_data_id INTO related_pinboard_id;

list_group_id := ARRAY(SELECT DISTINCT group_id
						FROM pinboard.pinboard_group
						WHERE pinboard_id = related_pinboard_id);
						
RETURN list_group_id;

END;$$;


CREATE FUNCTION pinboard.get_pins(p_user_id integer, p_pinboard_id integer, p_retrieve_only_direct_pins boolean)
  RETURNS SETOF pinboard.v_pin AS 
  $$DECLARE
query text;

BEGIN

	IF p_user_id > 0 AND p_pinboard_id > 0 THEN
		query := 'SELECT DISTINCT a.pin_id, a.pinboard_id, a.label, a.vote, a.user_id, a.registration FROM pinboard.v_pin a JOIN pinboard.get_pinboards( ' || p_user_id || ', -1, false, false, ' || quote_liretal('') || ', ' || quote_literal('ALPHABETICAL') ||') b ON a.pinboard_id = b.pinboard_id AND (a.pinboard_id = ' || p_pinboard_id || ' ';
		
		if not p_retrieve_only_direct_pins then
			query := query || 'OR a.pinboard_id IN (SELECT pinboard_id FROM pinboard.v_pinboard_pinboard_node WHERE node_id = ' || p_pinboard_id || ')';
		end if;
		
		query := query || ');';
	END IF;
	RETURN query EXECUTE query;
END;
$$ LANGUAGE plpgsql;


CREATE FUNCTION pinboard.get_pinboards(p_user_id integer, p_pinboard_id integer, p_retrieve_only_can_edit boolean, p_retrieve_only_roots boolean, p_filter text, p_sort_by text)
  RETURNS SETOF pinboard.v_pinboard AS 
  $$DECLARE
query text;
where_clause_present_in_principal_query boolean := false;

BEGIN
	query := 'SELECT * FROM (SELECT c.pinboard_id, c.label, c.user_id, c.registration FROM pinboard.v_pinboard c WHERE (c.user_id = ' || p_user_id || ') UNION SELECT DISTINCT a.pinboard_id, a.label, a.user_id, a.registration FROM pinboard.v_pinboard a JOIN pinboard.v_pinboard_group b ON (a.pinboard_id = b.pinboard_id AND b.group_id IN (SELECT group_id FROM public.get_member_groups_of_user(' || p_user_id || ')) AND (b.can_edit = true ';
	
	if not p_retrieve_only_can_edit then
		query := query || 'OR (b.can_edit = false AND now() >= b.start_valid) ';
	end if;
	
	query := query || '))) AS z ';

	IF p_retrieve_only_roots THEN
		query := query ||  'WHERE NOT EXISTS(SELECT 1 FROM pinboard.v_pinboard_pinboard WHERE pinboard_id = z.pinboard_id) ';
		where_clause_present_in_principal_query := true;
	END IF;
	
	IF p_pinboard_id > 0 THEN
		IF NOT where_clause_present_in_principal_query THEN
			query := query || 'WHERE ';
			where_clause_present_in_principal_query := true;
		ELSE
			query := query || 'AND ';
		END IF;
		
		query := query ||  'z.pinboard_id = ' || p_pinboard_id;
	END IF;
	
	IF p_filter is not null AND p_filter <> '' THEN
		IF NOT where_clause_present_in_principal_query THEN
			query := query || 'WHERE ';
			where_clause_present_in_principal_query := true;
		ELSE
			query := query || 'AND ';
		END IF;
		
		query := query ||  'z.label LIKE ' || quote_literal('%' || p_filter || '%') || ' OR (to_tsvector(' || quote_literal('pg_catalog.french') || ', z.label) @@ to_tsquery(' || quote_literal('pg_catalog.french') || ', ' || quote_literal(p_filter) || ')) ';
	END IF;

	IF p_sort_by = 'ALPHABETICAL' THEN
		query := query || 'ORDER BY z.label';
	ELSIF p_sort_by = 'LATEST_FIRST' THEN
		query := query || 'ORDER BY z.registration DESC';
	ELSIF p_sort_by = 'OLDEST_FIRST' THEN
		query := query || 'ORDER BY z.registration ASC';
	END IF;

	query := query || ';';

	RETURN query EXECUTE query;
END;
$$ LANGUAGE plpgsql;


SET search_path = public, pg_catalog;


CREATE FUNCTION get_administrated_groups_by_user(p_user_id integer) RETURNS SETOF v_group
    AS $$declare
query text;

begin

if p_user_id > 0 then
	
	query := 'SELECT DISTINCT * FROM (SELECT c.group_id, c.label, c.description, c.unique_label, c.user_id, c.registration FROM public.v_administrator_group a JOIN public.v_user_group_node b ON a.administrator_id = b.group_id AND b.member_id = ' || p_user_id || ' JOIN public.v_group c ON a.group_id = c.group_id';
	query := query || ' UNION SELECT d.group_id, d.label, d.description, d.unique_label, d.user_id, d.registration FROM public.v_group d WHERE d.user_id = ' || p_user_id || ') z ORDER BY z.label';

	if query <> '' then
		return query execute query;
	end if;
end if;
END;
$$ LANGUAGE plpgsql;


CREATE FUNCTION get_application_privilege_groups_of_user(p_user_id integer, p_application_id integer)
  RETURNS SETOF v_application_privilege_group AS
$$declare
query text;

begin
	if p_user_id > 0 then
		query := 'SELECT * FROM (SELECT a.application_id, a.privilege_id, a.group_id, a.user_id, a.registration FROM public.v_application_privilege_group a JOIN public.v_user_group_node b ON (a.group_id = b.group_id) WHERE (b.member_id = ' || p_user_id;

		if p_application_id > 0 then
			query := query || ' AND a.application_id = ' || p_application_id;
		end if;

		query := query || ')) z ORDER BY z.application_id;';
		
		return query execute query;
	end if;
END;
$$ LANGUAGE plpgsql; 


CREATE FUNCTION get_groups_to_notify_about_application_privilege(p_schema_and_table_name text, p_log_data_id integer) RETURNS integer[]
    LANGUAGE plpgsql
    AS $$DECLARE

list_group_id integer[];
related_application_id integer;
related_privilege_id integer;

BEGIN
	EXECUTE 'SELECT application_id, privilege_id FROM ' || p_schema_and_table_name || ' WHERE log_data_id = ' || p_log_data_id INTO related_application_id, related_privilege_id;

	list_group_id := ARRAY(SELECT DISTINCT group_id
							FROM public.application_privilege_group
							WHERE application_id = related_application_id AND privilege_id = related_privilege_id);
	
	RETURN list_group_id;
END;$$;


CREATE FUNCTION get_groups_to_notify_about_group(p_schema_and_table_name text, p_log_data_id integer) RETURNS integer[]
    LANGUAGE plpgsql
    AS $$DECLARE

list_group_id integer[];
related_group_id integer;

BEGIN
	EXECUTE 'SELECT group_id FROM ' || p_schema_and_table_name || ' WHERE log_data_id = ' || p_log_data_id INTO related_group_id;

	list_group_id := ARRAY(SELECT DISTINCT parent_id
							FROM public.v_group_group_node
							WHERE group_id = related_group_id);
	
	RETURN list_group_id;
END;$$;


CREATE FUNCTION get_groups_to_notify_about_administrator_group(p_schema_and_table_name text, p_log_data_id integer) RETURNS integer[]
    LANGUAGE plpgsql
    AS $$DECLARE

list_group_id integer[];
related_group_id integer;

BEGIN
	EXECUTE 'SELECT group_id FROM ' || p_schema_and_table_name || ' WHERE log_data_id = ' || p_log_data_id INTO related_group_id;

	list_group_id := ARRAY(SELECT DISTINCT administrator_id
							FROM public.administrator_group
							WHERE group_id = related_group_id);
	
	RETURN list_group_id;
END;$$;


CREATE FUNCTION get_member_groups_of_user(p_user_id integer) RETURNS SETOF v_group
    AS $$declare
query text;

begin
	if p_user_id > 0 then
		query := 'SELECT * FROM public.v_group a WHERE a.group_id IN (SELECT DISTINCT b.group_id FROM public.v_user_group_node b WHERE b.member_id = ' || p_user_id || ') ORDER BY label;';

		return query execute query;
	end if;
END;
$$ LANGUAGE plpgsql;


CREATE FUNCTION get_direct_and_indirect_users_of_group(p_group_id integer) RETURNS SETOF v_user
    AS $$declare
query text;

begin
	if(p_group_id > 0) then
		query := 'SELECT DISTINCT ON (f.last_name, f.first_name, f.user_id) * FROM (SELECT a.* FROM public.v_user a JOIN public.v_user_group_node b ON (a.user_id = b.member_id) JOIN public.v_group_group_node c ON (b.group_id = c.group_id) WHERE (b.group_id = ' || p_group_id || ') UNION SELECT d.* FROM public.v_user d JOIN public.v_user_group_node e ON (d.user_id = e.member_id) WHERE (e.group_id = ' || p_group_id || ')) f ORDER BY f.last_name, f.first_name, f.user_id;';

		return query execute query;
	end if;
END;
$$ LANGUAGE plpgsql;


CREATE FUNCTION split_group(p_nb_new_children integer, p_nb_user_by_new_child integer, p_group_id integer, p_user_id integer, p_postfix text) RETURNS SETOF v_group
    AS $$DECLARE
source_group v_group%rowtype; 
list_user_members integer[];
nb_user_members real;
nb_new_children real := p_nb_new_children;
nb_user_by_new_child real := p_nb_user_by_new_child;
random_user_member_id integer;
i integer := 0;
new_group_label text;
new_group_id integer;
list_new_group_id integer[];

BEGIN
	if(p_group_id > 0) then
		SELECT array_agg(a.user_id) INTO list_user_members FROM public.get_direct_and_indirect_users_of_group(p_group_id) a;
		nb_user_members := array_length(list_user_members, 1);

		if(nb_user_members > 0) then
			SELECT * INTO source_group FROM public.v_group WHERE group_id = p_group_id;
			
			if(nb_new_children > 0) then
				if(nb_new_children > nb_user_members) then
					nb_new_children := nb_user_members;
				end if;
			elsif(nb_user_by_new_child > 0) then
				nb_new_children := ceil(nb_user_members / nb_user_by_new_child);
			else
				return;
			end if;
			
			if(nb_new_children > 0) then
				for i in 1..nb_new_children loop
					new_group_label := source_group.label || '-' || p_postfix;
					if i < 10 then
						new_group_label := new_group_label || '0';
					end if;
					new_group_label := new_group_label || i::text;

					new_group_id := nextval('public.groups_group_id_seq');
					
					INSERT INTO public.v_group (group_id, label, description, unique_label, user_id) VALUES (new_group_id, new_group_label, source_group.description, false, p_user_id);
					
					list_new_group_id := list_new_group_id || new_group_id;
				end loop;
				
				i := 1;
				while(array_length(list_user_members, 1) > 0) loop
					random_user_member_id := list_user_members[trunc(random()*array_length(list_user_members, 1)) + 1];
					list_user_members := array_remove(list_user_members, random_user_member_id);
					INSERT INTO public.v_user_group (group_id, member_id, user_id) VALUES (list_new_group_id[i], random_user_member_id, p_user_id);
					i := i % array_length(list_new_group_id, 1) + 1;
				end loop;
			end if;
		end if;
		
		return query SELECT * FROM public.v_group WHERE group_id = ANY(list_new_group_id);
	end if;
END;$$ LANGUAGE plpgsql;
