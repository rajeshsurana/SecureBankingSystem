-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: southwesttech
-- ------------------------------------------------------
-- Server version	5.6.26-log

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
-- Table structure for table `accesslog`
--

DROP TABLE IF EXISTS `accesslog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accesslog` (
  `accessLogId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(100) DEFAULT NULL,
  `IPAddress` varchar(100) DEFAULT NULL,
  `deviceId` varchar(2000) DEFAULT NULL,
  `accessedOn` datetime DEFAULT NULL,
  `suspiciousActivity` int(11) DEFAULT NULL,
  PRIMARY KEY (`accessLogId`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accesslog`
--

LOCK TABLES `accesslog` WRITE;
/*!40000 ALTER TABLE `accesslog` DISABLE KEYS */;
INSERT INTO `accesslog` VALUES (1,'chithras','192.168.56.1','0A-00-27-00-00-00','2015-10-24 13:43:15',1),(2,'chithras','192.168.56.1','0A-00-27-00-00-00','2015-10-24 13:43:22',5),(3,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 14:01:32',0),(4,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-24 14:03:44',0),(5,'chithras','192.168.56.1','0A-00-27-00-00-00','2015-10-24 14:05:34',5),(6,'chithras','192.168.56.1','0A-00-27-00-00-00','2015-10-24 14:34:06',0),(7,'chithras','192.168.56.1','0A-00-27-00-00-00','2015-10-24 14:35:41',1),(8,'chithras','192.168.56.1','0A-00-27-00-00-00','2015-10-24 14:35:46',0),(9,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 14:41:42',0),(10,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 14:43:19',0),(11,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 14:56:31',0),(12,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 15:03:11',0),(13,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 15:14:45',0),(14,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 15:16:21',0),(15,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 15:30:07',1),(16,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 15:30:13',0),(17,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 15:36:09',0),(18,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 15:36:54',0),(19,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 15:44:38',0),(20,'chithras','192.168.56.1','0A-00-27-00-00-00','2015-10-24 15:47:23',0),(21,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 15:55:27',0),(22,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 16:09:20',0),(23,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 16:11:15',0),(24,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 16:25:10',0),(25,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 17:30:51',0),(26,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-24 17:31:59',0),(27,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 17:32:31',0),(28,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 17:34:10',1),(29,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 17:34:14',0),(30,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 17:37:59',0),(31,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 17:44:00',0),(32,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-24 17:45:12',1),(33,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-24 17:45:17',0),(34,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-24 17:48:14',0),(35,'chithras','192.168.56.1','0A-00-27-00-00-00','2015-10-24 17:50:04',5),(36,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-24 18:35:32',0),(37,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-24 18:37:19',0),(38,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-24 18:54:07',0),(39,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-24 18:58:35',0),(40,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-24 19:00:16',0),(41,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-24 19:12:02',0),(42,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 19:51:55',0),(43,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 19:52:51',0),(44,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 20:06:27',1),(45,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 20:06:34',0),(46,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 20:06:49',1),(47,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 20:06:53',0),(48,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 20:07:09',1),(49,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 20:07:15',0),(50,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 20:11:40',1),(51,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 20:11:45',0),(52,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 20:12:01',1),(53,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 20:12:07',0),(54,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 20:12:22',1),(55,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 20:12:25',0),(56,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 21:02:04',0),(57,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 21:07:06',0),(58,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-24 21:22:30',0),(59,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-24 21:23:44',0),(60,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-24 21:24:06',1),(61,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-24 21:24:11',1),(62,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-24 21:24:19',0),(63,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-24 21:45:52',0),(64,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-24 22:40:41',0),(65,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 22:42:40',0),(66,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-24 22:43:03',0),(67,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-24 22:48:16',0),(68,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-24 22:57:24',0),(69,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-24 22:57:45',1),(70,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-24 22:57:50',0),(71,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-24 23:36:54',0),(72,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-24 23:44:44',0),(73,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-25 00:06:17',0),(74,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-25 00:07:53',0),(75,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-25 00:08:38',0),(76,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-25 00:10:30',0),(77,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-25 00:51:48',0),(78,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-25 00:52:22',1),(79,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-25 00:52:26',0),(80,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-25 01:01:55',0),(81,'chithras','192.168.56.1','0A-00-27-00-00-00','2015-10-25 01:02:38',0),(82,'government','192.168.56.1','0A-00-27-00-00-00','2015-10-25 02:30:04',0),(83,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-25 02:30:47',0),(84,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-25 02:36:09',0),(85,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-25 02:46:21',0),(86,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-25 02:58:27',5),(87,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-25 03:00:16',0),(88,'sysadmin','192.168.56.1','0A-00-27-00-00-00','2015-10-25 03:01:27',0),(89,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-25 03:03:07',5),(90,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-25 03:04:34',0),(91,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-25 03:10:57',0),(92,'chithras','192.168.56.1','0A-00-27-00-00-00','2015-10-25 03:12:32',5),(93,'chithras','192.168.56.1','0A-00-27-00-00-00','2015-10-25 03:15:17',0),(94,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-25 03:18:42',0),(95,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-25 12:15:20',3),(96,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-25 12:15:30',0),(97,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-25 12:31:36',3),(98,'raghavs','192.168.56.1','0A-00-27-00-00-00','2015-10-25 12:31:47',0),(99,'mahathis','192.168.56.1','0A-00-27-00-00-00','2015-10-25 12:34:26',0),(100,'amazons','192.168.56.1','0A-00-27-00-00-00','2015-10-25 12:38:34',5),(101,'amazons','192.168.56.1','0A-00-27-00-00-00','2015-10-25 12:40:15',0);
/*!40000 ALTER TABLE `accesslog` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-25 13:43:38
