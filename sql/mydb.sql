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
    element VARCHAR(255) NOT NULL,
    card_holder VARCHAR(255) REFERENCES user_session(username)
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


CREATE TABLE IF NOT EXISTS deck (
    deckID VARCHAR(255) PRIMARY KEY REFERENCES user_session(username),
    card1 VARCHAR(255) REFERENCES card(cardID) NOT NULL,
    card2 VARCHAR(255) REFERENCES card(cardID) NOT NULL,
    card3 VARCHAR(255) REFERENCES card(cardID) NOT NULL,
    card4 VARCHAR(255) REFERENCES card(cardID) NOT NULL
);

DROP TABLE deck;


CREATE TABLE IF NOT EXISTS stats (
    name VARCHAR(255) PRIMARY KEY REFERENCES user_session(username),
    elo INT DEFAULT 100,
    wins INT DEFAULT 0,
    losses INT DEFAULT 0
);

DROP TABLE stats;

CREATE TABLE IF NOT EXISTS trades (
    id varchar(255) PRIMARY KEY,
    cardID varchar(255) UNIQUE REFERENCES card(cardID),
    type varchar(255),
    minDamage float,
    card_holder varchar(255) REFERENCES user_session(username)
);

DROP TABLE trades;
