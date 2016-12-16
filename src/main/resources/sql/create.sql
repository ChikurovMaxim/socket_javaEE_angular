USE diploma;
CREATE TABLE IF NOT EXISTS RECORDS(RECORD_ID INT AUTO_INCREMENT NOT NULL,USER_NAME VARCHAR(30) NOT NULL ,DATE DATETIME,PLAIN_MODEL VARCHAR(30),SIM_DATA TEXT,PRIMARY KEY (RECORD_ID));
CREATE TABLE IF NOT EXISTS USERS (ID INT NOT NULL AUTO_INCREMENT,NAME VARCHAR(100),ROLE VARCHAR(15),LOGIN VARCHAR(100),PASSWORD VARCHAR(100),PRIMARY KEY (ID));
CREATE TABLE IF NOT EXISTS PLAIN_MODELS(ID INT NOT NULL AUTO_INCREMENT,NAME VARCHAR(30) UNIQUE ,PRIMARY KEY (ID));
CREATE TABLE IF NOT EXISTS METRICS(ID INT NOT NULL AUTO_INCREMENT,NAME VARCHAR(20),VALUE DOUBLE,PLAIN_ID INT,PRIMARY KEY (ID),FOREIGN KEY (PLAIN_ID) REFERENCES PLAIN_MODELS(ID));

