SET search_path TO markus;

CREATE VIEW AssignmentsWhichAllowGroups (
       SELECT COUNT(assignment_id)
       FROM Assignment
       WHERE group_max > 1
);

CREATE VIEW GroupsWithMultipleMembers (
       SELECT assignment_id, group_id, username
       FROM AssignmentGroup NATURAL JOIN Membership
       WHERE group_id IN ( SELECT group_id
                           FROM Membership
                           GROUP BY group_id
                           HAVING COUNT(username) > 1 )
);

CREATE VIEW StudentsWhoAlwaysWorkedInAGroup (
       SELECT username
       FROM GroupsWithMultipleMembers
       GROUP BY username
       HAVING COUNT(assignment_id) = ( SELECT *
                                       FROM AssignmentsWhichAllowGroups)
);

CREATE VIEW UserGroups (
       SELECT username, group_id
       FROM StudentsWhoAlwaysWorkedInAGroup NATURAL JOIN Membership
);

CREATE VIEW UserPairs (
       SELECT a.username AS student1, b.username AS student2
       FROM UserGroups a JOIN UserGroups b ON a.group_id = b.group_id AND a.username != b.username AND a.username < b.username
);

CREATE VIEW PairsWhoAlwaysWorkTogether (
       SELECT student1, student2
       FROM UserPairs
       GROUP BY student1, student2
       HAVING COUNT(*) = ( SELECT * FROM AssignmentsWhichAllowGroups )
       
);

INSERT INTO q9 ( SELECT * FROM PairsWhoAlwaysWorkTogether ); 
