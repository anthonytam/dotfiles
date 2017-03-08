SET search_path TO markus;

CREATE VIEW TAs (
       SELECT username
       FROM MarkusUser
       WHERE usertype='TA'
);

-- Condition One
CREATE VIEW TAWhoMarkedEveryAssignment (
       SELECT username
       FROM Grader NATURAL JOIN AssignmentGroup
       GROUP BY username
       HAVING COUNT(DISTINCT assignment_id) =
              (SELECT COUNT(DISTINCT assignment_id)
               FROM Assignment)
);

CREATE VIEW TAWhoMarkedTenGroups (
        SELECT assignment_id, username
        FROM Result NATURAL JOIN Grader NATURAL JOIN AssignmentGroup
        GROUP BY assignment_id, username
        HAVING COUNT(group_id) >= 10
);

-- Condition Two
CREATE VIEW TAWhoMarkedTenGroupsForEveryAssignment (
       SELECT username
       FROM TAWhoMarkedTenGroups
       GROUP BY assignment_id
       HAVING COUNT(DISTINCT assignment_id) =
              (SELECT COUNT(DISTINCT assignment_id)
               FROM Assignment)
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

CREATE VIEW groupsForAssignment (
       SELECT assignment_id, group_id, username AS student
       FROM Assignment NATURAL JOIN AssignmentGroup NATURAL JOIN Membership
);

CREATE VIEW groupsWithPercent (
       SELECT assignment_id, group_id, student, username, due_date
              ( SELECT mark
                FROM Result r
                WHERE r.group_id = group_id) /
              ( SELECT totalWeight
                FROM totalWeightForAssignment a
                WHERE a.assignment_id = assignment_id) * 100 AS totalMark
       FROM groupsForAssignment NATURAL JOIN Grader NATURAL JOIN Assignment
);

CREATE VIEW averagePercentagePerAssignment (
       SELECT assignment_id, due_date, username, AVG(totalMark) as averageMark
       FROM groupsWithPercent
       GROUP BY assignment_id, due_date, username
       ORDER BY username, due_date
);

CREATE VIEW increasingAverages (
       SELECT assignment_id, due_date, username, averageMark
       FROM averagePercentagePerAssignment
       WHERE averageMark > EVERY (SELECT averageMark
                                  FROM averagePercentagePerAssignment AS a
                                  WHERE a.username = username AND
                                        a.due_date < due_date)
);

-- Condition Three
CREATE VIEW TAWithIncreasingAverages (
       SELECT username
       FROM increasingAverages
       WHERE ( SELECT COUNT(assignment_id)
               FROM increasingAverages AS i
               GROUP BY i.username
               HAVING i.username = username ) =
               ( SELECT COUNT(assignment_id)
               FROM averagePercentagePerAssignment AS ap
               GROUP BY ap.username
               HAVING ap.username = username )
);

CREATE VIEW AverageAllAssignments (
       SELECT username, AVG(averageMark) AS average_mark_all_assignments
       FROM averagePercetagePerAssignment NATURAL JOIN TAWithIncreasingAverages
       GROUP BY username
);

CREATE VIEW markChangeBetweenAssignments (
       SELECT username, MAX(averageMark) - MIN(averageMark) as mark_change_first_last
       FROM averagePercentagePerAssignment NATURAL JOIN TAWithIncreasingAverages
       GROUP BY username
);

CREATE VIEW TAWhoMeetsAllConditions (
       SELECT username, first_name || ' ' || surname AS ta_name
       FROM TAWithIncreasingAvergaes NATURAL JOIN
            TAWhoMarkedEveryGroup NATURAL JOIN
            TAWhoMarkedEveryAssignment NATURAL JOIN
            MarkusUser
);

CREATE VIEW Solution (
       SELECT ta_name, average_mark_all_assignments, mark_change_first_last
       FROM TAWhoMeetsAllConditions NATURAL JOIN
            markChangeBetweenAssignments NATURAL JOIN
            AverageAllAssignments
);

INSERT INTO q2 (SELECT * FROM Solution);
