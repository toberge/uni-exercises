DROP TABLE goal;
DROP TABLE game;
DROP TABLE eteam;


CREATE TABLE eteam(
   id VARCHAR (3) NOT NULL,
   teamname VARCHAR(30),
   coach VARCHAR(60),
   PRIMARY KEY (id)
   );

CREATE TABLE game(
   id DECIMAL (4) NOT NULL,
   mdate VARCHAR(20),
   stadium VARCHAR(60),
   team1 VARCHAR(3),
   team2 VARCHAR(3),
   PRIMARY KEY (id),
   FOREIGN KEY(team1) REFERENCES eteam(id),
   FOREIGN KEY(team2) REFERENCES eteam(id)
   );

CREATE TABLE goal(
   matchid DECIMAL (4) NOT NULL,
   teamid VARCHAR (3) NOT NULL,
   player VARCHAR(40),
   gtime INTEGER NOT NULL,
   PRIMARY KEY (matchid, teamid, gtime),
   FOREIGN KEY(matchid) REFERENCES game(id),
   FOREIGN KEY(teamid) REFERENCES eteam(id)
   );



INSERT INTO eteam VALUES ('POL','Poland','Franciszek Smuda');
INSERT INTO eteam VALUES ('RUS','Russia','Dick Advocaat');
INSERT INTO eteam VALUES ('CZE','Czech Republic','Michal Bilek');
INSERT INTO eteam VALUES ('GRE','Greece','Fernando Santos');
INSERT INTO eteam VALUES ('NED','Netherlands','Bert van Marwijk');
INSERT INTO eteam VALUES ('DEN','Denmark','Morten Olsen');
INSERT INTO eteam VALUES ('GER','Germany','Joachim Low');
INSERT INTO eteam VALUES ('POR','Portugal','Paulo Bento');
INSERT INTO eteam VALUES ('ESP','Spain','Vicente del Bosque');
INSERT INTO eteam VALUES ('ITA','Italy','Cesare Prandelli');
INSERT INTO eteam VALUES ('IRL','Republic of Ireland','Giovanni Trapattoni');
INSERT INTO eteam VALUES ('CRO','Croatia','Slaven Bilic');
INSERT INTO eteam VALUES ('UKR','Ukraine','Oleh Blokhin');
INSERT INTO eteam VALUES ('SWE','Sweden','Erik Hamren');
INSERT INTO eteam VALUES ('ENG','England','Roy Hodgson');
INSERT INTO eteam VALUES ('FRA','France','Laurent Blanc');


