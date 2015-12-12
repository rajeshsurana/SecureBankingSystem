package com.spring.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.model.BankAccount;
import com.spring.model.BankTransaction;
import com.spring.model.BankUser;
import com.spring.model.BankUserList;
import com.spring.model.PendingMerchantTransactionAuth;
import com.spring.model.RefTransactionStatus;
import com.spring.service.BankAccountService;
import com.spring.service.BankTransactionService;
import com.spring.service.BankUserService;
import com.spring.service.RefAccountTypeService;
import com.spring.service.RefTransactionStatusService;
import com.spring.service.RefUserRoleService;
import com.spring.service.UserVerificationService;
import com.spring.util.EmailValidator;
import com.spring.util.StaticConstants;
import com.spring.util.TransactionStatus;
import com.spring.validators.UpdateBankUserValidator;
/**
 * @author shankar
 *
 */

@Controller
public class CustomerHomeController{
	
	private UserVerificationService userVerificationService;
	private BankUserService bankUserService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private RefTransactionStatusService refTransactionStatusService;
	@Autowired
	private BankTransactionService bankTransactionService;
	@Autowired
	private RefAccountTypeService refAccountTypeService;
	@Autowired
	private UpdateBankUserValidator updateBankUserValidator;
	@Autowired
	public BankAccountService getBankAccountService() {
		return bankAccountService;
	}
	@Autowired
	public void setBankAccountService(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}

	@Autowired
	public void setrefTransactionStatusService(RefTransactionStatusService refTransactionStatusService) {
		this.refTransactionStatusService = refTransactionStatusService;
	}
	
	@Autowired
	public void setUserService(BankUserService userServ) {
		this.bankUserService = userServ;
	}

	@ModelAttribute("bankAccount")
	public BankAccount createModelBankAccount() {
		return new BankAccount();
	}
	
	@ModelAttribute("bankTransaction")
	public BankTransaction createModelBankTransaction() {
		return new BankTransaction();
	}

	public UserVerificationService getUserService() {
		return userVerificationService;
	}

	@Autowired
	public void setBankTransactionService(BankTransactionService bankTransactionService) {
		this.bankTransactionService = bankTransactionService;
	}
	
	@Autowired
	public void setRefAccountTypeService(RefAccountTypeService refAccountTypeService) {
		this.refAccountTypeService = refAccountTypeService;
	}
	
	
	@Autowired
	public void setUserService(UserVerificationService userVerificationService) {
		this.userVerificationService = userVerificationService;
	}
	
	
	@Transactional
	@RequestMapping(value = "/transferToChecking.do", method = RequestMethod.POST)
	public String transferToChecking(@RequestParam("transferToChecking") String amountString, 
									 HttpServletRequest request, 
									 final RedirectAttributes redirectAttributes){
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}else{
			// Check for role authorization
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if(!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.ExternalCustomer)){
				redirectAttributes.addFlashAttribute("messageLoggedOut", "You have been redirected to login page due to authorization violation.");
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		
		BankAccount savingAccount;
		BankAccount checkingAccount;
		float savingAmount = 0;
		float inAmount = 0;
		BankUser user = bankUserService.findUserByUserName((String) (request.getSession().getAttribute("sUserName")));
		
		// Check for valid float amount 
		try{
			inAmount = Float.parseFloat(amountString);
			
		} catch(Exception e){
			redirectAttributes.addFlashAttribute("transferToChecking", "Invalid amount format.");
			return "redirect:externalCustomerHome.do";
		}
		
		// Check if transfer amount is greater than 0
		if (inAmount > 0) {
			savingAmount = (savingAccount = bankAccountService.findSavingAccountByUserId(user.getBankUserId()))
					.getBalance();
			System.out.println("Test Saving Account: " + savingAmount);
			
			checkingAccount = bankAccountService.findCheckingAccountByUserId(user.getBankUserId());
			
			// Check if transfer amount is less that balance in the account
			if ((inAmount <= savingAmount - savingAccount.getDebitLimit()) 
				 && (inAmount + checkingAccount.getBalance() <= checkingAccount.getCreditLimit()) ) {
				
				float outAmount = inAmount + checkingAccount.getBalance();
				bankAccountService.updateBankAccountBalance(checkingAccount.getBankAccountId(), outAmount);
				bankAccountService.updateBankAccountBalance(savingAccount.getBankAccountId(), savingAmount - inAmount);
				redirectAttributes.addFlashAttribute("transferToChecking", "Amount transferred successfully.");
			}else{
				redirectAttributes.addFlashAttribute("transferToChecking", "Amount violate debit or credit limit.");
			}
		}else{
			redirectAttributes.addFlashAttribute("transferToChecking", "Amount can't be negative.");
		}
		return "redirect:externalCustomerHome.do";
	}
		
	
	@Transactional
	@RequestMapping(value = "/transferToSaving.do", method = RequestMethod.POST)
	public String transferToSaving(@RequestParam("transferToSaving") String amountString, 
									 HttpServletRequest request, 
									 final RedirectAttributes redirectAttributes){
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}else{
			// Check for role authorization
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if(!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.ExternalCustomer)){
				redirectAttributes.addFlashAttribute("messageLoggedOut", "You have been redirected to login page due to authorization violation.");
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		
		BankAccount savingAccount;
		BankAccount checkingAccount;
		float checkingAmount = 0;
		float inAmount = 0;
		BankUser user = bankUserService.findUserByUserName((String) (request.getSession().getAttribute("sUserName")));
		
		// Check for valid float amount
		try{
			inAmount = Float.parseFloat(amountString);
			
		} catch(Exception e){
			redirectAttributes.addFlashAttribute("transferToSaving", "Invalid amount format.");
			return "redirect:externalCustomerHome.do";
		}
		
		// Check if transfer amount is greater than 0
		if (inAmount > 0) {
			checkingAmount = (checkingAccount = bankAccountService.findCheckingAccountByUserId(user.getBankUserId()))
					.getBalance();
			System.out.println("Test Checking Account: " + checkingAmount);
			
			savingAccount = bankAccountService.findSavingAccountByUserId(user.getBankUserId());
	
			if(savingAccount == null){ //check for no savings account
				redirectAttributes.addFlashAttribute("transferToSaving", "No Savings account found");
			}else if ((inAmount <= checkingAmount - checkingAccount.getDebitLimit()) // Check if transfer amount is less that balance in the account
				&& (inAmount + savingAccount.getBalance() <= savingAccount.getCreditLimit())) {
				
				float outAmount = inAmount + savingAccount.getBalance();
				bankAccountService.updateBankAccountBalance(savingAccount.getBankAccountId(), outAmount);
				bankAccountService.updateBankAccountBalance(checkingAccount.getBankAccountId(), checkingAmount - inAmount);
				redirectAttributes.addFlashAttribute("transferToSaving", "Amount transferred successfully.");
			}else{
				redirectAttributes.addFlashAttribute("transferToSaving", "Amount violates debit or credit limit.");
			}
		}else{
			redirectAttributes.addFlashAttribute("transferToSaving", "Amount can't be negative.");
		}
		return "redirect:externalCustomerHome.do";
	}
	
