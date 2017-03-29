SET search_path TO markus;

--Create a table for the solutions
DROP TABLE IF EXISTS q3;
CREATE TABLE q3 (
	assignment_id integer,
	description varchar(100), 
	num_solo integer, 
	average_solo real,
	num_collaborators integer, 
	average_collaborators real, 
	average_students_per_submission real
);

--Calculates the wieghted vale of every rubric item for
--every assignment
DROP VIEW IF EXISTS rubricItemsForAssignment CASCADE;
CREATE VIEW rubricItemsForAssignment AS
       SELECT rubric_id, assignment_id, weight*out_of AS weightedOutOf
       FROM rubricItem;

--Calculates the total eright of every assignment by
-- summing the value of the rubric item
DROP VIEW IF EXISTS totalWeightForAssignment CASCADE;
CREATE VIEW totalWeightForAssignment AS
       SELECT assignment_id, SUM(weightedOutOf) AS totalWeight
       FROM rubricItemsForAssignment
       GROUP BY assignment_id;

--Finds a list of group ID's for each assignment
DROP VIEW IF EXISTS groupsForAssignment CASCADE;
CREATE VIEW groupsForAssignment AS
       SELECT assignment_id, group_id
       FROM Assignment NATURAL JOIN AssignmentGroup;

--Calculates the percentage mark of every group that has
--been graded
DROP VIEW IF EXISTS groupsWithPercent CASCADE;
CREATE VIEW groupsWithPercent AS
       SELECT assignment_id, group_id, mark/totalWeight * 100 AS totalMark
       FROM groupsForAssignment NATURAL JOIN Result NATURAL JOIN totalWeightForAssignment;

--Find a list of group ID's which have multiple members
DROP VIEW IF EXISTS multiGroups CASCADE;
CREATE VIEW multiGroups AS
       SELECT assignment_id, group_id, COUNT(username) as numMembers, totalMark
       FROM AssignmentGroup NATURAL RIGHT JOIN groupsWithPercent NATURAL JOIN Membership 
       GROUP BY assignment_id, group_id, totalMark
       HAVING COUNT(username) > 1;

--Find a list of group ID's which only have one member
DROP VIEW IF EXISTS soloGroups CASCADE;
CREATE VIEW soloGroups AS
       SELECT assignment_id, group_id, totalMark
       FROM AssignmentGroup NATURAL RIGHT JOIN groupsWithPercent NATURAL JOIN Membership 
       GROUP BY assignment_id, group_id, totalMark
       HAVING COUNT(username) = 1;

--Find a list of assignments which have no formed groups
DROP VIEW IF EXISTS assignmentWithNoGroups CASCADE;
CREATE VIEW assignmentWithNoGroups AS
       SELECT assignment_id, description, 0 AS num_solo, NULL AS average_solo, 0 AS num_collaborators, NULL AS averave_collaborators, 0 AS average_students_per_submission
       FROM Assignment
       WHERE assignment_id NOT IN (SELECT DISTINCT assignment_id FROM AssignmentGroup);

--Create a large table storing all the data for solo in multi groups per assignment in
-- a single location. This data will not include assignments with no groups
DROP VIEW IF EXISTS soloAndMulti CASCADE;
CREATE VIEW soloAndMulti AS
       SELECT a.assignment_id, a.description,
              ( SELECT COALESCE ((SELECT COUNT(DISTINCT sg.group_id)
                FROM soloGroups sg
                GROUP BY sg.assignment_id
                HAVING sg.assignment_id = a.assignment_id ), 0)) AS num_solo,
              ( SELECT AVG(sg2.totalMark)
                FROM soloGroups sg2
                GROUP BY sg2.assignment_id
                HAVING sg2.assignment_id = a.assignment_id ) AS average_solo,
              ( SELECT COALESCE ((SELECT SUM(mg.numMembers)
                FROM multiGroups mg
                GROUP BY mg.assignment_id
                HAVING mg.assignment_id = a.assignment_id ), 0)) AS num_collaborators,
              ( SELECT AVG(mg2.totalMark)
                FROM multiGroups mg2
                GROUP BY mg2.assignment_id
                HAVING mg2.assignment_id = a.assignment_id ) AS average_collaborators,
              ( SELECT  (COALESCE (COUNT(DISTINCT sg3.group_id), 0) +
                         COALESCE ((SELECT SUM(nummembers) FROM multiGroups WHERE assignment_id = a.assignment_id), 0)) /
                        (COALESCE (COUNT(DISTINCT sg3.group_id), 0) +
                         COALESCE ((SELECT COUNT(DISTINCT group_id) FROM multiGroups WHERE assignment_id = a.assignment_id), 0))
                FROM soloGroups sg3
                GROUP BY sg3.assignment_id
                HAVING sg3.assignment_id = a.assignment_id) AS average_students_per_submission
       FROM Assignment a;

--Join together the large information view with the assignments with no groups to create a table
--of all required tuples
DROP VIEW IF EXISTS Solution CASCADE;
CREATE VIEW Solution AS
       (SELECT * FROM soloAndMulti WHERE average_students_per_submission IS NOT NULL)
       UNION
       (SELECT * FROM assignmentWithNoGroups);

--Insert the result into the solutions table
INSERT INTO q3 ( SELECT * FROM Solution );
