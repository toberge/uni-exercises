-- Du skal lage en del av et program som kan brukes ved den månedlige faktureringen, det vil si påløpte kostnader i tabellene prosjektkostnader og prosjektarbeid.

-- Regnskapssystemet er ikke integrert med det programmet du arbeider med. Dataene skal overføres dit via tekstfiler.
-- Tekstfilen har fire kolonner:   prosjekt_id, kunde, tekst og beløp,    atskilt med semikolon.

-- For prosjektarbeid skal alle timekostnadene for denne måneden og det aktuelle prosjektet summeres til én linje med tekst "timer",
-- for prosjektkostnader skal listen inneholde en linje pr linje i tabellen for denne måneden. Listen skal være sortert på kunde og prosj_id.

-- Programmet skal sette faktura_sendt lik dagens dato i de to tabellene.

-- Tips: Det fins funksjoner i MySQL for å sjekke f.eks. måned, se http://dev.mysql.com/doc/refman/5.5/en/date-and-time-functions.html, f.eks. Built-in-functions, MONTH().


/* RELMOD:

ansatt(__ans_id__, fornavn, etternavn, timelønn)
prosjekt(__prosj_id__, prosj_tittel, kunde, budsjett, timefaktor, leder_ans_id*)
prosjektkostnader(__prosj_kost_id__, dato, beløp, tekst, faktura_sendt, prosj_id*)
prosjektarbeid(__ans_id*, prosj_id*, dato__, ant_timer, faktura_sendt)

 */

 SELECT * FROM prosjektkostnader;

 SELECT MONTH(dato) AS mnd, SUM(beløp) FROM prosjektkostnader GROUP BY mnd;


-- Timepåslaget, kalt timefaktor i tabellen prosjekt, avtales for hvert enkelt prosjekt. Dette er faktoren timelønna skal multipliseres med ved fakturering.
-- Eksempel: Dersom en ansatt har 200 kr i timelønn, og faktoren er 2,1, betyr det at 200 kr * 2,1 = 420 kr skal faktureres ut pr time.


SELECT a.ans_id, p.prosj_id, dato, ant_timer, timelønn, timefaktor, (ant_timer * timefaktor * timelønn) AS lønn, kunde
FROM prosjekt p, prosjektarbeid pa, ansatt a
WHERE a.ans_id = pa.ans_id AND p.prosj_id = pa.prosj_id;


-- For prosjektarbeid skal alle timekostnadene for denne måneden og det aktuelle prosjektet summeres til én linje med tekst "timer", --> rader = ant proj den mnd

DROP VIEW IF EXISTS timeoversikt, kostnadoversikt;

CREATE VIEW timeoversikt AS
SELECT mnd, prosj_id, 'timer' AS tekst, kunde, SUM(lønn) AS beløp -- alle kolonnene som trengs + mnd for å identifisere
FROM (
      SELECT MONTH(dato) AS mnd, p.prosj_id, kunde, (ant_timer * timefaktor * timelønn) AS lønn
      FROM prosjekt p,
           prosjektarbeid pa,
           ansatt a
      WHERE a.ans_id = pa.ans_id
      AND p.prosj_id = pa.prosj_id
) summert
GROUP BY mnd, prosj_id
ORDER BY mnd, prosj_id, kunde ASC; -- gruppere etter BÅDE måned OG prosjekt for å skille dem


-- BETTER VERSION INCOMING
CREATE VIEW timeoversikt AS
SELECT MONTH(dato) AS mnd, p.prosj_id, 'timer' AS tekst, kunde, SUM(ant_timer * timefaktor * timelønn) AS beløp -- alle kolonnene som trengs + mnd for å identifisere
FROM prosjekt p,
     prosjektarbeid pa,
     ansatt a
WHERE a.ans_id = pa.ans_id
AND p.prosj_id = pa.prosj_id
GROUP BY mnd, prosj_id
ORDER BY mnd, prosj_id, kunde ASC;

-- Tabellen prosjektkostnader inneholder andre kostnader (dvs ikke timekostnader) knyttet til prosjektene, f.eks. innkjøp av utstyr, reisekostnader, etc.

-- for prosjektkostnader skal listen inneholde en linje pr linje i tabellen for denne måneden. Listen skal være sortert på kunde og prosj_id.

CREATE VIEW kostnadoversikt AS
SELECT MONTH(dato) AS mnd, p.prosj_id, tekst, kunde, beløp
FROM prosjekt p,
prosjektkostnader pk
WHERE p.prosj_id = pk.prosj_id
ORDER BY mnd, p.prosj_id, kunde ASC, beløp DESC;

-- en samlet UNION av underspørringene

SELECT CONCAT_WS(';', prosj_id, tekst, kunde, beløp), prosj_id -- slår sammen strengene, etterlater en prosj_id så programmet kan HENTE DEN UT VELDIG LETT.
FROM (
       SELECT *
       FROM timeoversikt
          UNION
       SELECT *
       FROM kostnadoversikt
          ORDER BY prosj_id ASC, kunde ASC, beløp DESC -- gjelder for unionen
     ) oversikt
WHERE mnd = 10 ;
-- skal allerede være sortert i riktig rekkefølge


-- sette datoen fakturaen ble sendt

UPDATE prosjektarbeid pa, prosjektkostnader pk
SET pa.faktura_sendt = CURDATE(), pk.faktura_sendt = CURDATE()
WHERE (pa.prosj_id = 101 OR pk.prosj_id = 101)
  AND (MONTH(pa.dato) = 10 OR MONTH(pk.dato) = 10)
  AND pa.faktura_sendt IS NULL AND pk.faktura_sendt IS NULL;


