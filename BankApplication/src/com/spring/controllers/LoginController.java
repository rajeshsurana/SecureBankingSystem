package com.spring.controllers;

import java.util.Base64;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.javalite.activeweb.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.model.AccessLog;
import com.spring.model.BankUser;
import com.spring.service.AccessLogService;
import com.spring.service.BankAccountService;
import com.spring.service.BankUserService;
import com.spring.util.AuthenticationHandler;
import com.spring.util.StaticConstants;
import com.spring.util.SuspiciousActivityType;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

/**
 * @author mahathi
 *
 */
@Controller
public class LoginController {

	private BankUserService bankUserService;
	private AccessLogService accessLogService;
	private BankAccountService bankAccountService;
	
	private AuthenticationHandler auth = new AuthenticationHandler();

	@Autowired
	private ReCaptchaImpl reCaptchaService = null;

	public BankUserService getUserService() {
		return this.bankUserService;
	}

	public AccessLogService getAccessLogService() {
		return this.accessLogService;
	}
	
	

	@Autowired
	public void setAccessLogService(AccessLogService accessLogServ) {
		this.accessLogService = accessLogServ;
	}
	
	@Autowired
	public void setBankAccountService(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}

	@Autowired
	public void setUserService(BankUserService userServ) {
		this.bankUserService = userServ;
	}

