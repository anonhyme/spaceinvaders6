-- ********************************* TRIGGER FUNCTIONS ********************************* --

SET search_path = audit, pg_catalog;


CREATE FUNCTION event_trig_funct() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

list_group_id integer[];
function_name text;
g_id integer;
u_id integer;
list_user_column_name text[][2];
user_column_name text[2];
list_tmp_users_id_to_notify integer[];
tmp_user_id_to_notify integer;
list_users_to_notify integer[];

BEGIN
IF(NEW.log_data_id IS NOT NULL) THEN
	SELECT function_name_to_notify_groups_about_an_event INTO function_name FROM content.type WHERE table_schema = NEW.table_schema AND table_name = new.table_name;

	IF(function_name IS NOT NULL) THEN
		EXECUTE 'SELECT * FROM ' || function_name || '(' || quote_literal(NEW.table_schema || '.' || NEW.table_name) || ', ' || NEW.log_data_id || ')' INTO list_group_id;
		
		IF(array_length(list_group_id, 1) > 0) THEN
			FOR g_id IN (SELECT unnest(list_group_id)) LOOP
				SELECT array_agg(user_id) INTO list_tmp_users_id_to_notify FROM public.get_direct_and_indirect_users_of_group(g_id);
				list_users_to_notify := list_users_to_notify || list_tmp_users_id_to_notify;
			END LOOP;
		END IF;
	ELSE
		SELECT ARRAY[tc.table_schema || '.' || tc.table_name, kcu.column_name]
			INTO list_user_column_name
			FROM information_schema.table_constraints AS tc 
			JOIN information_schema.key_column_usage AS kcu
			  ON tc.constraint_name = kcu.constraint_name
			JOIN information_schema.constraint_column_usage AS ccu
			  ON ccu.constraint_name = tc.constraint_name
			WHERE constraint_type = 'FOREIGN KEY' AND tc.table_schema = new.table_schema AND tc.table_name = new.table_name AND ccu.table_schema = 'public' AND ccu.table_name = 'users' AND ccu.column_name = 'user_id';
		
		IF(array_length(list_user_column_name, 1) > 0) THEN	
			FOREACH user_column_name SLICE 1 IN ARRAY list_user_column_name LOOP
				EXECUTE 'SELECT ' || user_column_name[2] || ' FROM ' || user_column_name[1] || ' WHERE log_data_id = ' || NEW.log_data_id INTO tmp_user_id_to_notify;
				list_users_to_notify := list_users_to_notify || tmp_user_id_to_notify;
			END LOOP;
		END IF;
	END IF;

	IF(array_length(list_users_to_notify, 1) > 0) THEN
		FOR u_id IN (SELECT unnest(list_users_to_notify)) LOOP
			IF(NEW.user_id <> u_id) THEN
				PERFORM notification.notify_user(NEW.event_id, u_id);
			END IF;
		END LOOP;

		PERFORM pg_notify('notification', NEW.event_id::text);
	END IF;
END IF;

RETURN NULL; -- result is ignored since this is an AFTER trigger

END;$$;


SET search_path = discussion, pg_catalog;


CREATE FUNCTION message_check_no_cycle_trig_funct() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

list_group_id integer[];
	
BEGIN

IF NEW.message_id <> NEW.parent_id THEN
	IF NOT EXISTS(SELECT 1 FROM discussion.v_message_ WHERE node_id = NEW.message_id AND message_id = NEW.parent_id) THEN
	  RETURN NEW;
	ELSE
	  RETURN NULL;
	END IF;
ELSE
	RETURN NULL;
END IF;

END;$$;


SET search_path = file, pg_catalog;


CREATE FUNCTION folder_check_no_cycle_trig_funct() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

list_group_id integer[];
	
BEGIN

IF NEW.folder_id <> NEW.parent_id THEN
	IF NOT EXISTS(SELECT 1 FROM file.v_folder WHERE node_id = NEW.folder_id AND folder_id = NEW.parent_id) THEN
	  RETURN NEW;
	ELSE
	  RETURN NULL;
	END IF;
ELSE
	RETURN NULL;
END IF;

END;$$;


SET search_path = pinboard, pg_catalog;


CREATE FUNCTION pinboard_pinboard_check_no_cycle_trig_funct() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

list_group_id integer[];
	
BEGIN

IF NEW.pinboard_id <> NEW.parent_id THEN
	IF NOT EXISTS(SELECT 1 FROM pinboard.v_pinboard_pinboard_node WHERE node_id = NEW.pinboard_id AND pinboard_id = NEW.parent_id) THEN
	  RETURN NEW;
	ELSE
	  RETURN NULL;
	END IF;
ELSE
	RETURN NULL;
END IF;

END;$$;


SET search_path = public, pg_catalog;


CREATE FUNCTION group_group_check_no_cycle_trig_funct() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

list_group_id integer[];
	
BEGIN

IF NEW.group_id <> NEW.parent_id THEN
	IF NOT EXISTS(SELECT 1 FROM public.v_group_group_node WHERE node_id = NEW.group_id AND group_id = NEW.parent_id) THEN
	  RETURN NEW;
	ELSE
	  RETURN NULL;
	END IF;
ELSE
	RETURN NULL;
END IF;

END;$$;


-- ********************************* TRIGGERS ********************************* --


SET search_path = audit, pg_catalog;


CREATE TRIGGER event_trig AFTER INSERT ON event FOR EACH ROW EXECUTE PROCEDURE event_trig_funct();


SET search_path = discussion, pg_catalog;


CREATE TRIGGER message_check_no_cycle_trig BEFORE INSERT OR UPDATE ON message FOR EACH ROW EXECUTE PROCEDURE message_check_no_cycle_trig_funct();


SET search_path = file, pg_catalog;


CREATE TRIGGER folder_check_no_cycle_trig BEFORE INSERT OR UPDATE ON folder FOR EACH ROW EXECUTE PROCEDURE folder_check_no_cycle_trig_funct();


SET search_path = pinboard, pg_catalog;


CREATE TRIGGER pinboard_pinboard_check_no_cycle_trig BEFORE INSERT OR UPDATE ON pinboard_pinboard FOR EACH ROW EXECUTE PROCEDURE pinboard_pinboard_check_no_cycle_trig_funct();



SET search_path = public, pg_catalog;


CREATE TRIGGER group_group_check_no_cycle_trig BEFORE INSERT OR UPDATE ON group_group FOR EACH ROW EXECUTE PROCEDURE group_group_check_no_cycle_trig_funct();
