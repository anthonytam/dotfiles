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
       SELECT assignment_id, SUM(weightedOutOf) as totalWeight
       FROM rubticItemsForAssignment
       GROUP BY assignment_id
);

CREATE VIEW groupsForAssignment (
       SELECT assignment_id, group_id
       FROM Assignment NATURAL JOIN AssignmentGroup
);

CREATE VIEW groupsWithPercent (
       SELECT assignment_id, group_id, username
              ( SELECT mark
                FROM Result r
                WHERE r.group_id = group_id) /
              ( SELECT totalWeight
                FROM totalWeightForAssignment a
                WHERE a.assignment_id = assignment_id) * 100 AS totalMark
       FROM groupsForAssignment NATURAL JOIN Grader
);

CREATE VIEW minMaxMarkPerAssignmentPerGrader (
       SELECT assignment_id, username, COUNT(DISTINCT group_id) AS num_marked, MIN(totalMark) AS min_mark, MAX(totalMark) AS max_mark
       FROM groupsWithPercent
       GROUP BY assignment_id, username
);

CREATE VIEW totalGroupsAssignedToGraderPerAssignment (
       SELECT assignment_id, username, COUNT(DISTINCT group_id) AS totalGroups
       FROM Grader NATURAL JOIN AssignmentGroup
       GROUP BY assignment_id, username
);

CREATE VIEW Solution (
       SELECT assignment_id, username, num_marked, totalGroups - num_marked AS num_not_marked, min_mark, max_mark
       FROM minMaxMarkPerAssignmentPerGrader NATURAL JOIN totalGroupsAssignedToGraderPerAssignment
);

INSERT INTO q4 ( SELECT * FROM Solution );
