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