	@ModelAttribute("bankUser")
	public BankUser createModel() {
		return new BankUser();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(Model model, 
						@ModelAttribute("messageLoggedOut") final String messageLoggedOut,
						HttpServletRequest request) {
		// Load the messages 
		model.addAttribute("messageLoggedOut", messageLoggedOut);
		
		// Generate Captcha
	 	String encodedArr = generateCaptcha(request);
		model.addAttribute("imageString", encodedArr);
		
		return "login";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String login(@Valid BankUser user, 
					    ServletRequest request, 
					    BindingResult bindingResult, 
					    Model model,
					    HttpServletRequest httpRequest,
					    final RedirectAttributes redirectAttributes,
					    @RequestParam("captchaCode") String inCaptchaCode) {
		
		HttpSession session = httpRequest.getSession();
		//session.setMaxInactiveInterval(15);

		
		  
		// Access Log updates
		AccessLog accessLog = new AccessLog();
		
		// User name
		accessLog.setUserName(user.getUserName());

		// Log date and time
		accessLog.setAccessedOn(new Date());

		try {
			//String ipAddress = null;
			//ipAddress = httpRequest.getParameter("ipAddress").toString();
			String ipAddress = httpRequest.getHeader("X-FORWARDED-FOR");  
			if (ipAddress == null) {  
			   ipAddress = httpRequest.getRemoteAddr();  
				   
			 }
			System.out.println("Current IP address : " + ipAddress);
			
			// Log IP address
			accessLog.setIPAddress(ipAddress);
			
						
		/*	String macAddress = null;
			macAddress = httpRequest.getParameter("macAddress").toString();			
			System.out.println("Current MAC address : " + macAddress);
			
			// Log device id - MAC address
			accessLog.setDeviceId(macAddress);*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		// Suspicious activity set to Non-suspicious
		accessLog.setSuspiciousActivity(SuspiciousActivityType.NONSUSPICIOUS.getValue());

		// Adding to database
		int accessLogId = accessLogService.addAccessLog(accessLog);

		// First level check reCaptcha
/*		String challenge = request.getParameter("recaptcha_challenge_field");
		String response = request.getParameter("recaptcha_response_field");
		String remoteAddr = request.getRemoteAddr();
		// reCaptchaResponse.isValid() removed 
		ReCaptchaResponse reCaptchaResponse = reCaptchaService.checkAnswer(remoteAddr, challenge, response);*/
		
		boolean reCaptchaResponse = false;
		
		String captchaCode = (String) httpRequest.getSession().getAttribute("sCaptchaCode");
		
		if(inCaptchaCode.equals(captchaCode)){
			reCaptchaResponse = true;
		}

		if (!reCaptchaResponse) {

			// update accessLog suspicious activity value for wrong captcha
			accessLogService.updateLogSuspiciousActivity(accessLogId, SuspiciousActivityType.WRONGCAPTCHA.getValue());

			// Generate Captcha
		 	String encodedArr = generateCaptcha(httpRequest);
			model.addAttribute("imageString", encodedArr);
			
			model.addAttribute("message", "Try again reCaptcha!");
			return "login";
		}

		// Blocking machine for 1 hour if 9 unsuccessful login attempts for same
		// MAC address
		if (accessLogService.checkFailedAttemptsLastHourByDeviceId(accessLog.getDeviceId()) >= 9) {
			// update accessLog suspicious activity value
			accessLogService.updateLogSuspiciousActivity(accessLogId,
					SuspiciousActivityType.SECURITYALERTDEVICE.getValue());
			bindingResult.rejectValue("messageDevice", "You device has been blocked for an hour due to suspicious activity.");
			
			// Generate Captcha
		 	String encodedArr = generateCaptcha(httpRequest);
			model.addAttribute("imageString", encodedArr);
			
			return "login";
		}

		// Second level check for userName
		BankUser tempUser = bankUserService.findUserByUserName(user);
		if (tempUser != null) {

			// Blocking user for 1 hour if 3 unsuccessful login attempts for
			// same username
			if (accessLogService.checkFailedAttemptsLastHourByUsername(accessLog.getUserName()) >= 3) {
				// update accessLog suspicious activity value
				accessLogService.updateLogSuspiciousActivity(accessLogId,
						SuspiciousActivityType.SECURITYALERTUSER.getValue());
				bindingResult.rejectValue("userName", "BlockedUser.BankUser.userName");
				
				// Generate Captcha
			 	String encodedArr = generateCaptcha(httpRequest);
				model.addAttribute("imageString", encodedArr);
				
				return "login";
			}

			// Third level check for password
			System.out.println(tempUser.toString());
			if (auth.matches(user.getUserPassword(), tempUser.getUserPassword()) 
					&& reCaptchaResponse) {
				if (tempUser.getAccountStatus() != null
						&& tempUser.getAccountStatus().equals(StaticConstants.Active_AccountStatus)) {
					
					if(httpRequest.getSession().getAttribute("sUserName") !=null){
						redirectAttributes.addFlashAttribute("multipleSession", 
								"Multiple sessions not allowed. Please exit all windows and try again!");
						return "redirect:/";
					}
					
					httpRequest.getSession().setAttribute("sUserName", user.getUserName());
					
					// Update last login On time in bank account
					bankUserService.updateLastLoginOnByUserName(tempUser.getUserName());
					
					if (tempUser.getRefUserRole().getRefUserRoleId() == 5) {
						// Mapped in CustomerHomeController
						return "redirect:externalCustomerHome.do";
					} else if (tempUser.getRefUserRole().getRefUserRoleId() == 6) {
						// Mapped in GovernmentController
						return "govtHomepage";
					} else if (tempUser.getRefUserRole().getRefUserRoleId() == 1) {
						// Mapped in SystemAdminController
						return "sysAdminhome";
					} else if (tempUser.getRefUserRole().getRefUserRoleId() == 2) {
						// Mapped in ManagerController
						return "ManagerHome";
					} else if (tempUser.getRefUserRole().getRefUserRoleId() == 3) {
						// Mapped in BankUserController
						return "homeInternalEmployee";
					} else if (tempUser.getRefUserRole().getRefUserRoleId() == 4) {
						// Mapped in CustomerHomeController
						return "redirect:merchantHomepage";
					} else {
						return "Error";
					}
				} else if (tempUser.getAccountStatus().equals(StaticConstants.Require_Activation)) {
					// update accessLog suspicious activity value
					accessLogService.updateLogSuspiciousActivity(accessLogId,
							SuspiciousActivityType.REQUIREACTIVATION.getValue());

					return "ActivateAccount";
				} else {
					// update accessLog suspicious activity value
					accessLogService.updateLogSuspiciousActivity(accessLogId,
							SuspiciousActivityType.INACTIVEACCOUNT.getValue());

					bindingResult.rejectValue("userName", StaticConstants.InactiveAccountMessage);
					
					// Generate Captcha
				 	String encodedArr = generateCaptcha(httpRequest);
					model.addAttribute("imageString", encodedArr);
					
					return "login";
				}
			} else {
				// update accessLog suspicious activity value
				accessLogService.updateLogSuspiciousActivity(accessLogId,
						SuspiciousActivityType.WRONGPASSWORD.getValue());

				bindingResult.rejectValue("userPassword", "UnmatchedPassword.bankUser.userPassword");
				
				// Generate Captcha
			 	String encodedArr = generateCaptcha(httpRequest);
				model.addAttribute("imageString", encodedArr);
				
				return "login";
			}
		} else {
			// update accessLog suspicious activity value
			accessLogService.updateLogSuspiciousActivity(accessLogId, SuspiciousActivityType.WRONGUSERNAME.getValue());

			bindingResult.rejectValue("userName", "NonexistingUser.bankUser.userName");
			
			// Generate Captcha
		 	String encodedArr = generateCaptcha(httpRequest);
			model.addAttribute("imageString", encodedArr);
			
			return "login";
		}
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public String resetPasswordShow() {
		return "resetPassword";
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public String resetPasswordProcess(@Valid BankUser user, BindingResult bindingResult,
										HttpServletRequest httpRequest,
										final RedirectAttributes redirectAttributes) {


		BankUser tempUser = bankUserService.findUserByEmail(user);
		if (tempUser != null) {
			httpRequest.getSession().setAttribute("sUserName", tempUser.getUserName());
			// To tell verifyOTP to redirect to reset password and confirm page
			httpRequest.getSession().setAttribute("sPassReset", "resetAndConfirm");
			System.out.println("Debug Email:" + tempUser.getEmail());
			return "redirect:sendOTP.do";
		}
		bindingResult.rejectValue("email", "NonexistingEmail.bankUser.email");
		return "resetPassword";
	}

	@RequestMapping(value = "/ActivateAccount.do", method = RequestMethod.POST)
	public String ActivateAccount(@Valid BankUser bankUser, 
								  BindingResult bindingResult,
								  HttpServletRequest httpRequest,
								  final RedirectAttributes redirectAttributes) {
		
		

		BankUser tempUser = bankUserService.findUserByEmail(bankUser);
		if (tempUser != null) {
			httpRequest.getSession().setAttribute("sUserName", tempUser.getUserName());
			// To tell verifyOTP to redirect to reset password and confirm page
			httpRequest.getSession().setAttribute("sActivateAccount", "verifyAccount");
			System.out.println("Debug Email:" + tempUser.getEmail());
			return "redirect:sendOTP.do";
		}
		bindingResult.rejectValue(StaticConstants.bankuserEmail, StaticConstants.NonExistingEmailMessage);
		return "ActivateAccount";
	}

	@RequestMapping(value = "/resetAndConfirm", method = RequestMethod.GET)
	public String resetAndConfirmShow(HttpServletRequest request,
									  final RedirectAttributes redirectAttributes) {
		
		
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}
		
		return "resetAndConfirm";
	}

	@RequestMapping(value = "/resetAndConfirm", method = RequestMethod.POST)
	public String resetAndConfirmProcess(BankUser bankUser, 
										 Model model,
										 BindingResult bindingResult, 
										 HttpServletRequest request,	
										 final RedirectAttributes redirectAttributes,
										 @RequestParam("confirmpass") String confirmpass) {
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}
		
		String action = "";
		if ((request.getSession().getAttribute("sPassReset")) != null
				&& ((String) (request.getSession().getAttribute("sPassReset"))).equals("resetAndConfirmValid")) {
			
			// **Validate password**
			// one small case letter
			// one capital case letter
			// one special char
			// one number
			// length 8 to 20
			if(!(bankUser.getUserPassword()).matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})")){
				bindingResult.rejectValue("userPassword", "WeakPassword.bankUser.userPassword");
				return "resetAndConfirm";
			}
			
			// Check if password and confirm password are same
			if(confirmpass == null || !confirmpass.equals(bankUser.getUserPassword())){
				model.addAttribute("confirmpass", "Confirm password field does not match with password field.");
				return "resetAndConfirm";
			}

			bankUserService.updateUserPassword((String) (request.getSession().getAttribute("sUserName")), bankUser.getUserPassword());
			action = "passwordUpdated";
		} else {
			action = "resetPassword";
		}
		request.getSession().removeAttribute("sPassReset");
		return action;
	}

	@RequestMapping(value = "/passwordUpdated", method = RequestMethod.GET)
	public String passwordUpdatedShow() {

		return "passwordUpdated";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(final RedirectAttributes redirectAttributes, 
						 HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		session.invalidate();
		
		redirectAttributes.addFlashAttribute("messageLoggedOut", "You have been successully logged out.");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/goToHome")
	public String goToHome(final RedirectAttributes redirectAttributes, 
						 HttpServletRequest request) {
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			return "redirect:/";
		} else {
			String UserName = (String) request.getSession().getAttribute("sUserName");
			BankUser currentUser = bankUserService.findUserByUserName(UserName);
			if (currentUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.ExternalCustomer)) {
				// Mapped in CustomerHomeController
				return "redirect:externalCustomerHome.do";
			} else if (currentUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Government)) {
				// Mapped in GovernmentController
				return "govtHomepage";
			} else if (currentUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.SystemAdmin)) {
				// Mapped in SystemAdminController
				return "sysAdminhome";
			} else if (currentUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Manager)) {
				// Mapped in ManagerController
				return "ManagerHome";
			} else if (currentUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.InternalEmployee)) {
				// Mapped in BankUserController
				return "homeInternalEmployee";
			} else if (currentUser.getRefUserRole().getBankUserRoleName().equals(StaticConstants.Merchant)) {
				// Mapped in CustomerHomeController
				return "redirect:merchantHomepage";
			} else {
				return "Error";
			}
		}		
	}
	
	// Generate Captcha
	private String generateCaptcha(HttpServletRequest request){
		String captchaCode = "";
		Random rand = new Random();
		for (int i = 0; i < 6; i++) {
			captchaCode = captchaCode + StaticConstants.alphabet.charAt(rand.nextInt(StaticConstants.alphabet.length()));
		}
		request.getSession().setAttribute("sCaptchaCode", captchaCode);
		byte[] imgArr = Captcha.generateImage(captchaCode);
		//TODO
	 	return Base64.getEncoder().encodeToString(imgArr);
	}
	
}
