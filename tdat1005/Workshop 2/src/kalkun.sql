-- Forelesning 4. februar

-- VIEW: Ikke så meget på eksamen, det står i oppgaveteksten
-- for komplekse setninger, kjøre flere SELECT etter hverandre
-- eller begrense hva som skal vises (men eh, er ikke det det samme?)
CREATE VIEW daarlige_leverandorer AS SELECT lev_nr, lev_navn, status FROM leverandor WHERE status < 30;
SELECT * FROM daarlige_leverandorer;
-- insert, update og delete gir ikke mening mot et view

-- WE KNOW THAT BY NOW JESUS CHRIST

-- NULL-VERDIER: Unngå NULL ved f.eks. DEFAULT <default_value> i CREATE-setninger der NOT NULL ikke passer
-- '' blir NULL i Oracle-databaser, ' ' istedenfor
-- COUNT(attributt) teller ikke med null-verdier, COUNT(*) tar dem med.
SELECT COUNT(lev_by) FROM lev_med_null; -- leverandører med by, uten de med NULL-verdier i bystaffasjen
SELECT lev_by, COUNT(*) antall FROM lev_med_null WHERE lev_by IS NOT NULL GROUP BY lev_by;

-- UPDATE:
UPDATE produkt SET kode='gul', vekt='3' WHERE prod_nr=2; -- ingen angretast
UPDATE produkt SET kode='gul'; -- will do just that, for ALL THEM SWEET PRECIOUS TUPLES OMG
-- må anta at referanseintegriteten er satt (kan være slått av i MySQL omg how naughty)

-- teste fremmednøkkel først med ALTER TABLE etter CREATE vs bare legge den direkte inn i CREATE

-- bruke INSERT for å flytte over data for så å bruke DELETE for å slette fra den gamle

--kan ha liste med mulige statusverdier

-- istedenfor å slette kan vi ha en dummy-kunde/whatever

-- wooow jeg har FULGT MED OH SHIT NOPE
-- fantastisk forelesning gitt
-- jaaa lærern bedriver copy-paste