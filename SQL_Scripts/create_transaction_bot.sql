INSERT INTO `southwesttech`.`bankuser` 
(`email`, `userName`, `accountStatus`) VALUES ('bank.bot@mail.com', 'transactionBot', 'Inactive');

UPDATE  `southwesttech`.`banktransaction`
SET transactionBenefactorId = (SELECT bankUserId FROM southwesttech.bankuser WHERE userName = 'transactionBot' LIMIT 1)
WHERE transactionBenefactorId IS NULL;

UPDATE  `southwesttech`.`banktransaction`
SET transactionRecipientId = (SELECT bankUserId FROM southwesttech.bankuser WHERE userName = 'transactionBot' LIMIT 1)
WHERE transactionRecipientId IS NULL;