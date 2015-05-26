-- SCHEMA PUBLIC
set search_path to public;


-- USER

INSERT INTO users (administrative_user_id, last_name, first_name, email_address) VALUES ('geim9002', 'Super', 'Admin', 'Admin.Super@USherbrooke.ca');
INSERT INTO users (administrative_user_id, last_name, first_name, email_address) VALUES ('maip2202', 'Maillé', 'Pascale', 'Pascale.Maille@USherbrooke.ca');

INSERT INTO users (administrative_user_id, last_name, first_name, email_address) VALUES ('foum2410', 'Maillé', 'Pascale', 'Pascale.Maille@USherbrooke.ca');
INSERT INTO users (administrative_user_id, last_name, first_name, email_address) VALUES ('foum2411', 'Maillé', 'Pascale', 'Pascale.Maille@USherbrooke.ca');
INSERT INTO users (administrative_user_id, last_name, first_name, email_address) VALUES ('foum2412', 'Maillé', 'Pascale', 'Pascale.Maille@USherbrooke.ca');
INSERT INTO users (administrative_user_id, last_name, first_name, email_address) VALUES ('foum2413', 'Maillé', 'Pascale', 'Pascale.Maille@USherbrooke.ca');
INSERT INTO users (administrative_user_id, last_name, first_name, email_address) VALUES ('foum2414', 'Maillé', 'Pascale', 'Pascale.Maille@USherbrooke.ca');
INSERT INTO users (administrative_user_id, last_name, first_name, email_address) VALUES ('foum2415', 'Maillé', 'Pascale', 'Pascale.Maille@USherbrooke.ca');
INSERT INTO users (administrative_user_id, last_name, first_name, email_address) VALUES ('foum2416', 'Maillé', 'Pascale', 'Pascale.Maille@USherbrooke.ca');
INSERT INTO users (administrative_user_id, last_name, first_name, email_address) VALUES ('foum2417', 'Maillé', 'Pascale', 'Pascale.Maille@USherbrooke.ca');
INSERT INTO users (administrative_user_id, last_name, first_name, email_address) VALUES ('foum2418', 'Maillé', 'Pascale', 'Pascale.Maille@USherbrooke.ca');
INSERT INTO users (administrative_user_id, last_name, first_name, email_address) VALUES ('foum2419', 'Maillé', 'Pascale', 'Pascale.Maille@USherbrooke.ca');

ALTER SEQUENCE public.users_user_id_seq RESTART WITH 13;


-- APPLICATION

INSERT INTO application (label, url, description, user_id) VALUES ('Opus', 'https://www-dev.gel.usherbrooke.ca:8444/opus', 'Regroupement d''applications web de support à l''apprentissage et à l''enseignement.', 1);
INSERT INTO application (label, url, description, user_id) VALUES ('Gestion des groupes', 'https://www-dev.gel.usherbrooke.ca:8444/group', 'Application de gestion des groupe.', 1);
INSERT INTO application (label, url, description, user_id) VALUES ('Gestion des applications', 'https://www-dev.gel.usherbrooke.ca:8444/application', 'Application de gestion des applications.', 1);
INSERT INTO application (label, url, description, user_id) VALUES ('Formulaire', 'https://www-dev.gel.usherbrooke.ca:8444/formulaire', 'Application de création de formulaires d''évaluation.', 1);
INSERT INTO application (label, url, description, user_id) VALUES ('Photo de profil', 'https://www-dev.gel.usherbrooke.ca:8444/profilePicture', 'Application de dépôt et de gestion des photos de profils.', 1);
INSERT INTO application (label, url, description, user_id) VALUES ('Babillard', 'https://www-dev.gel.usherbrooke.ca:8444/pinboard', 'Application d''organisation des ressources électroniques en babillards.', 1);
INSERT INTO application (label, url, description, user_id) VALUES ('Discussion', 'https://www-dev.gel.usherbrooke.ca:8444/discussion', 'Application de discussion permettant les commentaires imbriqués.', 1);
INSERT INTO application (label, url, description, user_id) VALUES ('Dépôt de fichier', 'https://www-dev.gel.usherbrooke.ca:8444/deposit', 'Application de téléversement et téléchargement de fichiers.', 1);

