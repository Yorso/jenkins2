<!-- DROP DATABASE IF EXISTS jenkins_test_db; -->
CREATE SCHEMA `jenkins_test_db` ;
USE `jenkins_test_db`;
CREATE TABLE user(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	first_name VARCHAR(100) NOT NULL,
	age INT NOT NULL
);