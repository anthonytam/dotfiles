
SET search_path TO markus;

CREATE VIEW rubricItemsForAssignment (
       SELECT rubric_id, assignment_id,
              ( SELECT weight
                FROM rubricItem r1
                WHERE r1.rubric_id = rubric_id) *
              ( SELECT out_of
                FROM rubricItem r2
                WHERE r2.rubric_id = rubric_id) AS weightedOutOf
       FROM rubricItem
);

CREATE VIEW totalWeightForAssignment (
       SELECT assignment_id, SUM(weightedOutOf) AS totalWeight
       FROM rubticItemsForAssignment
       GROUP BY assignment_id
);

CREATE VIEW groupsForAssignment (
       SELECT assignment_id, group_id
       FROM Assignment NATURAL JOIN AssignmentGroup
);

CREATE VIEW groupsWithPercent (
       SELECT assignment_id, group_id
              ( SELECT mark
                FROM Result r
                WHERE r.group_id = group_id) /
              ( SELECT totalWeight
                FROM totalWeightForAssignment a
                WHERE a.assignment_id = assignment_id) * 100 AS totalMark
       FROM groupsForAssignment
);

CREATE VIEW multiGroups (
       SELECT assignment_id, group_id, COUNT(username) as numMembers, totalMark
       FROM AssignmentGroup NATURAL JOIN Membership RIGHT OUTER JOIN groupsWithPercent
       GROUP BY assignment_id, group_id
       HAVING COUNT(username) > 1
);

CREATE VIEW soloGroups (
       SELECT assignment_id, group_id, totalMark
       FROM AssignmentGroup NATURAL JOIN Membership RIGHT OUTER JOIN groupsWithPercent
       GROUP BY assignment_id, group_id
       HAVING COUNT(username) = 1
);

CREATE VIEW soloBetterThenMulti (
       SELECT assignment_id, description
              ( SELECT COUNT(DISTINCT group_id)
                FROM soloGroups sg
                GROUP BY sg.assignment_id
                HAVING sg.assignment_id = assignment_id ) AS num_solo,
              ( SELECT AVG(totalMark)
                FROM soloGroups sg
                GROUP BY sg.assignment_id
                HAVING sg.assignment_id = assignment_id ) AS average_solo,
              ( SELECT SUM(numMembers)
                FROM multiGroups mg
                GROUP BY mg.assignment_id
                HAVING mg.assignment_id = assignment_id ) AS num_collaborators,
              ( SELECT AVG(totalMark)
                FROM multiGroups mg
                GROUP BY mg.assignment_id
                HAVING mg.assignment_id = assignment_id ) AS average_collaborators,
              ( SELECT (COUNT(DISTINCT sg.group_id) + num_collaborators)/(COUNT(DISTINCT sg.group_id) + COUNT(DISTINCT mg.group_id))
                FROM soloGroups, multiGroups smg
                GROUP BY smg.assignment_id
                HAVING smg.assignment_id = assignment_id) AS average_students_per_group
       FROM Assignment
       WHERE average_solo > average_collaborators
);

INSERT INTO q3 ( SELECT * FROM soloBetterThenMulti );
