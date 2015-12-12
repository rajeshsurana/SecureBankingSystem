package com.spring.model;
//Author Shankar
public class PendingMerchantTransactionAuth {
	private String recipient;
	private String benefactor;
	private Float transactionAmount;
	private int transactionId;
	
	public PendingMerchantTransactionAuth(){}
	
	public PendingMerchantTransactionAuth(String recipient, Float transactionAmount) {
		this.recipient = recipient;
		this.transactionAmount = transactionAmount;
	}
	
	public int getTransactionId() {
		return transactionId;
	}
	

	public String getBenefactor() {
		return benefactor;
	}

	public void setBenefactor(String benefactor) {
		this.benefactor = benefactor;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public Float getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(Float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
}
