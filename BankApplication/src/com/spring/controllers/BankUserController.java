package com.spring.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.model.BankAccount;
import com.spring.model.BankUser;
import com.spring.model.BankUserList;
import com.spring.model.NewBankUser;
import com.spring.model.RefUserRole;
import com.spring.service.BankUserService;
import com.spring.service.EmailService;
import com.spring.service.NewBankUserService;
import com.spring.service.RefUserRoleService;
import com.spring.util.AuthenticationHandler;
import com.spring.util.StaticConstants;
import com.spring.validators.BankUserValidator;

/**
 * @author Mahathi
 *
 */
@Controller
public class BankUserController {

	private RefUserRoleService refUserRoleService;
	private BankUserService bankUserService;
	private NewBankUserService newBankUserService;
	private EmailService emailService;
	private static final String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private BankUserValidator bankUserValidator;
	private Random rand;
	private int length;

	public BankUserController() {
		this.rand = new Random();
		this.length = 10;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public BankUserValidator getBankUserValidator() {
		return bankUserValidator;
	}

	@Autowired
	public void setBankUserValidator(BankUserValidator bankUserValidator) {
		this.bankUserValidator = bankUserValidator;
	}

	public NewBankUserService getNewBankUserService() {
		return newBankUserService;
	}

	@Autowired
	public void setNewBankUserService(NewBankUserService newBankUserService) {
		this.newBankUserService = newBankUserService;
	}

	public RefUserRoleService getRefUserRoleService() {
		return refUserRoleService;
	}

	public BankUserService getBankUserService() {
		return bankUserService;
	}

	@Autowired
	public void setBankUserService(BankUserService bankUserService) {
		this.bankUserService = bankUserService;
	}

	@Autowired
	public void setRefUserRoleService(RefUserRoleService refUserRoleService) {
		this.refUserRoleService = refUserRoleService;
	}

	@RequestMapping(value = "/addNewCustomer.do", method = RequestMethod.POST)
	public String createNewExternalCustomer(HttpServletRequest request, final RedirectAttributes redirectAttributes) {

		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}

		return "CreateNewExtUser";
	}

	@RequestMapping(value = "/addNewMerchant.do", method = RequestMethod.POST)
	public String createNewMerchant(HttpServletRequest request, final RedirectAttributes redirectAttributes) {

		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}

