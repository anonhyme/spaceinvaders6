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

ALTER TABLE ONLY user_group
    ADD CONSTRAINT fk_user_g_links_group FOREIGN KEY (group_id) REFERENCES groups(group_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY user_group
    ADD CONSTRAINT fk_user_g_links_member FOREIGN KEY (member_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY user_group
    ADD CONSTRAINT fk_user_g_added_by_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT;
