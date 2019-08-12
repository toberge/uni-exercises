-- ***Oppgave 1***

-- Bruk dette scriptet (MySQL) for å lage en database. Merk at vi ikke har NULL-verdier i denne databasen.

-- Gjør deg kjent med databasen ved f.eks. å tegne et ER-diagram.

-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
--  a. List ut all informasjon (ordrehode og ordredetalj) om ordrer for leverandør nr 44.

      -- inkluderer beskrivelse fra delinfo:
      SELECT h.ordrenr, h.dato, h.status, d.delnr, d.beskrivelse, d.kvantum FROM ordrehode h JOIN -- unngå dupliserte kolonner
        (SELECT ordrenr, d.delnr, beskrivelse, kvantum FROM ordredetalj d JOIN delinfo i on d.delnr = i.delnr) d -- subquery for fetching part description
          ON h.ordrenr = d.ordrenr WHERE levnr=44; -- spesifisere
      -- -- ||| THE ANSWER IS FOUND ||| -- --


-- TODO natural join

-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
--  b. Finn navn og by ("LevBy") for leverandører som kan levere del nummer 1.

      SELECT navn, levby, delnr FROM levinfo l JOIN
        (SELECT d.ordrenr, h.levnr, d.delnr FROM ordrehode h JOIN ordredetalj d ON h.ordrenr = d.ordrenr) o -- må slå sammen for å finne både lev- og delnr
          ON l.levnr = o.levnr WHERE o.delnr = 1;
      -- -- ||| THE ANSWER IS FOUND ||| -- --

-- TODO prisinfo og levinfo, ingen subquery
-- yeah, if you smart you use the shortest way




-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
--  c. Finn nummer, navn og pris for den leverandør som kan levere del nummer 201 til billigst pris.

      SELECT l.levnr, l.navn, p.pris FROM levinfo l JOIN prisinfo p on l.levnr = p.levnr
        WHERE p.delnr = 201 ORDER BY p.pris ASC LIMIT 1;
      -- -- ||| THE ANSWER IS FOUND ||| -- --


-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
--  d. Lag fullstendig oversikt over ordre nr 16, med ordrenr, dato, delnr, beskrivelse, kvantum, (enhets-)pris og beregnet beløp (=pris*kvantum).

-- konstruerte denne underspørringa først
SELECT l.levnr, pi.delnr, pi.beskrivelse, pi.levnr, pi.pris FROM levinfo l JOIN (SELECT i.delnr, i.beskrivelse, p.levnr, p.pris FROM delinfo i JOIN prisinfo p ON i.delnr = p.delnr) pi ON l.levnr = pi.levnr;

      -- så kommer hele sulamitten
      -- YEAH I KNOW ikke riktig navnsetting, skulle vel brukt underscore yada yada
      SELECT ordrenr, dato, delnr, beskrivelse, kvantum, pris, (pris*kvantum) belop FROM ordrehode datoSetter JOIN

        (SELECT indreInfo.levnr, indreInfo.delnr, indreInfo.beskrivelse, kvantifisering.kvantum, indreInfo.pris FROM ordredetalj kvantifisering JOIN -- får med kvantum

          (SELECT knytteTilLevnr.levnr, delerMedPris.delnr, delerMedPris.beskrivelse, delerMedPris.pris FROM levinfo knytteTilLevnr JOIN -- får med levnr
            (SELECT i.delnr, i.beskrivelse, p.levnr, p.pris FROM delinfo i JOIN prisinfo p ON i.delnr = p.delnr) delerMedPris -- trenger prisinfo, joiner med delinfo men kunne joina med levinfo
              ON knytteTilLevnr.levnr = delerMedPris.levnr) indreInfo ON kvantifisering.delnr = indreInfo.delnr -- joiner på delnr

        ) d ON datoSetter.levnr = d.levnr WHERE datoSetter.ordrenr = 16; -- joiner på levnr og spesifiserer ordrenr
      -- -- ||| THE ANSWER IS FOUND ||| -- --
      -- TODO WRONG & INEFFICIENT ANSWER


