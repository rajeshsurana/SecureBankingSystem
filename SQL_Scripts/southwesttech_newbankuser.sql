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
-- Table structure for table `newbankuser`
--

DROP TABLE IF EXISTS `newbankuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `newbankuser` (
  `userName` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `userPassword` varchar(100) DEFAULT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `SSN` varchar(12) DEFAULT NULL,
  `residentialAddress` varchar(1000) DEFAULT NULL,
  `mailingAddress` varchar(1000) DEFAULT NULL,
  `phoneNumber` varchar(100) DEFAULT NULL,
  `userCreatedOn` datetime DEFAULT NULL,
  `refUserRoleId` int(11) DEFAULT NULL,
  `accountStatus` varchar(45) NOT NULL,
  `assignedToManager` varchar(45) NOT NULL,
  PRIMARY KEY (`userName`),
  KEY `refUserRoleId_idx` (`refUserRoleId`),
  CONSTRAINT `refUserRoleId` FOREIGN KEY (`refUserRoleId`) REFERENCES `refuserrole` (`refUserRoleId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `newbankuser`
--

LOCK TABLES `newbankuser` WRITE;
/*!40000 ALTER TABLE `newbankuser` DISABLE KEYS */;
INSERT INTO `newbankuser` VALUES ('amazons','unity626@gmail.com','amazons1234','Amazon','Jeff','1989-03-02','78965412','Fairview Ave seattle','Fairview Ave seattle','98878456','2015-10-25 12:33:50',4,'managerApproved','mahathis'),('chithras','chithras.1963@gmail.com','chithras1234','Chithra','Shakthidharan','1963-05-23','56465121','32480,Willoughby roaad','32480,Willoughby roaad','484561','2015-10-25 03:07:11',5,'managerApproved','mahathis');
/*!40000 ALTER TABLE `newbankuser` ENABLE KEYS */;
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
