CREATE DATABASE userdb;
DROP DATABASE userdb;

\c userdb;

CREATE TABLE IF NOT EXISTS user_session (


    id varchar(255)PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

DROP TABLE user;