-- TODO totalpris (studass) ---- vent, hva skjer her nå?

      SELECT oh.ordrenr, dato, od.delnr, beskrivelse, kvantum, pris, (pris*kvantum) belop
        FROM ordrehode oh, ordredetalj od, levinfo l, delinfo d, prisinfo p
        WHERE oh.ordrenr = od.ordrenr AND l.levnr = oh.levnr AND d.delnr = od.delnr AND p.levnr = oh.levnr AND p.delnr = od.delnr
      AND oh.ordrenr = 18;

      -- hvorfor var levinfo med i det hele tatt?
      SELECT oh.ordrenr, dato, od.delnr, beskrivelse, kvantum, pris, (pris*kvantum) belop
        FROM ordrehode oh, ordredetalj od, delinfo d, prisinfo p
        WHERE oh.ordrenr = od.ordrenr AND d.delnr = od.delnr AND p.levnr = oh.levnr AND p.delnr = od.delnr
      AND oh.ordrenr = 18;

    -- eller som flere joins, bare etter hverandre...

      SELECT oh.ordrenr, dato, od.delnr, beskrivelse, kvantum, pris, (pris*kvantum) belop FROM ordrehode oh
        JOIN ordredetalj od ON oh.ordrenr = od.ordrenr
        JOIN delinfo d ON od.delnr = d.delnr
        JOIN prisinfo p ON od.delnr = p.delnr AND oh.levnr = p.levnr
      WHERE oh.ordrenr = 18;

    -- funker ekvivalent

    -- TODO totalisere prisen? hm? eller var det bare fordi det var så *masse*




-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
--  e. Finn delnummer og leverandørnummer for deler som har en pris som er høyere enn prisen for del med katalognr X7770.
-- -- -- -- ahem, katalognr er entydig her men det trenger ikke være det... Jaja.

      -- tar med pris som kontrollkolonne
      SELECT delnr, levnr, pris FROM prisinfo WHERE pris > (SELECT pris FROM prisinfo WHERE katalognr = 'X7770');
      -- -- ||| THE ANSWER IS FOUND ||| -- --
-- hva i helsike, denne var enkel


-- for å se hvilke som utelukkes
SELECT delnr, levnr, pris FROM prisinfo WHERE pris <= (SELECT pris FROM prisinfo WHERE katalognr = 'X7770');


-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
--  f.i) Tenk deg at tabellen levinfo skal deles i to. Sammenhengen mellom by og fylke skal tas ut av tabellen.
--  Det er unødvendig å lagre fylketilhørigheten for hver forekomst av by.
--  Lag én ny tabell som inneholder byer og fylker. Fyll denne med data fra levinfo.
--  Lag også en tabell som er lik levinfo unntatt kolonnen Fylke.
--  (Denne splittingen av tabellen levinfo gjelder bare i denne oppgaven.
--   I resten av oppgavesettet antar du at du har den opprinnelige levinfo-tabellen.)

-- drepe tabellene
DROP TABLE IF EXISTS levinfo_uten_fylke, byer; -- woo, måtte bytte om rekkefølgen også

      -- BY OG FYLKE:
      CREATE TABLE byer (
        levby   VARCHAR(20) NOT NULL,
        fylke   VARCHAR(20) NOT NULL,
        CONSTRAINT byer_pk PRIMARY KEY(levby) -- antar for latskapens skyld at bynavn er entydig nok (og det er det vel innenfor landegrensa)
      );

      INSERT INTO byer SELECT DISTINCT levby, fylke FROM levinfo; -- sette inn unike rade med levby og fylke

SELECT * FROM byer; -- viser at det er rett

      -- LEVINFO UTEN FYLKE:
      CREATE TABLE levinfo_uten_fylke( -- de leverer godt, selv uten fylke
        levnr   INTEGER,
        navn    VARCHAR(20) NOT NULL,
        adresse VARCHAR(20) NOT NULL,
        levby   VARCHAR(20) NOT NULL,
      -- fylke   VARCHAR(20) NOT NULL, -- as simple as purging this
        postnr  INTEGER NOT NULL,
        CONSTRAINT levinfo_pk PRIMARY KEY(levnr), -- TODO foreign key
        CONSTRAINT levinfo_fk FOREIGN KEY(levby) REFERENCES byer(levby)
      );

      INSERT INTO levinfo_uten_fylke SELECT levnr, navn, adresse, levby, postnr FROM levinfo; -- setja inn

SELECT * FROM levinfo_uten_fylke; -- sjå her du, det funke, gett

-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
--  f.ii) Lag en virtuell tabell (view) slik at brukerne i størst mulig grad kan jobbe på samme måte mot de to nye tabellene som den gamle.
--  Prøv ulike kommandoer mot tabellen (select, update, delete, insert).
--  Hvilke begrensninger, hvis noen, har brukerne i forhold til tidligere?

