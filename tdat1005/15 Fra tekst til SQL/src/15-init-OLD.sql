
DROP TABLE IF EXISTS vikariat, oppdrag, bedrift, kval_per_kand, kvalifikasjon, kandidat;

CREATE TABLE kandidat(
  kand_nr INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  fornavn VARCHAR(30) NOT NULL,
  etternavn VARCHAR(30) NOT NULL,
  telefon CHAR(8),
  epost VARCHAR(30)
  -- CONSTRAINT kand_pk PRIMARY KEY(kand_nr)
);

CREATE TABLE bedrift(
   org_nr INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
   navn VARCHAR(30) NOT NULL,
   telefon CHAR(8),
   epost VARCHAR(30)
   -- CONSTRAINT bedr_pk PRIMARY KEY(org_nr)
);

CREATE TABLE kvalifikasjon(
  kval_nr INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  betegnelse VARCHAR(30) NOT NULL
);

CREATE TABLE kval_per_kand(
  kval_nr INTEGER NOT NULL,
  kand_nr INTEGER NOT NULL,
  CONSTRAINT kval_pk PRIMARY KEY(kval_nr, kand_nr),
  CONSTRAINT kval_fk FOREIGN KEY(kand_nr) REFERENCES kandidat(kand_nr)
);

-- HERE COMES THE NEW VERSION

CREATE TABLE oppdrag(
  oppdr_nr INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  startdato DATE NOT NULL,
  antatt_sluttdato DATE NOT NULL,
  org_nr INTEGER NOT NULL,
  krevd_kval INTEGER,
  CONSTRAINT oppdrag_fk1 FOREIGN KEY(org_nr) REFERENCES bedrift(org_nr),
  CONSTRAINT oppdrag_fk2 FOREIGN KEY(krevd_kval) REFERENCES kvalifikasjon(kval_nr)
);

CREATE TABLE vikariat(
  kand_nr INTEGER NOT NULL,
  oppdr_nr INTEGER NOT NULL,
--  krevd_kval INTEGER, -- POSSIBILITY
  sluttdato DATE /*NOT NULL*/,
  ant_timer INTEGER /*NOT NULL*/,
  CONSTRAINT vikariat_pk PRIMARY KEY(kand_nr, oppdr_nr),
  CONSTRAINT vikariat_fk1 FOREIGN KEY(kand_nr) REFERENCES kandidat(kand_nr),
  CONSTRAINT vikariat_fk2 FOREIGN KEY(oppdr_nr) REFERENCES oppdrag(oppdr_nr)
--  CONSTRAINT vikariat_fk3 FOREIGN KEY(krevd_kval, kand_nr) REFERENCES kval_per_kand(kval_nr, kand_nr)
);

-- Opprett databasetabellene med primær- og fremmednøkler. Bruk datatypen DATE for dato.

-- I oppgave d) skal du kjøre SELECT-setninger mot databasen.
-- Legg inn data som sikrer at du får prøvd ut disse setningene sånn noenlunde.
-- Det bør være nok med to-tre rader i hver tabell. Du vil trenge å oppgi datoer.
-- Måten du skriver dette på varierer fra databasesystem til databasesystem, men i MySQL kan du skrive, eksempel: DATE('2003-10-18').
-- Ang oppdrag: Legg inn noen oppdrag som **ikke er tildelt vikar**, og noen som er **avsluttet**.

INSERT INTO kandidat VALUES(DEFAULT, 'Leif Ole', 'Gundersen', '99999999', 'leif.ole.gu@nders.en');
INSERT INTO kandidat VALUES(DEFAULT, 'Åse Marie', 'Gundersen', '91911919', 'aase.marie.gu@nders.en'); -- fikk ikke oppdrag
INSERT INTO kandidat VALUES(DEFAULT, 'Barack', 'Obama', '11122111', 'bar@ack.ob');
INSERT INTO kandidat VALUES(DEFAULT, 'Doonaldo', 'Duké', '11300113', 'duke@duke.go'); -- ubrukelig, ukvalifisert kandidat

