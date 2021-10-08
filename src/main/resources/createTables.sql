SET NAMES utf8;

-- --------------------
-- Creation of Database
-- --------------------
CREATE DATABASE payMyBuddy;
USE payMyBuddy;

-- ----------------------------------
-- Creation of the different Tables
-- ----------------------------------

-- Creation of the Table User
CREATE TABLE User(
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
first_name VARCHAR(20) NOT NULL,
last_name VARCHAR(20) NOT NULL,
email_address VARCHAR(50) NOT NULL,
account_password VARCHAR(50) NOT NULL,
account_balance DECIMAL(10,2) DEFAULT 0.00,
PRIMARY KEY (id)
)
ENGINE=InnoDB;

-- Creation of the Table BankAccount
CREATE TABLE Bank_Account(
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
iban VARCHAR(27) NOT NULL,
user_id INT UNSIGNED NOT NULL,
PRIMARY KEY (id),
CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE=InnoDB;

-- Creation of the Table Connection
CREATE TABLE Connection(
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
date_start DATE NOT NULL,
PRIMARY KEY (id)
)
ENGINE=InnoDB;

-- Creation of the Table Relationship
CREATE TABLE Connection(
connection_id INT NOT NULL,
user_id INT NOT NULL,
PRIMARY KEY (connection_id,user_id),
CONSTRAINT fk_connection_id FOREIGN KEY (connection_id) REFERENCES Connection(id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE=InnoDB;

CREATE TABLE Transaction(
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
connection_id INT NOT NULL,
amount DECIMAL(10,2) DEFAULT 0.00,
date DATE NOT NULL,
description TEXT,
PRIMARY KEY (id),
CONSTRAINT fk_connection_id FOREIGN KEY (connection_id) REFERENCES Connection(id) ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE=InnoDB;