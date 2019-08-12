/*
-- OPPGAVE 1 --

a)  Lag en SQL-spørring som utfører operasjonene seleksjon og projeksjon på tabellen Bok. */

-- Seleksjon: (velge rader/tupler)
SELECT * FROM bok WHERE 1;
SELECT * FROM bok WHERE utgitt_aar<1980;

-- Projeksjon: (velge kolonner/attributter)
SELECT DISTINCT tittel, utgitt_aar FROM bok WHERE 1;
-- uten DISTINCT kan vi få identiske rader NB

-- Seleksjon og projeksjon:
SELECT DISTINCT tittel, utgitt_aar FROM bok WHERE utgitt_aar<1980;

/*
b)  Lag en SQL-spørring som utfører operasjonen produkt på tabellene Forlag og Bok.
    Beskriv resultatet med egne ord. */

-- Produkt: (sammensmeltning av alle mulige kombinasjoner)
SELECT * FROM forlag, bok;
-- gir meningsløs oversikt over alle bøker sammenkoblet med alle mulige forlag, som om de *blir* utgitt av alle forlag. vrøvl.
-- trenger en begrensning (forening) for å få noe bedre

/*
c)  Lag SQL-spørringer som utfører operasjonene likhetsforening (equijoin) og naturlig forening (natural join) på tabellene Forlag og Bok.
    Hva forteller resultatet? */

-- equijoin: (joine på likhet)
SELECT * FROM forlag, bok WHERE bok.forlag_id = forlag.forlag_id;
-- gir en oversikt over bøker utgitt av samme forlag - eller hvilke bøker hvert forlag har utgitt, alt ettersom.

-- natural join: (likhet uten duplikater) (basically projisere vekk de attributtene som er representert flere ganger)
SELECT forlag.*, bok.bok_id, bok.tittel, bok.utgitt_aar FROM forlag, bok WHERE bok.forlag_id = forlag.forlag_id;
SELECT forlag.*, bok.bok_id, bok.tittel, bok.utgitt_aar FROM forlag JOIN bok ON(bok.forlag_id = forlag.forlag_id);
-- gir samme oversikt, bare mer konsist og (ahem) oversiktlig.

-- extra
SELECT forlag.*, bok.bok_id, bok.tittel, bok.utgitt_aar FROM forlag LEFT OUTER JOIN bok ON(bok.forlag_id = forlag.forlag_id);
SELECT forlag.*, bok.bok_id, bok.tittel, bok.utgitt_aar FROM forlag RIGHT OUTER JOIN bok ON(bok.forlag_id = forlag.forlag_id);
-- and we haz no FULL OUTER JOIN :sadface:

/*
d)  Finn eksempler på attributter eller kombinasjoner av attributter som er unionkompatible.
    Hvilke relasjonsoperasjoner krever at operandene er unionkompatible?
    Sett opp SQL-spørringer som utfører disse operasjonene, et eksempel på hver. Beskriv med egne ord hva spørringene gir deg svaret på. */

-- unionkompatible attributter:

-- simpelthen samme datatype:
-- tittel, fornavn (mindre), etternavn, nasjonalitet (mindre), forlag_navn og adresse er alle strenger (telefon i tillegg, men den er av konstant lengde)
-- utgitt_aar, fode_aar, dod_aar er alle integere (samme med id-ene)
-- id-ene (som er fremmed- og primærnøkler) vil gjerne ha en sammenheng

-- faktisk samsvarende domene IRL:
-- good spm yeees
-- utgivelses-, fødsels- og dødsår er årstall - men har ikke direkte sammenheng
-- id-ene er surrogatnøkler, de samsvarer parvis og er de MEST MENINGSFYLTE atributtene vi kan velge her
-- og mesteparten av strengene *kan* være tilsvarende på en eller annen måte

-- in conclusion: De nyttige er parene bok_id, forfatter_id og forlag_id i sine respektive tabeller.
-- Det er *de* vi faktisk kan bruke til mer nyttige ting med disse operatorene


-- hvilke operasjoner krever dette: union, snitt, differanse

-- Union: (smelter sammen begge + fjerner duplikater ifølge boka --> DISTINCT nødvendig?)
SELECT bok_id id FROM bok
  UNION
SELECT bok_id FROM bok_forfatter;
-- gir bare... de instansene vi har av IDer for bøker, uavhengig av om de har en registrert forfatter eller ei

-- Snitt: (kun de like tuplene)
SELECT DISTINCT dod_aar FROM forfatter JOIN bok ON(dod_aar = utgitt_aar); -- gir ikke mening, unnskyld meg
SELECT DISTINCT bok.bok_id FROM bok JOIN bok_forfatter ON bok.bok_id = bok_forfatter.bok_id;
-- samme bok-id... gir mengden bøker som faktisk er knytta til forfatter
SELECT DISTINCT tittel FROM bok JOIN forlag ON tittel = forlag_navn;
-- hadde gitt eventuelle bøker med samme tittel som navnet på et forlag
SELECT DISTINCT forlag.forlag_id, forlag_navn FROM bok JOIN forlag on bok.forlag_id = forlag.forlag_id;
-- gir ID-ene og navnene til forlagene som har utgitt bøker

