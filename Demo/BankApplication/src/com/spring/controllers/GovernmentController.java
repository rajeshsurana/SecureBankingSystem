package com.spring.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.model.BankUser;
import com.spring.service.BankUserService;
import com.spring.util.StaticConstants;

@Controller
public class GovernmentController {
	private BankUserService bankUserService;

	public BankUserService getBankUserService() {
		return bankUserService;
	}

	@Autowired
	public void setBankUserService(BankUserService bankUserService) {
		this.bankUserService = bankUserService;
	}
	
	@RequestMapping(value = "/AcceptPIIRequest.do", method = RequestMethod.POST)
	public String acceptPIIRequest(HttpServletRequest request, 
								   final RedirectAttributes redirectAttributes) {
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}
		
		List<BankUser> adminUsers = bankUserService.findUsersOfParticularRole(1);
		if(adminUsers!=null &&!adminUsers.isEmpty()){
			for(BankUser tempUser:adminUsers){
				bankUserService.updatePIIAuthorization(tempUser.getUserName(), StaticConstants.piiAuthorized);
			}
		}
		return "govtHomepage";
	}
	
	@RequestMapping(value = "/DenyPIIRequest.do", method = RequestMethod.POST)
	public String DenyPIIRequest(HttpServletRequest request, 
								 final RedirectAttributes redirectAttributes) {
		
		// Redirect to login page if session has expired
		if ((request.getSession().getAttribute("sUserName")) == null ) {
			redirectAttributes.addFlashAttribute("messageLoggedOut", "Your session has expired! Please login again.");
			return "redirect:/";
		}
		
		List<BankUser> adminUsers = bankUserService.findUsersOfParticularRole(1);
		if(adminUsers!=null &&!adminUsers.isEmpty()){
			for(BankUser tempUser:adminUsers){
				bankUserService.updatePIIAuthorization(tempUser.getUserName(), StaticConstants.piiNotAuthorized);
			}
		}
		return "govtHomepage";
	}

}
