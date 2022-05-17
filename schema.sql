
-- -----------------------
-- Creation of Database --
-- -----------------------
/* Setting up PROD DB */
CREATE DATABASE IF NOT EXISTS paymybuddy_db_prod;
USE paymybuddy_db_prod;
-- ----------------------------------
-- Creation of the different Tables
-- ----------------------------------

-- Creation of the Table User
CREATE TABLE IF NOT EXISTS User(
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
first_name VARCHAR(100) NOT NULL,
last_name VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL,
password VARCHAR(100) NOT NULL,
balance DECIMAL(10,2) DEFAULT 0.00,

PRIMARY KEY (id),
UNIQUE INDEX ind_email (email)
);

-- Creation of the Table BankAccount
CREATE TABLE IF NOT EXISTS Bank_Account(
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
iban VARCHAR(50) NOT NULL,
user_id INT UNSIGNED NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Creation of the Table Connection
CREATE TABLE IF NOT EXISTS Connection(
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
date_start TIMESTAMP NOT NULL,
PRIMARY KEY (id)
);

-- Creation of the Table Relationship
CREATE TABLE IF NOT EXISTS Relationship(
connection_id INT UNSIGNED NOT NULL,
user_id INT UNSIGNED NOT NULL,
PRIMARY KEY (connection_id,user_id),
CONSTRAINT fk_relationship_connection_id FOREIGN KEY (connection_id) REFERENCES Connection(id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_relationship_user_id FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Transaction(
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
connection_id INT UNSIGNED NOT NULL,
user_sender_id INT UNSIGNED NOT NULL,
user_receiver_id INT UNSIGNED NOT NULL,
amount DECIMAL(10,2) DEFAULT 0.00,
date TIMESTAMP NOT NULL,
description TEXT,
PRIMARY KEY (id),
CONSTRAINT fk_connection_id FOREIGN KEY (connection_id) REFERENCES Connection(id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_user_sender_id FOREIGN KEY (user_sender_id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_user_receiver_id FOREIGN KEY (user_receiver_id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE
);