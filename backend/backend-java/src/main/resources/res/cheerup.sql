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
-- Table `cheerup`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`user` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `id` VARCHAR(30) NULL DEFAULT NULL,
  `password` VARCHAR(128) NULL DEFAULT NULL,
  `nickname` VARCHAR(30) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `user_img_url` VARCHAR(500) NULL DEFAULT NULL,
  `user_img_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cheerup`.`alarm`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`alarm` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`alarm` (
  `alarm_id` INT NOT NULL AUTO_INCREMENT,
  `alarm_receiver_id` INT NULL DEFAULT NULL,
  `alarm_sender_id` INT NULL DEFAULT NULL,
  `alarm_type` VARCHAR(45) NULL DEFAULT NULL,
  `alarm_content` VARCHAR(100) NULL DEFAULT NULL,
  `alarm_url` VARCHAR(500) NULL DEFAULT NULL,
  `alarm_datetime` DATETIME NULL DEFAULT NULL,
  `alarm_check_datetime` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`alarm_id`),
  INDEX `alarm_receiver_id_idx` (`alarm_receiver_id` ASC) VISIBLE,
  INDEX `alarm_sender_id_idx` (`alarm_sender_id` ASC) VISIBLE,
  CONSTRAINT `alarm_receiver_id`
    FOREIGN KEY (`alarm_receiver_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE CASCADE,
  CONSTRAINT `alarm_sender_id`
    FOREIGN KEY (`alarm_sender_id`)
    REFERENCES `cheerup`.`user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cheerup`.`board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`board` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`board` (
  `board_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `board_name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`board_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `board_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cheerup`.`feed`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`feed` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`feed` (
  `feed_id` INT NOT NULL AUTO_INCREMENT,
  `feed_type` INT NULL DEFAULT NULL,
  `feed_url` VARCHAR(500) NULL DEFAULT NULL,
  `feed_source` VARCHAR(45) NULL DEFAULT NULL,
  `feed_title` VARCHAR(120) NULL DEFAULT NULL,
  `feed_content` VARCHAR(3000) NULL DEFAULT NULL,
  `feed_author` VARCHAR(45) NULL DEFAULT NULL,
  `feed_date` DATETIME NULL DEFAULT NULL,
  `feed_category` VARCHAR(45) NULL DEFAULT NULL,
  `feed_img_name` VARCHAR(45) NULL DEFAULT NULL,
  `feed_img_url` VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (`feed_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cheerup`.`comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`comment` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`comment` (
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `feed_id` INT NULL DEFAULT NULL,
  `comment_content` VARCHAR(300) NULL DEFAULT NULL,
  `id` VARCHAR(30) NULL DEFAULT NULL,
  `comment_date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  INDEX `comment_feed_id_idx` (`feed_id` ASC) VISIBLE,
  CONSTRAINT `comment_feed_id`
    FOREIGN KEY (`feed_id`)
    REFERENCES `cheerup`.`feed` (`feed_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cheerup`.`comment_like`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`comment_like` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`comment_like` (
  `like_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `comment_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`like_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `comment_id_idx` (`comment_id` ASC) VISIBLE,
  CONSTRAINT `comment_like_id`
    FOREIGN KEY (`comment_id`)
    REFERENCES `cheerup`.`comment` (`comment_id`)
    ON DELETE CASCADE,
  CONSTRAINT `comment_like_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cheerup`.`keyword`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`keyword` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`keyword` (
  `keyword_id` INT NOT NULL AUTO_INCREMENT,
  `keyword` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`keyword_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cheerup`.`fav_keyword`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`fav_keyword` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`fav_keyword` (
  `fav_keyword_id` INT NOT NULL AUTO_INCREMENT,
  `keyword_id` INT NULL DEFAULT NULL,
  `user_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`fav_keyword_id`),
  INDEX `keyword_id_idx` (`keyword_id` ASC) VISIBLE,
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fav_keyword_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE CASCADE,
  CONSTRAINT `keyword_id`
    FOREIGN KEY (`keyword_id`)
    REFERENCES `cheerup`.`keyword` (`keyword_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cheerup`.`follow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`follow` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`follow` (
  `follow_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `follow_user_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`follow_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `user_id_idx1` (`follow_user_id` ASC) VISIBLE,
  CONSTRAINT `follow_user_id`
    FOREIGN KEY (`follow_user_id`)
    REFERENCES `cheerup`.`user` (`user_id`),
  CONSTRAINT `from_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cheerup`.`personal_word`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`personal_word` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`personal_word` (
  `personal_word_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `personal_word` VARCHAR(60) NULL DEFAULT NULL,
  `personal_word_exp` VARCHAR(600) NULL DEFAULT NULL,
  `personal_fav_word` TINYINT NULL DEFAULT NULL,
  `personal_word_date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`personal_word_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `personal_word_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cheerup`.`schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`schedule` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`schedule` (
  `schedule_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `schedule_title` VARCHAR(45) NULL DEFAULT NULL,
  `schedule_company` VARCHAR(45) NULL DEFAULT NULL,
  `schedule_date` DATETIME NULL DEFAULT NULL,
  `schedule_memo` VARCHAR(600) NULL DEFAULT NULL,
  `schedule_category` VARCHAR(45) NULL DEFAULT NULL,
  `schedule_status` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`schedule_id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `schedule_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cheerup`.`token`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`token` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`token` (
  `token_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `refresh_token_period` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`token_id`),
  INDEX `token_user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `token_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 31
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cheerup`.`user_feed_myfeed`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`user_feed_myfeed` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`user_feed_myfeed` (
  `myfeed_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL DEFAULT NULL,
  `feed_id` INT NULL DEFAULT NULL,
  `myfeed_date` DATETIME NULL DEFAULT NULL,
  `board_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`myfeed_id`),
  INDEX `board_id_idx` (`board_id` ASC) VISIBLE,
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `myfeed_feed_id_idx` (`feed_id` ASC) VISIBLE,
  CONSTRAINT `myfeed_board_id`
    FOREIGN KEY (`board_id`)
    REFERENCES `cheerup`.`board` (`board_id`),
  CONSTRAINT `myfeed_feed_id`
    FOREIGN KEY (`feed_id`)
    REFERENCES `cheerup`.`feed` (`feed_id`)
    ON DELETE CASCADE,
  CONSTRAINT `myfeed_user_id1`
    FOREIGN KEY (`user_id`)
    REFERENCES `cheerup`.`user` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cheerup`.`word`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cheerup`.`word` ;

CREATE TABLE IF NOT EXISTS `cheerup`.`word` (
  `word_id` INT NOT NULL AUTO_INCREMENT,
  `word` VARCHAR(60) NULL DEFAULT NULL,
  `word_exp` VARCHAR(600) NULL DEFAULT NULL,
  PRIMARY KEY (`word_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
