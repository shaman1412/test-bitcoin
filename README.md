# test-bitcoin
#Setup
- you have to installed docker first then run "docker-compose up -d" in path: ~/test-bitcoin
- **without docker you have to start DB mysql with DB name (btc_db) and create table with this command
````
CREATE TABLE btc (
  id int NOT NULL AUTO_INCREMENT,
  dateTime DATETIME,
  amount DOUBLE,
  PRIMARY KEY (id)
);
````
link DB -> jdbc:mysql://localhost:3306/btc_db"
then run "mvn spring-boot:run" with java 11

After all setup complete you can test with dump data

###Get BTC history detail
````
curl --location --request POST 'localhost:8080/bitcoin-composite/v1/detail' \
--header 'Content-Type: application/json' \
--data-raw '{
    "startDatetime": "2011-10-05T10:48:01+00:00",
    "endDatetime": "2011-10-05T18:48:03+00:00"
}'
````

Request body
````
{
    "startDatetime": "2011-10-05T10:48:01+00:00",
    "endDatetime": "2011-10-05T18:48:03+00:00"
}
````
Response body
````
[
    {
        "startDatetime": "2011-10-05T12:00:00+00:00",
        "amount": 45.23
    },
    {
        "startDatetime": "2011-10-05T10:00:00+00:00",
        "amount": 10.0
    },
    {
        "startDatetime": "2011-10-05T18:00:00+00:00",
        "amount": 10.23
    }
]

````

###Add BTC
````
curl --location --request POST 'localhost:8080/bitcoin-composite/v1/addBtc' \
--header 'Accept-Language: th' \
--header 'Content-Type: application/json' \
--data-raw '{
    "datetime": "2011-10-05T10:48:01+00:00",
    "amount": 120
}'
````
Request body
````
{
    "datetime": "2011-10-05T10:48:01+00:00",
    "amount": 120
}
````
Response body
````
{
    "code": "bc-200",
    "message": "โอน bitcoin สำเร็จ"
}
````