-- Differanse: (tar vekk tuplene som fins i begge og etterlater en strippet versjon av den til venstre)
SELECT tittel FROM bok LEFT JOIN forlag ON tittel = forlag_navn WHERE forlag_navn IS NULL;
-- spesifikt right join uten outer, okei, i see what you did there
-- gir alle boktitler som ikke er det samme som navnet på et forlag

SELECT bok.bok_id FROM bok LEFT JOIN bok_forfatter ON bok.bok_id = bok_forfatter.bok_id WHERE bok_forfatter.bok_id IS NULL;
-- ID-en til alle bøker som er uten forfattere - INGEN DER HELLER NEI

-- faktisk givende eksempel i 2b

-- --|| OPPGAVE 2 ||-- --

/*
a)  Bruk SQL til å finne navnene til alle forlagene. Hvilken eller hvilke operasjoner fra relasjonsalgebraen brukte du? */

SELECT forlag_navn FROM forlag; -- just like that
-- projeksjon, simpelthen

/*
b)  Bruk SQL til å finne eventuelle forlag (forlag_id er nok) som ikke har gitt ut bøker.
    Hvilken eller hvilke operasjoner fra relasjonsalgebraen brukte du? */

-- DIFFERANSE tipper jeg
SELECT forlag.forlag_id, forlag_navn FROM forlag LEFT JOIN bok ON forlag.forlag_id = bok.forlag_id WHERE bok.forlag_id IS NULL; -- is null in joined table
-- dvs differanse men egentlig en left join med seleksjon og en avsluttende projeksjon

/*
c)  Bruk SQL til å finne forfattere som er født i 1948. Hvilken eller hvilke operasjoner fra relasjonsalgebraen brukte du? */

-- hey dat is selection
SELECT * FROM forfatter WHERE fode_aar=1948;

-- with sensible projection
SELECT fornavn, etternavn, dod_aar, nasjonalitet FROM forfatter WHERE fode_aar=1948;

/*
d)  Bruk SQL til å finne navn og adresse til forlaget som har gitt ut boka 'Generation X'.
    Hvilken eller hvilke operasjoner fra relasjonsalgebraen brukte du? */

-- seleksjon for å finne boka
-- så natural join på id-en + projeksjon til ønskede attributter i forlag

SELECT forlag_navn, adresse FROM forlag WHERE forlag_id=(SELECT forlag_id FROM bok WHERE tittel='Generation X');
-- korrekt syntaks? PROBLEMATISK MED MER ENN 1 FRA SELECT-EN I PARENTES men funker TILFELDIGVIS her

SELECT forlag_navn, adresse FROM forlag JOIN bok ON(bok.tittel = 'Generation X' AND bok.forlag_id = forlag.forlag_id);
-- hm hm hm. langt bedre.

-- THEY BOTH WORK OMG I'M PROWD

/*
e)  Bruk SQL til å finne titlene på bøkene som Hamsun har skrevet. Hvilken eller hvilke operasjoner fra relasjonsalgebraen brukte du? */

-- seleksjon for å finne Hamsun, joine med sammenhengstabellen bok_forfatter på forfatter_id, joine med bok på bok_id, projisere ut titlene.
-- direkte ut fra dette:
SELECT tittel FROM bok JOIN
  (bok_forfatter JOIN forfatter ON(forfatter.etternavn = 'Hamsun' AND bok_forfatter.forfatter_id = forfatter.forfatter_id))
  ON bok.bok_id = bok_forfatter.bok_id;

-- mer lettvin måte:
SELECT tittel FROM bok, bok_forfatter, forfatter WHERE
  forfatter.etternavn = 'Hamsun' AND bok_forfatter.forfatter_id = forfatter.forfatter_id AND bok.bok_id = bok_forfatter.bok_id;

-- DUMT FORSØK - vend dine øyne vekk
-- SELECT tittel FROM bok WHERE bok.bok_id=(SELECT bok_forfatter.bok_id FROM bok_forfatter WHERE forfatter_id=(SELECT forfatter.forfatter_id FROM forfatter WHERE etternavn='Hamsun'));
-- subquery returns more than 1 row --> exactly. you moron.

/*
f)  Bruk SQL til å finne informasjon om bøker og forlagene som har utgitt dem.
    Én linje i oversikten skal inneholde bokas tittel og utgivelsesår, samt forlagets navn, adresse og telefonnummer.
    Forlag som ikke har gitt ut noen bøker skal også med i listen. Hvilken eller hvilke operasjoner fra relasjonsalgebraen brukte du? */

-- jeg syns jeg kan lukte en [lEFT/RIGHT] OUTER JOIN her?
SELECT tittel, utgitt_aar, forlag_navn, adresse, telefon FROM forlag f LEFT OUTER JOIN bok b ON f.forlag_id = b.forlag_id;
-- operasjoner: left outer join + projeksjon