		return "CreateNewMerchant";
	}

	@RequestMapping(value = "/addNewManagerAccount.do", method = RequestMethod.POST)
	public String createNewManagerAccount(HttpServletRequest request, final RedirectAttributes redirectAttributes) {

		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}

		return "CreateManagerAccount";
	}

	@RequestMapping(value = "/addNewInternalEmployee.do", method = RequestMethod.POST)
	public String createNewInternalEmployee(HttpServletRequest request, final RedirectAttributes redirectAttributes) {

		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}

		return "createNewIntUser";
	}
	
	private String generateRandomPassowrd(){
		String password ="";
		for (int i = 0; i < length; i++) {
			password = password + alphabet.charAt(rand.nextInt(alphabet.length()));
		}
		return password;
	}

	private String redirectSuceess(String userName) {
		String action = "";
		BankUser user = bankUserService.findUserByUserName(userName);
		if (user != null) {
			if (user.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)) {
				action = "ManagerHome";

			} else if (user.getRefUserRole().getBankUserRoleName().equals(StaticConstants.InternalEmployee)) {
				action = "homeInternalEmployee";
			}
		} else {
			action = "Error";
		}

		return action;

	}
	@RequestMapping(value = "/CommitNewCustomer.do", method = RequestMethod.POST)
	public String addNewUser(@Valid BankUser user, ServletRequest request, BindingResult bindingResult, Model model,
			HttpServletRequest httpRequest, final RedirectAttributes redirectAttributes) {

		// Redirect to login page if session has expired
		if ((httpRequest.getSession().getAttribute("sUserName")) == null) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}

		String action = redirectSuceess((String)(httpRequest.getSession().getAttribute("sUserName")));
		// Check if user email id exists
		if (user.getUserName() != null && !user.getUserName().trim().isEmpty()) {
			BankUser tempuser = bankUserService.findUserByEmail(user);
			if (tempuser != null) {
				bindingResult.rejectValue(StaticConstants.bankuserEmail, StaticConstants.emailAlreadyExists);
				action = "CreateNewExtUser";
				return action;
			}
			NewBankUser tempNewBankUser = newBankUserService.findUserByUserEmail(user.getEmail());
			if (tempNewBankUser != null) {
				bindingResult.rejectValue(StaticConstants.bankuserEmail, StaticConstants.emailAlreadyExists);
				action = "CreateNewExtUser";
				return action;
			}
		}
		// check if user name exists
		if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
			BankUser tempuser = bankUserService.findUserByUserName(user);
			if (tempuser != null) {
				bindingResult.rejectValue(StaticConstants.bankuserUsername, StaticConstants.userNameAlreadyExists);
				action = "CreateNewExtUser";
				return action;
			}
			NewBankUser tempNewBankUser = newBankUserService.findUserByUserName(user.getUserName());
			if (tempNewBankUser != null) {
				bindingResult.rejectValue(StaticConstants.bankuserUsername, StaticConstants.userNameAlreadyExists);
				action = "CreateNewExtUser";
				return action;
			}
		}

		/* Assigning the role to the ExternalCustomer */
		RefUserRole tempRole = refUserRoleService.findRoleByName(StaticConstants.ExternalCustomer);
		user.setRefUserRole(tempRole);

		/* Setting the created time */
		Date createdDate = new Date();
		createdDate.setTime(System.currentTimeMillis());
		user.setUserCreatedOn(createdDate);
		user.setUserPassword(generateRandomPassowrd());
		/* Validating the entered User Details */
		ValidationUtils.invokeValidator(bankUserValidator, user, bindingResult);

		
		if (bindingResult.getErrorCount() != 0) {
			action = "CreateNewExtUser";
		} else {
			if (isManagerAppointed())
				pushUserForApproval(user);
			else {
				bindingResult.rejectValue(StaticConstants.bankuserUsername, StaticConstants.NoManagerExists);
				action = "CreateNewExtUser";
				return action;
			}
		}

		return action;

	}

	@RequestMapping(value = "/CommitNewMerchant.do", method = RequestMethod.POST)
	public String addNewMerchant(@Valid BankUser user, ServletRequest request, BindingResult bindingResult, Model model,
			HttpServletRequest httpRequest, final RedirectAttributes redirectAttributes) {

		// Redirect to login page if session has expired
		if ((httpRequest.getSession().getAttribute("sUserName")) == null) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}

		String action = redirectSuceess((String)(httpRequest.getSession().getAttribute("sUserName")));

		if (user.getUserName() != null && !user.getUserName().trim().isEmpty()) {
			BankUser tempuser = bankUserService.findUserByUserName(user);
			if (tempuser != null) {
				bindingResult.rejectValue(StaticConstants.bankuserUsername, StaticConstants.userNameAlreadyExists);
				action = "CreateNewMerchant";
				return action;
			}
			NewBankUser tempNewBankUser = newBankUserService.findUserByUserName(user.getUserName());
			if (tempNewBankUser != null) {
				bindingResult.rejectValue(StaticConstants.bankuserUsername, StaticConstants.userNameAlreadyExists);
				action = "CreateNewMerchant";
				return action;
			}
		}

		// Check if user email id exists
		if (user.getUserName() != null && !user.getUserName().trim().isEmpty()) {
			BankUser tempuser = bankUserService.findUserByEmail(user);
			if (tempuser != null) {
				bindingResult.rejectValue(StaticConstants.bankuserEmail, StaticConstants.emailAlreadyExists);
				action = "CreateNewMerchant";
				return action;
			}
			NewBankUser tempNewBankUser = newBankUserService.findUserByUserEmail(user.getEmail());
			if (tempNewBankUser != null) {
				bindingResult.rejectValue(StaticConstants.bankuserEmail, StaticConstants.emailAlreadyExists);
				action = "CreateNewMerchant";
				return action;
			}
		}

		/* Assigning the role to the ExternalCustomer */
		RefUserRole tempRole = refUserRoleService.findRoleByName(StaticConstants.Merchant);
		user.setRefUserRole(tempRole);

		/* Setting the created time */
		Date createdDate = new Date();
		createdDate.setTime(System.currentTimeMillis());
		user.setUserCreatedOn(createdDate);
		user.setUserPassword(generateRandomPassowrd());

		/* Validating the entered User Details */
		ValidationUtils.invokeValidator(bankUserValidator, user, bindingResult);

		// Do not remove this commented code - Mahathi
		// action = validateUser(user,bindingResult);
		// List<ObjectError> allObjectErrors = bindingResult.getAllErrors();
		// for(ObjectError objectError : allObjectErrors){
		// FieldError fieldError = (FieldError)objectError;
		// System.out.println("Field name is " + fieldError.getField());
		// }

		if (bindingResult.getErrorCount() != 0) {
			action = "CreateNewMerchant";
		} else {
			if (isManagerAppointed())
				pushUserForApproval(user);
			else {
				bindingResult.rejectValue(StaticConstants.bankuserUsername, StaticConstants.NoManagerExists);
				action = "CreateNewMerchant";
				return action;
			}
		}

		return action;

	}

	private void pushUserForApproval(BankUser user) {

		NewBankUser tempUser = new NewBankUser();
		if (user != null) {
			tempUser.setUserName(user.getUserName());
			tempUser.setUserPassword(user.getUserPassword());

			tempUser.setFirstName(user.getFirstName());
			tempUser.setLastName(user.getLastName());
			tempUser.setDateOfBirth(user.getDateOfBirth());
			tempUser.setSsn(user.getSsn());
			tempUser.setRefuserrole(user.getRefUserRole());

			tempUser.setEmail(user.getEmail());
			tempUser.setPhoneNumber(user.getPhoneNumber());

			tempUser.setMailingAddress(user.getMailingAddress());
			tempUser.setResidentialAddress(user.getResidentialAddress());

			tempUser.setUserCreatedOn(user.getUserCreatedOn());

			tempUser.setAccountStatus(StaticConstants.managerPendingApproval);
			String Manager = assignToEmployee();
			tempUser.setAssignedToManager(Manager);
			newBankUserService.setNewBankUser(tempUser);
		}

	}

	private boolean isManagerAppointed() {
		if (refUserRoleService.findRoleByName(StaticConstants.Manager) != null) {
			int roleIdOfManager = refUserRoleService.findRoleByName(StaticConstants.Manager).getRefUserRoleId();
			List<BankUser> managerUsers = bankUserService.findUsersOfParticularRole(roleIdOfManager);
			if (managerUsers != null && !managerUsers.isEmpty()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private String assignToEmployee() {
		String manager = "";
		Random rand = new Random();
		int length;
		if (refUserRoleService.findRoleByName(StaticConstants.Manager) != null) {
			int roleIdOfManager = refUserRoleService.findRoleByName(StaticConstants.Manager).getRefUserRoleId();
			List<BankUser> managerUsers = bankUserService.findUsersOfParticularRole(roleIdOfManager);
			if (managerUsers != null && !managerUsers.isEmpty()) {
				length = managerUsers.size();
				manager = managerUsers.get(rand.nextInt(length)).getUserName();
			}
		}
		return manager;
	}

	@ModelAttribute("bankUser")
	public BankUser createBankUserModel() {
		return new BankUser();
	}

	@ModelAttribute("refuserrole")
	public RefUserRole createRefUserModel() {
		return new RefUserRole();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat(StaticConstants.DatePattern);
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
