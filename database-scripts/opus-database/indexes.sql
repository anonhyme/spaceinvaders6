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
