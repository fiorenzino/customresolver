-- phpMyAdmin SQL Dump
-- version 3.1.2deb1ubuntu0.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 29, 2010 at 12:35 AM
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
-- Table structure for table `Template`
--

CREATE TABLE IF NOT EXISTS `Template` (
  `id` bigint(20) NOT NULL auto_increment,
  `attivo` bit(1) NOT NULL,
  `col1_start` text,
  `col1_stop` text,
  `col2_start` text,
  `col2_stop` text,
  `col3_start` text,
  `col3_stop` text,
  `footer_start` text,
  `footer_stop` text,
  `header_start` text,
  `header_stop` text,
  `nome` varchar(255) default NULL,
  `statico` bit(1) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `Template`
--

INSERT INTO `Template` (`id`, `attivo`, `col1_start`, `col1_stop`, `col2_start`, `col2_stop`, `col3_start`, `col3_stop`, `footer_start`, `footer_stop`, `header_start`, `header_stop`, `nome`, `statico`) VALUES
(4, b'1', '			<!-- SINISTRA -->\r\n			<div class="COLNewsLeft">\r\n				<div class="COLNewsLeftInt">\r\n', '				</div>\r\n\r\n			</div>\r\n			<!-- /SINISTRA -->\r\n', '					<!-- destra -->\r\n\r\n			<div class="contentRight">\r\n\r\n<!-- Menu di navigazione -->\r\n				<div id="menuNav">\r\n					<span class="navigator">\r\n		<flw:dynamicBreadcrumbs/>\r\n					</span>\r\n\r\n					<div class="clearDiv"></div>\r\n				</div>\r\n<!-- /Menu di navigazione -->			\r\n				<div class="clearDiv" id="mainContent"></div><!-- importante -->\r\n\r\n				<!-- Box Contenuti -->\r\n				<div id="contentBox">\r\n\r\n					<h3 class="hidden">Contenuto della pagina</h3>\r\n					<div class="cbox">\r\n\r\n						<div class="cboxLeftRow"><div class="cboxRightRow">\r\n							<div class="cboxTopLeft"><div class="cboxTopRight">\r\n								<div class="cboxBotLeft"><div class="cboxBotRight">\r\n									<div class="cboxIntContArticWP">\r\n', '	</div>\r\n\r\n		                   		 		<p class="hidden">[ <a title="Torna all''inizio della pagina" href="#top">Torna all''inizio della pagina</a> ]</p>\r\n									</div>\r\n								</div></div>\r\n\r\n							</div></div>\r\n						</div></div>\r\n					</div>\r\n				</div>\r\n				<div class="clearDiv"></div>\r\n			    <!-- /Box Contenuti -->\r\n			</div>\r\n			<!-- /destra -->\r\n', '', '', '		</div>\r\n		<!-- /CONTENUTI -->\r\n		<div class="clearDiv"></div>\r\n		<div> &nbsp;</div>\r\n	</div>\r\n	<!-- /CONTAINER -->\r\n\r\n	<!-- FOOTER -->\r\n	\r\n\r\n\r\n\r\n<div id="footerCOL" class="box">\r\n	<div class="bgRight">\r\n		<div class="footerRightNav">\r\n			<a href="#" accesskey="a" title="Mappa del Portale">Mappa del Portale</a><span>&nbsp;&nbsp;|&nbsp;</span>\r\n			<a href="#" accesskey="b" title="Note tecniche">Accessibilita''</a><span>&nbsp;&nbsp;|&nbsp;</span>\r\n			<a href="#" accesskey="c" title="Credits">Credits</a>\r\n		</div>\r\n\r\n		<div class="footerLeftNav">\r\n			Comune di Colonnella<span>&nbsp;&nbsp;|&nbsp;</span>\r\n			<a href="#" accesskey="c" title="Contatti">Contatti</a><span>&nbsp;&nbsp;|&nbsp;</span>\r\n			<a href="#" accesskey="n" title="Note legali">Note legali</a><span>&nbsp;&nbsp;|&nbsp;</span>\r\n			<a href="#" accesskey="l" title="Links">Links</a>\r\n\r\n		</div>\r\n	</div>\r\n</div>\r\n\r\n	<p class="hidden">[ <a href="#top" title="Scarica">Torna all''inizio della pagina</a> ]</p>\r\n	<!-- /FOOTER -->\r\n	</h:body>\r\n</f:view>\r\n</html>', '', '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" \r\n    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">\r\n<html xmlns="http://www.w3.org/1999/xhtml"\r\n	xmlns:ui="http://java.sun.com/jsf/facelets"\r\n	xmlns:h="http://java.sun.com/jsf/html"\r\n	xmlns:p="http://primefaces.prime.com.tr/ui"\r\n	xmlns:f="http://java.sun.com/jsf/core"\r\n	xmlns:c="http://java.sun.com/jstl/core"\r\n	xmlns:cc="http://colonnella/taglib"\r\n	xmlns:flw="http://jflower/taglib">\r\n<f:view contentType="text/html">\r\n	<h:head>\r\n\r\n<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>\r\n<meta name="language" content="it" />\r\n<meta name="Keywords" content="Colonnella, Comune di Colonnella, Amministrazione comunale di Colonnella, Municipio Colonnella" />\r\n<meta name="Description" content="Sito ufficiale del Comune di Colonnella" />\r\n\r\n	<title>Comune di Colonnella -  Teramo</title>\r\n	<link rel="stylesheet" href="#{request.contextPath}/css/style_modulistica.css" media="screen"/>\r\n	<link rel="stylesheet" href="#{request.contextPath}/css/home.css" media="screen"/>\r\n	<!--[if IE 7]><link rel="stylesheet" href="#{request.contextPath}/css/ie7.css" media="screen" /><![endif]-->\r\n\r\n	<!--[if IE 6]><link rel="stylesheet" href="#{request.contextPath}/css/ie6.css" media="screen" /><![endif]-->\r\n	<!--[if IE 5]><link rel="stylesheet" href="#{request.contextPath}/css/ie5x.css" media="screen" /><![endif]-->\r\n\r\n\r\n</h:head><h:body>\r\n	\r\n	<!-- CONTAINER -->\r\n	<div id="container">\r\n		<div class="hidden"><a name="top" id="top">XXXXXXXx</a>\r\n			Metanavigazione in questa pagina:\r\n			<ul>\r\n\r\n				<li><a title="Vai al menu principale" href="#navigation">Vai al menu principale</a></li>\r\n				<li><a title="Scarica" href="#mainContent">Vai al contenuto della pagina</a></li>\r\n			</ul>\r\n		</div>\r\n\r\n		<!-- HEADER -->\r\n		\r\n\r\n\r\n<div id="headerGruppo">\r\n	<h1 class="hidden">Comune di Colonnella</h1>\r\n\r\n	\r\n	<div class="headerBottom">\r\n\r\n		<div class="headerBottomLeft">\r\n			<div class="headerBottomLeftIn" style="background: transparent url(#{request.contextPath}/img/modulistica.jpg) no-repeat scroll left bottom; "></div>\r\n		</div>\r\n	</div>\r\n	<div class="clearDiv"></div>\r\n</div>\r\n\r\n		<!-- /HEADER -->\r\n\r\n		<!-- MENU DI PRIMO LIVELLO -->\r\n		\r\n  \r\n\r\n	<div id="navigation">\r\n		<div class="menuInt">\r\n			<div class="menuIntLeftRow"><div class="menuIntRightRow">\r\n				<div class="menuIntTopLeft"><div class="menuIntTopRight">\r\n					<div class="menuIntBotLeft"><div class="menuIntBotRight">\r\n						<h3 class="hidden">Menu principale</h3>\r\n						<ul id="menu1" title="MenÃ¹ di primo livello">\r\n\r\n							<li class="C5cellaprima"><a href="index.html" title="Home" accesskey="1">Home</a></li>	\r\n							<li class="C5cellaunica"><a href="comune.html" title="Comune" accesskey="2">Comune</a></li>	\r\n							<li class="C5cellaunica"><a href="turismo.html" title="Turismo" accesskey="3">Turismo</a></li>	\r\n							<li class="C5cellaunica"><a href="cultura.html" title="Cultura" accesskey="4">Cultura</a></li>	\r\n							<li class="C5cellaultima"><a href="modulistica.html" title="Modulistica" accesskey="5">Modulistica</a></li>	\r\n					</ul>\r\n						<p class="hidden">[ <a title="Scarica" href="#top">Torna all''inizio della pagina</a> ]</p>\r\n\r\n					</div></div>\r\n				</div></div>\r\n			</div></div>\r\n		</div>\r\n	</div>\r\n		<div class="clearDiv"></div>\r\n		<!-- /MENU DI PRIMO LIVELLO -->\r\n\r\n		<div class="clearDiv"></div><!-- importante id="mainContent"-->\r\n\r\n		<!-- CONTENUTI -->\r\n		<div id="content">\r\n\r\n\r\n\r\n', '', 'Due colonne A', b'0');
