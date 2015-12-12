package com.spring.service;

import java.util.List;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.dao.BankAccountDAO;
import com.spring.dao.BankTransactionDAO;
import com.spring.model.BankTransaction;
import com.spring.model.RefTransactionStatus;
import com.spring.util.StaticConstants;
import com.spring.util.TransactionStatus;

@Service("bankTransactionService")
public class BankTransactionService {
	@Autowired
	BankTransactionDAO bankTransactionDao;
	
	@Autowired
	BankAccountDAO bankAccountDao;
	
	public BankTransactionDAO getRefUserRoleDao () {
		return this.bankTransactionDao;
	}
	
	public void setRefUserRoleDao (BankTransactionDAO bankTransactionDAO) {
		this.bankTransactionDao = bankTransactionDAO;
	}

	public int addBankTransaction(BankTransaction transaction) {
		return this.bankTransactionDao.addBankTransaction(transaction);
		
	}

	public void updateValidatedBankTransactionById(int transactionId, RefTransactionStatus reftransactionstatus) {
		this.bankTransactionDao.updateValidatedBankTransactionById(transactionId, reftransactionstatus);
		
	}


	public List<BankTransaction> getTransactionList(Integer bankUserId) {
		return this.bankTransactionDao.getTransactionList(bankUserId);
	}
	
	public List<BankTransaction> getPendingTransactions() {
		return this.bankTransactionDao.getPendingTransactions();
	}
	
	public List<BankTransaction> getTransactionById(Integer transactionId) {
		return this.bankTransactionDao.getTransactionById(transactionId);
	}
	
	public List<BankTransaction> getAllTransactions() {
		return this.bankTransactionDao.getAllTransactions();
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void approveTransaction(Integer benefactorAccountId, Integer recipientAccountId, Float transferAmount, Integer transactionId, Integer approverId) {
		//System.out.println("Approving transaction - " + transactionId);
		bankAccountDao.adjustBalance(benefactorAccountId, -transferAmount);
		bankAccountDao.adjustBalance(recipientAccountId, transferAmount);
		bankTransactionDao.updateTransactionStatus(transactionId, StaticConstants.APPROVED, approverId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void completeDebitTransaction(Integer benefactorAccountId, Float transferAmount, Integer transactionId, Integer approverId) {
		//System.out.println("Approving transaction - " + transactionId);
		bankAccountDao.adjustBalance(benefactorAccountId, -transferAmount);
		bankTransactionDao.updateTransactionStatus(transactionId, StaticConstants.APPROVED, approverId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void completeCreditTransaction(Integer recipientAccountId, Float transferAmount, Integer transactionId, Integer approverId) {
		//System.out.println("Approving transaction - " + transactionId);
		bankAccountDao.adjustBalance(recipientAccountId, transferAmount);
		bankTransactionDao.updateTransactionStatus(transactionId, StaticConstants.APPROVED, approverId);
	}
	//When customer authorizes Merchant to transfer in his behalf
	@Transactional(propagation = Propagation.REQUIRED)
	public void approveTransactionFromMerchant(Integer transactionId, Integer approverId){
		bankTransactionDao.updateTransactionStatus(transactionId, StaticConstants.VALIDATED, approverId);
	}
	public void declineTransaction(Integer transactionId, Integer approverId) {
		//System.out.println("Declining transaction - " + transactionId);
		bankTransactionDao.updateTransactionStatus(transactionId, StaticConstants.DENIED, approverId);
	}
}
