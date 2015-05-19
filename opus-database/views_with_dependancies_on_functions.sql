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