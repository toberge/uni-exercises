-- drop if already there
DROP TABLE leilighet;
DROP TABLE andelseier;
DROP TABLE bygning;
DROP TABLE borettslag;

-- create tables
CREATE TABLE borettslag(
  bor_id INTEGER NOT NULL, -- possibly excessive caution, but an ID is easier to deal with than an address - except for remembering it, and that could be a valid point.
  navn VARCHAR(30) NOT NULL,
  adr VARCHAR(30) NOT NULL, -- kunne fungert som primærnøkkel
  ant_bygn INTEGER NOT NULL,
  etabl_aar INTEGER NOT NULL,
  CONSTRAINT bor_pk PRIMARY KEY(bor_id)
);

CREATE TABLE bygning(
  bygn_id INTEGER NOT NULL,
  adr VARCHAR(30) NOT NULL,
  ant_etg INTEGER NOT NULL,
  ant_leil INTEGER NOT NULL,
  bor_id INTEGER NOT NULL,
  CONSTRAINT bygn_pk PRIMARY KEY(bygn_id)
);

CREATE TABLE leilighet(
--  leil_id INTEGER NOT NULL, yeeeeah
  bygn_id INTEGER NOT NULL,
  leil_nr INTEGER NOT NULL,
  etg INTEGER NOT NULL,
  ant_rom INTEGER NOT NULL,
  areal INTEGER NOT NULL,
  eier_id INTEGER UNIQUE, -- kan være NULL men en eier skal IKKE kunne eie mer enn én leilighet TODO markerer svar på e) --> se nedenfor også
  CONSTRAINT leil_pk PRIMARY KEY(bygn_id, leil_nr) -- TBD
  -- TODO: how 2 check the count is off or not
);

CREATE TABLE andelseier(
  eier_id INTEGER NOT NULL,
  navn VARCHAR(30) NOT NULL,
  bor_id INTEGER NOT NULL,
--  leil_id INTEGER NOT NULL, -- ops, gikk ikke for å ha en leil_id nei...
-- trenger overhodet ikke la denne peke til noe annet enn borettslag tho
  CONSTRAINT eier_pk PRIMARY KEY(eier_id)
);

-- add foreign key
ALTER TABLE bygning
  ADD CONSTRAINT bygn_fk FOREIGN KEY(bor_id)
  REFERENCES borettslag(bor_id) ON UPDATE CASCADE; -- diskutabelt, opprinnelig DELETE

ALTER TABLE leilighet
  ADD CONSTRAINT leil_fk1 FOREIGN KEY(bygn_id)
  REFERENCES bygning(bygn_id) ON DELETE CASCADE; -- cannot exist without building

ALTER TABLE leilighet
  ADD CONSTRAINT leil_fk2 FOREIGN KEY(eier_id)
  REFERENCES andelseier(eier_id); -- can exist without owner

ALTER TABLE andelseier
  ADD CONSTRAINT eier_fk FOREIGN KEY(bor_id)
  REFERENCES borettslag(bor_id) ON UPDATE CASCADE; -- idk is still a person, opprinnelig DELETE

-- fill with data
INSERT INTO borettslag(bor_id, navn, adr, ant_bygn, etabl_aar) VALUES(113,'Overbygget', 'Ruslevegen 3', 3, 1953);

INSERT INTO bygning(bygn_id, adr, ant_etg, ant_leil, bor_id) VALUES(1131, 'Ruslevegen 3', 4, 10, 113);
INSERT INTO bygning(bygn_id, adr, ant_etg, ant_leil, bor_id) VALUES(1132, 'Røstbakklia 58', 1, 2, 113);
INSERT INTO bygning(bygn_id, adr, ant_etg, ant_leil, bor_id) VALUES(1133, 'Ruslevegen 4', 2, 5, 113);

-- eiere
INSERT INTO andelseier(eier_id, navn, bor_id) VALUES (2112, 'Grøftegraver Gundersen', 113);
INSERT INTO andelseier(eier_id, navn, bor_id) VALUES (1337, 'Oddvar Brå', 113);
INSERT INTO andelseier(eier_id, navn, bor_id) VALUES (1234, 'Gustne Gustav', 113);
-- INSERT INTO andelseier(eier_id, navn, bor_id) VALUES (5678, 'Grethe Støvtak', 999); will fail
-- også brudd på referanseintegritet

-- bygning 1
INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1131, 1, 1, 2, 15, NULL);
INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1131, 2, 1, 2, 15, NULL);
INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1131, 3, 1, 2, 17, 1337);
INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1131, 4, 2, 5, 60, NULL);
INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1131, 5, 2, 3, 40, NULL);
INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1131, 6, 3, 4, 50, NULL);
INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1131, 7, 3, 4, 50, NULL);
INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1131, 8, 4, 2, 17, NULL);
INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1131, 9, 4, 2, 15, NULL);
INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1131, 10, 4, 2, 15, NULL);

-- bygning 2
INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1132, 1, 1, 5, 75, 1234);

-- bygning 3
INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1133, 1, 1, 3, 60, 2112);
INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1133, 2, 1, 3, 60, NULL);
INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1133, 3, 2, 3, 50, NULL);
INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1133, 4, 2, 2, 30, NULL);
INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1133, 5, 2, 2, 40, NULL); -- setting 2112 here fails, as intended
-- INSERT INTO leilighet(bygn_id, leil_nr, etg, ant_rom, areal, eier_id) VALUES(1133, 5, 2, 2, 40, 2112); -- you see? uncomment this for demonstration
-- brudd på referanseintegritet

UPDATE leilighet SET eier_id=2112 WHERE bygn_id = 11133 AND leil_nr = 5; -- doesn't work of course
-- SELECT * FROM leilighet WHERE bygn_id = 1133 AND leil_nr = 5;


/*
-- e. Fra forelesningene har vi at en fremmednøkkel kan være NULL.
--    Er det fornuftig å tillate dette for de fremmednøklene du har kommet fram til her?
--> Ja, oppgaveteksten legger spesielt opp til at eier_id (eller en leil_id avhengig av hvilken vei det refereres) skal ha mulighet til å være NULL.

-- Kan primærnøkler være NULL?
--> Absolutt ikke - brudd på entitetsintegriteten og generell sunn fornuft. Du bør virkelig

-- f. Når vi skal implementere referanseintegriteten (fremmednøkkel) er det mulig å sette ON DELETE/UPDATE CASCADE (se under for nærmere forklaring).
--    Vurder konsekvensen av dette for hver enkelt fremmednøkkel.
--> eier_id skal IKKE ha noen innvirkning på tuplene i leilighet-relasjonen
--> men hvis borettslaget slettes må bygningene og leilighetene slettes - eller flyttes til et annet borettslag? Bygningene står der fortsatt...
---> om en bygning rives er det mer klart hva som må skje - DELETE THEM APARTMENTS
--> eierne selv bør kunne følge leiligheter inn i nye borettslag - but how exactly

-- hvorvidt
*/