SET search_path TO markus;

--Create the table for the solutions
DROP TABLE IF EXISTS q2;
CREATE TABLE q2 (
	ta_name varchar(100),
	average_mark_all_assignments real,
	mark_change_first_last real
);

--Get a list of all TA's avaliable to us
DROP VIEW IF EXISTS TAs CASCADE;
CREATE VIEW TAs AS
       SELECT username
       FROM MarkusUser
       WHERE type='TA';

-- Condition One
-- Find TA's who marked every assignment
DROP VIEW IF EXISTS TAWhoMarkedEveryAssignment CASCADE;
CREATE VIEW TAWhoMarkedEveryAssignment AS
       SELECT username
       FROM Grader NATURAL JOIN AssignmentGroup
       GROUP BY username
       HAVING COUNT(DISTINCT assignment_id) =
              (SELECT COUNT(DISTINCT assignment_id)
               FROM Assignment);

--Find a list of TA's who makred ten groups and the assignment they marked
--the groups on 
DROP VIEW IF EXISTS TAWhoMarkedTenGroups CASCADE;
CREATE VIEW TAWhoMarkedTenGroups AS
        SELECT assignment_id, username
        FROM Result NATURAL JOIN Grader NATURAL JOIN AssignmentGroup
        GROUP BY assignment_id, username
        HAVING COUNT(group_id) >= 10;

-- Condition Two
--TA's who amrked ten groups for every assignment which exist
DROP VIEW IF EXISTS TAWhoMarkedTenGroupsForEveryAssignment CASCADE;
CREATE VIEW TAWhoMarkedTenGroupsForEveryAssignment AS
       SELECT t.username
       FROM TAWhoMarkedTenGroups t, ( SELECT COUNT(DISTINCT assignment_id) AS allAssignments FROM Assignment) a
       GROUP BY t.username, a.allAssignments
       HAVING COUNT(DISTINCT t.assignment_id) = a.allAssignments;

--Calculates the weighted value of every rubric item for
--every assignment
DROP VIEW IF EXISTS rubricItemsForAssignment CASCADE;
CREATE VIEW rubricItemsForAssignment AS
       SELECT rubric_id, assignment_id, weight*out_of AS weightedOutOf
       FROM rubricItem;

--Calculates the total weright for every assignment by
--summing the value of the rubric items
DROP VIEW IF EXISTS totalWeightForAssignment CASCADE;
CREATE VIEW totalWeightForAssignment AS
       SELECT assignment_id, SUM(weightedOutOf) AS totalWeight
       FROM rubricItemsForAssignment
       GROUP BY assignment_id;

--Calculates the percentage mark of every group that has
--been graded
DROP VIEW IF EXISTS groupsForAssignment CASCADE;
CREATE VIEW groupsForAssignment AS
       SELECT assignment_id, group_id, username AS student
       FROM Assignment NATURAL JOIN AssignmentGroup NATURAL JOIN Membership;

--Calculates the pecentage mark of every group that has
--been graded
DROP VIEW IF EXISTS groupsWithPercent CASCADE;
CREATE VIEW groupsWithPercent AS
       SELECT assignment_id, group_id, student, username, due_date, mark/totalWeight*100 AS totalMark
       FROM groupsForAssignment NATURAL JOIN Result NATURAL JOIN totalWeightForAssignment NATURAL JOIN Grader NATURAL JOIN Assignment;

--Calculates the average percentage mark acriss assignments by students
--not groups
DROP VIEW IF EXISTS averagePercentagePerAssignment CASCADE;
CREATE VIEW averagePercentagePerAssignment AS
       SELECT assignment_id, due_date, username, AVG(totalMark) as averageMark
       FROM groupsWithPercent
       GROUP BY assignment_id, due_date, username
       ORDER BY username, due_date;

--Find trends where the avaerage increased over time
DROP VIEW IF EXISTS increasingAverages CASCADE;
CREATE VIEW increasingAverages AS
       SELECT p.assignment_id, p.due_date, p.username, p.averageMark
       FROM averagePercentagePerAssignment p
       WHERE averageMark > ALL (SELECT a.averageMark
                                  FROM averagePercentagePerAssignment a
                                  WHERE a.username = p.username AND
                                        a.due_date < p.due_date);

-- Condition Three
-- Generate a list of TA's who have shown an increasing average trend
DROP VIEW IF EXISTS TAWithIncreasingAverages CASCADE;
CREATE VIEW TAWithIncreasingAverages AS
       SELECT ia.username
       FROM increasingAverages ia
       WHERE ( SELECT COUNT(assignment_id)
               FROM increasingAverages AS i
               GROUP BY i.username
               HAVING i.username = ia.username ) =
               ( SELECT COUNT(assignment_id)
               FROM averagePercentagePerAssignment AS ap
               GROUP BY ap.username
               HAVING ap.username = ia.username );

-- Calculate the average mark across all assignments for any
--given TA
DROP VIEW IF EXISTS AverageAllAssignments CASCADE;
CREATE VIEW AverageAllAssignments AS
       SELECT username, AVG(totalMark) AS average_mark_all_assignments
       FROM groupsWithPercent NATURAL JOIN TAWithIncreasingAverages
       GROUP BY username;

--Calculate the larest mark variation between assignments for a TA
DROP VIEW IF EXISTS markChangeBetweenAssignments CASCADE;
CREATE VIEW markChangeBetweenAssignments AS
       SELECT username, MAX(averageMark) - MIN(averageMark) as mark_change_first_last
       FROM averagePercentagePerAssignment NATURAL JOIN TAWithIncreasingAverages
       GROUP BY username;

--Join all condition tables to leave us with on the TA's who
--meet all 3 conditions
DROP VIEW IF EXISTS TAWhoMeetsAllConditions CASCADE;
CREATE VIEW TAWhoMeetsAllConditions AS
       SELECT username, firstname || ' ' || surname AS ta_name
       FROM TAWithIncreasingAverages NATURAL JOIN
            TAWhoMarkedTenGroupsForEveryAssignment NATURAL JOIN
            TAWhoMarkedEveryAssignment NATURAL JOIN
            MarkusUser;

--Join all required attributes together to formulate the solution
DROP VIEW IF EXISTS Solution CASCADE;
CREATE VIEW Solution AS
       SELECT DISTINCT ta_name, average_mark_all_assignments, mark_change_first_last
       FROM TAWhoMeetsAllConditions NATURAL JOIN
            markChangeBetweenAssignments NATURAL JOIN
            AverageAllAssignments;

--Insert the result into the solutions table
INSERT INTO q2 (SELECT * FROM Solution);
