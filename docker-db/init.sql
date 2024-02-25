-- Drop tables if they exist
DROP TABLE IF EXISTS user_ticket CASCADE;
DROP TABLE IF EXISTS lottery CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE SEQUENCE user_sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9999999999
    CACHE 1;

CREATE TABLE users (
    user_id VARCHAR(10) DEFAULT LPAD(nextval('user_sequence')::TEXT, 10, '0') PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE lottery (
    id SERIAL PRIMARY KEY,
    ticket VARCHAR(6) UNIQUE NOT NULL,
    price INT NOT NULL,
    amount INT NOT NULL
);

CREATE TABLE user_ticket (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(10) REFERENCES users(user_id),
    lottery_id INT REFERENCES lottery(id),
    purchase_date TIMESTAMP
);

-- Initial data
INSERT INTO users(user_id, username, password) VALUES(LPAD(nextval('user_sequence')::TEXT, 10, '0'), 'admin', 'password');
INSERT INTO users(user_id, username, password) VALUES(LPAD(nextval('user_sequence')::TEXT, 10, '0'), 'john', 'wick');
