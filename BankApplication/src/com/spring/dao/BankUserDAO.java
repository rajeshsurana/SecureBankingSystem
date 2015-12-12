package com.spring.dao;

import java.util.Date;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.model.BankUser;
import com.spring.model.NewBankUser;
import com.spring.model.RefUserRole;
import com.spring.model.UserVerification;
import com.spring.util.AuthenticationHandler;
import com.spring.util.CustomHibernateDAOSupport;

@Repository("bankUserDao")
public class BankUserDAO extends CustomHibernateDAOSupport {
	private AuthenticationHandler auth = new AuthenticationHandler();
	public BankUser findByUserName(String username){
		List list = getHibernateTemplate().find("FROM BankUser where userName = ?", username);
		if (list.size()>0){
			return (BankUser)list.get(0);
		}else{
			return null;
		}
	}	
	
	public List<BankUser> findUsersByAccountStatusAndRoleId(String accountStatus,int roleID){
		List list = getHibernateTemplate().find("FROM BankUser where accountStatus = ?  and refUserRoleId = ? ", accountStatus,roleID);
		return list;
	}
	 
	public BankUser findByEmail(String email){
		List list = getHibernateTemplate().find("from BankUser where email = ?", email);
		if (list.size()>0){
			return (BankUser)list.get(0);
		}else{
			return null;
		}
	}
	
	public List<BankUser> findUsersOfParticularRole(int RoleId){
		List list = getHibernateTemplate().find("from BankUser where refUserRoleId = ?", RoleId);
		if (list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	
	public void updateBankUser(BankUser bankuser){
		getHibernateTemplate().update(bankuser);
	}

	public void updateUserPassword(String userName, String userPassword) {
		List list = getHibernateTemplate().find("from BankUser where userName = ?", userName);
		if (list.size()>0){
			BankUser bankUser = (BankUser)list.get(0);
			//	Hash password uring password reset
			bankUser.setUserPassword(auth.encode(userPassword));
			getHibernateTemplate().update(bankUser);
		}
		
	}
	public void updateAccountStatus(String userName, String AccountStatus) {
		List list = getHibernateTemplate().find("from BankUser where userName = ?", userName);
		if (list.size()>0){
			BankUser bankUser = (BankUser)list.get(0);
			bankUser.setAccountStatus(AccountStatus);
			getHibernateTemplate().update(bankUser);
		}
		
	}
	
	public void setBankUser(BankUser bankUser){
		getHibernateTemplate().save(bankUser);
	}
	
	public void clearBankUserDetails(BankUser bankUser) {
		getHibernateTemplate().delete(bankUser);
	}

	public void updatePIIAuthorization(String userName, String pIIAuthorization) {
		List list = getHibernateTemplate().find("from BankUser where userName = ?", userName);
		if (list.size()>0){
			BankUser bankUser = (BankUser)list.get(0);
			bankUser.setPiiAuthorization(pIIAuthorization);
			getHibernateTemplate().update(bankUser);
		}
	}

	public void updateLastLoginOnByUserName(String userName) {
		BankUser bankUser = findByUserName(userName);
		bankUser.setLastLoginOn(new Date());
		getHibernateTemplate().update(bankUser);		
	}

}
