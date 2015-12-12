package com.spring.service;

import java.util.List;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.BankUserDAO;
import com.spring.model.BankUser;

@Service("bankUserService")
public class BankUserService {
	@Autowired
	BankUserDAO bankUserDao;
	
	public BankUserDAO getBankUserDao () {
		return this.bankUserDao;
	}
	
	public void setBankUserDao(BankUserDAO bankUserDAO) {
		this.bankUserDao = bankUserDAO;
	}
	
	public BankUser findUserByUserName (BankUser user) {
		return bankUserDao.findByUserName(user.getUserName());
	}
	
	public BankUser findUserByUserName (String user) {
		return bankUserDao.findByUserName(user);
	}
	
	public BankUser findUserByEmail (BankUser user) {
		return bankUserDao.findByEmail(user.getEmail());
	}

	public BankUser findUserByEmail(String email) {
		return bankUserDao.findByEmail(email);
	}
	
	public void updateAccountStatus(String userName, String accountStatus) {
		bankUserDao.updateAccountStatus(userName, accountStatus);

	}
	
	public List<BankUser> findUsersOfParticularRole(int RoleID){
		return bankUserDao.findUsersOfParticularRole(RoleID);
	}
	
	public void updateUserPassword(String userName, String userPassword) {
		bankUserDao.updateUserPassword(userName, userPassword);
		
	}
	public void updatePIIAuthorization(String userName, String PIIAuthorization) {
		bankUserDao.updatePIIAuthorization(userName, PIIAuthorization);
		
	}
	public void setBankUser(BankUser tempUser){
		 bankUserDao.setBankUser(tempUser);
	}
	public void UpdateBankUser(BankUser tempUser){
		 bankUserDao.updateBankUser(tempUser);
	}
	
	public void clearBankUser(BankUser tempUser){
		bankUserDao.clearBankUserDetails(tempUser);
	}
	
	public List<BankUser> findUsersByAccountStatusAndRoleId(String accountStatus,int roleId){
		return bankUserDao.findUsersByAccountStatusAndRoleId(accountStatus, roleId);
	}

	public void updateLastLoginOnByUserName(String userName) {
		bankUserDao.updateLastLoginOnByUserName(userName);		
	}

}