ALTER SEQUENCE public.application_application_id_seq RESTART WITH 9;


-- PRIVILEGE

INSERT INTO privilege (label, description, user_id) VALUES ('Accès membre', 'Accès membre à l''application', 1);
INSERT INTO privilege (label, description, user_id) VALUES ('Éditer tous les groupes', 'Accès administrateur permettant d''éditer tous les groupes', 1);
INSERT INTO privilege (label, description, user_id) VALUES ('Administrer les photos de profil', 'Accès administrateur permettant de gérer toutes les photos de profil', 1);

ALTER SEQUENCE public.privilege_privilege_id_seq RESTART WITH 4;


-- GROUP

INSERT INTO groups (label, description, unique_label, user_id) VALUES ('Administrateurs Opus', 'Groupe des administrateurs du système Opus', false, 1);
INSERT INTO groups (label, description, unique_label, user_id) VALUES ('Coordonnateurs GEGI', 'Groupe des coordonnateurs de département GEGI', false, 1);
INSERT INTO groups (label, description, unique_label, user_id) VALUES ('GI58', 'Groupe de Génie informatique promotion 58', false, 1);

ALTER SEQUENCE public.groups_group_id_seq RESTART WITH 4;


-- ADMINISTRATOR_GROUP

INSERT INTO administrator_group (group_id, administrator_id, user_id) VALUES (1, 1, 1);
INSERT INTO administrator_group (group_id, administrator_id, user_id) VALUES (2, 1, 1);


-- USER_GROUP

/**
	Example of how to simply bind a user to a group:

	INSERT INTO public.user_group (member_id, group_id, user_id)
	    (SELECT users.user_id, groups.group_id, 1
	     FROM users , groups
	     WHERE users.administrative_user_id = 'abcd1234' AND groups.label = 'label');
*/

INSERT INTO user_group (member_id, group_id, user_id) VALUES (1, 1, 1);
INSERT INTO user_group (member_id, group_id, user_id) VALUES (1, 2, 1);
INSERT INTO user_group (member_id, group_id, user_id) VALUES (2, 1, 1);
INSERT INTO user_group (member_id, group_id, user_id) VALUES (2, 2, 1);

INSERT INTO user_group (member_id, group_id, user_id) VALUES (3, 3, 1);
INSERT INTO user_group (member_id, group_id, user_id) VALUES (4, 3, 1);
INSERT INTO user_group (member_id, group_id, user_id) VALUES (5, 3, 1);
INSERT INTO user_group (member_id, group_id, user_id) VALUES (6, 3, 1);
INSERT INTO user_group (member_id, group_id, user_id) VALUES (7, 3, 1);
INSERT INTO user_group (member_id, group_id, user_id) VALUES (8, 3, 1);
INSERT INTO user_group (member_id, group_id, user_id) VALUES (9, 3, 1);
INSERT INTO user_group (member_id, group_id, user_id) VALUES (10, 3, 1);
INSERT INTO user_group (member_id, group_id, user_id) VALUES (11, 3, 1);
INSERT INTO user_group (member_id, group_id, user_id) VALUES (12, 3, 1);


-- APPLICATION_PRIVILEGE

INSERT INTO application_privilege (application_id, privilege_id, user_id) VALUES (1, 1, 1);
INSERT INTO application_privilege (application_id, privilege_id, user_id) VALUES (2, 1, 1);
INSERT INTO application_privilege (application_id, privilege_id, user_id) VALUES (2, 2, 1);
INSERT INTO application_privilege (application_id, privilege_id, user_id) VALUES (3, 1, 1);
INSERT INTO application_privilege (application_id, privilege_id, user_id) VALUES (4, 1, 1);
INSERT INTO application_privilege (application_id, privilege_id, user_id) VALUES (5, 1, 1);
INSERT INTO application_privilege (application_id, privilege_id, user_id) VALUES (5, 3, 1);
INSERT INTO application_privilege (application_id, privilege_id, user_id) VALUES (6, 1, 1);
INSERT INTO application_privilege (application_id, privilege_id, user_id) VALUES (7, 1, 1);
INSERT INTO application_privilege (application_id, privilege_id, user_id) VALUES (8, 1, 1);


