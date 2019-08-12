-- we make try

-- fallgruver!!! omg who would have thoooought

-- le select can return nothing, and it won't change the d a t a b a s e

-- prefixing necessary aye


-- OPPGAVE 1.1
-- Skriv ut alle kampene (31 stk.) med mdate og teamname på begge lagene i hver kamp sortert stigende etter når
-- kampen ble spillt (dvs. finalen til slutt i resultattabellen).
SELECT mdate, teamname FROM game JOIN eteam ON(team1=eteam.id OR team2=eteam.id) ORDER BY STR_TO_DATE(mdate, '%d %M %Y') ASC;

/*

DATE_FORMAT

%d for day number

%m for month number with leading 0
%M for month name

%Y for full year, four digitsdigits
%y for two digits year

More specific ones:


Link:
http://www.mysqltutorial.org/mysql-date_format/

 */

-- ORDER BY attr limit 5;

-- bruke LIKE og jokertegn _ og % (s% strenger med s i seg) for å søke gjennom tekst
-- Agreggeringsfunksjonene MÅ KUNNES til eksamen osv.
-- COUNT(), AVG(), MAX(), MIN(), SUM() --> de er funksjoner og gir ett tall.
-- GROUP BY --- HAVING + evt en av funksjonene ovenfor, nyttig tydeligvis

-- there exists a monstrous NATURAL JOIN omg
-- teacher wants us to specify what it should be joined on, I see
-- bruke NOT IN for å få f.eks. bare leverandører uten leveranser --> subquery gives multiple tuples, can't use = or anything like that

SELECT MAX(lev_nr) FROM leverandor;
SELECT lev_navn, lev_nr FROM leverandor
WHERE lev_nr = (SELECT MAX(lev_nr) FROM leverandor);

-- max takes all lev_nr - of course it duz.

-- mange skriver slik på eksamen:
SELECT lev_navn, lev_nr FROM leverandor
WHERE lev_nr = MAX(lev_nr);
-- MÅ gjøres som subspørring
-- INVALID USE OF GROUP FUNCTION

-- vanlig å bruke ei aggregatfunksjon sammen med GROUP BY for å f.eks. bare telle for hver gruppe, ikke hele tabellen!
-- "sum for å summere radene i en faktura" okei

-- SPM: hva er forskjellen på WHERE og HAVING? Annet enn at HAVING krever GROUP BY foran? og med LIMIT kan man begrense gruppene på en eller annen måte?
-- ha ha halve jula

-- subspørring inne i projeksjonen...

-- 1 thing: don't write excessively complex queries, it can probably be shortened.
-- EXISTS vs IN idk yeah sure
