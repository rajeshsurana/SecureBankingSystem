package com.spring.service;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 * Modified : Mahathi
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.UserVerificationDAO;
import com.spring.model.BankUser;
import com.spring.model.UserVerification;


/**
 * @author mahathi
 *
 */
@Service("userVerificationService")
public class UserVerificationService {
	@Autowired
	UserVerificationDAO userVerificationDao;
	
	public UserVerificationDAO getRefUserRoleDao () {
		return this.userVerificationDao;
	}
	
	public void setRefUserRoleDao (UserVerificationDAO userVerificationDAO) {
		this.userVerificationDao = userVerificationDAO;
	}
	
	public UserVerification findUserVerificationDetailsByUserName (UserVerification user) {
		return userVerificationDao.findUserVerificationDetailsByUserName(user);
	}
	
	public void setUserVerificationDetails(UserVerification userVerification){
		userVerificationDao.setUserVerificationDetails(userVerification);
	}
	
	public void clearUserVerificationDetails(UserVerification userVerification){
		userVerificationDao.clearUserVerificationDetails(userVerification);
	}
}
