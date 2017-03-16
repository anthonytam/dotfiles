SET search_path TO markus;
-- If there is already any data in these tables, empty it out.

TRUNCATE TABLE Result CASCADE;
TRUNCATE TABLE Grade CASCADE;
TRUNCATE TABLE RubricItem CASCADE;
TRUNCATE TABLE Grader CASCADE;
TRUNCATE TABLE Submissions CASCADE;
TRUNCATE TABLE Membership CASCADE;
TRUNCATE TABLE AssignmentGroup CASCADE;
TRUNCATE TABLE Required CASCADE;
TRUNCATE TABLE Assignment CASCADE;
TRUNCATE TABLE MarkusUser CASCADE;


-- Import 1
INSERT INTO MarkusUser VALUES ('i1', 'iln1', 'ifn1', 'instructor');
INSERT INTO MarkusUser VALUES ('s1', 'sln1', 'sfn1', 'student');
INSERT INTO MarkusUser VALUES ('s2', 'sln2', 'sfn2', 'student');
INSERT INTO MarkusUser VALUES ('s3', 'sln1', 'sfn1', 'student');
INSERT INTO MarkusUser VALUES ('t1', 'tln1', 'tfn1', 'TA');
INSERT INTO MarkusUser VALUES ('t2', 'tln2', 'tfn2', 'TA');
INSERT INTO MarkusUser VALUES ('t3', 'tln3', 'tfn3', 'TA');

INSERT INTO Assignment VALUES (1000, 'a1', '2017-02-08 20:00', 1, 2);
INSERT INTO Assignment VALUES (1001, 'a2', '2017-02-18 20:00', 1, 2);

INSERT INTO AssignmentGroup VALUES (2000, 1000, 'repo_url');
INSERT INTO AssignmentGroup VALUES (2001, 1000, 'repo_url');

INSERT INTO AssignmentGroup VALUES (2002, 1001, 'repo_url');
INSERT INTO AssignmentGroup VALUES (2003, 1001, 'repo_url');

INSERT INTO Membership VALUES ('s1', 2000);
INSERT INTO Membership VALUES ('s2', 2000);

INSERT INTO Membership VALUES ('s3', 2001);

INSERT INTO Membership VALUES ('s1', 2002);
INSERT INTO Membership VALUES ('s2', 2002);

INSERT INTO Membership VALUES ('s3', 2003);

INSERT INTO Grader VALUES (2000, 't1');
INSERT INTO Grader VALUES (2001, 't2');
INSERT INTO Grader VALUES (2003, 't1');
INSERT INTO Grader VALUES (2002, 't2');
