SET search_path TO markus;

--Average Stuff
CREATE VIEW groupsForAssignment (
       SELECT assignment_id, group_id
       FROM Assignment NATURAL JOIN AssignmentGroup
       WHERE description = 'A1'
);

CREATE VIEW rubricItemsForAssignment (
       SELECT rubric_id, assignment_id,
              ( SELECT weight
                FROM rubricItem r1
                WHERE r1.rubric_id = rubric_id) *
              ( SELECT out_of
                FROM rubricItem r2
                WHERE r2.rubric_id = rubric_id) AS weightedOutOf
       FROM rubricItem NATURAL JOIN groupsForAssignment
);

CREATE VIEW totalWeightForAssignment (
       SELECT weightedOutOf
       FROM rubticItemsForAssignment
);

CREATE VIEW groupsWithPercent (
       SELECT group_id,
              ( SELECT mark
                FROM Result r
                WHERE r.group_id = group_id) /
              ( SELECT totalWeight
                FROM totalWeightForAssignment a
                WHERE a.assignment_id = assignment_id) * 100 AS totalMark
       FROM groupsForAssignment
);

CREATE VIEW AverageForA1 (
       SELECT AVG(totalMark)
       FROM groupsWithPercent
);

CREATE VIEW GroupsAboveAverage (
       SELECT group_id, totalMark AS mark, totalMark - (SELECT * FROM AverageForA1) AS compared_to_average, 'above' AS status
       FROM groupsWithPercent
       WHERE totalMark > (SELECT * FROM AverageForA1)
);

CREATE VIEW GroupsAtAverage (
       SELECT group_id, totalMark AS mark, totalMark - (SELECT * FROM AverageForA1) AS compared_to_average, 'at' AS status
       FROM groupsWithPercent
       WHERE totalMark = (SELECT * FROM AverageForA1)
);

CREATE VIEW GroupsBelowAverage (
       SELECT group_id, totalMark AS mark, totalMark - (SELECT * FROM AverageForA1) AS compared_to_average, 'below' AS status
       FROM groupsWithPercent
       WHERE totalMark < (SELECT * FROM AverageForA1)
);

CREATE VIEW Solution (
       (SELECT * FROM GroupsAboveAverage)
       UNION
       (SELECT * FROM GroupsAtAverage)
       UNION
       (SELECT * FROM GroupsBelowAverage)
);

INSERT INTO q10 ( SELECT * FROM Solution );
