SET search_path TO markus;

DROP TABLE IF EXISTS q10;
CREATE TABLE q10 (
	group_id integer,
	mark real,
	compared_to_average real,
	status varchar(5)
);

--Average Stuff
DROP VIEW IF EXISTS groupsForAssignment CASCADE;
CREATE VIEW groupsForAssignment AS
       SELECT assignment_id, group_id
       FROM Assignment NATURAL JOIN AssignmentGroup
       WHERE description = 'A1';

DROP VIEW IF EXISTS rubricItemsForAssignment CASCADE;
CREATE VIEW rubricItemsForAssignment AS
       SELECT rubric_id, assignment_id, weight*out_of AS weightedOutOf
       FROM rubricItem NATURAL JOIN groupsForAssignment
       GROUP BY assignment_id, rubric_id;

DROP VIEW IF EXISTS totalWeightForAssignment CASCADE;
CREATE VIEW totalWeightForAssignment AS
       SELECT assignment_id, SUM(weightedOutOf) AS totalWeightedOutOf
       FROM rubricItemsForAssignment
       GROUP BY assignment_id;

DROP VIEW IF EXISTS groupsWithPercent CASCADE;
CREATE VIEW groupsWithPercent AS
       SELECT group_id, mark/totalWeightedOutOf * 100 AS totalMark
       FROM groupsForAssignment NATURAL Left JOIN Result NATURAL JOIN totalWeightForAssignment;

DROP VIEW IF EXISTS AverageForA1 CASCADE;
CREATE VIEW AverageForA1 AS
       SELECT AVG(totalMark)
       FROM groupsWithPercent;

DROP VIEW IF EXISTS GroupsAboveAverage CASCADE;
CREATE VIEW GroupsAboveAverage AS
       SELECT group_id, totalMark AS mark, totalMark - (SELECT * FROM AverageForA1) AS compared_to_average, 'above'::varchar(5) AS status
       FROM groupsWithPercent
       WHERE totalMark > (SELECT * FROM AverageForA1);

DROP VIEW IF EXISTS GroupsAtAverage CASCADE;
CREATE VIEW GroupsAtAverage AS
       SELECT group_id, totalMark AS mark, totalMark - (SELECT * FROM AverageForA1) AS compared_to_average, 'at'::varchar(5) AS status
       FROM groupsWithPercent
       WHERE totalMark = (SELECT * FROM AverageForA1);

DROP VIEW IF EXISTS GroupsBelowAvergae CASCADE;
CREATE VIEW GroupsBelowAverage AS
       SELECT group_id, totalMark AS mark, totalMark - (SELECT * FROM AverageForA1) AS compared_to_average, 'below'::varchar(5) AS status
       FROM groupsWithPercent
       WHERE totalMark < (SELECT * FROM AverageForA1);

DROP VIEW IF EXISTS GroupsWithNoMark CASCADE;
CREATE VIEW GroupsWithNoMark AS
       SELECT group_id, NULL as mark, NULL AS compared_to_average, NULL AS status
       FROM groupsWithPercent
       WHERE totalMark IS NULL;

DROP VIEW IF EXISTS Solution CASCADE;
CREATE VIEW Solution AS
       (SELECT * FROM GroupsAboveAverage)
       UNION
       (SELECT * FROM GroupsAtAverage)
       UNION
       (SELECT * FROM GroupsBelowAverage)
       UNION
       (SELECT * FROM GroupsWithNoMark);

INSERT INTO q10 ( SELECT * FROM Solution );