	@RequestMapping(value = "/externalCustomerHome.do", method = RequestMethod.GET)
	public String externalCustomerHome(HttpServletRequest request, 
									   Model model,
									   final RedirectAttributes redirectAttributes,
									   @ModelAttribute("transferToChecking") final String transferToChecking,
									   @ModelAttribute("transferToSaving") final String transferToSaving,
									   @ModelAttribute("debitResult") final String debitResult,
									   @ModelAttribute("creditResult") final String creditResult,
									   @ModelAttribute("transferResult") final String transferResult,
									   @ModelAttribute("createSavingAccResult") final String createSavingAccResult){
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}else{
			// Check for role authorization
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if(!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.ExternalCustomer)){
				redirectAttributes.addFlashAttribute("messageLoggedOut", "You have been redirected to login page due to authorization violation.");
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		
		// Load the messages 
		model.addAttribute("transferToChecking", transferToChecking);
		model.addAttribute("transferToSaving", transferToSaving);
		model.addAttribute("debitResult", debitResult);
		model.addAttribute("creditResult", creditResult);
		model.addAttribute("transferResult", transferResult);
		model.addAttribute("createSavingAccResult", createSavingAccResult);
		
		// Two type of accounts
		BankAccount checkingAccount;
		BankAccount savingAccount;
		
		// Create temporary bank user.
		BankUser tempUser = new BankUser();
		tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
		
		// Populate all fields
		tempUser = bankUserService.findUserByUserName(tempUser);
		
		// Set UserId
		request.setAttribute("bankUserId", tempUser.getBankUserId());
		
		// Get Checking Account
		checkingAccount = bankAccountService.findCheckingAccountByUserId(tempUser.getBankUserId());
		if(checkingAccount != null){
			System.out.println("Checking Account Balance: " + checkingAccount.getBalance());
			request.setAttribute("checkingBal", checkingAccount.getBalance());
			request.setAttribute("checkingAccNo", checkingAccount.getBankAccountId());
		}else{
			request.setAttribute("checkingBal", -1);
			request.setAttribute("checkingAccNo", -1);
		}
		
		// Get Saving Account
		savingAccount = bankAccountService.findSavingAccountByUserId(tempUser.getBankUserId());
		if(savingAccount != null){
			System.out.println("Saving Account Balance: " + savingAccount.getBalance());
			request.setAttribute("savingBal", savingAccount.getBalance());
			request.setAttribute("savingAccNo", savingAccount.getBankAccountId());
		}else{
			request.setAttribute("savingBal", -1);
			request.setAttribute("savingAccNo", -1);
		}
		return "homeExternalUser";
	}	
	
	@RequestMapping(value = "/merchantHomepage", method = RequestMethod.GET)
	public String merchantHome(HttpServletRequest request, 
									   Model model,
									   final RedirectAttributes redirectAttributes,
									   @ModelAttribute("transferToChecking") final String transferToChecking,
									   @ModelAttribute("debitResult") final String debitResult,
									   @ModelAttribute("creditResult") final String creditResult,
									   @ModelAttribute("transferResult") final String transferResult){
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}else{
			// Check for role authorization
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			
			if(!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Merchant)){
				redirectAttributes.addFlashAttribute("messageLoggedOut", "You have been redirected to login page due to authorization violation.");
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		
		// Load the messages 
		model.addAttribute("transferToChecking", transferToChecking);
		model.addAttribute("debitResult", debitResult);
		model.addAttribute("creditResult", creditResult);
		model.addAttribute("transferResult", transferResult);
		// Bank Account
		BankAccount checkingAccount;
		// Create temporary bank user.
		BankUser tempUser = new BankUser();
		tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
				
		// Populate all fields
		tempUser = bankUserService.findUserByUserName(tempUser);
		// Get Checking Account
		checkingAccount = bankAccountService.findCheckingAccountByUserId(tempUser.getBankUserId());
		if(checkingAccount != null){
			System.out.println("Checking Account Balance: " + checkingAccount.getBalance());
			request.setAttribute("checkingBal", checkingAccount.getBalance());
			request.setAttribute("checkingAccNo", checkingAccount.getBankAccountId());
		}else{
			request.setAttribute("checkingBal", "N/A");
			request.setAttribute("checkingAccNo", "N/A");
		}
		return "merchantHomepage";
	}
	
