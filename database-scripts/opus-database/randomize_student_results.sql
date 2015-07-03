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




CREATE OR REPLACE VIEW note.v_student_results_by_eg_instance AS
    SELECT evals.eg_instance_id, evals.student_id, evals.evaluation_id, evals.eg_id, result.value, evals.weighting, case when result.evaluation_instance_id is not null then 1 else 0 end as flag 
    FROM
    (SELECT egi.eg_instance_id, evi.evaluation_instance_id, evi.evaluation_id, u_gr.member_id as student_id, c.eg_id, c.weighting
    FROM note.educationnal_goal_instance egi, note.evaluation_instance evi, note.evaluation_rubric ev_ru, note.criterion c, note.assigned_group ag, public.user_group u_gr
    WHERE egi.eg_instance_id = evi.eg_instance_id AND evi.evaluation_id = ev_ru.evaluation_id AND ev_ru.rubric_id = c.rubric_id AND egi.eg_instance_id = ag.eg_instance_id AND ag.group_id = u_gr.group_id) AS evals
    LEFT OUTER JOIN
    (SELECT r.evaluation_instance_id, r.student_id, r.value, r.criterion_id FROM note.result r) AS result
    ON evals.evaluation_instance_id = result.evaluation_instance_id AND evals.student_id = result.student_id
    ORDER BY evals.eg_instance_id, evals.evaluation_id, evals.student_id;


CREATE OR REPLACE VIEW note.v_student_total_results_with_avg_by_eg_instance AS
    SELECT r2.eg_instance_id, r2.evaluation_id, r2.student_id, r2.eg_id, r2.result, r4.average, r2.total, r2.flag
    FROM (SELECT r.eg_instance_id, r.evaluation_id, r.student_id, r.eg_id, case when r.flag = 0 then 0 else SUM(r.value) end AS result, SUM(r.weighting)  AS total, r.flag
         FROM note.v_student_results_by_eg_instance r 
         GROUP BY r.eg_id, r.eg_instance_id, r.student_id, r.evaluation_id, r.flag 
         ORDER BY r.evaluation_id, r.student_id, r.eg_id) r2, 
         (SELECT r3.eg_instance_id, r3.evaluation_id, r3.eg_id, AVG(r3.result) AS average 
          FROM  (SELECT r.eg_instance_id, r.evaluation_id, r.student_id, r.eg_id, case when r.flag = 0 then 0 else SUM(r.value) end AS result, SUM(r.weighting)  AS total
                FROM note.v_student_results_by_eg_instance r 
                GROUP BY r.eg_id, r.eg_instance_id, r.student_id, r.evaluation_id
                ORDER BY r.evaluation_id, r.student_id, r.eg_id) r3
          GROUP BY r3.eg_instance_id, r3.evaluation_id, r3.eg_id 
          ORDER BY r3.eg_instance_id, r3.evaluation_id, r3.eg_id) r4
    WHERE r2.eg_instance_id = r4.eg_instance_id AND r2.evaluation_id = r4.evaluation_id AND r2.eg_id = r4.eg_id
    ORDER BY r2.eg_instance_id, r2.evaluation_id, r2.student_id, r2.eg_id;


SELECT r.eg_instance_id, r.evaluation_id, r.student_id, r.eg_id, case when r.flag = 0 then 0 else SUM(r.value) end AS result, SUM(r.weighting) AS total, r.flag
FROM note.v_student_results_by_eg_instance r 
GROUP BY r.eg_id, r.eg_instance_id, r.student_id, r.evaluation_id, r.flag 
ORDER BY r.evaluation_id, r.student_id, r.eg_id;

SELECT ev.short_description, ap_subap_h.ap_desc, ap_subap_h.subap_desc, r.result::int, r.average, r.total::int, 1
    FROM note.v_student_total_results_with_avg_by_eg_instance r, note.evaluation ev, public.users u, note.v_ap_subap_hierarchy ap_subap_h
    WHERE r.evaluation_id = ev.evaluation_id AND r.student_id = u.user_id AND u.administrative_user_id = 'foum2413' AND
          r.eg_id = ap_subap_h.subap_id AND r.eg_instance_id = 3;