
-- Add all these things to note schema
SET SEARCH_PATH TO note;

--
-- AP types and procedures
--
DROP TYPE ap_summary_t CASCADE;

CREATE TYPE ap_summary_t AS (ap_id int, ap_name text, ap_result_percent int);

CREATE OR REPLACE FUNCTION get_ap_results(student_id text, session_id text) RETURNS SETOF ap_summary_t AS $$
    -- TODO : CREATE SELECT STATEMENT
    SELECT 1, 'GEN501', 80
    UNION ALL SELECT 2, 'GEN402', 75
    UNION ALL SELECT 3, 'GEN666', 42;
$$ LANGUAGE SQL;

-- AP procedure example
-- SELECT * FROM get_ap_results('bedh2102', 'S5I');


--
-- Semester types and procedures
--
DROP TYPE evaluation_results_t CASCADE;

CREATE TYPE evaluation_results_t AS (
  eval_id int,
  eval_label text,
  course_label text,
  competence_label text,
  result_value int,
  avg_result_value int,
  max_result_value int,
  standard_dev int
);

CREATE OR REPLACE FUNCTION get_semester_results(student_id text, session_id text) RETURNS SETOF evaluation_results_t AS $$
  -- TODO : CREATE SELECT STATEMENT
  SELECT 1, 'Sommatif APP2', 'GEN501', 'GEN501-1', 80, 75, 120, 6
  UNION ALL SELECT 2, 'Sommatif APP2', 'GEN501', 'GEN501-2', 56, 50, 70, 5
  UNION ALL SELECT 3, 'Sommatif APP2', 'GEN402', 'GEN402-1', 74, 70, 75, 11
  UNION ALL SELECT 4, 'Sommatif APP3', 'GEN666', 'GEN501-1', 80, 75, 85, 3
  UNION ALL SELECT 5, 'Sommatif APP3', 'GEN666', 'GEN402-2', 80, 73, 110, 4;
$$ LANGUAGE SQL;

-- Semester procedure example
-- SELECT * FROM get_semester_results('bedh2102', 'S5I');


--
-- Course types and procedures
--
DROP TYPE competence_results_t CASCADE;

CREATE TYPE competence_results_t AS (
  eval_id int,
  eval_label text,
  competence_label text,
  result_value int,
  avg_result_value int,
  max_result_value int,
  standard_dev int,
  cumulated_frequency_percent int -- how many points compared to the max possible number at this moment
);

CREATE OR REPLACE FUNCTION get_course_results(student_id text, session_id text) RETURNS SETOF competence_results_t AS $$
  -- TODO : CREATE SELECT STATEMENT
  SELECT 1, 'Sommatif APP2', 'GEN501-1', 80, 75, 120, 6, 80
  UNION ALL SELECT 2, 'Sommatif APP2', 'GEN501-2', 56, 50, 70, 5, 85;
$$ LANGUAGE SQL;

-- Course procedure example
-- SELECT * FROM get_course_results('bedh2102', 'S5I');