DROP VIEW IF EXISTS levinfo_erstatning;

      -- THE GLORIOUS VIEW
      CREATE VIEW levinfo_erstatning AS
        SELECT levnr, navn, adresse, l.levby, fylke, postnr FROM levinfo_uten_fylke l JOIN byer b ON l.levby = b.levby; -- carefully selected...
      -- -- ||| THE ANSWER IS FOUND ||| -- --


-- ET ALTERNATIV (veksla mellom dem for å teste kapabilitetene i setningsvirvaret nedenfor)
CREATE VIEW levinfo_erstatning AS
  SELECT levnr, navn, adresse, b.levby, fylke, postnr FROM levinfo_uten_fylke l JOIN byer b ON l.levby = b.levby;

    -- (endte tydeligvis opp med å foretrekke den andre (som jeg lagde sist) pga. at det var mulig å gjøre mer eller noe sånt, se nedenfor)

-- vise innholdet:
SELECT * FROM levinfo_erstatning;

-- burde en /*WITH CHECK OPTION*/ vært med? ville det gjort noen forskjell for det jeg gjør under?


-- SELECT-setningen fra c)
SELECT l.levnr, l.navn, p.pris FROM levinfo_erstatning l JOIN prisinfo p on l.levnr = p.levnr WHERE p.delnr = 201 ORDER BY p.pris ASC LIMIT 1;


-- et par UPDATE-setninger:
UPDATE levinfo_erstatning SET levby = 'Ål' WHERE levnr = 6;
-- kolonna levby i erstatninga *må* være fra levinfo_uten_fylke for at dette skal kunne funke, ellers klages det på at vi prøver å endre en primærnøkkel
UPDATE levinfo_erstatning SET levby = 'Ål', fylke = 'Telemark' WHERE levnr = 6;
-- failer (med levby fra byer) fordi den prøver å legge til verdiene på ei helt ny rad i byer-tabellen, som ikke er mulig siden primærnøkler må være unike

UPDATE levinfo_erstatning SET levnr = 33 WHERE levnr = 44; -- kan endre levinfo_uten_fylke sin primærnøkkel

UPDATE levinfo_erstatning SET adresse = 'Strandbuvegen 2' WHERE levby = 'Trondheim'; -- woo, potensielt flytte flere leverandører til samme adresse, skumle saker


-- et par DELETE-setninger:
DELETE FROM levinfo_erstatning WHERE levnr = 12; -- slette Mr. Office
DELETE FROM levinfo_erstatning WHERE fylke = 'Østfold'; -- slette de i Østfold
-- ingen av dem funker, kan ikke slette fra et joina view


-- INSERT:
INSERT INTO levinfo_erstatning(levnr, navn, adresse, levby, fylke, postnr) VALUES(13, 'Dødens togfabrikk', 'Krattskogveien 666', 'Styros', 'Helvete', 1337);
-- "can not modify more than one base table through a join view"

-- så, hvis vi bare modifiserer én om ganga:
INSERT INTO levinfo_erstatning(levby, fylke) VALUES('Styros', 'Helvete'); -- funker ikke gitt, samme feilmelding (prolly siden levby er fra levinfo_uten_fylke)
                                                                          -- funker om levby settes til å være fra byer-tabellen
INSERT INTO levinfo_erstatning(levnr, navn, adresse, levby, postnr) VALUES(13, 'Dødens togfabrikk', 'Krattskogveien 666', 'Styros', 1337); -- funker ikke med endringa ovenfor engang.

