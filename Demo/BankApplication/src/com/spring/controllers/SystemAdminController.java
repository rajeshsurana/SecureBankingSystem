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

import com.spring.model.AccessLog;
import com.spring.model.AccessLogList;
import com.spring.model.BankAccount;
import com.spring.model.BankTransaction;
import com.spring.model.BankTransactionList;
import com.spring.model.BankUser;
import com.spring.model.BankUserList;
import com.spring.model.NewBankUser;
import com.spring.model.RefUserRole;
import com.spring.service.AccessLogService;
import com.spring.service.BankAccountService;
import com.spring.service.BankTransactionService;
import com.spring.service.BankUserService;
import com.spring.service.EmailService;
import com.spring.service.NewBankUserService;
import com.spring.service.RefUserRoleService;
import com.spring.util.StaticConstants;
import com.spring.validators.BankUserValidator;
import com.spring.validators.UpdateBankUserValidator;
import com.spring.util.AuthenticationHandler;

@Controller
public class SystemAdminController {

	private BankAccountService bankAccountService;
	private EmailService emailService;
	private RefUserRoleService refUserRoleService;
	private NewBankUserService newBankUserService;
	private BankUserService bankUserService;
	private BankUserValidator bankUserValidator;
	private AccessLogService accessLogService;
	private BankTransactionService bankTransactionService;
	private UpdateBankUserValidator updateBankUserValidator;
	private AuthenticationHandler auth = new AuthenticationHandler();
	private static final String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private Random rand;
	private int length;

	public SystemAdminController() {
		this.rand = new Random();
		this.length = 10;
	}

	public UpdateBankUserValidator getUpdateBankUserValidator() {
		return updateBankUserValidator;
	}

	@Autowired
	public void setUpdateBankUserValidator(UpdateBankUserValidator updateBankUserValidator) {
		this.updateBankUserValidator = updateBankUserValidator;
	}

	public BankTransactionService getBankTransactionService() {
		return bankTransactionService;
	}

	@Autowired
	public void setBankTransactionService(BankTransactionService bankTransactionService) {
		this.bankTransactionService = bankTransactionService;
	}

	public AccessLogService getAccessLogService() {
		return accessLogService;
	}

	@Autowired
	public void setAccessLogService(AccessLogService accessLogService) {
		this.accessLogService = accessLogService;
	}

	public BankUserService getBankUserService() {
		return bankUserService;
	}

	@Autowired
	public void setBankUserService(BankUserService bankUserService) {
		this.bankUserService = bankUserService;
	}

	public BankAccountService getBankAccountService() {
		return bankAccountService;
	}

	public BankUserValidator getBankUserValidator() {
		return bankUserValidator;
	}

	@Autowired
	public void setBankUserValidator(BankUserValidator bankUserValidator) {
		this.bankUserValidator = bankUserValidator;
	}