-- APPLICATION_PRIVILEGE_GROUP

INSERT INTO application_privilege_group (application_id, privilege_id, group_id, user_id) VALUES (1, 1, 2, 1);
INSERT INTO application_privilege_group (application_id, privilege_id, group_id, user_id) VALUES (2, 1, 2, 1);
INSERT INTO application_privilege_group (application_id, privilege_id, group_id, user_id) VALUES (2, 2, 1, 1);
INSERT INTO application_privilege_group (application_id, privilege_id, group_id, user_id) VALUES (3, 1, 1, 1);
INSERT INTO application_privilege_group (application_id, privilege_id, group_id, user_id) VALUES (4, 1, 1, 1);
INSERT INTO application_privilege_group (application_id, privilege_id, group_id, user_id) VALUES (5, 1, 2, 1);
INSERT INTO application_privilege_group (application_id, privilege_id, group_id, user_id) VALUES (5, 3, 1, 1);
INSERT INTO application_privilege_group (application_id, privilege_id, group_id, user_id) VALUES (6, 1, 2, 1);
INSERT INTO application_privilege_group (application_id, privilege_id, group_id, user_id) VALUES (7, 1, 2, 1);
INSERT INTO application_privilege_group (application_id, privilege_id, group_id, user_id) VALUES (8, 1, 2, 1);


-- EMPLOYEE

INSERT INTO employee (employee_id, user_id, phone_number, office, occupation) VALUES ('04000001', 1, '819-821-8000 #1234', 'C1-3111', 'Coordonnateur');


-- STUDENT

/**
	Example of how to simply add a student

	INSERT INTO public.student (user_id, student_id)
	    (SELECT users.user_id, 'matricule with space like: 11 111 111'
	     FROM users
	     WHERE users.administrative_user_id = 'abcd1234');
*/

INSERT INTO STUDENT (user_id, student_id) VALUES (3, '12000001');
INSERT INTO STUDENT (user_id, student_id) VALUES (4, '12000002');
INSERT INTO STUDENT (user_id, student_id) VALUES (5, '12000003');
INSERT INTO STUDENT (user_id, student_id) VALUES (6, '12000004');
INSERT INTO STUDENT (user_id, student_id) VALUES (7, '12000005');
INSERT INTO STUDENT (user_id, student_id) VALUES (8, '12000006');
INSERT INTO STUDENT (user_id, student_id) VALUES (9, '12000007');
INSERT INTO STUDENT (user_id, student_id) VALUES (10, '12000008');
INSERT INTO STUDENT (user_id, student_id) VALUES (11, '12000009');
INSERT INTO STUDENT (user_id, student_id) VALUES (12, '12000010');


-- SCHEMA CONTENT
set search_path to content;


-- TYPE

INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('content', 'content_message', 7, '#discussion;tyep=message;id=', 'discussion.get_groups_to_notify_about_message');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('content', 'content_pin', 6, '#pinboard;id=', 'pinboard.get_groups_to_notify_about_pin');
INSERT INTO type (table_schema, table_name) VALUES ('content', 'url');

INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('file', 'corrected_file', 8, '#deposit;id=', 'file.get_groups_to_notify_about_file');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('file', 'file', 8, '#deposit;id=', 'file.get_groups_to_notify_about_file');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('file', 'file_group', 8, '#deposit;id=', 'file.get_groups_to_notify_about_file');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('file', 'folder', 8, '#deposit;id=', 'file.get_groups_related_to_folder');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('file', 'folder_file', 8, '#deposit;id=', 'file.get_groups_to_notify_about_folder');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('file', 'folder_group', 8, '#deposit;id=', 'file.get_groups_to_notify_about_folder');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('file', 'turn_in_folder', 8, '#deposit;id=', 'file.get_groups_to_notify_about_folder');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('file', 'version', 8, '#deposit;id=', 'file.get_groups_to_notify_about_file');

INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('discussion', 'discussion', 7, '#discussion;type=discussion;id=', 'discussion.get_groups_to_notify_about_discussion');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('discussion', 'discussion_group', 7, '#discussion;type=discussion;id=', 'discussion.get_groups_to_notify_about_discussion');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('discussion', 'message', 7, '#discussion;type=message;id=', 'discussion.get_groups_to_notify_about_message');

INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('pinboard', 'pin', 6, '#pinboard;id=', 'pinboard.get_groups_to_notify_about_pin');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('pinboard', 'pin_connection', 6, '#pinboard;id=', 'pinboard.get_groups_to_notify_about_pin');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('pinboard', 'pin_geometry', 6, '#pinboard;id=', 'pinboard.get_groups_to_notify_about_pin');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('pinboard', 'pinboard', 6, '#pinboard;id=', 'pinboard.get_groups_to_notify_about_pinboard');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('pinboard', 'pinboard_group', 6, '#pinboard;id=', 'pinboard.get_groups_to_notify_about_pinboard');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('pinboard', 'pinboard_pinboard', 6, '#pinboard;id=', 'pinboard.get_groups_to_notify_about_pinboard');

INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('public', 'administrator_group', 2, '#group;id=', 'public.get_groups_to_notify_about_administrator_group');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('public', 'application_privilege', 2, '#application_privilege;id=', 'public.get_groups_to_notify_about_application_privilege');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('public', 'application_privilege_group', 3, '#application_privilege;id=', 'public.get_groups_to_notify_about_application_privilege');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('public', 'groups', 2, '#group;id=', 'public.get_groups_to_notify_about_group');
INSERT INTO type (table_schema, table_name, application_id, url, function_name_to_notify_groups_about_an_event) VALUES ('public', 'group_group', 2, '#group;id=', 'public.get_groups_to_notify_about_group');

ALTER SEQUENCE content.type_type_id_seq RESTART WITH 26;


-- FIELD

INSERT INTO field (field_id, type_id) VALUES ('label', 3);
INSERT INTO field (field_id, type_id) VALUES ('label', 5);
INSERT INTO field (field_id, type_id) VALUES ('label', 7);
INSERT INTO field (field_id, type_id) VALUES ('description', 11);
INSERT INTO field (field_id, type_id) VALUES ('label', 12);
INSERT INTO field (field_id, type_id) VALUES ('label', 14);
INSERT INTO field (field_id, type_id) VALUES ('label', 15);
INSERT INTO field (field_id, type_id) VALUES ('label', 18);
INSERT INTO field (field_id, type_id) VALUES ('label', 24);
INSERT INTO field (field_id, type_id) VALUES ('registration', 3);
INSERT INTO field (field_id, type_id) VALUES ('registration', 5);
INSERT INTO field (field_id, type_id) VALUES ('registration', 7);
INSERT INTO field (field_id, type_id) VALUES ('registration', 11);
INSERT INTO field (field_id, type_id) VALUES ('registration', 12);
INSERT INTO field (field_id, type_id) VALUES ('registration', 14);
INSERT INTO field (field_id, type_id) VALUES ('registration', 15);
INSERT INTO field (field_id, type_id) VALUES ('registration', 18);
INSERT INTO field (field_id, type_id) VALUES ('registration', 24);
INSERT INTO field (field_id, type_id) VALUES ('url', 3);
INSERT INTO field (field_id, type_id) VALUES ('user_id', 3);
INSERT INTO field (field_id, type_id) VALUES ('user_id', 5);
INSERT INTO field (field_id, type_id) VALUES ('user_id', 7);
INSERT INTO field (field_id, type_id) VALUES ('user_id', 11);
INSERT INTO field (field_id, type_id) VALUES ('user_id', 12);
INSERT INTO field (field_id, type_id) VALUES ('user_id', 14);
INSERT INTO field (field_id, type_id) VALUES ('user_id', 15);
INSERT INTO field (field_id, type_id) VALUES ('user_id', 18);
INSERT INTO field (field_id, type_id) VALUES ('user_id', 24);



