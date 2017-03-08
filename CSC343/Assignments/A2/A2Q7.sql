SET search_path TO markus;

CREATE VIEW NumStudentsInCourse (
       SELECT COUNT(username) AS numStudents
       FROM MarkusUser
       WHERE type = 'student'
);

CREATE VIEW TAWhoMarkedEveryAssignment (
       SELECT username
       FROM Grader NATURAL JOIN AssignmentGroup
       GROUP BY username
       HAVING COUNT(DISTINCT assignment_id) =
              (SELECT COUNT(DISTINCT assignment_id)
               FROM Assignment)
);

CREATE VIEW NumberOfStudentsInGroupPerAssignment (
       -- Assignment, TA, student they graded
       SELECT assignment_id, group_id, COUNT(DISTINCT username) as numMembers
       FROM AssignmentGroup NATURAL JOIN Membership
       GROUP BY assignment_id, group_id
);

CREATE VIEW TAWhoMarkedGroup (
       SELECT assignment_id, group_id, numMembers, username
       FROM NumberOfStudentsInGroupPerAssignment NATURAL JOIN Grader
);

CREATE VIEW TAWhoMarkedEveryStudentOnAnAssignment (
       SELECT DISTINCT username
       FROM TAWhoMarkedGroup
       GROUP BY assignment_id, username
       HAVING SUM(numMembers) = ( SELECT * FROM NumStudentsInCourse )
);

CREATE VIEW Solution (
       SELECT username AS ta
       FROM TAWhoMarkedEveryStudentOnAnAssignment NATURAL JOIN TAWhoMarkedEveryAssignment
);

INSERT INTO Q7 ( SELECT * FROM Solution );
