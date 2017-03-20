SET search_path TO markus;

--Create the solutions table
DROP TABLE IF EXISTS q4;
CREATE TABLE q4 (
	assignment_id integer,
	username varchar(25), 
	num_marked integer, 
	num_not_marked integer,
	min_mark real,
	max_mark real
);

--Calculates the weighted vale of every rubric item
--for every assignment
DROP VIEW IF EXISTS rubricItemsForAssignment CASCADE;
CREATE VIEW rubricItemsForAssignment AS
       SELECT rubric_id, assignment_id, weight*out_of AS weightedOutOf
       FROM rubricItem;

--Calculates the percentage mark of every group that
--has been graded
DROP VIEW IF EXISTS totalWeightForAssignment CASCADE;
CREATE VIEW totalWeightForAssignment AS
       SELECT assignment_id, SUM(weightedOutOf) as totalWeight
       FROM rubricItemsForAssignment
       GROUP BY assignment_id;

--Creates tuples of all group ID's for any assignment
DROP VIEW IF EXISTS groupsForAssignment CASCADE;
CREATE VIEW groupsForAssignment AS
       SELECT assignment_id, group_id
       FROM Assignment NATURAL JOIN AssignmentGroup;

--Calculates the percentage mark of every group
--that has been graded
DROP VIEW IF EXISTS groupsWithPercent CASCADE; 
CREATE VIEW groupsWithPercent AS
       SELECT assignment_id, group_id, username, mark/totalWeight * 100 AS totalMark
       FROM groupsForAssignment NATURAL JOIN Result NATURAL JOIN totalWeightForAssignment NATURAL JOIN Grader;

--Find the min and max mark each TA gave per assignment
DROP VIEW IF EXISTS minMaxMarkPerAssignmentPerGrader CASCADE;
CREATE VIEW minMaxMarkPerAssignmentPerGrader AS
       SELECT assignment_id, username, COUNT(DISTINCT group_id) AS num_marked, MIN(totalMark) AS min_mark, MAX(totalMark) AS max_mark
       FROM groupsWithPercent
       GROUP BY assignment_id, username;

--Find the number of assignments each grader was required to mark
DROP VIEW IF EXISTS totalGroupsAssignedToGraderPerAssignment CASCADE;
CREATE VIEW totalGroupsAssignedToGraderPerAssignment AS
       SELECT assignment_id, username, COUNT(DISTINCT group_id) AS totalGroups
       FROM Grader NATURAL JOIN AssignmentGroup
       GROUP BY assignment_id, username;

--Find the number of unmarked the assignments and join to results of the other
--views to formulate a solution
DROP VIEW IF EXISTS Solution CASCADE;
CREATE VIEW Solution AS
       SELECT assignment_id, username, num_marked, totalGroups - num_marked AS num_not_marked, min_mark, max_mark
       FROM minMaxMarkPerAssignmentPerGrader NATURAL JOIN totalGroupsAssignedToGraderPerAssignment;

--Inset the result into the solutions table
INSERT INTO q4 ( SELECT * FROM Solution );
