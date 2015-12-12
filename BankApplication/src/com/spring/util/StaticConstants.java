package com.spring.util;

import java.math.BigInteger;

public class StaticConstants {

	public static String fromAddress = "southwestbank.tech@gmail.com";

	
	public static String DatePattern = "MM/dd/yyyy";

	public static String ChallengeToClient = "bbsanbdsabd218731289akksduihaQHWGQEKJ";

	/* To Map the fields from the view to the controller and for validations */
	public static String bankuserUsername = "userName";
	public static String bankuserEmail = "email";
	public static String bankuserFirstName = "firstName";
	public static String bankuserLastName = "lastName";
	public static String bankuserPhoneNumber = "phoneNumber";
	public static String bankuserBankUserID = "bankUserId";
	public static String bankuserPassword = "userPassword";
	public static String bankuserDateOfBirth = "dateOfBirth";
	public static String bankuserSsn = "ssn";
	public static String bankuserResidentialAddress = "residentialAddress";
	public static String bankuserMailingAddress = "mailingAddress";
	public static String bankuserRefUserRoleId = "refUserRoleId";
	public static String bankuserCreatedOn = "userCreatedOn";
	public static String bankuserLastLoginOn = "lastLoginOn";
	public static String bankAccountId = "bankAccountId";

	/* Error Messages for the Message Properties */
	public static String emptyFieldMessage = "Empty.bankUser.EmptyField";
	public static String containNumbersMesage = "Field.containNumbersError";
	public static String shouldNotContainNumbersMessage = "Field.shouldNotContainNumbersError";
	public static String passwordLengthMessage = "Passowrd.length.notSatisfied";
	public static String PhoneNumberLengthMessage = "PhoneNumber.length.notSatisfied";
	public static String SSNLengthMessage = "SSN.length.notSatisfied";
	public static String userNameLengthMessage = "userName.length.notSatisfied";
	public static String userNameSameAsFirstNameMessage = "UserName.SameAs.FirstName";
	public static String userNameSameAsEmailMessage = "UserName.SameAs.Email";
	public static String userNameAlreadyExists ="UserName.AlreadyExists";
	public static String emailPatternErrorMessage ="EmailPattern.NotCorrect";
	public static String emailAlreadyExists ="Email.Exists";
	public static String checkBoxNotSelected ="checkBox.NotSelected";
	public static String InactiveAccountMessage ="Inactive.UserAccount";
	public static String NonExistingEmailMessage="NonexistingEmail.bankUser.email";
	public static String NoManagerExists ="NoManager.Exists";
	public static String userNameDoesNotExist ="NonexistingUser.bankUser.userName";
	public static String updateNotDoneCorrectly ="update.NotDoneCorrectly";
	public static String bindingErrorCheck ="Login.Binding.Error";
	public static String ssnLengthNotSatisfied = "SSN.length.notSatisfied";

	/*User Role Fields */
	
	public static String ExternalCustomer = "Customer";
	public static String SystemAdmin = "Admin";
	public static String InternalEmployee = "Employee";
	public static String Manager = "Manager";
	public static String Merchant = "Merchant";
	public static String Government = "Government";

	/* Account Status in Bank User Table */

	public static String Active_AccountStatus = "Active";
	public static String Inactive_AccountStatus = "Inactive";
	public static String Require_Activation = "RequireActivation";
	public static String Require_DeActivation = "RequireDeActivation";

	/* Transaction Status fields */
	public static String SUBMITTED = "Submitted";
	public static String VALIDATED = "Validated";
	public static String NONVALIDATED = "NonValidated";
	public static String APPROVED = "Approved";
	public static String DENIED = "Denied";
	
	/*Account Type*/
	
	public static String CheckingAccountType ="Checking";
	public static String savingAccountType ="Saving";
	
	public static  float minimum_balance = 0.0f;
	public static  float credit_limit = 50000000.0f;
	public static  float debit_limit = 2000.0f;
	
	
	/*Account Status in New User Table*/
	
	public static String managerPendingApproval="managerPendingApproval";
	public static String managerDenied ="managerDenied";
	public static String managerApproved ="managerApproved";
	
	/*PII authorization Status in the bankUser Table*/
	
	public static String piiAuthorized = "Authorized";
	public static String piiNotAuthorized = "Not_Authorized";
	
	/* Debit/Credit bot UserName */
	public static String transactionBotUserName = "transactionBot";
	public static String ServerPublicKey = "MTYwMDI4NTUxNDk4MTkyOTIyMTY3NjMzMzQ2NTAzODQ4MzM5MDI4NDA1OTU2MDc4NTEyNTQ1ODMwMjc1MzcxOTg3OTQxMjA5NDkzMTM3OTY2ODAzODYzMzQ5MTAxNDI3OTYzNzU0MjI1Mjg2MTI1NjU4Njk3OTYxMjQ0MzQ2NTcwMjg3OTYyNzY1NzEwMjgxOTU3MDgyNjM5OTY1OTg2NDY5NTk1MjYwOTE2Nzc0Njg1NDQzOTY4MTc3NjQyNTU3MTE3ODA2NDY1MDUxMDg0ODc5NzgxNzUzODgxMzc3MDQ5NDUwNDEzNTIyNDMxNDM2NTk5Mjc1MjU2NTcwNzQwNzMwNDEwMTQyODQzNDY0ODkxMTMyMzgwNTY3NjI2MDMzOTE5MTAxNTI5NTY4MDIzMTI5MjYzfDY1NTM3";
	public static String ServerPublicKeyUsername = "southwestbankpublic";
	public static String ServerPrivateKeyUsername = "southwestbankprivate";
	public static String salt = "QA*+g~fF=a9A";
	
	// Captcha	
	public static final String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
}
