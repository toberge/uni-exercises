SELECT * FROM boktittel;
SELECT * FROM eksemplar;
select forfatter, tittel from boktittel where isbn = '0-07-241163-5';
select count(*) antall from eksemplar where isbn = '0-07-241163-5';
update eksemplar set laant_av = 'Per Olsen' where isbn = '0-07-241163-5' and eks_nr = 1 and laant_av is null;
# CREATE TABLE person(
#                      persnr     INTEGER PRIMARY KEY,
#                      fornavn    VARCHAR(30) NOT NULL,
#                      etternavn VARCHAR(30) NOT NULL);
# INSERT INTO person VALUES (100, 'Ole', 'Hansen');
# INSERT INTO person VALUES (101, 'Anne Grethe', 'Ås');
# INSERT INTO person VALUES (102, 'Jonny', 'Hansen');


CREATE TABLE student (
  studnr INTEGER,
  fornavn VARCHAR(30) NOT NULL,
  etternavn VARCHAR(30) NOT NULL,

}