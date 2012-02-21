-- MySQL definition and initialization script for shop service application
-- Copyright 2010-2011 Sascha Baumeister, all rights reserved
SET CHARACTER SET latin1;
DROP DATABASE IF EXISTS sudoku;
CREATE DATABASE sudoku CHARACTER SET utf8;
USE sudoku;

CREATE TABLE Sudoku (
	id BIGINT AUTO_INCREMENT,
	hash BIGINT NOT NULL,
	digitsToSolve BINARY NOT NULL,
	digitsSolved BINARY NOT NULL,
	PRIMARY KEY (id),
	UNIQUE KEY (hash)
) ENGINE=InnoDB;


#INSERT INTO Customer VALUES (0, "ines", "ines", "Ines", "Bergmann", "Wiener Strasse 42", "10999", "Berlin", "ines.bergmann@web.de", "0172/2345678");
#SET @c1 = LAST_INSERT_ID();
#INSERT INTO Customer VALUES (0, "sascha", "sascha", "Sascha", "Baumeister", "Ohlauer Strasse 29", "10999", "Berlin", "sascha.baumeister@gmail.com", "0174/3345975");
#SET @c2 = LAST_INSERT_ID();

#INSERT INTO Article VALUES (0, "CARIOCA Fahrrad-Schlauch, 28x1.5 Zoll", 40, 167);
#SET @a1 = LAST_INSERT_ID();
#INSERT INTO Article VALUES (0, "CONTINENTAL Fahrrad-Schlauch Tour, 28 Zoll", 80, 336);
#SET @a2 = LAST_INSERT_ID();
#INSERT INTO Article VALUES (0, "PROPHETE Fahrrad-Schlauch, 14x1.75 Zoll", 20, 252);
#SET @a3 = LAST_INSERT_ID();

#INSERT INTO Purchase VALUES (0, @c1, 1288605807761, 0.19);
#SET @p1 = LAST_INSERT_ID();
#INSERT INTO Purchase VALUES (0, @c2, 1288635807761, 0.19);
#SET @p2 = LAST_INSERT_ID();

#INSERT INTO PurchaseItem VALUES (0, @p1, @a1, 2, 167);
#INSERT INTO PurchaseItem VALUES (0, @p2, @a2, 1, 336);
#INSERT INTO PurchaseItem VALUES (0, @p2, @a3, 4, 252);
#COMMIT;

#SELECT * FROM Customer;
#SELECT * FROM Article;
#SELECT * FROM Purchase;
#SELECT * FROM PurchaseItem;
