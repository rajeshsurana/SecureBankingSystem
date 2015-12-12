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
-- Table structure for table `bankuser`
--

DROP TABLE IF EXISTS `bankuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bankuser` (
  `bankUserId` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `phoneNumber` varchar(100) DEFAULT NULL,
  `userName` varchar(100) NOT NULL,
  `userPassword` varchar(100) DEFAULT NULL,
  `firstName` varchar(200) DEFAULT NULL,
  `lastName` varchar(200) DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `SSN` varchar(12) DEFAULT NULL,
  `residentialAddress` varchar(1000) DEFAULT NULL,
  `mailingAddress` varchar(1000) DEFAULT NULL,
  `userCreatedOn` datetime DEFAULT NULL,
  `lastLoginOn` datetime DEFAULT NULL,
  `refUserRoleId` int(11) DEFAULT NULL,
  `accountStatus` varchar(45) NOT NULL,
  `personalBanker` varchar(45) DEFAULT NULL,
  `piiAuthorization` varchar(45) NOT NULL DEFAULT 'Not_Authorized',
  PRIMARY KEY (`bankUserId`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `userName` (`userName`),
  KEY `refUserRoleId` (`refUserRoleId`),
  CONSTRAINT `bankuser_ibfk_1` FOREIGN KEY (`refUserRoleId`) REFERENCES `refuserrole` (`refUserRoleId`)
) ENGINE=InnoDB AUTO_INCREMENT=987481 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bankuser`
--

LOCK TABLES `bankuser` WRITE;
/*!40000 ALTER TABLE `bankuser` DISABLE KEYS */;
INSERT INTO `bankuser` VALUES (987474,'mshakthi@asu.edu','654789123','government','government','Government','Government','1957-08-15','54313218','white house','white house','2015-10-19 00:00:00',NULL,6,'Active',NULL,'Authorized'),(987475,'mahathi_1990@yahoo.com','1231548789','sysadmin','sysadmin','System','Administrator','2015-10-19','36985214','SouthwestBank','SouthwestBank','2015-10-24 00:00:00',NULL,1,'Active',NULL,'Authorized'),(987476,'mahathi2606@gmail.com','1232656','mahathis','mahathis1234','Mahathi','Shakthidharan','1990-06-26','12311564','1265 East University Drive','1265 East University Drive','2015-10-25 02:55:46',NULL,2,'Active',NULL,'Not_Authorized'),(987477,'mahathi.shakthi@gmail.com','615645648','raghavs','raghavs1234','Raghav','Shakthidharan','1993-12-19','522665569','32480,Willoughby roaad','32480,Willoughby roaad','2015-10-25 03:02:29',NULL,3,'Active',NULL,'Not_Authorized'),(987478,'chithras.1963@gmail.com','484561','chithras','chithras1234','Chithra','Shakthidharan','1963-05-23','56465121','32480,Willoughby roaad','32480,Willoughby roaad','2015-10-25 03:07:11',NULL,5,'Active','raghavs','Not_Authorized'),(987480,'unity626@gmail.com','98878456','amazons','amazons1234','Amazon','Jeff','1989-03-02','78965412','Fairview Ave seattle','Fairview Ave seattle','2015-10-25 12:33:50',NULL,4,'Active','raghavs','Not_Authorized');
/*!40000 ALTER TABLE `bankuser` ENABLE KEYS */;
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
