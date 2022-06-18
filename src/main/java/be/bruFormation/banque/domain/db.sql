DROP TABLE IF EXISTS Account;
DROP TABLE IF EXISTS Bank;
DROP TABLE IF EXISTS Holder;
CREATE TABLE Holder
(
    holder_id       INTEGER PRIMARY KEY AUTOINCREMENT,
    national_number VARCHAR(50) NOT NULL UNIQUE,
    last_name       VARCHAR(50) NOT NULL,
    first_name      VARCHAR(50) NOT NULL
);

CREATE TABLE Bank
(
    bank_id    INTEGER PRIMARY KEY AUTOINCREMENT,
    name       VARCHAR(50) NOT NULL UNIQUE,
    swift_code CHAR(11)    NOT NULL UNIQUE
);

CREATE TABLE Account
(
    account_id            INTEGER PRIMARY KEY AUTOINCREMENT,
    number                VARCHAR(50) NOT NULL UNIQUE,
    sold                  REAL        NOT NULL DEFAULT 0,
    credit_line           REAL        NULL,
    last_withdrawn_date DATE        NULL,
    desc                  VARCHAR(50) NOT NULL,
    holder_id             INTEGER REFERENCES Holder (holder_id),
    bank_id               INTEGER REFERENCES Bank (bank_id)
);

