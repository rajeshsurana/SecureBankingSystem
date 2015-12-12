package com.spring.dao;

import java.util.Date;
import java.util.List;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import org.springframework.stereotype.Repository;

import com.spring.model.BankTransaction;
import com.spring.model.BankUser;
import com.spring.model.RefTransactionStatus;
import com.spring.util.CustomHibernateDAOSupport;
import com.spring.util.StaticConstants;
import com.spring.util.TransactionStatus;

@Repository("bankTransactionDao")
public class BankTransactionDAO extends CustomHibernateDAOSupport {
	

	public int addBankTransaction(BankTransaction transaction) {
		return (int) getHibernateTemplate().save(transaction);
	}

	public void updateValidatedBankTransactionById(int transactionId, RefTransactionStatus reftransactionstatus) {
		List list = getHibernateTemplate().find("from BankTransaction where bankTransactionId = ?", transactionId);
		if (list.size()>0){
			BankTransaction bankTransaction = (BankTransaction)list.get(0);
			bankTransaction.setReftransactionstatus(reftransactionstatus);
			getHibernateTemplate().update(bankTransaction);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<BankTransaction> getPendingTransactions() {
		return (List<BankTransaction>) getHibernateTemplate().find("FROM BankTransaction bt WHERE bt.reftransactionstatus.transactionStatusDescription = ?", StaticConstants.VALIDATED);
	}

	@SuppressWarnings("unchecked")
	public List<BankTransaction> getTransactionList(Integer bankUserId) {
		return (List<BankTransaction>)(List<?>) getHibernateTemplate().find("from BankTransaction where transactionRecipientId = ? or transactionBenefactorId = ?", bankUserId, bankUserId); 
	}
	
	@SuppressWarnings("unchecked")
	public List<BankTransaction> getAllTransactions() {
		return (List<BankTransaction>) (List<?>) getHibernateTemplate().find("FROM BankTransaction");
	}
	
	@SuppressWarnings("unchecked")
	public List<BankTransaction> getTransactionById(Integer transactionId) {
		return (List<BankTransaction>) getHibernateTemplate().find("FROM BankTransaction WHERE bankTransactionId = ?", transactionId);
	}
	
	public void updateTransactionStatus(Integer transactionId, String statusName, Integer approverId) {
		@SuppressWarnings("unchecked")
		List<RefTransactionStatus> transactionStatusList = getHibernateTemplate().find("FROM RefTransactionStatus WHERE transactionStatusDescription = ?", statusName);
		List<BankTransaction> transactionList = getTransactionById(transactionId);
		if(transactionStatusList != null && transactionStatusList.size() > 0 && transactionList != null && transactionList.size() > 0) {
			RefTransactionStatus status = transactionStatusList.get(0);
			BankTransaction transaction = transactionList.get(0);
			transaction.setReftransactionstatus(status);
			Date now = new Date();
			transaction.setTransactionAuthorizerUserId(approverId);
			transaction.setTransactionApprovedOn(now);
			getHibernateTemplate().update(transaction);
		}
	}

}
