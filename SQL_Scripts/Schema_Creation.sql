CREATE DATABASE IF NOT EXISTS southwesttech;

USE southwesttech;

CREATE TABLE IF NOT EXISTS `refuserrole` (
	refUserRoleId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	bankUserRoleName NVARCHAR(100)
);

CREATE TABLE IF NOT EXISTS `refaccounttype` (
	refAccountTypeId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    accountTypeDescription NVARCHAR(100)
);

CREATE TABLE IF NOT EXISTS `reftransactionstatus` (
	refTransactionStatusId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    transactionStatusDescription NVARCHAR(100)
);

CREATE TABLE IF NOT EXISTS `bankuser` (
	bankUserId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email NVARCHAR(100) NOT NULL UNIQUE,
    phoneNumber NVARCHAR(100),
    userName NVARCHAR(100) NOT NULL UNIQUE,
    userPassword NVARCHAR(100),
    firstName NVARCHAR(200),
    lastName NVARCHAR(200),
    dateOfBirth DATE,
    SSN NVARCHAR(12),
    residentialAddress NVARCHAR(1000),
    mailingAddress NVARCHAR(1000),
    userCreatedOn DATETIME,
    lastLoginOn DATETIME,
    refUserRoleId INT,
    FOREIGN KEY (refUserRoleId)
		REFERENCES refuserrole(refUserRoleId)
);

CREATE TABLE IF NOT EXISTS `bankaccount` (
	bankAccountId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    bankUserId INT NOT NULL,
    refAccountTypeId INT NOT NULL,
    balance FLOAT,
    debitLimit FLOAT,
    creditLimit FLOAT,
    accountCreatedOn DATETIME,
    accountLastAccessedOn DATETIME,
    FOREIGN KEY (bankUserId)
		REFERENCES bankuser(bankUserId),
	FOREIGN KEY (refAccountTypeId)
		REFERENCES refaccounttype(refAccountTypeId)
);

CREATE TABLE IF NOT EXISTS `banktransaction` (
	bankTransactionId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    transactionRecipientId INT NOT NULL,
    transactionBenefactorId INT NOT NULL,
    transactionAmount FLOAT,
    transactionAuthorizerUserId INT,
    transactionApprovedOn DATETIME,
    refTransactionStatusId INT,
    FOREIGN KEY (transactionRecipientId)
		REFERENCES bankuser(bankUserId),
	FOREIGN KEY (transactionBenefactorId)
		REFERENCES bankuser(bankUserId),
	FOREIGN KEY (refTransactionStatusId)
		REFERENCES reftransactionstatus(refTransactionStatusId)
);

CREATE TABLE IF NOT EXISTS `accesslog` (
	accessLogId INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    userName NVARCHAR(100),
    IPAddress NVARCHAR(100),
    deviceId NVARCHAR(2000),
    accessedOn DATETIME,
    suspiciousActivity INT
);

CREATE TABLE IF NOT EXISTS `userverification` (
	userVerificationId INT PRIMARY KEY NOT NULL AUTo_INCREMENT,
    verifyingUserName NVARCHAR(100),
    verificationCode NVARCHAR(20),
    codeExpiresOn DATETIME,
    FOREIGN KEY (verifyingUserName)
		REFERENCES bankuser (userName)
);