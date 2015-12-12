-- Create user 
INSERT INTO `southwesttech`.`refuserrole` (bankUserRoleName) 
	VALUES ('Admin'), ('Manager'), ('Employee'), ('Merchant'), ('Customer');
INSERT INTO `southwesttech`.`bankuser` (`email`, `userName`, `userPassword`, `firstName`, `lastName`, `refUserRoleId`)
	VALUES ('sagarsangani29@gmail.com', 'sgr', '1234', 'Sagar', 'Sangani', 
		(SELECT refUserRoleId
        FROM `southwesttech`.`refuserrole`
        WHERE bankUserRoleName = 'Customer'
        LIMIT 1
        )
	);