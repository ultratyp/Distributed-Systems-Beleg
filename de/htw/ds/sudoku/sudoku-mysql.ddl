-- MySQL definition and initialization script for sudoku service application

SET CHARACTER SET latin1;
DROP DATABASE IF EXISTS sudoku;
CREATE DATABASE sudoku CHARACTER SET utf8;
USE sudoku;

CREATE TABLE Sudoku (
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	hash BIGINT(20) NOT NULL,
	digitsToSolve TEXT NOT NULL,
	digitsSolved TEXT NOT NULL,
	PRIMARY KEY (id),
	UNIQUE KEY (hash)
) ENGINE=InnoDB;