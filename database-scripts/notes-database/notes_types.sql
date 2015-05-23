-- -- To remove stuff from public if you put them in the wrong schema (should be in notes)
-- SET SEARCH_PATH TO public;
-- DROP TYPE ap_summary_t CASCADE;
-- DROP TYPE evaluation_results_t CASCADE;
-- DROP TYPE competence_results_t CASCADE;
-- DROP FUNCTION get_ap_results(student_id text, session_id text);
-- DROP FUNCTION get_semester_results(student_id text, session_id text);
-- DROP FUNCTION get_course_results(student_id text, session_id text);


-- Add all these things to note schema
SET SEARCH_PATH TO note;

--
-- AP types and procedures
--
-- DROP TYPE ap_summary_t CASCADE;

CREATE TYPE ap_summary_t AS (
  ap_name text,
  result_value int,
  avg_value int,
  max_value int
);

CREATE OR REPLACE FUNCTION get_ap_results(student_id text, session_id int) RETURNS SETOF ap_summary_t AS $$
    -- TODO : CREATE SELECT STATEMENT
    SELECT 'GEN501', 80, 79, 100
    UNION ALL SELECT 'GEN402', 75, 73, 90
    UNION ALL SELECT 'GEN666', 42, 50, 110;
$$ LANGUAGE SQL;

-- AP procedure example
-- SELECT * FROM get_ap_results('bedh2102', 'S5I');


--
-- Semester types and procedures
--
-- DROP TYPE evaluation_results_t CASCADE;

CREATE TYPE evaluation_results_t AS (
  eval_label text,
  course_label text,
  competence_label text,
  result_value int,
  avg_result_value int,
  max_result_value int,
  standard_dev int
);

CREATE OR REPLACE FUNCTION get_semester_results(student_id text, session_id int) RETURNS SETOF evaluation_results_t AS $$
  -- TODO : CREATE SELECT STATEMENT
  SELECT 'Sommatif APP2', 'GEN501', 'GEN501-1', 80, 75, 120, 6
  UNION ALL SELECT 'Sommatif APP2', 'GEN501', 'GEN501-2', 56, 50, 70, 5
  UNION ALL SELECT 'Sommatif APP2', 'GEN402', 'GEN402-1', 74, 70, 75, 11
  UNION ALL SELECT 'Sommatif APP3', 'GEN666', 'GEN501-1', 80, 75, 85, 3
  UNION ALL SELECT 'Sommatif APP3', 'GEN666', 'GEN402-2', 80, 73, 110, 4;
$$ LANGUAGE SQL;

-- Semester procedure example
-- SELECT * FROM get_semester_results('bedh2102', 'S5I');


--
-- Course types and procedures
--
-- DROP TYPE competence_results_t CASCADE;

CREATE TYPE competence_results_t AS (
  eval_label text,
  competence_label text,
  result_value int,
  avg_result_value int,
  max_result_value int,
  standard_dev int,
  cumulated_frequency_percent int -- how many points compared to the max possible number at this moment
);

CREATE OR REPLACE FUNCTION get_course_results(student_id text, session_id int) RETURNS SETOF competence_results_t AS $$
  -- TODO : CREATE SELECT STATEMENT
  SELECT 'Sommatif APP2', 'GEN501-1', 80, 75, 120, 6, 80
  UNION ALL SELECT 'Sommatif APP2', 'GEN501-2', 56, 50, 70, 5, 85;
$$ LANGUAGE SQL;

-- Course procedure example
-- SELECT * FROM get_course_results('bedh2102', 'S5I');