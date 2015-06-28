
/**
	Create an evaluation that represent a final exam, lab report, etc.
	Then create question for that exam and bind it using the evaluation_rubric association table.
	Then create criterion that represent the weight of a particular question on an AP
*/
INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gegis1_app1_intra', 'App 1 Sommatif', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Session' AND eg.label = 'gegis1');
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gegis1_app1_intra_q1', 'Question 1', 'gen111-2', 80);
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gegis1_app1_intra_q2', 'Question 2', 'gen111-1', 80);
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gegis1_app1_intra_q3', 'Question 3', 'gen111-1', 70);




INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gegis1_app1_rapport', 'App 1 Rapport', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Rapport' AND eg.label = 'gegis1');
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gegis1_app1_rapport_q1', 'Question 1', 'gen135-1', 72);


 

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
	Create an evaluation instance
*/
SELECT note.create_eval_inst_and_group('gegis1_app1_intra', 'A12', 'gegis1', 'GI58', '2012-10-12', '04000001');

/**
		Give student their note
 */

INSERT INTO note.results (criterion_id, evaluation_instance_id, student id, value, user_id)
	SELECT c.criterion_id, evi.evaluation_instance_id, u.user_id, 60, 1
	FROM (SELECT * FROM note.get_criterion_id_with_rubric_label('gegis1_app1_intra_q1')) AS c(criterion_id),
		 (SELECT * FROM note.get_eval_inst_id_with_eval_label('gegis1_app1_intra', 'A12', 'gegis1')) AS evi(evaluation_instance_id),
		 public.users u
	WHERE u.administrative_user_id = 'foum2413';

INSERT INTO note.results (criterion_id, evaluation_instance_id, student id, value, user_id)
	SELECT c.criterion_id, evi.evaluation_instance_id, u.user_id, 60, 1
	FROM (SELECT * FROM note.get_criterion_id_with_rubric_label('gegis1_app1_intra_q2')) AS c(criterion_id),
		 (SELECT * FROM note.get_eval_inst_id_with_eval_label('gegis1_app1_intra', 'A12', 'gegis1')) AS evi(evaluation_instance_id),
		 public.users u
	WHERE u.administrative_user_id = 'foum2413';

INSERT INTO note.results (criterion_id, evaluation_instance_id, student id, value, user_id)
	SELECT c.criterion_id, evi.evaluation_instance_id, u.user_id, 60, 1
	FROM (SELECT * FROM note.get_criterion_id_with_rubric_label('gegis1_app1_intra_q3')) AS c(criterion_id),
		 (SELECT * FROM note.get_eval_inst_id_with_eval_label('gegis1_app1_intra', 'A12', 'gegis1')) AS evi(evaluation_instance_id),
		 public.users u
	WHERE u.administrative_user_id = 'foum2413';






