USE paymybuddy_db_prod;

-- Creation of Data in the Table User

INSERT INTO User (first_name, last_name, email, password, balance)
VALUES ('Jean', 'Durand', 'j.d@hotmail.com', '1233456', 50.0),
('Monique', 'Durand', 'm.d@hotmail.com', '0000', 100.0),
('Carole', 'Durand', 'c.d@hotmail.com', 'dauphin', 75.0),
('Baptiste', 'Durand', 'b.d@hotmail.com', 'angeEtDemon', 50.0),
('Maxime', 'Martin', 'm.m@hotmail.com', 'voiture', 50.0);

-- Creation of Data in the Table Connection

INSERT INTO Connection (date_start)
VALUES (curdate()),(curdate()),(curdate()),(curdate());

-- Creation of Data in the Table Relationship

INSERT INTO Relationship (connection_id, user_id)
VALUES (1,1),(1,2),
(2,1),(2,3),
(3,1),(3,4),
(4,1),(4,5);

-- Creation of Data in the Table Transaction

INSERT INTO Transaction (connection_id, user_sender_id, user_receiver_id, amount, date, description)
VALUES
(1,1,2, 5.0, curdate(), 'transaction1'),
(2,1,3, 7.0, curdate(), 'transaction2'),
(1,1,2, 10.0, curdate(), 'transaction3'),
(3,1,4, 5.0, curdate(), 'transaction4'),
(4,5,1, 5.0, curdate(), 'transaction5');