-- hva med en som bruker en by som allerede eksisterer? (med den vanlige utgaven av view-et
INSERT INTO levinfo_erstatning(levnr, navn, adresse, levby, postnr) VALUES(13, 'Dødens togfabrikk', 'Krattskogveien 666', 'Oslo', 1337);


-- konklusjon:
-- kan SELECTe helt fint
-- UPDATE kan ikke endre på tabellen med byer og fylker (men den kan til og med endre primærnøkkelen til levinfo_uten_fylke)
-- DELETE funker ikke, punktum
-- kan INSERTe verdier med en by som allerede fins, kan bare legge til ny by hvis levby er fra byer
-- - men den nye byen kan tydeligvis ikke brukes til å INSERTe en ny leverandør


-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
--  g. Anta at en vurderer å slette opplysningene om de leverandørene som ikke er representert i Prisinfo-tabellen.
--  Finn ut hvilke byer en i tilfelle ikke får leverandør i. (Du skal ikke utføre slettingen.) (Tips: Svaret skal bli kun én by, "Ål".)

-- de LEVERANDØRER som ikke er representert i PRISINFO
SELECT l.levnr, l.levby FROM levinfo l LEFT JOIN prisinfo p ON l.levnr = p.levnr WHERE p.delnr IS NULL; -- is ok

-- slått sammen med full levinfo på by, inner join:
SELECT DISTINCT l.levby, l.levnr 'the others', lp.levnr 'the joined' FROM levinfo l JOIN
  (SELECT l.levnr, l.levby FROM levinfo l LEFT JOIN prisinfo p ON l.levnr = p.levnr WHERE p.delnr IS NULL) lp
    ON l.levby = lp.levby;

-- Trenger da alle instanser der levby ikke er representert mer enn én gang.
-- Grupperer da på levnr i resultatet fra underspørringa (siden denne er representert mer enn én gang *og* er primærnøkkel i tabellen fra underspørringa)
-- og krever at antallet i gruppa *skal* være 1.
      SELECT DISTINCT l.levby, lp.levnr 'the joined' FROM levinfo l JOIN
        (SELECT l.levnr, l.levby FROM levinfo l LEFT JOIN prisinfo p ON l.levnr = p.levnr WHERE p.delnr IS NULL) lp
          ON l.levby = lp.levby GROUP BY lp.levnr HAVING COUNT(*) = 1;
      -- -- ||| THE ANSWER IS FOUND ||| -- --

      -- TODO NOT IN


-- (og ja, du var inne på rett spor der, slapp av)

-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
--  h. Finn leverandørnummer for den leverandør som kan levere ordre nr 18 til lavest totale beløp (vanskelig).
--  Hint: Løs oppgaven i tre steg:
--      Lag en virtuell tabell (view) som viser hvem som kan levere hele eller deler av ordren.
--      Fra denne velger du så ut de leverandørene som kan levere like mange deler til ordren som ordren krever. Det vil si, kan levere hele ordren.
--      Til slutt finner du ut hvem som leverer billigst.
--      Svaret skal bli at leverandør 6 kan levere ordren for 6798 kroner.

-- *windowflip*
DROP VIEW IF EXISTS ordre_atten;

-- først kjapt finne de delene ordren trenger
      -- ble til (første) VIEW:
      CREATE VIEW ordre_atten AS
        SELECT ordrenr, d.delnr, kvantum, levnr, pris, (kvantum*pris) kostnad_per_del
        FROM ordredetalj o JOIN prisinfo d ON o.delnr = d.delnr
        WHERE o.ordrenr = 18;
      -- -- ||| HERE IS THE INITIAL VIEW ||| -- --
-- har da sammenheng mellom ordrenr, delnr, levnr og pris.
-- dette er all infoen jeg trenger, jeg lar denne være view-et.
SELECT * FROM ordre_atten;

-- total kostnad beregnes slik:
SELECT SUM(kostnad_per_del) total_kostnad FROM ordre_atten GROUP BY levnr;
-- men er her uten skikkelig utvalgt leverandør

-- prøver å finne en COUNT
SELECT delnr, COUNT(delnr) FROM ordre_atten GROUP BY delnr; -- wrong thing, djeez
SELECT levnr, COUNT(*) FROM ordre_atten GROUP BY levnr; -- this one is useful, must be same as a *proper* part count

-- dette skal bli innledende utvelgelse av mulige leverandører
SELECT levnr FROM ordre_atten GROUP BY levnr HAVING COUNT(levnr) = 2;

-- må finne en COUNT basert på delnr som fungerer
SELECT COUNT(DISTINCT delnr) FROM ordre_atten GROUP BY delnr; -- NOPE eller vent - her har vi opptelt DISTINKTE delnr...
-- her har vi summen YESSS
SELECT SUM(the_count) ant_deler FROM
  (SELECT COUNT(DISTINCT delnr) the_count FROM ordre_atten GROUP BY delnr) thing GROUP BY the_count;


-- slengt inn:
SELECT levnr FROM ordre_atten GROUP BY levnr
  HAVING COUNT(levnr) = (SELECT SUM(the_count) ant_deler
                           FROM (SELECT COUNT(DISTINCT delnr) the_count FROM ordre_atten GROUP BY delnr) thing
                         GROUP BY the_count);
-- burde kunne forenkles somehow

-- svaret blir
SELECT levnr, SUM(kostnad_per_del) total_kostnad FROM ordre_atten GROUP BY levnr
HAVING COUNT(levnr) = (SELECT SUM(the_count) ant_deler
                       FROM (SELECT COUNT(DISTINCT delnr) the_count FROM ordre_atten GROUP BY delnr) thing
                       GROUP BY the_count)
ORDER BY total_kostnad ASC LIMIT 1;
-- (improve it plz)

DROP VIEW IF EXISTS deler_som_trengs;

      -- -- ||| HERE COMES THE ANSWERING ||| -- --
      -- lager et VIEW som viser en seriøst begrensa mengde verdier men gjør det hele mye mer leselig:
      CREATE VIEW deler_som_trengs AS
        SELECT COUNT(DISTINCT delnr) the_count, delnr FROM ordre_atten GROUP BY delnr;
      -- delnr tatt med for syns skyld

-- for demonstration purposes:
SELECT * FROM ordre_atten;
SELECT * FROM deler_som_trengs;
-- da har vi tingen

      -- ferdig summert pris + skikkelig utvelgelse
      SELECT levnr, SUM(kostnad_per_del) total_kostnad FROM ordre_atten GROUP BY levnr
        HAVING COUNT(levnr) = (SELECT SUM(the_count) FROM deler_som_trengs GROUP BY the_count)
        ORDER BY total_kostnad ASC LIMIT 1;
      -- -- ||| THE ANSWER IS FOUND ||| -- --



-- ET LITE INNSLAG AV HÅPLØS IDIOTI
-- ops, innså nettop at DISTINCT delnr strengt tatt ikke er nødvendig
-- siden vi kan COUNTe istedenfor å SUMmere viewet, da vil COUNT(*) inne i viewet være antall mulige leverandører av den spesifikke delen
-- og dermed ikke nødvendig å bruke...
CREATE VIEW deler_som_trengs AS
SELECT delnr, COUNT(*) ant_lev FROM ordre_atten GROUP BY delnr;
-- nei, vent, wtf, dette stemmer IKKE, stryk alt det der
-- funker ikke å bare COUNTe antall kolonner utenfra, yeeeeeah great

-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
-- ***Oppgave 2***
-- Bruk Bok-databasen fra Øving 6. Gå gjennom datasettet og finn ut hvor det ligger NULL-verdier.

--  a. Sett opp en SELECT-setning som er UNION mellom alle forlag med Oslo-nummer (telefonnummer begynner med 2) og alle som ikke er Oslo-nummer.
--  Får du med forlaget med NULL-verdi på telefonnummer? Hvis ikke, utvid unionen med en mengde til.

-- forlag med oslo-nummer
SELECT forlag_navn, telefon FROM forlag WHERE cast(telefon as UNSIGNED INTEGER) BETWEEN 20000000 AND 29999999;

-- forlag som ikke har oslo-nummer --> NOT BETWEEN, men også IS NULL som særtilfelle.
SELECT forlag_navn, telefon FROM forlag WHERE telefon IS NULL OR cast(telefon as UNSIGNED INTEGER) NOT BETWEEN 20000000 AND 29999999;

-- merk at BETWEEN *inkluderer* de oppgitte grensene
      -- full unionsetning
      SELECT forlag_navn, telefon FROM forlag WHERE cast(telefon as UNSIGNED INTEGER) BETWEEN 20000000 AND 29999999
        UNION
      SELECT forlag_navn, telefon FROM forlag WHERE telefon IS NULL OR cast(telefon as UNSIGNED INTEGER) NOT BETWEEN 20000000 AND 29999999;
      -- -- ||| THE ANSWER IS FOUND ||| -- --


-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
--  b. Sett opp SQL-setninger som finner gjennomsnittlig alder på forfattere der fødselsåret er oppgitt.
--  For forfattere der dødsåret ikke er oppgitt, skal du kun ta med de som er født etter 1900.
--  Tips for å få ut året i år:
--      MySQL: SELECT YEAR(CURRENT_DATE) FROM ... hvilken tabell som helst ...

-- fødselsår oppgitt
SELECT etternavn, fode_aar, dod_aar FROM forfatter WHERE fode_aar IS NOT NULL;

-- også dødsår oppgitt ELLER fødeår > 1900
SELECT etternavn, fode_aar, dod_aar FROM forfatter WHERE fode_aar IS NOT NULL AND (dod_aar IS NOT NULL OR fode_aar > 1900);

-- alder
SELECT etternavn, fode_aar, dod_aar, IF(dod_aar IS NULL, YEAR(CURRENT_DATE) - fode_aar, /*else*/ dod_aar - fode_aar) 'alder' FROM forfatter WHERE fode_aar IS NOT NULL AND (dod_aar IS NOT NULL OR fode_aar > 1900);

      -- gjennomsnittlig alder, testet med kalkulator
      SELECT AVG(   IF(dod_aar IS NULL, YEAR(CURRENT_DATE) - fode_aar, /*else*/ dod_aar - fode_aar)    ) 'gjennomsnittlig alder' FROM forfatter
        WHERE fode_aar IS NOT NULL AND (dod_aar IS NOT NULL OR fode_aar > 1900);
      -- -- ||| THE ANSWER IS FOUND ||| -- --


-- more bulky alternative, useful for multiple conditions I guess
SELECT AVG(CASE WHEN dod_aar IS NULL THEN (YEAR(CURRENT_DATE) - fode_aar)
                                 ELSE (dod_aar - fode_aar) END) 'gjennomsnittlig alder'
 FROM forfatter WHERE fode_aar IS NOT NULL AND (dod_aar IS NOT NULL OR fode_aar > 1900);


-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
--  c. Sett opp SQL-setninger som finner hvor stor andel av forfatterne som ble med i beregningene under b).

