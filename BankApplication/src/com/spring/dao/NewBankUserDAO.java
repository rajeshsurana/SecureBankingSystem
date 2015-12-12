package com.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.model.BankUser;
import com.spring.model.NewBankUser;
import com.spring.util.AuthenticationHandler;
import com.spring.util.CustomHibernateDAOSupport;

/**
 * @author mahathi
 *
 */
@Repository("newBankUserDao")
public class NewBankUserDAO extends CustomHibernateDAOSupport {
	private AuthenticationHandler auth = new AuthenticationHandler();

	public List<NewBankUser> findUsersByAccountStatusAndAssignedManager(String accountStatus,String ManagerName,int roleID){
		List list = getHibernateTemplate().find("FROM NewBankUser where accountStatus = ? and assignedToManager = ? and refUserRoleId = ? ", accountStatus,ManagerName,roleID);
		return list;
	}
	
	public List<NewBankUser> findUsersByAccountStatus(String accountStatus){
		List list = getHibernateTemplate().find("FROM NewBankUser where accountStatus = ?", accountStatus);
		return list;
	}
	public NewBankUser findByUserName(String username){
		List list = getHibernateTemplate().find("FROM NewBankUser where userName = ?", username);
		if (list.size()>0){
			return (NewBankUser)list.get(0);
		}else{
			return null;
		}
	}
	
	public NewBankUser findByUserEmail(String useremail){
		List list = getHibernateTemplate().find("FROM NewBankUser where email = ?", useremail);
		if (list.size()>0){
			return (NewBankUser)list.get(0);
		}else{
			return null;
		}
	}
	
	public void updateAccountStatus(String userName, String AccountStatus) {
		List list = getHibernateTemplate().find("from NewBankUser where userName = ?", userName);
		if (list.size()>0){
			NewBankUser bankUser = (NewBankUser)list.get(0);
			bankUser.setAccountStatus(AccountStatus);
			getHibernateTemplate().update(bankUser);
		}
		
	}
	
	public void setNewBankUser(NewBankUser newBankUser){
		//	Hash password
		//newBankUser.setUserPassword(auth.encode(newBankUser.getUserPassword()));
		getHibernateTemplate().save(newBankUser);
	}
	
	public void clearNewBankUserDetails(NewBankUser newBankUser) {
		getHibernateTemplate().delete(newBankUser);
	}
	
}
