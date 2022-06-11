DROP TABLE IF EXISTS Account;
DROP TABLE IF EXISTS Bank;
DROP TABLE IF EXISTS Holder;
DROP TABLE IF EXISTS Compte;
DROP TABLE IF EXISTS Banque;
DROP TABLE IF EXISTS Titulaire;
CREATE TABLE Holder (
                           id INTEGER PRIMARY KEY AUTOINCREMENT,
                           ssin VARCHAR(50) NOT NULL UNIQUE,
                           last_name VARCHAR(50) NOT NULL,
                           first_name VARCHAR(50) NOT NULL
);

CREATE TABLE Bank (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Account (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        number VARCHAR(50) NOT NULL UNIQUE,
                        solde REAL NOT NULL DEFAULT 0,
                        credit_line REAL NULL,
                        last_whithdrawan_date DATE NULL,
                        desc VARCHAR(50) NOT NULL,
                        holder_id INTEGER REFERENCES Holder(id),
                        bank_id INTEGER REFERENCES Bank(id)
);

