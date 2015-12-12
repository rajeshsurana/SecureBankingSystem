-- Create schema
CREATE DATABASE IF NOT EXISTS southwesttech;

-- Select schema
USE southwesttech;
DROP TABLE IF EXISTS `accesslog`;
DROP TABLE IF EXISTS `userverification`;
DROP TABLE IF EXISTS `banktransaction`;
DROP TABLE IF EXISTS `bankaccount`;
DROP TABLE IF EXISTS `newbankuser`;
DROP TABLE IF EXISTS `bankuser`;
DROP TABLE IF EXISTS `refuserrole`;
DROP TABLE IF EXISTS `refaccounttype`;
DROP TABLE IF EXISTS `reftransactionstatus`;
-- DROP TABLE IF EXISTS `ipSession`;
DROP TABLE IF EXISTS `pkiverification`;


-- Create tables
/*
CREATE TABLE `ipSession` (
	`sessionId` int(6) NOT NULL AUTO_INCREMENT,
    `macAddress` varchar(50),
    PRIMARY KEY(`sessionId`)
);
*/

CREATE TABLE `refuserrole` (
  `refUserRoleId` int(11) NOT NULL AUTO_INCREMENT,
  `bankUserRoleName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`refUserRoleId`)
);

CREATE TABLE `refaccounttype` (
  `refAccountTypeId` int(11) NOT NULL AUTO_INCREMENT,
  `accountTypeDescription` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`refAccountTypeId`)
);

CREATE TABLE `reftransactionstatus` (
  `refTransactionStatusId` int(11) NOT NULL AUTO_INCREMENT,
  `transactionStatusDescription` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`refTransactionStatusId`)
);

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
  CONSTRAINT `bankuser_ibfk_1` 
	FOREIGN KEY (`refUserRoleId`) 
    REFERENCES `refuserrole` (`refUserRoleId`)
);

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
  CONSTRAINT `bankaccount_ibfk_1` 
	FOREIGN KEY (`bankUserId`) 
    REFERENCES `bankuser` (`bankUserId`),
  CONSTRAINT `bankaccount_ibfk_2` 
	FOREIGN KEY (`refAccountTypeId`) 
    REFERENCES `refaccounttype` (`refAccountTypeId`)
);

CREATE TABLE `banktransaction` (
  `bankTransactionId` int(11) NOT NULL AUTO_INCREMENT,
  `transactionRecipientId` int(11) NOT NULL,
  `transactionBenefactorId` int(11) NOT NULL,
  `transactionAmount` float DEFAULT NULL,
  `transactionAuthorizerUserId` int(11) DEFAULT NULL,
  `transactionApprovedOn` datetime DEFAULT NULL,
  `refTransactionStatusId` int(11) DEFAULT NULL,
  PRIMARY KEY (`bankTransactionId`),
  KEY `transactionRecipientId` (`transactionRecipientId`),
  KEY `transactionBenefactorId` (`transactionBenefactorId`),
  KEY `refTransactionStatusId` (`refTransactionStatusId`),
  CONSTRAINT `banktransaction_ibfk_1` 
	FOREIGN KEY (`transactionRecipientId`) 
    REFERENCES `bankuser` (`bankUserId`),
  CONSTRAINT `banktransaction_ibfk_2` 
	FOREIGN KEY (`transactionBenefactorId`) 
    REFERENCES `bankuser` (`bankUserId`),
  CONSTRAINT `banktransaction_ibfk_3` 
	FOREIGN KEY (`refTransactionStatusId`) 
    REFERENCES `reftransactionstatus` (`refTransactionStatusId`)
);

CREATE TABLE `accesslog` (
  `accessLogId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(100) DEFAULT NULL,
  `IPAddress` varchar(100) DEFAULT NULL,
  `deviceId` varchar(2000) DEFAULT NULL,
  `accessedOn` datetime DEFAULT NULL,
  `suspiciousActivity` int(11) DEFAULT NULL,
  PRIMARY KEY (`accessLogId`)
);

CREATE TABLE `userverification` (
  `userVerificationId` int(11) NOT NULL AUTO_INCREMENT,
  `verifyingUserName` varchar(100) DEFAULT NULL,
  `verificationCode` varchar(20) DEFAULT NULL,
  `codeExpiresOn` datetime DEFAULT NULL,
  PRIMARY KEY (`userVerificationId`),
  KEY `verifyingUserName` (`verifyingUserName`),
  CONSTRAINT `userverification_ibfk_1` 
	FOREIGN KEY (`verifyingUserName`) 
    REFERENCES `bankuser` (`userName`)
);

