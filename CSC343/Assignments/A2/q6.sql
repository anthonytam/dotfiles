SET search_path TO markus;

DROP TABLE IF EXISTS q6;
CREATE TABLE q6 (
	group_id integer,
	first_file varchar(25),
	first_time timestamp,
	first_submitter varchar(25),
	last_file varchar(25),
	last_time timestamp, 
	last_submitter varchar(25),
	elapsed_time interval
);

DROP VIEW IF EXISTS A1Groups CASCADE;
CREATE VIEW A1Groups AS
       SELECT group_id, file_name, username, submission_date
       FROM AssignmentGroup NATURAL JOIN Assignment NATURAL LEFT JOIN Submissions
       WHERE description = 'A1';

DROP VIEW IF EXISTS FirstSubmittedFile CASCADE;
CREATE VIEW FirstSubmittedFile AS
       SELECT a.group_id, a.file_name AS first_file, a.submission_date AS first_time, a.username AS first_submitter
       FROM A1Groups a
       WHERE a.submission_date <= ALL ( SELECT ag.submission_date
                                        FROM A1Groups ag
                                        WHERE ag.group_id = a.group_id ) OR a.file_name IS NULL;

DROP VIEW IF EXISTS LastSubmittedFile CASCADE;
CREATE VIEW LastSubmittedFile AS
       SELECT a.group_id, a.file_name AS last_file, a.submission_date AS last_time, a.username AS last_submitter
       FROM A1Groups a
       WHERE a.submission_date >= ALL ( SELECT ag.submission_date
                                        FROM A1Groups ag
                                        WHERE ag.group_id = a.group_id ) OR a.file_name IS NULL;

DROP VIEW IF EXISTS Solution CASCADE;
CREATE VIEW Solution AS
       SELECT group_id, first_file, first_time, first_submitter, last_file, last_time, last_submitter, last_time - first_time AS elapsed_time
       FROM FirstSubmittedFile NATURAL JOIN LastSubmittedFile;

INSERT INTO q6 ( SELECT * FROM Solution );
