
/**
	Create an evaluation that represent a final exam, lab report, etc.
	Then create question for that exam and bind it using the evaluation_rubric association table.
	Then create criterion that represent the weight of a particular question on an AP
*/
INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gegis1_app1_intra', 'App 1 Sommatif', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');

INSERT INTO note.rubric (label, statement, validity_start, user_id) VALUES ('gegis1_app1_intra_q1', 'Question 1', now(), 1);
INSERT INTO note.evaluation_rubric (evaluation_id, rubric_id, user_id)
	SELECT ev.evaluation_id, r.rubric_id, 1
	FROM 
		(SELECT last_value AS evaluation_id from note.evaluation_evaluation_id_seq ) ev, 
		(SELECT last_value AS rubric_id from note.rubric_rubric_id_seq ) r;

INSERT INTO note.criterion(rubric_id, eg_id, weighting, validity_start, user_id)
    SELECT r.rubric_id, eg.eg_id, 80, now(), 1
    FROM (SELECT last_value AS rubric_id from note.rubric_rubric_id_seq ) r,
         note.educationnal_goal eg
    WHERE eg.label = 'gen111-2';

INSERT INTO note.rubric (label, statement, validity_start, user_id) VALUES ('gegis1_app1_intra_q2', 'Question 2', now(), 1);
INSERT INTO note.evaluation_rubric (evaluation_id, rubric_id, user_id)
	SELECT ev.evaluation_id, r.rubric_id, 1
	FROM 
		(SELECT last_value AS evaluation_id from note.evaluation_evaluation_id_seq ) ev, 
		(SELECT last_value AS rubric_id from note.rubric_rubric_id_seq ) r;

INSERT INTO note.criterion(rubric_id, eg_id, weighting, validity_start, user_id)
    SELECT r.rubric_id, eg.eg_id, 80, now(), 1
    FROM (SELECT last_value AS rubric_id from note.rubric_rubric_id_seq ) r,
         note.educationnal_goal eg
    WHERE eg.label = 'gen111-1';

INSERT INTO note.rubric (label, statement, validity_start, user_id) VALUES ('gegis1_app1_intra_q3', 'Question 3', now(), 1);
INSERT INTO note.evaluation_rubric (evaluation_id, rubric_id, user_id)
	SELECT ev.evaluation_id, r.rubric_id, 1
	FROM 
		(SELECT last_value AS evaluation_id from note.evaluation_evaluation_id_seq ) ev, 
		(SELECT last_value AS rubric_id from note.rubric_rubric_id_seq ) r;

INSERT INTO note.criterion(rubric_id, eg_id, weighting, validity_start, user_id)
    SELECT r.rubric_id, eg.eg_id, 80, now(), 1
    FROM (SELECT last_value AS rubric_id from note.rubric_rubric_id_seq ) r,
         note.educationnal_goal eg
    WHERE eg.label = 'gen111-1';

INSERT INTO note.evaluation_instance (evaluation_id, eg_instance_id, employee_id, occurence, registration, user_id)
  SELECT ev.evaluation_id, egi.eg_instance_id, e.user_id, '2012-10-12', t.start_date, 1
  FROM note.evaluation ev, note.timespan t, public.employee e, note.educationnal_goal_instance egi, note.educationnal_goal eg
  WHERE egi.timespan_id = t.timespan_id AND t.label = 'A12' AND egi.eg_id = eg.eg_id AND eg.label = 'gegis1' AND
  		ev.label = 'gegis1_app1_intra' AND e.employee_id = '04000001';

INSERT INTO note.evaluated_group(evaluation_instance_id, group_id, registration, user_id)
      SELECT evi.evaluation_instance_id, g.group_id, now(), 1
      FROM note.get_eval_inst_id_with_eval_label('gegis1_app1_intra', 'A12', 'gegis1') AS evi(evaluation_instance_id),
      	   public.groups g
      WHERE g.label = 'GI58';




INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gegis1_app1_rapport', 'App 1 Rapport', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Rapport' AND eg.label = 'gegis1');

INSERT INTO note.rubric (label, statement, validity_start, user_id) VALUES ('gegis1_app1_rapport_q1', 'Question 1', now(), 1);
INSERT INTO note.evaluation_rubric (evaluation_id, rubric_id, user_id)
	SELECT ev.evaluation_id, r.rubric_id, 1
	FROM 
		(SELECT last_value AS evaluation_id from note.evaluation_evaluation_id_seq ) ev, 
		(SELECT last_value AS rubric_id from note.rubric_rubric_id_seq ) r;

INSERT INTO note.criterion(rubric_id, eg_id, weighting, validity_start, user_id)
    SELECT r.rubric_id, eg.eg_id, 40, now(), 1
    FROM (SELECT last_value AS rubric_id from note.rubric_rubric_id_seq ) r,
         note.educationnal_goal eg
    WHERE eg.label = 'gen135-1';


 

INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gegis1_app2_intra', 'App 2 Sommatif', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gegis1_app2_rapport', 'App 2 Rapport', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Rapport' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gegis1_app3_intra', 'App 3 Sommatif', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gegis1_app3_rapport', 'App 3 Rapport', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Rapport' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gegis1_app4_intra', 'App 4 Sommatif', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gegis1_app4_rapport', 'App 4 Rapport', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Rapport' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gegis1_app5_intra', 'App 5 Sommatif', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gegis1_app5_rapport', 'App 5 Rapport', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Rapport' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gegis1_app6_intra', 'App 6 Sommatif', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
 
INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gegis1_app6_rapport', 'App 6 Rapport', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Rapport' AND eg.label = 'gegis1');


/**
		Give student their note
 */

INSERT INTO note.results (criterion_id, evaluation_instance_id, student id, value, user_id)
	SELECT c.criterion_id, evi.evaluation_instance_id, u.user_id, 60, 1
	FROM (SELECT * FROM note.get_criterion_id_with_rubric_label('gegis1_app1_intra_q1')) AS c(criterion_id),
		 (SELECT * FROM note.get_eval_inst_id_with_eval_label('gegis1_app1_intra', 'A12')) AS evi(evaluation_instance_id),
		 public.users u
	WHERE u.administrative_user_id = 'foum2413';

INSERT INTO note.results (criterion_id, evaluation_instance_id, student id, value, user_id)
	SELECT c.criterion_id, evi.evaluation_instance_id, u.user_id, 60, 1
	FROM (SELECT * FROM note.get_criterion_id_with_rubric_label('gegis1_app1_intra_q2')) AS c(criterion_id),
		 (SELECT * FROM note.get_eval_inst_id_with_eval_label('gegis1_app1_intra', 'A12')) AS evi(evaluation_instance_id),
		 public.users u
	WHERE u.administrative_user_id = 'foum2413';

INSERT INTO note.results (criterion_id, evaluation_instance_id, student id, value, user_id)
	SELECT c.criterion_id, evi.evaluation_instance_id, u.user_id, 60, 1
	FROM (SELECT * FROM note.get_criterion_id_with_rubric_label('gegis1_app1_intra_q3')) AS c(criterion_id),
		 (SELECT * FROM note.get_eval_inst_id_with_eval_label('gegis1_app1_intra', 'A12')) AS evi(evaluation_instance_id),
		 public.users u
	WHERE u.administrative_user_id = 'foum2413';






