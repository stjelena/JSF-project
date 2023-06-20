-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 07, 2021 at 10:53 PM
-- Server version: 8.0.21
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `promocije2021`
--
DROP DATABASE IF EXISTS `promocije2021`;
CREATE DATABASE IF NOT EXISTS `promocije2021` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `promocije2021`;

-- --------------------------------------------------------

--
-- Table structure for table `knjige`
--

DROP TABLE IF EXISTS `knjige`;
CREATE TABLE IF NOT EXISTS `knjige` (
  `idK` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `broj_strana` int NOT NULL,
  `pisac` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `status` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`idK`),
  KEY `pisci` (`pisac`),
  KEY `status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `knjige`
--

INSERT INTO `knjige` (`idK`, `naziv`, `broj_strana`, `pisac`, `status`) VALUES
(1, 'Svaki dan u godini', 150, 'ana', 1),
(2, 'Gusarski gulas', 196, 'jova', 2),
(3, 'Moritati i legende', 235, 'jova', 3),
(4, 'Glasovi zute sveske', 108, 'mina', 2);

-- --------------------------------------------------------

--
-- Table structure for table `korisnici`
--

DROP TABLE IF EXISTS `korisnici`;
CREATE TABLE IF NOT EXISTS `korisnici` (
  `ime` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `prezime` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `korisnicko_ime` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `lozinka` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `tip` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`korisnicko_ime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `korisnici`
--

INSERT INTO `korisnici` (`ime`, `prezime`, `korisnicko_ime`, `lozinka`, `tip`) VALUES
('Ana', 'Stojkovic', 'ana', 'ana123', 'pisac'),
('Jova', 'Mirkovic', 'jova', 'jova123', 'pisac'),
('Mina', 'Tepovic', 'mina', 'mina123', 'pisac'),
('Petar', 'Nikolic', 'pera', 'pera123', 'organizator'),
('Srdjan', 'Petrovic', 'srki', 'srki123', 'organizator');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `knjige`
--
ALTER TABLE `knjige`
  ADD CONSTRAINT `pisci` FOREIGN KEY (`pisac`) REFERENCES `korisnici` (`korisnicko_ime`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;