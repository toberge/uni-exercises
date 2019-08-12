

CREATE TABLE rectangle (
                         width INT,
                         height INT,
                         area INT GENERATED ALWAYS AS (width*height) -- generert kolonne
);


INSERT INTO rectangle(width, height) VALUES(1, 1), (2, 3);

SELECT * FROM rectangle;

-- does not work in our version:

CREATE TABLE rectangle (
                         width INT,
                         height INT,
                         area INT DEFAULT (width*height)
);

INSERT INTO rectangle(width, height) VALUES(1, 1), (2, 3);

UPDATE rectangle SET area = area + 1; -- gir ikke helt mening, nei



-- kan indeksere genererte kolonner

-- grunn til gen. kol.: JSON-støtte (JavaScript Object Notation) "courses":["physisc","abcdef"]

CREATE TABLE students (data JSON);
INSERT INTO students(data) VALUES ('
{
  "id": 123,
  "name": "Joe",
  "courses":["physisc","chemistry"]
}
');
SELECT * FROM students;

-- and THEN:
ALTER TABLE students ADD COLUMN name VARCHAR(20) AS (data ->> '$.name'); -- gjøre fornuftig

UPDATE students SET data = JSON_ARRAY_APPEND(data, '$.courses', 'biology') WHERE data->>'$.id'=123; -- add friggin biology

-- annen formatering
CREATE TABLE navn (data JSON, _id AS (data ->> '$.id')); -- fønker inte her


-- Common Table Expressions (CTEs) --> gi navn til ei spørring
WITH
  hello AS (
    SELECT * FROM rectangle WHERE height = 1
  )
SELECT * FROM rectangle; -- meningsløst men yeah, funker som spesifikke views

SELECT *
FROM (setning) AS cake
       NATURAL JOIN (setning) AS dessert;


CREATE TABLE employees(name VARCHAR(20), manager VARCHAR(20));

INSERT INTO employees VALUES
('Big Damn Boss', NULL),
('Manager Joe', 'Big Damn Boss'),
('Hello', 'Manager Joe'),
('Alias', 'Manager Joe')
;

-- rekursiv CTE... --> trestruktur, loope gjennom
WITH RECURSIVE
     subordinates AS (
       SELECT name FROM employees WHERE manager = 'Manager Joe'
       UNION ALL
       SELECT e.name FROM employees e JOIN subordinates s ON e.manager = s.name -- rekursiv
     )
SELECT name FROM subordinates;

-- omg you can program here now...


-- erstatning for GROUP BY --> uten å kollapse sammen

-- COUNT(something) OVER (partition_name) AS given_name

-- vindusfunksjoner aye aye


-- ROMLIGE DATA

-- SELECT * FROM information_schema.OPS EG HAr IKKE TILGANG;

-- SPATIAL INDEX (pos) --- med: (def av pos)
-- ST_WITHIN(geometry shit)