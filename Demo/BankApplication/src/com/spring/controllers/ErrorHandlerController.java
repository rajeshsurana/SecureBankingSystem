package com.spring.controllers;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller

class CustomErrorController {

 @RequestMapping("error")
 public String customError(HttpServletRequest request, HttpServletResponse response,HttpSession session, Model model) {
  // retrieve some useful information from the request
     System.out.println("Custom Error Handler");    

	 
	 if((request.getAttribute("org.springframework.web.servlet.DispatcherServlet.EXCEPTION")!=null)){
	 if((request.getAttribute("org.springframework.web.servlet.DispatcherServlet.EXCEPTION")).getClass().equals(org.springframework.validation.BindException.class)){
		 org.springframework.validation.BindException bEx = (org.springframework.validation.BindException) request.getAttribute("org.springframework.web.servlet.DispatcherServlet.EXCEPTION");
		
		 System.out.println("Bind Error");
		 String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		 System.out.println("URL: "+ requestUri);
		// bindingresult.reject("inputError","Login.Binding.Error");
		 session.setAttribute("inputError","Invalid Input. Please try again");
		 //model.addAttribute("inputError","Login.Binding.Error");
		 
		 return "redirect:/";
	 }
	}
  Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
  Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
  // String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
  String exceptionMessage = getExceptionMessage(throwable, statusCode);
  String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
  String redirectUri = requestUri.substring(requestUri.lastIndexOf("/"));
  model.addAttribute("redirectUri", redirectUri); 

  
  if (requestUri == null) {
   requestUri = "Unknown";
  }
  String message = MessageFormat.format("Error code \"{0}\" returned with message \"{2}\"",
   statusCode, requestUri, exceptionMessage
  );
  
  String req = request.getLocalName();
  String req1 = request.getLocalAddr();
  model.addAttribute("errorMessage", message); 
  //response.setHeader("Refresh", "2;url=https://group15.mobicloud.asu.edu");
  SecurityContextHolder.clearContext();
  session.invalidate();
  String contextPath = request.getContextPath();

  response.setHeader("Refresh", "2;url=/");
  return "customError";
 }
 private String getExceptionMessage(Throwable throwable, Integer statusCode) {
  if (throwable != null) {
   return throwable.getLocalizedMessage();
  }
  HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
  return httpStatus.getReasonPhrase();
 }
}