/*==============*/
/*inserting type*/
/*==============*/
INSERT INTO note.educationnal_goal_type(eg_type_id, label, registration, user_id) VALUES (DEFAULT, 'Bacc', DEFAULT, 1);
INSERT INTO note.educationnal_goal_type(eg_type_id, label, registration, user_id) VALUES (DEFAULT, 'Session', DEFAULT, 1);
INSERT INTO note.educationnal_goal_type(eg_type_id, label, registration, user_id) VALUES (DEFAULT, 'Ap', DEFAULT, 1);
INSERT INTO note.educationnal_goal_type(eg_type_id, label, registration, user_id) VALUES (DEFAULT, 'Compétence', DEFAULT, 1);
INSERT INTO note.educationnal_goal_type(eg_type_id, label, registration, user_id) VALUES (DEFAULT, 'Unité', DEFAULT, 1);


/*==============*/
/*inserting bacc*/
/*==============*/

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'GE', 'Génie électrique', '', 0, now(), DEFAULT, now(), 1, 1);
INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'GI', 'Génie informatique', '', 0, now(), DEFAULT, now(), 1, 1);

/*=================*/
/*inserting session*/
/*=================*/


INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gegis1', 'Session 1', '', 0, now(), DEFAULT, now(), 1, 2); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GI'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GE');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'ges2', 'Session 2', '', 0, now(), DEFAULT, now(), 1, 2); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GE');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gis2', 'Sesion 2', '', 0, now(), DEFAULT, now(), 1, 2); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GI');
INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'ges3', 'Session 3', '', 0, now(), DEFAULT, now(), 1, 2); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GE');
INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gis3', 'Session 3', '', 0, now(), DEFAULT, now(), 1, 2); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GI');
INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'ges4', 'Session 4', '', 0, now(), DEFAULT, now(), 1, 2); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GE');
INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gis4', 'Session 4', '', 0, now(), DEFAULT, now(), 1, 2); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GI');
INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'ges5', 'Session 5', '', 0, now(), DEFAULT, now(), 1, 2); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GE');
INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gis5', 'Session 5', '', 0, now(), DEFAULT, now(), 1, 2); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GI');
INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'ges6', 'Session 6', '', 0, now(), DEFAULT, now(), 1, 2); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GE');
INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gis6', 'Session 6', '', 0, now(), DEFAULT, now(), 1, 2); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GI');

/*=============*/
/*inserting app*/
/*=============*/

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gegis1app1', 'APP 1', 'Réalisation et mesure de circuits électriques', 0, now(), DEFAULT, now(), 1, 5); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gegis1app2', 'APP 2', 'Programmation et algorithmes', 0, now(), DEFAULT, now(), 1, 5); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gegis1app3', 'APP 3', 'Circuits et physique des semi-conducteurs', 0, now(), DEFAULT, now(), 1, 5); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gegis1app4', 'APP 4', 'Circuits, systèmes du 1er ordre et physique des semi-conducteurs', 0, now(), DEFAULT, now(), 1, 5); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gegis1app5', 'APP 5', 'Atelier de programmation', 0, now(), DEFAULT, now(), 1, 5); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gegis1app6', 'APP 6', 'Circuits, systèmes du 2ème ordre et physique des semi-conducteurs', 0, now(), DEFAULT, now(), 1, 5); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gegis1proj', 'Projet', 'Concours robot-jouet', 0, now(), DEFAULT, now(), 1, 5); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');


/*============*/
/*inserting ap*/
/*============*/

