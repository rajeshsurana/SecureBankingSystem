package com.spring.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.service.EmailService;

/**
 * @author mahathi
 *
 */
@Controller
public class EmailController {
	
	private EmailService emailService;

	public EmailService getEmailService() {
		return emailService;
	}
	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sendEmail.do",method = RequestMethod.POST)
	public String doSendEmail(HttpServletRequest request,
			                  final RedirectAttributes redirectAttributes) {
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:";
		}
		
		// takes input from e-mail form
		String recipientAddress = "mahathi.xyz@xyz.com";
		String fromAddress = "southwestbank.tech@gmail.com";
		String subject = "test Email";
		String message = "E-Mail Sent Successfully";

		// prints debug info
		System.out.println("To: " + recipientAddress);
		System.out.println("Subject: " + subject);
		System.out.println("Message: " + message);

		emailService.ReadyToSendEmail(recipientAddress, fromAddress, subject, message);

		// forwards to a dummy result page as of now
		return "ActionComplete";
	}
	

}
