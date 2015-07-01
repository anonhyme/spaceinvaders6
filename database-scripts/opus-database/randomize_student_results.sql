CREATE OR REPLACE FUNCTION note.randomize_student_result(eg_instance_id int, min_value double precision) RETURNS VOID AS $$
DECLARE
    eval RECORD;
    criterion RECORD;
    student RECORD;
    max_result int;
    result int;
BEGIN
    RAISE NOTICE 'Fetching evals...';

    FOR eval IN SELECT * FROM note.evaluation_instance evi WHERE evi.eg_instance_id = $1 LOOP

        RAISE NOTICE 'Eval id is:%', to_char(eval.evaluation_instance_id, '999');


        FOR criterion IN 
        SELECT c.criterion_id, ru.statement, c.weighting
        FROM note.evaluation_instance evi, note.evaluation ev, note.evaluation_rubric ev_ru, note.rubric ru, note.criterion c 
        WHERE evi.evaluation_id = ev.evaluation_id AND ev.evaluation_id = ev_ru.evaluation_id AND ev_ru.rubric_id = ru.rubric_id AND evi.evaluation_instance_id = eval.evaluation_instance_id AND ru.rubric_id = c.rubric_id 
        LOOP

        	RAISE NOTICE '	Rubric label is:%', criterion.statement;
        	max_result := criterion.weighting;

        	FOR student IN 
        	SELECT u.user_id, u.administrative_user_id
			FROM note.educationnal_goal_instance egi, note.assigned_group ag, public.user_group u_gr, public.users u
			WHERE egi.eg_instance_id = $1 AND ag.eg_instance_id = egi.eg_instance_id AND ag.group_id = u_gr.group_id AND u_gr.member_id = u.user_id LOOP

				result = round((random()*max_result*(1.0-min_value)) + (min_value*max_result))::integer;
        		RAISE NOTICE '		% had %', student.administrative_user_id, result;

        		INSERT INTO note.result (criterion_id, evaluation_instance_id, student_id, value, user_id)
				SELECT criterion.criterion_id, eval.evaluation_instance_id, student.user_id, result, 1;
        	
    		END LOOP;
        
    	END LOOP;
        
    END LOOP;

    RAISE NOTICE 'Done fetching evaluation instance for educational goal instance.';
    RETURN;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION note.insert_educ_goal_with_hierarchy(eg_label text, eg_short_desc text, eg_description text, eg_type_label text, eg_hierarchy text) RETURNS VOID AS $$
DECLARE
    eg_array text ARRAY;
    eg text;
BEGIN
	eg_array = regexp_split_to_array(eg_hierarchy, ',');

	INSERT INTO note.educationnal_goal(label, short_description, description, validity_start, user_id, eg_type_id)
	SELECT $1, $2, $3, now(), 1, egt.eg_type_id
		FROM note.educationnal_goal_type egt
		WHERE egt.label = $4;

    FOREACH eg IN ARRAY eg_array LOOP
    	INSERT INTO note.educationnal_goal_hierarchy (SELECT a.eg_id, b.eg_id, now(), 1 FROM note.educationnal_goal a JOIN ( select last_value as eg_id from note.educationnal_goal_eg_id_seq ) b ON 1=1 WHERE a.label = eg);
    	RAISE NOTICE 'Made % parent of %', eg, $1;
    END LOOP;

    RETURN;
END;
$$ LANGUAGE plpgsql;

SELECT note.insert_educ_goal_with_hierarchy('gen600', 'blablabla', 'more blablabla', 'Ap', 'GI,gis6');

