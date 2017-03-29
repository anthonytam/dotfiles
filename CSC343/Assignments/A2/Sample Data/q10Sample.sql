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
INSERT INTO MarkusUser VALUES ('s4', 'sln1', 'sfn1', 'student');
INSERT INTO MarkusUser VALUES ('t1', 'tln1', 'tfn1', 'TA');

INSERT INTO Assignment VALUES (1000, 'A1', '2017-02-08 20:00', 1, 2);
INSERT INTO Assignment VALUES (1001, 'A2', '2017-02-18 20:00', 1, 2);

INSERT INTO AssignmentGroup VALUES (2000, 1000, 'repo_url');
INSERT INTO AssignmentGroup VALUES (2001, 1000, 'repo_url');
INSERT INTO AssignmentGroup VALUES (2002, 1000, 'repo_url');
INSERT INTO AssignmentGroup VALUES (2003, 1001, 'repo_url');
INSERT INTO AssignmentGroup VALUES (2004, 1000, 'repo_url');

INSERT INTO Membership VALUES ('s1', 2000);
INSERT INTO Membership VALUES ('s2', 2001);
INSERT INTO Membership VALUES ('s3', 2002);
INSERT INTO Membership VALUES ('s4', 2003);
INSERT INTO Membership VALUES ('s4', 2004);

INSERT INTO Grader VALUES (2000, 't1');
INSERT INTO Grader VALUES (2001, 't1');
INSERT INTO Grader VALUES (2002, 't1');
INSERT INTO Grader VALUES (2003, 't1');
INSERT INTO Grader VALUES (2004, 't1');

INSERT INTO RubricItem VALUES (4000, 1000, 'style', 4, 0.25);
INSERT INTO RubricItem VALUES (4001, 1000, 'tester', 12, 0.75);
INSERT INTO RubricItem VALUES (4002, 1001, 'style', 4, 0.25);
INSERT INTO RubricItem VALUES (4003, 1001, 'tester', 12, 0.75);

INSERT INTO Grade VALUES (2000, 4000, 0);
INSERT INTO Grade VALUES (2000, 4001, 0);
INSERT INTO Grade VALUES (2001, 4000, 2);
INSERT INTO Grade VALUES (2001, 4001, 6);
INSERT INTO Grade VALUES (2002, 4000, 4);
INSERT INTO Grade VALUES (2002, 4001, 12);
INSERT INTO Grade VALUES (2003, 4002, 2);
INSERT INTO Grade VALUES (2003, 4003, 6);

INSERT INTO Result VALUES (2000, 0, true);
INSERT INTO Result VALUES (2001, 5, true);
INSERT INTO Result VALUES (2002, 10, true);
INSERT INTO Result VALUES (2003, 5, true);