INSERT INTO note.educationnal_goal(eg_id,  label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen101', 'GEN 101', 'Résolution de problème et conception en génie', 0, now(), DEFAULT, now(), 1, 3); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GE'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GI'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen111', 'GEN 111', 'La communication et le travail en équipe', 0, now(), DEFAULT, now(), 1, 3); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GE'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GI'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen122', 'GEN 122', 'Équations différentielles linéaires', 0, now(), DEFAULT, now(), 1, 3); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GE'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GI'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen135', 'GEN 135', 'Circuits électriques I', 0, now(), DEFAULT, now(), 1, 3); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GE'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GI'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen136', 'GEN 136', 'Circuits électriques II', 0, now(), DEFAULT, now(), 1, 3); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GE'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GI'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen143', 'GEN 143', 'Introduction à la programmation', 0, now(), DEFAULT, now(), 1, 3); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GE'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GI'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen144', 'GEN 144', 'Programmation et algorithmes', 0, now(), DEFAULT, now(), 1, 3); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GE'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GI'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen145', 'GEN 145', 'Atelier de programmation', 0, now(), DEFAULT, now(), 1, 3); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GE'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GI'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen150', 'GEN 150', 'Physique des semi-conducteurs I', 0, now(), DEFAULT, now(), 1, 3); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GE'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GI'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen170', 'GEN 170', 'Réalisation et mesure de circuits électriques', 0, now(), DEFAULT, now(), 1, 3); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GE'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'GI'); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gegis1');


/*================*/
/*inserting sub-ap*/
/*================*/

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen101-1', 'GEN 101-1', 'Compétence 1', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen101');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen101-2', 'GEN 101-2', 'Compétence 2', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen101');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen101-3', 'GEN 101-3', 'Compétence 3', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen101');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen111-1', 'GEN 111-1', 'Compétence 1', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen111');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen111-2', 'GEN 111-2', 'Compétence 2', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen111');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen111-3', 'GEN 111-3', 'Compétence 3', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen111');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen122-1', 'GEN 122-1', 'Compétence 1', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen122');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen122-2', 'GEN 122-2', 'Compétence 2', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen122');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen135-1', 'GEN 135-1', 'Compétence 1', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen135');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen136-1', 'GEN 136-1', 'Compétence 1', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen136');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen136-2', 'GEN 136-2', 'Compétence 2', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen136');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen143-1', 'GEN 143-1', 'Compétence 1', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen143');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen144-1', 'GEN 144-1', 'Compétence 1', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen144');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen145-1', 'GEN 145-1', 'Compétence 1', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen145');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen150-1', 'GEN 150-1', 'Compétence 1', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen150');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen170-1', 'GEN 170-1', 'Compétence 1', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen170');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen170-2', 'GEN 170-2', 'Compétence 2', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen170');

INSERT INTO note.educationnal_goal(eg_id, label, short_description, description, administrative_value, validity_start, validity_end, registration, user_id, eg_type_id) VALUES (DEFAULT, 'gen170-3', 'GEN 170-3', 'Compétence 3', 0, now(), DEFAULT, now(), 1, 4); INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = 'gen170');


/*=====================*/
/*inserting other stuff*/
/*=====================*/


INSERT INTO note.evaluation_type(evaluation_type_id, label, registration, user_id) VALUES(1, 'Session', '2008-08-19', 1),
                                                                                         (2, 'Final', '2008-08-19', 1),
                                                                                         (3, 'Validation', '2008-08-19', 1),
                                                                                         (4, 'Particuliére', '2008-08-19', 1),
                                                                                         (5, 'Évaluation par les pairs', '2012-04-26', 1);
INSERT INTO note.quality(quality_id, label, description, validity_start, validity_end, registration, user_id) VALUES (1,  'Q1 - Connaissances en génie', '', now(), DEFAULT, now(), 1),
                                                                                                                     (2,  'Q2 - Analyse de problémes', '', now(), DEFAULT, now(), 1),
                                                                                                                     (3,  'Q3 - Investigation', '', now(), DEFAULT, now(), 1),
                                                                                                                     (4,  'Q4 - Conception', '', now(), DEFAULT, now(), 1),
                                                                                                                     (5,  'Q5 - Utilisation d''outils d''ingénérie', '', now(), DEFAULT, now(), 1),
                                                                                                                     (6,  'Q6 - Travail individuel et en équipe', '', now(), DEFAULT, now(), 1),
                                                                                                                     (7,  'Q7E - Communication écrite', '', now(), DEFAULT, now(), 1),
                                                                                                                     (8,  'Q7O - Communication orale', '', now(), DEFAULT, now(), 1),
                                                                                                                     (9,  'Q8 - Professionnalisme', '', now(), DEFAULT, now(), 1),
                                                                                                                     (10, 'Q9 - Impacte du génie sur la société et l''environnement', '', now(), DEFAULT, now(), 1),
                                                                                                                     (11, 'Q10 - Déontologie et équité', '', now(), DEFAULT, now(), 1),
                                                                                                                     (12, 'Q11 - économie et gestion de projets', '', now(), DEFAULT, now(), 1),
                                                                                                                     (13, 'Q12 - Apprentissage continu', '', now(), DEFAULT, now(), 1);
