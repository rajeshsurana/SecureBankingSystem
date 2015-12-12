package com.spring.service;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.BankAccountDAO;
import com.spring.model.BankAccount;

@Service("bankAccountService")
public class BankAccountService {
	@Autowired
	BankAccountDAO bankAccountDao;
	
	public BankAccountDAO getRefUserRoleDao () {
		return this.bankAccountDao;
	}
	
	public void setRefUserRoleDao (BankAccountDAO bankAccountDAO) {
		this.bankAccountDao = bankAccountDAO;
	}
	

	public BankAccountDAO getBankAccountDao() {
		return bankAccountDao;
	}

	public void setBankAccountDao(BankAccountDAO bankAccountDao) {
		this.bankAccountDao = bankAccountDao;
	}
	
	public void setBankAccount(BankAccount bankAccount){
		bankAccountDao.setBankAccount(bankAccount);
	}
	
	public BankAccount findAccountByAccountNumber(Integer bankAccountNumber){
		return bankAccountDao.findBankAccountByAccountNumber(bankAccountNumber);
	}

	public BankAccount findCheckingAccountByUserId(Integer bankUserId) {
		return bankAccountDao.findCheckingAccountByUserId(bankUserId);
	}

	public BankAccount findSavingAccountByUserId(Integer bankUserId) {
		return bankAccountDao.findSavingAccountByUserId(bankUserId);
	}

	public void updateBankAccountBalance(Integer bankAccountId, float amount) {
		bankAccountDao.updateBankAccountBalance(bankAccountId, amount);
		
	}
}
