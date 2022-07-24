-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema cheerup
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cheerup
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cheerup` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `cheerup` ;

-- -----------------------------------------------------
-- Table `cheerup`.`keyword`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`keyword` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`keyword` (
  `keyword_id` INT NOT NULL AUTO_INCREMENT,
  `keyword` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`keyword_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cheerup`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`user` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `id` VARCHAR(30) NULL,
  `password` VARCHAR(128) NULL,
  `nickname` VARCHAR(30) NULL,
  `email` VARCHAR(45) NULL,
  `user_img_url` VARCHAR(500) NULL,
  `user_img_name` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cheerup`.`fav_keyword`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`fav_keyword` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`fav_keyword` (
  `fav_keyword_id` INT NOT NULL AUTO_INCREMENT,
  `keyword_id` INT NULL,
  `user_id` INT NULL,
  PRIMARY KEY (`fav_keyword_id`),
  INDEX `keyword_id_idx` (`keyword_id` ASC) VISIBLE,
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `keyword_id`
    FOREIGN KEY (`keyword_id`)
    REFERENCES `cheerup`.`keyword` (`keyword_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fav_keyword_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cheerup`.`follow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`follow` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`follow` (
  `follow_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `follow_user_id` INT NULL,
  PRIMARY KEY (`follow_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `user_id_idx1` (`follow_user_id` ASC) VISIBLE,
  CONSTRAINT `from_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `follow_user_id`
    FOREIGN KEY (`follow_user_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cheerup`.`alarm`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`alarm` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`alarm` (
  `alarm_id` INT NOT NULL AUTO_INCREMENT,
  `alarm_receiver_id` INT NULL,
  `alarm_sender_id` INT NULL,
  `alarm_type` VARCHAR(45) NULL,
  `alarm_content` VARCHAR(100) NULL,
  `alarm_url` VARCHAR(500) NULL,
  `alarm_datetime` DATETIME NULL,
  `alarm_check_datetime` DATETIME NULL,
  PRIMARY KEY (`alarm_id`),
  INDEX `alarm_receiver_id_idx` (`alarm_receiver_id` ASC) VISIBLE,
  INDEX `alarm_sender_id_idx` (`alarm_sender_id` ASC) VISIBLE,
  CONSTRAINT `alarm_receiver_id`
    FOREIGN KEY (`alarm_receiver_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `alarm_sender_id`
    FOREIGN KEY (`alarm_sender_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cheerup`.`word`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`word` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`word` (
  `word_id` INT NOT NULL AUTO_INCREMENT,
  `word` VARCHAR(60) NULL,
  `word_exp` VARCHAR(600) NULL,
  PRIMARY KEY (`word_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cheerup`.`personal_word`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`personal_word` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`personal_word` (
  `personal_word_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `personal_word` VARCHAR(60) NULL,
  `personal_word_exp` VARCHAR(600) NULL,
  `personal_fav_word` TINYINT NULL,
  `personal_word_date` DATETIME NULL,
  PRIMARY KEY (`personal_word_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `personal_word_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cheerup`.`board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`board` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`board` (
  `board_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `board_name` VARCHAR(45) NULL,
  PRIMARY KEY (`board_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `board_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cheerup`.`scrapfeed`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`scrapfeed` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`scrapfeed` (
  `scrapfeed_id` INT NOT NULL AUTO_INCREMENT,
  `scrapfeed_url` VARCHAR(500) NULL,
  `scrapfeed_type` VARCHAR(45) NULL,
  `scrapfeed_category` VARCHAR(45) NULL,
  `scrapfeed_source` VARCHAR(45) NULL,
  `scrapfeed_img_name` VARCHAR(45) NULL,
  `scrapfeed_img_url` VARCHAR(500) NULL,
  PRIMARY KEY (`scrapfeed_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cheerup`.`user_scrapfeed_myfeed`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`user_scrapfeed_myfeed` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`user_scrapfeed_myfeed` (
  `myfeed_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `scrapfeed_id` INT NULL,
  `myfeed_date` DATETIME NULL,
  `board_id` INT NULL,
  PRIMARY KEY (`myfeed_id`),
  INDEX `board_id_idx` (`board_id` ASC) VISIBLE,
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `scrapfeed_id_idx` (`scrapfeed_id` ASC) VISIBLE,
  CONSTRAINT `board_id`
    FOREIGN KEY (`board_id`)
    REFERENCES `cheerup`.`board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `myfeed_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `scrapfeed_id`
    FOREIGN KEY (`scrapfeed_id`)
    REFERENCES `cheerup`.`scrapfeed` (`scrapfeed_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cheerup`.`infofeed`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`infofeed` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`infofeed` (
  `infofeed_id` INT NOT NULL AUTO_INCREMENT,
  `infofeed_url` VARCHAR(500) NULL,
  `infofeed_source` VARCHAR(45) NULL,
  `infofeed_title` VARCHAR(120) NULL,
  `infofeed_content` VARCHAR(3000) NULL,
  `infofeed_author` VARCHAR(45) NULL,
  `infofeed_date` DATETIME NULL,
  `infofeed_category` VARCHAR(45) NULL,
  `infofeed_img_name` VARCHAR(45) NULL,
  `infofeed_img_url` VARCHAR(500) NULL,
  PRIMARY KEY (`infofeed_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cheerup`.`comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`comment` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`comment` (
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `comment_url` VARCHAR(500) NULL,
  `comment_content` VARCHAR(300) NULL,
  `id` VARCHAR(30) NULL,
  `comment_date` DATETIME NULL,
  PRIMARY KEY (`comment_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cheerup`.`like`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`like` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`like` (
  `like_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `comment_id` INT NULL,
  PRIMARY KEY (`like_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `comment_id_idx` (`comment_id` ASC) VISIBLE,
  CONSTRAINT `like_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `comment_id`
    FOREIGN KEY (`comment_id`)
    REFERENCES `cheerup`.`comment` (`comment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cheerup`.`schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`schedule` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`schedule` (
  `schedule_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `schedule_title` VARCHAR(45) NULL,
  `schedule_company` VARCHAR(45) NULL,
  `schedule_date` DATETIME NULL,
  `schedule_memo` VARCHAR(600) NULL,
  PRIMARY KEY (`schedule_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `schedule_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
