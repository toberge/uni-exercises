
DROP TABLE IF EXISTS oppdrag, bedrift, kval_per_kand, kvalifikasjon, kandidat;

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

-- HERE COMES THE NEW VERSION (2.0)

CREATE TABLE oppdrag(
  oppdr_nr INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  startdato DATE NOT NULL,
  antatt_sluttdato DATE NOT NULL,
  sluttdato DATE /*NOT NULL*/,
  ant_timer INTEGER /*NOT NULL*/,
  org_nr INTEGER NOT NULL,
  krevd_kval INTEGER,
  kand_nr INTEGER, -- HERE WE GOOOOOOOOOOOOOO
  CONSTRAINT oppdrag_fk1 FOREIGN KEY(org_nr) REFERENCES bedrift(org_nr),
  CONSTRAINT oppdrag_fk2 FOREIGN KEY(krevd_kval) REFERENCES kvalifikasjon(kval_nr),
  CONSTRAINT oppdrag_fk3 FOREIGN KEY(kand_nr) REFERENCES kandidat(kand_nr),
  CONSTRAINT oppdrag_fk4 FOREIGN KEY(krevd_kval, kand_nr) REFERENCES kval_per_kand(kval_nr, kand_nr) -- HERE IT IS WOO
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

--                           nr       start       antatt      faktisk  timer bedr kand kval
INSERT INTO oppdrag VALUES(DEFAULT, '22.07.13', '22.08.14',     NULL,   NULL, 1,    1, NULL); -- har ingen kandidat
INSERT INTO oppdrag VALUES(DEFAULT, '18.04.14', '18.08.03', '18.07.01', 245,  1,    2,    3);
INSERT INTO oppdrag VALUES(DEFAULT, '18.09.12', '18.10.01', '18.10.04', 300,  2,    2,    1);
INSERT INTO oppdrag VALUES(DEFAULT, '18.11.23', '19.01.11', '19.01.12',  25,  1,    3,    1);
INSERT INTO oppdrag VALUES(DEFAULT, '18.10.13', '19.02.28',     NULL,   130,  2, NULL,    4); -- no qualif and not done


