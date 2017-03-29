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
INSERT INTO MarkusUser VALUES ('s4', 'sln2', 'sfn2', 'student');
INSERT INTO MarkusUser VALUES ('s5', 'sln1', 'sfn1', 'student');
INSERT INTO MarkusUser VALUES ('s6', 'sln2', 'sfn2', 'student');
INSERT INTO MarkusUser VALUES ('s7', 'sln1', 'sfn1', 'student');
INSERT INTO MarkusUser VALUES ('s8', 'sln2', 'sfn2', 'student');
INSERT INTO MarkusUser VALUES ('s9', 'sln2', 'sfn2', 'student');
INSERT INTO MarkusUser VALUES ('t1', 'tln1', 'tfn1', 'TA');

INSERT INTO Assignment VALUES (1000, 'A1', '2017-02-08 20:00', 1, 2);
INSERT INTO Assignment VALUES (1001, 'A2', '2017-02-18 20:00', 1, 2);

INSERT INTO AssignmentGroup VALUES (2000, 1000, 'repo_url');
INSERT INTO AssignmentGroup VALUES (2001, 1000, 'repo_url');
INSERT INTO AssignmentGroup VALUES (2002, 1000, 'repo_url');
INSERT INTO AssignmentGroup VALUES (2003, 1000, 'repo_url');
INSERT INTO AssignmentGroup VALUES (2004, 1001, 'repo_url');

INSERT INTO Membership VALUES ('s1', 2000);
INSERT INTO Membership VALUES ('s2', 2000);

INSERT INTO Membership VALUES ('s3', 2001);
INSERT INTO Membership VALUES ('s4', 2001);

INSERT INTO Membership VALUES ('s5', 2002);
INSERT INTO Membership VALUES ('s6', 2002);

INSERT INTO Membership VALUES ('s8', 2003);

INSERT INTO Membership VALUES ('s9', 2004);

INSERT INTO Submissions VALUES (3000, 'A1.pdf', 's1', 2000, '2017-02-04 19:59');
INSERT INTO Submissions VALUES (3001, 'A1.pdf', 's1', 2000, '2017-02-05 19:59');
INSERT INTO Submissions VALUES (3002, 'A11.pdf', 's2', 2000, '2017-02-08 19:59');

INSERT INTO Submissions VALUES (3003, 'A12.pdf', 's3', 2001, '2017-02-04 19:59');
INSERT INTO Submissions VALUES (3004, 'A12.pdf', 's4', 2001, '2017-02-08 19:59');

INSERT INTO Submissions VALUES (3005, 'A13.pdf', 's5', 2002, '2017-02-05 19:59');

INSERT INTO Submissions VALUES (3006, 'A14.pdf', 's9', 2004, '2017-02-07 19:59');
