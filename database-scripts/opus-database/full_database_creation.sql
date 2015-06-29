-- THE USER LOGGED IN TO CREATE EVERY ELEMENT IN THE DB MUST BE appopus. OTHERWISE, AT THE END OF THE LAST SCRIPT, THE QUERY USED TO CHANGE THE OWNERSHIP FROM appopus TO opus WON'T AFFECT ALL OWNERSHIPS.

CREATE SCHEMA audit;
CREATE SCHEMA content;
CREATE SCHEMA discussion;
CREATE SCHEMA file;
CREATE SCHEMA notification;
CREATE SCHEMA pinboard;

COMMENT ON SCHEMA audit IS 'Out-of-table audit/history logging tables and trigger functions';

SET default_tablespace = '';
SET default_with_oids = false;


CREATE EXTENSION IF NOT EXISTS hstore;


CREATE SEQUENCE log_data_id_seq;


CREATE TYPE content.t_content AS (
    type_id integer,
	log_data_id integer,
	label text,
   	url text
);


CREATE TABLE audit.event (
    event_id bigserial,
    table_schema text NOT NULL,
	table_name text NOT NULL,
	log_data_id integer,
	user_id integer,
    action text NOT NULL CHECK (action IN ('I','D','U', 'T')),
    row_data hstore,
    changed_fields hstore,
	statement_only boolean not null,
	session_user_name text,
	application_name text,
    client_addr inet,
    client_port integer,
    client_query text,
	registration timestamp with time zone NOT NULL DEFAULT now()
);


SET search_path = audit, pg_catalog;


REVOKE ALL ON event FROM public;

