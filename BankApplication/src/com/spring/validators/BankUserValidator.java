
package com.spring.validators;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.spring.model.BankUser;
import com.spring.model.RefUserRole;
import com.spring.util.StaticConstants;

/**
 * @author mahathi
 *
 */
@Component("bankUserValidator")
public class BankUserValidator implements Validator {
	
	private EmailValidator emailValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return BankUser.class.equals(clazz);
	}
	
	public EmailValidator getEmailValidator() {
		return emailValidator;
	}

	@Autowired
	public void setEmailValidator(EmailValidator emailValidator) {
		this.emailValidator = emailValidator;
	}

	@Override
	public void validate(Object target, Errors errors) {

		BankUser user = (BankUser) target;

		RefUserRole userRole = (user.getRefUserRole());
		Date tempDate = user.getUserCreatedOn();

		/*Checking for the fields of the form to check if they are empty*/
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, StaticConstants.bankuserEmail,
				StaticConstants.emptyFieldMessage);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, StaticConstants.bankuserUsername,
				StaticConstants.emptyFieldMessage);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, StaticConstants.bankuserFirstName,
				StaticConstants.emptyFieldMessage);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, StaticConstants.bankuserLastName,
				StaticConstants.emptyFieldMessage);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, StaticConstants.bankuserPassword,
				StaticConstants.emptyFieldMessage);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, StaticConstants.bankuserDateOfBirth,
				StaticConstants.emptyFieldMessage);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, StaticConstants.bankuserPhoneNumber,
				StaticConstants.emptyFieldMessage);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, StaticConstants.bankuserResidentialAddress,
				StaticConstants.emptyFieldMessage);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, StaticConstants.bankuserMailingAddress,
				StaticConstants.emptyFieldMessage);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, StaticConstants.bankuserSsn,
				StaticConstants.emptyFieldMessage);
		
 
		if (userRole == null) {
			errors.reject("User Role Not Set");
		}

		if (tempDate == null) {
			errors.reject("System Time Not set");
		}

		
		if (user.getPhoneNumber() != null && !isLong(user.getPhoneNumber()))
			errors.rejectValue(StaticConstants.bankuserPhoneNumber, StaticConstants.shouldNotContainNumbersMessage);

		if (user.getSsn() != null && (user.getSsn().length() >= 11 || user.getSsn().length() <= 7))
			errors.rejectValue(StaticConstants.bankuserSsn, StaticConstants.SSNLengthMessage);

		if (user.getPhoneNumber() != null && !isLong(user.getPhoneNumber()))
			errors.rejectValue(StaticConstants.bankuserPhoneNumber, StaticConstants.shouldNotContainNumbersMessage);

		if (user.getFirstName() != null && user.getFirstName().matches(".*\\d.*"))
			errors.rejectValue(StaticConstants.bankuserFirstName, StaticConstants.containNumbersMesage);

		if (user.getLastName() != null && user.getLastName().matches(".*\\d.*"))
			errors.rejectValue(StaticConstants.bankuserLastName, StaticConstants.containNumbersMesage);

		if (user.getUserPassword() != null && user.getUserPassword().length() < 8)
			errors.rejectValue(StaticConstants.bankuserPassword, StaticConstants.passwordLengthMessage);
		
		if (user.getPhoneNumber() != null && user.getPhoneNumber().length() != 10)
			errors.rejectValue(StaticConstants.bankuserPhoneNumber, StaticConstants.PhoneNumberLengthMessage);		

		if (user.getUserName() != null && user.getUserName().length() < 6)
			errors.rejectValue(StaticConstants.bankuserUsername, StaticConstants.userNameLengthMessage);

		if (user.getEmail() != null && !emailValidator.validate(user.getEmail()))
			errors.rejectValue(StaticConstants.bankuserEmail, StaticConstants.emailPatternErrorMessage);

		if (user.getUserName() != null && user.getUserName().equals(user.getFirstName()))
			errors.rejectValue(StaticConstants.bankuserUsername, StaticConstants.userNameSameAsFirstNameMessage);
		
		if (user.getUserName() != null && user.getUserName().equals(user.getEmail()))
			errors.rejectValue(StaticConstants.bankuserUsername, StaticConstants.userNameSameAsEmailMessage);
		
		System.out.println("Completed Validation" + errors.getAllErrors().toString());
	}

	private boolean isLong(String number) {
		try {
			Long.parseLong(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
