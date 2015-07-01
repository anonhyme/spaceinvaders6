CREATE OR REPLACE FUNCTION note.randomize_student_result(eg_instance_id int) RETURNS VOID AS $$
DECLARE
    eval RECORD;
BEGIN
    RAISE NOTICE 'Fetching evals...';

    FOR eval IN SELECT * FROM note.evaluation_instance evi WHERE evi.eg_instance_id = $1 LOOP

        RAISE NOTICE 'Eval id is:%', to_char(eval.evaluation_instance_id, '999');
        
    END LOOP;

    RAISE NOTICE 'Done fetching evaluation instance for educational goal instance.';
    RETURN;
END;
$$ LANGUAGE plpgsql;

SELECT note.randomize_student_result(1);