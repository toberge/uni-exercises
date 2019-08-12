
-- Oppgave d)

-- Sett opp SELECT-setninger som gjør følgende:

-- 1. Lag en liste over alle bedriftene. Navn, telefon og epost til bedriften skal skrives ut.

SELECT navn, telefon, epost FROM bedrift;

-- 2. Lag en liste over alle oppdragene.
--    Om hvert oppdrag skal du skrive ut oppdragets nummer samt navn og telefonnummer til bedriften som tilbyr oppdraget.

SELECT oppdr_nr, navn, telefon FROM oppdrag o, bedrift b WHERE o.org_nr = b.org_nr ORDER BY oppdr_nr ASC;

-- 3. Lag en liste over kandidater og kvalifikasjoner.
--    Kandidatnavn og kvalifikasjonsbeskrivelse skal med i utskriften i tillegg til løpenumrene som identifiserer kandidat og kvalifikasjon.

SELECT ka.kand_nr, fornavn, etternavn, kv.kval_nr, betegnelse
  FROM kandidat ka, kvalifikasjon kv, kval_per_kand kk
  WHERE kk.kand_nr = ka.kand_nr AND kk.kval_nr = kv.kval_nr;

-- 4. Som oppgave 3), men få med de kandidatene som ikke er registrert med kvalifikasjoner.

-- må bruke en left/right outer join, spesifikt
SELECT ka.kand_nr, fornavn, etternavn, kk.kval_nr, betegnelse
  FROM kandidat ka LEFT JOIN
    (SELECT kk.kand_nr, kk.kval_nr, kv.betegnelse FROM kval_per_kand kk, kvalifikasjon kv WHERE kv.kval_nr=kk.kval_nr) kk
  ON kk.kand_nr = ka.kand_nr;

-- 5. Skriv ut jobbhistorikken til en bestemt vikar, gitt kandidatnr. Vikarnavn, sluttdato, oppdragsnr og bedriftsnavn skal med.

SELECT k.fornavn, k.etternavn, sluttdato, o.oppdr_nr, b.navn
  FROM kandidat k, oppdrag o, bedrift b
  WHERE k.kand_nr = o.kand_nr AND o.org_nr = b.org_nr AND k.kand_nr = 1
  ORDER BY sluttdato ASC;


