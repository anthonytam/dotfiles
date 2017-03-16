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
INSERT INTO MarkusUser VALUES ('t1', 'tln1', 'tfn1', 'TA');

INSERT INTO Assignment VALUES (1000, 'a1', '2017-02-08 20:00', 1, 2);

INSERT INTO Required VALUES (1000, 'A1.pdf');

INSERT INTO AssignmentGroup VALUES (2000, 1000, 'repo_url');

INSERT INTO Membership VALUES ('s1', 2000);
INSERT INTO Membership VALUES ('s2', 2000);

INSERT INTO Submissions VALUES (3000, 'A1.pdf', 's1', 2000, '2017-02-08 19:59');

INSERT INTO Grader VALUES (2000, 't1');

INSERT INTO RubricItem VALUES (4000, 1000, 'style', 4, 0.25);
INSERT INTO RubricItem VALUES (4001, 1000, 'tester', 12, 0.75);

INSERT INTO Grade VALUES (2000, 4000, 3);
INSERT INTO Grade VALUES (2000, 4001, 9);

INSERT INTO Result VALUES (2000, 7.5, true);

-- Import 2
INSERT INTO Assignment VALUES (1001, 'a2', '2017-02-28 20:00', 1, 2);

INSERT INTO Required VALUES (1001, 'A2.pdf');

INSERT INTO AssignmentGroup VALUES (2001, 1001, 'another_repo_url');

INSERT INTO Membership VALUES ('s1', 2001);
INSERT INTO Membership VALUES ('s2', 2001);

INSERT INTO Submissions VALUES (3001, 'A2.pdf', 's2', 2001, '2017-02-18 19:59');

INSERT INTO Grader VALUES (2001, 't1');

INSERT INTO RubricItem VALUES (4010, 1001, 'style', 4, 0.25);
INSERT INTO RubricItem VALUES (4011, 1001, 'tester', 12, 0.75);

-- Import 3
INSERT INTO MarkusUser VALUES ('s3', 'sln1', 'sfn1', 'student');
INSERT INTO MarkusUser VALUES ('s4', 'sln2', 'sfn2', 'student');
INSERT INTO MarkusUser VALUES ('s5', 'sln1', 'sfn1', 'student');
INSERT INTO MarkusUser VALUES ('s6', 'sln2', 'sfn2', 'student');

--New Assignment
INSERT INTO Assignment VALUES (1002, 'a3', '2017-02-28 20:00', 1, 2);

INSERT INTO Required VALUES (1002, 'A3.pdf');

--G2
INSERT INTO AssignmentGroup VALUES (2010, 1002, 'g1');

INSERT INTO Membership VALUES ('s1', 2010);
INSERT INTO Membership VALUES ('s2', 2010);

INSERT INTO Submissions VALUES (3002, 'A3.pdf', 's2', 2010, '2017-02-015 19:59');

INSERT INTO Grader VALUES (2010, 't1');

INSERT INTO RubricItem VALUES (4100, 1002, 'style', 10, 0.25);
INSERT INTO RubricItem VALUES (4101, 1002, 'tester', 10, 0.75);

INSERT INTO Grade VALUES (2010, 4100, 2);
INSERT INTO Grade VALUES (2010, 4101, 2);

INSERT INTO Result VALUES (2010, 2, true);

--G3
INSERT INTO AssignmentGroup VALUES (2011, 1002, 'g2');

INSERT INTO Membership VALUES ('s3', 2011);
INSERT INTO Membership VALUES ('s4', 2011);

INSERT INTO Submissions VALUES (3003, 'A3.pdf', 's3', 2011, '2017-02-014 19:59');

INSERT INTO Grader VALUES (2011, 't1');

INSERT INTO Grade VALUES (2011, 4100, 5);
INSERT INTO Grade VALUES (2011, 4101, 5);

INSERT INTO Result VALUES (2011, 5, true);

--G4
INSERT INTO AssignmentGroup VALUES (2012, 1002, 'g3');

INSERT INTO Membership VALUES ('s5', 2012);
INSERT INTO Membership VALUES ('s6', 2012);

INSERT INTO Submissions VALUES (3004, 'A3.pdf', 's6', 2011, '2017-02-014 19:59');

INSERT INTO Grader VALUES (2012, 't1');

INSERT INTO Grade VALUES (2012, 4100, 9);
INSERT INTO Grade VALUES (2012, 4101, 9);

INSERT INTO Result VALUES (2012, 9, true);

--G5
INSERT INTO AssignmentGroup VALUES (2002, 1000, 'g5');

INSERT INTO Membership VALUES ('s5', 2002);
INSERT INTO Membership VALUES ('s6', 2002);

INSERT INTO Submissions VALUES (3005, 'A1.pdf', 's6', 2002, '2017-02-014 19:59');

INSERT INTO Grader VALUES (2002, 't1');

INSERT INTO Grade VALUES (2002, 4000, 3);
INSERT INTO Grade VALUES (2002, 4001, 9);

INSERT INTO Result VALUES (2002, 7, true);

--Import 4
INSERT INTO Assignment VALUES (1004, 'a4', '2017-02-28 20:00', 1, 2);

INSERT INTO Required VALUES (1004, 'A4.pdf');

INSERT INTO RubricItem VALUES (4006, 1004, 'style', 8, 0.25);
INSERT INTO RubricItem VALUES (4007, 1004, 'tester', 12, 0.75);
