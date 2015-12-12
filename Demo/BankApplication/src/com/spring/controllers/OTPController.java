package com.spring.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.model.BankUser;
import com.spring.model.UserVerification;
import com.spring.service.BankUserService;
import com.spring.service.EmailService;
import com.spring.service.UserVerificationService;
import com.spring.util.StaticConstants;

/**
 * @author mahathi
 *
 */
@Controller
public class OTPController {

	private UserVerificationService userVerificationService;
	private BankUserService bankUserService;
	private EmailService emailService;

	private static final String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private Random rand;
	private int length;

	public OTPController() {
		this.rand = new Random();
		this.length = 6;
	}

	@Autowired
	public void setUserService(BankUserService userServ) {
		this.bankUserService = userServ;
	}

	@ModelAttribute("bankUser")
	public BankUser createModel() {
		return new BankUser();
	}

	public UserVerificationService getUserService() {
		return userVerificationService;
	}

	@Autowired
	public void setUserService(UserVerificationService userVerificationService) {
		this.userVerificationService = userVerificationService;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	@RequestMapping(value = "/sendOTP.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String doSendOTP(HttpServletRequest request) {
		
		// Login authentication
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			return "redirect:/";
		}

		BankUser tempUser = new BankUser();
		// It is very important to set session attribute "sUserName" before
		// calling sendOTP.do
		
		tempUser.setUserName((String) (request.getSession().getAttribute("sUserName")));
		tempUser = bankUserService.findUserByUserName(tempUser);
		String subject = "Secure OTP for transaction";
		StringBuilder message = new StringBuilder();

		message.append("Your OTP for the current transaction is :: \t ");
		String verificationCode = "";
		for (int i = 0; i < length; i++) {
			verificationCode = verificationCode + alphabet.charAt(rand.nextInt(alphabet.length()));
		}

		message.append(verificationCode);

		// prints debug info
		System.out.println("To: " + StaticConstants.fromAddress);
		System.out.println("Subject: " + subject);
		System.out.println("Message: " + message);

		/*
		 * temp creating the userVerification Object manually since the session
		 * objects have not been designed
		 */
		UserVerification tempUserVerification = new UserVerification();
		tempUserVerification.setBankuser((String) (request.getSession().getAttribute("sUserName")));
		

		/*
		 * Setting the expiration time as 20(2 minutes for testing now) minutes
		 * from the time the code was generated
		 */
		Date expirationDate = new Date();
		expirationDate.setTime(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(expirationDate);
		cal.add(Calendar.MINUTE, 5);
		expirationDate = cal.getTime();
		
		tempUserVerification.setVerificationCode(verificationCode);
		tempUserVerification.setCodeExpiresOn(expirationDate);

		// Debug
		System.out.println(tempUserVerification.toString());
		UserVerification existingUser = userVerificationService.findUserVerificationDetailsByUserName(tempUserVerification);
		if(existingUser == null){
			userVerificationService.setUserVerificationDetails(tempUserVerification);
			emailService.ReadyToSendEmail(tempUser.getEmail(), StaticConstants.fromAddress, subject, message.toString());
		}
		else{
			userVerificationService.clearUserVerificationDetails(existingUser);
			userVerificationService.setUserVerificationDetails(tempUserVerification);
			emailService.ReadyToSendEmail(tempUser.getEmail(), StaticConstants.fromAddress, subject, message.toString());
		}
		return "verifyOTP";
	}

	@RequestMapping(value = "/verifyOTP.do", method = RequestMethod.POST)
	public String verifyOTP(@RequestParam(value = "otp") String otp, 
										  HttpServletRequest request,
										  final RedirectAttributes redirectAttribute) {
		
	
		/*
		 * temp setting for getting the user manually since the session objects
		 * have not been designed
		 */

		UserVerification tempUser = new UserVerification();
		tempUser.setBankuser((String) (request.getSession().getAttribute("sUserName")));
		System.out.println("Username set in session: " + (String) (request.getSession().getAttribute("sUserName")));
		
		// By default return set to Error page
		String action = "Error";
		tempUser = userVerificationService.findUserVerificationDetailsByUserName(tempUser);
		Date currentTime = new Date();
		currentTime.setTime(System.currentTimeMillis());

		if (tempUser != null) {
			if (otp.equals(tempUser.getVerificationCode())) {
				if (currentTime.before(tempUser.getCodeExpiresOn()))
					/*
					 * View for Successful OTP completion view or the following
					 * logic
					 */
					if ((request.getSession().getAttribute("sPassReset")) != null && ((String) (request.getSession().getAttribute("sPassReset"))).equals("resetAndConfirm")) {
						action = "resetAndConfirm";
						request.getSession().setAttribute("sPassReset", "resetAndConfirmValid");
					} else if ((request.getSession().getAttribute("sTransfer")) != null && ((String) (request.getSession().getAttribute("sTransfer"))).equals("transfer")) {
						action = "redirect:transferSuccessful.do";
					} else if ((request.getSession().getAttribute("sActivateAccount")) != null && ((String) (request.getSession().getAttribute("sActivateAccount"))).equals("verifyAccount")) {
						bankUserService.updateAccountStatus(tempUser.getBankuser(), StaticConstants.Active_AccountStatus);
						action = "resetAndConfirm";
						request.getSession().setAttribute("sPassReset", "resetAndConfirmValid");
					} else if ((request.getSession().getAttribute("sTransferToMerchant")) !=null && ((String) (request.getSession().getAttribute("sTransferToMerchant"))).equals("transferToMerchant")){
						action = "redirect:transferToMerchantSuccessful.do";
					} else {
						action = "ActionComplete";
					}
				else
					/* View Required the OTP has expired view */
					action = "Timeout";
			} else if((request.getSession().getAttribute("sTransfer")) != null) {
				action = "redirect:transferFailed.do";
			} else if((request.getSession().getAttribute("sPassReset")) != null) {
				redirectAttribute.addFlashAttribute("passwordResetResult", "Incorrect OTP Entered.Try Again");
				action = "redirect:resetPassword";
			} else if ((request.getSession().getAttribute("sTransferToMerchant")) != null){
				action = "redirect:transferFailed.do";
			}
		} else{
			action="redirect:sendOTP.do";			
		}
		
		/* Resetting the lastGenerated password in the database */
		userVerificationService.clearUserVerificationDetails(tempUser);
		return action;
	}

	
}
