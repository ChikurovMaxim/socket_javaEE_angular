INSERT INTO users (NAME, ROLE, LOGIN, PASSWORD) VALUES ('MAKSYM','ADMIN','asd','123123');
INSERT INTO plain_models(NAME) VALUES ('Plain model 1'),('Plain model 2'),('Plain model 3'),('Plain model 4'),('Plain model 5');
INSERT INTO metrics(NAME, VALUE, PLAIN_MODEL_ID) VALUES ('heght',154.4,1),('width',164.4,1),('arrow',1.4,1),('plank',0.0124,1);
INSERT INTO records(USER_ID, DATE, PLAIN_MODEL_ID, SIM_DATA) VALUES (1,'2017-01-03 17:44:14',1,'asdasdasd');