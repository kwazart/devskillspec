CREATE DATABASE devskillspec;
USE devskillspec;
go

CREATE TABLE `devskillspec`.`skills` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `skill_name` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `skill_name_UNIQUE` (`skill_name` ASC) VISIBLE);

go

CREATE TABLE `devskillspec`.`specialties` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `specialties_name` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `specialties_name_UNIQUE` (`specialties_name` ASC) VISIBLE);
go

CREATE TABLE `devskillspec`.`developers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `specialty` VARCHAR(45) NOT NULL,
  `skills` VARCHAR(200) NOT NULL,
  `status` VARCHAR(45) NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `firstNamw_UNIQUE` (`firstName` ASC) VISIBLE,
  UNIQUE INDEX `lastName_UNIQUE` (`lastName` ASC) VISIBLE,
  UNIQUE INDEX `specialty_UNIQUE` (`specialty` ASC) VISIBLE,
  UNIQUE INDEX `skills_UNIQUE` (`skills` ASC) VISIBLE);

 