	@Autowired
	public void setBankAccountService(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public RefUserRoleService getRefUserRoleService() {
		return refUserRoleService;
	}

	@Autowired
	public void setRefUserRoleService(RefUserRoleService refUserRoleService) {
		this.refUserRoleService = refUserRoleService;
	}

	public NewBankUserService getNewBankUserService() {
		return newBankUserService;
	}

	@Autowired
	public void setNewBankUserService(NewBankUserService newBankUserService) {
		this.newBankUserService = newBankUserService;
	}

	@RequestMapping(value = "/SystemAdminupdateUserProfile.do", method = RequestMethod.POST)
	public String searchCustomer(@Valid BankAccount bankaccount, HttpServletRequest request) {
		return "sysAdminSearchUserForUpdate";
	}

	@RequestMapping(value = "/sysAdminDeleteAccount.do", method = RequestMethod.POST)
	public String searchCustomerForDelete(@Valid BankAccount bankaccount, HttpServletRequest request) {
		return "sysAdminSearchUserForDelete";
	}

	@RequestMapping(value = "/sysAdminDeleteUserProfile.do")
	public ModelAndView DeleteUser(@RequestParam("bankUserName") String bankUserName,
			@ModelAttribute("bankuser") BankUser bankuser, BindingResult bindingResult, HttpServletRequest request) {
		BankUser tempUser = bankUserService.findUserByUserName(bankUserName);
		if (tempUser != null) {
			if (tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)
					|| tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.InternalEmployee)) {
				return new ModelAndView("sysAdminDeleteUserDetails", "bankuser", tempUser);
			} else {
				ModelAndView mvc = new ModelAndView("sysAdminSearchUserForDelete");
				mvc.addObject("message", "you do not have authority to delete this profile");
				return mvc;
			}
		} else {
			ModelAndView mvc = new ModelAndView("sysAdminSearchUserForDelete");
			mvc.addObject("message", "The user does not exist");
			return mvc;
		}
	}

	@RequestMapping(value = "/sysAdminDeleteUserProfileConfirm.do", method = RequestMethod.POST)
	public String DeleteUserAfterConfirmation(@Valid BankUser bankuser, ServletRequest request,
			BindingResult bindingResult, Model model, HttpServletRequest httpRequest,
			final RedirectAttributes redirectAttributes) {

		// Redirect to login page if session has expired
		if ((httpRequest.getSession().getAttribute("sUserName")) == null) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		} else {
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (httpRequest.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if (!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.SystemAdmin)) {
				return "redirect:/";
			}
		}

		String action = "sysAdminhome";
		BankUser tempUser1 = bankUserService.findUserByUserName(bankuser.getUserName());
		if (tempUser1 != null) {
			if (tempUser1.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)
					|| tempUser1.getRefUserRole().getBankUserRoleName().equals(StaticConstants.InternalEmployee)) {
				tempUser1.setAccountStatus(StaticConstants.Inactive_AccountStatus);
				bankUserService.UpdateBankUser(tempUser1);

			} else {
				action = "sysAdminDeleteUserDetails";
			}
		} else {
			action = "sysAdminDeleteUserDetails";
		}
		return action;
	}

	@RequestMapping(value = "/sysAdminUpdateUserProfileEdit.do")
	public ModelAndView updateUser(@RequestParam("bankUserName") String bankUserName,
			@ModelAttribute("bankuser") BankUser bankuser, BindingResult bindingResult, HttpServletRequest request) {
		BankUser tempUser = bankUserService.findUserByUserName(bankUserName);
		if (tempUser != null) {
			if (tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)
					|| tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.ExternalCustomer)) {
				System.out.println("Done getting the users");
				return new ModelAndView("sysAdminEditUserDetails", "bankuser", tempUser);
			} else {
				ModelAndView mvc = new ModelAndView("sysAdminSearchUserForUpdate");
				mvc.addObject("message", "you do not have authority to delete this profile");
				return mvc;
			}
		} else {
			ModelAndView mvc = new ModelAndView("sysAdminSearchUserForUpdate");
			mvc.addObject("message", "The account does not exist");
			return mvc;
		}
	}

	@RequestMapping(value = "/sysAdminUpdateUserProfileConfirm.do", method = RequestMethod.POST)
	public String updateUser(@Valid BankUser bankuser, ServletRequest request, BindingResult bindingResult, Model model,
			HttpServletRequest httpRequest, final RedirectAttributes redirectAttributes) {

		// Redirect to login page if session has expired
		if ((httpRequest.getSession().getAttribute("sUserName")) == null) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		} else {
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (httpRequest.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if (!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.SystemAdmin)) {
				return "redirect:/";
			}
		}

		String action = "sysAdminhome";
		/* Validating the entered User Details */
		ValidationUtils.invokeValidator(updateBankUserValidator, bankuser, bindingResult);
		if (bindingResult.getErrorCount() != 0) {
			action = "sysAdminEditUserDetails";
		} else {
			BankUser tempUser = bankUserService.findUserByUserName(bankuser.getUserName());
			if (tempUser != null) {
				if (!tempUser.getEmail().equals(bankuser.getEmail()))
					tempUser.setEmail(bankuser.getEmail());
				if (!tempUser.getPhoneNumber().equals(bankuser.getPhoneNumber()))
					tempUser.setPhoneNumber(bankuser.getPhoneNumber());
				if (!tempUser.getFirstName().equals(bankuser.getFirstName()))
					tempUser.setFirstName(bankuser.getFirstName());
				if (!tempUser.getLastName().equals(bankuser.getLastName()))
					tempUser.setLastName(bankuser.getLastName());
				if (!tempUser.getDateOfBirth().equals(bankuser.getDateOfBirth()))
					tempUser.setDateOfBirth(bankuser.getDateOfBirth());
				if (!tempUser.getResidentialAddress().equals(bankuser.getResidentialAddress()))
					tempUser.setResidentialAddress(bankuser.getResidentialAddress());
				if (!tempUser.getMailingAddress().equals(bankuser.getMailingAddress()))
					tempUser.setMailingAddress(bankuser.getMailingAddress());
				bankUserService.UpdateBankUser(tempUser);
			} else {
				action = "Error";
			}
		}
		return action;
	}

	@RequestMapping(value = "/PIIUsingAccountNumber.do", method = RequestMethod.POST)
	public String searchCustomerByAccountNumber(@Valid BankAccount bankaccount, HttpServletRequest request,
			final RedirectAttributes redirectAttributes) {

		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		} else {
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if (!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.SystemAdmin)) {
				return "redirect:/";
			}
		}

		String action = "Error";
		String UserName = (String) request.getSession().getAttribute("sUserName");
		BankUser tempUser = bankUserService.findUserByUserName(UserName);
		if (tempUser != null) {
			String authorization = tempUser.getPiiAuthorization();
			if (authorization.equals(StaticConstants.piiAuthorized)) {
				return "SysAdminSearchUser";
			}
		}
		return action;
	}

	@RequestMapping(value = "/PIIUsingUserName.do", method = RequestMethod.POST)
	public String searchCustomerByUserName(@Valid BankAccount bankaccount, HttpServletRequest request,
			final RedirectAttributes redirectAttributes) {

		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		} else {
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if (!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.SystemAdmin)) {
				return "redirect:/";
			}
		}

		String action = "Error";
		String UserName = (String) request.getSession().getAttribute("sUserName");
		BankUser tempUser = bankUserService.findUserByUserName(UserName);
		if (tempUser != null) {
			String authorization = tempUser.getPiiAuthorization();
			if (authorization.equals(StaticConstants.piiAuthorized)) {
				return "sysAdminSearchUserByUserName";
			} else {
				action = "PIIDenied";
			}
		} else {
			action = "PIIDenied";
		}
		return action;
	}

	@RequestMapping(value = "/searchPII.do", method = RequestMethod.POST)
	public String searchPII(HttpServletRequest request, final RedirectAttributes redirectAttributes) {

		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		} else {
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if (!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.SystemAdmin)) {
				return "redirect:/";
			}
		}
		String action = "Error";
		String UserName = (String) request.getSession().getAttribute("sUserName");
		BankUser tempUser = bankUserService.findUserByUserName(UserName);
		if (tempUser != null) {
			String authorization = tempUser.getPiiAuthorization();
			if (authorization.equals(StaticConstants.piiAuthorized)) {
				action = "extractPII";
				return action;
			} else {
				action = "PIIDenied";
				return action;
			}
		} else {
			action = "PIIDenied";

		}
		return action;
	}

	@RequestMapping(value = "/SearchUserforPIIByAccountNumber.do", method = RequestMethod.POST)
	public ModelAndView searchUser(@RequestParam("bankAccountId") String bankAccountId,
			@ModelAttribute("bankuserList") BankUserList bankuserList, BindingResult bindingResult,
			HttpServletRequest request) {

		String UserName = (String) request.getSession().getAttribute("sUserName");
		BankUser tempUser = bankUserService.findUserByUserName(UserName);

		if (tempUser != null) {
			String authorization = tempUser.getPiiAuthorization();
			if (authorization.equals(StaticConstants.piiAuthorized)) {
				if (isNumber(bankAccountId)) {
					BankAccount tempAccount = bankAccountService
							.findAccountByAccountNumber(Integer.parseInt(bankAccountId));
					if (tempAccount != null) {
						BankUser searchedUser = tempAccount.getBankuser();
						List<BankUser> bankUserRequested = new ArrayList<BankUser>();
						if (searchedUser != null) {
							bankUserRequested.add(searchedUser);
							bankuserList.setBankUsers(bankUserRequested);
							return new ModelAndView("adminViewPII", "bankuserList", bankuserList);
						} else {
							ModelAndView mvc = new ModelAndView("sysAdminSearchUserForDelete");
							mvc.addObject("message", "The account does not exist");
							return mvc;
						}
					} else {
						ModelAndView mvc = new ModelAndView("sysAdminSearchUserForDelete");
						mvc.addObject("message", "The account does not exist");
						return mvc;
					}

				} else {
					ModelAndView mvc = new ModelAndView("sysAdminSearchUserForDelete");
					mvc.addObject("message", "Please enter a number");
					return mvc;
				}
			} else {
				return new ModelAndView("PIIDenied", "bankuserList", bankuserList);
			}
		} else
			return new ModelAndView("PIIDenied", "bankuserList", bankuserList);
	}

	@RequestMapping(value = "/SearchUserforPIIByUserName.do", method = RequestMethod.POST)
	public ModelAndView searchUserbyUserName(@RequestParam("bankUserName") String bankUserName,
			@ModelAttribute("bankuserList") BankUserList bankuserList, BindingResult bindingResult,
			HttpServletRequest request) {
		String UserName = (String) request.getSession().getAttribute("sUserName");
		BankUser tempUser = bankUserService.findUserByUserName(UserName);
		if (tempUser != null) {
			String authorization = tempUser.getPiiAuthorization();
			if (authorization.equals(StaticConstants.piiAuthorized)) {
				BankUser searchedUser = bankUserService.findUserByUserName(bankUserName);
				List<BankUser> bankUserRequested = new ArrayList<BankUser>();
				if (searchedUser != null) {
					bankUserRequested.add(searchedUser);
					bankuserList.setBankUsers(bankUserRequested);
				} else {
					ModelAndView mvc = new ModelAndView("sysAdminSearchUserByUserName");
					mvc.addObject("message", "The account does not exist");
					return mvc;
				}
				return new ModelAndView("adminViewPII", "bankuserList", bankuserList);

			} else {
				return new ModelAndView("PIIDenied", "bankuserList", bankuserList);
			}
		} else
			return new ModelAndView("PIIDenied", "bankuserList", bankuserList);
	}

	private boolean isNumber(String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@RequestMapping(value = "/AccessSystemLog.do", method = RequestMethod.POST)
	public ModelAndView showSystemAccessLog(@ModelAttribute("accessLogList") AccessLogList accessLogList,
			HttpServletRequest request, Model m) {
		String UserName = (String) request.getSession().getAttribute("sUserName");
		List<AccessLog> accessLogRetrieved = new ArrayList<AccessLog>();
		System.out.println("Showing the list of System Log");
		accessLogRetrieved = accessLogService.selectAllRecords();

		if (null != accessLogRetrieved && accessLogRetrieved.size() > 0) {
			accessLogList.setAccessLog(accessLogRetrieved);
			System.out.println("Access Log Retrieved");
		}
		return new ModelAndView("adminGetSyslog", "accessLogList", accessLogList);
	}

	@RequestMapping(value = "/AccessTransactionLog.do", method = RequestMethod.POST)
	public ModelAndView showTransactionLog(
			@ModelAttribute("bankTransactionList") BankTransactionList bankTransactionList, HttpServletRequest request,
			Model m) {
		String UserName = (String) request.getSession().getAttribute("sUserName");
		List<BankTransaction> bankTransactionRetrieved = new ArrayList<BankTransaction>();
		System.out.println("Showing the list of System Log");
		bankTransactionRetrieved = bankTransactionService.getAllTransactions();

		if (null != bankTransactionRetrieved && bankTransactionRetrieved.size() > 0) {
			bankTransactionList.setBankTransactions(bankTransactionRetrieved);

			System.out.println("Transaction Log Retrieved");
			for (BankTransaction tempTransaction : bankTransactionRetrieved) {
				System.out.println(tempTransaction.getBankTransactionId());
			}
		}
		return new ModelAndView("adminGetTransactionLog", "bankTransactionList", bankTransactionList);
	}

	@RequestMapping(value = "/CommitNewEmployee.do", method = RequestMethod.POST)
	public String addNewEmployeeUser(@Valid BankUser user, ServletRequest request, BindingResult bindingResult,
			Model model, HttpServletRequest httpRequest, final RedirectAttributes redirectAttributes) {

		// Redirect to login page if session has expired
		if ((httpRequest.getSession().getAttribute("sUserName")) == null) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		} else {
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (httpRequest.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if (!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.SystemAdmin)) {
				return "redirect:/";
			}
		}

		String action = "sysAdminhome";

		if (user.getUserName() != null && !user.getUserName().trim().isEmpty()) {
			BankUser tempuser = bankUserService.findUserByUserName(user);
			if (tempuser != null) {
				bindingResult.rejectValue(StaticConstants.bankuserUsername, StaticConstants.userNameAlreadyExists);
				action = "createNewIntUser";
				return action;
			}
			NewBankUser tempNewBankUser = newBankUserService.findUserByUserName(user.getUserName());
			if (tempNewBankUser != null) {
				bindingResult.rejectValue(StaticConstants.bankuserUsername, StaticConstants.userNameAlreadyExists);
				action = "createNewIntUser";
				return action;
			}
		}

		// Check if user email id exists
		if (user.getUserName() != null && !user.getUserName().trim().isEmpty()) {
			BankUser tempuser = bankUserService.findUserByEmail(user);
			if (tempuser != null) {
				bindingResult.rejectValue(StaticConstants.bankuserEmail, StaticConstants.emailAlreadyExists);
				action = "createNewIntUser";
				return action;
			}
			NewBankUser tempNewBankUser = newBankUserService.findUserByUserEmail(user.getEmail());
			if (tempNewBankUser != null) {
				bindingResult.rejectValue(StaticConstants.bankuserEmail, StaticConstants.emailAlreadyExists);
				action = "createNewIntUser";
				return action;
			}
		}
		/* Assigning the role to the InternalEmployee */
		RefUserRole tempRole = refUserRoleService.findRoleByName(StaticConstants.InternalEmployee);
		user.setRefUserRole(tempRole);
		user.setPiiAuthorization(StaticConstants.piiNotAuthorized);

		/* Setting the created time */
		Date createdDate = new Date();
		createdDate.setTime(System.currentTimeMillis());
		user.setUserCreatedOn(createdDate);
		user.setUserPassword(generateRandomPassowrd());

		/* Validating the entered User Details */
		ValidationUtils.invokeValidator(bankUserValidator, user, bindingResult);

		if (bindingResult.getErrorCount() != 0) {
			System.out.println(bindingResult.getErrorCount());
			action = "createNewIntUser";
		} else {
			System.out.println("Validated the internal Employee adding to the daatbase");

			user.setAccountStatus(StaticConstants.Require_Activation);
			// takes input from e-mail form
			String recipientAddress = user.getEmail();
			String fromAddress = "southwestbank.tech@gmail.com";
			String subject = "Employee Account Creation";
			StringBuilder message = new StringBuilder();

			message.append("Hello Internal Employee,");
			message.append("\n\n");
			message.append(
					"Welcome to the southwest bank. Your employee account has been created. Please login to the website to activate your account.");
			message.append("Please click on the link below");
			message.append("\n\nhttps://group15.mobicloud.asu.edu");
			// message.append("\nTo Do Attach the login link here\n");
			message.append("\n\nYour Temporary Password is  : " + user.getUserPassword());

			message.append("\n\n Regards,\n SouthWest Bank IT Operations Team");

			// prints debug info
			System.out.println("To: " + recipientAddress);
			System.out.println("Subject: " + subject);
			System.out.println("Message: " + message);

			// Encode password to hash
			user.setUserPassword(auth.encode(user.getUserPassword()));
			bankUserService.setBankUser(user);
			emailService.ReadyToSendEmail(recipientAddress, fromAddress, subject, message.toString());

			action = "sysAdminhome";

		}
		return action;
	}

	private String generateRandomPassowrd() {
		String password = "";
		for (int i = 0; i < length; i++) {
			password = password + alphabet.charAt(rand.nextInt(alphabet.length()));
		}
		return password;
	}

	@RequestMapping(value = "/CommitNewManager.do", method = RequestMethod.POST)
	public String addNewManager(@Valid BankUser bankUser, ServletRequest request, BindingResult bindingResult,
			Model model, HttpServletRequest httpRequest, final RedirectAttributes redirectAttributes) {

		// Redirect to login page if session has expired
		if ((httpRequest.getSession().getAttribute("sUserName")) == null) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		} else {
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (httpRequest.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if (!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.SystemAdmin)) {
				return "redirect:/";
			}
		}

		String action = "sysAdminhome";

		if (bankUser.getUserName() != null && !bankUser.getUserName().trim().isEmpty()) {
			BankUser tempuser = bankUserService.findUserByUserName(bankUser);
			if (tempuser != null) {
				bindingResult.rejectValue(StaticConstants.bankuserUsername, StaticConstants.userNameAlreadyExists);
				action = "CreateManagerAccount";
				return action;
			}
			NewBankUser tempNewBankUser = newBankUserService.findUserByUserName(bankUser.getUserName());
			if (tempNewBankUser != null) {
				bindingResult.rejectValue(StaticConstants.bankuserUsername, StaticConstants.userNameAlreadyExists);
				action = "CreateManagerAccount";
				return action;
			}
		}

		// Check if user email id exists
		if (bankUser.getUserName() != null && !bankUser.getUserName().trim().isEmpty()) {
			BankUser tempuser = bankUserService.findUserByEmail(bankUser);
			if (tempuser != null) {
				bindingResult.rejectValue(StaticConstants.bankuserEmail, StaticConstants.emailAlreadyExists);
				action = "CreateManagerAccount";
				return action;
			}
			NewBankUser tempNewBankUser = newBankUserService.findUserByUserEmail(bankUser.getEmail());
			if (tempNewBankUser != null) {
				bindingResult.rejectValue(StaticConstants.bankuserEmail, StaticConstants.emailAlreadyExists);
				action = "CreateManagerAccount";
				return action;
			}
		}
		/* Assigning the role to the Manager */
		RefUserRole tempRole = refUserRoleService.findRoleByName(StaticConstants.Manager);
		bankUser.setRefUserRole(tempRole);
		bankUser.setPiiAuthorization(StaticConstants.piiNotAuthorized);

		/* Setting the created time */
		Date createdDate = new Date();
		createdDate.setTime(System.currentTimeMillis());
		bankUser.setUserCreatedOn(createdDate);
		bankUser.setUserPassword(generateRandomPassowrd());

		/* Validating the entered User Details */
		ValidationUtils.invokeValidator(bankUserValidator, bankUser, bindingResult);

		if (bindingResult.getErrorCount() != 0) {
			System.out.println(bindingResult.getErrorCount());
			action = "CreateManagerAccount";
		} else {
			System.out.println("Validated the internal Employee adding to the daatbase");

			bankUser.setAccountStatus(StaticConstants.Require_Activation);
			// takes input from e-mail form
			String recipientAddress = bankUser.getEmail();
			String fromAddress = "southwestbank.tech@gmail.com";
			String subject = "Employee Account Creation";
			StringBuilder message = new StringBuilder();

			message.append("Hello Manager,");
			message.append("\n\n");
			message.append(
					"Welcome to the southwest bank. Your employee account has been created. Please login to the website to activate your account.");
			message.append("Please click on the link below");
			// message.append("\nTo Do Attach the login link here\n");
			message.append("\n\nhttps://group15.mobicloud.asu.edu");
			message.append("\n\nYour Temporary Password is  : " + bankUser.getUserPassword());

			message.append("\n\n Regards,\n SouthWest Bank IT Operations team");

			// prints debug info
			System.out.println("To: " + recipientAddress);
			System.out.println("Subject: " + subject);
			System.out.println("Message: " + message);

			// Encode password to hash
			bankUser.setUserPassword(auth.encode(bankUser.getUserPassword()));
			bankUserService.setBankUser(bankUser);
			emailService.ReadyToSendEmail(recipientAddress, fromAddress, subject, message.toString());
			action = "sysAdminhome";
		}
		return action;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat(StaticConstants.DatePattern);
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
}
