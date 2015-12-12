package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.NewBankUserDAO;
import com.spring.model.NewBankUser;
import com.spring.model.RefUserRole;

/**
 * @author mahathi
 *
 */
@Service("newBankUserService")
public class NewBankUserService {

	@Autowired
	NewBankUserDAO newBankUserDAO;

	public NewBankUserDAO getNewBankUserDAO() {
		return newBankUserDAO;
	}

	public void setNewBankUserDAO(NewBankUserDAO newBankUserDAO) {
		this.newBankUserDAO = newBankUserDAO;
	}

	public NewBankUser findUserByUserName(String userName) {
		return newBankUserDAO.findByUserName(userName);
	}
	
	public NewBankUser findUserByUserEmail(String userEmail) {
		return newBankUserDAO.findByUserEmail(userEmail);
	}

	public List<NewBankUser> findUsersForApprovalforParticularAccount(String accountStatus,String ManagerName,int roleId) {
		return newBankUserDAO.findUsersByAccountStatusAndAssignedManager(accountStatus,ManagerName,roleId);
	}
	public List<NewBankUser> findUsersForApproval(String accountStatus) {
		return newBankUserDAO.findUsersByAccountStatus(accountStatus);
	}

	public void updateAccountStatus(String userName, String accountStatus) {
		newBankUserDAO.updateAccountStatus(userName, accountStatus);

	}

	public void setNewBankUser(NewBankUser tempUser) {
		newBankUserDAO.setNewBankUser(tempUser);
	}

	public void clearNewBankUser(NewBankUser tempUser) {
		newBankUserDAO.clearNewBankUserDetails(tempUser);
	}

}
