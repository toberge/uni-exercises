DROP TABLE prosjektarbeid;
DROP TABLE prosjektkostnader;
DROP TABLE prosjekt;
DROP TABLE ansatt;

CREATE TABLE ansatt(
  ans_id INTEGER NOT NULL,
  fornavn VARCHAR(30) NOT NULL,
  etternavn VARCHAR(30) NOT NULL,
  timelønn REAL,
CONSTRAINT ansatt_pk PRIMARY KEY(ans_id));

CREATE TABLE prosjekt(
  prosj_id INTEGER NOT NULL,
  prosj_tittel VARCHAR(30) NOT NULL UNIQUE,
  kunde VARCHAR(30) NOT NULL,
  budsjett REAL,
  timefaktor REAL DEFAULT 2.0,
  leder_ans_id INTEGER NOT NULL,
CONSTRAINT prosjekt_pk PRIMARY KEY(prosj_id));

CREATE TABLE prosjektkostnader(
  prosj_kost_id INTEGER NOT NULL,
  dato DATE NOT NULL,
  beløp REAL NOT NULL,
  tekst VARCHAR(30) NOT NULL,
  faktura_sendt DATE,
  prosj_id INTEGER NOT NULL,
CONSTRAINT prosj_kost_pk PRIMARY KEY(prosj_kost_id));
  
CREATE TABLE prosjektarbeid(
  ans_id INTEGER NOT NULL,
  prosj_id INTEGER NOT NULL,
  dato DATE NOT NULL,
  ant_timer REAL NOT NULL,
  faktura_sendt DATE,
CONSTRAINT prosj_arb_pk PRIMARY KEY(ans_id, prosj_id, dato));

ALTER TABLE prosjekt
  ADD CONSTRAINT prosjekt_fk FOREIGN KEY(leder_ans_id) REFERENCES ansatt(ans_id);
  
ALTER TABLE prosjektkostnader
  ADD CONSTRAINT prosj_kost_fk FOREIGN KEY(prosj_id) REFERENCES prosjekt(prosj_id);

ALTER TABLE prosjektarbeid
  ADD CONSTRAINT prosj_arb_fk1 FOREIGN KEY(ans_id) REFERENCES ansatt(ans_id);
  
ALTER TABLE prosjektarbeid
  ADD CONSTRAINT prosj_arb_fk2 FOREIGN KEY(prosj_id) REFERENCES prosjekt(prosj_id);

  
INSERT INTO ansatt(ans_id,fornavn,etternavn,timelønn) VALUES(1001, 'Ole', 'Hansen', 330);
INSERT INTO ansatt(ans_id,fornavn,etternavn,timelønn) VALUES(1002, 'Anne', 'Vik', 320);
INSERT INTO ansatt(ans_id,fornavn,etternavn,timelønn) VALUES(1003, 'Eva', 'Jensen', 400);
INSERT INTO ansatt(ans_id,fornavn,etternavn,timelønn) VALUES(1004, 'Jens', 'Ås', 370);

INSERT INTO prosjekt(prosj_id,prosj_tittel,kunde,budsjett,timefaktor,leder_ans_id)
VALUES(101, 'Frukt og grønt i massevis', 'Haloween', 430000, 1.7, 1004);
INSERT INTO prosjekt(prosj_id,prosj_tittel,kunde,budsjett,timefaktor,leder_ans_id)
VALUES(102, 'Farsdagsmoro', 'Barnehagene AS', 355000, 1.3, 1004);

INSERT INTO prosjektkostnader(prosj_kost_id,dato,beløp,tekst,faktura_sendt,prosj_id)
VALUES(1, '2008-10-22', 16000, 'Masker og rart, sending 2', NULL, 101);
INSERT INTO prosjektkostnader(prosj_kost_id,dato,beløp,tekst,faktura_sendt,prosj_id)
VALUES(2, '2008-09-21', 10000, 'Masker og rart, sending 1', '1008-09-30', 101);
INSERT INTO prosjektkostnader(prosj_kost_id,dato,beløp,tekst,faktura_sendt,prosj_id)
VALUES(3, '2008-10-17', 10000, 'Gresskar', NULL, 101);

INSERT INTO prosjektarbeid(ans_id, prosj_id, dato, ant_timer, faktura_sendt)
VALUES(1002, 101, '2008-10-15', 5, NULL);
INSERT INTO prosjektarbeid(ans_id, prosj_id, dato, ant_timer, faktura_sendt)
VALUES(1002, 101, '2008-10-16', 7, NULL);
INSERT INTO prosjektarbeid(ans_id, prosj_id, dato, ant_timer, faktura_sendt)
VALUES(1003, 101, '2008-10-15', 8, NULL);








  
  