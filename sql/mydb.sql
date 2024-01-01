CREATE DATABASE mydb;
DROP DATABASE mydb;



CREATE TABLE IF NOT EXISTS user_session (

    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    token VARCHAR(255) DEFAULT NULL,
    coins INT DEFAULT 20
);

DROP TABLE user_session;



CREATE TABLE IF NOT EXISTS user_data (

    username VARCHAR(255) PRIMARY KEY REFERENCES user_session(username),
    name VARCHAR(255) NOT NULL,
    bio VARCHAR(255) NOT NULL,
    image VARCHAR(255) NOT NULL
);

DROP TABLE user_data;



CREATE TABLE IF NOT EXISTS card (
    cardID VARCHAR(255) PRIMARY KEY,
    card_name VARCHAR(255) NOT NULL,
    damage FLOAT NOT NULL,
    monsterCard BOOLEAN NOT NULL,
    element VARCHAR(255) NOT NULL
    );


DROP TABLE card;



CREATE TABLE IF NOT EXISTS package (
    packageID SERIAL PRIMARY KEY,
    card1 VARCHAR(255) REFERENCES card(cardID) NOT NULL,
    card2 VARCHAR(255) REFERENCES card(cardID) NOT NULL,
    card3 VARCHAR(255) REFERENCES card(cardID) NOT NULL,
    card4 VARCHAR(255) REFERENCES card(cardID) NOT NULL,
    card5 VARCHAR(255) REFERENCES card(cardID) NOT NULL,
    available BOOLEAN DEFAULT true
);

DROP TABLE package;



