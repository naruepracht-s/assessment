-- Drop tables if they exist
DROP TABLE IF EXISTS user_ticket CASCADE;
DROP TABLE IF EXISTS lottery CASCADE;

CREATE TABLE lottery (
    id SERIAL PRIMARY KEY,
    ticket VARCHAR(6) UNIQUE NOT NULL,
    price INT NOT NULL,
    amount INT NOT NULL
);

CREATE TABLE user_ticket (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(10),
    lottery_id INT REFERENCES lottery(id),
    purchase_date TIMESTAMP
);

-- Initial data
--INSERT INTO lottery(ticket, price, amount) VALUES(123456, 80, 10);
--INSERT INTO lottery(ticket, price, amount) VALUES(654321, 120, 5);
