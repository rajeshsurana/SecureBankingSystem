package com.spring.model;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="banktransaction", catalog="southwesttech")
public class BankTransaction implements Serializable{
	//	TODO - Add comparator
	
	//	Mapping column rules
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "bankTransactionId", unique = true, nullable = false)
	private Integer bankTransactionId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "transactionRecipientId")
	private BankUser recipient;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "transactionBenefactorId")
	private BankUser benefactor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "refTransactionStatusId")
	private RefTransactionStatus reftransactionstatus;
	
	@Column(name = "transactionAmount", precision = 12, scale = 0)
	private Float transactionAmount;
	
	//	No Foreign Key reference on this since it can be NULL if transaction is auto-approved
	@Column(name = "transactionAuthorizerUserId")
	private Integer transactionAuthorizerUserId;
	
	@Column(name = "transactionApprovedOn")
	private Date transactionApprovedOn;

	//	Constructors
	public BankTransaction() {
	}

	public BankTransaction(BankUser recipient, BankUser benefactor) {
		this.recipient = recipient;
		this.benefactor = benefactor;
	}

	public BankTransaction(BankUser recipient, RefTransactionStatus reftransactionstatus,
			BankUser benefactor, Float transactionAmount, Integer transactionAuthorizerUserId,
			Date transactionApprovedOn) {
		this.recipient = recipient;
		this.reftransactionstatus = reftransactionstatus;
		this.benefactor = benefactor;
		this.transactionAmount = transactionAmount;
		this.transactionAuthorizerUserId = transactionAuthorizerUserId;
		this.transactionApprovedOn = transactionApprovedOn;
	}

	//	Getter-Setter Methods for entity object
	public Integer getBankTransactionId() {
		return this.bankTransactionId;
	}

	public void setBankTransactionId(Integer bankTransactionId) {
		this.bankTransactionId = bankTransactionId;
	}

	public BankUser getRecipient() {
		return this.recipient;
	}
	
	public void setRecipient(BankUser recipient) {
		this.recipient = recipient;
	}
	
	public BankUser getBenefactor() {
		return this.benefactor;
	}

	public void setBenefactor(BankUser benefactor) {
		this.benefactor = benefactor;
	}	

	public RefTransactionStatus getReftransactionstatus() {
		return this.reftransactionstatus;
	}

	public void setReftransactionstatus(RefTransactionStatus reftransactionstatus) {
		this.reftransactionstatus = reftransactionstatus;
	}	

	public Float getTransactionAmount() {
		return this.transactionAmount;
	}

	public void setTransactionAmount(Float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Integer getTransactionAuthorizerUserId() {
		return this.transactionAuthorizerUserId;
	}

	public void setTransactionAuthorizerUserId(Integer transactionAuthorizerUserId) {
		this.transactionAuthorizerUserId = transactionAuthorizerUserId;
	}

	public Date getTransactionApprovedOn() {
		return this.transactionApprovedOn;
	}

	public void setTransactionApprovedOn(Date transactionApprovedOn) {
		this.transactionApprovedOn = transactionApprovedOn;
	}
	
	//	Modify toString() method as needed
	@Override
	public String toString() {
		return "Modify toString() method for BankTransaction";
	}
}
