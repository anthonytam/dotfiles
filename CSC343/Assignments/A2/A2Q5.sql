SET search_path TO markus;

CREATE VIEW numAssignedGroups (
       SELECT assignment_id, username, COUNT(DISTINCT group_id) AS num_assigned
       FROM AssignmentGroup NATURAL JOIN Grader
       GROUP BY assignment_id, username
);

CREATE VIEW assignmentIDsWithRangesOverTen (
       SELECT assignment_id
       FROM numAssignedGroups
       GROUP BY assignment_id
       HAVING MAX(num_assigned) - MIN(num_assigned) > 10
);

CREATE VIEW Solution (
       SELECT assignment_id, username, num_assigned
       FROM numAssignedGroups NATURAL JOIN assignmentIDsWithRangesOverTen
);

INSERT INTO q5 (SELECT * FROM Solution);
