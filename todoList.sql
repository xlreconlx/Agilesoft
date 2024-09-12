-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema todoList
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema todoList
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `todoList` DEFAULT CHARACTER SET utf8 ;
USE `todoList` ;

-- -----------------------------------------------------
-- Table `todoList`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `todoList`.`usuarios` (
  `idUsuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(100) NULL,
  PRIMARY KEY (`idUsuario`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `todoList`.`tareas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `todoList`.`tareas` (
  `idTarea` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(80) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(200) NULL,
  `fechaCreacion` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fechaModificacion` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `idUsuario` INT NOT NULL,
  PRIMARY KEY (`idTarea`),
  INDEX `fk_tarea_usuarios_idx` (`idUsuario` ASC) VISIBLE,
  CONSTRAINT `fk_tarea_usuarios`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `todoList`.`usuarios` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
