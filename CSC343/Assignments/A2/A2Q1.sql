SET search_path TO markus;

CREATE VIEW groupsForAssignment (
       SELECT assignment_id, group_id
       FROM Assignment NATURAL JOIN AssignmentGroup
);

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

CREATE VIEW groupsWithPercent (
       SELECT assignment_id, group_id,
              ( SELECT mark
                FROM Result r
                WHERE r.group_id = group_id) /
              ( SELECT totalWeight
                FROM totalWeightForAssignment a
                WHERE a.assignment_id = assignment_id) * 100 AS totalMark
       FROM groupsForAssignment
);

CREATE VIEW gradeRanges (
       ( SELECT assignment_id, AVG(totalMark) AS average_mark_percent
         FROM groupsWithPercent
         GROUP BY assignment_id )
        UNION
        ( SELECT assignment_id, COUNT(group_id) AS num_80_100
          FROM groupsWithPercent
          WHERE totalMark >= 80
          GROUP BY assignment_id )
        UNION
        ( SELECT assignment_id, COUNT(group_id) AS num_60_79
          FROM groupsWithPercent
          WHERE totalMark >= 60 AND totalMark <= 79
          GROUP BY assignment_id )
        UNION
        ( SELECT assignment_id, COUNT(group_id) AS num_50_59
          FROM groupsWithPercent
          WHERE totalMark >= 50 AND totalMark <= 59
          GROUP BY assignment_id )
        UNION
        ( SELECT assignment_id, COUNT(group_id) AS num_0_49
          FROM groupsWithPercent
          WHERE totalMark < 50
          GROUP BY assignment_id )
);

INSERT INTO q1 ( SELECT * FROM gradeRanges );
