
SELECT * FROM bok WHERE tittel LIKE '%av%';

-- 1. Finn alle borettslag etablert i årene 1975-1985.

SELECT b.* FROM borettslag b WHERE etabl_aar >= 1975 AND etabl_aar <= 1985;
SELECT b.* FROM borettslag b WHERE etabl_aar BETWEEN 1975 AND 1985;


-- 2 Skriv ut en liste over andelseiere. Listen skal ha linjer som ser slik ut (tekster i kursiv er data fra databasen):
--   "fornavn etternavn, ansiennitet: ansiennitet år".
--  Listen skal være sortert på ansiennitet, de med lengst ansiennitet øverst.

-- dette er skrevet ut fra presentasjonen læreren brukte, men er selvsagt ikke riktig syntaks
SELECT fornavn || ' ' || etternavn || ', ansiennitet: ' || ansiennitet || ' år'  FROM andelseier a ORDER BY ansiennitet DESC;

-- DETTE ER SABLA RIKTIG SYNTAKS og virker mer elegant
SELECT CONCAT(fornavn, ' ', etternavn, ', ansiennitet: ', ansiennitet) 'listens linjer' FROM andelseier ORDER BY ansiennitet DESC;
-- første kontrollforsøk:
SELECT CONCAT(fornavn, ' ', etternavn, ', ansiennitet: ', ansiennitet), CONCAT(fornavn), CONCAT(' ', etternavn), CONCAT(', ansiennitet: ', ansiennitet) FROM andelseier ORDER BY ansiennitet DESC;

-- dette FUNKER men er ikke eksakt det oppgaven ber om
SELECT fornavn, etternavn, ansiennitet AS 'ansiennitet (år)' FROM andelseier ORDER BY `ansiennitet (år)` DESC;


-- 3. I hvilket år ble det eldste borettslaget etablert?

SELECT etabl_aar FROM borettslag ORDER BY etabl_aar ASC LIMIT 1;
SELECT MIN(etabl_aar) FROM borettslag ORDER BY etabl_aar;



-- 4. Finn adressene til alle bygninger som inneholder leiligheter med minst tre rom.

SELECT bygn_adr FROM bygning b JOIN leilighet l ON b.bygn_id = l.bygn_id WHERE ant_rom >= 3;
-- for verification purposes:
SELECT * FROM bygning b JOIN leilighet l ON b.bygn_id = l.bygn_id;


-- 5. Finn antall bygninger i borettslaget "Tertitten".

SELECT COUNT(*) 'antall bygninger' FROM borettslag b JOIN bygning bg ON b.bolag_navn = bg.bolag_navn WHERE b.bolag_navn='Tertitten';


-- 6. Lag en liste som viser antall bygninger i hvert enkelt borettslag. Listen skal være sortert på borettslagsnavn.
--    Husk at det kan finnes borettslag uten bygninger - de skal også med.

SELECT COUNT(bg.bolag_navn) 'antall bygninger', b.bolag_navn FROM borettslag b
  LEFT JOIN bygning bg ON b.bolag_navn = bg.bolag_navn
    GROUP BY b.bolag_navn ORDER BY b.bolag_navn ASC;
-- i COUNT: spesifisere en attributt som kan være NULL i høyre relasjon for å få riktig antall bygninger (vs med *, da blir antallet 1 for Lerken...)


-- 7. Finn antall leiligheter i borettslaget "Tertitten".

SELECT COUNT(bl.leil_nr) 'antall leiligheter' FROM borettslag bo JOIN
  (SELECT bg.bolag_navn, l.bygn_id, l.leil_nr FROM bygning bg JOIN leilighet l on bg.bygn_id = l.bygn_id) bl -- omg BL of course
    ON bo.bolag_navn = bl.bolag_navn WHERE bo.bolag_navn='Tertitten';

-- spesifisere LEFT JOIN i tilfelle 0 leiligheter? tester det:
SELECT COUNT(bl.leil_nr) 'antall leiligheter' FROM borettslag bo
  JOIN (SELECT bg.bolag_navn, l.bygn_id, l.leil_nr FROM bygning bg JOIN leilighet l on bg.bygn_id = l.bygn_id) bl
    ON bo.bolag_navn = bl.bolag_navn WHERE bo.bolag_navn='Lerken';
-- nope, trengs ikke, funker fint (til og med med * i COUNT)


-- 8. Hvor høyt kan du bo i borettslaget "Tertitten"?

SELECT bo.bolag_navn, MAX(ant_etasjer) 'maks antall etasjer' FROM borettslag bo
  JOIN bygning bg ON bo.bolag_navn = bg.bolag_navn WHERE bo.bolag_navn='Tertitten';