INSERT INTO note.possible_score(score_id, description, user_id, registration) VALUES ('A+', 'Cote standard'                , 1, now()),
                                                                                     ('A' , 'Cote standard'                , 1, now()),
                                                                                     ('A-', 'Cote standard'                , 1, now()),
                                                                                     ('B+', 'Cote standard'                , 1, now()),
                                                                                     ('B' , 'Cote standard'                , 1, now()),
                                                                                     ('B-', 'Cote standard'                , 1, now()),
                                                                                     ('C+', 'Cote standard'                , 1, now()),
                                                                                     ('C' , 'Cote standard'                , 1, now()),
                                                                                     ('C-', 'Cote standard'                , 1, now()),
                                                                                     ('D+', 'Cote standard'                , 1, now()),
                                                                                     ('D' , 'Cote standard'                , 1, now()),
                                                                                     ('E' , 'Cote standard'                , 1, now()),
                                                                                     ('IN', 'Incomplet'                    , 1, now()),
                                                                                     ('W' , 'Échec par abandon'            , 1, now()),
                                                                                     ('R' , 'Réussit'                      , 1, now()),
                                                                                     ('EA', 'Équivalence par autorisation' , 1, now()),
                                                                                     ('EQ', 'Équivalence'                  , 1, now()),
                                                                                     ('ND', 'Non disponible'               , 1, now()),
                                                                                     ('RP', 'Reprise'                      , 1, now()),
                                                                                     ('NT', 'Non terminé'                  , 1, now()),
                                                                                     ('XS', 'Substitution'                 , 1, now()),
                                                                                     ('SE', 'Sans évaluation'              , 1, now()),
                                                                                     ('AB', 'Abandon'                      , 1, now());
INSERT INTO note.score_group(score_group_id, user_id, label, registration) VALUES (DEFAULT, 1, 'Seuils de cote pour le BAC.', now());
INSERT INTO note.score_group_definition(score_group_id, score_id, user_id, default_value, registration) VALUES(1, 'A+', 1, 85.0, now()),
                                                                                                              (1, 'A' , 1, 81.0, now()),
                                                                                                              (1, 'A-', 1, 78.0, now()),
                                                                                                              (1, 'B+', 1, 75.0, now()),
                                                                                                              (1, 'B' , 1, 71.0, now()),
                                                                                                              (1, 'B-', 1, 68.0, now()),
                                                                                                              (1, 'C+', 1, 64.0, now()),
                                                                                                              (1, 'C' , 1, 60.0, now()),
                                                                                                              (1, 'C-', 1, 57.0, now()),
                                                                                                              (1, 'D+', 1, 53.0, now()),
                                                                                                              (1, 'D' , 1, 50.0, now()),
                                                                                                              (1, 'E' , 1,  0.0, now()),
                                                                                                              (1, 'IN', 1, null, now()),
                                                                                                              (1, 'W' , 1, null, now()),
                                                                                                              (1, 'R' , 1, null, now()),
                                                                                                              (1, 'NT', 1, null, now()),
                                                                                                              (1, 'AB', 1, null, now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'E08', '2008-04-25', '2008-08-22', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'A08', '2008-08-25', '2008-12-19', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'H09', '2009-01-05', '2009-04-24', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'E09', '2009-04-27', '2009-08-22', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'A09', '2009-08-31', '2009-12-23', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'H10', '2010-01-05', '2010-04-24', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'E10', '2010-04-26', '2010-08-16', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'A02', '2002-08-28', '2002-12-20', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'A03', '2003-08-25', '2003-12-19', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'H03', '2003-01-06', '2003-04-11', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'A06', '2006-08-28', '2006-12-22', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'H06', '2006-01-03', '2006-04-28', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'E02', '2002-04-29', '2002-08-09', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'E05', '2005-05-02', '2005-08-15', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'H02', '2004-02-03', '2004-02-03', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'A04', '2004-08-30', '2004-12-23', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'E03', '2003-05-05', '2003-08-15', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'E06', '2006-05-01', '2006-08-14', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'A07', '2007-08-27', '2007-12-21', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'E07', '2007-04-30', '2007-08-13', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'H05', '2005-01-05', '2005-04-29', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'H07', '2007-01-03', '2006-04-28', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'H04', '2004-11-16', '2004-11-18', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'E04', '2004-05-03', '2004-08-13', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'A01', '2009-08-27', '2009-08-27', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'A05', '2009-08-27', '2009-08-27', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'A10', '2010-08-27', '2010-12-23', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'E01', '2009-08-27', '2009-08-27', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'H11', '2011-01-03', '2011-04-29', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'E11', '2011-05-02', '2011-08-19', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'A11', '2011-08-29', '2011-12-23', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'H12', '2012-01-04', '2012-04-30', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'A12', '2012-08-27', '2012-12-23', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'E12', '2012-04-30', '2012-08-22', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'H13', '2013-01-07', '2013-04-26', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'H08', '2008-01-01', '2008-05-01', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'E13', '2013-04-29', '2013-08-16', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'A13', '2013-08-26', '2013-12-23', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'H14', '2014-01-03', '2014-04-26', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'E14', '2014-04-28', '2014-08-15', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'A14', '2014-08-25', '2014-12-23', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'H15', '2015-01-05', '2015-04-24', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'E15', '2015-04-27', '2015-08-14', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'A15', '2015-08-24', '2015-12-23', now());
