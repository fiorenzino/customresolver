-- phpMyAdmin SQL Dump
-- version 3.2.2.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generato il: 31 mag, 2010 at 12:30 AM
-- Versione MySQL: 5.1.37
-- Versione PHP: 5.2.10-2ubuntu6.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `colonnella`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `Page`
--

CREATE TABLE IF NOT EXISTS `Page` (
  `id` varchar(255) NOT NULL,
  `attivo` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `template_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK25D6AFA527DC8A` (`template_id`),
  KEY `FK25D6AF1F9A669` (`template_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `Page`
--

INSERT INTO `Page` (`id`, `attivo`, `description`, `title`, `template_id`) VALUES
('modulistica', b'1', 'In questa sezione sono raccolti i moduli per richieste all''Amministrazione comunale. Seleziona il modulo di tuo interesse per visualizzarlo sullo schermo.', 'modulistica', 14),
('index', b'1', 'home page', 'index', 15),
('testindex', b'1', 'ttt', 'test_index', 16),
('ddd', b'1', 'ddd', 'ddd', 17),
('wqeqwe', b'1', 'qweqwe', 'wqeqwe', 18),
('ddddd', b'1', 'ddd', 'ddddd', 19),
('wwww', b'1', 'wwww', 'wwww', 20);
