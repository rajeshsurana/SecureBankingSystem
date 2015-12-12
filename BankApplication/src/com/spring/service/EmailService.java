package com.spring.service;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author mahathi
 *
 */
@Service("emailService")
public class EmailService {
	
	@Autowired
	private JavaMailSender emailSender; // MailSender interface defines a strategy
										// for sending simple mails
 
	public void ReadyToSendEmail(String toAddress, String fromAddress, String subject, String msgBody) { 
		SimpleMailMessage Message = new SimpleMailMessage();
		Message.setFrom(fromAddress);
		Message.setTo(toAddress);
		Message.setSubject(subject);
		Message.setText(msgBody);
		emailSender.send(Message);
	}
	
	

	
}
