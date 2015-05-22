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