INSERT INTO game VALUES (1001,'8 June 2012','National Stadium Warsaw','POL','GRE');
INSERT INTO game VALUES (1002,'8 June 2012','Stadion Miejski Wroclaw','RUS','CZE');
INSERT INTO game VALUES (1003,'12 June 2012','Stadion Miejski Wroclaw','GRE','CZE');
INSERT INTO game VALUES (1004,'12 June 2012','National Stadium Warsaw','POL','RUS');
INSERT INTO game VALUES (1005,'16 June 2012','Stadion Miejski Wroclaw','CZE','POL');
INSERT INTO game VALUES (1006,'16 June 2012','National Stadium Warsaw','GRE','RUS');
INSERT INTO game VALUES (1007,'9 June 2012','Metalist Stadium','NED','DEN');
INSERT INTO game VALUES (1008,'9 June 2012','Arena Lviv','GER','POR');
INSERT INTO game VALUES (1009,'13 June 2012','Arena Lviv','DEN','POR');
INSERT INTO game VALUES (1010,'13 June 2012','Metalist Stadium','NED','GER');
INSERT INTO game VALUES (1011,'17 June 2012','Metalist Stadium','POR','NED');
INSERT INTO game VALUES (1012,'17 June 2012','Arena Lviv','DEN','GER');
INSERT INTO game VALUES (1013,'10 June 2012','PGE Arena Gdansk','ESP','ITA');
INSERT INTO game VALUES (1014,'10 June 2012','Stadion Miejski Poznan','IRL','CRO');
INSERT INTO game VALUES (1015,'14 June 2012','Stadion Miejski Poznan','ITA','CRO');
INSERT INTO game VALUES (1016,'14 June 2012','PGE Arena Gdansk','ESP','IRL');
INSERT INTO game VALUES (1017,'18 June 2012','PGE Arena Gdansk','CRO','ESP');
INSERT INTO game VALUES (1018,'18 June 2012','Stadion Miejski Poznan','ITA','IRL');
INSERT INTO game VALUES (1019,'11 June 2012','Donbass Arena','FRA','ENG');
INSERT INTO game VALUES (1020,'11 June 2012','Olimpiyskiy National Sports Complex','UKR','SWE');
INSERT INTO game VALUES (1021,'15 June 2012','Donbass Arena','UKR','FRA');
INSERT INTO game VALUES (1022,'15 June 2012','Olimpiyskiy National Sports Complex','SWE','ENG');
INSERT INTO game VALUES (1023,'19 June 2012','Donbass Arena','ENG','UKR');
INSERT INTO game VALUES (1024,'19 June 2012','Olimpiyskiy National Sports Complex','SWE','FRA');
INSERT INTO game VALUES (1025,'21 June 2012','National Stadium Warsaw','CZE','POR');
INSERT INTO game VALUES (1026,'22 June 2012','PGE Arena Gdansk','GER','GRE');
INSERT INTO game VALUES (1027,'23 June 2012','Donbass Arena','ESP','FRA');
INSERT INTO game VALUES (1028,'24 June 2012','Olimpiyskiy National Sports Complex','ENG','ITA');
INSERT INTO game VALUES (1029,'27 June 2012','Donbass Arena','POR','ESP');
INSERT INTO game VALUES (1030,'28 June 2012','National Stadium Warsaw','GER','ITA');
INSERT INTO game VALUES (1031,'1 July 2012','Olimpiyskiy National Sports Complex','ESP','ITA');