INSERT INTO bedrift VALUES(DEFAULT, 'E-Corp', '66600666', 'mail@evil.corp');
INSERT INTO bedrift VALUES(DEFAULT, 'Stavanger luft og vann', '00022000', 'post@stavluft.no');

INSERT INTO kvalifikasjon VALUES(DEFAULT, 'Storsmisking');
INSERT INTO kvalifikasjon VALUES(DEFAULT, 'Tidsfordriving');
INSERT INTO kvalifikasjon VALUES(DEFAULT, 'Kvalifisering');
INSERT INTO kvalifikasjon VALUES(DEFAULT, 'Uoppnåelighet'); -- ingen har denne

--                            kval kand
INSERT INTO kval_per_kand VALUES(1,3);
INSERT INTO kval_per_kand VALUES(2,1);
INSERT INTO kval_per_kand VALUES(2,3);
INSERT INTO kval_per_kand VALUES(3,1);
INSERT INTO kval_per_kand VALUES(3,2);

--                          nr        start       slutt   org kval
INSERT INTO oppdrag VALUES(DEFAULT, '22.07.13', '22.08.14', 1, 1); -- har ingen kandidat
INSERT INTO oppdrag VALUES(DEFAULT, '18.04.14', '18.08.03', 1, 2);
INSERT INTO oppdrag VALUES(DEFAULT, '18.09.12', '18.10.01', 2, 1);
INSERT INTO oppdrag VALUES(DEFAULT, '18.11.23', '19.01.11', 1, 3);
INSERT INTO oppdrag VALUES(DEFAULT, '18.10.13', '19.02.28', 2, NULL); -- no qualif and not done



-- denne skal FAILE helst
-- INSERT INTO vikariat VALUES(2, 2, '18.09.09',23);

INSERT INTO vikariat VALUES(3, 2, '18.07.01', 245);
INSERT INTO vikariat VALUES(1, 3, '18.10.04', 300);
INSERT INTO vikariat VALUES(1, 4, '19.01.12', 25);
INSERT INTO vikariat VALUES(4, 5, NULL, 130);



-- DEPRECATED LEFTOVERS
/*
-- TODO EITHER
CREATE TABLE kvalifikasjon(
  beskrivelse VARCHAR(30) NOT NULL,
  kand_nr INTEGER NOT NULL,
  CONSTRAINT kval_pk PRIMARY KEY(beskrivelse),
  CONSTRAINT kval_fk FOREIGN KEY(kand_nr) REFERENCES kandidat(kand_nr)
);

CREATE TABLE oppdrag(
  oppdragsnr INTEGER NOT NULL AUTO_INCREMENT,
  paakrevd_kval VARCHAR(30),
  CONSTRAINT oppdrag_pk PRIMARY KEY(oppdragsnr),
  CONSTRAINT oppdrag_fk FOREIGN KEY(paakrevd_kval) REFERENCES kvalifikasjon(betegnelse)
);

-- TODO OR
CREATE TABLE kvalifikasjon(
  kval_nr INTEGER NOT NULL AUTO_INCREMENT,
  beskrivelse VARCHAR(30) NOT NULL,
  kand_nr INTEGER NOT NULL,
  CONSTRAINT kval_pk PRIMARY KEY(kval_nr),
  CONSTRAINT kval_fk FOREIGN KEY(kand_nr) REFERENCES kandidat(kand_nr)
);

CREATE TABLE oppdrag(
  oppdragsnr INTEGER NOT NULL AUTO_INCREMENT,
  paakrevd_kval INTEGER,
  CONSTRAINT oppdrag_pk PRIMARY KEY(oppdragsnr),
  CONSTRAINT oppdrag_fk FOREIGN KEY(paakrevd_kval) REFERENCES kvalifikasjon(kval_nr)
);

-- TODO DECIDE THE REST FFS
CREATE TABLE vikariat(
  kand_nr INTEGER NOT NULL,
  org_nr INTEGER NOT NULL,
  oppdragsnr INTEGER NOT NULL,
  -- YEAAAAAH
)
*/