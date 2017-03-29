SET search_path TO markus;

DROP TABLE IF EXISTS q5;
CREATE TABLE q5 (
	assignment_id integer,
	username varchar(25), 
	num_assigned integer
);

DROP VIEW IF EXISTS numAssignedGroups CASCADE;
CREATE VIEW numAssignedGroups AS
       SELECT assignment_id, username, COUNT(DISTINCT group_id) AS num_assigned
       FROM AssignmentGroup NATURAL JOIN Grader
       GROUP BY assignment_id, username;

DROP VIEW IF EXISTS assignmentIDsWithRangesOverTen CASCADE;
CREATE VIEW assignmentIDsWithRangesOverTen AS
       SELECT assignment_id
       FROM numAssignedGroups
       GROUP BY assignment_id
       HAVING MAX(num_assigned) - MIN(num_assigned) > 10;

DROP VIEW IF EXISTS Solution CASCADE;
CREATE VIEW Solution AS
       SELECT assignment_id, username, num_assigned
       FROM numAssignedGroups NATURAL JOIN assignmentIDsWithRangesOverTen;

INSERT INTO q5 (SELECT * FROM Solution);