INSERT INTO goal VALUES (1001,'POL','Robert Lewandowski',17);
INSERT INTO goal VALUES (1001,'GRE','Dimitris Salpingidis',51);
INSERT INTO goal VALUES (1002,'RUS','Alan Dzagoev',15);
INSERT INTO goal VALUES (1002,'RUS','Alan Dzagoev',79);
INSERT INTO goal VALUES (1002,'RUS','Roman Shirokov',24);
INSERT INTO goal VALUES (1002,'RUS','Roman Pavlyuchenko',82);
INSERT INTO goal VALUES (1002,'CZE','Vaclav Pilar',52);
INSERT INTO goal VALUES (1003,'GRE','Theofanis Gekas',53);
INSERT INTO goal VALUES (1003,'CZE','Petr Jiracek',3);
INSERT INTO goal VALUES (1003,'CZE','Vaclav Pilar',6);
INSERT INTO goal VALUES (1004,'POL','Jakub Blaszczykowski',57);
INSERT INTO goal VALUES (1004,'RUS','Alan Dzagoev',37);
INSERT INTO goal VALUES (1005,'CZE','Petr Jiracek',72);
INSERT INTO goal VALUES (1006,'GRE','Giorgos Karagounis',45);
INSERT INTO goal VALUES (1007,'DEN','Michael Krohn-Dehli',24);
INSERT INTO goal VALUES (1008,'GER','Mario Gomez',72);
INSERT INTO goal VALUES (1009,'DEN','Nicklas Bendtner',41);
INSERT INTO goal VALUES (1009,'DEN','Nicklas Bendtner',80);
INSERT INTO goal VALUES (1009,'POR','Pepe',24);
INSERT INTO goal VALUES (1009,'POR','Helder Postiga',36);
INSERT INTO goal VALUES (1009,'POR','Silvestre Varela',87);
INSERT INTO goal VALUES (1010,'NED','Robin van Persie',73);
INSERT INTO goal VALUES (1010,'GER','Mario Gomez',24);
INSERT INTO goal VALUES (1010,'GER','Mario Gomez',38);
INSERT INTO goal VALUES (1011,'POR','Cristiano Ronaldo',28);
INSERT INTO goal VALUES (1011,'POR','Cristiano Ronaldo',74);
INSERT INTO goal VALUES (1011,'NED','Rafael van der Vaart',11);
INSERT INTO goal VALUES (1012,'DEN','Michael Krohn-Dehli',24);
INSERT INTO goal VALUES (1012,'GER','Lukas Podolski',19);
INSERT INTO goal VALUES (1012,'GER','Lars Bender',80);
INSERT INTO goal VALUES (1013,'ESP','Cesc Fabregas',64);
INSERT INTO goal VALUES (1013,'ITA','Antonio Di Natale',61);
INSERT INTO goal VALUES (1014,'IRL','Sean St Ledger',19);
INSERT INTO goal VALUES (1014,'CRO','Mario Mandzukic',3);
INSERT INTO goal VALUES (1014,'CRO','Mario Mandzukic',49);
INSERT INTO goal VALUES (1014,'CRO','Nikica Jelavic',43);
INSERT INTO goal VALUES (1015,'ITA','Andrea Pirlo',39);
INSERT INTO goal VALUES (1015,'CRO','Mario Mandzukic',72);
INSERT INTO goal VALUES (1016,'ESP','Fernando Torres',4);
INSERT INTO goal VALUES (1016,'ESP','Fernando Torres',70);
INSERT INTO goal VALUES (1016,'ESP','David Silva',49);
INSERT INTO goal VALUES (1016,'ESP','Cesc Fabregas',83);
INSERT INTO goal VALUES (1017,'ESP','Jesus Navas',88);
INSERT INTO goal VALUES (1018,'ITA','Antonio Cassano',35);
INSERT INTO goal VALUES (1018,'ITA','Mario Balotelli',90);
INSERT INTO goal VALUES (1019,'FRA','Samir Nasri',39);
INSERT INTO goal VALUES (1019,'ENG','Joleon Lescott',30);
INSERT INTO goal VALUES (1020,'UKR','Andriy Shevchenko',55);
INSERT INTO goal VALUES (1020,'UKR','Andriy Shevchenko',62);
INSERT INTO goal VALUES (1020,'SWE','Zlatan Ibrahimovic',52);
INSERT INTO goal VALUES (1021,'FRA','Jeremy Menez',53);
INSERT INTO goal VALUES (1021,'FRA','Yohan Cabaye',56);
INSERT INTO goal VALUES (1022,'SWE','Glen Johnson',49);
INSERT INTO goal VALUES (1022,'SWE','Olof Mellberg',59);
INSERT INTO goal VALUES (1022,'ENG','Andy Carroll',23);
INSERT INTO goal VALUES (1022,'ENG','Theo Walcott',64);
INSERT INTO goal VALUES (1022,'ENG','Danny Welbeck',78);
INSERT INTO goal VALUES (1023,'ENG','Wayne Rooney',48);
INSERT INTO goal VALUES (1024,'SWE','Zlatan Ibrahimovic',54);
INSERT INTO goal VALUES (1024,'SWE','Sebastian Larsson',90);
INSERT INTO goal VALUES (1025,'POR','Cristiano Ronaldo',79);
INSERT INTO goal VALUES (1026,'GER','Philipp Lahm',39);
INSERT INTO goal VALUES (1026,'GER','Sami Khedira',61);
INSERT INTO goal VALUES (1026,'GER','Miroslav Klose',68);
INSERT INTO goal VALUES (1026,'GER','Marco Reus',74);
INSERT INTO goal VALUES (1026,'GRE','Georgios Samaras',55);
INSERT INTO goal VALUES (1026,'GRE','Dimitris Salpingidis',89);
INSERT INTO goal VALUES (1027,'ESP','Xabi Alonso',19);
INSERT INTO goal VALUES (1027,'ESP','Xabi Alonso',90);
INSERT INTO goal VALUES (1030,'GER','Mesut Ozil',90);
INSERT INTO goal VALUES (1030,'ITA','Mario Balotelli',20);
INSERT INTO goal VALUES (1030,'ITA','Mario Balotelli',36);
INSERT INTO goal VALUES (1031,'ESP','David Silva',14);
INSERT INTO goal VALUES (1031,'ESP','Jordi Alba',41);
INSERT INTO goal VALUES (1031,'ESP','Fernando Torres',84);
INSERT INTO goal VALUES (1031,'ESP','Juan Mata',88);