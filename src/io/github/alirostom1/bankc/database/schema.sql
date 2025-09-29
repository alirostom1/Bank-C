CREATE TABLE clients (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE accounts (
    id VARCHAR(36) PRIMARY KEY,
    number VARCHAR(50) NOT NULL UNIQUE,
    balance DOUBLE NOT NULL,
    clientId VARCHAR(26) NOT NULL,
    accountType ENUM('SAVINGS', 'CHECKING') NOT NULL DEFAULT 'CHECKING',
    FOREIGN KEY (clientId) REFERENCES clients(id)
);
CREATE TABLE checkings_account(
    id VARCHAR(36) PRIMARY KEY,
    overdraft_limit DOUBLE NOT NULL DEFAULT 0,
    FOREIGN KEY (id) REFERENCES accounts(id)
);
CREATE TABLE savings_account(
    id VARCHAR(36) PRIMARY KEY,
    interest_rate DOUBLE NOT NULL DEFAULT 0,
    FOREIGN KEY (id) REFERENCES accounts(id)
);

CREATE TABLE transactions (
    id VARCHAR(36) PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    amount DOUBLE NOT NULL,
    type ENUM("TRANSFER","WITHDRAW","DEPOSIT") NOT NULL,
    location VARCHAR(100),
    accountId VARCHAR(26) NOT NULL,
    FOREIGN KEY (accountId) REFERENCES accounts(id)
);