COMMENT ON TABLE event IS 'History of auditable actions on audited tables, from audit.if_modified_func()';
COMMENT ON COLUMN event.event_id IS 'Unique identifier for each auditable event';
COMMENT ON COLUMN event.table_schema IS 'Database schema audited table for this event is in';
COMMENT ON COLUMN event.table_name IS 'Non-schema-qualified table name of table event occured in';
COMMENT ON COLUMN event.log_data_id IS 'Unique identifier of row event occured on';
COMMENT ON COLUMN event.user_id IS 'Opus user whose statement caused the audited event';
COMMENT ON COLUMN event.action IS 'Action type; I = insert, D = delete, U = update, T = truncate';
COMMENT ON COLUMN event.row_data IS 'Record value. Null for statement-level trigger. For INSERT this is the new tuple. For DELETE and UPDATE it is the old tuple.';
COMMENT ON COLUMN event.changed_fields IS 'New values of fields changed by UPDATE. Null except for row-level UPDATE events.';
COMMENT ON COLUMN event.statement_only IS '''t'' if audit event is from an FOR EACH STATEMENT trigger, ''f'' for FOR EACH ROW';
COMMENT ON COLUMN event.session_user_name IS 'Login / session user whose statement caused the audited event';
COMMENT ON COLUMN event.client_addr IS 'IP address of client that issued query. Null for unix domain socket.';
COMMENT ON COLUMN event.client_port IS 'Remote peer IP port address of client that issued query. Undefined for unix socket.';
COMMENT ON COLUMN event.application_name IS 'Application name set when this audit event occurred. Can be changed in-session by client.';
COMMENT ON COLUMN event.client_query IS 'Top-level query that caused this auditable event. May be more than one statement.';
COMMENT ON COLUMN event.registration IS 'Transaction start timestamp';


SET search_path = content, pg_catalog;


CREATE TABLE content_message (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    type_id integer NOT NULL,
    data_id integer NOT NULL,
    message_id integer NOT NULL,
	user_id integer NOT NULL,
	registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE content_pin (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    type_id integer NOT NULL,
    data_id integer NOT NULL,
    pin_id integer NOT NULL,
    user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE content_tag (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	type_id integer NOT NULL,
	data_id integer NOT NULL,
	tag_id text NOT NULL,
	user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);

CREATE TABLE field (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    field_id text NOT NULL,
    type_id integer NOT NULL,
	registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE tag (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	tag_id text NOT NULL,
	user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE type (
	type_id serial,
	table_schema text NOT NULL,
	table_name text NOT NULL,
    application_id integer,
	url text,
	searchable boolean NOT NULL DEFAULT false,
	is_content boolean NOT NULL DEFAULT false,
	function_name_to_notify_groups_about_an_event text,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE url (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    url_id SERIAL,
    url text NOT NULL,
    label text NOT NULL,
	user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


SET search_path = discussion, pg_catalog;


CREATE TABLE discussion (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    discussion_id SERIAL,
    label text NOT NULL,
	user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE discussion_group (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    discussion_id integer NOT NULL,
    group_id integer NOT NULL,
    can_edit boolean NOT NULL,
	user_id integer NOT NULL,
	start_valid timestamp with time zone NOT NULL DEFAULT now(),
	registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE message (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    message_id SERIAL,
    discussion_id integer NOT NULL,
    parent_id integer,
    label text,
	user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE vote_message (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    message_id integer NOT NULL,
    vote integer NOT NULL,
	user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


SET search_path = file, pg_catalog;


CREATE TABLE corrected_file (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	corrected_file_id integer NOT NULL,
	file_id integer NOT NULL,
    correction_completed boolean NOT NULL DEFAULT false,
  	user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE file (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    file_id SERIAL,
    label text NOT NULL,
	user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE file_group (
    log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	file_id integer NOT NULL,
    group_id integer NOT NULL,
    can_edit boolean NOT NULL DEFAULT false,
	user_id integer NOT NULL,
	start_valid timestamp with time zone NOT NULL DEFAULT now(),
	registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE folder (
    log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	folder_id SERIAL,
	parent_id integer,
	label text NOT NULL,
	user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE folder_file (
    log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	folder_id integer NOT NULL,
	file_id integer NOT NULL,
    user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE folder_group (
    log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	folder_id integer NOT NULL,
	group_id integer NOT NULL,
	can_edit boolean NOT NULL DEFAULT false,
	user_id integer NOT NULL,
	start_valid timestamp with time zone NOT NULL DEFAULT now(),
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE turn_in_folder (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    folder_id integer NOT NULL,
	opening_time timestamp with time zone NOT NULL,
    closing_time timestamp with time zone NOT NULL,
	user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE version (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	version_id SERIAL,
	file_id integer NOT NULL,
	description text,
	path text,
	user_id integer NOT NULL,
	registration timestamp with time zone NOT NULL DEFAULT now()
);


SET search_path = notification, pg_catalog;


CREATE TABLE device (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    device_id SERIAL,
	user_id integer NOT NULL,
	registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE device_application (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    device_id integer NOT NULL,
	application_id integer NOT NULL,
	user_id integer NOT NULL,
	registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE device_user_notification (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	device_id integer NOT NULL,
	user_id integer NOT NULL,
	event_id integer NOT NULL,
	sent boolean NOT NULL DEFAULT false,
	registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE email (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	device_id integer NOT NULL,
	email_address text UNIQUE NOT NULL,
	user_id integer NOT NULL,
	registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE phone (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	device_id integer NOT NULL,
    phone_number text UNIQUE NOT NULL,
	phone_service_provider_id integer NOT NULL,
	user_id integer NOT NULL,
	registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE phone_service_provider (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	phone_service_provider_id SERIAL,
	email_address_domain_part text UNIQUE NOT NULL,
	user_id integer NOT NULL,
	registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE user_notification (
    user_id integer NOT NULL,
    event_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE web_gui (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	device_id integer NOT NULL,
	seen boolean NOT NULL DEFAULT false,
	user_id integer UNIQUE NOT NULL,
	registration timestamp with time zone NOT NULL DEFAULT now()
);


SET search_path = pinboard, pg_catalog;


CREATE TABLE pin (
    log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	pin_id SERIAL,
    pinboard_id integer NOT NULL,
    label text NOT NULL,
	user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE pin_connection (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    from_pin_id integer NOT NULL,
    to_pin_id integer NOT NULL,
    pinboard_id integer NOT NULL,
    user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE pin_geometry (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    pin_id integer NOT NULL,
    pinboard_id integer NOT NULL,
    user_id integer NOT NULL,
    visible boolean NOT NULL DEFAULT true,
    x_dimension integer NOT NULL,
    y_dimension integer NOT NULL,
    x_position integer NOT NULL,
    y_position integer NOT NULL,
	registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE pinboard (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    pinboard_id SERIAL,
    label text NOT NULL,
	user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE pinboard_group (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    pinboard_id integer NOT NULL,
	group_id integer NOT NULL,
    can_edit boolean NOT NULL DEFAULT false,
	user_id integer NOT NULL,
	start_valid timestamp with time zone NOT NULL DEFAULT now(),
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE pinboard_pinboard (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    pinboard_id integer NOT NULL,
	parent_id integer NOT NULL,
	user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE vote_pin (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    pin_id integer NOT NULL,
    vote integer NOT NULL,
	user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


SET search_path = public, pg_catalog;


CREATE TABLE administrator_group (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    group_id integer NOT NULL,
    administrator_id integer NOT NULL,
    user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE application (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    application_id SERIAL,
    url text NOT NULL,
    label text NOT NULL,
    description text,
    icon text,
	user_id integer NOT NULL,
	registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE application_privilege (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    privilege_id integer NOT NULL,
    application_id integer NOT NULL,
    description text,
	user_id integer NOT NULL,
    registration timestamp without time zone DEFAULT now()
);


CREATE TABLE application_privilege_group (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    application_id integer NOT NULL,
	privilege_id integer NOT NULL,
    group_id integer NOT NULL,
    user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);

CREATE TABLE educationnal_pathway (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	ep_id serial NOT NULL,
	label text NOT NULL,
    user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE employee (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    user_id integer NOT NULL,
    employee_id text NOT NULL UNIQUE,
    phone_number text,
    office text,
    occupation text,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE groups (
    log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	group_id SERIAL,
    label text NOT NULL,
    description text,
	unique_label boolean DEFAULT false NOT NULL,
	user_id integer NOT NULL,
    last_modification timestamp with time zone,
	registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE group_group (
    log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	group_id integer NOT NULL,
    parent_id integer NOT NULL,
    user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE parameter (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	application_id integer NOT NULL,
	user_id integer NOT NULL,
    parameter_key text NOT NULL,
    parameter_value text NOT NULL,
	registration timestamp without time zone DEFAULT now()
);


CREATE TABLE privilege (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	privilege_id SERIAL,
    label text NOT NULL,
    description text,
	user_id integer NOT NULL,
	registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE profile_picture (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	user_id integer NOT NULL,
    user_agreement boolean NOT NULL DEFAULT false,
	administrator_user_id integer,
    administrator_approval boolean,
    image bytea,
    icon bytea,
	registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE student (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	user_id integer NOT NULL,
    student_id text NOT NULL UNIQUE,
    ep_id integer NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);


CREATE TABLE users (
	log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
    user_id SERIAL,
	administrative_user_id text,
    last_name text NOT NULL,
    first_name text NOT NULL,
	gender character(1),
    email_address text NOT NULL,
    last_login timestamp with time zone,
    registration timestamp with time zone NOT NULL DEFAULT now(),
	valid_end timestamp with time zone
);


CREATE TABLE user_group (
    log_data_id integer NOT NULL UNIQUE DEFAULT nextval('public.log_data_id_seq'::regclass),
	member_id integer NOT NULL,
    group_id integer NOT NULL,
	user_id integer NOT NULL,
    registration timestamp with time zone NOT NULL DEFAULT now()
);

SET search_path = audit, pg_catalog;


CREATE UNIQUE INDEX event_pk ON event USING btree (event_id);


SET search_path = content, pg_catalog;


CREATE INDEX content_message_fk ON content_message USING btree (type_id);

CREATE INDEX content_message_fk2 ON content_message USING btree (message_id);

CREATE INDEX content_message_fk3 ON content_message USING btree (user_id);

CREATE UNIQUE INDEX content_message_pk ON content_message USING btree (message_id);

CREATE INDEX content_pin_fk ON content_pin USING btree (type_id);

CREATE INDEX content_pin_fk2 ON content_pin USING btree (pin_id);

CREATE INDEX content_pin_fk3 ON content_pin USING btree (user_id);

CREATE UNIQUE INDEX content_pin_pk ON content_pin USING btree (pin_id);

CREATE INDEX content_tag_fk ON content_tag USING btree (type_id);

CREATE INDEX content_tag_fk2 ON content_tag USING btree (tag_id);

CREATE INDEX content_tag_fk3 ON content_tag USING btree (user_id);

CREATE UNIQUE INDEX content_tag_pk ON content_tag USING btree (tag_id, type_id, data_id);

CREATE INDEX fields_fk ON field USING btree (type_id);

CREATE UNIQUE INDEX field_pk ON field USING btree (field_id, type_id);

CREATE INDEX tag_fk ON tag USING btree (user_id);

CREATE UNIQUE INDEX tag_pk ON tag USING btree (tag_id);

CREATE INDEX type_fk ON type USING btree (application_id);

CREATE UNIQUE INDEX type_pk ON type USING btree (type_id);

CREATE UNIQUE INDEX type_candidate_key ON type USING btree (table_schema, table_name);

CREATE INDEX url_fk ON url USING btree (user_id);

CREATE UNIQUE INDEX url_pk ON url USING btree (url_id);


SET search_path = discussion, pg_catalog;


CREATE INDEX discussion_fk ON discussion USING btree (user_id);

CREATE UNIQUE INDEX discussion_pk ON discussion USING btree (discussion_id);

CREATE INDEX discussion_group_fk ON discussion_group USING btree (discussion_id);

CREATE INDEX discussion_group_fk2 ON discussion_group USING btree (group_id);

CREATE INDEX discussion_group_fk3 ON discussion_group USING btree (user_id);

CREATE UNIQUE INDEX discussion_group_pk ON discussion_group USING btree (discussion_id, group_id);

CREATE INDEX message_fk ON message USING btree (discussion_id);

CREATE INDEX message_fk2 ON message USING btree (parent_id);

CREATE INDEX message_fk3 ON message USING btree (user_id);

CREATE UNIQUE INDEX message_pk ON message USING btree (message_id);

CREATE INDEX vote_message_fk ON vote_message USING btree (message_id);

CREATE INDEX vote_message_fk2 ON vote_message USING btree (user_id);

CREATE UNIQUE INDEX vote_message_pk ON vote_message USING btree (message_id, user_id);


SET search_path = file, pg_catalog;


CREATE INDEX corrected_file_fk ON corrected_file USING btree (corrected_file_id);

CREATE INDEX corrected_file_fk2 ON corrected_file USING btree (file_id);

CREATE INDEX corrected_file_fk3 ON corrected_file USING btree (user_id);

CREATE UNIQUE INDEX corrected_file_pk ON corrected_file USING btree (corrected_file_id);

CREATE INDEX file_fk ON corrected_file USING btree (user_id);

CREATE UNIQUE INDEX file_pk ON file USING btree (file_id);

CREATE INDEX file_group_fk ON file_group USING btree (file_id);

CREATE INDEX file_group_fk2 ON file_group USING btree (group_id);

CREATE INDEX file_group_fk3 ON file_group USING btree (user_id);

CREATE UNIQUE INDEX file_group_pk ON file_group USING btree (file_id, group_id);

CREATE INDEX folder_fk ON folder USING btree (parent_id);

CREATE INDEX folder_fk2 ON folder USING btree (user_id);

CREATE UNIQUE INDEX folder_pk ON folder USING btree (folder_id);

CREATE INDEX folder_file_fk ON folder_file USING btree (folder_id);

CREATE INDEX folder_file_fk2 ON folder_file USING btree (file_id);

CREATE INDEX folder_file_fk3 ON folder_file USING btree (user_id);

CREATE UNIQUE INDEX folder_file_pk ON folder_file USING btree (folder_id, file_id);

CREATE INDEX folder_group_fk ON folder_group USING btree (folder_id);

CREATE INDEX folder_group_fk2 ON folder_group USING btree (group_id);

CREATE INDEX folder_group_fk3 ON folder_group USING btree (user_id);

CREATE UNIQUE INDEX folder_group_pk ON folder_group USING btree (folder_id, group_id);

CREATE INDEX turn_in_folder_fk ON turn_in_folder USING btree (folder_id);

CREATE INDEX turn_in_folder_fk2 ON turn_in_folder USING btree (user_id);

CREATE UNIQUE INDEX turn_in_folder_pk ON turn_in_folder USING btree (folder_id);

CREATE INDEX version_fk ON version USING btree (file_id);

CREATE INDEX version_fk2 ON version USING btree (user_id);

CREATE UNIQUE INDEX version_pk ON version USING btree (version_id);
	

SET search_path = notification, pg_catalog;


CREATE UNIQUE INDEX device_pk ON device USING btree (device_id);

CREATE INDEX device_fk ON device USING btree (user_id);

CREATE UNIQUE INDEX device_application_pk ON device_application USING btree (device_id, application_id);

CREATE INDEX device_application_fk ON device_application USING btree (device_id);

CREATE INDEX device_application_fk2 ON device_application USING btree (application_id);

CREATE INDEX device_application_fk3 ON device_application USING btree (user_id);

CREATE UNIQUE INDEX device_user_notification_pk ON device_user_notification USING btree (device_id, event_id);

CREATE INDEX device_user_notification_fk ON device_user_notification USING btree (device_id);

CREATE INDEX device_user_notification_fk3 ON device_user_notification USING btree (user_id);

CREATE INDEX device_user_notification_fk2 ON device_user_notification USING btree (event_id);

CREATE UNIQUE INDEX email_pk ON email USING btree (device_id);

CREATE INDEX email_fk ON email USING btree (user_id);

CREATE UNIQUE INDEX phone_pk ON phone USING btree (device_id);

CREATE INDEX phone_fk ON phone USING btree (user_id);

CREATE INDEX phone_fk2 ON phone USING btree (phone_service_provider_id);

CREATE UNIQUE INDEX phone_service_provider_pk ON phone_service_provider USING btree (phone_service_provider_id);

CREATE INDEX phone_service_provider_fk ON phone_service_provider USING btree (user_id);

CREATE UNIQUE INDEX user_notification_pk ON user_notification USING btree (user_id, event_id);

CREATE INDEX user_notification_fk ON user_notification USING btree (user_id);

CREATE INDEX user_notification_fk2 ON user_notification USING btree (event_id);

CREATE UNIQUE INDEX web_gui_pk ON web_gui USING btree (device_id);

CREATE INDEX web_gui_fk ON web_gui USING btree (user_id);


SET search_path = pinboard, pg_catalog;


CREATE INDEX pin_fk ON pin USING btree (pinboard_id);

CREATE INDEX pin_fk2 ON pin USING btree (user_id);

CREATE UNIQUE INDEX pin_pk ON pin USING btree (pin_id);

CREATE INDEX pin_connection_fk2 ON pin_connection USING btree (pinboard_id);

CREATE INDEX pin_connection_fk3 ON pin_connection USING btree (user_id);

CREATE UNIQUE INDEX pin_connection_pk ON pin_connection USING btree (from_pin_id, to_pin_id, pinboard_id);

CREATE INDEX pin_geometry_fk ON pin_geometry USING btree (pin_id);

CREATE INDEX pin_geometry_fk2 ON pin_geometry USING btree (pinboard_id);

CREATE INDEX pin_geometry_fk3 ON pin_geometry USING btree (user_id);

CREATE UNIQUE INDEX pin_geometry_pk ON pin_geometry USING btree (pin_id, pinboard_id);

CREATE INDEX pinboard_fk ON pinboard USING btree (user_id);

CREATE UNIQUE INDEX pinboard_pk ON pinboard USING btree (pinboard_id);

CREATE INDEX pinboard_group_fk ON pinboard_group USING btree (group_id);

CREATE INDEX pinboard_group_fk2 ON pinboard_group USING btree (user_id);

CREATE INDEX pinboard_group_fk3 ON pinboard_group USING btree (pinboard_id);

CREATE UNIQUE INDEX pinboard_group_pk ON pinboard_group USING btree (group_id, pinboard_id);

CREATE INDEX pinboard_pinboard_fk ON pinboard_pinboard USING btree (pinboard_id);

CREATE INDEX pinboard_pinboard_fk2 ON pinboard_pinboard USING btree (parent_id);

CREATE INDEX pinboard_pinboard_fk3 ON pinboard_pinboard USING btree (user_id);

CREATE UNIQUE INDEX pinboard_pinboard_pk ON pinboard_pinboard USING btree (pinboard_id, parent_id);

CREATE INDEX vote_pin_fk ON vote_pin USING btree (user_id);

CREATE INDEX vote_pin_fk2 ON vote_pin USING btree (pin_id);

CREATE UNIQUE INDEX vote_pin_pk ON vote_pin USING btree (user_id, pin_id);


SET search_path = public, pg_catalog;


CREATE INDEX administrator_group_fk ON administrator_group USING btree (administrator_id);

CREATE INDEX administrator_group_fk2 ON administrator_group USING btree (group_id);

CREATE INDEX administrator_group_fk3 ON administrator_group USING btree (user_id);

CREATE UNIQUE INDEX administrator_group_pk ON administrator_group USING btree (group_id, administrator_id);

CREATE INDEX application_fk ON application USING btree (user_id);

CREATE UNIQUE INDEX application_pk ON application USING btree (application_id);

CREATE INDEX application_privilege_fk ON application_privilege USING btree (user_id);

CREATE INDEX application_privilege_fk2 ON application_privilege USING btree (application_id);

CREATE INDEX application_privilege_fk3 ON application_privilege USING btree (privilege_id);

CREATE UNIQUE INDEX application_privilege_pk ON application_privilege USING btree (application_id, privilege_id);

CREATE INDEX employee_fk ON employee USING btree (user_id);

CREATE UNIQUE INDEX employee_pk ON employee USING btree (user_id);

CREATE INDEX group_group_fk ON group_group USING btree (parent_id);

CREATE INDEX group_group_fk2 ON group_group USING btree (group_id);

CREATE INDEX group_group_fk3 ON group_group USING btree (user_id);

CREATE UNIQUE INDEX group_group_pk ON group_group USING btree (group_id, parent_id);

CREATE UNIQUE INDEX unique_label_idx ON groups USING btree (label) WHERE unique_label;

CREATE INDEX groups_fk ON groups USING btree (user_id);

CREATE UNIQUE INDEX groups_pk ON groups USING btree (group_id);

CREATE INDEX parameter_fk ON parameter USING btree (application_id);

CREATE INDEX parameter_fk2 ON parameter USING btree (user_id);

CREATE UNIQUE INDEX parameter_pk ON parameter USING btree (application_id, parameter_key);

CREATE INDEX privilege_fk ON privilege USING btree (user_id);

CREATE UNIQUE INDEX privilege_pk ON privilege USING btree (privilege_id);

CREATE INDEX application_privilege_group_fk ON application_privilege_group USING btree (privilege_id);

CREATE INDEX application_privilege_group_fk2 ON application_privilege_group USING btree (application_id);

CREATE INDEX application_privilege_group_fk3 ON application_privilege_group USING btree (group_id);

CREATE INDEX application_privilege_group_fk4 ON application_privilege_group USING btree (user_id);

CREATE INDEX student_fk ON student USING btree (user_id);

CREATE UNIQUE INDEX student_pk ON student USING btree (user_id);

CREATE INDEX profile_picture_fk ON profile_picture USING btree (user_id);

CREATE UNIQUE INDEX profile_picture_pk ON profile_picture USING btree (user_id);

CREATE INDEX user_group_fk ON user_group USING btree (member_id);

CREATE INDEX user_group_fk2 ON user_group USING btree (group_id);

CREATE INDEX user_group_fk3 ON user_group USING btree (user_id);

CREATE UNIQUE INDEX user_group_pk ON user_group USING btree (member_id, group_id);

CREATE UNIQUE INDEX users_pk ON users USING btree (user_id);

-- ********************************* PRIMARY KEYS ********************************* --

SET search_path = audit, pg_catalog;


ALTER TABLE ONLY event
    ADD CONSTRAINT pk_event PRIMARY KEY (event_id);


SET search_path = content, pg_catalog;


ALTER TABLE ONLY content_message
    ADD CONSTRAINT pk_content_message PRIMARY KEY (message_id);

ALTER TABLE ONLY content_pin
    ADD CONSTRAINT pk_content_pin PRIMARY KEY (pin_id);
	
ALTER TABLE ONLY content_tag
    ADD CONSTRAINT pk_content_tag PRIMARY KEY (tag_id, type_id, data_id);

ALTER TABLE ONLY field
    ADD CONSTRAINT pk_field PRIMARY KEY (field_id, type_id);

ALTER TABLE ONLY tag
    ADD CONSTRAINT pk_tag PRIMARY KEY (tag_id);	
	
ALTER TABLE ONLY type
    ADD CONSTRAINT pk_type PRIMARY KEY (type_id);

ALTER TABLE ONLY url
    ADD CONSTRAINT pk_url PRIMARY KEY (url_id);


SET search_path = discussion, pg_catalog;


ALTER TABLE ONLY discussion
    ADD CONSTRAINT pk_discussion PRIMARY KEY (discussion_id);

ALTER TABLE ONLY discussion_group
    ADD CONSTRAINT pk_discussion_group PRIMARY KEY (discussion_id, group_id);

ALTER TABLE ONLY message
    ADD CONSTRAINT pk_messages PRIMARY KEY (message_id);

ALTER TABLE ONLY vote_message
    ADD CONSTRAINT pk_vote_message PRIMARY KEY (message_id, user_id);

	
SET search_path = file, pg_catalog;


ALTER TABLE ONLY corrected_file
    ADD CONSTRAINT pk_corrected_file PRIMARY KEY (corrected_file_id);

ALTER TABLE ONLY file
    ADD CONSTRAINT pk_file PRIMARY KEY (file_id);
	
ALTER TABLE ONLY folder
    ADD CONSTRAINT pk_folder PRIMARY KEY (folder_id);

ALTER TABLE ONLY folder_group
    ADD CONSTRAINT pk_folder_group PRIMARY KEY (folder_id, group_id);

ALTER TABLE ONLY folder_file
    ADD CONSTRAINT pk_folder_file PRIMARY KEY (folder_id, file_id);

ALTER TABLE ONLY file_group
    ADD CONSTRAINT pk_file_group PRIMARY KEY (file_id, group_id);

ALTER TABLE ONLY version
    ADD CONSTRAINT pk_version PRIMARY KEY (version_id);

ALTER TABLE ONLY turn_in_folder
    ADD CONSTRAINT pk_turn_in_folder PRIMARY KEY (folder_id);

  
SET search_path = notification, pg_catalog;


ALTER TABLE ONLY device
    ADD CONSTRAINT pk_device PRIMARY KEY (device_id);
	
ALTER TABLE ONLY device_application
    ADD CONSTRAINT pk_device_application PRIMARY KEY (device_id, application_id);

ALTER TABLE ONLY device_user_notification
    ADD CONSTRAINT pk_device_user_notification PRIMARY KEY (device_id, event_id);	

ALTER TABLE ONLY email
    ADD CONSTRAINT pk_email PRIMARY KEY (device_id);	

ALTER TABLE ONLY phone
    ADD CONSTRAINT pk_phone PRIMARY KEY (device_id);
	
ALTER TABLE ONLY phone_service_provider
    ADD CONSTRAINT pk_phone_service_provider PRIMARY KEY (phone_service_provider_id);	

ALTER TABLE ONLY user_notification
    ADD CONSTRAINT pk_user_notification PRIMARY KEY (user_id, event_id);
	
ALTER TABLE ONLY web_gui
    ADD CONSTRAINT pk_web_gui PRIMARY KEY (device_id);


SET search_path = pinboard, pg_catalog;


ALTER TABLE ONLY pin
    ADD CONSTRAINT pk_pin PRIMARY KEY (pin_id);

ALTER TABLE ONLY pin_connection
    ADD CONSTRAINT pk_pin_connection PRIMARY KEY (from_pin_id, to_pin_id, pinboard_id);

ALTER TABLE ONLY pin_geometry
    ADD CONSTRAINT pk_pin_geometry PRIMARY KEY (pin_id, pinboard_id);

ALTER TABLE ONLY pinboard
    ADD CONSTRAINT pk_pinboard PRIMARY KEY (pinboard_id);
	
ALTER TABLE ONLY pinboard_pinboard
    ADD CONSTRAINT pk_pinboard_pinboard PRIMARY KEY (pinboard_id, parent_id);

ALTER TABLE ONLY pinboard_group
    ADD CONSTRAINT pk_pinboard_group PRIMARY KEY (pinboard_id, group_id);

ALTER TABLE ONLY vote_pin
    ADD CONSTRAINT pk_vote_pin PRIMARY KEY (user_id, pin_id);


SET search_path = public, pg_catalog;


ALTER TABLE ONLY application
    ADD CONSTRAINT pk_application PRIMARY KEY (application_id);
	
ALTER TABLE ONLY application_privilege
    ADD CONSTRAINT pk_application_privilege PRIMARY KEY (privilege_id, application_id);
	
ALTER TABLE ONLY application_privilege_group
    ADD CONSTRAINT pk_privilege_group PRIMARY KEY (application_id, privilege_id, group_id);
	
ALTER TABLE ONLY administrator_group
    ADD CONSTRAINT pk_administrator_group PRIMARY KEY (group_id, administrator_id);

ALTER TABLE ONLY educationnal_pathway
    ADD CONSTRAINT pk_educationnal_pathway PRIMARY KEY (ep_id);

ALTER TABLE ONLY employee
    ADD CONSTRAINT pk_employee PRIMARY KEY (user_id);
	
ALTER TABLE ONLY groups
    ADD CONSTRAINT pk_group PRIMARY KEY (group_id);
	
ALTER TABLE ONLY group_group
    ADD CONSTRAINT pk_group_group PRIMARY KEY (group_id, parent_id);

ALTER TABLE ONLY parameter
    ADD CONSTRAINT pk_parameter PRIMARY KEY (application_id, parameter_key);
	
ALTER TABLE ONLY privilege
    ADD CONSTRAINT pk_privilege PRIMARY KEY (privilege_id);

ALTER TABLE ONLY profile_picture
    ADD CONSTRAINT pk_profile_picture PRIMARY KEY (user_id);

ALTER TABLE ONLY student
    ADD CONSTRAINT pk_student PRIMARY KEY (user_id);
	
ALTER TABLE ONLY users
    ADD CONSTRAINT pk_users PRIMARY KEY (user_id);
	
ALTER TABLE ONLY user_group
    ADD CONSTRAINT pk_user_group PRIMARY KEY (member_id, group_id);

	
-- ********************************* FOREIGN KEYS ********************************* --


SET search_path = content, pg_catalog;	


ALTER TABLE ONLY content_message
    ADD CONSTRAINT fk_content_m_has_type FOREIGN KEY (type_id) REFERENCES type(type_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY content_message
    ADD CONSTRAINT fk_content_m_links_message FOREIGN KEY (message_id) REFERENCES discussion.message(message_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY content_message
    ADD CONSTRAINT fk_content_m_added_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY content_pin
    ADD CONSTRAINT fk_content_p_has_type FOREIGN KEY (type_id) REFERENCES type(type_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY content_pin
    ADD CONSTRAINT fk_content_p_links_pin FOREIGN KEY (pin_id) REFERENCES pinboard.pin(pin_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY content_pin
    ADD CONSTRAINT fk_content_p_added_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;
	
ALTER TABLE ONLY content_tag
    ADD CONSTRAINT fk_content_t_has_type FOREIGN KEY (type_id) REFERENCES type(type_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY content_tag
    ADD CONSTRAINT fk_content_t_links_tag FOREIGN KEY (tag_id) REFERENCES tag(tag_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY content_tag
    ADD CONSTRAINT fk_content_t_added_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY field
    ADD CONSTRAINT fk_field_from_type FOREIGN KEY (type_id) REFERENCES type(type_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY tag
    ADD CONSTRAINT fk_tag_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY type
    ADD CONSTRAINT fk_type_used_by_application FOREIGN KEY (application_id) REFERENCES public.application ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY url
    ADD CONSTRAINT fk_url_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;
	

SET search_path = discussion, pg_catalog;


ALTER TABLE ONLY discussion
    ADD CONSTRAINT fk_discussion_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY discussion_group
    ADD CONSTRAINT fk_discussion_g_links_discussion FOREIGN KEY (discussion_id) REFERENCES discussion(discussion_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY discussion_group
    ADD CONSTRAINT fk_discussion_g_links_group FOREIGN KEY (group_id) REFERENCES public.groups(group_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY discussion_group
    ADD CONSTRAINT fk_discussion_g_added_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY message
    ADD CONSTRAINT fk_message_contained_in_discussion FOREIGN KEY (discussion_id) REFERENCES discussion(discussion_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY message
    ADD CONSTRAINT fk_message_has_parent_message FOREIGN KEY (parent_id) REFERENCES message(message_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY message
    ADD CONSTRAINT fk_message_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY vote_message
    ADD CONSTRAINT fk_vote_m_links_message FOREIGN KEY (message_id) REFERENCES message(message_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY vote_message
    ADD CONSTRAINT fk_vote_m_voted_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


SET search_path = file, pg_catalog;


ALTER TABLE ONLY corrected_file
    ADD CONSTRAINT fk_corrected_f_links_corrected_file FOREIGN KEY (corrected_file_id) REFERENCES file(file_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY corrected_file
    ADD CONSTRAINT fk_corrected_f_links_file FOREIGN KEY (file_id) REFERENCES file(file_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY corrected_file
    ADD CONSTRAINT fk_corrected_f_added_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY file
    ADD CONSTRAINT fk_file_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY file_group
    ADD CONSTRAINT fk_file_g_links_file FOREIGN KEY (file_id) REFERENCES file(file_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY file_group
    ADD CONSTRAINT fk_file_g_links_group FOREIGN KEY (group_id) REFERENCES public.groups(group_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY file_group
    ADD CONSTRAINT fk_file_g_added_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY folder
    ADD CONSTRAINT fk_folder_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY folder
    ADD CONSTRAINT fk_folder_has_parent FOREIGN KEY (parent_id) REFERENCES folder(folder_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY folder_file
    ADD CONSTRAINT fk_folder_f_links_folder FOREIGN KEY (folder_id) REFERENCES folder(folder_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY folder_file
    ADD CONSTRAINT fk_folder_f_links_file FOREIGN KEY (file_id) REFERENCES file(file_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY folder_file
    ADD CONSTRAINT fk_folder_f_added_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY folder_group
    ADD CONSTRAINT fk_folder_g_links_folder FOREIGN KEY (folder_id) REFERENCES folder(folder_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY folder_group
    ADD CONSTRAINT fk_folder_g_links_group FOREIGN KEY (group_id) REFERENCES public.groups(group_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY folder_group
    ADD CONSTRAINT fk_folder_g_added_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY turn_in_folder
    ADD CONSTRAINT fk_turn_i_f_links_folder FOREIGN KEY (folder_id) REFERENCES folder(folder_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY turn_in_folder
    ADD CONSTRAINT fk_turn_i_f_added_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY version
    ADD CONSTRAINT fk_version_is_a_revision_of_file FOREIGN KEY (file_id) REFERENCES file(file_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY version
    ADD CONSTRAINT fk_version_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;	
	
	
SET search_path = notification, pg_catalog;

ALTER TABLE ONLY device
    ADD CONSTRAINT fk_device_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY device_application
    ADD CONSTRAINT fk_device_a_links_device FOREIGN KEY (device_id) REFERENCES device(device_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY device_application
    ADD CONSTRAINT fk_device_a_links_application FOREIGN KEY (application_id) REFERENCES public.application(application_id) ON UPDATE CASCADE ON DELETE CASCADE;
	
ALTER TABLE ONLY device_application
    ADD CONSTRAINT fk_device_a_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY device_user_notification
    ADD CONSTRAINT fk_device_u_n_links_device FOREIGN KEY (device_id) REFERENCES device(device_id) ON UPDATE CASCADE ON DELETE CASCADE;
	
ALTER TABLE ONLY device_user_notification
    ADD CONSTRAINT fk_device_u_n_links_event FOREIGN KEY (user_id, event_id) REFERENCES user_notification(user_id, event_id) ON UPDATE CASCADE ON DELETE CASCADE;	
	
ALTER TABLE ONLY email
    ADD CONSTRAINT fk_email_links_device FOREIGN KEY (device_id) REFERENCES device(device_id) ON UPDATE CASCADE ON DELETE CASCADE;
	
ALTER TABLE ONLY email
    ADD CONSTRAINT fk_email_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY phone
    ADD CONSTRAINT fk_phone_links_device FOREIGN KEY (device_id) REFERENCES device(device_id) ON UPDATE CASCADE ON DELETE CASCADE;
	
ALTER TABLE ONLY phone
    ADD CONSTRAINT fk_phone_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY phone
    ADD CONSTRAINT fk_phone_links_phone_service_provider FOREIGN KEY (phone_service_provider_id) REFERENCES phone_service_provider(phone_service_provider_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY phone_service_provider
    ADD CONSTRAINT fk_phone_s_p_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY user_notification
    ADD CONSTRAINT fk_user_n_links_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY user_notification
    ADD CONSTRAINT fk_user_n_links_event FOREIGN KEY (event_id) REFERENCES audit.event(event_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY web_gui
    ADD CONSTRAINT fk_web_g_links_device FOREIGN KEY (device_id) REFERENCES device(device_id) ON UPDATE CASCADE ON DELETE CASCADE;
	
ALTER TABLE ONLY web_gui
    ADD CONSTRAINT fk_web_g_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;

	
SET search_path = pinboard, pg_catalog;


ALTER TABLE ONLY pin
    ADD CONSTRAINT fk_pin_contained_in_pinboard FOREIGN KEY (pinboard_id) REFERENCES pinboard(pinboard_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY pin
    ADD CONSTRAINT fk_pin_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY pin_connection
    ADD CONSTRAINT fk_pin_c_links_from_pin FOREIGN KEY (from_pin_id) REFERENCES pin(pin_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY pin_connection
    ADD CONSTRAINT fk_pin_c_links_to_pin FOREIGN KEY (to_pin_id) REFERENCES pin(pin_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY pin_connection
    ADD CONSTRAINT fk_pin_c_inside_a_pinboard FOREIGN KEY (pinboard_id) REFERENCES pinboard(pinboard_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY pin_connection
    ADD CONSTRAINT fk_pin_c_added_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY pin_geometry
    ADD CONSTRAINT fk_pin_g_links_pin FOREIGN KEY (pin_id) REFERENCES pin(pin_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY pin_geometry
    ADD CONSTRAINT fk_pin_g_inside_a_pinboard FOREIGN KEY (pinboard_id) REFERENCES pinboard(pinboard_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY pin_geometry
    ADD CONSTRAINT fk_pin_g_added_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY pinboard
    ADD CONSTRAINT fk_pinboard_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY pinboard_group
    ADD CONSTRAINT fk_pinboard_g_links_group FOREIGN KEY (group_id) REFERENCES public.groups(group_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY pinboard_group
    ADD CONSTRAINT fk_pinboard_g_links_pinboard FOREIGN KEY (pinboard_id) REFERENCES pinboard(pinboard_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY pinboard_group
    ADD CONSTRAINT fk_pinboard_g_added_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;	
	
ALTER TABLE ONLY pinboard_pinboard
    ADD CONSTRAINT fk_pinboard_p_links_child FOREIGN KEY (pinboard_id) REFERENCES pinboard(pinboard_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY pinboard_pinboard
    ADD CONSTRAINT fk_pinboard_p_links_parent FOREIGN KEY (parent_id) REFERENCES pinboard(pinboard_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY pinboard_pinboard
    ADD CONSTRAINT fk_pinboard_p_added_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY vote_pin
    ADD CONSTRAINT fk_vote_p_links_pin FOREIGN KEY (pin_id) REFERENCES pin(pin_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY vote_pin
    ADD CONSTRAINT fk_vote_p_links_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


SET search_path = public, pg_catalog;


ALTER TABLE ONLY administrator_group
    ADD CONSTRAINT fk_group_a_g_links_administrated_group FOREIGN KEY (group_id) REFERENCES groups(group_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY administrator_group
    ADD CONSTRAINT fk_group_a_g_links_administrator_group FOREIGN KEY (administrator_id) REFERENCES groups(group_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY administrator_group
    ADD CONSTRAINT fk_group_a_g_links_added_by_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY application
    ADD CONSTRAINT fk_application_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;	

ALTER TABLE ONLY application_privilege
    ADD CONSTRAINT fk_application_p_links_application FOREIGN KEY (application_id) REFERENCES application(application_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY application_privilege
    ADD CONSTRAINT fk_application_p_links_privilege FOREIGN KEY (privilege_id) REFERENCES privilege(privilege_id) ON UPDATE CASCADE ON DELETE CASCADE;	

ALTER TABLE ONLY application_privilege
    ADD CONSTRAINT fk_application_p_added_by_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;	
	
ALTER TABLE ONLY application_privilege_group
    ADD CONSTRAINT fk_application_p_g_links_application_privilege FOREIGN KEY (application_id, privilege_id) REFERENCES application_privilege(application_id, privilege_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY application_privilege_group
    ADD CONSTRAINT fk_application_p_g_links_group FOREIGN KEY (group_id) REFERENCES groups(group_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY application_privilege_group
    ADD CONSTRAINT fk_application_p_g_links_added_by_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY employee
    ADD CONSTRAINT fk_employee_is_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY groups
    ADD CONSTRAINT fk_groups_created_by_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY group_group
    ADD CONSTRAINT fk_group_g_links_child FOREIGN KEY (group_id) REFERENCES groups(group_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY group_group
    ADD CONSTRAINT fk_group_g_links_parent FOREIGN KEY (parent_id) REFERENCES groups(group_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY group_group
    ADD CONSTRAINT fk_group_g_added_by_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY parameter
    ADD CONSTRAINT fk_parameter_owned_by_application FOREIGN KEY (application_id) REFERENCES application(application_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY parameter
    ADD CONSTRAINT fk_parameter_created_by_user FOREIGN KEY (user_id) REFERENCES public.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY privilege
    ADD CONSTRAINT fk_privilege_created_by_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY profile_picture
    ADD CONSTRAINT fk_profile_p_created_by_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY profile_picture
    ADD CONSTRAINT fk_profile_p_approved_by_administrator FOREIGN KEY (administrator_user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY student
    ADD CONSTRAINT fk_student_is_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY student
    ADD CONSTRAINT fk_student_has_ed_pathway FOREIGN KEY (ep_id) REFERENCES educationnal_pathway(ep_id) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY user_group
    ADD CONSTRAINT fk_user_g_links_group FOREIGN KEY (group_id) REFERENCES groups(group_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY user_group
    ADD CONSTRAINT fk_user_g_links_member FOREIGN KEY (member_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY user_group
    ADD CONSTRAINT fk_user_g_added_by_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;

-- Voir le fichier script_views_with_dependancies_on_functions.sql pour les vues supplémentaires

SET search_path = content, pg_catalog;


CREATE VIEW v_field AS
 SELECT field.field_id,
	field.type_id
   FROM field;

   
CREATE VIEW v_tag AS
 SELECT tag.tag_id,
	tag.user_id
   FROM content.tag;


CREATE VIEW v_type AS
 SELECT type.type_id,
	type.table_schema,
	type.table_name,
	type.application_id,
	type.url,
	type.function_name_to_notify_groups_about_an_event
   FROM content.type;   


CREATE VIEW v_url AS
 SELECT url.url_id,
    url.url,
    url.label,
	url.user_id
   FROM url;
   
   
SET search_path = discussion, pg_catalog;


CREATE VIEW v_discussion AS
 SELECT discussion.discussion_id,
    discussion.label,
	discussion.user_id,
    discussion.registration
   FROM discussion;


CREATE VIEW v_discussion_group AS
 SELECT discussion_group.discussion_id,
    discussion_group.group_id,
    discussion_group.can_edit,
	discussion_group.user_id,
	discussion_group.start_valid,
	discussion_group.registration
   FROM discussion_group;


CREATE VIEW v_message AS
 WITH RECURSIVE everymessage AS (
                 SELECT message.parent_id AS node_id,
                    message.parent_id,
                    message.message_id,
                    message.user_id,
                    message.label,
                    1 AS depth,
                    ARRAY[message.message_id] AS path,
                    message.discussion_id,
					message.registration
                   FROM message
        UNION ALL
                 SELECT every.node_id,
                    submessage.parent_id,
                    submessage.message_id,
                    submessage.user_id,
                    submessage.label,
                    (every.depth + 1),
                    (every.path || submessage.message_id) AS path,
                    submessage.discussion_id,
					submessage.registration
                   FROM (message submessage
              JOIN everymessage every ON ((submessage.parent_id = every.message_id)))
        )
 SELECT a.node_id,
    a.parent_id,
    a.message_id,
    a.user_id,
    a.label,
    a.depth,
    (a.path)::text AS path,
    COALESCE(b.nb_children, (0)::bigint) AS nb_children,
    a.discussion_id,
    COALESCE(c.vote, (0)::bigint) AS vote,
	a.registration
   FROM ((everymessage a
   LEFT JOIN ( SELECT count(v.node_id) AS nb_children,
            v.node_id
           FROM everymessage v
          GROUP BY v.node_id) b ON ((a.message_id = b.node_id)))
   LEFT JOIN ( SELECT sum(m.vote) AS vote,
       m.message_id
      FROM vote_message m
     GROUP BY m.message_id) c ON ((a.message_id = c.message_id)))
  ORDER BY a.node_id;


CREATE VIEW v_vote_message AS
 SELECT vote_message.message_id,
    vote_message.user_id,
    vote_message.vote
   FROM vote_message;
   

SET search_path = file, pg_catalog;


CREATE VIEW v_corrected_file AS 
 SELECT corrected_file.corrected_file_id,
	corrected_file.file_id,
	corrected_file.correction_completed,
	corrected_file.user_id
   FROM corrected_file;

CREATE VIEW v_file AS
 SELECT file.file_id,
    file.label,
	file.user_id,
    file.registration   
   FROM file;
  
  
CREATE VIEW v_file_group AS 
 SELECT file_group.file_id,
    file_group.group_id,
    file_group.can_edit,
	file_group.user_id,
	file_group.start_valid 
   FROM file_group;


CREATE VIEW v_folder AS
 WITH RECURSIVE everyfolder AS (
                 SELECT folder.parent_id AS node_id,
                    folder.parent_id,
                    folder.folder_id,
                    folder.label,
					folder.user_id,
                    1 AS depth,
                    ARRAY[folder.parent_id] AS path
                   FROM folder
        UNION ALL
                 SELECT every.node_id,
                    subfolder.parent_id,
                    subfolder.folder_id,
                    subfolder.label,
					subfolder.user_id,
                    (every.depth + 1),
                    (every.path || subfolder.parent_id) AS path
                   FROM (folder subfolder
              JOIN everyfolder every ON ((subfolder.parent_id = every.folder_id)))
        )
 SELECT everyfolder.node_id,
    everyfolder.parent_id,
    everyfolder.folder_id,
	everyfolder.label,
    everyfolder.user_id,
    everyfolder.depth,
    everyfolder.path
   FROM everyfolder
  ORDER BY everyfolder.node_id;

  
CREATE VIEW v_folder_file AS 
 SELECT folder_file.folder_id,
	folder_file.file_id,
    folder_file.user_id
   FROM folder_file;


CREATE VIEW v_folder_group AS
 SELECT folder_group.folder_id,
	folder_group.group_id,
	folder_group.can_edit,
	folder_group.user_id,
	folder_group.start_valid  
   FROM folder_group;


CREATE VIEW v_turn_in_folder AS
 SELECT turn_in_folder.folder_id,
	turn_in_folder.opening_time,
    turn_in_folder.closing_time,
	turn_in_folder.user_id
   FROM turn_in_folder;


CREATE VIEW v_version AS
 SELECT version.version_id,
	version.file_id,
	version.description,
    version.path,
	version.user_id,
	version.registration
   FROM version;


SET search_path = notification, pg_catalog;


CREATE VIEW v_device AS
 SELECT device.device_id,
    device.user_id
   FROM device;


CREATE VIEW v_device_application AS
 SELECT device_application.device_id,
	device_application.application_id,
	device_application.user_id 
   FROM device_application;

   
CREATE VIEW v_device_user_notification AS 
 SELECT device_user_notification.device_id,
	device_user_notification.user_id,
	device_user_notification.event_id,
	device_user_notification.sent
   FROM device_user_notification; 
   
   
CREATE VIEW v_email AS
 SELECT email.device_id,
	email.user_id, 
	email.email_address 
   FROM email;
   

CREATE VIEW v_phone AS
 SELECT phone.device_id,
	phone.user_id,
    phone.phone_number,
	phone.phone_service_provider_id 
   FROM phone;


CREATE VIEW v_phone_service_provider AS
 SELECT phone_service_provider.phone_service_provider_id,
	phone_service_provider.email_address_domain_part,
	phone_service_provider.user_id
   FROM phone_service_provider;


CREATE VIEW v_user_notification AS
 SELECT user_notification.user_id,
    user_notification.event_id,
    user_notification.registration
   FROM user_notification;
   

CREATE VIEW v_web_gui AS
 SELECT web_gui.device_id,
	web_gui.user_id
   FROM web_gui;
   
   
SET search_path = pinboard, pg_catalog;


CREATE VIEW v_pin AS
 SELECT p.pin_id,
    p.pinboard_id,
    p.label,
	COALESCE(b.vote, (0)::bigint) AS vote,
	p.user_id,
    p.registration
   FROM (pin p
   LEFT JOIN ( SELECT sum(a.vote) AS vote,
            a.pin_id
           FROM vote_pin a
          GROUP BY a.pin_id) b ON ((b.pin_id = p.pin_id)));


CREATE VIEW v_pin_connection AS
 SELECT pin_connection.from_pin_id,
    pin_connection.to_pin_id,
    pin_connection.pinboard_id,
    pin_connection.user_id
   FROM pin_connection;


CREATE VIEW v_pin_geometry AS
 SELECT pin_geometry.pin_id,
    pin_geometry.pinboard_id,
    pin_geometry.user_id,
    pin_geometry.x_dimension,
    pin_geometry.y_dimension,
    pin_geometry.x_position,
    pin_geometry.y_position,
    pin_geometry.visible
   FROM pin_geometry;

   
CREATE VIEW v_pinboard AS
 SELECT pinboard.pinboard_id,
    pinboard.label,
	pinboard.user_id,
    pinboard.registration
   FROM pinboard;
   
   
CREATE VIEW v_pinboard_group AS
 SELECT pinboard_group.pinboard_id,
    pinboard_group.group_id,
    pinboard_group.can_edit,
	pinboard_group.user_id,
	pinboard_group.start_valid  
   FROM pinboard_group;

   
CREATE VIEW v_pinboard_pinboard AS
 SELECT pinboard_pinboard.pinboard_id,
    pinboard_pinboard.parent_id,
	pinboard_pinboard.user_id   
   FROM pinboard_pinboard;

   
CREATE VIEW v_pinboard_pinboard_node AS
 WITH RECURSIVE everypinboard AS (
                 SELECT pinboard_pinboard.parent_id AS node_id,
                    pinboard_pinboard.parent_id,
                    pinboard_pinboard.pinboard_id,
                    pinboard_pinboard.user_id,
                    1 AS depth,
                    ARRAY[pinboard_pinboard.parent_id] AS path
                   FROM pinboard_pinboard
        UNION ALL
                 SELECT every.node_id,
                    subpinboard.parent_id,
                    subpinboard.pinboard_id,
                    subpinboard.user_id,
                    (every.depth + 1),
                    (every.path || subpinboard.parent_id) AS path
                   FROM (pinboard_pinboard subpinboard
              JOIN everypinboard every ON ((subpinboard.parent_id = every.pinboard_id)))
        )
 SELECT everypinboard.node_id,
    everypinboard.parent_id,
    everypinboard.pinboard_id,
    everypinboard.user_id,
    everypinboard.depth,
    everypinboard.path
   FROM everypinboard
  ORDER BY everypinboard.node_id;
   

CREATE VIEW v_vote_pin AS
 SELECT vote_pin.pin_id,
    vote_pin.vote,
	vote_pin.user_id
   FROM vote_pin;
   
   
SET search_path = public, pg_catalog;


CREATE VIEW v_application AS
 SELECT application.application_id,
    application.url,
    application.label,
    application.description,
	application.icon,
	application.user_id,
    application.registration
   FROM application;
  
 
CREATE OR REPLACE VIEW v_application_privilege AS 
 SELECT application_privilege.application_id,
    application_privilege.privilege_id,
	application_privilege.description,
    application_privilege.user_id
   FROM application_privilege;
 

CREATE VIEW v_application_privilege_group AS
 SELECT application_privilege_group.application_id,
	application_privilege_group.privilege_id,
    application_privilege_group.group_id,
    application_privilege_group.user_id,
	application_privilege_group.registration
   FROM application_privilege_group;


CREATE VIEW v_administrator_group AS
 SELECT DISTINCT administrator_group.group_id,
    administrator_group.administrator_id,
    administrator_group.user_id
   FROM administrator_group
  ORDER BY administrator_group.administrator_id;


CREATE VIEW v_employee AS
	SELECT employee.user_id,
		employee.employee_id,
		employee.phone_number,
		employee.office,
		employee.occupation
	FROM public.employee;
  

CREATE VIEW v_group AS
 SELECT groups.group_id,
    groups.label,
    groups.description,
	groups.unique_label,
	groups.user_id,
	groups.registration
   FROM groups;
  

CREATE VIEW v_group_group AS
 SELECT DISTINCT group_group.parent_id,
    group_group.group_id,
    group_group.user_id
   FROM group_group
  ORDER BY group_group.parent_id;


CREATE VIEW v_group_group_node AS
 WITH RECURSIVE everygroup AS (
                 SELECT group_group.parent_id AS node_id,
                    group_group.parent_id,
                    group_group.group_id,
                    group_group.user_id,
                    1 AS depth,
                    ARRAY[group_group.parent_id] AS path
                   FROM group_group
        UNION ALL
                 SELECT every.node_id,
                    subgroup.parent_id,
                    subgroup.group_id,
                    subgroup.user_id,
                    (every.depth + 1),
                    (every.path || subgroup.parent_id) AS path
                   FROM (group_group subgroup
              JOIN everygroup every ON ((subgroup.parent_id = every.group_id)))
        )
 SELECT everygroup.node_id,
    everygroup.parent_id,
    everygroup.group_id,
    everygroup.user_id,
    everygroup.depth,
    everygroup.path
   FROM everygroup
  ORDER BY everygroup.node_id;


CREATE VIEW v_parameter AS
 SELECT application_id,
	  user_id,
      parameter_key,
      parameter_value
	FROM parameter;


CREATE VIEW v_privilege AS
 SELECT privilege.privilege_id,
	privilege.user_id,
    privilege.label,
    privilege.description,
	privilege.registration
   FROM privilege;
 

CREATE VIEW v_profile_picture AS
 SELECT profile_picture.user_id,
    profile_picture.user_agreement,
	profile_picture.administrator_user_id,
    profile_picture.administrator_approval,
    profile_picture.image,
    profile_picture.icon,
	profile_picture.registration
   FROM profile_picture;


CREATE VIEW v_student AS
 SELECT student.user_id,
	student.student_id
   FROM public.student;
   

CREATE VIEW v_user AS
 SELECT users.user_id,
	users.administrative_user_id,
    users.last_name,
    users.first_name,
    users.email_address,
	users.gender,
    users.last_login,
	users.registration,
	users.valid_end
   FROM users;

   
CREATE VIEW v_user_group AS
 SELECT user_group.group_id,
    groups.label,
    user_group.member_id,
    user_group.user_id,
    user_group.registration
   FROM (user_group
   JOIN groups ON ((user_group.group_id = groups.group_id)));


CREATE VIEW v_user_group_node AS
 SELECT DISTINCT ON (e.member_id, e.group_id) e.node_id, e.group_id, e.member_id, e.user_id
   FROM ( SELECT DISTINCT b.node_id, c.group_id, a.member_id, a.user_id
            FROM v_user_group a
            JOIN v_group_group_node b ON a.group_id = b.group_id
			JOIN v_group c ON (c.group_id = ANY (b.path)) OR c.group_id = a.group_id
		  UNION 
          SELECT DISTINCT d.group_id AS node_id, d.group_id, d.member_id, d.user_id
			FROM v_user_group d) e;

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

SET search_path = content, pg_catalog;


CREATE VIEW v_content_message AS
 SELECT content_message.type_id,
	content_message.data_id, 
	( SELECT get_content_label(content_message.data_id, content_message.type_id) AS content_label) AS label,
	( SELECT get_content_url(content_message.data_id, content_message.type_id) AS content_url) AS url,
    content_message.message_id,
    content_message.user_id
   FROM content_message;


CREATE VIEW v_content_pin AS
 SELECT content_pin.type_id,
	content_pin.data_id,
	( SELECT get_content_label(content_pin.data_id, content_pin.type_id) AS content_label) AS label,
	( SELECT get_content_url(content_pin.data_id, content_pin.type_id) AS content_url) AS url, 
    content_pin.pin_id,
    content_pin.user_id
   FROM content_pin;
   
   
CREATE VIEW v_content_tag AS
 SELECT content_tag.type_id,
	content_tag.data_id, 
	( SELECT get_content_label(content_tag.data_id, content_tag.type_id) AS content_label) AS label,
	( SELECT get_content_url(content_tag.data_id, content_tag.type_id) AS content_url) AS url,
    content_tag.tag_id,
    content_tag.user_id
   FROM content_tag;

SET search_path = content, pg_catalog;


CREATE RULE v_content_message_delete AS
    ON DELETE TO v_content_message DO INSTEAD  DELETE FROM content_message
  WHERE (content_message.message_id = old.message_id);


CREATE RULE v_content_message_insert AS
    ON INSERT TO v_content_message DO INSTEAD  INSERT INTO content_message (type_id, data_id, message_id, user_id)
  VALUES (new.type_id, new.data_id, new.message_id, new.user_id);


CREATE RULE v_content_message_update AS
    ON UPDATE TO v_content_message DO INSTEAD  UPDATE content_message SET type_id = new.type_id, data_id = new.data_id, user_id = new.user_id, registration = now()
  WHERE (content_message.message_id = new.message_id);


CREATE RULE v_content_pin_delete AS
    ON DELETE TO v_content_pin DO INSTEAD  DELETE FROM content_pin
  WHERE (content_pin.pin_id = old.pin_id);


CREATE RULE v_content_pin_insert AS
    ON INSERT TO v_content_pin DO INSTEAD  INSERT INTO content_pin (type_id, data_id, pin_id, user_id)
  VALUES (new.type_id, new.data_id, new.pin_id, new.user_id);
  
  
CREATE RULE v_content_pin_update AS
    ON UPDATE TO v_content_pin DO INSTEAD  UPDATE content_pin SET type_id = new.type_id, data_id = new.data_id, user_id = new.user_id, registration = now()
  WHERE (content_pin.pin_id = new.pin_id);
  
  
CREATE RULE v_content_tag_delete AS
    ON DELETE TO v_content_tag DO INSTEAD  DELETE FROM content_tag
  WHERE (content_tag.tag_id = old.tag_id AND type_id = old.type_id AND data_id = old.data_id);


CREATE RULE v_content_tag_insert AS
    ON INSERT TO v_content_tag DO INSTEAD  INSERT INTO content_tag (type_id, data_id, tag_id, user_id)
  VALUES (new.type_id, new.data_id, new.tag_id, new.user_id);


CREATE RULE v_content_tag_update AS
    ON UPDATE TO v_content_tag DO INSTEAD  UPDATE content_tag SET type_id = new.type_id, data_id = new.data_id, user_id = new.user_id, registration = now()
  WHERE (content_tag.tag_id = new.tag_id);  
  

CREATE RULE v_field_delete AS
    ON DELETE TO v_field DO INSTEAD  DELETE FROM field 
  WHERE (field.field_id = old.field_id AND field.type_id = old.type_id);


CREATE RULE v_field_insert AS
    ON INSERT TO v_field DO INSTEAD  INSERT INTO field (field_id, type_id)
  VALUES (new.field_id, new.type_id);

  
CREATE RULE v_field_update AS
    ON UPDATE TO v_field DO INSTEAD  NOTHING;

	
CREATE RULE v_tag_delete AS
    ON DELETE TO v_tag DO INSTEAD  DELETE FROM content.tag
  WHERE (tag.tag_id = old.tag_id);


CREATE RULE v_tag_insert AS
    ON INSERT TO v_tag DO INSTEAD  INSERT INTO content.tag (tag_id, user_id)
  VALUES (new.tag_id, new.user_id);


CREATE RULE v_tag_update AS
    ON UPDATE TO v_tag DO INSTEAD  NOTHING;
  

CREATE RULE v_type_delete AS
    ON DELETE TO v_type DO INSTEAD  DELETE FROM content.type
  WHERE (type.type_id = old.type_id);


CREATE RULE v_type_insert AS
    ON INSERT TO v_type DO INSTEAD  INSERT INTO content.type (type_id, table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event)
  VALUES (new.type_id, new.table_schema, new.table_name, new.application_id, new.url, new.function_name_to_notify_groups_about_an_event);


CREATE RULE v_type_update AS
    ON UPDATE TO v_type DO INSTEAD  UPDATE content.type SET table_schema = new.table_schema, table_name = new.table_name, application_id = new.application_id, url = new.url, function_name_to_notify_groups_about_an_event = new.function_name_to_notify_groups_about_an_event
  WHERE (type.type_id = old.type_id);


CREATE RULE v_url_delete AS
    ON DELETE TO v_url DO INSTEAD  DELETE FROM url
  WHERE (url.url_id = old.url_id);


CREATE RULE v_url_insert AS
    ON INSERT TO v_url DO INSTEAD  INSERT INTO url (url_id, url, label, user_id)
  VALUES (new.url_id, new.url, new.label, new.user_id);


CREATE RULE v_url_update AS
    ON UPDATE TO v_url DO INSTEAD  UPDATE url SET url = new.url, label = new.label 
  WHERE (url_id = new.url_id);


SET search_path = discussion, pg_catalog;


CREATE RULE v_discussion_delete AS
    ON DELETE TO v_discussion DO INSTEAD  DELETE FROM discussion
  WHERE (discussion.discussion_id = old.discussion_id);


CREATE RULE v_discussion_insert AS
    ON INSERT TO v_discussion DO INSTEAD  INSERT INTO discussion (discussion_id, label, user_id)
  VALUES (new.discussion_id, new.label, new.user_id);


CREATE RULE v_discussion_update AS
    ON UPDATE TO v_discussion DO INSTEAD  UPDATE discussion SET label = new.label
  WHERE (discussion.discussion_id = new.discussion_id);


CREATE RULE v_discussion_group_delete AS
    ON DELETE TO v_discussion_group DO INSTEAD  DELETE FROM discussion_group
  WHERE ((discussion_group.discussion_id = old.discussion_id) AND (discussion_group.group_id = old.group_id));


CREATE RULE v_discussion_group_insert AS
    ON INSERT TO v_discussion_group DO INSTEAD  INSERT INTO discussion_group (discussion_id, group_id, can_edit, user_id, start_valid)
  VALUES (new.discussion_id, new.group_id, new.can_edit, new.user_id, COALESCE(new.start_valid, now()));


CREATE RULE v_discussion_group_update AS
    ON UPDATE TO v_discussion_group DO INSTEAD  UPDATE discussion_group SET can_edit = new.can_edit, start_valid = COALESCE(new.start_valid, now())
  WHERE ((discussion_group.discussion_id = new.discussion_id) AND (discussion_group.group_id = new.group_id));


CREATE RULE v_message_delete AS
    ON DELETE TO v_message DO INSTEAD  DELETE FROM message
  WHERE ((message.message_id = old.message_id) OR (message.parent_id = old.message_id));


CREATE RULE v_message_insert AS
    ON INSERT TO v_message DO INSTEAD  INSERT INTO message (message_id, discussion_id, parent_id, label, user_id)
  VALUES (new.message_id, new.discussion_id, new.parent_id, new.label, new.user_id);

CREATE RULE v_message_update AS
    ON UPDATE TO v_message DO INSTEAD  UPDATE message SET label = new.label
  WHERE (message.message_id = new.message_id);


CREATE RULE v_vote_message_delete AS
    ON DELETE TO v_vote_message DO INSTEAD  DELETE FROM vote_message
  WHERE ((vote_message.user_id = old.user_id) AND (vote_message.message_id = old.message_id));


CREATE RULE v_vote_message_insert AS
    ON INSERT TO v_vote_message DO INSTEAD  INSERT INTO vote_message (message_id, user_id, vote)
  VALUES (new.message_id, new.user_id, new.vote);


CREATE RULE v_vote_message_update AS
    ON UPDATE TO v_vote_message DO INSTEAD  UPDATE vote_message SET vote = new.vote, registration = now()
  WHERE ((vote_message.user_id = new.user_id) AND (vote_message.message_id = new.message_id));


SET search_path = file, pg_catalog;


CREATE RULE v_corrected_file_delete AS
    ON DELETE TO v_corrected_file DO INSTEAD  DELETE FROM corrected_file
  WHERE (corrected_file.file_id = old.file_id);


CREATE RULE v_corrected_file_insert AS
    ON INSERT TO v_corrected_file DO INSTEAD  INSERT INTO corrected_file (corrected_file_id, file_id, correction_completed, user_id)
  VALUES (new.corrected_file_id, new.file_id, new.correction_completed, new.user_id);


CREATE RULE v_corrected_file_update AS
    ON UPDATE TO v_corrected_file DO INSTEAD UPDATE corrected_file SET correction_completed = new.correction_completed, user_id = new.user_id
  WHERE (corrected_file.file_id = new.file_id); 


CREATE RULE v_file_delete AS
    ON DELETE TO v_file DO INSTEAD  DELETE FROM file
  WHERE (file.file_id = old.file_id);


CREATE RULE v_file_insert AS
    ON INSERT TO v_file DO INSTEAD  INSERT INTO file (file_id, label, user_id)
  VALUES (new.file_id, new.label, new.user_id);


CREATE RULE v_file_update AS
    ON UPDATE TO v_file DO INSTEAD UPDATE file SET label = new.label
  WHERE (file.file_id = new.file_id); 


CREATE RULE v_file_group_delete AS
    ON DELETE TO v_file_group DO INSTEAD  DELETE FROM file.file_group
  WHERE (file_group.file_id = old.file_id) AND (file_group.group_id = old.group_id);


CREATE RULE v_file_group_insert AS
    ON INSERT TO v_file_group DO INSTEAD  INSERT INTO file_group (file_id, group_id, can_edit, user_id, start_valid)
  VALUES (new.file_id, new.group_id, new.can_edit, new.user_id, COALESCE(new.start_valid, now()));


CREATE RULE v_file_group_update AS
    ON UPDATE TO v_file_group DO INSTEAD  UPDATE file_group SET can_edit = new.can_edit, start_valid = COALESCE(new.start_valid, now())
  WHERE file_group.file_id = new.file_id AND file_group.group_id = new.group_id;


CREATE RULE v_folder_delete AS
    ON DELETE TO v_folder DO INSTEAD  DELETE FROM folder
  WHERE (folder.folder_id = old.folder_id);


CREATE RULE v_folder_insert AS
    ON INSERT TO v_folder DO INSTEAD  INSERT INTO folder (folder_id, parent_id, label, user_id)
  VALUES (new.folder_id, new.parent_id, new.label, new.user_id);


CREATE RULE v_folder_update AS
    ON UPDATE TO v_folder DO INSTEAD UPDATE folder SET label = new.label, parent_id = new.parent_id
  WHERE (folder.folder_id = new.folder_id); 


CREATE RULE v_folder_file_delete AS
    ON DELETE TO v_folder_file DO INSTEAD  DELETE FROM folder_file
  WHERE (folder_file.folder_id = old.folder_id) AND (folder_file.file_id = old.file_id);


CREATE RULE v_folder_file_insert AS
    ON INSERT TO v_folder_file DO INSTEAD  INSERT INTO folder_file (folder_id, file_id, user_id)
  VALUES (new.folder_id, new.file_id, new.user_id);


CREATE RULE v_folder_file_update AS
    ON UPDATE TO v_folder_file DO INSTEAD  NOTHING;


CREATE RULE v_folder_group_delete AS
    ON DELETE TO v_folder_group DO INSTEAD  DELETE FROM folder_group
  WHERE (folder_group.folder_id = old.folder_id) AND (folder_group.group_id = old.group_id);


CREATE RULE v_folder_group_insert AS
    ON INSERT TO v_folder_group DO INSTEAD  INSERT INTO folder_group (folder_id, group_id, can_edit, user_id, start_valid)
  VALUES (new.folder_id, new.group_id, new.can_edit, new.user_id, COALESCE(new.start_valid, now()));


CREATE RULE v_folder_group_update AS
    ON UPDATE TO v_folder_group DO INSTEAD  UPDATE folder_group SET can_edit = new.can_edit, start_valid = COALESCE(new.start_valid, now())
  WHERE (folder_group.folder_id = new.folder_id) AND (folder_group.group_id = new.group_id);


CREATE RULE v_turn_in_folder_delete AS
    ON DELETE TO v_turn_in_folder DO INSTEAD  DELETE FROM turn_in_folder
  WHERE (turn_in_folder.folder_id = old.folder_id);


CREATE RULE v_turn_in_folder_insert AS
    ON INSERT TO v_turn_in_folder DO INSTEAD  INSERT INTO turn_in_folder (folder_id, opening_time, closing_time, user_id)
  VALUES (new.folder_id, new.opening_time, new.closing_time, new.user_id);


CREATE RULE v_turn_in_folder_update AS
    ON UPDATE TO v_turn_in_folder DO INSTEAD UPDATE turn_in_folder SET opening_time = new.opening_time, closing_time = new.closing_time
  WHERE (turn_in_folder.folder_id = new.folder_id);


CREATE RULE v_version_delete AS
    ON DELETE TO v_version DO INSTEAD  DELETE FROM version
  WHERE (version.version_id = old.version_id);


CREATE RULE v_version_insert AS
    ON INSERT TO v_version DO INSTEAD  INSERT INTO version (version_id, file_id, path, description, user_id)
  VALUES (new.version_id, new.file_id, new.path, new.description, new.user_id);


CREATE RULE v_version_update AS
    ON UPDATE TO v_version DO INSTEAD UPDATE version SET description = new.description
  WHERE (version.version_id = new.version_id);
  

SET search_path = notification, pg_catalog;


CREATE RULE v_device_delete AS
    ON DELETE TO v_device DO INSTEAD  DELETE FROM device
  WHERE (device.device_id = old.device_id);


CREATE RULE v_device_insert AS
    ON INSERT TO v_device DO INSTEAD  INSERT INTO device (device_id, user_id)
  VALUES (new.device_id, new.user_id);


CREATE RULE v_device_update AS
    ON UPDATE TO v_device DO INSTEAD NOTHING;

	
CREATE RULE v_device_application_delete AS
    ON DELETE TO v_device_application DO INSTEAD  DELETE FROM device_application
  WHERE (device_application.device_id = old.device_id AND device_application.application_id = old.application_id);


CREATE RULE v_device_application_insert AS
    ON INSERT TO v_device_application DO INSTEAD  INSERT INTO device_application (device_id, application_id, user_id)
  VALUES (new.device_id, new.application_id, new.user_id);


CREATE RULE v_device_application_update AS
    ON UPDATE TO v_device_application DO INSTEAD NOTHING;

	
CREATE RULE v_device_user_notification_delete AS
    ON DELETE TO v_device_user_notification DO INSTEAD  NOTHING;


CREATE RULE v_device_user_notification_insert AS
    ON INSERT TO v_device_user_notification DO INSTEAD  NOTHING;


CREATE RULE v_device_user_notification_update AS
    ON UPDATE TO v_device_user_notification DO INSTEAD UPDATE device_user_notification SET sent = new.sent 
  WHERE (device_user_notification.device_id = new.device_id AND device_user_notification.event_id = new.event_id); 
  
  
CREATE RULE v_email_delete AS
    ON DELETE TO v_email DO INSTEAD  DELETE FROM email
  WHERE (email.device_id = old.device_id);


CREATE RULE v_email_insert AS
    ON INSERT TO v_email DO INSTEAD  INSERT INTO email (device_id, user_id, email_address)
  VALUES (new.device_id, new.user_id, new.email_address);


CREATE RULE v_email_update AS
    ON UPDATE TO v_email DO INSTEAD UPDATE email SET email_address = new.email_address
  WHERE (email.device_id = new.device_id);
	
  
CREATE RULE v_phone_delete AS
    ON DELETE TO v_phone DO INSTEAD  DELETE FROM phone
  WHERE (phone.device_id = old.device_id);


CREATE RULE v_phone_insert AS
    ON INSERT TO v_phone DO INSTEAD  INSERT INTO phone (device_id, user_id, phone_number, phone_service_provider_id)
  VALUES (new.device_id, new.user_id, new.phone_number, new.phone_service_provider_id);


CREATE RULE v_phone_update AS
    ON UPDATE TO v_phone DO INSTEAD UPDATE phone SET phone_number = new.phone_number, phone_service_provider_id = new.phone_service_provider_id 
  WHERE (phone.device_id = new.device_id);   

  
CREATE RULE v_phone_service_provider_delete AS
    ON DELETE TO v_phone_service_provider DO INSTEAD  DELETE FROM phone_service_provider
  WHERE (phone_service_provider.phone_service_provider_id = old.phone_service_provider_id);


CREATE RULE v_phone_service_provider_insert AS
    ON INSERT TO v_phone_service_provider DO INSTEAD  INSERT INTO phone_service_provider (phone_service_provider_id, email_address_domain_part, user_id)
  VALUES (new.phone_service_provider_id, new.email_address_domain_part, new.user_id);


CREATE RULE v_phone_service_provider_update AS
    ON UPDATE TO v_phone_service_provider DO INSTEAD UPDATE phone_service_provider SET email_address_domain_part = new.email_address_domain_part 
  WHERE (phone_service_provider.phone_service_provider_id = new.phone_service_provider_id);   
  

CREATE RULE v_web_gui_delete AS
    ON DELETE TO v_web_gui DO INSTEAD  DELETE FROM web_gui
  WHERE (web_gui.device_id = old.device_id);


CREATE RULE v_web_gui_insert AS
    ON INSERT TO v_web_gui DO INSTEAD  INSERT INTO web_gui (device_id, user_id)
  VALUES (new.device_id, new.user_id);


CREATE RULE v_web_gui_update AS
    ON UPDATE TO v_web_gui DO INSTEAD NOTHING;   
	
  
SET search_path = pinboard, pg_catalog;


CREATE RULE v_pin_delete AS
    ON DELETE TO v_pin DO INSTEAD  DELETE FROM pin
  WHERE (pin.pin_id = old.pin_id);

  
CREATE RULE v_pin_insert AS
    ON INSERT TO v_pin DO INSTEAD  INSERT INTO pin (pin_id, pinboard_id, label, user_id)
  VALUES (new.pin_id, new.pinboard_id, new.label, new.user_id);


CREATE RULE v_pin_update AS
    ON UPDATE TO v_pin DO INSTEAD  UPDATE pin SET label = new.label, pinboard_id = new.pinboard_id
  WHERE (pin.pin_id = old.pin_id);


CREATE RULE v_pin_connection_delete AS
    ON DELETE TO v_pin_connection DO INSTEAD  DELETE FROM pin_connection
  WHERE (((pin_connection.from_pin_id = old.from_pin_id) AND (pin_connection.to_pin_id = old.to_pin_id)) AND (pin_connection.pinboard_id = old.pinboard_id));


CREATE RULE v_pin_connection_insert AS
    ON INSERT TO v_pin_connection DO INSTEAD  INSERT INTO pin_connection (from_pin_id, to_pin_id, pinboard_id, user_id)
  VALUES (new.from_pin_id, new.to_pin_id, new.pinboard_id, new.user_id);

  
CREATE RULE v_pin_connection_update AS
    ON UPDATE TO v_pin_connection DO INSTEAD  NOTHING;
  

CREATE RULE v_pin_geometry_delete AS
    ON DELETE TO v_pin_geometry DO INSTEAD  DELETE FROM pin_geometry
  WHERE ((pin_geometry.pin_id = old.pin_id) AND (pin_geometry.pinboard_id = old.pinboard_id));


CREATE RULE v_pin_geometry_insert AS
    ON INSERT TO v_pin_geometry DO INSTEAD  INSERT INTO pin_geometry (pin_id, pinboard_id, user_id, x_dimension, y_dimension, x_position, y_position, visible)
  VALUES (new.pin_id, new.pinboard_id, new.user_id, new.x_dimension, new.y_dimension, new.x_position, new.y_position, new.visible);


CREATE RULE v_pin_geometry_update AS
    ON UPDATE TO v_pin_geometry DO INSTEAD  UPDATE pin_geometry SET x_dimension = new.x_dimension, y_dimension = new.y_dimension, x_position = new.x_position, y_position = new.y_position, visible = new.visible
  WHERE ((pin_geometry.pin_id = new.pin_id) AND (pin_geometry.pinboard_id = new.pinboard_id));


CREATE RULE v_pinboard_delete AS
    ON DELETE TO v_pinboard DO INSTEAD  DELETE FROM pinboard
  WHERE (pinboard.pinboard_id = old.pinboard_id);

  
CREATE RULE v_pinboard_insert AS
    ON INSERT TO v_pinboard DO INSTEAD  INSERT INTO pinboard (pinboard_id, label, user_id)
  VALUES (new.pinboard_id, new.label, new.user_id);


CREATE RULE v_pinboard_update AS
    ON UPDATE TO v_pinboard DO INSTEAD  UPDATE pinboard SET label = new.label 
  WHERE (pinboard.pinboard_id = new.pinboard_id);
  
  
CREATE RULE v_pinboard_group_delete AS
    ON DELETE TO v_pinboard_group DO INSTEAD  DELETE FROM pinboard_group
  WHERE ((pinboard_group.pinboard_id = old.pinboard_id) AND (pinboard_group.group_id = old.group_id));


CREATE RULE v_pinboard_group_insert AS
    ON INSERT TO v_pinboard_group DO INSTEAD  INSERT INTO pinboard_group (pinboard_id, group_id, can_edit, user_id, start_valid)
  VALUES (new.pinboard_id, new.group_id, new.can_edit, new.user_id, COALESCE(new.start_valid, now()));


CREATE RULE v_pinboard_group_update AS
    ON UPDATE TO v_pinboard_group DO INSTEAD  UPDATE pinboard_group SET can_edit = new.can_edit, start_valid = COALESCE(new.start_valid, now())
  WHERE ((pinboard_group.pinboard_id = new.pinboard_id) AND (pinboard_group.group_id = new.group_id));


CREATE RULE v_pinboard_pinboard_delete AS
    ON DELETE TO v_pinboard_pinboard DO INSTEAD  DELETE FROM pinboard_pinboard
  WHERE ((pinboard_pinboard.pinboard_id = old.pinboard_id) AND (pinboard_pinboard.parent_id = old.parent_id));


CREATE RULE v_pinboard_pinboard_insert AS
    ON INSERT TO v_pinboard_pinboard DO INSTEAD  INSERT INTO pinboard_pinboard (pinboard_id, parent_id, user_id)
  VALUES (new.pinboard_id, new.parent_id, new.user_id);


CREATE RULE v_pinboard_pinboard_update AS
    ON UPDATE TO v_pinboard_pinboard DO INSTEAD  NOTHING;

  
CREATE RULE v_vote_pin_delete AS
    ON DELETE TO v_vote_pin DO INSTEAD  DELETE FROM vote_pin
  WHERE ((vote_pin.user_id = old.user_id) AND (vote_pin.pin_id = old.pin_id));


CREATE RULE v_vote_pin_insert AS
    ON INSERT TO v_vote_pin DO INSTEAD  INSERT INTO vote_pin (pin_id, user_id, vote)
  VALUES (new.pin_id, new.user_id, new.vote);


CREATE RULE v_vote_pin_update AS
    ON UPDATE TO v_vote_pin DO INSTEAD  UPDATE vote_pin SET vote = new.vote, registration = now()
  WHERE ((vote_pin.user_id = new.user_id) AND (vote_pin.pin_id = new.pin_id));


SET search_path = public, pg_catalog;


CREATE RULE v_administrator_group_delete AS
    ON DELETE TO v_administrator_group DO INSTEAD  DELETE FROM administrator_group
  WHERE ((administrator_group.administrator_id = old.administrator_id) AND (administrator_group.group_id = old.group_id));


CREATE RULE v_administrator_group_insert AS
    ON INSERT TO v_administrator_group DO INSTEAD  INSERT INTO administrator_group (group_id, administrator_id, user_id)
  VALUES (new.group_id, new.administrator_id, new.user_id);


CREATE RULE v_administrator_group_update AS
    ON UPDATE TO v_administrator_group DO INSTEAD  NOTHING;

  
CREATE RULE v_application_insert AS
    ON INSERT TO v_application DO INSTEAD  INSERT INTO application (application_id, url, label, description, icon, user_id)
  VALUES (new.application_id, new.url, new.label, new.description, new.icon, new.user_id);
  
  
CREATE RULE v_application_update AS
    ON UPDATE TO v_application DO INSTEAD  UPDATE application SET url = new.url, label = new.label, description = new.description, icon = new.icon
  WHERE (application.application_id = new.application_id);

  
CREATE RULE v_application_delete AS
    ON DELETE TO v_application DO INSTEAD  DELETE FROM application
  WHERE (application.application_id = old.application_id);


CREATE RULE v_application_privilege_delete AS
    ON DELETE TO v_application_privilege DO INSTEAD  DELETE FROM application_privilege
  WHERE application_privilege.application_id = old.application_id AND application_privilege.privilege_id = old.privilege_id ;


CREATE RULE v_application_privilege_insert AS
    ON INSERT TO v_application_privilege DO INSTEAD  INSERT INTO application_privilege (application_id, privilege_id, description, user_id)
  VALUES (new.application_id, new.privilege_id, new.description, new.user_id);  


CREATE RULE v_application_privilege_update AS
    ON UPDATE TO v_application_privilege DO INSTEAD  UPDATE application_privilege SET description = new.description
  WHERE (application_privilege.application_id = new.application_id and application_privilege.privilege_id = new.privilege_id);  
 
 
CREATE RULE v_application_privilege_group_delete AS
    ON DELETE TO v_application_privilege_group DO INSTEAD  DELETE FROM application_privilege_group
  WHERE (application_privilege_group.application_id = old.application_id AND application_privilege_group.privilege_id = old.privilege_id AND application_privilege_group.group_id = old.group_id);

  
CREATE RULE v_application_privilege_group_insert AS
    ON INSERT TO v_application_privilege_group DO INSTEAD  INSERT INTO application_privilege_group (application_id, privilege_id, group_id, user_id)
  VALUES (new.application_id, new.privilege_id, new.group_id, new.user_id);


CREATE RULE v_application_privilege_group_update AS
    ON UPDATE TO v_application_privilege_group DO INSTEAD  NOTHING;
  
  
CREATE RULE v_employee_delete AS
    ON DELETE TO v_employee DO INSTEAD  DELETE FROM employee
  WHERE (employee.user_id = old.user_id AND employee.employee_id = old.employee_id);
  
  
CREATE RULE v_employee_insert AS
    ON INSERT TO v_employee DO INSTEAD  INSERT INTO employee (employee_id, user_id, phone_number, office, occupation)
  VALUES (new.employee_id, new.user_id, new.phone_number, new.office, new.occupation);


CREATE RULE v_employee_update AS
    ON UPDATE TO v_employee DO INSTEAD  UPDATE employee SET employee_id = new.employee_id, phone_number = new.phone_number, office = new.office, occupation = new.occupation
  WHERE (employee.user_id = new.user_id);


CREATE RULE v_group_group_delete AS
    ON DELETE TO v_group_group DO INSTEAD  DELETE FROM group_group
  WHERE ((group_group.parent_id = old.parent_id) AND (group_group.group_id = old.group_id));

  
CREATE RULE v_group_group_insert AS
    ON INSERT TO v_group_group DO INSTEAD  INSERT INTO group_group (group_id, parent_id, user_id)
  VALUES (new.group_id, new.parent_id, new.user_id);


CREATE RULE v_group_group_update AS
    ON UPDATE TO v_group_group DO INSTEAD  NOTHING;


CREATE RULE v_group_delete AS
    ON DELETE TO v_group DO INSTEAD DELETE FROM groups
  WHERE (groups.group_id = old.group_id);

  
CREATE RULE v_group_insert AS
    ON INSERT TO v_group DO INSTEAD  INSERT INTO groups (group_id, user_id, label, description, last_modification, unique_label)
  VALUES (new.group_id, new.user_id, new.label, new.description, now(), new.unique_label);

  
CREATE RULE v_group_update AS
    ON UPDATE TO v_group DO INSTEAD  UPDATE groups SET label = new.label, description = new.description, last_modification = now(), unique_label = new.unique_label
  WHERE (groups.group_id = new.group_id); 
  
  
CREATE RULE v_parameter_insert AS
    ON INSERT TO v_parameter DO INSTEAD  INSERT INTO parameter (application_id, parameter_key, parameter_value, user_id)
  VALUES (new.application_id, new.parameter_key, new.parameter_value, new.user_id);
  

CREATE RULE v_parameter_update AS
    ON UPDATE TO v_parameter DO INSTEAD  UPDATE parameter SET parameter_value = new.parameter_value
  WHERE parameter.application_id = new.application_id AND parameter.parameter_key = old.parameter_key;
 

CREATE RULE v_parameter_delete AS
    ON DELETE TO v_parameter DO INSTEAD  DELETE FROM parameter
  WHERE (parameter.application_id = old.application_id and parameter.parameter_key = old.parameter_key);
 
 
CREATE RULE v_privilege_delete AS
    ON DELETE TO v_privilege DO INSTEAD  DELETE FROM privilege
  WHERE (privilege.privilege_id = old.privilege_id);
  
  
CREATE RULE v_privilege_insert AS
    ON INSERT TO v_privilege DO INSTEAD  INSERT INTO privilege (privilege_id, user_id, label, description)
  VALUES (new.privilege_id, new.user_id, new.label, new.description);


CREATE RULE v_privilege_update AS
    ON UPDATE TO v_privilege DO INSTEAD  UPDATE privilege SET label = new.label, description = new.description
  WHERE (privilege.privilege_id = new.privilege_id);


CREATE RULE v_profile_picture_delete AS
    ON DELETE TO v_profile_picture DO INSTEAD  DELETE FROM profile_picture
  WHERE (profile_picture.user_id = old.user_id);


CREATE RULE v_profile_picture_insert AS
    ON INSERT TO v_profile_picture DO INSTEAD  INSERT INTO profile_picture (user_id, user_agreement, administrator_user_id, administrator_approval, image, icon)
  VALUES (new.user_id, new.user_agreement, new.administrator_user_id, new.administrator_approval, new.image, new.icon);


CREATE RULE v_profile_picture_update AS
    ON UPDATE TO v_profile_picture DO INSTEAD  UPDATE profile_picture SET user_agreement = new.user_agreement, administrator_user_id = new.administrator_user_id, administrator_approval = new.administrator_approval, image = new.image, icon = new.icon
  WHERE (profile_picture.user_id = new.user_id);

  
CREATE RULE v_student_delete AS
    ON DELETE TO v_student DO INSTEAD  DELETE FROM student
  WHERE (student.user_id = old.user_id AND student.student_id = old.student_id);

  
CREATE RULE v_student_insert AS
    ON INSERT TO v_student DO INSTEAD  INSERT INTO student (student_id, user_id)
  VALUES (new.student_id, new.user_id);
 
 
CREATE RULE v_student_update AS
    ON UPDATE TO v_student DO INSTEAD  UPDATE student SET student_id = new.student_id
  WHERE (student.user_id = new.user_id);


CREATE OR REPLACE RULE v_user_delete AS
    ON DELETE TO v_user DO INSTEAD  UPDATE users SET valid_end = now()
  WHERE users.user_id = old.user_id;
  
  
CREATE RULE v_user_insert AS
    ON INSERT TO v_user DO INSTEAD  INSERT INTO users (user_id, administrative_user_id, first_name, last_name, email_address, gender)  
		SELECT new.user_id, new.administrative_user_id, new.first_name, new.last_name, new.email_address, new.gender
          WHERE NOT (EXISTS ( SELECT users.user_id
                   FROM users
                  WHERE users.user_id = new.user_id));


CREATE RULE v_user_update AS
    ON UPDATE TO v_user DO INSTEAD  UPDATE users SET administrative_user_id = new.administrative_user_id, first_name = new.first_name, last_name = new.last_name, email_address = new.email_address, gender = new.gender, valid_end = new.valid_end
  WHERE users.user_id = new.user_id;


CREATE RULE v_user_group_delete AS
    ON DELETE TO v_user_group DO INSTEAD  DELETE FROM user_group
  WHERE ((user_group.group_id = old.group_id) AND (user_group.member_id = old.member_id));


CREATE RULE v_user_group_insert AS
    ON INSERT TO v_user_group DO INSTEAD  INSERT INTO user_group (group_id, member_id, user_id)  SELECT new.group_id,
            new.member_id, new.user_id
          WHERE (NOT EXISTS ( SELECT user_group.group_id,
                    user_group.member_id
                   FROM user_group
                  WHERE ((user_group.group_id = new.group_id) AND (user_group.member_id = new.member_id))));


CREATE RULE v_user_group_update AS
    ON UPDATE TO v_user_group DO INSTEAD NOTHING;

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

TRUNCATE public.users RESTART IDENTITY CASCADE;
TRUNCATE content.type RESTART IDENTITY CASCADE;




CREATE SCHEMA note;


CREATE TABLE note.timespan
(
  timespan_id serial NOT NULL,
  user_id INT4 NOT NULL,
  label text NOT NULL,
  start_date timestamp with time zone NOT NULL,
  end_date timestamp with time zone NOT NULL,
  registration timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT pk_timespan PRIMARY KEY (timespan_id),
  CONSTRAINT fk_created_by_user FOREIGN KEY (user_id)
      REFERENCES public.users (user_id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);
ALTER TABLE note.timespan
  OWNER TO opus;

-- Index: timespan_pk

-- DROP INDEX timespan_pk;

CREATE UNIQUE INDEX timespan_pk
  ON note.timespan
  USING btree
  (timespan_id);
/*==============================================================*/
/* Table: note.ADMINISTRATIVE_ELEMENT                                */
/*==============================================================*/
create table note.ADMINISTRATIVE_ELEMENT (
   PROGRAM_ID           SERIAL               not null,
   LABEL                TEXT                 null,
   DESCRIPTION          TEXT                 null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_ADMINISTRATIVE_ELEMENT primary key (PROGRAM_ID)
);

/*==============================================================*/
/* Index: ADMINISTRATIVE_ELEMENT_PK                             */
/*==============================================================*/
create unique index ADMINISTRATIVE_ELEMENT_PK on note.ADMINISTRATIVE_ELEMENT (
PROGRAM_ID
);

/*==============================================================*/
/* Table: note.ADMINISTRATIVE_GROUP                                  */
/*==============================================================*/
create table note.ADMINISTRATIVE_GROUP (
   PROGRAM_ID           INT4                 not null,
   GROUP_ID             INT4                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_ADMINISTRATIVE_GROUP primary key (PROGRAM_ID, GROUP_ID)
);

/*==============================================================*/
/* Index: ADMINISTRATIVE_GROUP_PK                               */
/*==============================================================*/
create unique index ADMINISTRATIVE_GROUP_PK on note.ADMINISTRATIVE_GROUP (
PROGRAM_ID,
GROUP_ID
);

/*==============================================================*/
/* Index: ADMINISTRATIVE_GROUP_FK                               */
/*==============================================================*/
create  index ADMINISTRATIVE_GROUP_FK on note.ADMINISTRATIVE_GROUP (
PROGRAM_ID
);

/*==============================================================*/
/* Index: ADMINISTRATIVE_GROUP_FK2                              */
/*==============================================================*/
create  index ADMINISTRATIVE_GROUP_FK2 on note.ADMINISTRATIVE_GROUP (
GROUP_ID
);

/*==============================================================*/
/* Table: note.ADMINISTRATIVE_HIERARCHY                              */
/*==============================================================*/
create table note.ADMINISTRATIVE_HIERARCHY (
   ADM_PROGRAM_ID       INT4                 not null,
   PROGRAM_ID           INT4                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_ADMINISTRATIVE_HIERARCHY primary key (ADM_PROGRAM_ID, PROGRAM_ID)
);

/*==============================================================*/
/* Index: ADMINISTRATIVE_HIERARCHY_PK                           */
/*==============================================================*/
create unique index ADMINISTRATIVE_HIERARCHY_PK on note.ADMINISTRATIVE_HIERARCHY (
ADM_PROGRAM_ID,
PROGRAM_ID
);

/*==============================================================*/
/* Index: ADMINISTRATIVE_HIERARCHY_FK                           */
/*==============================================================*/
create  index ADMINISTRATIVE_HIERARCHY_FK on note.ADMINISTRATIVE_HIERARCHY (
ADM_PROGRAM_ID
);

/*==============================================================*/
/* Index: ADMINISTRATIVE_HIERARCHY2_FK                          */
/*==============================================================*/
create  index ADMINISTRATIVE_HIERARCHY2_FK on note.ADMINISTRATIVE_HIERARCHY (
PROGRAM_ID
);

/*==============================================================*/
/* Table: note.ASSIGNED_GROUP                                        */
/*==============================================================*/
create table note.ASSIGNED_GROUP (
   EG_INSTANCE_ID 		INT4				 not null,
   PRIVILEGE_ID         INT4                 not null,
   GROUP_ID             INT4                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_ASSIGNED_GROUP primary key (EG_INSTANCE_ID, PRIVILEGE_ID, GROUP_ID)
);

/*==============================================================*/
/* Index: ASSIGNED_GROUP_PK                                     */
/*==============================================================*/
create unique index ASSIGNED_GROUP_PK on note.ASSIGNED_GROUP (
EG_INSTANCE_ID,
PRIVILEGE_ID,
GROUP_ID
);

/*==============================================================*/
/* Index: ASSIGNED_GROUP_FK                                     */
/*==============================================================*/
create  index ASSIGNED_GROUP_FK on note.ASSIGNED_GROUP (
EG_INSTANCE_ID
);

/*==============================================================*/
/* Index: ASSIGNED_GROUP_FK2                                    */
/*==============================================================*/
create  index ASSIGNED_GROUP_FK2 on note.ASSIGNED_GROUP (
PRIVILEGE_ID
);

/*==============================================================*/
/* Index: ASSIGNED_GROUP_FK3                                    */
/*==============================================================*/
create  index ASSIGNED_GROUP_FK3 on note.ASSIGNED_GROUP (
GROUP_ID
);

/*==============================================================*/
/* Table: note.CRITERION                                             */
/*==============================================================*/
create table note.CRITERION (
   CRITERION_ID         SERIAL               not null,
   LEVEL_AGGREGATE_ID   INT4                 null,
   RUBRIC_ID            INT4                 not null,
   GENERIC_CRITERION_ID INT4                 null,
   EG_ID                INT4                 not null,
   LABEL                TEXT                 null,
   WEIGHTING            INT4                 not null,
   VALIDITY_START       TIMESTAMP            not null,
   VALIDITY_END         TIMESTAMP            null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_CRITERION primary key (CRITERION_ID)
);

/*==============================================================*/
/* Index: CRITERION_PK                                          */
/*==============================================================*/
create unique index CRITERION_PK on note.CRITERION (
CRITERION_ID
);

/*==============================================================*/
/* Index: GOAL_CRITERION_FK                                     */
/*==============================================================*/
create  index GOAL_CRITERION_FK on note.CRITERION (
EG_ID
);

/*==============================================================*/
/* Index: CRITERION_RUBRIC_FK                                   */
/*==============================================================*/
create  index CRITERION_RUBRIC_FK on note.CRITERION (
RUBRIC_ID
);

/*==============================================================*/
/* Index: DERIVED_FROM_FK                                       */
/*==============================================================*/
create  index DERIVED_FROM_FK on note.CRITERION (
GENERIC_CRITERION_ID
);

/*==============================================================*/
/* Index: HAS_FK4                                               */
/*==============================================================*/
create  index HAS_FK4 on note.CRITERION (
LEVEL_AGGREGATE_ID
);

/*==============================================================*/
/* Table: note.DEFAULT_SCORE_GROUP                                   */
/*==============================================================*/
create table note.DEFAULT_SCORE_GROUP (
   EG_ID                INT4                 not null,
   SCORE_GROUP_ID       INT4                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_DEFAULT_SCORE_GROUP primary key (EG_ID, SCORE_GROUP_ID)
);

/*==============================================================*/
/* Index: DEFAULT_SCORE_GROUP_PK                                */
/*==============================================================*/
create unique index DEFAULT_SCORE_GROUP_PK on note.DEFAULT_SCORE_GROUP (
EG_ID,
SCORE_GROUP_ID
);

/*==============================================================*/
/* Index: DEFAULT_SCORE_GROUP_FK                                */
/*==============================================================*/
create  index DEFAULT_SCORE_GROUP_FK on note.DEFAULT_SCORE_GROUP (
EG_ID
);

/*==============================================================*/
/* Index: DEFAULT_SCORE_GROUP_FK2                               */
/*==============================================================*/
create  index DEFAULT_SCORE_GROUP_FK2 on note.DEFAULT_SCORE_GROUP (
SCORE_GROUP_ID
);

/*==============================================================*/
/* Table: note.EDUCATIONNAL_GOAL_TYPE                           */
/*==============================================================*/
CREATE TABLE note.educationnal_goal_type (
  EG_TYPE_ID            SERIAL               not null,
  LABEL                 TEXT                 not null,
  REGISTRATION          TIMESTAMP            not null default now(),
  CONSTRAINT PK_EG_GOAL_TYPE PRIMARY KEY (EG_TYPE_ID)
);

/*==============================================================*/
/* Table: note.EDUCATIONNAL_GOAL                                     */
/*==============================================================*/
create table note.EDUCATIONNAL_GOAL (
   EG_ID                SERIAL               not null,
   LABEL                TEXT                 not null,
   SHORT_DESCRIPTION    TEXT                 not null,
   DESCRIPTION          TEXT                 null,
   ADMINISTRATIVE_VALUE INT4                 null,
   VALIDITY_START       TIMESTAMP            not null,
   VALIDITY_END         TIMESTAMP            null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_EDUCATIONNAL_GOAL primary key (EG_ID)
);

/*==============================================================*/
/* Index: EDUCATIONNAL_GOAL_PK                                  */
/*==============================================================*/
create unique index EDUCATIONNAL_GOAL_PK on note.EDUCATIONNAL_GOAL (
EG_ID
);

/*==============================================================*/
/* Table: note.EDUCATIONNAL_GOAL_HIERARCHY                           */
/*==============================================================*/
create table note.EDUCATIONNAL_GOAL_HIERARCHY (
   EG_ID                INT4                 not null,
   EDU_EG_ID            INT4                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_EDUCATIONNAL_GOAL_HIERARCHY primary key (EG_ID, EDU_EG_ID)
);

/*==============================================================*/
/* Index: EDUCATIONNAL_GOAL_HIERARCHY_PK                        */
/*==============================================================*/
create unique index EDUCATIONNAL_GOAL_HIERARCHY_PK on note.EDUCATIONNAL_GOAL_HIERARCHY (
EG_ID,
EDU_EG_ID
);

/*==============================================================*/
/* Index: EDUCATIONNAL_GOAL_HIERARCHY_FK                        */
/*==============================================================*/
create  index EDUCATIONNAL_GOAL_HIERARCHY_FK on note.EDUCATIONNAL_GOAL_HIERARCHY (
EG_ID
);

/*==============================================================*/
/* Index: EDUCATIONNAL_GOAL_HIERARCHY2_FK                       */
/*==============================================================*/
create  index EDUCATIONNAL_GOAL_HIERARCHY2_FK on note.EDUCATIONNAL_GOAL_HIERARCHY (
EDU_EG_ID
);

/*==============================================================*/
/* Table: note.EDUCATIONNAL_GOAL_INSTANCE                            */
/*==============================================================*/
create table note.EDUCATIONNAL_GOAL_INSTANCE (
   EG_INSTANCE_ID		SERIAL 				 not null, 
   TIMESPAN_ID          INT4                 not null,
   EG_ID                INT4                 not null,
   VALIDITY_END         TIMESTAMP            null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_EDUCATIONNAL_GOAL_INSTANCE primary key (EG_INSTANCE_ID)
);

/*==============================================================*/
/* Index: EDUCATIONNAL_GOAL_INSTANCE_PK                         */
/*==============================================================*/
create unique index EDUCATIONNAL_GOAL_INSTANCE_PK on note.EDUCATIONNAL_GOAL_INSTANCE (
EG_INSTANCE_ID
);

/*==============================================================*/
/* Index: FOR_TIMESPAN_FK                                       */
/*==============================================================*/
create  index FOR_TIMESPAN_FK on note.EDUCATIONNAL_GOAL_INSTANCE (
TIMESPAN_ID
);

/*==============================================================*/
/* Index: FOR_EDUCATIONNAL_GOAL_FK                              */
/*==============================================================*/
create  index FOR_EDUCATIONNAL_GOAL_FK on note.EDUCATIONNAL_GOAL_INSTANCE (
EG_ID
);

/*==============================================================*/
/* Table: note.ENDORSEMENT                                           */
/*==============================================================*/
create table note.ENDORSEMENT (
   ENDORSER_ID          INT4                 not null,
   EG_INSTANCE_ID       INT4                 not null,
   STUDENT_ID           INT4                 not null,
   ENDORSEMENT_LEVEL_ID INT4                 not null,
   COMMENT              TEXT                 null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_ENDORSEMENT primary key (EG_INSTANCE_ID, ENDORSER_ID, STUDENT_ID, ENDORSEMENT_LEVEL_ID, REGISTRATION)
);

/*==============================================================*/
/* Index: ENDORSEMENT_PK                                        */
/*==============================================================*/
create unique index ENDORSEMENT_PK on note.ENDORSEMENT (
EG_INSTANCE_ID,
ENDORSER_ID,
STUDENT_ID,
ENDORSEMENT_LEVEL_ID,
REGISTRATION
);

/*==============================================================*/
/* Index: ENDORSED_BY_FK                                        */
/*==============================================================*/
create  index ENDORSED_BY_FK on note.ENDORSEMENT (
EG_INSTANCE_ID,
STUDENT_ID
);

/*==============================================================*/
/* Index: GIVE_FK                                               */
/*==============================================================*/
create  index GIVE_FK on note.ENDORSEMENT (
ENDORSER_ID
);

/*==============================================================*/
/* Index: LINKED_TO_FK3                                         */
/*==============================================================*/
create  index LINKED_TO_FK3 on note.ENDORSEMENT (
ENDORSEMENT_LEVEL_ID
);

/*==============================================================*/
/* Table: note.ENDORSEMENT_LEVEL                                     */
/*==============================================================*/
create table note.ENDORSEMENT_LEVEL (
   ENDORSEMENT_LEVEL_ID SERIAL               not null,
   LABEL                TEXT                 not null,
   DESCRIPTION          TEXT                 null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_ENDORSEMENT_LEVEL primary key (ENDORSEMENT_LEVEL_ID)
);

/*==============================================================*/
/* Index: ENDORSEMENT_LEVEL_PK                                  */
/*==============================================================*/
create unique index ENDORSEMENT_LEVEL_PK on note.ENDORSEMENT_LEVEL (
ENDORSEMENT_LEVEL_ID
);

/*==============================================================*/
/* Table: note.EVALUATED_GROUP                                       */
/*==============================================================*/
create table note.EVALUATED_GROUP (
   EVALUATION_INSTANCE_ID INT4                 not null,
   GROUP_ID             INT4                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_EVALUATED_GROUP primary key (EVALUATION_INSTANCE_ID, GROUP_ID)
);

/*==============================================================*/
/* Index: EVALUATED_GROUP_PK                                    */
/*==============================================================*/
create unique index EVALUATED_GROUP_PK on note.EVALUATED_GROUP (
EVALUATION_INSTANCE_ID,
GROUP_ID
);

/*==============================================================*/
/* Index: EVALUATED_GROUP_FK                                    */
/*==============================================================*/
create  index EVALUATED_GROUP_FK on note.EVALUATED_GROUP (
EVALUATION_INSTANCE_ID
);

/*==============================================================*/
/* Index: EVALUATED_GROUP_FK2                                   */
/*==============================================================*/
create  index EVALUATED_GROUP_FK2 on note.EVALUATED_GROUP (
GROUP_ID
);

/*==============================================================*/
/* Table: note.EVALUATION                                            */
/*==============================================================*/
create table note.EVALUATION (
   EVALUATION_ID        SERIAL               not null,
   EVALUATION_TYPE_ID   INT4                 null,
   EG_ID                INT4                 null,
   LABEL                TEXT                 not null,
   SHORT_DESCRIPTION 	TEXT 				 null,
   VALIDITY_START       TIMESTAMP            not null,
   VALIDITY_END         TIMESTAMP            null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_EVALUATION primary key (EVALUATION_ID)
);

/*==============================================================*/
/* Index: EVALUATION_PK                                         */
/*==============================================================*/
create unique index EVALUATION_PK on note.EVALUATION (
EVALUATION_ID
);

/*==============================================================*/
/* Index: OF_TYPE_FK                                            */
/*==============================================================*/
create  index OF_TYPE_FK on note.EVALUATION (
EVALUATION_TYPE_ID
);

/*==============================================================*/
/* Index: LINKED_TO_FK                                          */
/*==============================================================*/
create  index LINKED_TO_FK on note.EVALUATION (
EG_ID
);

/*==============================================================*/
/* Table: note.EVALUATION_INSTANCE                                   */
/*==============================================================*/
create table note.EVALUATION_INSTANCE (
   EVALUATION_INSTANCE_ID SERIAL               not null,
   EVALUATION_ID        INT4                 null,
   EG_INSTANCE_ID       INT4                 null,
   EMPLOYEE_ID          INT4                 not null,
   OCCURENCE            DATE                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_EVALUATION_INSTANCE primary key (EVALUATION_INSTANCE_ID)
);

/*==============================================================*/
/* Index: EVALUATION_INSTANCE_PK                                */
/*==============================================================*/
create unique index EVALUATION_INSTANCE_PK on note.EVALUATION_INSTANCE (
EVALUATION_INSTANCE_ID
);

/*==============================================================*/
/* Index: LINKED_TO_FK2                                         */
/*==============================================================*/
create  index LINKED_TO_FK2 on note.EVALUATION_INSTANCE (
EVALUATION_ID
);

/*==============================================================*/
/* Index: IN_CHARGE_OF_FK                                       */
/*==============================================================*/
create  index IN_CHARGE_OF_FK on note.EVALUATION_INSTANCE (
EMPLOYEE_ID
);

/*==============================================================*/
/* Index: FOR_COURSE_FK                                         */
/*==============================================================*/
create  index FOR_COURSE_FK on note.EVALUATION_INSTANCE (
EG_INSTANCE_ID
);

/*==============================================================*/
/* Table: note.EVALUATION_RUBRIC                                     */
/*==============================================================*/
create table note.EVALUATION_RUBRIC (
   EVALUATION_ID        INT4                 not null,
   RUBRIC_ID            INT4                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_EVALUATION_RUBRIC primary key (EVALUATION_ID, RUBRIC_ID)
);

/*==============================================================*/
/* Index: EVALUATION_RUBRIC_PK                                  */
/*==============================================================*/
create unique index EVALUATION_RUBRIC_PK on note.EVALUATION_RUBRIC (
EVALUATION_ID,
RUBRIC_ID
);

/*==============================================================*/
/* Index: EVALUATION_RUBRIC_FK                                  */
/*==============================================================*/
create  index EVALUATION_RUBRIC_FK on note.EVALUATION_RUBRIC (
EVALUATION_ID
);

/*==============================================================*/
/* Index: EVALUATION_RUBRIC_FK2                                 */
/*==============================================================*/
create  index EVALUATION_RUBRIC_FK2 on note.EVALUATION_RUBRIC (
RUBRIC_ID
);

/*==============================================================*/
/* Table: note.EVALUATION_TYPE                                       */
/*==============================================================*/
create table note.EVALUATION_TYPE (
   EVALUATION_TYPE_ID   SERIAL               not null,
   LABEL                TEXT                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_EVALUATION_TYPE primary key (EVALUATION_TYPE_ID)
);

/*==============================================================*/
/* Index: EVALUATION_TYPE_PK                                    */
/*==============================================================*/
create unique index EVALUATION_TYPE_PK on note.EVALUATION_TYPE (
EVALUATION_TYPE_ID
);

/*==============================================================*/
/* Table: note.GENERIC_CRITERION                                     */
/*==============================================================*/
create table note.GENERIC_CRITERION (
   GENERIC_CRITERION_ID SERIAL               not null,
   GENERIC_LEVEL_AGGREGATE_ID   INT4                 not null,
   QUALITY_ID           INT4                 not null,
   PROGRAM_ID           INT4                 not null,
   LABEL                TEXT                 not null,
   DESCRIPTION          TEXT                 null,
   VALIDITY_START       TIMESTAMP            not null,
   VALIDITY_END         TIMESTAMP            null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_GENERIC_CRITERION primary key (GENERIC_CRITERION_ID)
);

/*==============================================================*/
/* Index: GENERIC_CRITERION_PK                                  */
/*==============================================================*/
create unique index GENERIC_CRITERION_PK on note.GENERIC_CRITERION (
GENERIC_CRITERION_ID
);

/*==============================================================*/
/* Index: ASSESSMENT_CRITERIA_FK                                */
/*==============================================================*/
create  index ASSESSMENT_CRITERIA_FK on note.GENERIC_CRITERION (
QUALITY_ID
);

/*==============================================================*/
/* Index: HAS_FK                                                */
/*==============================================================*/
create  index HAS_FK on note.GENERIC_CRITERION (
GENERIC_LEVEL_AGGREGATE_ID
);

/*==============================================================*/
/* Index: RELATIVE_TO_FK                                        */
/*==============================================================*/
create  index RELATIVE_TO_FK on note.GENERIC_CRITERION (
PROGRAM_ID
);

/*==============================================================*/
/* Table: note.GENERIC_INDICATOR                                     */
/*==============================================================*/
create table note.GENERIC_INDICATOR (
   GENERIC_INDICATOR_ID SERIAL               not null,
   GENERIC_CRITERION_ID INT4                 not null,
   INDICATOR_TYPE_ID    INT4                 not null,
   GENERIC_LEVEL_AGGREGATE_ID   INT4                 not null,
   GENERIC_LEVEL_ID             INT4                 not null,
   DESCRIPTION          TEXT                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_GENERIC_INDICATOR primary key (GENERIC_INDICATOR_ID)
);

/*==============================================================*/
/* Index: GENERIC_INDICATOR_PK                                  */
/*==============================================================*/
create unique index GENERIC_INDICATOR_PK on note.GENERIC_INDICATOR (
GENERIC_INDICATOR_ID
);

/*==============================================================*/
/* Index: HAS_FK5                                               */
/*==============================================================*/
create  index HAS_FK5 on note.GENERIC_INDICATOR (
INDICATOR_TYPE_ID
);

/*==============================================================*/
/* Index: RELATED_TO_FK3                                        */
/*==============================================================*/
create  index RELATED_TO_FK3 on note.GENERIC_INDICATOR (
GENERIC_CRITERION_ID
);

/*==============================================================*/
/* Index: RELATED_TO_FK4                                        */
/*==============================================================*/
create  index RELATED_TO_FK4 on note.GENERIC_INDICATOR (
GENERIC_LEVEL_AGGREGATE_ID,
GENERIC_LEVEL_ID
);

/*==============================================================*/
/* Table: note.GENERIC_LEVEL                                         */
/*==============================================================*/
create table note.GENERIC_LEVEL (
   GENERIC_LEVEL_ID             SERIAL               not null,
   PROGRAM_ID           INT4                 null,
   LABEL                TEXT                 not null,
   DESCRIPTION          TEXT                 null,
   VALIDITY_START       TIMESTAMP            not null,
   VALIDITY_END         TIMESTAMP            null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_GENERIC_LEVEL primary key (GENERIC_LEVEL_ID)
);

/*==============================================================*/
/* Index: GENERIC_LEVEL_PK                                      */
/*==============================================================*/
create unique index GENERIC_LEVEL_PK on note.GENERIC_LEVEL (
GENERIC_LEVEL_ID
);

/*==============================================================*/
/* Index: RELATIVE_TO_FK3                                       */
/*==============================================================*/
create  index RELATIVE_TO_FK3 on note.GENERIC_LEVEL (
PROGRAM_ID
);

/*==============================================================*/
/* Table: note.GENERIC_LEVEL_AGGREGATE                               */
/*==============================================================*/
create table note.GENERIC_LEVEL_AGGREGATE (
   GENERIC_LEVEL_AGGREGATE_ID   SERIAL               not null,
   PROGRAM_ID           INT4                 null,
   LABEL                TEXT                 not null,
   DESCRIPTION          TEXT                 null,
   VALIDITY_START       TIMESTAMP            not null,
   VALIDITY_END         TIMESTAMP            null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_GENERIC_LEVEL_AGGREGATE primary key (GENERIC_LEVEL_AGGREGATE_ID)
);

/*==============================================================*/
/* Index: GENERIC_LEVEL_AGGREGATE_PK                            */
/*==============================================================*/
create unique index GENERIC_LEVEL_AGGREGATE_PK on note.GENERIC_LEVEL_AGGREGATE (
GENERIC_LEVEL_AGGREGATE_ID
);

/*==============================================================*/
/* Index: RELATIVE_TO_FK2                                       */
/*==============================================================*/
create  index RELATIVE_TO_FK2 on note.GENERIC_LEVEL_AGGREGATE (
PROGRAM_ID
);

/*==============================================================*/
/* Table: note.GENERIC_LEVEL_RATIO                                   */
/*==============================================================*/
create table note.GENERIC_LEVEL_RATIO (
   GENERIC_LEVEL_AGGREGATE_ID   INT4                 not null,
   GENERIC_LEVEL_ID             INT4                 not null,
   GENERIC_CRITERION_ID INT4                 not null,
   WEIGTH_RATIO         FLOAT8               null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_GENERIC_LEVEL_RATIO primary key (GENERIC_LEVEL_AGGREGATE_ID, GENERIC_LEVEL_ID, GENERIC_CRITERION_ID)
);

/*==============================================================*/
/* Index: GENERIC_LEVEL_RATIO_PK                                */
/*==============================================================*/
create unique index GENERIC_LEVEL_RATIO_PK on note.GENERIC_LEVEL_RATIO (
GENERIC_LEVEL_AGGREGATE_ID,
GENERIC_LEVEL_ID,
GENERIC_CRITERION_ID
);

/*==============================================================*/
/* Index: GENERIC_LEVEL_RATIO_FK                                */
/*==============================================================*/
create  index GENERIC_LEVEL_RATIO_FK on note.GENERIC_LEVEL_RATIO (
GENERIC_LEVEL_AGGREGATE_ID,
GENERIC_LEVEL_ID
);

/*==============================================================*/
/* Index: GENERIC_LEVEL_RATIO_FK2                               */
/*==============================================================*/
create  index GENERIC_LEVEL_RATIO_FK2 on note.GENERIC_LEVEL_RATIO (
GENERIC_CRITERION_ID
);

/*==============================================================*/
/* Table: note.GEN_LEVEL_ORDER_IN_AGGREGATE                          */
/*==============================================================*/
create table note.GEN_LEVEL_ORDER_IN_AGGREGATE (
   GENERIC_LEVEL_AGGREGATE_ID   INT4                 not null,
   GENERIC_LEVEL_ID             INT4                 not null,
   PLACE                INT2                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_GEN_LEVEL_ORDER_IN_AGGREGAT primary key (GENERIC_LEVEL_AGGREGATE_ID, GENERIC_LEVEL_ID)
);

/*==============================================================*/
/* Index: GEN_LEVEL_ORDER_IN_AGGREGATE_PK                       */
/*==============================================================*/
create unique index GEN_LEVEL_ORDER_IN_AGGREGATE_PK on note.GEN_LEVEL_ORDER_IN_AGGREGATE (
GENERIC_LEVEL_AGGREGATE_ID,
GENERIC_LEVEL_ID
);

/*==============================================================*/
/* Index: ORDERING_FK2                                          */
/*==============================================================*/
create  index ORDERING_FK2 on note.GEN_LEVEL_ORDER_IN_AGGREGATE (
GENERIC_LEVEL_AGGREGATE_ID
);

/*==============================================================*/
/* Index: ORDERED_BY_FK2                                        */
/*==============================================================*/
create  index ORDERED_BY_FK2 on note.GEN_LEVEL_ORDER_IN_AGGREGATE (
GENERIC_LEVEL_ID
);

/*==============================================================*/
/* Table: note.GROUP_ENDORSEMENT_LEVEL                               */
/*==============================================================*/
create table note.GROUP_ENDORSEMENT_LEVEL (
   ENDORSER_ID             INT4                 not null,
   ENDORSEMENT_LEVEL_ID INT4                 not null,
   GROUP_ID             INT4                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_GROUP_ENDORSEMENT_LEVEL primary key (ENDORSER_ID, ENDORSEMENT_LEVEL_ID, GROUP_ID)
);

/*==============================================================*/
/* Index: GROUP_ENDORSEMENT_LEVEL_PK                            */
/*==============================================================*/
create unique index GROUP_ENDORSEMENT_LEVEL_PK on note.GROUP_ENDORSEMENT_LEVEL (
ENDORSER_ID,
ENDORSEMENT_LEVEL_ID,
GROUP_ID
);

/*==============================================================*/
/* Index: GROUP_ENDORSEMENT_LEVEL_FK                            */
/*==============================================================*/
create  index GROUP_ENDORSEMENT_LEVEL_FK on note.GROUP_ENDORSEMENT_LEVEL (
ENDORSEMENT_LEVEL_ID
);

/*==============================================================*/
/* Index: GROUP_ENDORSEMENT_LEVEL_FK2                           */
/*==============================================================*/
create  index GROUP_ENDORSEMENT_LEVEL_FK2 on note.GROUP_ENDORSEMENT_LEVEL (
GROUP_ID
);

/*==============================================================*/
/* Index: GROUP_ENDORSEMENT_LEVEL_FK3                           */
/*==============================================================*/
create  index GROUP_ENDORSEMENT_LEVEL_FK3 on note.GROUP_ENDORSEMENT_LEVEL (
ENDORSER_ID
);

/*==============================================================*/
/* Table: note.INDICATOR                                             */
/*==============================================================*/
create table note.INDICATOR (
   INDICATOR_ID SERIAL               not null,
   INDICATOR_TYPE_ID    INT4                 not null,
   CRITERION_ID         INT4                 not null,
   LEVEL_AGGREGATE_ID   INT4                 not null,
   LEVEL_ID             INT4                 not null,
   DESCRIPTION          TEXT                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_INDICATOR primary key (INDICATOR_ID)
);

/*==============================================================*/
/* Index: INDICATOR_PK                                          */
/*==============================================================*/
create unique index INDICATOR_PK on note.INDICATOR (
INDICATOR_ID
);

/*==============================================================*/
/* Index: HAS_FK2                                               */
/*==============================================================*/
create  index HAS_FK2 on note.INDICATOR (
INDICATOR_TYPE_ID
);

/*==============================================================*/
/* Index: RELATED_TO_FK                                         */
/*==============================================================*/
create  index RELATED_TO_FK on note.INDICATOR (
CRITERION_ID
);

/*==============================================================*/
/* Index: RELATED_TO_FK2                                        */
/*==============================================================*/
create  index RELATED_TO_FK2 on note.INDICATOR (
LEVEL_AGGREGATE_ID,
LEVEL_ID
);

/*==============================================================*/
/* Table: note.INDICATOR_TYPE                                        */
/*==============================================================*/
create table note.INDICATOR_TYPE (
   INDICATOR_TYPE_ID    SERIAL               not null,
   LABEL                TEXT                 not null,
   DESCRIPTION          TEXT                 null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_INDICATOR_TYPE primary key (INDICATOR_TYPE_ID)
);

/*==============================================================*/
/* Index: INDICATOR_TYPE_PK                                     */
/*==============================================================*/
create unique index INDICATOR_TYPE_PK on note.INDICATOR_TYPE (
INDICATOR_TYPE_ID
);

/*==============================================================*/
/* Table: note.LEVEL                                                 */
/*==============================================================*/
create table note.LEVEL (
   LEVEL_ID             SERIAL               not null,
   LABEL                TEXT                 not null,
   DESCRIPTION          TEXT                 null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_LEVEL primary key (LEVEL_ID)
);

/*==============================================================*/
/* Index: LEVEL_PK                                              */
/*==============================================================*/
create unique index LEVEL_PK on note.LEVEL (
LEVEL_ID
);

/*==============================================================*/
/* Table: note.LEVEL_AGGREGATE                                       */
/*==============================================================*/
create table note.LEVEL_AGGREGATE (
   LEVEL_AGGREGATE_ID   SERIAL               not null,
   LABEL                TEXT                 not null,
   DESCRIPTION          TEXT                 null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_LEVEL_AGGREGATE primary key (LEVEL_AGGREGATE_ID)
);

/*==============================================================*/
/* Index: LEVEL_AGGREGATE_PK                                    */
/*==============================================================*/
create unique index LEVEL_AGGREGATE_PK on note.LEVEL_AGGREGATE (
LEVEL_AGGREGATE_ID
);

/*==============================================================*/
/* Table: note.LEVEL_ORDER_IN_AGGREGATE                              */
/*==============================================================*/
create table note.LEVEL_ORDER_IN_AGGREGATE (
   LEVEL_AGGREGATE_ID   INT4                 not null,
   LEVEL_ID             INT4                 not null,
   PLACE                INT2                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_LEVEL_ORDER_IN_AGGREGATE primary key (LEVEL_AGGREGATE_ID, LEVEL_ID)
);

/*==============================================================*/
/* Index: LEVEL_ORDER_IN_AGGREGATE_PK                           */
/*==============================================================*/
create unique index LEVEL_ORDER_IN_AGGREGATE_PK on note.LEVEL_ORDER_IN_AGGREGATE (
LEVEL_AGGREGATE_ID,
LEVEL_ID
);

/*==============================================================*/
/* Index: ORDERING_FK                                           */
/*==============================================================*/
create  index ORDERING_FK on note.LEVEL_ORDER_IN_AGGREGATE (
LEVEL_AGGREGATE_ID
);

/*==============================================================*/
/* Index: ORDERED_BY_FK                                         */
/*==============================================================*/
create  index ORDERED_BY_FK on note.LEVEL_ORDER_IN_AGGREGATE (
LEVEL_ID
);

/*==============================================================*/
/* Table: note.LEVEL_RATIO                                           */
/*==============================================================*/
create table note.LEVEL_RATIO (
   LEVEL_AGGREGATE_ID   INT4                 not null,
   LEVEL_ID             INT4                 not null,
   CRITERION_ID         INT4                 not null,
   WEIGTH_RATIO         FLOAT8               null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_LEVEL_RATIO primary key (LEVEL_AGGREGATE_ID, LEVEL_ID, CRITERION_ID)
);

/*==============================================================*/
/* Index: LEVEL_RATIO_PK                                        */
/*==============================================================*/
create unique index LEVEL_RATIO_PK on note.LEVEL_RATIO (
LEVEL_AGGREGATE_ID,
LEVEL_ID,
CRITERION_ID
);

/*==============================================================*/
/* Index: LEVEL_RATIO_FK                                        */
/*==============================================================*/
create  index LEVEL_RATIO_FK on note.LEVEL_RATIO (
CRITERION_ID
);

/*==============================================================*/
/* Index: LEVEL_RATIO_FK2                                       */
/*==============================================================*/
create  index LEVEL_RATIO_FK2 on note.LEVEL_RATIO (
LEVEL_AGGREGATE_ID,
LEVEL_ID
);

/*==============================================================*/
/* Table: note.POSSIBLE_SCORE                                        */
/*==============================================================*/
create table note.POSSIBLE_SCORE (
   SCORE_ID             CHAR(2)              not null,
   DESCRIPTION          TEXT                 null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_POSSIBLE_SCORE primary key (SCORE_ID)
);

/*==============================================================*/
/* Index: POSSIBLE_SCORE_PK                                     */
/*==============================================================*/
create unique index POSSIBLE_SCORE_PK on note.POSSIBLE_SCORE (
SCORE_ID
);

/*==============================================================*/
/* Table: note.PROGRAM_EDUCATIONNAL_GOAL                             */
/*==============================================================*/
create table note.PROGRAM_EDUCATIONNAL_GOAL (
   PROGRAM_ID           INT4                 not null,
   EG_ID                INT4                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_PROGRAM_EDUCATIONNAL_GOAL primary key (PROGRAM_ID, EG_ID)
);

/*==============================================================*/
/* Index: PROGRAM_EDUCATIONNAL_GOAL_PK                          */
/*==============================================================*/
create unique index PROGRAM_EDUCATIONNAL_GOAL_PK on note.PROGRAM_EDUCATIONNAL_GOAL (
PROGRAM_ID,
EG_ID
);

/*==============================================================*/
/* Index: PROGRAM_EDUCATIONNAL_GOAL_FK                          */
/*==============================================================*/
create  index PROGRAM_EDUCATIONNAL_GOAL_FK on note.PROGRAM_EDUCATIONNAL_GOAL (
PROGRAM_ID
);

/*==============================================================*/
/* Index: PROGRAM_EDUCATIONNAL_GOAL_FK2                         */
/*==============================================================*/
create  index PROGRAM_EDUCATIONNAL_GOAL_FK2 on note.PROGRAM_EDUCATIONNAL_GOAL (
EG_ID
);

/*==============================================================*/
/* Table: note.PROGRAM_QUALITY                                       */
/*==============================================================*/
create table note.PROGRAM_QUALITY (
   QUALITY_ID           INT4                 not null,
   PROGRAM_ID           INT4                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_PROGRAM_QUALITY primary key (QUALITY_ID, PROGRAM_ID)
);

/*==============================================================*/
/* Index: PROGRAM_QUALITY_PK                                    */
/*==============================================================*/
create unique index PROGRAM_QUALITY_PK on note.PROGRAM_QUALITY (
QUALITY_ID,
PROGRAM_ID
);

/*==============================================================*/
/* Index: PROGRAM_QUALITY_FK                                    */
/*==============================================================*/
create  index PROGRAM_QUALITY_FK on note.PROGRAM_QUALITY (
QUALITY_ID
);

/*==============================================================*/
/* Index: PROGRAM_QUALITY_FK2                                   */
/*==============================================================*/
create  index PROGRAM_QUALITY_FK2 on note.PROGRAM_QUALITY (
PROGRAM_ID
);

/*==============================================================*/
/* Table: note.QUALITY                                               */
/*==============================================================*/
create table note.QUALITY (
   QUALITY_ID           SERIAL               not null,
   LABEL                TEXT                 not null,
   DESCRIPTION          TEXT                 not null,
   VALIDITY_START       TIMESTAMP            not null,
   VALIDITY_END         TIMESTAMP            null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_QUALITY primary key (QUALITY_ID)
);

/*==============================================================*/
/* Index: QUALITY_PK                                            */
/*==============================================================*/
create unique index QUALITY_PK on note.QUALITY (
QUALITY_ID
);

/*==============================================================*/
/* Table: note.RESULT                                                */
/*==============================================================*/
create table note.RESULT (
   CRITERION_ID         INT4                 not null,
   EVALUATION_INSTANCE_ID INT4               not null,
   STUDENT_ID           INT4                 not null,
   GRADER_ID            INT4                 null,
   VALUE                FLOAT8               not null,
   COMMENT              TEXT                 null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_RESULT primary key (STUDENT_ID, CRITERION_ID, EVALUATION_INSTANCE_ID, REGISTRATION)
);

/*==============================================================*/
/* Index: RESULT_PK                                             */
/*==============================================================*/
create unique index RESULT_PK on note.RESULT (
STUDENT_ID,
CRITERION_ID,
EVALUATION_INSTANCE_ID,
REGISTRATION
);

/*==============================================================*/
/* Index: INSERTED_BY_FK                                        */
/*==============================================================*/
create  index INSERTED_BY_FK on note.RESULT (
GRADER_ID
);

/*==============================================================*/
/* Index: RELATING_TO_FK                                        */
/*==============================================================*/
create  index RELATING_TO_FK on note.RESULT (
CRITERION_ID
);

/*==============================================================*/
/* Index: FOR_FK                                                */
/*==============================================================*/
create  index FOR_FK on note.RESULT (
EVALUATION_INSTANCE_ID
);

/*==============================================================*/
/* Index: RECIPIENT_FK                                          */
/*==============================================================*/
create  index RECIPIENT_FK on note.RESULT (
STUDENT_ID
);

/*==============================================================*/
/* Table: note.RUBRIC                                                */
/*==============================================================*/
create table note.RUBRIC (
   RUBRIC_ID            SERIAL               not null,
   LABEL                TEXT                 not null,
   STATEMENT            TEXT                 not null,
   PLACE                INT2                 null,
   VALIDITY_START       TIMESTAMP            not null,
   VALIDITY_END         TIMESTAMP            null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_RUBRIC primary key (RUBRIC_ID)
);

/*==============================================================*/
/* Index: RUBRIC_PK                                             */
/*==============================================================*/
create unique index RUBRIC_PK on note.RUBRIC (
RUBRIC_ID
);

/*==============================================================*/
/* Table: note.SCORE_GROUP                                           */
/*==============================================================*/
create table note.SCORE_GROUP (
   SCORE_GROUP_ID       SERIAL               not null,
   LABEL                TEXT                 not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_SCORE_GROUP primary key (SCORE_GROUP_ID)
);

/*==============================================================*/
/* Index: SCORE_GROUP_PK                                        */
/*==============================================================*/
create unique index SCORE_GROUP_PK on note.SCORE_GROUP (
SCORE_GROUP_ID
);

/*==============================================================*/
/* Table: note.SCORE_GROUP_DEFINITION                                */
/*==============================================================*/
create table note.SCORE_GROUP_DEFINITION (
   SCORE_GROUP_ID       INT4                 not null,
   SCORE_ID             CHAR(2)              not null,
   DEFAULT_VALUE        FLOAT8               null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_SCORE_GROUP_DEFINITION primary key (SCORE_GROUP_ID, SCORE_ID)
);

/*==============================================================*/
/* Index: SCORE_GROUP_DEFINITION_PK                             */
/*==============================================================*/
create unique index SCORE_GROUP_DEFINITION_PK on note.SCORE_GROUP_DEFINITION (
SCORE_GROUP_ID,
SCORE_ID
);

/*==============================================================*/
/* Index: HAS_FK3                                               */
/*==============================================================*/
create  index HAS_FK3 on note.SCORE_GROUP_DEFINITION (
SCORE_GROUP_ID
);

/*==============================================================*/
/* Index: GROUPING_FK                                           */
/*==============================================================*/
create  index GROUPING_FK on note.SCORE_GROUP_DEFINITION (
SCORE_ID
);

/*==============================================================*/
/* Table: note.SCORE_GROUP_INSTANCE                                  */
/*==============================================================*/
create table note.SCORE_GROUP_INSTANCE (
   SCORE_GROUP_INSTANCE_ID SERIAL            not null,
   SCORE_GROUP_ID       INT4                 not null,
   EG_INSTANCE_ID       INT4                 null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_SCORE_GROUP_INSTANCE primary key (SCORE_GROUP_INSTANCE_ID)
);

/*==============================================================*/
/* Index: SCORE_GROUP_INSTANCE_PK                               */
/*==============================================================*/
create unique index SCORE_GROUP_INSTANCE_PK on note.SCORE_GROUP_INSTANCE (
SCORE_GROUP_INSTANCE_ID
);

/*==============================================================*/
/* Index: LINKED_TO_INSTANCE_FK                                 */
/*==============================================================*/
create  index LINKED_TO_INSTANCE_FK on note.SCORE_GROUP_INSTANCE (
SCORE_GROUP_ID
);

/*==============================================================*/
/* Index: FOR_COURSE_INSTANCE_FK                                */
/*==============================================================*/
create  index FOR_COURSE_INSTANCE_FK on note.SCORE_GROUP_INSTANCE (
EG_INSTANCE_ID
);

/*==============================================================*/
/* Table: note.SCORE_THRESHOLD_DEFINITION                            */
/*==============================================================*/
create table note.SCORE_THRESHOLD_DEFINITION (
   SCORE_GROUP_ID       INT4                 not null,
   SCORE_ID             CHAR(2)              not null,
   SCORE_GROUP_INSTANCE_ID INT4              not null,
   THRESHOLD            FLOAT8               not null,
   COMMENT              TEXT                 null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_SCORE_THRESHOLD_DEFINITION primary key (SCORE_GROUP_ID, SCORE_ID, SCORE_GROUP_INSTANCE_ID, REGISTRATION)
);

/*==============================================================*/
/* Index: SCORE_THRESHOLD_DEFINITION_PK                         */
/*==============================================================*/
create unique index SCORE_THRESHOLD_DEFINITION_PK on note.SCORE_THRESHOLD_DEFINITION (
SCORE_GROUP_ID,
SCORE_ID,
SCORE_GROUP_INSTANCE_ID,
REGISTRATION
);

/*==============================================================*/
/* Index: SCORE_THRESHOLD_DEFINITION_FK                         */
/*==============================================================*/
create  index SCORE_THRESHOLD_DEFINITION_FK on note.SCORE_THRESHOLD_DEFINITION (
SCORE_GROUP_INSTANCE_ID
);

/*==============================================================*/
/* Index: SCORE_THRESHOLD_DEFINITION_FK2                        */
/*==============================================================*/
create  index SCORE_THRESHOLD_DEFINITION_FK2 on note.SCORE_THRESHOLD_DEFINITION (
SCORE_GROUP_ID,
SCORE_ID
);

/*==============================================================*/
/* Index: SCORE_THRESHOLD_DEFINITION_FK3                        */
/*==============================================================*/
create  index SCORE_THRESHOLD_DEFINITION_FK3 on note.SCORE_THRESHOLD_DEFINITION (
REGISTRATION
);

/*==============================================================*/
/* Table: note.STUDENT_SCORE                                         */
/*==============================================================*/
create table note.STUDENT_SCORE (
   EG_INSTANCE_ID       INT4                 not null,
   STUDENT_ID           INT4                 not null,
   SCORE_ID             CHAR(2)              not null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_STUDENT_SCORE primary key (EG_INSTANCE_ID, STUDENT_ID)
);

/*==============================================================*/
/* Index: STUDENT_SCORE_PK                                      */
/*==============================================================*/
create unique index STUDENT_SCORE_PK on note.STUDENT_SCORE (
EG_INSTANCE_ID,
STUDENT_ID
);

/*==============================================================*/
/* Index: HAS_SCORE_FK                                          */
/*==============================================================*/
create  index HAS_SCORE_FK on note.STUDENT_SCORE (
STUDENT_ID
);

/*==============================================================*/
/* Index: IN_FK                                                 */
/*==============================================================*/
create  index IN_FK on note.STUDENT_SCORE (
SCORE_ID
);

/*==============================================================*/
/* Index: FOR_COURSE_FK2                                        */
/*==============================================================*/
create  index FOR_COURSE_FK2 on note.STUDENT_SCORE (
EG_INSTANCE_ID
);

/*==============================================================*/
/* Table: note.TRANSFORM                                             */
/*==============================================================*/
create table note.TRANSFORM (
   QUALITY_ID           INT4                 not null,
   EG_ID                INT4                 not null,
   COEFFICIENT          FLOAT8               not null,
   VALIDITY_START       TIMESTAMP            not null,
   VALIDITY_END         TIMESTAMP            null,
   REGISTRATION         TIMESTAMP            not null default now(),
   constraint PK_TRANSFORM primary key (QUALITY_ID, EG_ID)
);

/*==============================================================*/
/* Index: TRANSFORM_PK                                          */
/*==============================================================*/
create unique index TRANSFORM_PK on note.TRANSFORM (
QUALITY_ID,
EG_ID
);

/*==============================================================*/
/* Index: TRANSFORM_FK                                          */
/*==============================================================*/
create  index TRANSFORM_FK on note.TRANSFORM (
QUALITY_ID
);

/*==============================================================*/
/* Index: TRANSFORM_FK2                                         */
/*==============================================================*/
create  index TRANSFORM_FK2 on note.TRANSFORM (
EG_ID
);

alter table note.ADMINISTRATIVE_GROUP
   add constraint FK_ADMINIST_ADMINISTR_ADMINIST foreign key (PROGRAM_ID)
      references note.ADMINISTRATIVE_ELEMENT (PROGRAM_ID)
      on delete restrict on update restrict;

alter table note.ADMINISTRATIVE_GROUP
   add constraint FK_ADMINIST_ADMINISTR_GROUPS foreign key (GROUP_ID)
      references public.GROUPS (GROUP_ID)
      on delete restrict on update restrict;

alter table note.ADMINISTRATIVE_HIERARCHY
   add constraint FK_ADMINIST_ADMINISTR_ADMINIST foreign key (ADM_PROGRAM_ID)
      references note.ADMINISTRATIVE_ELEMENT (PROGRAM_ID)
      on delete restrict on update restrict;

alter table note.ADMINISTRATIVE_HIERARCHY
   add constraint FK_ADMINIST_ADMINISTR_ADMINIST2 foreign key (PROGRAM_ID)
      references note.ADMINISTRATIVE_ELEMENT (PROGRAM_ID)
      on delete restrict on update restrict;

alter table note.ASSIGNED_GROUP
   add constraint FK_ASSIGNED_ASSIGNED__EDUCATIO foreign key (EG_INSTANCE_ID)
      references note.EDUCATIONNAL_GOAL_INSTANCE (EG_INSTANCE_ID)
      on delete restrict on update restrict;

alter table note.ASSIGNED_GROUP
   add constraint FK_ASSIGNED_ASSIGNED__GROUPS foreign key (GROUP_ID)
      references public.GROUPS (GROUP_ID)
      on delete restrict on update restrict;

alter table note.ASSIGNED_GROUP
   add constraint FK_ASSIGNED_ASSIGNED__PRIVILEGE foreign key (PRIVILEGE_ID)
      references public.PRIVILEGE (PRIVILEGE_ID)
      on delete restrict on update restrict;

alter table note.CRITERION
   add constraint FK_CRITERIO_CRITERION_RUBRIC foreign key (RUBRIC_ID)
      references note.RUBRIC (RUBRIC_ID)
      on delete restrict on update restrict;

alter table note.CRITERION
   add constraint FK_CRITERIO_DERIVED_F_GENERIC_ foreign key (GENERIC_CRITERION_ID)
      references note.GENERIC_CRITERION (GENERIC_CRITERION_ID)
      on delete restrict on update restrict;

alter table note.CRITERION
   add constraint FK_CRITERIO_GOAL_CRIT_EDUCATIO foreign key (EG_ID)
      references note.EDUCATIONNAL_GOAL (EG_ID)
      on delete restrict on update restrict;

alter table note.CRITERION
   add constraint FK_CRITERIO_HAS_LEVEL_AG foreign key (LEVEL_AGGREGATE_ID)
      references note.LEVEL_AGGREGATE (LEVEL_AGGREGATE_ID)
      on delete restrict on update restrict;

alter table note.DEFAULT_SCORE_GROUP
   add constraint FK_DEFAULT__DEFAULT_S_EDUCATIO foreign key (EG_ID)
      references note.EDUCATIONNAL_GOAL (EG_ID)
      on delete restrict on update restrict;

alter table note.DEFAULT_SCORE_GROUP
   add constraint FK_DEFAULT__DEFAULT_S_SCORE_GR foreign key (SCORE_GROUP_ID)
      references note.SCORE_GROUP (SCORE_GROUP_ID)
      on delete restrict on update restrict;

alter table note.EDUCATIONNAL_GOAL_HIERARCHY
   add constraint FK_EDUCATIO_EDUCATION_EDUCATIO foreign key (EG_ID)
      references note.EDUCATIONNAL_GOAL (EG_ID)
      on delete restrict on update restrict;

alter table note.EDUCATIONNAL_GOAL_HIERARCHY
   add constraint FK_EDUCATIO_EDUCATION_EDUCATIO2 foreign key (EDU_EG_ID)
      references note.EDUCATIONNAL_GOAL (EG_ID)
      on delete restrict on update restrict;

alter table note.EDUCATIONNAL_GOAL_INSTANCE
   add constraint FK_EDUCATIO_FOR_EDUCA_EDUCATIO foreign key (EG_ID)
      references note.EDUCATIONNAL_GOAL (EG_ID)
      on delete restrict on update restrict;

alter table note.EDUCATIONNAL_GOAL_INSTANCE
   add constraint FK_EDUCATIO_FOR_TIMES_TIMESPAN foreign key (TIMESPAN_ID)
      references note.TIMESPAN (TIMESPAN_ID)
      on delete restrict on update restrict;

alter table note.ENDORSEMENT
   add constraint FK_ENDORSEM_ENDORSED__STUDENT_ foreign key (EG_INSTANCE_ID, STUDENT_ID)
      references note.STUDENT_SCORE (EG_INSTANCE_ID, STUDENT_ID)
      on delete restrict on update restrict;

alter table note.ENDORSEMENT
   add constraint FK_ENDORSEM_GIVE_EMPLOYEE foreign key (ENDORSER_ID)
      references public.EMPLOYEE (USER_ID)
      on delete restrict on update restrict;

alter table note.ENDORSEMENT
   add constraint FK_ENDORSEM_LINKED_TO_ENDORSEM foreign key (ENDORSEMENT_LEVEL_ID)
      references note.ENDORSEMENT_LEVEL (ENDORSEMENT_LEVEL_ID)
      on delete restrict on update restrict;

alter table note.EVALUATED_GROUP
   add constraint FK_EVALUATE_EVALUATED_EVALUATI foreign key (EVALUATION_INSTANCE_ID)
      references note.EVALUATION_INSTANCE (EVALUATION_INSTANCE_ID)
      on delete restrict on update restrict;

alter table note.EVALUATED_GROUP
   add constraint FK_EVALUATE_EVALUATED_GROUPS foreign key (GROUP_ID)
      references public.GROUPS (GROUP_ID)
      on delete restrict on update restrict;

alter table note.EVALUATION
   add constraint FK_EVALUATI_LINKED_TO_EDUCATIO foreign key (EG_ID)
      references note.EDUCATIONNAL_GOAL (EG_ID)
      on delete restrict on update restrict;

alter table note.EVALUATION
   add constraint FK_EVALUATI_OF_TYPE_EVALUATI foreign key (EVALUATION_TYPE_ID)
      references note.EVALUATION_TYPE (EVALUATION_TYPE_ID)
      on delete restrict on update restrict;

alter table note.EVALUATION_INSTANCE
   add constraint FK_EVALUATI_FOR_COURS_EDUCATIO foreign key (EG_INSTANCE_ID)
      references note.EDUCATIONNAL_GOAL_INSTANCE (EG_INSTANCE_ID)
      on delete restrict on update restrict;

alter table note.EVALUATION_INSTANCE
   add constraint FK_EVALUATI_IN_CHARGE_EMPLOYEE foreign key (EMPLOYEE_ID)
      references public.EMPLOYEE (USER_ID)
      on delete restrict on update restrict;

alter table note.EVALUATION_INSTANCE
   add constraint FK_EVALUATI_LINKED_TO_EVALUATI foreign key (EVALUATION_ID)
      references note.EVALUATION (EVALUATION_ID)
      on delete restrict on update restrict;

alter table note.EVALUATION_RUBRIC
   add constraint FK_EVALUATI_EVALUATIO_EVALUATI foreign key (EVALUATION_ID)
      references note.EVALUATION (EVALUATION_ID)
      on delete restrict on update restrict;

alter table note.EVALUATION_RUBRIC
   add constraint FK_EVALUATI_EVALUATIO_RUBRIC foreign key (RUBRIC_ID)
      references note.RUBRIC (RUBRIC_ID)
      on delete restrict on update restrict;

alter table note.GENERIC_CRITERION
   add constraint FK_GENERIC__ASSESSMEN_QUALITY foreign key (QUALITY_ID)
      references note.QUALITY (QUALITY_ID)
      on delete restrict on update restrict;

alter table note.GENERIC_CRITERION
   add constraint FK_GENERIC__HAS_GENERIC_ foreign key (GENERIC_LEVEL_AGGREGATE_ID)
      references note.GENERIC_LEVEL_AGGREGATE (GENERIC_LEVEL_AGGREGATE_ID)
      on delete restrict on update restrict;

alter table note.GENERIC_CRITERION
   add constraint FK_GENERIC__RELATIVE__ADMINIST foreign key (PROGRAM_ID)
      references note.ADMINISTRATIVE_ELEMENT (PROGRAM_ID)
      on delete restrict on update restrict;

alter table note.GENERIC_INDICATOR
   add constraint FK_GENERIC__HAS_INDICATO foreign key (INDICATOR_TYPE_ID)
      references note.INDICATOR_TYPE (INDICATOR_TYPE_ID)
      on delete restrict on update restrict;

alter table note.GENERIC_INDICATOR
   add constraint FK_GEN_IND_RELATED_T_GEN_CRI foreign key (GENERIC_CRITERION_ID)
      references note.GENERIC_CRITERION (GENERIC_CRITERION_ID)
      on delete restrict on update restrict;

alter table note.GENERIC_INDICATOR
   add constraint FK_GEN_IND_RELATED_T_GLOIA foreign key (GENERIC_LEVEL_AGGREGATE_ID, GENERIC_LEVEL_ID)
      references note.GEN_LEVEL_ORDER_IN_AGGREGATE (GENERIC_LEVEL_AGGREGATE_ID, GENERIC_LEVEL_ID)
      on delete restrict on update restrict;

alter table note.GENERIC_LEVEL
   add constraint FK_GENERIC__RELATIVE__ADMINIST foreign key (PROGRAM_ID)
      references note.ADMINISTRATIVE_ELEMENT (PROGRAM_ID)
      on delete restrict on update restrict;

alter table note.GENERIC_LEVEL_AGGREGATE
   add constraint FK_GENERIC__RELATIVE__ADMINIST foreign key (PROGRAM_ID)
      references note.ADMINISTRATIVE_ELEMENT (PROGRAM_ID)
      on delete restrict on update restrict;

alter table note.GENERIC_LEVEL_RATIO
   add constraint FK_GEN_LR_GEN_CRI foreign key (GENERIC_CRITERION_ID)
      references note.GENERIC_CRITERION (GENERIC_CRITERION_ID)
      on delete restrict on update restrict;

alter table note.GENERIC_LEVEL_RATIO
   add constraint FK_GEN_LR_GLOIA foreign key (GENERIC_LEVEL_AGGREGATE_ID, GENERIC_LEVEL_ID)
      references note.GEN_LEVEL_ORDER_IN_AGGREGATE (GENERIC_LEVEL_AGGREGATE_ID, GENERIC_LEVEL_ID)
      on delete restrict on update restrict;

alter table note.GEN_LEVEL_ORDER_IN_AGGREGATE
   add constraint FK_GEN_LEVE_ORDERED_B_GENERIC_ foreign key (GENERIC_LEVEL_ID)
      references note.GENERIC_LEVEL (GENERIC_LEVEL_ID)
      on delete restrict on update restrict;

alter table note.GEN_LEVEL_ORDER_IN_AGGREGATE
   add constraint FK_GEN_LEVE_ORDERING_GENERIC_ foreign key (GENERIC_LEVEL_AGGREGATE_ID)
      references note.GENERIC_LEVEL_AGGREGATE (GENERIC_LEVEL_AGGREGATE_ID)
      on delete restrict on update restrict;

alter table note.GROUP_ENDORSEMENT_LEVEL
   add constraint FK_GROUP_EN_GROUP_END_EMPLOYEE foreign key (ENDORSER_ID)
      references public.EMPLOYEE (USER_ID)
      on delete restrict on update restrict;

alter table note.GROUP_ENDORSEMENT_LEVEL
   add constraint FK_GROUP_EN_GROUP_END_ENDORSEM foreign key (ENDORSEMENT_LEVEL_ID)
      references note.ENDORSEMENT_LEVEL (ENDORSEMENT_LEVEL_ID)
      on delete restrict on update restrict;

alter table note.GROUP_ENDORSEMENT_LEVEL
   add constraint FK_GROUP_EN_GROUP_END_GROUPS foreign key (GROUP_ID)
      references public.GROUPS (GROUP_ID)
      on delete restrict on update restrict;

alter table note.INDICATOR
   add constraint FK_INDICATO_HAS_INDICATO foreign key (INDICATOR_TYPE_ID)
      references note.INDICATOR_TYPE (INDICATOR_TYPE_ID)
      on delete restrict on update restrict;

alter table note.INDICATOR
   add constraint FK_INDICATO_RELATED_T_CRITERIO foreign key (CRITERION_ID)
      references note.CRITERION (CRITERION_ID)
      on delete restrict on update restrict;

alter table note.INDICATOR
   add constraint FK_INDICATO_RELATED_T_LEVEL_OR foreign key (LEVEL_AGGREGATE_ID, LEVEL_ID)
      references note.LEVEL_ORDER_IN_AGGREGATE (LEVEL_AGGREGATE_ID, LEVEL_ID)
      on delete restrict on update restrict;

alter table note.LEVEL_ORDER_IN_AGGREGATE
   add constraint FK_LEVEL_OR_ORDERED_B_LEVEL foreign key (LEVEL_ID)
      references note.LEVEL (LEVEL_ID)
      on delete restrict on update restrict;

alter table note.LEVEL_ORDER_IN_AGGREGATE
   add constraint FK_LEVEL_OR_ORDERING_LEVEL_AG foreign key (LEVEL_AGGREGATE_ID)
      references note.LEVEL_AGGREGATE (LEVEL_AGGREGATE_ID)
      on delete restrict on update restrict;

alter table note.LEVEL_RATIO
   add constraint FK_LEVEL_RA_LEVEL_RAT_CRITERIO foreign key (CRITERION_ID)
      references note.CRITERION (CRITERION_ID)
      on delete restrict on update restrict;

alter table note.LEVEL_RATIO
   add constraint FK_LEVEL_RA_LEVEL_RAT_LEVEL_OR foreign key (LEVEL_AGGREGATE_ID, LEVEL_ID)
      references note.LEVEL_ORDER_IN_AGGREGATE (LEVEL_AGGREGATE_ID, LEVEL_ID)
      on delete restrict on update restrict;

alter table note.PROGRAM_EDUCATIONNAL_GOAL
   add constraint FK_PROGRAM__PROGRAM_E_ADMINIST foreign key (PROGRAM_ID)
      references note.ADMINISTRATIVE_ELEMENT (PROGRAM_ID)
      on delete restrict on update restrict;

alter table note.PROGRAM_EDUCATIONNAL_GOAL
   add constraint FK_PROGRAM__PROGRAM_E_EDUCATIO foreign key (EG_ID)
      references note.EDUCATIONNAL_GOAL (EG_ID)
      on delete restrict on update restrict;

alter table note.PROGRAM_QUALITY
   add constraint FK_PROGRAM__PROGRAM_Q_ADMINIST foreign key (PROGRAM_ID)
      references note.ADMINISTRATIVE_ELEMENT (PROGRAM_ID)
      on delete restrict on update restrict;

alter table note.PROGRAM_QUALITY
   add constraint FK_PROGRAM__PROGRAM_Q_QUALITY foreign key (QUALITY_ID)
      references note.QUALITY (QUALITY_ID)
      on delete restrict on update restrict;

alter table note.RESULT
   add constraint FK_RESULT_FOR_EVALUATI foreign key (EVALUATION_INSTANCE_ID)
      references note.EVALUATION_INSTANCE (EVALUATION_INSTANCE_ID)
      on delete restrict on update restrict;

alter table note.RESULT
   add constraint FK_RESULT_INSERTED__EMPLOYEE foreign key (GRADER_ID)
      references public.EMPLOYEE (USER_ID)
      on delete restrict on update restrict;

alter table note.RESULT
   add constraint FK_RESULT_RECIPIENT_STUDENT foreign key (STUDENT_ID)
      references public.STUDENT (USER_ID)
      on delete restrict on update restrict;

alter table note.RESULT
   add constraint FK_RESULT_RELATING__CRITERIO foreign key (CRITERION_ID)
      references note.CRITERION (CRITERION_ID)
      on delete restrict on update restrict;

alter table note.SCORE_GROUP_DEFINITION
   add constraint FK_SCORE_GR_GROUPING_POSSIBLE foreign key (SCORE_ID)
      references note.POSSIBLE_SCORE (SCORE_ID)
      on delete restrict on update restrict;

alter table note.SCORE_GROUP_DEFINITION
   add constraint FK_SCORE_GR_HAS_SCORE_GR foreign key (SCORE_GROUP_ID)
      references note.SCORE_GROUP (SCORE_GROUP_ID)
      on delete restrict on update restrict;

alter table note.SCORE_GROUP_INSTANCE
   add constraint FK_SCORE_GR_FOR_COURS_EDUCATIO foreign key (EG_INSTANCE_ID)
      references note.EDUCATIONNAL_GOAL_INSTANCE (EG_INSTANCE_ID)
      on delete restrict on update restrict;

alter table note.SCORE_GROUP_INSTANCE
   add constraint FK_SCORE_GR_LINKED_TO_SCORE_GR foreign key (SCORE_GROUP_ID)
      references note.SCORE_GROUP (SCORE_GROUP_ID)
      on delete restrict on update restrict;

alter table note.SCORE_THRESHOLD_DEFINITION
   add constraint FK_SCO_TH_DEF_SCO_GR_DEF foreign key (SCORE_GROUP_ID, SCORE_ID)
      references note.SCORE_GROUP_DEFINITION (SCORE_GROUP_ID, SCORE_ID)
      on delete restrict on update restrict;

alter table note.SCORE_THRESHOLD_DEFINITION
   add constraint FK_SCO_TH_DEF_SCO_GR_INST foreign key (SCORE_GROUP_INSTANCE_ID)
      references note.SCORE_GROUP_INSTANCE (SCORE_GROUP_INSTANCE_ID)
      on delete restrict on update restrict;

alter table note.STUDENT_SCORE
   add constraint FK_STUDENT__FOR_COURS_EDUCATIO foreign key (EG_INSTANCE_ID)
      references note.EDUCATIONNAL_GOAL_INSTANCE (EG_INSTANCE_ID)
      on delete restrict on update restrict;

alter table note.STUDENT_SCORE
   add constraint FK_STUDENT__HAS_SCORE_STUDENT foreign key (STUDENT_ID)
      references public.STUDENT (USER_ID)
      on delete restrict on update restrict;

alter table note.STUDENT_SCORE
   add constraint FK_STUDENT__IN_POSSIBLE foreign key (SCORE_ID)
      references note.POSSIBLE_SCORE (SCORE_ID)
      on delete restrict on update restrict;

alter table note.TRANSFORM
   add constraint FK_TRANSFOR_TRANSFORM_EDUCATIO foreign key (EG_ID)
      references note.EDUCATIONNAL_GOAL (EG_ID)
      on delete restrict on update restrict;

alter table note.TRANSFORM
   add constraint FK_TRANSFOR_TRANSFORM_QUALITY foreign key (QUALITY_ID)
      references note.QUALITY (QUALITY_ID)
      on delete restrict on update restrict;

ALTER TABLE NOTE.ADMINISTRATIVE_ELEMENT ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.ADMINISTRATIVE_ELEMENT ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.ADMINISTRATIVE_GROUP ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.ADMINISTRATIVE_GROUP ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.ADMINISTRATIVE_HIERARCHY ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.ADMINISTRATIVE_HIERARCHY ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.ASSIGNED_GROUP ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.ASSIGNED_GROUP ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.CRITERION ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.CRITERION ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.DEFAULT_SCORE_GROUP ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.DEFAULT_SCORE_GROUP ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.EDUCATIONNAL_GOAL_TYPE ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.EDUCATIONNAL_GOAL_TYPE ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.EDUCATIONNAL_GOAL ADD COLUMN EG_TYPE_ID INTEGER NOT NULL;
ALTER TABLE NOTE.EDUCATIONNAL_GOAL ADD CONSTRAINT fk_eg_type FOREIGN KEY (EG_TYPE_ID) REFERENCES note.educationnal_goal_type(EG_TYPE_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.EDUCATIONNAL_GOAL ADD COLUMN EP_ID INTEGER NULL;
ALTER TABLE NOTE.EDUCATIONNAL_GOAL ADD CONSTRAINT fk_ed_pathway FOREIGN KEY (EP_ID) REFERENCES public.educationnal_pathway(EP_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.EDUCATIONNAL_GOAL ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.EDUCATIONNAL_GOAL ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.EDUCATIONNAL_GOAL_HIERARCHY ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.EDUCATIONNAL_GOAL_HIERARCHY ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.EDUCATIONNAL_GOAL_INSTANCE ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.EDUCATIONNAL_GOAL_INSTANCE ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.ENDORSEMENT ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.ENDORSEMENT ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.ENDORSEMENT_LEVEL ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.ENDORSEMENT_LEVEL ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.EVALUATED_GROUP ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.EVALUATED_GROUP ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.EVALUATION ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.EVALUATION ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.EVALUATION_INSTANCE ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.EVALUATION_INSTANCE ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.EVALUATION_RUBRIC ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.EVALUATION_RUBRIC ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.EVALUATION_TYPE ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.EVALUATION_TYPE ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.GENERIC_CRITERION ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.GENERIC_CRITERION ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.GENERIC_INDICATOR ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.GENERIC_INDICATOR ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.GENERIC_LEVEL ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.GENERIC_LEVEL ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.GENERIC_LEVEL_AGGREGATE ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.GENERIC_LEVEL_AGGREGATE ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.GENERIC_LEVEL_RATIO ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.GENERIC_LEVEL_RATIO ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.GEN_LEVEL_ORDER_IN_AGGREGATE ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.GEN_LEVEL_ORDER_IN_AGGREGATE ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.GROUP_ENDORSEMENT_LEVEL ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.GROUP_ENDORSEMENT_LEVEL ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.INDICATOR ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.INDICATOR ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.INDICATOR_TYPE ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.INDICATOR_TYPE ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.LEVEL ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.LEVEL ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.LEVEL_AGGREGATE ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.LEVEL_AGGREGATE ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.LEVEL_ORDER_IN_AGGREGATE ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.LEVEL_ORDER_IN_AGGREGATE ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.LEVEL_RATIO ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.LEVEL_RATIO ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.POSSIBLE_SCORE ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.POSSIBLE_SCORE ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.PROGRAM_EDUCATIONNAL_GOAL ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.PROGRAM_EDUCATIONNAL_GOAL ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.PROGRAM_QUALITY ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.PROGRAM_QUALITY ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.QUALITY ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.QUALITY ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.RESULT ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.RESULT ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.RUBRIC ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.RUBRIC ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.SCORE_GROUP ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.SCORE_GROUP ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.SCORE_GROUP_DEFINITION ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.SCORE_GROUP_DEFINITION ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.SCORE_GROUP_INSTANCE ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.SCORE_GROUP_INSTANCE ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.SCORE_THRESHOLD_DEFINITION ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.SCORE_THRESHOLD_DEFINITION ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.STUDENT_SCORE ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.STUDENT_SCORE ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;
ALTER TABLE NOTE.TRANSFORM ADD COLUMN USER_ID INTEGER NOT NULL;
ALTER TABLE NOTE.TRANSFORM ADD CONSTRAINT fk_created_by_user FOREIGN KEY (USER_ID) REFERENCES public.USERS(USER_ID) ON UPDATE CASCADE ON DELETE RESTRICT;

-- View: note.v_assigned_group

-- DROP VIEW note.v_assigned_group;

CREATE OR REPLACE VIEW note.v_assigned_group AS
 SELECT assigned_group.eg_instance_id,
    assigned_group.privilege_id,
    assigned_group.group_id,
    assigned_group.registration,
    assigned_group.user_id
   FROM note.assigned_group;

ALTER TABLE note.v_assigned_group
  OWNER TO opus;

-- Rule: v_assigned_group_insert ON note.v_assigned_group

-- DROP RULE v_assigned_group_insert ON note.v_assigned_group;

CREATE OR REPLACE RULE v_assigned_group_insert AS
    ON INSERT TO note.v_assigned_group DO INSTEAD  INSERT INTO note.assigned_group (privilege_id, group_id, registration, user_id)
  VALUES (new.privilege_id, new.group_id, now(), new.user_id);
-- View: note.v_criterion

-- DROP VIEW note.v_criterion;

CREATE OR REPLACE VIEW note.v_criterion AS
 SELECT criterion.criterion_id,
    criterion.level_aggregate_id,
    criterion.rubric_id,
    criterion.generic_criterion_id,
    criterion.eg_id,
    criterion.label,
    criterion.weighting,
    criterion.validity_start,
    criterion.validity_end,
    criterion.registration,
    criterion.user_id
   FROM note.criterion;

ALTER TABLE note.v_criterion
  OWNER TO opus;

-- Rule: v_criterion_delete ON note.v_criterion

-- DROP RULE v_criterion_delete ON note.v_criterion;

CREATE OR REPLACE RULE v_criterion_delete AS
    ON DELETE TO note.v_criterion DO INSTEAD  UPDATE note.criterion SET validity_end = now()
  WHERE criterion.criterion_id = old.criterion_id;

-- Rule: v_criterion_insert ON note.v_criterion

-- DROP RULE v_criterion_insert ON note.v_criterion;

CREATE OR REPLACE RULE v_criterion_insert AS
    ON INSERT TO note.v_criterion DO INSTEAD  INSERT INTO note.criterion (criterion_id, level_aggregate_id, rubric_id, generic_criterion_id, eg_id, label, weighting, validity_start, validity_end, registration, user_id)
  VALUES (new.criterion_id, new.level_aggregate_id, new.rubric_id, new.generic_criterion_id, new.eg_id, new.label, new.weighting, now(), NULL::date, now(), new.user_id);
-- View: note.v_educationnal_goal

-- DROP VIEW note.v_educationnal_goal;

CREATE OR REPLACE VIEW note.v_educationnal_goal AS
 SELECT educationnal_goal.eg_id,
    educationnal_goal.label,
    educationnal_goal.short_description,
    educationnal_goal.description,
    educationnal_goal.administrative_value,
    educationnal_goal.validity_start,
    educationnal_goal.validity_end,
    educationnal_goal.registration,
    educationnal_goal.user_id
   FROM note.educationnal_goal;

ALTER TABLE note.v_educationnal_goal
  OWNER TO opus;
-- View: note.v_educationnal_goal_instance

-- DROP VIEW note.v_educationnal_goal_instance;

CREATE OR REPLACE VIEW note.v_educationnal_goal_instance AS
 SELECT educationnal_goal_instance.eg_instance_id,
 	educationnal_goal_instance.timespan_id,
    educationnal_goal_instance.eg_id,
    educationnal_goal_instance.validity_end,
    educationnal_goal_instance.registration,
    educationnal_goal_instance.user_id
   FROM note.educationnal_goal_instance;

ALTER TABLE note.v_educationnal_goal_instance
  OWNER TO opus;

-- Rule: v_educationnal_goal_instance_delete ON note.v_educationnal_goal_instance

-- DROP RULE v_educationnal_goal_instance_delete ON note.v_educationnal_goal_instance;

CREATE OR REPLACE RULE v_educationnal_goal_instance_delete AS
    ON DELETE TO note.v_educationnal_goal_instance DO INSTEAD  UPDATE note.educationnal_goal_instance SET validity_end = now()
  WHERE educationnal_goal_instance.timespan_id = old.timespan_id AND educationnal_goal_instance.eg_id = old.eg_id;

-- Rule: v_educationnal_goal_instance_insert ON note.v_educationnal_goal_instance

-- DROP RULE v_educationnal_goal_instance_insert ON note.v_educationnal_goal_instance;

CREATE OR REPLACE RULE v_educationnal_goal_instance_insert AS
    ON INSERT TO note.v_educationnal_goal_instance DO INSTEAD  INSERT INTO note.educationnal_goal_instance (timespan_id, eg_id, registration, user_id)
  VALUES (new.timespan_id, new.eg_id, now(), new.user_id);
-- View: note.v_evaluation

-- DROP VIEW note.v_evaluation;

CREATE OR REPLACE VIEW note.v_evaluation AS
 SELECT evaluation.evaluation_id,
    evaluation.evaluation_type_id,
    evaluation.label,
    evaluation.validity_start,
    evaluation.validity_end,
    evaluation.registration,
    evaluation.user_id
   FROM note.evaluation;

ALTER TABLE note.v_evaluation
  OWNER TO opus;
GRANT ALL ON TABLE note.v_evaluation TO opus;

-- Rule: v_evaluation_delete ON note.v_evaluation

-- DROP RULE v_evaluation_delete ON note.v_evaluation;

CREATE OR REPLACE RULE v_evaluation_delete AS
    ON DELETE TO note.v_evaluation DO INSTEAD  UPDATE note.evaluation SET validity_end = now()
  WHERE evaluation.evaluation_id = old.evaluation_id;

-- Rule: v_evaluation_insert ON note.v_evaluation

-- DROP RULE v_evaluation_insert ON note.v_evaluation;

CREATE OR REPLACE RULE v_evaluation_insert AS
    ON INSERT TO note.v_evaluation DO INSTEAD  INSERT INTO note.evaluation (evaluation_id, evaluation_type_id, label, validity_start, validity_end, registration, user_id)
  VALUES (new.evaluation_id, new.evaluation_type_id, new.label, now(), NULL::date, now(), new.user_id);
-- View: note.v_evaluation_instance

-- DROP VIEW note.v_evaluation_instance;

CREATE OR REPLACE VIEW note.v_evaluation_instance AS
 SELECT
    evaluation_instance.evaluation_instance_id,
    evaluation_instance.evaluation_id,
    evaluation_instance.eg_instance_id,
    evaluation_instance.employee_id,
    evaluation_instance.occurence,
    evaluation_instance.registration,
    evaluation_instance.user_id
   FROM note.evaluation_instance;

ALTER TABLE note.v_evaluation_instance
  OWNER TO opus;
GRANT ALL ON TABLE note.v_evaluation_instance TO opus;

-- Rule: v_evaluation_instance_insert ON note.v_evaluation_instance

-- DROP RULE v_evaluation_instance_insert ON note.v_evaluation_instance;

CREATE OR REPLACE RULE v_evaluation_instance_insert AS
    ON INSERT TO note.v_evaluation_instance DO INSTEAD  INSERT INTO note.evaluation_instance (evaluation_instance_id, evaluation_id, eg_instance_id, employee_id, occurence, registration, user_id)
  VALUES (new.evaluation_instance_id, new.evaluation_id, new.eg_instance_id, new.employee_id, new.occurence, now(), new.user_id);
-- View: note.v_evaluation_rubric

-- DROP VIEW note.v_evaluation_rubric;

CREATE OR REPLACE VIEW note.v_evaluation_rubric AS
 SELECT a.evaluation_id,
    a.rubric_id,
    a.registration,
    a.user_id
   FROM note.evaluation_rubric a
     LEFT JOIN note.rubric b ON a.rubric_id = b.rubric_id;

ALTER TABLE note.v_evaluation_rubric
  OWNER TO opus;

-- Rule: v_evaluation_rubric_insert ON note.v_evaluation_rubric

-- DROP RULE v_evaluation_rubric_insert ON note.v_evaluation_rubric;

CREATE OR REPLACE RULE v_evaluation_rubric_insert AS
    ON INSERT TO note.v_evaluation_rubric DO INSTEAD  INSERT INTO note.evaluation_rubric (evaluation_id, rubric_id, registration, user_id)
  VALUES (new.evaluation_id, new.rubric_id, now(), new.user_id);
-- View: note.v_evaluation_type

-- DROP VIEW note.v_evaluation_type;

CREATE OR REPLACE VIEW note.v_evaluation_type AS
 SELECT evaluation_type.evaluation_type_id,
    evaluation_type.label,
    evaluation_type.registration,
    evaluation_type.user_id
   FROM note.evaluation_type;

ALTER TABLE note.v_evaluation_type
  OWNER TO opus;

-- Rule: v_evaluation_type_insert ON note.v_evaluation_type

-- DROP RULE v_evaluation_type_insert ON note.v_evaluation_type;

CREATE OR REPLACE RULE v_evaluation_type_insert AS
    ON INSERT TO note.v_evaluation_type DO INSTEAD  INSERT INTO note.evaluation_type (evaluation_type_id, label, registration, user_id)
  VALUES (new.evaluation_type_id, new.label, now(), new.user_id);
-- View: note.v_gen_level_order_in_aggregate

-- DROP VIEW note.v_gen_level_order_in_aggregate;

CREATE OR REPLACE VIEW note.v_gen_level_order_in_aggregate AS
 SELECT gen_level_order_in_aggregate.generic_level_aggregate_id,
    gen_level_order_in_aggregate.generic_level_id,
    gen_level_order_in_aggregate.place,
    gen_level_order_in_aggregate.registration,
    gen_level_order_in_aggregate.user_id
   FROM note.gen_level_order_in_aggregate;

ALTER TABLE note.v_gen_level_order_in_aggregate
  OWNER TO opus;

-- Rule: v_genlevel_order_in_aggregate_insert ON note.v_gen_level_order_in_aggregate

-- DROP RULE v_genlevel_order_in_aggregate_insert ON note.v_gen_level_order_in_aggregate;

CREATE OR REPLACE RULE v_genlevel_order_in_aggregate_insert AS
    ON INSERT TO note.v_gen_level_order_in_aggregate DO INSTEAD  INSERT INTO note.gen_level_order_in_aggregate (generic_level_aggregate_id, generic_level_id, place, registration, user_id)
  VALUES (new.generic_level_aggregate_id, new.generic_level_id, new.place, now(), new.user_id);
-- View: note.v_generic_criterion

-- DROP VIEW note.v_generic_criterion;

CREATE OR REPLACE VIEW note.v_generic_criterion AS
 SELECT generic_criterion.generic_criterion_id,
    generic_criterion.generic_level_aggregate_id,
    generic_criterion.quality_id,
    generic_criterion.program_id,
    generic_criterion.label,
    generic_criterion.description,
    generic_criterion.validity_start,
    generic_criterion.validity_end,
    generic_criterion.registration,
    generic_criterion.user_id
   FROM note.generic_criterion;

ALTER TABLE note.v_generic_criterion
  OWNER TO opus;

-- Rule: v_generic_criterion_delete ON note.v_generic_criterion

-- DROP RULE v_generic_criterion_delete ON note.v_generic_criterion;

CREATE OR REPLACE RULE v_generic_criterion_delete AS
    ON DELETE TO note.v_generic_criterion DO INSTEAD  UPDATE note.generic_criterion SET validity_end = now()
  WHERE generic_criterion.generic_criterion_id = old.generic_criterion_id;

-- Rule: v_generic_criterion_insert ON note.v_generic_criterion

-- DROP RULE v_generic_criterion_insert ON note.v_generic_criterion;

CREATE OR REPLACE RULE v_generic_criterion_insert AS
    ON INSERT TO note.v_generic_criterion DO INSTEAD  INSERT INTO note.generic_criterion (generic_criterion_id, generic_level_aggregate_id, quality_id, program_id, label, description, validity_start, validity_end, registration, user_id)
  VALUES (new.generic_criterion_id, new.generic_level_aggregate_id, new.quality_id, 1, new.label, new.description, now(), NULL::date, now(), new.user_id);
-- View: note.v_generic_indicator

-- DROP VIEW note.v_generic_indicator;

CREATE OR REPLACE VIEW note.v_generic_indicator AS
 SELECT generic_indicator.generic_indicator_id,
    generic_indicator.generic_criterion_id,
    generic_indicator.generic_level_aggregate_id,
    generic_indicator.generic_level_id,
    generic_indicator.indicator_type_id,
    generic_indicator.description,
    generic_indicator.registration,
    generic_indicator.user_id
   FROM note.generic_indicator;

ALTER TABLE note.v_generic_indicator
  OWNER TO opus;

-- Rule: v_generic_indicator_insert ON note.v_generic_indicator

-- DROP RULE v_generic_indicator_insert ON note.v_generic_indicator;

CREATE OR REPLACE RULE v_generic_indicator_insert AS
    ON INSERT TO note.v_generic_indicator DO INSTEAD  INSERT INTO note.generic_indicator (generic_indicator_id, generic_criterion_id, generic_level_aggregate_id, generic_level_id, indicator_type_id, description, registration, user_id)  SELECT new.generic_indicator_id,
            new.generic_criterion_id,
            new.generic_level_aggregate_id,
            new.generic_level_id,
            new.indicator_type_id,
            new.description,
            now() AS now,
            new.user_id;
-- View: note.v_generic_level

-- DROP VIEW note.v_generic_level;

CREATE OR REPLACE VIEW note.v_generic_level AS
 SELECT generic_level.generic_level_id,
    generic_level.label,
    generic_level.description,
    generic_level.validity_start,
    generic_level.validity_end,
    generic_level.registration,
    generic_level.user_id
   FROM note.generic_level;

ALTER TABLE note.v_generic_level
  OWNER TO opus;

-- Rule: v_generic_level_delete ON note.v_generic_level

-- DROP RULE v_generic_level_delete ON note.v_generic_level;

CREATE OR REPLACE RULE v_generic_level_delete AS
    ON DELETE TO note.v_generic_level DO INSTEAD
    UPDATE note.generic_level SET validity_end = now()
    WHERE generic_level.generic_level_id = old.generic_level_id;

-- Rule: v_generic_level_insert ON note.v_generic_level

-- DROP RULE v_generic_level_insert ON note.v_generic_level;

CREATE OR REPLACE RULE v_generic_level_insert AS
    ON INSERT TO note.v_generic_level DO INSTEAD  INSERT INTO note.generic_level (generic_level_id, label, description, validity_start, validity_end, registration, user_id)  SELECT new.generic_level_id,
            new.label,
            new.description,
            now() AS now,
            NULL,
            now() AS now,
            new.user_id
          WHERE NOT (EXISTS ( SELECT generic_level.generic_level_id,
                    generic_level.label,
                    generic_level.description,
                    generic_level.registration
                   FROM note.generic_level
                  WHERE generic_level.label = new.label AND generic_level.description = new.description));

-- Rule: v_generic_level_update ON note.v_generic_level

-- DROP RULE v_generic_level_update ON note.v_generic_level;

CREATE OR REPLACE RULE v_generic_level_update AS
    ON UPDATE TO note.v_generic_level DO INSTEAD
    UPDATE note.generic_level SET label = new.label, description = new.description
    WHERE generic_level.generic_level_id = new.generic_level_id;
-- View: note.v_generic_level_aggregate

-- DROP VIEW note.v_generic_level_aggregate;

CREATE OR REPLACE VIEW note.v_generic_level_aggregate AS
 SELECT generic_level_aggregate.generic_level_aggregate_id,
    generic_level_aggregate.label,
    generic_level_aggregate.description,
    generic_level_aggregate.validity_start,
    generic_level_aggregate.validity_end,
    generic_level_aggregate.registration,
    generic_level_aggregate.user_id
   FROM note.generic_level_aggregate;

ALTER TABLE note.v_generic_level_aggregate
  OWNER TO opus;

-- Rule: v_generic_level_aggregate_delete ON note.v_generic_level_aggregate

-- DROP RULE v_generic_level_aggregate_delete ON note.v_generic_level_aggregate;

CREATE OR REPLACE RULE v_generic_level_aggregate_delete AS
    ON DELETE TO note.v_generic_level_aggregate DO INSTEAD  UPDATE note.generic_level_aggregate SET validity_end = now()
  WHERE generic_level_aggregate.generic_level_aggregate_id = old.generic_level_aggregate_id;

-- Rule: v_generic_level_aggregate_insert ON note.v_generic_level_aggregate

-- DROP RULE v_generic_level_aggregate_insert ON note.v_generic_level_aggregate;

CREATE OR REPLACE RULE v_generic_level_aggregate_insert AS
    ON INSERT TO note.v_generic_level_aggregate DO INSTEAD  INSERT INTO note.generic_level_aggregate (generic_level_aggregate_id, label, description, validity_start, validity_end, registration, user_id)  SELECT new.generic_level_aggregate_id,
            new.label,
            new.description,
            now() AS now,
            NULL::unknown,
            now() AS now,
            new.user_id
          WHERE NOT (EXISTS ( SELECT generic_level_aggregate.generic_level_aggregate_id,
                    generic_level_aggregate.label,
                    generic_level_aggregate.description,
                    generic_level_aggregate.validity_start,
                    generic_level_aggregate.validity_end,
                    generic_level_aggregate.registration
                   FROM note.generic_level_aggregate
                  WHERE generic_level_aggregate.label = new.label AND generic_level_aggregate.description = new.description));

-- Rule: v_generic_level_aggregate_update ON note.v_generic_level_aggregate

-- DROP RULE v_generic_level_aggregate_update ON note.v_generic_level_aggregate;

CREATE OR REPLACE RULE v_generic_level_aggregate_update AS
    ON UPDATE TO note.v_generic_level_aggregate DO INSTEAD  UPDATE note.generic_level_aggregate SET label = new.label, description = new.description
  WHERE generic_level_aggregate.generic_level_aggregate_id = new.generic_level_aggregate_id;
-- View: note.v_generic_level_ratio

-- DROP VIEW note.v_generic_level_ratio;

CREATE OR REPLACE VIEW note.v_generic_level_ratio AS
 SELECT generic_level_ratio.generic_level_aggregate_id,
    generic_level_ratio.generic_level_id,
    generic_level_ratio.generic_criterion_id,
    generic_level_ratio.weigth_ratio,
    generic_level_ratio.registration,
    generic_level_ratio.user_id
   FROM note.generic_level_ratio;

ALTER TABLE note.v_generic_level_ratio
  OWNER TO opus;

-- Rule: v_generic_level_ratio_insert ON note.v_generic_level_ratio

-- DROP RULE v_generic_level_ratio_insert ON note.v_generic_level_ratio;

CREATE OR REPLACE RULE v_generic_level_ratio_insert AS
    ON INSERT TO note.v_generic_level_ratio DO INSTEAD  INSERT INTO note.generic_level_ratio (generic_level_aggregate_id, generic_level_id, generic_criterion_id, weigth_ratio, registration, user_id)
    SELECT new.generic_level_aggregate_id,
            new.generic_level_id,
            new.generic_criterion_id,
            new.weigth_ratio,
            now() AS now,
            new.user_id;
-- View: note.v_indicator

-- DROP VIEW note.v_indicator;

CREATE OR REPLACE VIEW note.v_indicator AS
 SELECT indicator.indicator_id,
    indicator.criterion_id,
    indicator.level_aggregate_id,
    indicator.level_id,
    indicator.indicator_type_id,
    indicator.description,
    indicator.registration,
    indicator.user_id
   FROM note.indicator;

ALTER TABLE note.v_indicator
  OWNER TO opus;

-- Rule: v_indicator_insert ON note.v_indicator

-- DROP RULE v_indicator_insert ON note.v_indicator;

CREATE OR REPLACE RULE v_indicator_insert AS
    ON INSERT TO note.v_indicator DO INSTEAD  INSERT INTO note.indicator (indicator_id, criterion_id, level_aggregate_id, level_id, indicator_type_id, description, registration, user_id)  SELECT new.indicator_id,
            new.criterion_id,
            new.level_aggregate_id,
            new.level_id,
            new.indicator_type_id,
            new.description,
            now() AS now,
            new.user_id;
-- View: note.v_indicator_type

-- DROP VIEW note.v_indicator_type;

CREATE OR REPLACE VIEW note.v_indicator_type AS
 SELECT indicator_type.indicator_type_id,
    indicator_type.label,
    indicator_type.description,
    indicator_type.registration,
    indicator_type.user_id
   FROM note.indicator_type;

ALTER TABLE note.v_indicator_type
  OWNER TO opus;
-- View: note.v_level

-- DROP VIEW note.v_level;

CREATE OR REPLACE VIEW note.v_level AS
 SELECT level.level_id,
    level.label,
    level.description,
    level.registration,
    level.user_id
   FROM note.level;

ALTER TABLE note.v_level
  OWNER TO opus;

-- Rule: v_level_insert ON note.v_level

-- DROP RULE v_level_insert ON note.v_level;

CREATE OR REPLACE RULE v_level_insert AS
    ON INSERT TO note.v_level DO INSTEAD  INSERT INTO note.level (level_id, label, description, registration, user_id)  SELECT new.level_id,
            new.label,
            new.description,
            now() AS now,
            new.user_id;

-- Rule: v_level_update ON note.v_level

-- DROP RULE v_level_update ON note.v_level;

CREATE OR REPLACE RULE v_level_update AS
    ON UPDATE TO note.v_level DO INSTEAD  UPDATE note.level SET label = new.label, description = new.description
  WHERE level.level_id = new.level_id;
-- View: note.v_level_aggregate

-- DROP VIEW note.v_level_aggregate;

CREATE OR REPLACE VIEW note.v_level_aggregate AS
 SELECT level_aggregate.level_aggregate_id,
    level_aggregate.label,
    level_aggregate.description,
    level_aggregate.registration,
    level_aggregate.user_id
   FROM note.level_aggregate;

ALTER TABLE note.v_level_aggregate
  OWNER TO opus;

-- Rule: v_level_aggregate_insert ON note.v_level_aggregate

-- DROP RULE v_level_aggregate_insert ON note.v_level_aggregate;

CREATE OR REPLACE RULE v_level_aggregate_insert AS
    ON INSERT TO note.v_level_aggregate DO INSTEAD  INSERT INTO note.level_aggregate (level_aggregate_id, label, description, registration, user_id)  SELECT new.level_aggregate_id,
            new.label,
            new.description,
            now() AS now,
            new.user_id;

-- Rule: v_level_aggregate_update ON note.v_level_aggregate

-- DROP RULE v_level_aggregate_update ON note.v_level_aggregate;

CREATE OR REPLACE RULE v_level_aggregate_update AS
    ON UPDATE TO note.v_level_aggregate DO INSTEAD  UPDATE note.level_aggregate SET label = new.label, description = new.description
  WHERE level_aggregate.level_aggregate_id = new.level_aggregate_id;
-- View: note.v_level_order_in_aggregate

-- DROP VIEW note.v_level_order_in_aggregate;

CREATE OR REPLACE VIEW note.v_level_order_in_aggregate AS
 SELECT level_order_in_aggregate.level_aggregate_id,
    level_order_in_aggregate.level_id,
    level_order_in_aggregate.place,
    level_order_in_aggregate.registration,
    level_order_in_aggregate.user_id
   FROM note.level_order_in_aggregate;

ALTER TABLE note.v_level_order_in_aggregate
  OWNER TO opus;

-- Rule: v_level_order_in_aggregate_insert ON note.v_level_order_in_aggregate

-- DROP RULE v_level_order_in_aggregate_insert ON note.v_level_order_in_aggregate;

CREATE OR REPLACE RULE v_level_order_in_aggregate_insert AS
    ON INSERT TO note.v_level_order_in_aggregate DO INSTEAD  INSERT INTO note.level_order_in_aggregate (level_aggregate_id, level_id, place, registration, user_id)
  VALUES (new.level_aggregate_id, new.level_id, new.place, now(), new.user_id);
-- View: note.v_level_ratio

-- DROP VIEW note.v_level_ratio;

CREATE OR REPLACE VIEW note.v_level_ratio AS
 SELECT level_ratio.level_aggregate_id,
    level_ratio.level_id,
    level_ratio.criterion_id,
    level_ratio.weigth_ratio,
    level_ratio.registration,
    level_ratio.user_id
   FROM note.level_ratio;

ALTER TABLE note.v_level_ratio
  OWNER TO opus;

-- Rule: v_level_ratio_insert ON note.v_level_ratio

-- DROP RULE v_level_ratio_insert ON note.v_level_ratio;

CREATE OR REPLACE RULE v_level_ratio_insert AS
    ON INSERT TO note.v_level_ratio DO INSTEAD  INSERT INTO note.level_ratio (level_aggregate_id, level_id, criterion_id, weigth_ratio, registration, user_id)  SELECT new.level_aggregate_id,
            new.level_id,
            new.criterion_id,
            new.weigth_ratio,
            now() AS now,
            new.user_id;
-- View: note.v_quality

-- DROP VIEW note.v_quality;

CREATE OR REPLACE VIEW note.v_quality AS
 SELECT quality.quality_id,
    quality.label,
    quality.description,
    quality.validity_start,
    quality.validity_end,
    quality.registration,
    quality.user_id
   FROM note.quality;

ALTER TABLE note.v_quality
  OWNER TO opus;
-- View: note.v_result

-- DROP VIEW note.v_result;

CREATE OR REPLACE VIEW note.v_result AS
 SELECT result.student_id,
    result.criterion_id,
    result.evaluation_instance_id,
    result.registration,
    result.grader_id,
    result.value,
    result.comment,
    result.user_id
   FROM note.result;

ALTER TABLE note.v_result
  OWNER TO opus;

-- Rule: v_result_insert ON note.v_result

-- DROP RULE v_result_insert ON note.v_result;

CREATE OR REPLACE RULE v_result_insert AS
    ON INSERT TO note.v_result DO INSTEAD  INSERT INTO note.result (student_id, criterion_id, evaluation_instance_id, registration, grader_id, value, comment, user_id)  SELECT new.student_id,
            new.criterion_id,
            new.evaluation_instance_id,
            now() AS now,
            new.grader_id,
            new.value,
            new.comment,
            new.user_id
          WHERE NOT (EXISTS ( SELECT result.student_id,
                    result.criterion_id,
                    result.evaluation_instance_id,
                    result.registration,
                    result.grader_id,
                    result.value,
                    result.comment,
                    result.user_id
                   FROM note.result
                  WHERE result.student_id = new.student_id AND result.criterion_id = new.criterion_id AND result.evaluation_instance_id = new.evaluation_instance_id));
-- View: note.v_rubric

-- DROP VIEW note.v_rubric;

CREATE OR REPLACE VIEW note.v_rubric AS
 SELECT rubric.rubric_id,
    rubric.label,
    rubric.statement,
    rubric.place,
    rubric.validity_start,
    rubric.validity_end,
    rubric.registration,
    rubric.user_id
   FROM note.rubric
  ORDER BY rubric.rubric_id, rubric.place;

ALTER TABLE note.v_rubric
  OWNER TO opus;

-- Rule: v_rubric_delete ON note.v_rubric

-- DROP RULE v_rubric_delete ON note.v_rubric;

CREATE OR REPLACE RULE v_rubric_delete AS
    ON DELETE TO note.v_rubric DO INSTEAD  UPDATE note.rubric SET validity_end = now()
  WHERE rubric.rubric_id = old.rubric_id;

-- Rule: v_rubric_insert ON note.v_rubric

-- DROP RULE v_rubric_insert ON note.v_rubric;

CREATE OR REPLACE RULE v_rubric_insert AS
    ON INSERT TO note.v_rubric DO INSTEAD  INSERT INTO note.rubric (rubric_id, label, statement, place, validity_start, validity_end, registration, user_id)
  VALUES (new.rubric_id, new.label, new.statement, new.place, now(), NULL::date, now(), new.user_id);

-- View: note.v_timespan

-- DROP VIEW note.v_timespan;

CREATE OR REPLACE VIEW note.v_timespan AS
 SELECT timespan.timespan_id,
    timespan.user_id,
    timespan.label,
    timespan.start_date,
    timespan.end_date,
    timespan.registration
   FROM note.timespan;

ALTER TABLE note.v_timespan
  OWNER TO opus;

-- Rule: v_timespan_insert ON note.v_timespan

-- DROP RULE v_timespan_insert ON note.v_timespan;

CREATE OR REPLACE RULE v_timespan_insert AS
    ON INSERT TO note.v_timespan DO INSTEAD  INSERT INTO note.timespan (timespan_id, user_id, label, start_date, end_date, registration)
  VALUES (new.timespan_id, new.user_id, new.label, new.start_date, new.end_date, now());

-- Rule: v_timespan_update ON note.v_timespan

-- DROP RULE v_timespan_update ON note.v_timespan;

CREATE OR REPLACE RULE v_timespan_update AS
  ON UPDATE TO note.v_timespan DO INSTEAD  UPDATE note.timespan SET label = new.label, start_date = new.start_date, end_date = new.end_date
  WHERE timespan.timespan_id = new.timespan_id;


--
-- ********************************* CUSTOM GRADES PROCEDURES AND VIEWS********************************* --
--

--
-- student semester views
--
CREATE OR REPLACE VIEW note.v_student_semester AS
	SELECT DISTINCT u.administrative_user_id, eg.short_description, t.label, egi.eg_instance_id
	  FROM public.users u, public.user_group ug, public.groups g, note.assigned_group ag, note.educationnal_goal_instance egi, note.timespan t, note.educationnal_goal eg
	  WHERE u.user_id = ug.member_id
	    AND g.group_id = ug.group_id
	    AND g.group_id = ag.group_id
	    AND ag.eg_instance_id = egi.eg_instance_id
	    AND egi.eg_id = eg.eg_id
	    AND egi.timespan_id = t.timespan_id;

--
-- Semester to AP hierarchy view
--
CREATE OR REPLACE VIEW note.v_semester_ap_hierarchy AS
	SELECT DISTINCT eg1.eg_id AS semester_id, eg1.short_description AS semester_desc, eg2.eg_id AS ap_id, eg2.short_description as ap_desc
	FROM note.educationnal_goal eg1
	  INNER JOIN note.educationnal_goal_hierarchy eh1
	    ON eg1.eg_id = eh1.eg_id
	  INNER JOIN note.educationnal_goal eg2
	    ON eg2.eg_id = eh1.edu_eg_id
	  INNER JOIN note.educationnal_goal_type egt1
	    ON egt1.eg_type_id = eg1.eg_type_id
	  INNER JOIN note.educationnal_goal_type egt2
	    ON egt2.eg_type_id = eg2.eg_type_id
	WHERE egt1.label = 'Session' AND egt2.label = 'Ap';

--
-- Ap to Sub Ap hierarchy view
--
CREATE OR REPLACE VIEW note.v_ap_subap_hierarchy AS
	SELECT DISTINCT eg1.eg_id AS ap_id, eg1.short_description AS ap_desc, eg2.eg_id AS subap_id, eg2.short_description as subap_desc
	FROM note.educationnal_goal eg1
	  INNER JOIN note.educationnal_goal_hierarchy eh1
	    ON eg1.eg_id = eh1.eg_id
	  INNER JOIN note.educationnal_goal eg2
	    ON eg2.eg_id = eh1.edu_eg_id
	  INNER JOIN note.educationnal_goal_type egt1
	    ON egt1.eg_type_id = eg1.eg_type_id
	  INNER JOIN note.educationnal_goal_type egt2
	    ON egt2.eg_type_id = eg2.eg_type_id
	WHERE egt1.label = 'Ap' AND egt2.label = 'Compétence';

CREATE OR REPLACE VIEW note.v_student_results_by_eg_instance AS
	SELECT egi.eg_instance_id, r.student_id, evi.evaluation_id, c.eg_id, r.value, c.weighting
	FROM note.educationnal_goal_instance egi, note.evaluation_instance evi, note.result r, note.criterion c
	WHERE egi.eg_instance_id = evi.eg_instance_id AND
		  evi.evaluation_instance_id = r.evaluation_instance_id AND 
		  c.criterion_id = r.criterion_id;


CREATE OR REPLACE FUNCTION note.get_criterion_id_with_rubric_label(label text) RETURNS integer AS $$
	SELECT c.criterion_id
	FROM note.criterion c, note.rubric r
	WHERE c.rubric_id = r.rubric_id AND r.label = $1;
$$ LANGUAGE SQL;


CREATE OR REPLACE FUNCTION note.get_eval_inst_id_with_eval_label(evaluation_label text, timespan_label text, educ_goal_label text) RETURNS integer AS $$
	SELECT ev.evaluation_id
	FROM note.evaluation_instance evi, note.evaluation ev, note.educationnal_goal_instance egi, note.educationnal_goal eg, note.timespan t
	WHERE evi.evaluation_id = ev.evaluation_id AND ev.label = $1 AND
		  egi.eg_instance_id = evi.eg_instance_id AND
		  eg.eg_id = egi.eg_id AND eg.label = $3 AND
		  egi.timespan_id = t.timespan_id AND t.label = $2;
$$ LANGUAGE SQL;


CREATE OR REPLACE FUNCTION note.create_eg_instance_assigned(timespan_label text, eg_label text, group_label text, access_privilege_label text) RETURNS void AS $$
	INSERT INTO note.educationnal_goal_instance(timespan_id, eg_id, user_id) (SELECT t.timespan_id, eg.eg_id, 1 FROM note.timespan t, note.educationnal_goal eg WHERE t.label = $1 AND eg.label = $2);
	INSERT INTO note.assigned_group (eg_instance_id, privilege_id, group_id, user_id)
		SELECT egi.eg_instance_id, p.privilege_id, g.group_id, 1
		FROM public.groups g, public.privilege p, ( select last_value as eg_instance_id from note.educationnal_goal_instance_eg_instance_id_seq ) egi
		WHERE  g.label = $3 AND p.label = $4;
$$ LANGUAGE SQL;


CREATE OR REPLACE FUNCTION note.create_rubric_and_criterion(eval_id integer, rubric_label text, rubric_short_desc text, course_label text, weighting integer) RETURNS void AS $$
	INSERT INTO note.rubric (label, statement, validity_start, user_id) VALUES ($2, $3, now(), 1);

	INSERT INTO note.evaluation_rubric (evaluation_id, rubric_id, user_id)
		SELECT $1, r.rubric_id, 1
		FROM (SELECT last_value AS rubric_id from note.rubric_rubric_id_seq ) r;

	INSERT INTO note.criterion(rubric_id, eg_id, weighting, validity_start, user_id)
    SELECT r.rubric_id, eg.eg_id, $5, now(), 1
    FROM (SELECT last_value AS rubric_id from note.rubric_rubric_id_seq ) r,
         note.educationnal_goal eg
    WHERE eg.label = $4;
$$ LANGUAGE SQL;



/**
	Insert evaluation instance for a given group on a given timespan for a given evaluation
*/
CREATE OR REPLACE FUNCTION note.create_eval_inst_and_group(eval_label text, timespan_label text, semester_label text, eval_group_label text, occurence_time text, employee_id_label text) RETURNS void AS $$

	INSERT INTO note.evaluation_instance (evaluation_id, eg_instance_id, employee_id, occurence, registration, user_id)
	  SELECT ev.evaluation_id, egi.eg_instance_id, e.user_id, $5, t.start_date, 1
	  FROM note.evaluation ev, note.timespan t, public.employee e, note.educationnal_goal_instance egi, note.educationnal_goal eg
	  WHERE egi.timespan_id = t.timespan_id AND t.label = $2 AND egi.eg_id = eg.eg_id AND eg.label = $3 AND
	  		ev.label = $1 AND e.employee_id = $6;

	INSERT INTO note.evaluated_group(evaluation_instance_id, group_id, registration, user_id)
      SELECT evi.evaluation_instance_id, g.group_id, now(), 1
      FROM note.get_eval_inst_id_with_eval_label($1, $2, $3) AS evi(evaluation_instance_id),
      	   public.groups g
      WHERE g.label = $4;
$$ LANGUAGE SQL;


--
-- Semester type
--
CREATE TYPE note.t_semester AS (
  semester_label text,
  timespan_label text,
  semester_id int
);

--
-- student semester function
--
CREATE OR REPLACE FUNCTION note.get_student_semester(administrative_user_id text) RETURNS SETOF note.t_semester AS $$
SELECT vss.short_description, vss.label, vss.eg_instance_id
  FROM note.v_student_semester vss
  WHERE vss.administrative_user_id = $1;
$$ LANGUAGE SQL;

--
-- AP types and procedures
--
CREATE TYPE note.t_ap_grades_summary AS (
  ap_name text,
  result_value int,
  avg_value int,
  max_value int
);

CREATE OR REPLACE FUNCTION note.get_ap_results(student_id text, session_id int) RETURNS SETOF note.t_ap_grades_summary AS $$
    -- TODO : CREATE SELECT STATEMENT
    SELECT           'GEN501', 80, 79, 100
    UNION ALL SELECT 'GEN402', 75, 73, 90
    UNION ALL SELECT 'GEN666', 42, 50, 110;
$$ LANGUAGE SQL;


--
-- Semester types and procedures
--
CREATE TYPE note.t_competence_eval_result AS (
  eval_label text,
  course_label text,
  competence_label text,
  result_value int,
  avg_result_value int,
  max_result_value int,
  standard_dev int
);

CREATE OR REPLACE FUNCTION note.get_semester_eval_results(student_id text, session_id int) RETURNS SETOF note.t_competence_eval_result AS $$
  -- TODO : CREATE SELECT STATEMENT
  SELECT           'Sommatif APP2', 'GEN501', 'GEN501-1', 80, 75, 120, 6
  UNION ALL SELECT 'Sommatif APP2', 'GEN501', 'GEN501-2', 56, 50, 70, 5
  UNION ALL SELECT 'Sommatif APP2', 'GEN402', 'GEN402-1', 74, 70, 75, 11
  UNION ALL SELECT 'Sommatif APP3', 'GEN666', 'GEN666-1', 80, 75, 85, 3
  UNION ALL SELECT 'Sommatif APP3', 'GEN666', 'GEN666-2', 80, 73, 110, 4;
$$ LANGUAGE SQL;

-- Semester procedure example
-- SELECT * FROM get_semester_results('bedh2102', 'S5I');

CREATE OR REPLACE FUNCTION note.get_ap_eval_results(student_id text, session_id int, ap_id int) RETURNS SETOF note.t_competence_eval_result AS $$
  -- TODO : CREATE SELECT STATEMENT
  SELECT           'Sommatif APP3', 'GEN666', 'GEN666-1', 80, 75, 85, 3
  UNION ALL SELECT 'Sommatif APP3', 'GEN666', 'GEN666-2', 80, 73, 110, 4;
$$ LANGUAGE SQL;

-- Ap results procedure example
-- SELECT * FROM get_semester_results('bedh2102', 'S5I');


-- TODO : probably add a procedure to retrieve competence progress (ex : student has currently 50/300 of the total points for a competence)
-- We could also include it in the competence_eval_result_t but it seems like a lot of information at the same time

CREATE TYPE note.t_competence AS (
	ap_label text,
	competence_label text
);

CREATE OR REPLACE FUNCTION note.get_semester_competences(student_id text, session_id int) RETURNS SETOF note.t_competence AS $$
  SELECT apsap.ap_desc, apsap.subap_desc
  FROM note.v_semester_ap_hierarchy seap, note.v_ap_subap_hierarchy apsap, note.educationnal_goal_instance egi
  WHERE egi.eg_instance_id = $2
  	AND seap.semester_id = egi.eg_id
  	AND seap.ap_id = apsap.ap_id
  ORDER BY apsap.ap_desc, apsap.subap_desc
$$ LANGUAGE SQL;

CREATE TYPE note.t_evaluation AS (evaluation_label text);

CREATE OR REPLACE FUNCTION note.get_semester_evals(student_id text, session_id int) RETURNS SETOF note.t_evaluation AS $$
  -- TODO : CREATE SELECT STATEMENT
  SELECT           'Sommatif APP2'
  UNION ALL SELECT 'Sommatif APP3';
$$ LANGUAGE SQL;



GRANT SELECT ON ALL TABLES IN SCHEMA audit TO opus;
GRANT SELECT ON ALL TABLES IN SCHEMA content TO opus;
GRANT SELECT ON ALL TABLES IN SCHEMA discussion TO opus;
GRANT SELECT ON ALL TABLES IN SCHEMA file TO opus;
GRANT SELECT ON ALL TABLES IN SCHEMA notification TO opus;
GRANT SELECT ON ALL TABLES IN SCHEMA pinboard TO opus;
GRANT SELECT ON ALL TABLES IN SCHEMA note TO opus;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO opus;

GRANT USAGE ON SCHEMA audit TO opus;
GRANT USAGE ON SCHEMA content TO opus;
GRANT USAGE ON SCHEMA discussion TO opus;
GRANT USAGE ON SCHEMA file TO opus;
GRANT USAGE ON SCHEMA notification TO opus;
GRANT USAGE ON SCHEMA pinboard TO opus;
GRANT USAGE ON SCHEMA note TO opus;
GRANT USAGE ON SCHEMA public TO opus;