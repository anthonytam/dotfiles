SET search_path TO markus;

DROP TABLE IF EXISTS q8;
CREATE TABLE q8 (
	username varchar(25),
	group_average real,
	solo_average real
);

DROP VIEW IF EXISTS AssignmentsWhichAllowGroups CASCADE;
CREATE VIEW AssignmentsWhichAllowGroups AS
       SELECT assignment_id
       FROM Assignment
       WHERE group_max > 1;

DROP VIEW IF EXISTS StudentsWhoAlwaysContributedToAGroup CASCADE;
CREATE VIEW StudentsWhoAlwaysContributedToAGroup AS
       SELECT a.assignment_id, a.username, a.group_id
       FROM (AssignmentGroup NATURAL JOIN Membership NATURAL JOIN Submissions) a
       GROUP BY a.assignment_id, a.username, a.group_id
       HAVING (SELECT COUNT(b.assignment_id) FROM (AssignmentGroup NATURAL JOIN Membership NATURAL JOIN Submissions) b WHERE b.username = a.username) = (SELECT COUNT(b.assignment_id) FROM (AssignmentGroup NATURAL JOIN Membership) b WHERE b.username = a.username);

DROP VIEW IF EXISTS GroupsWithMultipleMembersAndContributed CASCADE;
CREATE VIEW GroupsWithMultipleMembersAndContributed AS
       SELECT assignment_id, group_id, username
       FROM AssignmentGroup NATURAL JOIN Membership NATURAL JOIN Submissions
       WHERE group_id IN ( SELECT group_id
                           FROM Membership
                           GROUP BY group_id
                           HAVING COUNT(username) > 1 );

DROP VIEW IF EXISTS StudentsWhoAlwaysWorkedInAGroup CASCADE;
CREATE VIEW StudentsWhoAlwaysWorkedInAGroup AS
       SELECT g.username
       FROM GroupsWithMultipleMembersAndContributed g
       GROUP BY g.username
       HAVING COUNT(DISTINCT assignment_id) = ( SELECT COUNT(DISTINCT assignment_id)
                                       FROM AssignmentsWhichAllowGroups NATURAL JOIN AssignmentGroup NATURAL JOIN Membership mem
                                       WHERE mem.username = g.username );

--Average Stuff
DROP VIEW IF EXISTS groupsForAssignment CASCADE;
CREATE VIEW groupsForAssignment AS
       SELECT assignment_id, group_id
       FROM Assignment NATURAL JOIN AssignmentGroup;

DROP VIEW IF EXISTS rubricItemsForAssignment CASCADE;
CREATE VIEW rubricItemsForAssignment AS
       SELECT rubric_id, assignment_id, weight*out_of AS weightedOutOf
       FROM rubricItem;

DROP VIEW IF EXISTS totalWeightForAssignment CASCADE;
CREATE VIEW totalWeightForAssignment AS
       SELECT assignment_id, SUM(weightedOutOf) as totalWeight
       FROM rubricItemsForAssignment
       GROUP BY assignment_id;

DROP VIEW IF EXISTS groupsWithPercent CASCADE;
CREATE VIEW groupsWithPercent AS
       SELECT assignment_id, group_id, mark/totalWeight * 100 AS totalMark
       FROM groupsForAssignment NATURAL JOIN Result NATURAL JOIN totalWeightForAssignment;

DROP VIEW IF EXISTS GroupWorkersMarks CASCADE;
CREATE VIEW GroupWorkersMarks AS
       SELECT assignment_id, group_id, username, totalMark
       FROM groupsWithPercent NATURAL JOIN Membership NATURAL JOIN StudentsWhoAlwaysContributedToAGroup;

DROP VIEW IF EXISTS AverageGroupPercentage CASCADE;
CREATE VIEW AverageGroupPercentage AS
       SELECT username, AVG(totalMark) AS group_average
       FROM GroupWorkersMarks
       WHERE assignment_id IN ( SELECT assignment_id
                                FROM Assignment
                                WHERE group_max > 1 )
       GROUP BY username;

DROP VIEW IF EXISTS AverageSoloPercentage CASCADE;
CREATE VIEW AverageSoloPercentage AS
       SELECT username, AVG(totalMark) AS solo_average
       FROM GroupWorkersMarks
       WHERE assignment_id IN ( SELECT assignment_id
                                FROM Assignment
                                WHERE group_max = 1 )
       GROUP BY username;

DROP VIEW IF EXISTS Solution CASCADE;
CREATE VIEW Solution AS
       SELECT username, group_average, solo_average
       FROM AverageGroupPercentage NATURAL JOIN (AverageSoloPercentage NATURAL RIGHT JOIN StudentsWhoAlwaysWorkedInAGroup);

INSERT INTO q8 ( SELECT * FROM Solution );