-- 9. Finn navn og nummer til andelseiere som ikke har leilighet.

SELECT fornavn, etternavn, telefon FROM andelseier a LEFT JOIN leilighet l ON a.and_eier_nr = l.and_eier_nr WHERE l.bygn_id IS NULL;


-- 10. Finn antall andelseiere pr borettslag, sortert etter antallet. Husk at det kan finnes borettslag uten andelseiere - de skal også med.

SELECT b.bolag_navn, COUNT(a.and_eier_nr) 'antall andelseiere' FROM borettslag b
  LEFT JOIN andelseier a ON b.bolag_navn = a.bolag_navn GROUP BY b.bolag_navn ORDER BY 'antall andelseiere';
-- må gruppere etter attributten i borettslag, naturligvis


-- 11. Skriv ut en liste over alle andelseiere. For de som har leilighet, skal leilighetsnummeret skrives ut.

SELECT fornavn, etternavn, leil_nr FROM andelseier a LEFT JOIN leilighet l ON a.and_eier_nr = l.and_eier_nr ORDER BY fornavn, etternavn ASC;
-- sortert og greier

SELECT etternavn, fornavn, leil_nr FROM andelseier a LEFT JOIN leilighet l ON a.and_eier_nr = l.and_eier_nr ORDER BY etternavn, fornavn ASC;
-- just cuz I can

-- 12. Hvilke borettslag har leiligheter med eksakt 4 rom?

-- regner med det menes 4 rom i MINST EN av leilighetene, ikke ALLE.

-- tester først med en oversikt uten spesifikt 4 rom
SELECT bo.bolag_navn, ant_rom FROM borettslag bo
  JOIN (SELECT bolag_navn, ant_rom FROM bygning b JOIN leilighet l on b.bygn_id = l.bygn_id) bl
    ON bo.bolag_navn = bl.bolag_navn;

-- legger til ei leilighet med 4 rom
INSERT INTO leilighet VALUES(DEFAULT, 4, 103, 2, 1, 5);

-- legger inn spesifikasjonen:
SELECT bo.bolag_navn, ant_rom FROM borettslag bo
  JOIN (SELECT bolag_navn, ant_rom FROM bygning b JOIN leilighet l on b.bygn_id = l.bygn_id) bl
    ON bo.bolag_navn = bl.bolag_navn WHERE bl.ant_rom = 4;

-- snevrer inn til kun én tuppel per bolag, to alternativer:
SELECT DISTINCT bo.bolag_navn FROM borettslag bo
  JOIN (SELECT bolag_navn, ant_rom FROM bygning b JOIN leilighet l on b.bygn_id = l.bygn_id) bl
    ON bo.bolag_navn = bl.bolag_navn WHERE bl.ant_rom = 4;

-- annen mulighet (flytter WHERE-kravet inn i underspørringa, grupperer den etter bolag_navn.
SELECT bo.bolag_navn FROM borettslag bo
  JOIN (SELECT bolag_navn, ant_rom FROM bygning b JOIN leilighet l on b.bygn_id = l.bygn_id WHERE ant_rom = 4 GROUP BY bolag_navn) bl
    ON bo.bolag_navn = bl.bolag_navn;


-- 13. Skriv ut en liste over antall andelseiere pr postnr og poststed, begrenset til de som bor i leiligheter tilknyttet et borettslag.
-- Husk at postnummeret til disse er postnummeret til bygningen de bor i, og ikke postnummeret til borettslaget.
-- Du trenger ikke ta med poststeder med 0 andelseiere. (Ekstraoppgave: Hva hvis vi vil ha med poststeder med 0 andelseiere?) --> WOO RETCON

-- konstruerer underspørringen:
SELECT bl.postnr, a.and_eier_nr FROM andelseier a JOIN
  (SELECT b.postnr, l.and_eier_nr FROM bygning b JOIN leilighet l ON b.bygn_id = l.bygn_id) bl
    ON a.and_eier_nr = bl.and_eier_nr;

-- slenger den inn i ramma
SELECT p.postnr, p.poststed, COUNT(bla.and_eier_nr) FROM poststed p LEFT JOIN
  (SELECT bl.postnr, a.and_eier_nr FROM andelseier a
    JOIN (SELECT b.postnr, l.and_eier_nr FROM bygning b JOIN leilighet l ON b.bygn_id = l.bygn_id) bl
    ON a.and_eier_nr = bl.and_eier_nr) bla
  ON p.postnr = bla.postnr GROUP BY p.postnr; -- forgot that GROUP BY could be needed...


