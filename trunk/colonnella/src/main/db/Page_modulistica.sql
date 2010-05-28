-- phpMyAdmin SQL Dump
-- version 3.1.2deb1ubuntu0.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 29, 2010 at 12:34 AM
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
  `description` varchar(255) default NULL,
  `title` varchar(255) default NULL,
  `template_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK25D6AFA527DC8A` (`template_id`),
  KEY `FK25D6AF1F9A669` (`template_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Page`
--

INSERT INTO `Page` (`id`, `attivo`, `description`, `title`, `template_id`) VALUES
('modulistica', b'1', 'In questa sezione sono raccolti i moduli per richieste all''Amministrazione comunale. Seleziona il modulo di tuo interesse per visualizzarlo sullo schermo.', 'modulistica', 14);
