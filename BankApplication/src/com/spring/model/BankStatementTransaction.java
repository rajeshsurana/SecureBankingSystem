package com.spring.model;
/**
 * 
 * @author Rajesh
 *
 */
import java.util.Date;

import com.spring.util.TransactionType;

public class BankStatementTransaction {
	private Date date;
	private Float transactionAmount;
	private String description;
	private TransactionType transactionType;

	public BankStatementTransaction(){}
	
	public BankStatementTransaction(Date date, Float transactionAmount, String description,
			TransactionType transactionType) {
		this.date = date;
		this.transactionAmount = transactionAmount;
		this.description = description;
		this.transactionType = transactionType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Float getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

}