DROP VIEW IF EXISTS forfatter_alder;

      -- okei, lager et VIEW (for lesbarhetens skyld) som er oversikten over forfatterne og deres respektive beregnede aldre:
      CREATE VIEW forfatter_alder AS
        SELECT forfatter_id, fornavn, etternavn, fode_aar,
               IF(dod_aar IS NULL, 'ikke ennå', -- nicer to look at than <NULL>
                  /*else*/ dod_aar) AS dod_aar,
               IF(dod_aar IS NULL, YEAR(CURRENT_DATE) - fode_aar,
                  /*else*/ dod_aar - fode_aar) AS alder
        FROM forfatter WHERE fode_aar IS NOT NULL AND (dod_aar IS NOT NULL OR fode_aar > 1900);
      -- trenger ikke å ha flere kolonner enn forfatter_id her, men hvis jeg først skal lage et view vil jeg at det skal se pent ut

      CREATE VIEW forfatter_alder AS
        SELECT forfatter_id
        FROM forfatter WHERE fode_aar IS NOT NULL AND (dod_aar IS NOT NULL OR fode_aar > 1900);
      -- som et forenkla alternativ som *skal* fungere helt likt

-- vent, "som ble med i *beregningene*" --> øh, kun de som fikk sin gjennomsnittsalder beregna?
-- i så fall:
CREATE VIEW forfatter_alder AS
  SELECT forfatter_id
  FROM forfatter WHERE fode_aar IS NOT NULL AND dod_aar IS NOT NULL;
