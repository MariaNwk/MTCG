CREATE DATABASE mydb;
DROP DATABASE mydb;



CREATE TABLE IF NOT EXISTS user_session (


    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
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

