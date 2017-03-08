SET search_path TO markus;

CREATE VIEW AssignmentsWhichAllowGroups (
       SELECT COUNT(assignment_id)
       FROM Assignment
       WHERE group_max > 1
);

CREATE VIEW GroupsWithMultipleMembersAndContributed (
       SELECT assignment_id, group_id, username
       FROM AssignmentGroup NATURAL JOIN Membership NATURAL JOIN Submissions
       WHERE group_id IN ( SELECT group_id
                           FROM Membership
                           GROUP BY group_id
                           HAVING COUNT(username) > 1 )
);

CREATE VIEW StudentsWhoAlwaysWorkedInAGroup (
       SELECT username
       FROM GroupsWithMultipleMembersAndContributed
       GROUP BY username
       HAVING COUNT(assignment_id) = ( SELECT *
                                       FROM AssignmentsWhichAllowGroups)
);

--Average Stuff
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

CREATE VIEW GroupWorkersMarks (
       SELECT assignment_id, group_id, username, totalMark
       FROM groupsWithPercent NATURAL JOIN Membership NATURAL JOIN StudentsWhoAlwaysWorkedInAGroup
);

CREATE VIEW AverageGroupPercentage
       SELECT username, AVG(totalMark) AS group_average
       FROM GroupWorkersMarks
       WHERE assignment_id IN ( SELECT assignment_id
                                FROM Assignment
                                WHERE group_max > 1 )
       GROUP BY username
);

CREATE VIEW AverageSoloPercentage
       SELECT username, AVG(totalMark) AS solo_average
       FROM GroupWorkersMarks
       WHERE assignment_id IN ( SELECT assignment_id
                                FROM Assignment
                                WHERE group_max = 1 )
       GROUP BY username
);

CREATE VIEW Solution (
       SELECT username, group_average, solo_average
       FROM AverageGroupPercentage NATURAL JOIN AverageSoloPercentage
);

INSERT INTO q8 ( SELECT * FROM Solution );