-- og svaret blir 2
-- men jeg tror ikke det er dette oppgava mener


-- for demonstrasjon:
SELECT * FROM forfatter_alder;

-- telle opp
SELECT COUNT(f.forfatter_id) FROM forfatter f JOIN forfatter_alder a ON f.forfatter_id = a.forfatter_id GROUP BY f.forfatter_id;

      -- summere
      SELECT CONCAT('forfattere som fikk sin alder beregnet: ', SUM(the_count)) AS svaret_altsaa FROM
        (SELECT COUNT(f.forfatter_id) AS the_count FROM forfatter f JOIN forfatter_alder a ON f.forfatter_id = a.forfatter_id GROUP BY f.forfatter_id) thing
      GROUP BY the_count;
      -- -- ||| THE ANSWER IS FOUND ||| -- --

-- TODO det var ANDEL (i prosent)

      SELECT CONCAT('forfattere som fikk sin alder beregnet: ', SUM(the_count),
        ', som prosentandel:', SUM(the_count) / SUM(SELECT COUNT( (SELECT COUNT(*) FROM forfatter GROUP BY forfatter_id) )) -- fuck it this not work whoopsie I need to drag it out somewhere and test
      ) AS svaret_altsaa FROM
        (SELECT COUNT(f.forfatter_id) AS the_count FROM forfatter f JOIN forfatter_alder a ON f.forfatter_id = a.forfatter_id GROUP BY f.forfatter_id) thing
        GROUP BY the_count;