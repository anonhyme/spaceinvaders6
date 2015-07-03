
/**
	Create an evaluation that represent a final exam, lab report, etc.
	Then create question for that exam and bind it using the evaluation_rubric association table.
	Then create criterion that represent the weight of a particular question on an AP
*/
INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gis3_app1_rapport', 'App1-Rapport', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Rapport' AND eg.label = 'gis3');
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gis3_app1_rapport_q1', 'Question 1', 'gif310-1', 20);
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gis3_app1_rapport_q2', 'Question 2', 'gif310-2', 70);
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gis3_app1_rapport_q3', 'Question 3', 'gif362-1', 16);
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gis3_app1_rapport_q4', 'Question 4', 'gif362-2', 16);

INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gis3_app1_somatif', 'App1-Examen sommatif', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Somatif' AND eg.label = 'gis3');
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gis3_app1_somatif_q1', 'Question 1', 'gif310-1', 40);
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gis3_app1_somatif_q2', 'Question 2', 'gif310-2', 50);
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gis3_app1_somatif_q5', 'Question 5', 'gif310-2', 90);
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gis3_app1_somatif_q3', 'Question 3', 'gif362-1', 32);
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gis3_app1_somatif_q4', 'Question 4', 'gif362-2', 32);


INSERT INTO note.evaluation (evaluation_type_id, eg_id, validity_start, label, short_description, user_id)
	(SELECT et.evaluation_type_id, eg.eg_id, now(), 'gis3_app2_rapport', 'App2-Rapport', 1
	FROM note.evaluation_type et, note.educationnal_goal eg
	WHERE et.label = 'Rapport' AND eg.label = 'gis3');
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gis3_app2_rapport_q1', 'Question 1', 'gif331-1', 40);
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gis3_app2_rapport_q2', 'Question 2', 'gif331-2', 40);
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gis3_app2_rapport_q6', 'Question 6', 'gif331-2', 30);
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gis3_app2_rapport_q3', 'Question 3', 'gif331-3', 20);
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gis3_app2_rapport_q4', 'Question 4', 'gif362-1', 16);
SELECT note.create_rubric_and_criterion((SELECT last_value FROM note.evaluation_evaluation_id_seq)::integer, 'gis3_app2_rapport_q5', 'Question 5', 'gif362-2', 16);


/**
	Create an evaluation instance
*/
SELECT note.create_eval_inst_and_group('gis3_app1_rapport', 'A13', 'gis3', 'GI58', '2013-09-01', '04000001');
SELECT note.create_eval_inst_and_group('gis3_app1_somatif', 'A13', 'gis3', 'GI58', '2012-09-02', '04000001');

/**
		Give student their note
 */
SELECT note.randomize_student_result(3, 0.5);

SELECT note.create_eval_inst_and_group('gis3_app2_rapport', 'A13', 'gis3', 'GI58', '2012-09-03', '04000001');