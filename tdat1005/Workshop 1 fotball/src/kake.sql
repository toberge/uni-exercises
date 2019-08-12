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

-- EXER SIZE OF BEAN
-- OPPGAVE 1.2 TODO
-- Finn teamname for begge lagene og totalt antall mål i hver kamp , inkl. de kampene som endte 0-0 (hvor antall mål er 0), sortert stigende etter når kampen ble spilt.
-- Ekstra: Finn resultatene for hver kamp inkl. de kampene som endte 0-0 (hvor antall mål er 0).
SELECT * FROM goal JOIN eteam ON(goal.teamid = eteam.id) /*ORDER BY STR_TO_DATE(game.mdate, '%d %M %Y') ASC*/;
SELECT * FROM game g, eteam t1, eteam t2 WHERE g.team1 = t1.id AND g.team2 = t2.id; -- the base
JOIN (SELECT COUNT(*) 'num goals' FROM goal JOIN game WHERE goal.matchid = game.id GROUP BY matchid) a; -- the count

SELECT mdate, g.id, t1.teamname, t2.teamname, a.* FROM game g, eteam t1, eteam t2, -- project the ones you want
(SELECT matchid, COUNT(gtime) 'num goals' FROM goal JOIN game WHERE goal.matchid = game.id GROUP BY matchid) a -- getting the count, must be grouped
WHERE g.team1 = t1.id AND g.team2 = t2.id AND g.id = a.matchid -- joining on this
ORDER BY STR_TO_DATE(mdate, '%d %M %Y') ASC; -- just date sorting
-- doesn't show the ones with 0 goals

-- start of stupid "epiphany"
-- MAYBE BECAUSE THERE AREN'T ANY
-- nah, there are... relax, you tested before
INSERT INTO eteam VALUES ('SQL','Unknown Entity','Daniela Smugface');
INSERT INTO game VALUES (9999,'14 August 2012','Nowhere','SQL','CRO');
-- hm doesn't quite work after this
-- YEAH this time your shit DOESN'T WORK so relax



-- an attempt
SELECT mdate, g.id, t1.teamname, t2.teamname, a.* FROM game g, eteam t1, eteam t2, -- project the ones you want
(SELECT matchid, COUNT(*) 'num goals' FROM goal RIGHT OUTER JOIN game ON goal.matchid = game.id /*OR goal.player IS NULL*/ GROUP BY matchid) a -- getting the count, must be grouped
WHERE g.team1 = t1.id AND g.team2 = t2.id AND g.id = a.matchid -- joining on this
ORDER BY STR_TO_DATE(mdate, '%d %M %Y') ASC; -- just date sorting
-- afaik it doesn't work

-- OPPGAVE 1.3
-- List the the dates of the matches and the name of the team (teamname) in which Fernando Santos was the team coach (både team1 og team2).
SELECT mdate, t1.teamname, t1.coach, t2.teamname, t2.coach FROM game g, eteam t1, eteam t2
WHERE g.team1 = t1.id AND g.team2 = t2.id AND (t1.coach = 'Fernando Santos' OR t2.coach = 'Fernando Santos');
-- left the coach names in as tests

-- OPPGAVE 1.4
-- Skriv ut alle lag (teamname) som har skåret minst 4 mål totalt, sotert (synkende) etter antall mål.
SELECT teamname, a.goals FROM eteam t,
(SELECT teamid, COUNT(*) 'goals' FROM goal JOIN game WHERE teamid = team1 OR teamid = team2 GROUP BY teamid HAVING COUNT(*) > 3) a
WHERE teamid = id ORDER BY a.goals DESC;
-- versus alle
SELECT teamname, a.goals FROM eteam t,
(SELECT teamid, COUNT(*) 'goals' FROM goal JOIN game WHERE teamid = team1 OR teamid = team2 GROUP BY teamid) a
WHERE teamid = id ORDER BY a.goals DESC;
-- og det jeg prøvde på først fungerer
SELECT teamname, a.goals FROM eteam t,
(SELECT teamid, COUNT(*) 'goals' FROM goal JOIN game WHERE teamid = team1 OR teamid = team2 GROUP BY teamid) a
WHERE teamid = id AND a.goals > 3 ORDER BY a.goals DESC;
-- med hvorvidt conditionen er i HAVING eller WHERE som eneste forskjell
-- som ikke har noe å si her men kunne hatt det i andre tilfeller AFAIK
-- WHERE begrenser enkeltrader, HAVING begrenser grupper --> gruppene ender opp som rader her, so it okey

-- OPPGAVE 1.5
-- Vis alle lagene (teamname) som aldri har scoret mål.
SELECT teamname FROM eteam LEFT OUTER JOIN goal ON id = teamid
WHERE teamid IS NULL;

-- comparing the two tables makes me believe THERE ARE NO SUCH TEAMS
SELECT DISTINCT teamid FROM goal;
SELECT id FROM eteam;
-- OMG WHAT BULLSHIT

-- making a bogus team
INSERT INTO eteam VALUES ('SQL','Unknown Entity','Daniela Smugface');
SELECT teamname FROM eteam LEFT OUTER JOIN goal ON id = teamid
WHERE teamid IS NULL;
-- now it works, """yay"""

-- OPPGAVE 1.6
-- Vis navn på spillere med teamname på alle spillere som skåret på "National Stadium".
SELECT DISTINCT player, teamname FROM eteam JOIN
(SELECT teamid, player, stadium FROM goal JOIN game ON (teamid = team1 OR teamid = team2)) a
ON eteam.id = a.teamid WHERE a.stadium='National Stadium Warsaw'; -- WHY U NOT WRITE FULL NAME

-- testing:
SELECT teamid, player, stadium, teamid, team1, team2 FROM goal JOIN game ON (teamid = team1 OR teamid = team2);

-- OPPGAVE 1.7 TODO
-- Vis navn på spillere med teamname som har skåret minst 3 mål sortert på antall mål.
SELECT DISTINCT player, teamname, goals FROM eteam JOIN
(SELECT DISTINCT player, team1, team2, COUNT(*) 'goals' FROM goal JOIN game WHERE teamid = team1 OR teamid = team2 GROUP BY goal.player) a
ON ()
WHERE a.goals > 2 ORDER BY a.goals DESC;
-- NAAAAI

SELECT player, teamname, goals

-- OPPGAVE 1.8 TODO
-- Finn navn på spillere som kun har scoret i én kamp.
-- will be easier with a working 1.7

-- OPPGAVE 1.9 TODO
-- Finn antall kamper som endte med seier til "hjemmelaget" dvs team1, gitt at kampen ikke endte uavgjort.