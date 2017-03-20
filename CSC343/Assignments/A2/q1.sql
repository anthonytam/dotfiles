SET search_path TO markus;

--Create the solutions table
DROP TABLE IF EXISTS q1;
CREATE TABLE q1 (
	assignment_id integer,
	average_mark_percent real, 
	num_80_100 integer, 
	num_60_79 integer, 
	num_50_59 integer, 
	num_0_49 integer
);

--Finds every group_id for every assignment
--and the assignment that thet group was created for
DROP VIEW IF EXISTS groupsForAssignment CASCADE;
CREATE VIEW groupsForAssignment AS
       SELECT assignment_id, group_id
       FROM Assignment NATURAL LEFT JOIN AssignmentGroup;

--Calculated the weighted value of every rubric item
--for every assignment
DROP VIEW IF EXISTS rubricItemsForAssignment CASCADE;
CREATE VIEW rubricItemsForAssignment AS
       SELECT rubric_id, assignment_id, weight*out_of AS weightedOutOf
       FROM rubricItem;

--Calculates the total weight of every assignment by
--summing the value of the rubric items
DROP VIEW IF EXISTS totalWeightForAssignment CASCADE;
CREATE VIEW totalWeightForAssignment AS
       SELECT assignment_id, SUM(weightedOutOf) AS totalWeight
       FROM rubricItemsForAssignment
       GROUP BY assignment_id;

--Calculatees the percentage mark of every group
--that has been graded
DROP VIEW IF EXISTS groupsWithPercent CASCADE;
CREATE VIEW groupsWithPercent AS
       SELECT assignment_id, group_id, mark/totalWeight*100 AS totalMark
       FROM groupsForAssignment NATURAL JOIN totalWeightForAssignment NATURAL LEFT JOIN Result;

--Calculate the students who are in the grade range of
--over 80 percent
DROP VIEW IF EXISTS gradeRanges80 CASCADE;
CREATE VIEW gradeRanges80 AS
              SELECT p.assignment_id, AVG(p.totalMark) AS average_mark_percent,
                     COALESCE ((SELECT COUNT(*)
                                FROM groupsWithPercent g
                                WHERE g.totalMark > 80
                                GROUP BY g.assignment_id
                                HAVING g.assignment_id = p.assignment_id), 0) AS num_80_100
              FROM groupsWithPercent p
              GROUP BY p.assignment_id;

--Calculate the students in the 60 to 79 grade range
--append the previous results as well
DROP VIEW IF EXISTS gradeRanges60 CASCADE;
CREATE VIEW gradeRanges60 AS
        SELECT p.assignment_id, p.average_mark_percent, p.num_80_100, COALESCE ((SELECT COUNT(*)
                                                                           FROM groupsWithPercent g
                                                                           WHERE g.totalMark >= 60 AND g.totalMark < 80
                                                                           GROUP BY g.assignment_id
                                                                           HAVING g.assignment_id = p.assignment_id), 0) AS num_60_79
        FROM gradeRanges80 p NATURAL JOIN groupsWithPercent
        GROUP BY p.assignment_id, p.average_mark_percent, p.num_80_100;

--Calculate the students in the 50 to 59 grade range
--append the previous results as well
DROP VIEW IF EXISTS gradeRanges50 CASCADE;
CREATE VIEW gradeRanges50 AS
        SELECT p.assignment_id, p.average_mark_percent, p.num_80_100, p.num_60_79, COALESCE ((SELECT COUNT(*)
                                                                           FROM groupsWithPercent g
                                                                           WHERE g.totalMark >= 50 AND g.totalMark < 60
                                                                           GROUP BY g.assignment_id
                                                                           HAVING g.assignment_id = p.assignment_id), 0) AS num_50_59
        FROM gradeRanges60 p NATURAL JOIN groupsWithPercent
        GROUP BY p.assignment_id, p.average_mark_percent, p.num_80_100, p.num_60_79;

--Calculate the students in the 0 to 49 grade range
--append the previous results as well
DROP VIEW IF EXISTS gradeRanges CASCADE;
CREATE VIEW gradeRanges AS
        SELECT p.assignment_id, p.average_mark_percent, p.num_80_100, p.num_60_79, p.num_50_59, COALESCE ((SELECT COUNT(*)
                                                                                                 FROM groupsWithPercent g
                                                                                                 WHERE g.totalMark < 50
                                                                                                 GROUP BY g.assignment_id
                                                                                                 HAVING g.assignment_id = p.assignment_id), 0) AS num_0_49
        FROM gradeRanges50 p NATURAL JOIN groupsWithPercent
        GROUP BY p.assignment_id, p.average_mark_percent, p.num_80_100, p.num_60_79, p.num_50_59;

--Insert the result into the answers table
INSERT INTO q1 ( SELECT * FROM gradeRanges );
