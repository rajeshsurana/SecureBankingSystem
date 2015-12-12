package com.spring.model;

import java.util.List;

import javax.persistence.Entity;


@Entity
public class BankTransactionList {
	private List<BankTransaction> bankTransactions;

	public List<BankTransaction> getBankTransactions() {
		return bankTransactions;
	}

	public void setBankTransactions(List<BankTransaction> transactions) {
		this.bankTransactions = transactions;
	}

}