INSERT INTO note.timespan(timespan_id, user_id, label, start_date, end_date, registration) VALUES (DEFAULT, 1, 'H16', '2016-01-04', '2016-05-25', now());

INSERT INTO note.indicator_type(indicator_type_id, label, description, registration, user_id) VALUES (DEFAULT, 'ETUDIANT', 'etudiant', now(), 1), (DEFAULT, 'PROFESSEUR', 'professeur', now(), 1);
INSERT INTO note.administrative_element(program_id, label, description, registration, user_id) VALUES (1, 'GEGI', 'Génie électrique et informatique', now(), 1);


/**
	Add educationnal goal instance for group GI58
*/
INSERT INTO note.educationnal_goal_instance(timespan_id, eg_id, user_id) (SELECT t.timespan_id, eg.eg_id, 1 FROM note.timespan t, note.educationnal_goal eg WHERE t.label = 'A14' AND eg.label = 'gegis1');


/**
	Assign group to educationnal goal previously created
*/
INSERT INTO note.assigned_group (timespan_id, eg_id, privilege_id, group_id, user_id) 
	(SELECT egi.timespan_id, egi.eg_id, p.privilege_id, g.group_id, 1
		FROM note.educationnal_goal_instance egi, public.groups g, public.privilege p, note.educationnal_goal eg,  note.timespan t
		WHERE egi.eg_id = eg.eg_id AND eg.label = 'gegis1' AND t.label = 'A12' AND g.label = 'GI58' AND p.label = 'Accès membre');

/**
	Create an evaluation that represent a final exam, lab report, etc
*/
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, 'gegis1_app1_intra', 'App 1 Sommatif', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, 'gegis1_app1_RAPPORT', 'App 1 Rapport', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, 'gegis1_app2_intra', 'App 2 Sommatif', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, 'gegis1_app2_RAPPORT', 'App 2 Rapport', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, 'gegis1_app3_intra', 'App 3 Sommatif', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, 'gegis1_app3_RAPPORT', 'App 3 Rapport', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, 'gegis1_app4_intra', 'App 4 Sommatif', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, 'gegis1_app4_RAPPORT', 'App 4 Rapport', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, 'gegis1_app5_intra', 'App 5 Sommatif', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, 'gegis1_app5_RAPPORT', 'App 5 Rapport', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, 'gegis1_app6_intra', 'App 6 Sommatif', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, 'gegis1_app6_RAPPORT', 'App 6 Rapport', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
