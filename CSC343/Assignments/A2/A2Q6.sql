SET search_path TO markus;

CREATE VIEW A1Groups (
       SELECT group_id, file_name, username, submission_date
       FROM AssignmentGroup NATURAL JOIN Assignment RIGHT FULL JOIN Submissions
       WHERE description = 'A1'
);

CREATE VIEW FirstSubmittedFile (
       SELECT group_id, filename AS first_file, submission_date AS first_time, username AS first_submitter
       FROM A1Groups
       WHERE submission_date <= ( SELECT submission_date
                                  FROM A1Groups a
                                  WHERE a.group_id = group_id )
);

CREATE VIEW LastSubmittedFile (
       SELECT group_id, filename AS last_file, submission_date AS last_time, username AS last_submitter
       FROM A1Groups
       WHERE submission_date >= ( SELECT submission_date
                                  FROM A1Groups a
                                  WHERE a.group_id = group_id )
);

CREATE VIEW Solution (
       SELECT group_id, first_file, first_time, first_submitter, last_file, last_time, last_submitter, last_time - first_time AS elapsed_time
       FROM FirstSubmittedFile NATURAL JOIN LastSubmittedFile
);

INSERT INTO q6 ( SELECT * FROM Solution );