	@RequestMapping(value = "/debit.do", method = RequestMethod.POST)
	public String debitAccount(@Valid BankTransaction bankTransaction,
							   HttpServletRequest request,
							   BindingResult bindingResult,
							   final RedirectAttributes redirectAttributes){
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}else{
			// Check for role authorization
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if(!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.ExternalCustomer)  && 
					!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Merchant)){
				redirectAttributes.addFlashAttribute("messageLoggedOut", "You have been redirected to login page due to authorization violation.");
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		
		BankUser user = bankUserService.findUserByUserName((String) (request.getSession().getAttribute("sUserName")));
		BankUser recipient = bankUserService.findUserByUserName("transactionBot");
		int refRoleId = user.getRefUserRole().getRefUserRoleId();
		BankAccount checkingAccount = bankAccountService.findCheckingAccountByUserId(user.getBankUserId());
		Float checkingBalance = checkingAccount.getBalance();
		RefTransactionStatus reftransactionstatus;
		Float debitAmount = -1f;

		try{
			debitAmount = bankTransaction.getTransactionAmount(); 
		}catch(Exception e){
			bindingResult.rejectValue("transactionAmount", "InvalidAmount.bankTransaction.transactionAmount");
			if(refRoleId == 5)
				return "redirect:externalCustomerHome.do";
			else
				return "redirect:merchantHomepage";
		}
		
		// Check if debit amount is less than available checking balance - debit limit
		// ie. minimum debit limit balance should be there in the account for debit 
		// transaction to succeed
		if(debitAmount == null || debitAmount < 1){
			redirectAttributes.addFlashAttribute("debitResult", "Invalid amount");
		}else if(debitAmount >= 1 && checkingBalance < checkingAccount.getDebitLimit()){
		//	bindingResult.rejectValue("transactionAmount", "NegativeAmount.bankTransaction.transactionAmount");
			redirectAttributes.addFlashAttribute("debitResult", "Insufficient funds");

		}
		else if(debitAmount <= (checkingBalance - checkingAccount.getDebitLimit()) && debitAmount >= 1){
			
			// Set Benefactor with current logged user as amount to be debited from self account
			bankTransaction.setBenefactor(user);
			bankTransaction.setRecipient(recipient);
			// Set transaction status to be submitted
			TransactionStatus status = TransactionStatus.VALIDATED;
			reftransactionstatus = refTransactionStatusService.getRefTransactionStatusById(status.getValue());
			bankTransaction.setReftransactionstatus(reftransactionstatus);
			bankTransactionService.addBankTransaction(bankTransaction);
			redirectAttributes.addFlashAttribute("debitResult", "Debit transaction sent for Approval to Bank.");
			
		}else{
		//	bindingResult.rejectValue("transactionAmount", "NegativeAmount.bankTransaction.transactionAmount");
			redirectAttributes.addFlashAttribute("transactionAmount", "Amount can't be negative or total balance can't be less than debit limit");
		}
		if(refRoleId == 5)
			return "redirect:externalCustomerHome.do";
		else
			return "redirect:merchantHomepage";
	}
	
	@RequestMapping(value = "/credit.do", method = RequestMethod.POST)
	public String creditAccount(@Valid BankTransaction bankTransaction,
							   HttpServletRequest request,
							   BindingResult bindingResult,
							   final RedirectAttributes redirectAttributes){
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}else{
			// Check for role authorization
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if(!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.ExternalCustomer) && 
					!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Merchant)){
				redirectAttributes.addFlashAttribute("messageLoggedOut", "You have been redirected to login page due to authorization violation.");
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		
		BankUser user = bankUserService.findUserByUserName((String) (request.getSession().getAttribute("sUserName")));
		BankUser benefactor = bankUserService.findUserByUserName("transactionBot");
		int refRoleId = user.getRefUserRole().getRefUserRoleId();
		BankAccount checkingAccount = bankAccountService.findCheckingAccountByUserId(user.getBankUserId());
		Float checkingBalance = checkingAccount.getBalance();
		RefTransactionStatus reftransactionstatus;
		Float creditAmount = -1f;

		try{
			creditAmount = bankTransaction.getTransactionAmount(); 
		}catch(Exception e){
			bindingResult.rejectValue("transactionAmount", "InvalidAmount.bankTransaction.transactionAmount");
			if(refRoleId == 5)
				return "redirect:externalCustomerHome.do";
			else
				return "redirect:merchantHomepage";
		}
		
		// Check if credit amount is less than debit limit - available checking balance
		// ie. total amount cannot exceed credit limit
		if(creditAmount == null || creditAmount < 1){
			redirectAttributes.addFlashAttribute("creditResult", "Invalid amount");
//		}else if(creditAmount >= 1 && (creditAmount <= (checkingAccount.getCreditLimit() - checkingBalance))){
//		//	bindingResult.rejectValue("transactionAmount", "NegativeAmount.bankTransaction.transactionAmount");
//			redirectAttributes.addFlashAttribute("creditResult", "Insufficient funds");

		}else if(creditAmount <= (checkingAccount.getCreditLimit() - checkingBalance) && creditAmount >= 1){
			
			// Set Recipient with current logged user as amount to be debited from self account
			bankTransaction.setRecipient(user);
			bankTransaction.setBenefactor(benefactor);
			// Set transaction status to be submitted
			TransactionStatus status = TransactionStatus.VALIDATED;
			reftransactionstatus = refTransactionStatusService.getRefTransactionStatusById(status.getValue());
			bankTransaction.setReftransactionstatus(reftransactionstatus);
			bankTransactionService.addBankTransaction(bankTransaction);
			redirectAttributes.addFlashAttribute("creditResult", "Credit transaction sent for Bank Approval");
		}else{
			bindingResult.rejectValue("transactionAmount", "ExceedCreditLimitAmount.bankTransaction.transactionAmount");
		}
		if(refRoleId == 5)
			return "redirect:externalCustomerHome.do";
		else
			return "redirect:merchantHomepage";
	}
	
	@RequestMapping(value = "/transfer.do", method = RequestMethod.POST)
	public String tranferAmount(@Valid BankTransaction bankTransaction,
			                    @RequestParam("email") String email,
							    HttpServletRequest request,
							    BindingResult bindingResult,
							    final RedirectAttributes redirectAttributes){
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}else{
			// Check for role authorization
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if(!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.ExternalCustomer) && 
					!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Merchant)){
				redirectAttributes.addFlashAttribute("messageLoggedOut", "You have been redirected to login page due to authorization violation.");
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		
		BankUser benefactor = bankUserService.findUserByUserName((String) (request.getSession().getAttribute("sUserName")));
		int refRoleId = benefactor.getRefUserRole().getRefUserRoleId();
		BankAccount checkingAccount = bankAccountService.findCheckingAccountByUserId(benefactor.getBankUserId());
		Float checkingBalance = checkingAccount.getBalance();
		RefTransactionStatus reftransactionstatus;
		Float transferAmount = -1f;

		try{
			transferAmount = bankTransaction.getTransactionAmount(); 
		}catch(Exception e){
			bindingResult.rejectValue("transactionAmount", "InvalidAmount.bankTransaction.transactionAmount");
			if(refRoleId == 5)
				return "redirect:externalCustomerHome.do";
			else
				return "redirect:merchantHomepage";
		}
		
		EmailValidator emailValidator = new EmailValidator();
		
		if(!emailValidator.validate(email)){
			redirectAttributes.addFlashAttribute("transferResult", "Invalid Email format.");
			if(refRoleId == 5)
				return "redirect:externalCustomerHome.do";
			else
				return "redirect:merchantHomepage";
		}
		
		BankUser recipient = bankUserService.findUserByEmail(email);
		
		if(recipient == null){
			redirectAttributes.addFlashAttribute("transferResult", "Email does not belong to any bank customer.");
			if(refRoleId == 5)
				return "redirect:externalCustomerHome.do";
			else
				return "redirect:merchantHomepage";
		}else if(recipient.getBankUserId() == benefactor.getBankUserId()){
			if(refRoleId == 5){
				redirectAttributes.addFlashAttribute("transferResult", "Use other tranfer functionality to tranfer to own differnt accounts.");
				return "redirect:externalCustomerHome.do";
			}
				
			else{
				redirectAttributes.addFlashAttribute("transferResult", "Can't transfer to yourself!!!");
				return "redirect:merchantHomepage";
			}
		}
		
		// Check if recipient is customer or merchant
		if(recipient.getRefUserRole().getRefUserRoleId() < 4){
			redirectAttributes.addFlashAttribute("transferResult", "Recipient is not external customer or merchant");
			if(refRoleId == 5)
				return "redirect:externalCustomerHome.do";
			else
				return "redirect:merchantHomepage";
		}
		
		// Check if recipient has checking account
		BankAccount tmpRecipientAccount = bankAccountService.findCheckingAccountByUserId(recipient.getBankUserId());
		if(tmpRecipientAccount == null){
			redirectAttributes.addFlashAttribute("transferResult", "Recipient does not have checking account.");
			if(refRoleId == 5)
				return "redirect:externalCustomerHome.do";
			else
				return "redirect:merchantHomepage";
		}
		
		// Check if transfer amount is less than available checking balance - debit limit
		// ie. total amount cannot exceed credit limit
		if(transferAmount <= (checkingBalance - checkingAccount.getDebitLimit()) && transferAmount > 0){
			
			// Set Recipient
			bankTransaction.setRecipient(recipient);
			
			// Set Benefactor
			bankTransaction.setBenefactor(benefactor);
			
			// Set transaction status to be submitted
			TransactionStatus status = TransactionStatus.SUBMITTED;
			reftransactionstatus = refTransactionStatusService.getRefTransactionStatusById(status.getValue());
			bankTransaction.setReftransactionstatus(reftransactionstatus);
			
			//Submit transaction to bank
			int transactionId = bankTransactionService.addBankTransaction(bankTransaction);
			request.getSession().setAttribute("sTransactionId", transactionId);

		}else{
			bindingResult.rejectValue("transactionAmount", "NegativeAmount.bankTransaction.transactionAmount");
			if(refRoleId == 5)
				return "redirect:externalCustomerHome.do";
			else
				return "redirect:merchantHomepage";
		}
		request.getSession().setAttribute("sTransfer", "transfer");
		return "redirect:sendOTP.do";		
	}
	
	//This functions initiates transfer by a Merchant on behalf of a Customer/Merchant
	@RequestMapping(value = "/transferToMerchant.do", method = RequestMethod.POST)
	public String transferToMerchant(@Valid BankTransaction bankTransaction,
				                    @RequestParam("email") String email,
								    HttpServletRequest request,
								    BindingResult bindingResult,
								    final RedirectAttributes redirectAttributes){
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}else{
			// Check for role authorization
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if(!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Merchant)){
				redirectAttributes.addFlashAttribute("messageLoggedOut", "You have been redirected to login page due to authorization violation.");
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		
		BankUser recipient = bankUserService.findUserByUserName((String) (request.getSession().getAttribute("sUserName")));
		RefTransactionStatus reftransactionstatus;
		Float transferAmount = -1f;
		try{
			transferAmount = bankTransaction.getTransactionAmount(); 
		}catch(Exception e){
			bindingResult.rejectValue("transactionAmount", "InvalidAmount.bankTransaction.transactionAmount");
			return "redirect:merchantHomepage";
		}
		
		EmailValidator emailValidator = new EmailValidator();
		
		if(!emailValidator.validate(email)){
			redirectAttributes.addFlashAttribute("transferToMerchantResult", "Invalid Email format.");
			return "redirect:merchantHomepage";
		}
		BankUser benefactor = bankUserService.findUserByEmail(email);
		
		if(benefactor == null){
			redirectAttributes.addFlashAttribute("transferToMerchantResult", "Email does not belong to any bank customer.");
			return "redirect:merchantHomepage";
		}else if(benefactor.getBankUserId() == recipient.getBankUserId()){
			redirectAttributes.addFlashAttribute("transferToMerchantResult", "Can't transfer to Yourself!!!");
			return "redirect:merchantHomepage";
		}
		
		BankAccount checkingAccount = bankAccountService.findCheckingAccountByUserId(benefactor.getBankUserId());
		Float checkingBalance = checkingAccount.getBalance();
		// Check if recipient is customer or merchant
		if(benefactor.getRefUserRole().getRefUserRoleId() < 4){
			redirectAttributes.addFlashAttribute("transferToMerchantResult", "Benefactor is not external customer or merchant");
			return "redirect:merchantHomepage";
		}
			
		// Check if recipient has checking account
		BankAccount tmpBenefactorAccount = bankAccountService.findCheckingAccountByUserId(benefactor.getBankUserId());
		if(tmpBenefactorAccount == null){
			redirectAttributes.addFlashAttribute("transferToMerchantResult", "Benefactor does not have checking account.");
			return "redirect:merchantHomepage";
		}
		// Check if transfer amount is negative
		if(transferAmount > 0 && transferAmount <= (checkingBalance - checkingAccount.getDebitLimit())){
		// Set Recipient
			bankTransaction.setRecipient(recipient);
						
		// Set Benefactor
			bankTransaction.setBenefactor(benefactor);
		// Set transaction status to be submitted
			TransactionStatus status = TransactionStatus.SUBMITTEDBYMERCHANT;
			reftransactionstatus = refTransactionStatusService.getRefTransactionStatusById(status.getValue());
			bankTransaction.setReftransactionstatus(reftransactionstatus);
		//Submit transaction to bank
			int transactionId = bankTransactionService.addBankTransaction(bankTransaction);
			request.getSession().setAttribute("sTransactionIdMerchant", transactionId);

		}else{
			bindingResult.rejectValue("transactionAmount", "NegativeAmount.bankTransaction.transactionAmount");
			return "redirect:merchantHomepage";
		}
		request.getSession().setAttribute("sTransferToMerchant", "transferToMerchant");
		return "redirect:sendOTP.do";
	}	
	
	@RequestMapping(value = "/transferSuccessful.do", method = RequestMethod.GET)
	public String tranferAmountSuccessful(HttpServletRequest request,
										  final RedirectAttributes redirectAttributes){
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}else{
			// Check for role authorization
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if(!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.ExternalCustomer) && 
					!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Merchant)){
				redirectAttributes.addFlashAttribute("messageLoggedOut", "You have been redirected to login page due to authorization violation.");
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		
		BankUser benefactor = bankUserService.findUserByUserName((String) (request.getSession().getAttribute("sUserName")));
		int refRoleId = benefactor.getRefUserRole().getRefUserRoleId();
		int transactionId = (int) request.getSession().getAttribute("sTransactionId");
		System.out.println("Transaction ID:" + transactionId);
		RefTransactionStatus reftransactionstatus;
		
		// Set transaction status to be validated
		TransactionStatus status = TransactionStatus.VALIDATED;
		reftransactionstatus = refTransactionStatusService.getRefTransactionStatusById(status.getValue());
		bankTransactionService.updateValidatedBankTransactionById(transactionId, reftransactionstatus);
		
		// Remove session variables
		request.getSession().removeAttribute("sTransfer");
		request.getSession().removeAttribute("sTransactionId");
		
		// Set display message for view
		redirectAttributes.addFlashAttribute("transferResult", "Transfer transaction sent for Approval to Bank.");
		if(refRoleId == 5)
			return "redirect:externalCustomerHome.do";
		else
			return "redirect:merchantHomepage";
	}
	
	@RequestMapping(value = "/transferToMerchantSuccessful.do", method = RequestMethod.GET)
	public String transferToMerchantSuccessful(HttpServletRequest request,
											   final RedirectAttributes redirectAttributes){
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}else{
			// Check for role authorization
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if(!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Merchant)){
				redirectAttributes.addFlashAttribute("messageLoggedOut", "You have been redirected to login page due to authorization violation.");
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		
		int transactionId = (int) request.getSession().getAttribute("sTransactionIdMerchant");
		System.out.println("Transaction ID:" + transactionId);
		RefTransactionStatus reftransactionstatus;
		// Set transaction status to be validated
		TransactionStatus status = TransactionStatus.VALIDATEDBYMERCHANT;
		reftransactionstatus = refTransactionStatusService.getRefTransactionStatusById(status.getValue());
		bankTransactionService.updateValidatedBankTransactionById(transactionId, reftransactionstatus);
		
		// Remove session variables
		request.getSession().removeAttribute("sTransferToMerchant");
		request.getSession().removeAttribute("sTransactionIdMerchant");
		
		// Set display message for view
		redirectAttributes.addFlashAttribute("transferToMerchantResult", "Transfer transaction sent for Approval to Customer.");
		return "redirect:merchantHomepage";
	}	
	
	@RequestMapping(value = "/transferFailed.do", method = RequestMethod.GET)
	public String tranferAmountFailed(HttpServletRequest request,
								      final RedirectAttributes redirectAttributes){
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}else{
			// Check for role authorization
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if(!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.ExternalCustomer) && 
					!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Merchant)){
				redirectAttributes.addFlashAttribute("messageLoggedOut", "You have been redirected to login page due to authorization violation.");
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		
		BankUser benefactor = bankUserService.findUserByUserName((String) (request.getSession().getAttribute("sUserName")));
		int refRoleId = benefactor.getRefUserRole().getRefUserRoleId();
		RefTransactionStatus reftransactionstatus;
		
		// Set transaction status to be nonvalidated
		TransactionStatus status = TransactionStatus.NONVALIDATED;
		reftransactionstatus = refTransactionStatusService.getRefTransactionStatusById(status.getValue());
		if(request.getSession().getAttribute("sTransactionId") != null){
			int transactionId = (int) request.getSession().getAttribute("sTransactionId");
			bankTransactionService.updateValidatedBankTransactionById(transactionId, reftransactionstatus);
				
			// Remove session variables
			request.getSession().removeAttribute("sTransfer");
			request.getSession().removeAttribute("sTransactionId");
			// Set display message for view
			redirectAttributes.addFlashAttribute("transferResult", "Transfer transaction failed due to wrong OTP.");
			if(refRoleId == 5)
				return "redirect:externalCustomerHome.do";
			else //If transfer from merchant to some other account was initiated
				return "redirect:merchantHomepage";
		}
		else{ //When Merchant is initiating transfer for another customer/merchant
			int transactionIdMerchant = (int) request.getSession().getAttribute("sTransactionIdMerchant");
			bankTransactionService.updateValidatedBankTransactionById(transactionIdMerchant, reftransactionstatus);
					
			// Remove session variables
			request.getSession().removeAttribute("sTransferToMerchant");
			request.getSession().removeAttribute("sTransactionIdMerchant");	
			// Set display message for view
			redirectAttributes.addFlashAttribute("transferToMerchantResult", "Transfer transaction failed due to wrong OTP.");
			return "redirect:merchantHomepage";	
		}
	}

	@RequestMapping(value = "/viewPendingApproval", method = RequestMethod.GET)
	public String redirectPendingApproval(HttpServletRequest request,
	final RedirectAttributes redirectAttributes){
		return "redirect:merchantAcceptDeclineMoney.do";
	}

	@RequestMapping(value = "/merchantAcceptDeclineMoney.do", method = RequestMethod.GET)
	public ModelAndView viewPendingApprovals(HttpServletRequest request,
			final RedirectAttributes redirectAttributes){
		
		
		BankUser user = bankUserService.findUserByUserName((String) (request.getSession().getAttribute("sUserName")));
    	List<BankTransaction> bankTransactionList = bankTransactionService.getTransactionList(user.getBankUserId());
    	List<PendingMerchantTransactionAuth> pendingMerchantTransactionList = null;
    	
    	if(bankTransactionList != null){
    		pendingMerchantTransactionList = new ArrayList<PendingMerchantTransactionAuth>();
    		
    		for(BankTransaction transaction : bankTransactionList){
    			PendingMerchantTransactionAuth pendingMerchantTransaction = new PendingMerchantTransactionAuth();
    			BankUser tmpRecipient = transaction.getRecipient();
    			BankUser tmpBenefactor = transaction.getBenefactor();
    			
    			String recipient = tmpRecipient.getUserName();
    		
    			Float transactionAmount = transaction.getTransactionAmount();
    			RefTransactionStatus refTransactionStatusId = transaction.getReftransactionstatus();
    			int refStatusId = refTransactionStatusId.getRefTransactionStatusId();
    			
    			//Check if Transfer was made for you
    			if(refStatusId == 6 && tmpBenefactor.getBankUserId().equals(user.getBankUserId())){  
    				
    				pendingMerchantTransaction.setRecipient(recipient);
    				pendingMerchantTransaction.setBenefactor(tmpBenefactor.getUserName());
    				pendingMerchantTransaction.setTransactionAmount(transactionAmount);
    				pendingMerchantTransaction.setTransactionId(transaction.getBankTransactionId());
    				pendingMerchantTransactionList.add(pendingMerchantTransaction);
    				request.getSession().setAttribute("sPendingMerchantList", 1); //Keeps track if the list is not empty
    			}
    			
    			
    		}
    		
    	}
 
		return new ModelAndView("merchantAcceptDeclineMoney","pendingMerchantTransactionList",pendingMerchantTransactionList);
	}	
	
	//User authorizes the merchant
	@RequestMapping(value = "/pendingAuth/{action}/{id}/", method = RequestMethod.POST)
	public String acceptDeclineTransaction	(@PathVariable("action") String action,
			@PathVariable("id") int id,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes){
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}else{
			// Check for role authorization
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if(!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.ExternalCustomer) && 
					!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Merchant)){
				redirectAttributes.addFlashAttribute("messageLoggedOut", "You have been redirected to login page due to authorization violation.");
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		
		BankUser user = bankUserService.findUserByUserName((String) (request.getSession().getAttribute("sUserName")));
		
    	List<BankTransaction> bankTransactionList = bankTransactionService.getTransactionList(user.getBankUserId());
    	int refUserRole = user.getRefUserRole().getRefUserRoleId();
		if(action.equals("accept")){
			if(bankTransactionList != null){
				
				int flag = 0;
				if((int)(request.getSession().getAttribute("sPendingMerchantList")) != 1){ //Got called when No Pending Transaction were present
					redirectAttributes.addFlashAttribute("pendingAuthUrlInjection", "URL Injection Not Allowed!!! Press the relevant button");
					if(refUserRole == 4)
						return "redirect:/merchantHomepage";
					else if(refUserRole == 5)
						return "redirect:/externalCustomerHome.do";
				}
				
				for(BankTransaction transaction : bankTransactionList){
					BankUser tmpuser = transaction.getBenefactor();
					if(transaction.getBankTransactionId() == id && transaction.getReftransactionstatus().getRefTransactionStatusId().equals(6)
							&& tmpuser.getBankUserId().equals(user.getBankUserId())){ //So many checks are added to make URL Injection can't occur
						bankTransactionService.approveTransactionFromMerchant(transaction.getBankTransactionId(), user.getBankUserId());
						flag = 1;
						break;
					}
				}
				if(flag == 0 && (int)(request.getSession().getAttribute("sPendingMerchantList")) != 1)
					redirectAttributes.addFlashAttribute("pendingAuthUrlInjection", "URL Injection Not Allowed!!! Press the relevant button");
				
			}
		}
		else if(action.equals("decline")){
			if(bankTransactionList != null){
				int flag = 0; //Check if Someone Manipulated the URL
				if((int)(request.getSession().getAttribute("sPendingMerchantList")) != 1) //Got called when No Pending Transaction were present
					redirectAttributes.addFlashAttribute("pendingAuthUrlInjection", "URL Injection Not Allowed!!! Press the relevant button");
				
				for(BankTransaction transaction : bankTransactionList){
					BankUser tmpuser = transaction.getBenefactor();
					if(transaction.getBankTransactionId() == id && transaction.getReftransactionstatus().getRefTransactionStatusId().equals(6)
							&& tmpuser.getBankUserId().equals(user.getBankUserId())){
						bankTransactionService.declineTransaction(transaction.getBankTransactionId(), user.getBankUserId());
						flag = 1;
						break;
					}
				}
				if(flag == 0 && (int)(request.getSession().getAttribute("sPendingMerchantList")) != 1)
					redirectAttributes.addFlashAttribute("pendingAuthUrlInjection", "URL Injection Not Allowed!!! Press the relevant button");
				
			}
		}
		
		//Remove session Variable
		request.getSession().removeAttribute("sPendingMerchantList");
		if(refUserRole == 4)
			return "redirect:/merchantHomepage";
		else
			return "redirect:/externalCustomerHome.do";
	}
		
	@RequestMapping(value = "/deleteMerchantUser", method = RequestMethod.POST)
	public String deleteUser(HttpServletRequest request,
			final RedirectAttributes redirectAttributes){
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}else{
			// Check for role authorization
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if(!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.ExternalCustomer)   &&
					!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Merchant)){
				redirectAttributes.addFlashAttribute("messageLoggedOut", "You have been redirected to login page due to authorization violation.");
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		
		BankUser user = bankUserService.findUserByUserName((String) (request.getSession().getAttribute("sUserName")));
		
		bankUserService.updateAccountStatus(user.getUserName(), "RequireDeActivation");
		redirectAttributes.addFlashAttribute("deactiveUser","User is in suspended state. Waiting approval from manager");
		//Remove session Variable
		request.getSession().removeAttribute("sUserName");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/customerUpdateProfile.do", method = RequestMethod.POST)
	public ModelAndView customerUpdateProfile(@ModelAttribute("bankuser") BankUser bankuser, BindingResult bindingResult,
			HttpServletRequest request) {
		BankUserList bankuserList = new BankUserList();
		String UserName = (String) request.getSession().getAttribute("sUserName");
		BankUser tempUser = bankUserService.findUserByUserName(UserName);
		if (tempUser != null) {
			List<BankUser> bankUserRequested = new ArrayList<BankUser>();
			bankUserRequested.add(tempUser);
			bankuserList.setBankUsers(bankUserRequested);
		} 
		return new ModelAndView("extUserAcctPrefs", "bankuserList", bankuserList);
		
	}
	
	@RequestMapping(value = "/customerUpdateProfileConfirm.do", method = RequestMethod.POST)
	public String customerEditProfileConfirm(@Valid BankUser bankuser, ServletRequest request,
			BindingResult bindingResult, Model model, HttpServletRequest httpRequest,
			final RedirectAttributes redirectAttributes) {
		
		// Redirect to login page if session has expired
		if ((httpRequest.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}else{
			// Check for role authorization
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (httpRequest.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if(!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.ExternalCustomer) && 
					!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Merchant)){
				redirectAttributes.addFlashAttribute("messageLoggedOut", "You have been redirected to login page due to authorization violation.");
				httpRequest.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		
		BankUser user = bankUserService.findUserByUserName((String)(httpRequest.getSession().getAttribute("sUserName")));
		int refUserRole = user.getRefUserRole().getRefUserRoleId();
		bankuser.setDateOfBirth(user.getDateOfBirth());
		/* Validating the entered User Details */
		ValidationUtils.invokeValidator(updateBankUserValidator, bankuser, bindingResult);
		BankUser tempUser = bankUserService.findUserByUserName(bankuser.getUserName());
		if (bindingResult.getErrorCount() != 0) {
			redirectAttributes.addFlashAttribute("UpdateError", "Incorrect Value Entered");
			if(refUserRole == 4)
				return "redirect:/merchantHomepage";
			else
				return "redirect:/externalCustomerHome.do";
		}
		if (tempUser != null) {
			
			if (!tempUser.getPhoneNumber().equals(bankuser.getPhoneNumber()))
				tempUser.setPhoneNumber(bankuser.getPhoneNumber());
			if (!tempUser.getResidentialAddress().equals(bankuser.getResidentialAddress()))
				tempUser.setResidentialAddress(bankuser.getResidentialAddress());
			if (!tempUser.getMailingAddress().equals(bankuser.getMailingAddress()))
				tempUser.setMailingAddress(bankuser.getMailingAddress());
				bankUserService.UpdateBankUser(tempUser);
			} else {
				return "Error";
			}
		
		if(refUserRole == 4)
			return "redirect:/merchantHomepage";
		else
			return "redirect:/externalCustomerHome.do";
	}
				
	
	@Transactional
	@RequestMapping(value = "/createSavingAccount.do", method = RequestMethod.POST)
	public String createSavingAccount(@Valid BankAccount bankAccount,
									  HttpServletRequest request,
									  BindingResult bindingResult,
									  final RedirectAttributes redirectAttributes){
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}else{
			// Check for role authorization
			BankUser tempUser = new BankUser();
			tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
			// Populate all fields
			tempUser = bankUserService.findUserByUserName(tempUser);
			if(!tempUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.ExternalCustomer)){
				redirectAttributes.addFlashAttribute("messageLoggedOut", "You have been redirected to login page due to authorization violation.");
				request.getSession().removeAttribute("sUserName");
				return "redirect:/";
			}
		}
		
		BankAccount savingAccount;
		BankAccount checkingAccount;
		float checkingAmount = 0;
		
		// Create temporary bank user.
		BankUser tempUser = new BankUser();
		tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
		
		// Populate all fields
		tempUser = bankUserService.findUserByUserName(tempUser);
		
		// Get Saving Account
		savingAccount = bankAccountService.findSavingAccountByUserId(tempUser.getBankUserId());
		if(savingAccount != null){
			redirectAttributes.addFlashAttribute("createSavingAccResult", "Saving Account already exists");
			return "redirect:externalCustomerHome.do";
		}
		
		checkingAccount = bankAccountService.findCheckingAccountByUserId(tempUser.getBankUserId());
		if(checkingAccount == null){
			redirectAttributes.addFlashAttribute("createSavingAccResult", "No Checking Account found");

			return "redirect:externalCustomerHome.do";
		}
		checkingAmount = checkingAccount.getBalance();
		if(checkingAmount < (2 * StaticConstants.debit_limit)){
			redirectAttributes.addFlashAttribute("createSavingAccResult", "Your checking account need to have atleast $" +
					(2*StaticConstants.debit_limit) + " to create Saving account.");
			return "redirect:externalCustomerHome.do";
		}
		
		bankAccount.setBankuser(tempUser);
		bankAccount.setCreditLimit(StaticConstants.credit_limit);
		bankAccount.setDebitLimit(StaticConstants.debit_limit);
		bankAccount.setAccountCreatedOn(new Date());
		bankAccount.setAccountLastAccessedOn(new Date());
		bankAccount.setBalance(StaticConstants.debit_limit);
		bankAccount.setRefaccounttype(refAccountTypeService.findByName("Saving"));
		bankAccountService.setBankAccount(bankAccount);
		
		bankAccountService.updateBankAccountBalance(checkingAccount.getBankAccountId(), checkingAmount - StaticConstants.debit_limit);
		redirectAttributes.addFlashAttribute("createSavingAccResult", "Saving Account successfully created.");
		return "redirect:externalCustomerHome.do";
	}
}
