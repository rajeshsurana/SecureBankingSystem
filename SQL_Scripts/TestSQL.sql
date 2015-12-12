-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: southwesttech
-- ------------------------------------------------------
-- Server version	5.6.17

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
  `userName` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `IPAddress` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `deviceId` varchar(2000) CHARACTER SET utf8 DEFAULT NULL,
  `accessedOn` datetime DEFAULT NULL,
  `suspiciousActivity` int(11) DEFAULT NULL,
  PRIMARY KEY (`accessLogId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accesslog`
--

LOCK TABLES `accesslog` WRITE;
/*!40000 ALTER TABLE `accesslog` DISABLE KEYS */;
/*!40000 ALTER TABLE `accesslog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bankaccount`
--

DROP TABLE IF EXISTS `bankaccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bankaccount` (
  `bankAccountId` int(11) NOT NULL AUTO_INCREMENT,
  `bankUserId` int(11) NOT NULL,
  `refAccountTypeId` int(11) NOT NULL,
  `balance` float DEFAULT NULL,
  `debitLimit` float DEFAULT NULL,
  `creditLimit` float DEFAULT NULL,
  `accountCreatedOn` datetime DEFAULT NULL,
  `accountLastAccessedOn` datetime DEFAULT NULL,
  PRIMARY KEY (`bankAccountId`),
  KEY `bankUserId` (`bankUserId`),
  KEY `refAccountTypeId` (`refAccountTypeId`),
  CONSTRAINT `bankaccount_ibfk_1` FOREIGN KEY (`bankUserId`) REFERENCES `bankuser` (`bankUserId`),
  CONSTRAINT `bankaccount_ibfk_2` FOREIGN KEY (`refAccountTypeId`) REFERENCES `refaccounttype` (`refAccountTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bankaccount`
--

LOCK TABLES `bankaccount` WRITE;
/*!40000 ALTER TABLE `bankaccount` DISABLE KEYS */;
INSERT INTO `bankaccount` VALUES (1,1,1,50000,50,100000,NULL,NULL),(2,1,2,30000,50,100000,NULL,NULL),(3,2,1,70000,50,100000,NULL,NULL),(4,2,2,20000,50,100000,NULL,NULL);
/*!40000 ALTER TABLE `bankaccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banktransaction`
--

DROP TABLE IF EXISTS `banktransaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `banktransaction` (
  `bankTransactionId` int(11) NOT NULL AUTO_INCREMENT,
  `transactionRecipientId` int(11) DEFAULT NULL,
  `transactionBenefactorId` int(11) DEFAULT NULL,
  `transactionAmount` float DEFAULT NULL,
  `transactionAuthorizerUserId` int(11) DEFAULT NULL,
  `transactionApprovedOn` datetime DEFAULT NULL,
  `refTransactionStatusId` int(11) DEFAULT NULL,
  PRIMARY KEY (`bankTransactionId`),
  KEY `transactionRecipientId` (`transactionRecipientId`),
  KEY `transactionBenefactorId` (`transactionBenefactorId`),
  KEY `refTransactionStatusId` (`refTransactionStatusId`),
  CONSTRAINT `banktransaction_ibfk_1` FOREIGN KEY (`transactionRecipientId`) REFERENCES `bankuser` (`bankUserId`),
  CONSTRAINT `banktransaction_ibfk_2` FOREIGN KEY (`transactionBenefactorId`) REFERENCES `bankuser` (`bankUserId`),
  CONSTRAINT `banktransaction_ibfk_3` FOREIGN KEY (`refTransactionStatusId`) REFERENCES `reftransactionstatus` (`refTransactionStatusId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banktransaction`
--

LOCK TABLES `banktransaction` WRITE;
/*!40000 ALTER TABLE `banktransaction` DISABLE KEYS */;
INSERT INTO `banktransaction` VALUES (1,NULL,1,400,NULL,NULL,0),(2,1,NULL,400,NULL,NULL,0),(3,2,1,20000,NULL,NULL,0),(4,2,1,4000,NULL,NULL,0),(5,2,1,500,NULL,NULL,0),(6,2,1,400,NULL,NULL,0),(7,2,1,4000,NULL,NULL,0),(8,2,1,798,NULL,NULL,0),(9,2,1,798,NULL,NULL,0);
/*!40000 ALTER TABLE `banktransaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bankuser`
--

DROP TABLE IF EXISTS `bankuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bankuser` (
  `bankUserId` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) CHARACTER SET utf8 NOT NULL,
  `phoneNumber` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `userName` varchar(100) CHARACTER SET utf8 NOT NULL,
  `userPassword` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `firstName` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `lastName` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `SSN` varchar(12) CHARACTER SET utf8 DEFAULT NULL,
  `residentialAddress` varchar(1000) CHARACTER SET utf8 DEFAULT NULL,
  `mailingAddress` varchar(1000) CHARACTER SET utf8 DEFAULT NULL,
  `userCreatedOn` datetime DEFAULT NULL,
  `lastLoginOn` datetime DEFAULT NULL,
  `refUserRoleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`bankUserId`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `userName` (`userName`),
  KEY `refUserRoleId` (`refUserRoleId`),
  CONSTRAINT `bankuser_ibfk_1` FOREIGN KEY (`refUserRoleId`) REFERENCES `refuserrole` (`refUserRoleId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bankuser`
--

LOCK TABLES `bankuser` WRITE;
/*!40000 ALTER TABLE `bankuser` DISABLE KEYS */;
INSERT INTO `bankuser` VALUES (1,'rajeshsurana1470@gmail.com','4802898922','rsurana','rajeshsurana','Rajesh','Surana',NULL,NULL,NULL,NULL,NULL,NULL,5),(2,'mr.rajeshsurana@gmail.com','4802898922','rajeshsurana','rsurana','Rajesh','Surana',NULL,NULL,NULL,NULL,NULL,NULL,5);
/*!40000 ALTER TABLE `bankuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refaccounttype`
--

DROP TABLE IF EXISTS `refaccounttype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `refaccounttype` (
  `refAccountTypeId` int(11) NOT NULL AUTO_INCREMENT,
  `accountTypeDescription` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`refAccountTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refaccounttype`
--

LOCK TABLES `refaccounttype` WRITE;
/*!40000 ALTER TABLE `refaccounttype` DISABLE KEYS */;
INSERT INTO `refaccounttype` VALUES (1,'Checking'),(2,'Saving');
/*!40000 ALTER TABLE `refaccounttype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reftransactionstatus`
--

DROP TABLE IF EXISTS `reftransactionstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reftransactionstatus` (
  `refTransactionStatusId` int(11) NOT NULL AUTO_INCREMENT,
  `transactionStatusDescription` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`refTransactionStatusId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reftransactionstatus`
--

LOCK TABLES `reftransactionstatus` WRITE;
/*!40000 ALTER TABLE `reftransactionstatus` DISABLE KEYS */;
INSERT INTO `reftransactionstatus` VALUES (0,'Submitted'),(1,'Validated'),(2,'NonValidated'),(3,'Approved'),(4,'Denied');
/*!40000 ALTER TABLE `reftransactionstatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refuserrole`
--

DROP TABLE IF EXISTS `refuserrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `refuserrole` (
  `refUserRoleId` int(11) NOT NULL AUTO_INCREMENT,
  `bankUserRoleName` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`refUserRoleId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refuserrole`
--

LOCK TABLES `refuserrole` WRITE;
/*!40000 ALTER TABLE `refuserrole` DISABLE KEYS */;
INSERT INTO `refuserrole` VALUES (1,'Admin'),(2,'Manager'),(3,'Employee'),(4,'Merchant'),(5,'Customer');
/*!40000 ALTER TABLE `refuserrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userverification`
--

DROP TABLE IF EXISTS `userverification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userverification` (
  `userVerificationId` int(11) NOT NULL AUTO_INCREMENT,
  `verifyingUserName` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `verificationCode` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `codeExpiresOn` datetime DEFAULT NULL,
  PRIMARY KEY (`userVerificationId`),
  KEY `verifyingUserName` (`verifyingUserName`),
  CONSTRAINT `userverification_ibfk_1` FOREIGN KEY (`verifyingUserName`) REFERENCES `bankuser` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userverification`
--

LOCK TABLES `userverification` WRITE;
/*!40000 ALTER TABLE `userverification` DISABLE KEYS */;
INSERT INTO `userverification` VALUES (1,'rsurana','RLjVco','2015-10-16 15:20:59'),(2,'rsurana','qVU4xh','2015-10-17 15:59:43'),(3,'rsurana','cHwjTM','2015-10-17 16:41:48'),(4,'rsurana','L4xwsy','2015-10-17 16:50:47'),(5,'rsurana','NUu7Z2','2015-10-17 16:54:44'),(6,'rsurana','MHyoiy','2015-10-17 17:14:04'),(7,'rsurana','PDwnQU','2015-10-17 17:15:07'),(8,'rajeshsurana','M6gw4E','2015-10-17 17:17:13'),(9,'rajeshsurana','DtZdMp','2015-10-17 17:22:51');
/*!40000 ALTER TABLE `userverification` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-17 17:56:15
