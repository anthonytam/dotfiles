SET search_path TO markus;

DROP TABLE IF EXISTS q7;
CREATE TABLE q7 (
	ta varchar(100)
);

DROP VIEW IF EXISTS NumStudentsInCourse CASCADE;
CREATE VIEW NumStudentsInCourse AS
       SELECT COUNT(username) AS numStudents
       FROM MarkusUser
       WHERE type = 'student';

DROP VIEW IF EXISTS TAWhoMarkedEveryAssignment CASCADE;
CREATE VIEW TAWhoMarkedEveryAssignment AS
       SELECT username
       FROM Grader NATURAL JOIN AssignmentGroup
       GROUP BY username
       HAVING COUNT(DISTINCT assignment_id) =
              (SELECT COUNT(DISTINCT assignment_id)
               FROM Assignment);

DROP VIEW IF EXISTS StudentsInGroupPerAssignment CASCADE;
CREATE VIEW StudentsInGroupPerAssignment AS
       -- Assignment, TA, student they graded
       SELECT assignment_id, group_id, username as student
       FROM AssignmentGroup NATURAL JOIN Membership;

DROP VIEW IF EXISTS TAWhoMarkedGroup CASCADE;
CREATE VIEW TAWhoMarkedGroup AS
       SELECT assignment_id, group_id, student, username
       FROM StudentsInGroupPerAssignment NATURAL JOIN Grader;

DROP VIEW IF EXISTS TAWhoMarkedEveryStudentOnAnAssignment CASCADE;
CREATE VIEW TAWhoMarkedEveryStudentOnAnAssignment AS
       SELECT DISTINCT username
       FROM TAWhoMarkedGroup
       GROUP BY username
       HAVING COUNT(DISTINCT student) = ( SELECT * FROM NumStudentsInCourse );

DROP VIEW IF EXISTS Solution CASCADE;
CREATE VIEW Solution AS
       SELECT username AS ta
       FROM TAWhoMarkedEveryStudentOnAnAssignment NATURAL JOIN TAWhoMarkedEveryAssignment;

INSERT INTO Q7 ( SELECT * FROM Solution );
