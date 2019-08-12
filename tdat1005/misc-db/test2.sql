CREATE TABLE antatt_ansatt (
  ans_id INTEGER NOT NULL AUTO_INCREMENT,
  fornavn VARCHAR(30) NOT NULL,
  etternavn VARCHAR(30) NOT NULL,
  tlf CHAR(15),
  epost VARCHAR(30),
  har_som_sjef_ans_id INTEGER,
  CONSTRAINT atat_pk PRIMARY KEY(ans_id),
  CONSTRAINT atat_fk FOREIGN KEY(har_som_sjef_ans_id) REFERENCES antatt_ansatt(ans_id)
);