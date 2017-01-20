INSERT INTO users (NAME, ROLE, LOGIN, PASSWORD) VALUES ('MAKSYM','ADMIN','asd','123123');
INSERT INTO plains(NAME) VALUES ('Plain model 1'),('Plain model 2'),('Plain model 3'),('Plain model 4'),('Plain model 5');
INSERT INTO situation(NAME, DESCRIPTION) VALUES ('One engine failure','Assessment of correctness parry the failure of one engine.'),('Quality of touch the runway',''),('Quality of braking','');
INSERT INTO metrics(NAME, VALUE, SITUATION_ID) VALUES ('vaning ',1,1),('fuel',1,1);
INSERT INTO records(USER_ID, DATE, SITUATION_ID, PLAIN_ID, SIM_DATA) VALUES (1,'2017-01-03 17:44:14',1,1,'asdasdasd');

