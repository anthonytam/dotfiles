SET search_path TO markus;

DROP TABLE IF EXISTS q9;
CREATE TABLE q9 (
	student1 varchar(25),
	student2 varchar(25)
);

DROP VIEW IF EXISTS AssignmentsWhichAllowGroups CASCADE;
CREATE VIEW AssignmentsWhichAllowGroups AS
       SELECT COUNT(assignment_id)
       FROM Assignment
       WHERE group_max > 1;

DROP VIEW IF EXISTS GroupsWithMultipleMembers CASCADE;
CREATE VIEW GroupsWithMultipleMembers AS
       SELECT assignment_id, group_id, username
       FROM AssignmentGroup NATURAL JOIN Membership
       WHERE group_id IN ( SELECT group_id
                           FROM Membership
                           GROUP BY group_id
                           HAVING COUNT(username) > 1 );

DROP VIEW IF EXISTS StudentsWhoAlwaysWorkedInAGroup CASCADE;
CREATE VIEW StudentsWhoAlwaysWorkedInAGroup AS
       SELECT username
       FROM GroupsWithMultipleMembers
       GROUP BY username
       HAVING COUNT(assignment_id) = ( SELECT *
                                       FROM AssignmentsWhichAllowGroups);

DROP VIEW IF EXISTS UserGroups CASCADE;
CREATE VIEW UserGroups AS
       SELECT username, group_id
       FROM StudentsWhoAlwaysWorkedInAGroup NATURAL JOIN Membership;

DROP VIEW IF EXISTS UserPairs CASCADE;
CREATE VIEW UserPairs AS
       SELECT a.username AS student1, b.username AS student2
       FROM UserGroups a JOIN UserGroups b ON a.group_id = b.group_id AND a.username != b.username AND a.username < b.username;

DROP VIEW IF EXISTS PairsWhoAlwaysWorkTogether CASCADE;
CREATE VIEW PairsWhoAlwaysWorkTogether AS
       SELECT student1, student2
       FROM UserPairs
       GROUP BY student1, student2
       HAVING COUNT(*) = ( SELECT * FROM AssignmentsWhichAllowGroups );

INSERT INTO q9 ( SELECT * FROM PairsWhoAlwaysWorkTogether ); 
