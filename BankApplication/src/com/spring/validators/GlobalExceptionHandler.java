package com.spring.validators;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "customError";

    @ExceptionHandler(value = {Exception.class, RuntimeException.class, java.lang.Throwable.class})
    public ModelAndView defaultErrorHandler(HttpServletRequest request,HttpServletResponse response, Exception e) {
        System.out.println("Global Exception Handler");    
    	ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);
            request.getSession().invalidate();
            String contextPath = request.getContextPath();
            
            response.setHeader("Refresh", "1;url="+contextPath);

     //   mav.addObject("datetime", new Date());
       // mav.addObject("exception", e);
      //  mav.addObject("url", request.getRequestURL());
        return mav;
    }
}