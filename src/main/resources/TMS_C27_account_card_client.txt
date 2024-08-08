CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    email VARCHAR(256) NOT NULL UNIQUE
);

CREATE TABLE account (
    id SERIAL PRIMARY KEY,
    account_number VARCHAR(28) NOT NULL UNIQUE,
    balance NUMERIC(15, 2) NOT NULL DEFAULT 0.00,
    client_id INTEGER REFERENCES client(id)
);

CREATE TABLE card (
    id SERIAL PRIMARY KEY,
    card_number BIGINT NOT NULL UNIQUE,
    account_id INTEGER REFERENCES account(id),
    client_id INTEGER REFERENCES client(id)
);