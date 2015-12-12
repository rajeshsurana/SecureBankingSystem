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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.model.BankAccount;
import com.spring.model.BankTransaction;
import com.spring.model.BankTransactionList;
import com.spring.model.BankUser;
import com.spring.model.BankUserList;
import com.spring.model.NewBankUser;
import com.spring.model.NewBankUserList;
import com.spring.model.RefUserRole;
import com.spring.model.StringList;
import com.spring.service.BankAccountService;
import com.spring.service.BankTransactionService;
import com.spring.service.BankUserService;
import com.spring.service.EmailService;
import com.spring.service.NewBankUserService;
import com.spring.service.RefAccountTypeService;
import com.spring.service.RefUserRoleService;
import com.spring.util.AuthenticationHandler;
import com.spring.util.StaticConstants;
import com.spring.util.TransactionStatus;
import com.spring.validators.BankUserValidator;
import com.spring.validators.UpdateBankUserValidator;

/**
 * @author mahathi
 *
 */
@Controller
public class ManagerController {

	private NewBankUserService newBankService;
	private BankUserService bankUserService;
	private NewBankUserService newBankUserService;
	private NewBankUserList bankuserList;
	private RefAccountTypeService refAccountTypeService;
	private BankAccountService bankAccountService;
	private EmailService emailService;
	private RefUserRoleService refUserRoleService;
	private UpdateBankUserValidator updateBankUserValidator;
	private BankTransactionService bankTransactionService;
	private AuthenticationHandler auth = new AuthenticationHandler();

	public UpdateBankUserValidator getUpdateBankUserValidator() {
		return updateBankUserValidator;
	}

