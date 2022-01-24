CREATE TABLE btc (
  id int NOT NULL AUTO_INCREMENT,
  dateTime DATETIME,
  amount DOUBLE,
  PRIMARY KEY (id)
);

INSERT INTO btc (dateTime, amount) VALUES ('2011-10-05 10:48:01',10);
INSERT INTO btc (dateTime, amount) VALUES ('2011-10-05 18:48:02',10.23);
INSERT INTO btc (dateTime, amount) VALUES ('2011-10-05 12:48:02',30.23);
INSERT INTO btc (dateTime, amount) VALUES ('2011-10-05 12:03:02',15);