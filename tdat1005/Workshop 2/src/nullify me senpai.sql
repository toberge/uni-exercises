DROP TABLE lev_med_null;

CREATE TABLE lev_med_null(
  lev_nr INTEGER,
  lev_navn VARCHAR(15) NOT NULL,
  status INTEGER,
  lev_by  VARCHAR(15),
  CONSTRAINT lev_med_null_pk PRIMARY KEY(lev_nr));

INSERT INTO lev_med_null
  SELECT * FROM leverandor;

INSERT INTO lev_med_null(lev_nr, lev_navn, status, lev_by)
  VALUES(6, 'Jensen', NULL, NULL);

INSERT INTO lev_med_null(lev_nr, lev_navn, status, lev_by)
  VALUES(7, 'Hansen', 40, NULL);

UPDATE lev_med_null
  SET status = NULL, lev_by = NULL where lev_nr = 1;