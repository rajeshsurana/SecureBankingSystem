package com.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ErrorController {	
	
	
	@ExceptionHandler(Throwable.class)
	public String handleException(Throwable t) {		
		return "Error";
	}
	
	@ExceptionHandler(Exception.class)
	public String handleError(Throwable t) {		
		return "Error";
	}

}
