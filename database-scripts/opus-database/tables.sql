-- THE USER LOGGED IN TO CREATE EVERY ELEMENT IN THE DB MUST BE appopus. OTHERWISE, AT THE END OF THE LAST SCRIPT, THE QUERY USED TO CHANGE THE OWNERSHIP FROM appopus TO opus WON'T AFFECT ALL OWNERSHIPS.
CREATE DATABASE projetS6;

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


CREATE TYPE t_content AS (
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
