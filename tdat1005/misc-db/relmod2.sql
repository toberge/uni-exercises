-- I tilfelle vi må kjøre scriptet på nytt

DROP TABLE leveranse;
DROP TABLE leverandor;
DROP TABLE produkt;

-- To tabeller

CREATE TABLE leverandor(
  lev_nr INTEGER NOT NULL,
  lev_navn VARCHAR(15) NOT NULL,
  status INTEGER NOT NULL,
  lev_by  VARCHAR(15) NOT NULL,
  CONSTRAINT lev_pk PRIMARY KEY(lev_nr));

CREATE TABLE produkt(
  prod_nr INTEGER NOT NULL,
  prod_navn VARCHAR(10) NOT NULL,
  kode  VARCHAR(10) NOT NULL,
  vekt INTEGER NOT NULL,
  prod_by VARCHAR(15),
  CONSTRAINT produkt_pk PRIMARY KEY(prod_nr));

-- Denne tabellen uttrykker en mange-til-mange-kopling mellom de to forrige tabellene

CREATE TABLE leveranse(
  lev_nr INTEGER NOT NULL,
  prod_nr INTEGER NOT NULL,
  antall INTEGER NOT NULL,
  CONSTRAINT leveranse_pk PRIMARY KEY(lev_nr, prod_nr));

-- Fremmednøkler

ALTER TABLE leveranse
  ADD CONSTRAINT leveranse_fk1 FOREIGN KEY(lev_nr)
  REFERENCES leverandor(lev_nr);

ALTER TABLE leveranse
  ADD CONSTRAINT leveranse_fk2 FOREIGN KEY(prod_nr)
  REFERENCES produkt(prod_nr);


-- Legger inn eksempeldata
INSERT INTO leverandor(lev_nr, lev_navn, status, lev_by)  VALUES(1, 'Svendsen', 20, 'Lillehammer');
INSERT INTO leverandor(lev_nr, lev_navn, status, lev_by)  VALUES(2, 'Jensen', 10, 'Porsgrunn');
INSERT INTO leverandor(lev_nr, lev_navn, status, lev_by)  VALUES(3, 'Bø', 30, 'Porsgrunn');
INSERT INTO leverandor(lev_nr, lev_navn, status, lev_by)  VALUES(4, 'Christiansen', 20, 'Lillehammer');
INSERT INTO leverandor(lev_nr, lev_navn, status, lev_by)  VALUES(5, 'Andersen', 30, 'Arendal');

INSERT INTO produkt(prod_nr, prod_navn, kode, vekt, prod_by) VALUES(1, 'synåler', 'rød', 12, 'Lillehammer');
INSERT INTO produkt(prod_nr, prod_navn, kode, vekt, prod_by) VALUES(2, 'binders', 'grønn', 17, 'Porsgrunn');
INSERT INTO produkt(prod_nr, prod_navn, kode, vekt, prod_by) VALUES(3, 'skruer', 'blå', 17, 'Risør');
INSERT INTO produkt(prod_nr, prod_navn, kode, vekt, prod_by) VALUES(4, 'skruer', 'rød', 14, 'Lillehammer');
INSERT INTO produkt(prod_nr, prod_navn, kode, vekt, prod_by) VALUES(5, 'knapper', 'blå', 12, 'Porsgrunn');
INSERT INTO produkt(prod_nr, prod_navn, kode, vekt, prod_by) VALUES(6, 'spiker', 'rød', 19, 'Lillehammer');

INSERT INTO leveranse(lev_nr, prod_nr, antall) VALUES(1, 1, 300);
INSERT INTO leveranse(lev_nr, prod_nr, antall) VALUES(1, 2, 200);
INSERT INTO leveranse(lev_nr, prod_nr, antall) VALUES(1, 3, 400);
INSERT INTO leveranse(lev_nr, prod_nr, antall) VALUES(1, 4, 200);
INSERT INTO leveranse(lev_nr, prod_nr, antall) VALUES(1, 5, 100);
INSERT INTO leveranse(lev_nr, prod_nr, antall) VALUES(2, 1, 300);
INSERT INTO leveranse(lev_nr, prod_nr, antall) VALUES(2, 2, 400);
INSERT INTO leveranse(lev_nr, prod_nr, antall) VALUES(3, 2, 200);
INSERT INTO leveranse(lev_nr, prod_nr, antall) VALUES(4, 2, 200);
INSERT INTO leveranse(lev_nr, prod_nr, antall) VALUES(4, 4, 300);
INSERT INTO leveranse(lev_nr, prod_nr, antall) VALUES(4, 5, 400);


