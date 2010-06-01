-- phpMyAdmin SQL Dump
-- version 3.1.2deb1ubuntu0.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 01, 2010 at 03:16 PM
-- Server version: 5.0.75
-- PHP Version: 5.2.6-3ubuntu4.5

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
-- Table structure for table `Page`
--

CREATE TABLE IF NOT EXISTS `Page` (
  `id` varchar(255) NOT NULL,
  `attivo` bit(1) NOT NULL,
  `description` text,
  `title` varchar(255) default NULL,
  `template_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK25D6AF1F9A669` (`template_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Page`
--

INSERT INTO `Page` (`id`, `attivo`, `description`, `title`, `template_id`) VALUES
('modulistica', b'1', 'In questa sezione sono raccolti i moduli per richieste all''Amministrazione comunale. Seleziona il modulo di tuo interesse per visualizzarlo sullo schermo.', 'modulistica', 14),
('index', b'1', 'home page', 'index', 15),
('comune', b'1', 'La giunta comunale e'' composta dal sindaco, che la presiede, e da dieci assessori ovvero dal numero massimo di assessori previsto dalla legge, se inferiore.', 'comune', 34),
('albo-pretorio', b'1', 'Ogni comune deve avere un Albo Pretorio per la pubblicazione delle deliberazioni, delle ordinanze, dei manifesti e degli atti che devono essere portati a conoscenza del pubblico per disposizione di legge o di regolamento.', 'albo pretorio', 32),
('turismo', b'1', 'Le serate della stagione 2009.', 'turismo', 33),
('cultura', b'1', '"Antichi palazzi costruiti su un''alta collina, un intreccio di viuzze e scalinate, diverse piazzette caratteristiche, un panorama incantevole, unico, l''aria salubre, fresca, questa e'' Colonnella."', 'cultura', 35),
('prova-notizie', b'1', 'Prova notizie paginate con parametri get e scope di request', 'Prova notizie', 36),
('prova-notizie-2', b'1', 'Prova del paginatore in get con f:metadata fuori da f:view', 'Prova notizie 2', 37);