CREATE TABLE `newbankuser` (
  `userName` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `userPassword` varchar(100) DEFAULT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `dateOfBirth` datetime DEFAULT NULL,
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
  CONSTRAINT `refUserRoleId` 
	FOREIGN KEY (`refUserRoleId`) 
    REFERENCES `refuserrole` (`refUserRoleId`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `pkiverification` (
  `username` varchar(45) NOT NULL,
  `userPublicKeyModulus` varchar(2048) DEFAULT NULL,
  `userPublicKeyExponent` varchar(2048) DEFAULT NULL,
  `userVerificationCode` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`username`)
);
-- End table creation

-- Create insert/update trigger to avoid negative balance
DELIMITER $$
-- Drop if insert trigger already exists
DROP TRIGGER IF EXISTS balance_insert_trigger
$$
-- Insert trigger for inserting accounts with negative balance
CREATE TRIGGER balance_insert_trigger
     BEFORE INSERT ON `southwesttech`.`bankaccount` FOR EACH ROW
     BEGIN
          IF NEW.balance < 0
          THEN
               SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Cannot add/update with negative balance';
          END IF;
     END;
$$
-- Drop if update trigger exists
DROP TRIGGER IF EXISTS balance_update_trigger
$$
-- Insert trigger for updating accounts with negative balance
CREATE TRIGGER balance_update_trigger
     BEFORE UPDATE ON `southwesttech`.`bankaccount`  FOR EACH ROW
     BEGIN
          IF NEW.balance < 0 
          THEN
               SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Cannot add/update with negative balance';
          END IF;
     END;
$$
DELIMITER ;

-- Create insert/update trigger to avoid negative/zero transfers
DELIMITER $$
-- Drop if insert trigger already exists
DROP TRIGGER IF EXISTS transfer_insert_trigger
$$
-- Insert trigger for inserting transfers with negative/zero amount
CREATE TRIGGER transfer_insert_trigger
     BEFORE INSERT ON `southwesttech`.`banktransaction` FOR EACH ROW
     BEGIN
          IF NEW.transactionAmount <= 0
          THEN
               SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Cannot add/update with negative/zero amount trasnfers';
          END IF;
     END;
$$
-- Drop if update trigger exists
DROP TRIGGER IF EXISTS trasnfer_update_trigger
$$
-- Insert trigger for updating accounts with negative/zero transfers
CREATE TRIGGER trasnfer_update_trigger
     BEFORE UPDATE ON `southwesttech`.`banktransaction` FOR EACH ROW
     BEGIN
          IF NEW.transactionAmount <= 0 
          THEN
               SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Cannot add/update with negative/zero amount transfers';
          END IF;
     END;
$$
DELIMITER ;


-- Reference table values
INSERT INTO `southwesttech`.`refuserrole` VALUES (1,'Admin'),(2,'Manager'),(3,'Employee'),(4,'Merchant'),(5,'Customer'),(6,'Government');

SET SESSION SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
INSERT INTO `southwesttech`.`reftransactionstatus` VALUES (0,'Submitted'),(1,'Validated'),(2,'NonValidated'),(3,'Approved'),(4,'Denied');
-- UPDATE `southwesttech`.`reftransactionstatus` SET refTransactionStatusId = 0 WHERE refTransactionStatusId = 6;
-- ALTER TABLE `southwesttech`.`reftransactionstatus` AUTO_INCREMENT = 0;

INSERT INTO `southwesttech`.`refaccounttype` VALUES (1,'Checking'),(2,'Saving');

INSERT INTO `southwesttech`.`bankuser` (`email`, `userName`, `userPassword`, `refUserRoleId`, `accountStatus`) 
VALUES ('mahathi_1990@yahoo.com', 'sysadmin', '$2a$12$.8t8jEQIHkpv/EYRRG.qFu/nr5TV9M8JrYoSL1Q.9dRAeB48w89yu', 1, 'Active');

INSERT INTO `southwesttech`.`bankuser` (`email`, `userName`, `userPassword`, `refUserRoleId`, `accountStatus`) 
VALUES ('southwestbank.tech@gmail.com', 'transactionBot', '$2a$12$.8t8jEQIHkpv/EYRRG.qFu/nr5TV9M8JrYoSL1Q.9dRAeB48w89yu', 1, 'Inactive');

INSERT INTO `southwesttech`.`bankuser` (`email`, `userName`, `userPassword`, `refUserRoleId`, `accountStatus`) VALUES ('gov@mail.com', 'gov', '$2a$12$.8t8jEQIHkpv/EYRRG.qFu/nr5TV9M8JrYoSL1Q.9dRAeB48w89yu', '6', 'Active');

/*
INSERT INTO `southwesttech`.`bankuser` (`email`, `userName`, `userPassword`, `refUserRoleId`, `accountStatus`) VALUES ('manager@mail.com', 'mgr', '$2a$12$.8t8jEQIHkpv/EYRRG.qFu/nr5TV9M8JrYoSL1Q.9dRAeB48w89yu', '2', 'Active');
INSERT INTO `southwesttech`.`bankuser` (`email`, `userName`, `userPassword`, `refUserRoleId`, `accountStatus`) VALUES ('employee@mail.com', 'emp', '$2a$12$.8t8jEQIHkpv/EYRRG.qFu/nr5TV9M8JrYoSL1Q.9dRAeB48w89yu', '3', 'Active');
INSERT INTO `southwesttech`.`bankuser` (`email`, `userName`, `userPassword`, `refUserRoleId`, `accountStatus`) VALUES ('merchant@mail.com', 'merc', '$2a$12$.8t8jEQIHkpv/EYRRG.qFu/nr5TV9M8JrYoSL1Q.9dRAeB48w89yu', '4', 'Active');
INSERT INTO `southwesttech`.`bankuser` (`email`, `userName`, `userPassword`, `refUserRoleId`, `accountStatus`) VALUES ('customer@mail.com', 'cust', '$2a$12$.8t8jEQIHkpv/EYRRG.qFu/nr5TV9M8JrYoSL1Q.9dRAeB48w89yu', '5', 'Active');
*/
/*
DELIMITER $$
-- Define update triggers to avoid updating system variables
-- Drop if update trigger exists for RefUserRole
DROP TRIGGER IF EXISTS ref_user_role_update_trigger
$$
-- Create trigger for RefUserRole to prevent updating system variable
CREATE TRIGGER ref_user_role_update_trigger
    BEFORE UPDATE ON `southwesttech`.`refuserrole`  FOR EACH ROW
     BEGIN
          IF NEW.refUserRoleId IN (1, 2, 3, 4, 5) OR NEW.bankUserRoleName IN ('Admin', 'Manager', 'Employee', 'Merchant', 'Customer')
          THEN
               SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Cannot update system variables';
          END IF;
     END;
$$
-- Drop if update trigger exists for RefTransactionStatus
DROP TRIGGER IF EXISTS ref_transaction_status_update_trigger
$$
-- Create trigger for RefTransactionStatus to prevent updating system variable
CREATE TRIGGER ref_transaction_status_update_trigger
    BEFORE UPDATE ON `southwesttech`.`reftransactionstatus`  FOR EACH ROW
     BEGIN
          IF NEW.refTransactionStatusId IN (0, 1, 2, 3, 4) OR NEW.transactionStatusDescription IN ('Submitted', 'Validated', 'NonValidated', 'Approved', 'Denied')
          THEN
               SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Cannot update system variables';
          END IF;
     END;
$$
-- Drop if update trigger exists for RefAccountType
DROP TRIGGER IF EXISTS ref_account_type_update_trigger
$$
-- Create trigger for RefAccountType to prevent updating system variable
CREATE TRIGGER ref_account_type_update_trigger
    BEFORE UPDATE ON `southwesttech`.`refaccounttype`  FOR EACH ROW
     BEGIN
          IF NEW.refAccountTypeId IN (1, 2) OR NEW.accountTypeDescription('Checking', 'Saving')
          THEN
               SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Cannot update system variables';
          END IF;
     END;
$$

-- Define insert triggers to avoid updating system variables
-- Drop if insert trigger exists for RefUserRole
DROP TRIGGER IF EXISTS ref_user_role_insert_trigger
$$
-- Create trigger for RefUserRole to prevent inserting system variable
CREATE TRIGGER ref_user_role_insert_trigger
    BEFORE INSERT ON `southwesttech`.`refuserrole`  FOR EACH ROW
     BEGIN
          IF NEW.refUserRoleId IN (1, 2, 3, 4, 5) OR NEW.bankUserRoleName IN ('Admin', 'Manager', 'Employee', 'Merchant', 'Customer')
          THEN
               SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Cannot update system variables';
          END IF;
     END;
$$
-- Drop if insert trigger exists for RefTransactionStatus
DROP TRIGGER IF EXISTS ref_transaction_status_insert_trigger
$$
-- Create trigger for RefTransactionStatus to prevent inserting system variable
CREATE TRIGGER ref_transaction_status_insert_trigger
    BEFORE INSERT ON `southwesttech`.`reftransactionstatus`  FOR EACH ROW
     BEGIN
          IF NEW.refTransactionStatusId IN (0, 1, 2, 3, 4) OR NEW.transactionStatusDescription IN ('Submitted', 'Validated', 'NonValidated', 'Approved', 'Denied')
          THEN
               SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Cannot update system variables';
          END IF;
     END;
$$
-- Drop if insert trigger exists for RefAccountType
DROP TRIGGER IF EXISTS ref_account_type_insert_trigger
$$
-- Create trigger for RefAccountType to prevent inserting system variable
CREATE TRIGGER ref_account_type_insert_trigger
    BEFORE INSERT ON `southwesttech`.`refaccounttype`  FOR EACH ROW
     BEGIN
          IF NEW.refAccountTypeId IN (1, 2) OR NEW.accountTypeDescription('Checking', 'Saving')
          THEN
               SIGNAL SQLSTATE '45000'
                    SET MESSAGE_TEXT = 'Cannot update system variables';
          END IF;
     END;
$$
DELIMITER ;
*/