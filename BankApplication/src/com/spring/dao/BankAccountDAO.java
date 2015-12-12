package com.spring.dao;

import java.util.List;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import org.springframework.stereotype.Repository;

import com.spring.model.BankAccount;
import com.spring.model.BankUser;
import com.spring.util.CustomHibernateDAOSupport;

@Repository("bankAccountDao")
public class BankAccountDAO extends CustomHibernateDAOSupport {
	public BankAccount findAccountByUserId(int bankUserId){
		List list = getHibernateTemplate().find("FROM BankAccount where bankUserId = ?", bankUserId);
		if (list.size()>0){
			return (BankAccount)list.get(0);
		}else{
			return null;
		}
	}
	
	public BankAccount findBankAccountByAccountNumber(Integer bankAccountNo) {
		List list = getHibernateTemplate().find("FROM BankAccount where bankAccountId = ?", bankAccountNo);
		if (list.size()>0){
			return (BankAccount)list.get(0);
		}else{
			return null;
		}
	}


	public BankAccount findCheckingAccountByUserId(Integer bankUserId) {
		List list = getHibernateTemplate().find("FROM BankAccount where bankUserId = ? and refAccountTypeId = 1", bankUserId);
		if (list.size()>0){
			return (BankAccount)list.get(0);
		}else{
			return null;
		}
	}

	public BankAccount findSavingAccountByUserId(Integer bankUserId) {
		List list = getHibernateTemplate().find("FROM BankAccount where bankUserId = ? and refAccountTypeId = 2", bankUserId);
		if (list.size()>0){
			return (BankAccount)list.get(0);
		}else{
			return null;
		}
	}

	public void updateBankAccountBalance(Integer bankAccountId, float amount) {
		List list = getHibernateTemplate().find("from BankAccount where bankAccountId = ?", bankAccountId);
		if (list.size()>0){
			BankAccount bankAccount = (BankAccount)list.get(0);
			bankAccount.setBalance(amount);
			getHibernateTemplate().update(bankAccount);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<BankAccount> getAccountById (Integer id) {
		return (List<BankAccount>) getHibernateTemplate().find("from BankAccount where bankAccountId = ?", id);
	}
	
	public void adjustBalance(Integer accountId, Float amount) {
		List<BankAccount> bankAccountList = getAccountById(accountId);
		if(bankAccountList != null && bankAccountList.size() > 0) {
			BankAccount bankAccount = bankAccountList.get(0);
			System.out.println("Adjusting account balance for - " + bankAccount.getBankAccountId() + " by :" + amount);
			bankAccount.setBalance(bankAccount.getBalance() + amount);
			getHibernateTemplate().update(bankAccount);
		}		
	}
	
	public void setBankAccount(BankAccount bankAccount){
		getHibernateTemplate().save(bankAccount);
	}
	
	public void clearBankUserDetails(BankAccount bankAccount) {
		getHibernateTemplate().delete(bankAccount);
	}
}
