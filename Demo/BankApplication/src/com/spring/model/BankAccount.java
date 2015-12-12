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
@Table(name="bankaccount", catalog="southwesttech")
public class BankAccount implements Serializable {
	//	TODO - Add comparator
	
	//	Mapping column rules
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "bankAccountId", unique = true, nullable = false)
	private Integer bankAccountId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bankUserId", nullable = false)
	private BankUser bankuser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "refAccountTypeId", nullable = false)
	private RefAccountType refaccounttype;
	
	@Column(name = "balance", precision = 12, scale = 0)
	private Float balance;
	
	@Column(name = "debitLimit", precision = 12, scale = 0)
	private Float debitLimit;
	
	@Column(name = "creditLimit", precision = 12, scale = 0)
	private Float creditLimit;
	
	@Column(name = "accountCreatedOn")
	private Date accountCreatedOn;
	
	@Column(name = "accountLastAccessedOn")
	private Date accountLastAccessedOn;
	
	//	 Constructors
	public BankAccount() {
	}

	public BankAccount(BankUser bankuser, RefAccountType refaccounttype) {
		this.bankuser = bankuser;
		this.refaccounttype = refaccounttype;
	}

	public BankAccount(BankUser bankuser, RefAccountType refaccounttype, Float balance, Float debitLimit,
			Float creditLimit, Date accountCreatedOn, Date accountLastAccessedOn) {
		this.bankuser = bankuser;
		this.refaccounttype = refaccounttype;
		this.balance = balance;
		this.debitLimit = debitLimit;
		this.creditLimit = creditLimit;
		this.accountCreatedOn = accountCreatedOn;
		this.accountLastAccessedOn = accountLastAccessedOn;
	}
	
	//	Getter-Setter Methods for entity object
	public Integer getBankAccountId() {
		return this.bankAccountId;
	}

	public void setBankAccountId(Integer bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public BankUser getBankuser() {
		return this.bankuser;
	}

	public void setBankuser(BankUser bankuser) {
		this.bankuser = bankuser;
	}

	public RefAccountType getRefaccounttype() {
		return this.refaccounttype;
	}

	public void setRefaccounttype(RefAccountType refaccounttype) {
		this.refaccounttype = refaccounttype;
	}

	public Float getBalance() {
		return this.balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public Float getDebitLimit() {
		return this.debitLimit;
	}

	public void setDebitLimit(Float debitLimit) {
		this.debitLimit = debitLimit;
	}

	public Float getCreditLimit() {
		return this.creditLimit;
	}

	public void setCreditLimit(Float creditLimit) {
		this.creditLimit = creditLimit;
	}

	public Date getAccountCreatedOn() {
		return this.accountCreatedOn;
	}

	public void setAccountCreatedOn(Date accountCreatedOn) {
		this.accountCreatedOn = accountCreatedOn;
	}

	public Date getAccountLastAccessedOn() {
		return this.accountLastAccessedOn;
	}

	public void setAccountLastAccessedOn(Date accountLastAccessedOn) {
		this.accountLastAccessedOn = accountLastAccessedOn;
	}
	
	//	Modify toString() method as needed
	@Override
	public String toString() {
		return "Modify toString() method for BankAccount";
	}
}
