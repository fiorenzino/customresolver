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
-- Table structure for table `TemplateImpl`
--

CREATE TABLE IF NOT EXISTS `TemplateImpl` (
  `id` bigint(20) NOT NULL auto_increment,
  `attivo` bit(1) NOT NULL,
  `col1` text,
  `col2` text,
  `col3` text,
  `footer` text,
  `header` text,
  `page_id` varchar(255) default NULL,
  `template_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKCF9A29DAE589CCA9` (`template_id`),
  KEY `FKCF9A29DAFCB8809` (`page_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `TemplateImpl`
--

INSERT INTO `TemplateImpl` (`id`, `attivo`, `col1`, `col2`, `col3`, `footer`, `header`, `page_id`, `template_id`) VALUES
(14, b'1', '				 	<!-- BOX 1 SX -->\r\n\r\n					<div class="contcbox2">\r\n						<div class="cbox2">\r\n							<div class="cbox2LeftRow"><div class="cbox2RightRow">\r\n								<div class="cboxTopLeft"><div class="cboxTopRight">\r\n									<div class="cboxBotLeft"><div class="cboxBotRight">\r\n										<div class="ContBoxCurva">\r\n											<div class="ContBoxRedTitle">\r\n												<div class="BoxRedTitleTop">\r\n													<div class="rbtopl"><div class="rbtopr">\r\n\r\n														<div class="titolo">\r\n															\r\n\r\n	<h3 class="titoloredbold">Modulistica</h3>\r\n\r\n														</div>\r\n													</div></div>\r\n												</div>\r\n											 	<div class="BoxRedTitleBot">\r\n\r\n	<div class="txt">\r\n\r\n		<h4><a href="index.html" title="Ambiente ed Animali" class="arrowLink ">Ambiente ed Animali</a></h4>\r\nInformazioni utili\r\n	</div>\r\n	<div class="RowBot"></div>\r\n	<div class="txt">\r\n		<h4><a href="#" title="Edilizia ed Urbanistica" class="arrowLink ">Edilizia ed Urbanistica</a></h4>\r\nInformazioni utili\r\n	</div>\r\n	<div class="txt">\r\n		<h4><a href="#" title="Cultura Arte e Turismo" class="arrowLink ">Cultura Arte e Turismo</a></h4>\r\n\r\nInformazioni utili\r\n	</div>\r\n<div class="txt">\r\n		<h4><a href="#" title="Commercio Imprese Lavoro" class="arrowLink ">Commercio Imprese Lavoro</a></h4>\r\nInformazioni utili\r\n	</div>\r\n<div class="txt">\r\n		<h4><a href="#" title="Agricoltura" class="arrowLink ">Agricoltura</a></h4>\r\nInformazioni utili\r\n	</div>\r\n<div class="txt">\r\n		<h4><a href="#" title="Identita'' e Cittadinanza" class="arrowLink ">Identita'' e Cittadinanza</a></h4>\r\n\r\n Informazioni utili\r\n	</div>\r\n<div class="txt">\r\n		<h4><a href="#" title="Scuola Istruzione" class="arrowLink ">Scuola Istruzione</a></h4>\r\n Informazioni utili\r\n	</div>\r\n<div class="txt">\r\n		<h4><a href="#" title="Sport e Tempo Libero" class="arrowLink ">Sport e Tempo Libero</a></h4>\r\n Informazioni utili\r\n	</div>\r\n\r\n											 	</div>\r\n											</div>\r\n										</div>\r\n									</div></div>\r\n								</div></div>\r\n							</div></div>\r\n						</div>\r\n					</div>\r\n\r\n					<!-- /BOX 1 SX -->\r\n\r\n					<!-- BOX 2 SX -->\r\n					<div class="contcbox2 top6">\r\n						<div class="cbox2">\r\n							<div class="cbox2LeftRow"><div class="cbox2RightRow">\r\n								<div class="cboxTopLeft"><div class="cboxTopRight">\r\n									<div class="cboxBotLeft"><div class="cboxBotRight">\r\n										<div class="ContBoxCurva">\r\n\r\n											<div class="ContBoxRedTitle">\r\n												<div class="BoxRedTitleTop">\r\n													<div class="rbtopl"><div class="rbtopr">\r\n														<div class="titolo">\r\n															\r\n\r\n	<h3><a href="comune.html" title="Comune">Comune</a></h3>\r\n\r\n														</div>\r\n													</div></div>\r\n												</div>\r\n\r\n											 	<div class="BoxRedTitleBot">\r\n											 		\r\n\r\n\r\n	<div class="txt">					\r\n<strong><a href="#" title="Eventi e Informazioni">Eventi e Informazioni</a></strong> \r\n	</div>\r\n\r\n											 	</div>\r\n											</div>\r\n										</div>\r\n									</div></div>\r\n								</div></div>\r\n\r\n							</div></div>\r\n						</div>\r\n					</div>\r\n					<!-- /BOX 2 SX -->\r\n\r\n					<!-- BOX 2 SX -->\r\n					<div class="contcbox2 top6">\r\n						<div class="cbox2">\r\n							<div class="cbox2LeftRow"><div class="cbox2RightRow">\r\n\r\n								<div class="cboxTopLeft"><div class="cboxTopRight">\r\n									<div class="cboxBotLeft"><div class="cboxBotRight">\r\n										<div class="ContBoxCurva">\r\n											<div class="ContBoxRedTitle">\r\n												<div class="BoxRedTitleTop">\r\n													<div class="rbtopl"><div class="rbtopr">\r\n														<div class="titolo">\r\n															\r\n\r\n	<h3><a href="turismo.html" title="Turismo">Turismo</a></h3>\r\n\r\n														</div>\r\n													</div></div>\r\n												</div>\r\n											 	<div class="BoxRedTitleBot">\r\n											 		\r\n\r\n\r\n	<div class="txt">					\r\n<strong><a href="#" title="Archivio e Informazioni">Archivio e Informazioni</a></strong> \r\n	</div>\r\n\r\n											 	</div>\r\n\r\n											</div>\r\n										</div>\r\n									</div></div>\r\n								</div></div>\r\n							</div></div>\r\n						</div>\r\n					</div>\r\n					<!-- /BOX 2 SX -->\r\n\r\n					<!-- BOX 2 SX -->\r\n					<div class="contcbox2 top6">\r\n						<div class="cbox2">\r\n							<div class="cbox2LeftRow"><div class="cbox2RightRow">\r\n								<div class="cboxTopLeft"><div class="cboxTopRight">\r\n									<div class="cboxBotLeft"><div class="cboxBotRight">\r\n										<div class="ContBoxCurva">\r\n											<div class="ContBoxRedTitle">\r\n												<div class="BoxRedTitleTop">\r\n\r\n													<div class="rbtopl"><div class="rbtopr">\r\n														<div class="titolo">\r\n															\r\n\r\n	<h3><a href="#1" title="Cultura">Cultura</a></h3>\r\n\r\n														</div>\r\n													</div></div>\r\n												</div>\r\n											 	<div class="BoxRedTitleBot">\r\n											 		\r\n\r\n\r\n	<div class="txt">					\r\n\r\n<strong><a title="Scarica" href="#2" >Archivio e informazioni</a></strong> \r\n	</div>\r\n\r\n											 	</div>\r\n											</div>\r\n										</div>\r\n									</div></div>\r\n								</div></div>\r\n							</div></div>\r\n						</div>\r\n\r\n					</div>\r\n					<!-- /BOX 2 SX -->\r\n\r\n					<!-- BOX 2 SX -->\r\n					<div class="contcbox2 top6">\r\n						<div class="cbox2">\r\n							<div class="cbox2LeftRow"><div class="cbox2RightRow">\r\n								<div class="cboxTopLeft"><div class="cboxTopRight">\r\n									<div class="cboxBotLeft"><div class="cboxBotRight">\r\n\r\n										<div class="ContBoxCurva">\r\n											<div class="ContBoxRedTitle">\r\n												<div class="BoxRedTitleTop">\r\n													<div class="rbtopl"><div class="rbtopr">\r\n														<div class="titolo">\r\n															\r\n\r\n	<h3><a href="#3" title="Io sono">Io sono</a></h3>\r\n\r\n														</div>\r\n													</div></div>\r\n\r\n												</div>\r\n											 	<div class="BoxRedTitleBot">\r\n											 		\r\n\r\n\r\n	<div class="txt">					\r\n<strong><a href="#" title="Come cercare informazioni">Come cercare informazioni</a></strong> \r\n	</div>\r\n\r\n											 	</div>\r\n											</div>\r\n										</div>\r\n									</div></div>\r\n\r\n								</div></div>\r\n							</div></div>\r\n						</div>\r\n					</div>\r\n					<!-- /BOX 2 SX -->\r\n					<!-- BOX 2 SX -->\r\n					<div class="contcbox2 top6">\r\n						<div class="cbox2">\r\n							<div class="cbox2LeftRow"><div class="cbox2RightRow">\r\n\r\n								<div class="cboxTopLeft"><div class="cboxTopRight">\r\n									<div class="cboxBotLeft"><div class="cboxBotRight">\r\n										<div class="ContBoxCurva">\r\n											<div class="ContBoxRedTitle">\r\n												<div class="BoxRedTitleTop">\r\n													<div class="rbtopl"><div class="rbtopr">\r\n														<div class="titolo">\r\n															\r\n\r\n	<h3><a href="modulistica.html" title="Mi interessa">Mi interessa</a></h3>\r\n\r\n														</div>\r\n													</div></div>\r\n												</div>\r\n											 	<div class="BoxRedTitleBot">\r\n											 		\r\n\r\n\r\n	<div class="txt">					\r\n<strong><a href="#" title="Come cercare informazioni">Come cercare informazioni</a></strong> \r\n	</div>\r\n\r\n											 	</div>\r\n\r\n											</div>\r\n										</div>\r\n									</div></div>\r\n								</div></div>\r\n							</div></div>\r\n						</div>\r\n					</div>\r\n					<!-- /BOX 2 SX -->\r\n', '	<div class="Box2ImgTitolo">\r\n		<h3 class="titoloredbold">Modulistica</h3>\r\n\r\n		<br />In questa sezione sono raccolti i moduli per richieste all''Amministrazione comunale. Seleziona il modulo di tuo interesse per visualizzarlo sullo schermo.\r\n	</div>\r\n\r\n		                            	\r\n\r\n\r\n	<div class="ContResult">\r\n		<div class="contentable">\r\n\r\n<h:form>\r\n			<cc:moduli selectedStyleClass="selectedStyleClass" 	selectedClass="selectedClass" label="Moduli disponibili" showpages="2" pagesize="5" cellaprimaClass="cellaprima" />\r\n\r\n</h:form>\r\n		</div>\r\n		<div class="clearDiv"></div>\r\n', NULL, NULL, NULL, NULL, 4);
