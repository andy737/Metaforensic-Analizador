CREATE DATABASE  IF NOT EXISTS `metaforensic` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `metaforensic`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: metaforensic
-- ------------------------------------------------------
-- Server version	5.5.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `archivo`
--

DROP TABLE IF EXISTS `archivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `archivo` (
  `id_archivo` varchar(25) NOT NULL,
  `tipo` varchar(20) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `tamano` varchar(20) DEFAULT NULL,
  `tipo_cifrado` varchar(25) DEFAULT NULL,
  `directorio` varchar(300) DEFAULT NULL,
  `fecha_recoleccion` date DEFAULT NULL,
  `hora_recoleccion` time DEFAULT NULL,
  `fecha_descarga` date DEFAULT NULL,
  `hora_descarga` time DEFAULT NULL,
  `id_proyecto` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id_archivo`),
  KEY `archivo_ibfk_1` (`id_proyecto`),
  CONSTRAINT `archivo_ibfk_1` FOREIGN KEY (`id_proyecto`) REFERENCES `proyecto` (`id_proyecto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `archivo`
--

LOCK TABLES `archivo` WRITE;
/*!40000 ALTER TABLE `archivo` DISABLE KEYS */;
/*!40000 ALTER TABLE `archivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evento` (
  `id_evento` varchar(25) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `fecha_evento` date DEFAULT NULL,
  `hora_evento` time DEFAULT NULL,
  PRIMARY KEY (`id_evento`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
INSERT INTO `evento` VALUES ('1','Creacion del proyecto Auditoria_01251242013','2013-04-12','00:01:25'),('10','Creacion del proyecto Auditoria_018371242013','2013-04-12','00:18:37'),('11','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_018371242013','2013-04-12','00:18:37'),('12','Creacion del proyecto Auditoria_025381242013','2013-04-12','00:25:38'),('13','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_025381242013','2013-04-12','00:25:38'),('14','Creacion del proyecto Auditoria_030121242013','2013-04-12','00:30:12'),('15','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_030121242013','2013-04-12','00:30:13'),('16','Creacion del proyecto Auditoria_03331242013','2013-04-12','00:33:03'),('17','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_03331242013','2013-04-12','00:33:03'),('18','Creacion del proyecto Auditoria_042291242013','2013-04-12','00:42:29'),('19','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_042291242013','2013-04-12','00:42:29'),('2','Se inserto el archivo Nuevo documento de texto.afa en el proyecto  Auditoria_01251242013','2013-04-12','00:01:25'),('20','Creacion del proyecto Auditoria_045171242013','2013-04-12','00:45:17'),('21','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_045171242013','2013-04-12','00:45:17'),('22','Creacion del proyecto Auditoria_051111242013','2013-04-12','00:51:11'),('23','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_051111242013','2013-04-12','00:51:11'),('24','Creacion del proyecto Auditoria_053231242013','2013-04-12','00:53:24'),('25','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_053231242013','2013-04-12','00:53:24'),('26','Creacion del proyecto Auditoria_059421242013','2013-04-12','00:59:42'),('27','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_059421242013','2013-04-12','00:59:42'),('28','Creacion del proyecto Auditoria_75051242013','2013-04-12','07:50:05'),('29','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_75051242013','2013-04-12','07:50:06'),('3','Eliminacion del proyecto Auditoria_01251242013','2013-04-12','00:04:23'),('30','Creacion del proyecto Auditoria_753301242013','2013-04-12','07:53:30'),('31','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_753301242013','2013-04-12','07:53:30'),('32','Creacion del proyecto Auditoria_75521242013','2013-04-12','07:55:02'),('33','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_75521242013','2013-04-12','07:55:02'),('34','Creacion del proyecto Auditoria_755241242013','2013-04-12','07:55:24'),('35','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_755241242013','2013-04-12','07:55:25'),('36','Creacion del proyecto Auditoria_75811242013','2013-04-12','07:58:02'),('37','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_75811242013','2013-04-12','07:58:02'),('38','Creacion del proyecto Auditoria_926221242013','2013-04-12','09:26:22'),('39','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_926221242013','2013-04-12','09:26:23'),('4','Eliminacion del proyecto Auditoria_01251242013','2013-04-12','00:04:25'),('40','Creacion del proyecto Auditoria_940131242013','2013-04-12','09:40:14'),('41','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_940131242013','2013-04-12','09:40:14'),('42','Creacion del proyecto Auditoria_940531242013','2013-04-12','09:40:54'),('43','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_940531242013','2013-04-12','09:40:54'),('44','Creacion del proyecto Auditoria_945551242013','2013-04-12','09:45:56'),('45','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_945551242013','2013-04-12','09:45:56'),('46','Creacion del proyecto Auditoria_1031521242013','2013-04-12','10:31:52'),('47','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_1031521242013','2013-04-12','10:31:52'),('48','Creacion del proyecto Auditoria_1122151242013','2013-04-12','11:22:15'),('49','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_1122151242013','2013-04-12','11:22:15'),('5','Eliminacion del proyecto Auditoria_2348461142013','2013-04-12','00:05:34'),('50','Creacion del proyecto Auditoria_124521242013','2013-04-12','12:04:52'),('51','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_124521242013','2013-04-12','12:04:52'),('52','Creacion del proyecto Auditoria_1212231242013','2013-04-12','12:12:23'),('53','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_1212231242013','2013-04-12','12:12:23'),('54','Creacion del proyecto Auditoria_1218221242013','2013-04-12','12:18:22'),('55','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_1218221242013','2013-04-12','12:18:23'),('56','Creacion del proyecto Auditoria_1218401242013','2013-04-12','12:18:40'),('57','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_1218401242013','2013-04-12','12:18:40'),('58','Creacion del proyecto ','2013-04-12','12:40:29'),('59','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  ','2013-04-12','12:40:31'),('6','Creacion del proyecto Auditoria_015331242013','2013-04-12','00:15:33'),('60','Creacion del proyecto Auditoria_1241181242013','2013-04-12','12:41:19'),('61','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_1241181242013','2013-04-12','12:41:19'),('62','Creacion del proyecto Auditoria_1243301242013','2013-04-12','12:43:30'),('63','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_1243301242013','2013-04-12','12:43:31'),('64','Creacion del proyecto Auditoria_1314591242013','2013-04-12','13:14:59'),('65','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_1314591242013','2013-04-12','13:14:59'),('66','Creacion del proyecto Auditoria_1336161242013','2013-04-12','13:36:16'),('67','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_1336161242013','2013-04-12','13:36:16'),('68','Creacion del proyecto Auditoria_1338551242013','2013-04-12','13:38:55'),('69','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_1338551242013','2013-04-12','13:38:56'),('7','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_015331242013','2013-04-12','00:15:33'),('70','Creacion del proyecto Auditoria_1346391242013','2013-04-12','13:46:39'),('71','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_1346391242013','2013-04-12','13:46:39'),('72','Creacion del proyecto Auditoria_1350311242013','2013-04-12','13:50:31'),('73','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_1350311242013','2013-04-12','13:50:31'),('74','Creacion del proyecto Auditoria_1741191242013','2013-04-12','17:41:20'),('75','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_1741191242013','2013-04-12','17:41:20'),('76','Creacion del proyecto Auditoria_194351242013','2013-04-12','19:04:35'),('77','Se inserto el archivo \"20130412_1844423_andy737-1\".afa en el proyecto  Auditoria_194351242013','2013-04-12','19:04:36'),('78','Creacion del proyecto Auditoria_1925231242013','2013-04-12','19:25:23'),('79','Se inserto el archivo \"20130412_192258602_andy737-1\".afa en el proyecto  Auditoria_1925231242013','2013-04-12','19:25:23'),('8','Creacion del proyecto Auditoria_015561242013','2013-04-12','00:15:56'),('80','Creacion del proyecto Auditoria_194931242013','2013-04-12','19:49:03'),('81','Se inserto el archivo \"20130412_19443627_andy737-1\".afa en el proyecto  Auditoria_194931242013','2013-04-12','19:49:04'),('9','Se inserto el archivo \"Nuevo documento de texto\".afa en el proyecto  Auditoria_015561242013','2013-04-12','00:15:57');
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proyecto`
--

DROP TABLE IF EXISTS `proyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proyecto` (
  `id_proyecto` varchar(25) NOT NULL,
  `nombre` varchar(35) DEFAULT NULL,
  `descripcion` varchar(150) DEFAULT NULL,
  `autor` varchar(100) DEFAULT NULL,
  `fecha_creacion` date DEFAULT NULL,
  `hora_creacion` time DEFAULT NULL,
  PRIMARY KEY (`id_proyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proyecto`
--

LOCK TABLES `proyecto` WRITE;
/*!40000 ALTER TABLE `proyecto` DISABLE KEYS */;
/*!40000 ALTER TABLE `proyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proyecto_genera_evento`
--

DROP TABLE IF EXISTS `proyecto_genera_evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proyecto_genera_evento` (
  `id_proyecto` varchar(25) NOT NULL,
  `id_evento` varchar(25) NOT NULL,
  PRIMARY KEY (`id_proyecto`,`id_evento`),
  KEY `proyecto_genera_evento_ibfk_2` (`id_evento`),
  CONSTRAINT `proyecto_genera_evento_ibfk_1` FOREIGN KEY (`id_proyecto`) REFERENCES `proyecto` (`id_proyecto`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `proyecto_genera_evento_ibfk_2` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id_evento`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proyecto_genera_evento`
--

LOCK TABLES `proyecto_genera_evento` WRITE;
/*!40000 ALTER TABLE `proyecto_genera_evento` DISABLE KEYS */;
/*!40000 ALTER TABLE `proyecto_genera_evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'metaforensic'
--
/*!50003 DROP PROCEDURE IF EXISTS `consulta_proyecto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `consulta_proyecto`(in id varchar(25))
begin
select * from proyecto inner join archivo on proyecto.id_proyecto=archivo.id_proyecto where proyecto.id_proyecto=id;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consulta_proyecto_even` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `consulta_proyecto_even`(in id varchar(25))
begin
select evento.id_evento, evento.descripcion, fecha_evento, hora_evento from proyecto inner join proyecto_genera_evento on proyecto.id_proyecto=proyecto_genera_evento.id_proyecto inner join evento on proyecto_genera_evento.id_evento=evento.id_evento where proyecto.id_proyecto=id;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consulta_proyecto_evenAll` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `consulta_proyecto_evenAll`()
begin
select  evento.id_evento, evento.descripcion, fecha_evento, hora_evento from proyecto inner join proyecto_genera_evento on proyecto.id_proyecto=proyecto_genera_evento.id_proyecto inner join evento on proyecto_genera_evento.id_evento=evento.id_evento;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `consulta_proyecto_gen` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `consulta_proyecto_gen`()
begin
select * from proyecto inner join archivo on proyecto.id_proyecto=archivo.id_proyecto;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `eliminar_proyecto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `eliminar_proyecto`(in id varchar(25))
begin
delete from proyecto where id_proyecto=id;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `genera_even_deleteproy` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `genera_even_deleteproy`(in id1 varchar(25))
begin
declare cont int;
declare id int;
set cont=(select count(id_evento) from evento);
if cont=0 then
set id= 1;
else
set id=cont+1;
end if;
insert into evento values(id, concat("Eliminacion del proyecto"," ",id1), curdate(), curtime());
insert into proyecto_genera_evento values(id1,id);
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `genera_even_insertproy` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `genera_even_insertproy`(in id1 varchar(25))
begin
declare cont int;
declare id int;
set cont=(select count(id_evento) from evento);
if cont=0 then
set id= 1;
else
set id=cont+1;
end if;
insert into evento values(id, concat("Creacion del proyecto"," ",id1), curdate(), curtime());
insert into proyecto_genera_evento values(id1,id);
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `genera_even_universal` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `genera_even_universal`(in id1 varchar(25), msg varchar(100))
begin
declare cont int;
declare id int;
set cont=(select count(id_evento) from evento);
if cont=0 then
set id= 1;
else
set id=cont+1;
end if;
insert into evento values(id, concat(msg," ",id1), curdate(), curtime());
insert into proyecto_genera_evento values(id1,id);
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertar_archivo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `insertar_archivo`(in tipo1 varchar(20), nombre1 varchar(255), tamano1 varchar(20), tipo_cifrado1 varchar(25),directorio1 varchar(300), fecha_recoleccion1 date, hora_recoleccion1 time, id_proyecto1 varchar(25))
begin
declare id1 varchar(25);
set id1=concat(tipo1,hour(curtime()),minute(curtime()),second(curtime()),day(curdate()),month(curdate()),year(curdate()));
insert into archivo values(id1, tipo1, nombre1, tamano1 , tipo_cifrado1 , directorio1, fecha_recoleccion1, hora_recoleccion1, curdate(), curtime(), id_proyecto1);
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertar_proyecto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `insertar_proyecto`(in nombre1 varchar(35), descripcion1 varchar(150), autor1 varchar(100))
begin
declare id1 varchar(25);
set id1 = concat("Auditoria_",hour(curtime()),minute(curtime()),second(curtime()),day(curdate()),month(curdate()),year(curdate()));
insert into proyecto values(id1, nombre1, descripcion1, autor1, curdate(), curtime());
select id_proyecto from proyecto where id_proyecto=id1;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `llenar_combo_proyecto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `llenar_combo_proyecto`()
begin
select id_proyecto from proyecto;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ruta_archivo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `ruta_archivo`(in id varchar(25))
begin
select concat(directorio,nombre,".",tipo) from archivo where id_proyecto=id;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-04-13 10:32:05