	@Autowired
	public void setUpdateBankUserValidator(UpdateBankUserValidator updateBankUserValidator) {
		this.updateBankUserValidator = updateBankUserValidator;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public BankAccountService getBankAccountService() {
		return bankAccountService;
	}

	@Autowired
	public void setBankAccountService(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}

	public RefAccountTypeService getRefAccountTypeService() {
		return refAccountTypeService;
	}

	@Autowired
	public void setRefAccountTypeService(RefAccountTypeService refAccountTypeService) {
		this.refAccountTypeService = refAccountTypeService;
	}

	public ManagerController() {
		bankuserList = new NewBankUserList();
	}

	public NewBankUserService getNewBankService() {
		return newBankService;
	}

	@Autowired
	public void setNewBankService(NewBankUserService newBankService) {
		this.newBankService = newBankService;
	}

	public BankUserService getBankUserService() {
		return bankUserService;
	}

	@Autowired
	public void setBankUserService(BankUserService bankUserService) {
		this.bankUserService = bankUserService;
	}

	public RefUserRoleService getRefUserRoleService() {
		return refUserRoleService;
	}

	@Autowired
	public void setRefUserRoleService(RefUserRoleService refUserRoleService) {
		this.refUserRoleService = refUserRoleService;
	}

	@Autowired
	public void setBankTransactionService(BankTransactionService transactionService) {
		this.bankTransactionService = transactionService;
	}

	public BankTransactionService getBankTransactionService() {
		return bankTransactionService;
	}

	@RequestMapping(value = "/SearchUsersByAccountNumber.do", method = RequestMethod.POST)
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
			if (!(tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)
					|| tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.InternalEmployee))) {
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}

		return "managerSearchExtUser";
	}

	@RequestMapping(value = "/updateUserProfile.do", method = RequestMethod.POST)
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
			if (!(tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)
					|| tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.InternalEmployee))) {
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}

		return "regEmployeeSearchUsr";
	}

	@RequestMapping(value = "/SearchUser.do", method = RequestMethod.POST)
	public ModelAndView searchUser(@RequestParam("bankAccountId") String bankAccountId,
			@ModelAttribute("bankuserList") BankUserList bankuserList, BindingResult bindingResult,
			HttpServletRequest request) {

		if (isNumber(bankAccountId)) {
			BankAccount tempAccount = bankAccountService.findAccountByAccountNumber(Integer.parseInt(bankAccountId));
			if (tempAccount != null) {
				BankUser tempUser = tempAccount.getBankuser();
				List<BankUser> bankUserRequested = new ArrayList<BankUser>();
				if (tempUser != null) {
					bankUserRequested.add(tempUser);
					bankuserList.setBankUsers(bankUserRequested);
					return new ModelAndView("managerViewUserDetails", "bankuserList", bankuserList);
				} else {
					ModelAndView mvc = new ModelAndView("managerSearchExtUser");
					mvc.addObject("message", "The user does not exist for this account");
					return mvc;
				}
			} else {
				ModelAndView mvc = new ModelAndView("managerSearchExtUser");
				mvc.addObject("message", "The account number does not exist");
				return mvc;
			}

		} else {
			ModelAndView mvc = new ModelAndView("managerSearchExtUser");
			mvc.addObject("message", "Please enter an integer for the account number");
			return mvc;
		}
	}

	@RequestMapping(value = "/viewMyProfile.do", method = RequestMethod.POST)
	public ModelAndView employeeViewProfile(@ModelAttribute("bankuserList") BankUserList bankuserList,
			HttpServletRequest request) {
		String UserName = (String) request.getSession().getAttribute("sUserName");
		BankUser tempUser = bankUserService.findUserByUserName(UserName);
		if (tempUser != null) {
			List<BankUser> bankUserRequested = new ArrayList<BankUser>();
			if (tempUser != null) {
				bankUserRequested.add(tempUser);
				bankuserList.setBankUsers(bankUserRequested);
			}
		}
		return new ModelAndView("EmployeeProfile", "bankuserList", bankuserList);
	}

	@RequestMapping(value = "/UpdateEmployeeProfile.do", method = RequestMethod.POST)
	public ModelAndView employeeEditProfile(@ModelAttribute("bankuser") BankUser bankuser, BindingResult bindingResult,
			HttpServletRequest request) {
		BankUserList bankuserList = new BankUserList();
		String UserName = (String) request.getSession().getAttribute("sUserName");
		BankUser tempUser = bankUserService.findUserByUserName(UserName);
		if (tempUser != null) {
			List<BankUser> bankUserRequested = new ArrayList<BankUser>();
			bankUserRequested.add(tempUser);
			bankuserList.setBankUsers(bankUserRequested);
		} else {
			return new ModelAndView("ManagerHome");
		}
		return new ModelAndView("EmployeeEditProfile", "bankuserList", bankuserList);
	}

	@RequestMapping(value = "/UpdateEmployeeProfileConfirm.do", method = RequestMethod.POST)
	public String employeeEditProfileConfirm(@Valid BankUser bankuser, ServletRequest request,
			BindingResult bindingResult, Model model, HttpServletRequest httpRequest,
			final RedirectAttributes redirectAttributes) {
		String UserName = (String) httpRequest.getSession().getAttribute("sUserName");
		// Redirect to login page if session has expired
		if ((httpRequest.getSession().getAttribute("sUserName")) == null) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			
			return "redirect:/";
		} else {
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (httpRequest.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if (!(tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)
					|| tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.InternalEmployee))) {
				httpRequest.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}

		String action = redirectSuceess(UserName);
		
		
		BankUser userEmailCheck = bankUserService.findUserByEmail(bankuser);
		
		BankUser user = bankUserService.findUserByUserName(bankuser.getUserName());
		
		if (userEmailCheck != null) {
			bindingResult.rejectValue(StaticConstants.bankuserEmail, StaticConstants.emailAlreadyExists);
			model.addAttribute("bankuser", user);
			model.addAttribute("emailMessage","Email address used by another account");
			//return "redirect:/goToHome";
			action = "EmployeeEditProfile";
			return action;
		}
		
		NewBankUser tempNewBankUser = newBankUserService.findUserByUserEmail(bankuser.getEmail());
		if (tempNewBankUser != null) {
			bindingResult.rejectValue(StaticConstants.bankuserEmail, StaticConstants.emailAlreadyExists);
			model.addAttribute("bankuser", user);
			model.addAttribute("emailMessage","Email address used by another account");

			//return "redirect:/goToHome";
			action = "EmployeeEditProfile";
			return action;
		}
		
		

		
		
		
		
		
		

		/* Validating the entered User Details */
		ValidationUtils.invokeValidator(updateBankUserValidator, bankuser, bindingResult);
		if (bindingResult.getErrorCount() != 0) {		
			redirectAttributes.addFlashAttribute("message","The update profile was not succesful. Please go back and update with proper values");
			action = "EmployeeEditProfile";
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

	@RequestMapping(value = "/UpdateUser.do")
	public ModelAndView updateUser(@RequestParam("bankAccountId") String bankAccountId,
			@ModelAttribute("bankuser") BankUser bankuser, BindingResult bindingResult, HttpServletRequest request) {
		if (isNumber(bankAccountId)) {
			BankAccount tempAccount = bankAccountService.findAccountByAccountNumber(Integer.parseInt(bankAccountId));
			if (tempAccount != null) {
				BankUser tempUser = tempAccount.getBankuser();
				if (tempUser != null) {
					return new ModelAndView("EmployeeEditUserDetails", "bankuser", tempUser);
				} else {
					ModelAndView mvc = new ModelAndView("regEmployeeSearchUsr");
					mvc.addObject("message", "User does not exist for the account");
					return mvc;
				}
			} else {
				ModelAndView mvc = new ModelAndView("regEmployeeSearchUsr");
				mvc.addObject("message", "The account does not exist");
				return mvc;
			}

		} else {
			ModelAndView mvc = new ModelAndView("regEmployeeSearchUsr");
			mvc.addObject("message", "Enter Number");
			return mvc;
		}

	}

	@RequestMapping(value = "/UpdateCustomer.do", method = RequestMethod.POST)
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
			if (!(tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)
					|| tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.InternalEmployee))) {
				httpRequest.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		String UserName = (String) httpRequest.getSession().getAttribute("sUserName");

		String action = redirectSuceess(UserName);
		/* Validating the entered User Details */
		ValidationUtils.invokeValidator(updateBankUserValidator, bankuser, bindingResult);
		
		
		BankUser userEmailCheck = bankUserService.findUserByEmail(bankuser);
		
		BankUser user = bankUserService.findUserByUserName(bankuser.getUserName());
		if(!(userEmailCheck.getEmail().equals(user.getEmail()))){
		
			user.setEmail(bankuser.getEmail());
			
		if (userEmailCheck != null) {
			bindingResult.rejectValue(StaticConstants.bankuserEmail, StaticConstants.emailAlreadyExists);
			model.addAttribute("bankuser", user);
			model.addAttribute("emailMessage","Email address used by another account");
			//return "redirect:/goToHome";
			action = "EmployeeEditUserDetails";
			return action;
		}
		
		NewBankUser tempNewBankUser = newBankUserService.findUserByUserEmail(bankuser.getEmail());
		if (tempNewBankUser != null) {
			bindingResult.rejectValue(StaticConstants.bankuserEmail, StaticConstants.emailAlreadyExists);
			model.addAttribute("bankuser", user);
			model.addAttribute("emailMessage","Email address used by another account");

			//return "redirect:/goToHome";
			action = "EmployeeEditUserDetails";
			return action;
		}
		
		}
		if (bindingResult.getErrorCount() != 0) {
			//model.addAttribute("bankuser", bankuser);

			action = "EmployeeEditUserDetails";
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

	private boolean isNumber(String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@RequestMapping(value = "/ShowListOfPendingApprovals.do", method = RequestMethod.POST)
	public ModelAndView showPendingApprovals(@ModelAttribute("bankuserList") NewBankUserList bankuserList,
			HttpServletRequest request, Model m) {
		String UserName = (String) request.getSession().getAttribute("sUserName");
		List<NewBankUser> bankUsersForApproval = new ArrayList<NewBankUser>();
		System.out.println("Showing the list of pending Approvals");
		bankUsersForApproval = newBankService.findUsersForApproval(StaticConstants.managerPendingApproval);

		if (null != bankUsersForApproval && bankUsersForApproval.size() > 0) {
			bankuserList.setUsers(bankUsersForApproval);
			for (NewBankUser user : bankUsersForApproval) {
				System.out.printf("%s \t %s \n", user.getFirstName(), user.getLastName());
			}
		}
		m.addAttribute("userList", new StringList());
		return new ModelAndView("ListAccountApprovals", "bankuserList", bankuserList);
	}

	@RequestMapping(value = "/deleteUserAccounts.do")
	public ModelAndView Deactivating(@ModelAttribute("chosenList") String chosenList,
			@ModelAttribute("bankuserList") BankUserList bankuserList, HttpServletRequest request, Model m) {
		String UserName = (String) request.getSession().getAttribute("sUserName");
		List<BankUser> bankUsersForDeactivation = new ArrayList<BankUser>();
		System.out.println("Showing the list of pending Approvals");
		RefUserRole role = refUserRoleService.findRoleByName(StaticConstants.ExternalCustomer);
		if (role != null)
			bankUsersForDeactivation = bankUserService
					.findUsersByAccountStatusAndRoleId(StaticConstants.Require_DeActivation, role.getRefUserRoleId());
		if (null != bankUsersForDeactivation && bankUsersForDeactivation.size() > 0) {
			bankuserList.setBankUsers(bankUsersForDeactivation);
			for (BankUser user : bankUsersForDeactivation) {
				System.out.printf("%s \t %s \n", user.getFirstName(), user.getLastName());
			}
		}
		m.addAttribute("chosenList", chosenList);
		m.addAttribute("userList", new StringList());
		return new ModelAndView("AccountDeactivationRequests", "bankuserList", bankuserList);

	}

	@RequestMapping(value = "/deleteMerchantAccounts.do")
	public ModelAndView DeactivatingMerchants(@ModelAttribute("chosenList") String chosenList,
			@ModelAttribute("bankuserList") BankUserList bankuserList, HttpServletRequest request, Model m) {
		String UserName = (String) request.getSession().getAttribute("sUserName");
		List<BankUser> bankUsersForDeactivation = new ArrayList<BankUser>();
		System.out.println("Showing the list of pending Approvals");
		RefUserRole role = refUserRoleService.findRoleByName(StaticConstants.Merchant);
		if (role != null)
			bankUsersForDeactivation = bankUserService
					.findUsersByAccountStatusAndRoleId(StaticConstants.Require_DeActivation, role.getRefUserRoleId());
		if (null != bankUsersForDeactivation && bankUsersForDeactivation.size() > 0) {
			bankuserList.setBankUsers(bankUsersForDeactivation);
			for (BankUser user : bankUsersForDeactivation) {
				System.out.printf("%s \t %s \n", user.getFirstName(), user.getLastName());
			}
		}
		m.addAttribute("chosenList", chosenList);
		m.addAttribute("userList", new StringList());
		return new ModelAndView("MerchantAccountDeactivationRequests", "bankuserList", bankuserList);

	}

	@RequestMapping(value = "/ApproveNewCustomer.do")
	public ModelAndView showPendingAccountCreation(@ModelAttribute("bankuserList") NewBankUserList bankuserList,
			@ModelAttribute("chosenList") String chosenList, HttpServletRequest request, Model m) {
		String UserName = (String) request.getSession().getAttribute("sUserName");
		List<NewBankUser> bankUsersForApproval = new ArrayList<NewBankUser>();
		System.out.println("Showing the list of pending Approvals");
		RefUserRole role = refUserRoleService.findRoleByName(StaticConstants.ExternalCustomer);
		if (role != null)
			bankUsersForApproval = newBankService.findUsersForApprovalforParticularAccount(
					StaticConstants.managerPendingApproval, UserName, role.getRefUserRoleId());

		if (null != bankUsersForApproval && bankUsersForApproval.size() > 0) {
			bankuserList.setUsers(bankUsersForApproval);
			for (NewBankUser user : bankUsersForApproval) {
				System.out.printf("%s \t %s \n", user.getFirstName(), user.getLastName());
			}
		}

		m.addAttribute("chosenList", chosenList);
		m.addAttribute("userList", new StringList());
		return new ModelAndView("AccountCreationApprovalExtUsers", "bankuserList", bankuserList);

	}

	@RequestMapping(value = "/approveConfirmationForExtUsers.do")
	public String ApproveNewExternalCustomer(@Valid StringList userList, ServletRequest request,
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
			if (!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)) {
				httpRequest.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		String UserName = (String) httpRequest.getSession().getAttribute("sUserName");
		String action = "";
		System.out.println("Approve servlet");
		if (userList != null) {
			if (userList.getChosenList() == null) {
				redirectAttributes.addFlashAttribute("chosenList", StaticConstants.checkBoxNotSelected);
				action = "redirect:ApproveNewCustomer.do";
				return action;
			} else {
				if (isEmployeeAppointed()) {
					for (String userNametoApprove : userList.getChosenList()) {
						ApproveCustomer(userNametoApprove);
					}
				} else {
					redirectAttributes.addFlashAttribute("chosenList", StaticConstants.checkBoxNotSelected);
					action = "redirect:ApproveNewCustomer.do";
				}
				action = redirectSuceess(UserName);
			}
		}
		return action;
	}

	@RequestMapping(value = "/DeleteConfirmationForExtUsers.do")
	public String ApproveDeactivationRequests(@Valid StringList userList, ServletRequest request,
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
			if (!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)) {
				httpRequest.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		String UserName = (String) httpRequest.getSession().getAttribute("sUserName");
		String action = "";
		System.out.println("Approve Deactivation servlet");
		if (userList != null) {
			if (userList.getChosenList() == null) {
				redirectAttributes.addFlashAttribute("chosenList", StaticConstants.checkBoxNotSelected);
				action = "redirect:deleteUserAccounts.do";
				return action;
			} else {
				for (String tempUserName : userList.getChosenList()) {
					bankUserService.updateAccountStatus(tempUserName, StaticConstants.Inactive_AccountStatus);
				}
				action = redirectSuceess(UserName);
			}
		}
		return action;
	}

	@RequestMapping(value = "/DenyDeactivationRequests.do")
	public String DenyDeactivationRequests(@Valid StringList userList, ServletRequest request,
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
			if (!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)) {
				httpRequest.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		String UserName = (String) httpRequest.getSession().getAttribute("sUserName");
		String action = "";
		System.out.println("Deny Deactivation servlet");
		if (userList != null && userList.getChosenList() != null) {
			for (String userNametoDeny : userList.getChosenList()) {
				bankUserService.updateAccountStatus(userNametoDeny, StaticConstants.Active_AccountStatus);
			}
			action = redirectSuceess(UserName);
		} else {
			redirectAttributes.addFlashAttribute("chosenList", StaticConstants.checkBoxNotSelected);
			action = "redirect:deleteUserAccounts.do";
			return action;
		}
		return action;
	}

	@RequestMapping(value = "/ApproveNewMerchant.do")
	public ModelAndView showPendingMerchantAccountCreation(@ModelAttribute("bankuserList") NewBankUserList bankuserList,
			@ModelAttribute("chosenList") String chosenList, HttpServletRequest request, Model m,
			final RedirectAttributes redirectAttributes) {
		String UserName = (String) request.getSession().getAttribute("sUserName");
		List<NewBankUser> bankUsersForApproval = new ArrayList<NewBankUser>();
		System.out.println("Showing the list of pending Approvals");
		RefUserRole role = refUserRoleService.findRoleByName(StaticConstants.Merchant);
		if (role != null)
			bankUsersForApproval = newBankService.findUsersForApprovalforParticularAccount(
					StaticConstants.managerPendingApproval, UserName, role.getRefUserRoleId());

		if (null != bankUsersForApproval && bankUsersForApproval.size() > 0) {
			bankuserList.setUsers(bankUsersForApproval);
			for (NewBankUser user : bankUsersForApproval) {
				System.out.printf("%s \t %s \n", user.getFirstName(), user.getLastName());
			}
		}
		m.addAttribute("chosenList", chosenList);
		m.addAttribute("userList", new StringList());
		return new ModelAndView("AccountCreationApprovalMerchant", "bankuserList", bankuserList);

	}

	@RequestMapping(value = "/approveConfirmationForMerchant.do")
	public String ApproveNewMerchant(@Valid StringList userList, ServletRequest request, BindingResult bindingResult,
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
			if (!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)) {
				httpRequest.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}

		String UserName = (String) httpRequest.getSession().getAttribute("sUserName");
		String action = "";
		System.out.println("Approve servlet");
		if (userList != null) {
			if (userList.getChosenList() == null) {
				redirectAttributes.addFlashAttribute("chosenList", StaticConstants.checkBoxNotSelected);
				action = "redirect:ApproveNewMerchant.do";
				return action;
			} else {
				if (isEmployeeAppointed()) {
					for (String userNametoApprove : userList.getChosenList()) {
						ApproveCustomer(userNametoApprove);
					}
				} else {
					redirectAttributes.addFlashAttribute("chosenList", StaticConstants.checkBoxNotSelected);
					action = "redirect:ApproveNewMerchant.do";
				}
				action = redirectSuceess(UserName);
			}
		}
		return action;
	}

	private boolean isEmployeeAppointed() {
		if (refUserRoleService.findRoleByName(StaticConstants.Manager) != null) {
			int roleIdOfEmployee = refUserRoleService.findRoleByName(StaticConstants.InternalEmployee)
					.getRefUserRoleId();
			List<BankUser> internalEmployeeUsers = bankUserService.findUsersOfParticularRole(roleIdOfEmployee);
			if (internalEmployeeUsers != null && !internalEmployeeUsers.isEmpty()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private String assignToEmployee() {
		String employee = "";
		Random rand = new Random();
		int length;
		if (refUserRoleService.findRoleByName(StaticConstants.InternalEmployee) != null) {
			int roleIdOfEmployee = refUserRoleService.findRoleByName(StaticConstants.InternalEmployee)
					.getRefUserRoleId();
			List<BankUser> internalEmployeeUsers = bankUserService.findUsersOfParticularRole(roleIdOfEmployee);
			if (internalEmployeeUsers != null && !internalEmployeeUsers.isEmpty()) {
				length = internalEmployeeUsers.size();
				employee = internalEmployeeUsers.get(rand.nextInt(length)).getUserName();
			}
		}
		System.out.println("Assigning to employee  " + employee);
		return employee;
	}

	private void ApproveCustomer(String UserName) {
		NewBankUser tempuser = newBankService.findUserByUserName(UserName);
		BankUser tempBankUser = new BankUser();
		if (tempuser != null) {
			tempBankUser.setUserName(tempuser.getUserName());
			tempBankUser.setUserPassword(tempuser.getUserPassword());

			tempBankUser.setFirstName(tempuser.getFirstName());
			tempBankUser.setLastName(tempuser.getLastName());
			tempBankUser.setDateOfBirth(tempuser.getDateOfBirth());
			tempBankUser.setSsn(tempuser.getSsn());
			tempBankUser.setRefUserRole(tempuser.getRefuserrole());

			tempBankUser.setEmail(tempuser.getEmail());
			tempBankUser.setPhoneNumber(tempuser.getPhoneNumber());

			tempBankUser.setMailingAddress(tempuser.getMailingAddress());
			tempBankUser.setResidentialAddress(tempuser.getResidentialAddress());

			tempBankUser.setUserCreatedOn(tempuser.getUserCreatedOn());

			tempBankUser.setAccountStatus(StaticConstants.Require_Activation);

			tempBankUser.setPersonalBanker(assignToEmployee());
			tempBankUser.setPiiAuthorization(StaticConstants.piiNotAuthorized);

			System.out.println("BankUsers done");
			String tempPassword = tempBankUser.getUserPassword();
			newBankService.updateAccountStatus(tempuser.getUserName(), StaticConstants.managerApproved);
			tempBankUser.setUserPassword(auth.encode(tempBankUser.getUserPassword()));
			bankUserService.setBankUser(tempBankUser);

			tempBankUser = bankUserService.findUserByUserName(tempBankUser.getUserName());

			/* Creating a checking account for the created user */

			BankAccount tempAccount = new BankAccount();

			tempAccount.setBankuser(tempBankUser);

			/* Setting the created time and bankAccount */
			Date createdDate = new Date();
			createdDate.setTime(System.currentTimeMillis());
			tempAccount.setAccountCreatedOn(createdDate);
			tempAccount.setBalance(StaticConstants.minimum_balance);
			tempAccount.setCreditLimit(StaticConstants.credit_limit);
			tempAccount.setDebitLimit(StaticConstants.debit_limit);
			tempAccount.setRefaccounttype(refAccountTypeService.findByName(StaticConstants.CheckingAccountType));
			bankAccountService.setBankAccount(tempAccount);

			/* Notifying the user that the account has been created */
			String recipientAddress = tempBankUser.getEmail();
			String fromAddress = "southwestbank.tech@gmail.com";
			String subject = "Bank Account Approval";
			StringBuilder message = new StringBuilder();

			message.append("Dear Customer,");
			message.append("\n\n");
			message.append("Welcome to the southwest bank. ");
			message.append(
					"Your account creation request has been approved.\n Please login and activate your account with the rtegistered Eail ID.");
			message.append("Please click on the link below");
			message.append("\n\nhttps://group15.mobicloud.asu.edu");
			message.append("\n\n\n Your Temporary Password is  : " +tempPassword);
			
			message.append("\n\n Regards,\n southwest Bank IT operations team");
		
			// prints debug info
			System.out.println("To: " + recipientAddress);
			System.out.println("Subject: " + subject);
			System.out.println("Message: " + message);

			emailService.ReadyToSendEmail(recipientAddress, fromAddress, subject, message.toString());

		}
	}

	@RequestMapping(value = "/DenyNewExternalCustomer.do")
	public String DenyNewExternalCustomer(@Valid StringList userList, ServletRequest request,
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
			if (!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)) {
				httpRequest.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		String UserName = (String) httpRequest.getSession().getAttribute("sUserName");
		String action = "";
		System.out.println("Deny servlet");
		if (userList != null && userList.getChosenList() != null) {
			for (String userNametoDeny : userList.getChosenList()) {
				DenyCustomer(userNametoDeny);
			}
			action = redirectSuceess(UserName);
		} else {
			redirectAttributes.addFlashAttribute("chosenList", StaticConstants.checkBoxNotSelected);
			action = "redirect:ApproveNewCustomer.do";
		}
		return action;
	}

	private void DenyCustomer(String userName) {
		NewBankUser tempuser = newBankService.findUserByUserName(userName);
		if (tempuser != null) {
			newBankService.updateAccountStatus(tempuser.getUserName(), StaticConstants.managerDenied);
		}
	}

	@RequestMapping(value = "/ShowListOfPendingTransactions.do")
	public String showPendingTransactions(
			@ModelAttribute("bankTransactionList") BankTransactionList bankTransactionList,
			@ModelAttribute("transactionResult") String transactionResult, HttpServletRequest request, Model model,
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
			if (!(tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)
					|| tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.InternalEmployee))) {
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}

		String userName = (String) request.getSession().getAttribute("sUserName");
		List<BankTransaction> pendingTransactions = new ArrayList<BankTransaction>();
		pendingTransactions = bankTransactionService.getPendingTransactions();
		if (null != pendingTransactions && pendingTransactions.size() > 0) {
			// bankTransactionList.setBankTransactions(pendingTransactions);
			model.addAttribute("bankTransactionList", pendingTransactions);
			/*
			 * for(BankTransaction tr: pendingTransactions) {
			 * System.out.println(tr.getBenefactor().getUserName() + " -> " +
			 * tr.getRecipient().getUserName() + ":" +
			 * tr.getTransactionAmount());; }
			 */

		} else {
			model.addAttribute("bankTransactionList", new ArrayList<BankTransaction>());
		}
		if (null != transactionResult) {
			model.addAttribute("transactionResult", transactionResult);
		}
		return "listPendingTransactions";
	}

	// TODO
	@RequestMapping(value = "/transaction/{action}/{id}/")
	public String ApproveTransaction(@PathVariable("action") String action, @PathVariable("id") int id,
			HttpServletRequest request, final RedirectAttributes redirectAttributes) {

		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		} else {
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if (!(tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)
					|| tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.InternalEmployee))) {
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}

		String resultPage = "redirect:/ShowListOfPendingTransactions.do";
		String UserName = (String) request.getSession().getAttribute("sUserName");
		BankUser currentUser = bankUserService.findUserByUserName(UserName);
		if (currentUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)
				|| currentUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.InternalEmployee)) {

			List<BankTransaction> transactionList = bankTransactionService.getTransactionById(id);
			BankTransaction currentTransaction = transactionList.get(0);
			Float transferAmount = -1f;
			try {
				transferAmount = currentTransaction.getTransactionAmount();
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("transactionError", "Invalid Transfer Amount!");
				return resultPage;
			}

			if (currentTransaction.getReftransactionstatus().getTransactionStatusDescription()
					.equals(StaticConstants.VALIDATED)) {
				if (transferAmount > 10000
						&& !currentUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)) {
					// Privileged transactions can only be handled by Manager
					redirectAttributes.addFlashAttribute("transactionError",
							"This transaction is still undergoing verification. Please wait and return back to approve!!!");
					return resultPage;
				} else if (action.equals("approve")) {
					if (currentTransaction.getBenefactor().getUserName()
							.equals(StaticConstants.transactionBotUserName)) {
						// Credit Account
						BankAccount recipientCheckingAcc = bankAccountService
								.findCheckingAccountByUserId(currentTransaction.getRecipient().getBankUserId());
						bankTransactionService.completeCreditTransaction(recipientCheckingAcc.getBankAccountId(),
								transferAmount, currentTransaction.getBankTransactionId(), currentUser.getBankUserId());
					} else if (currentTransaction.getRecipient().getUserName()
							.equals(StaticConstants.transactionBotUserName)) {
						// Debit Account
						BankAccount benefactorCheckingAcc = bankAccountService
								.findCheckingAccountByUserId(currentTransaction.getBenefactor().getBankUserId());
						Float minBenefactorBalance = benefactorCheckingAcc.getDebitLimit();
						if (minBenefactorBalance == null) {
							minBenefactorBalance = 0f;
						}
						if (benefactorCheckingAcc.getBalance() < (minBenefactorBalance + transferAmount)) {
							// Not enough balance for debit
							redirectAttributes.addFlashAttribute("transactionError", "Transaction not valid!");
							bankTransactionService.declineTransaction(currentTransaction.getBankTransactionId(),
									currentUser.getBankUserId());
						} else {
							bankTransactionService.completeDebitTransaction(benefactorCheckingAcc.getBankAccountId(),
									transferAmount, currentTransaction.getBankTransactionId(),
									currentUser.getBankUserId());
						}

					} else {
						BankAccount benefactorCheckingAcc = bankAccountService
								.findCheckingAccountByUserId(currentTransaction.getBenefactor().getBankUserId());
						BankAccount recipientCheckingAcc = bankAccountService
								.findCheckingAccountByUserId(currentTransaction.getRecipient().getBankUserId());
						Float minBenefactorBalance = benefactorCheckingAcc.getDebitLimit();
						Float minRecipientBalance = recipientCheckingAcc.getDebitLimit();
						if (minBenefactorBalance == null) {
							minBenefactorBalance = 0f;
						}
						if (minRecipientBalance == null) {
							minRecipientBalance = 0f;
						}
						if (benefactorCheckingAcc.getBalance() < (minBenefactorBalance + transferAmount)) {
							// Not enough balance for transfer
							redirectAttributes.addFlashAttribute("transactionError", "Transaction not valid!");
							bankTransactionService.declineTransaction(currentTransaction.getBankTransactionId(),
									currentUser.getBankUserId());
						} else {
							bankTransactionService.approveTransaction(benefactorCheckingAcc.getBankAccountId(),
									recipientCheckingAcc.getBankAccountId(), transferAmount,
									currentTransaction.getBankTransactionId(), currentUser.getBankUserId());
						}
					}
				} else if (action.equals("decline")) {
					bankTransactionService.declineTransaction(currentTransaction.getBankTransactionId(),
							currentUser.getBankUserId());
					return resultPage;
				}
			} else {
				// Transaction hasn't been validated
				redirectAttributes.addFlashAttribute("transactionError", "Transaction not validated!");
				return resultPage;
			}
		} else {
			// Current session isn't held by manager or employee
			redirectAttributes.addFlashAttribute("transactionError", "Illegal access exception!");
			return resultPage;
		}
		return resultPage;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat(StaticConstants.DatePattern);
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

}
