TRUNCATE public.users RESTART IDENTITY CASCADE;
TRUNCATE content.type RESTART IDENTITY CASCADE;


-- SCHEMA PUBLIC
set search_path to public;


-- USER

INSERT INTO users (administrative_user_id, last_name, first_name, email_address) VALUES ('geim9002', 'Super', 'Admin', 'Admin.Super@USherbrooke.ca');
INSERT INTO users (administrative_user_id, last_name, first_name, email_address) VALUES ('maip2202', 'Maillé', 'Pascale', 'Pascale.Maille@USherbrooke.ca');

ALTER SEQUENCE public.users_user_id_seq RESTART WITH 3;


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

ALTER SEQUENCE public.groups_group_id_seq RESTART WITH 3;


-- ADMINISTRATOR_GROUP

INSERT INTO administrator_group (group_id, administrator_id, user_id) VALUES (1, 1, 1);
INSERT INTO administrator_group (group_id, administrator_id, user_id) VALUES (2, 1, 1);


-- USER_GROUP

INSERT INTO user_group (member_id, group_id, user_id) VALUES (1, 1, 1);
INSERT INTO user_group (member_id, group_id, user_id) VALUES (1, 2, 1);
INSERT INTO user_group (member_id, group_id, user_id) VALUES (2, 1, 1);
INSERT INTO user_group (member_id, group_id, user_id) VALUES (2, 2, 1);


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