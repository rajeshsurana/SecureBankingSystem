package com.spring.model;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="refaccounttype", catalog="southwesttech")
public class RefAccountType implements Serializable{
	//	TODO - Add comparator
	
	//	Mapping column rules
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "refAccountTypeId", unique = true, nullable = false)
	private Integer refAccountTypeId;
	
	@Column(name = "accountTypeDescription", length = 100)
	private String accountTypeDescription;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "refaccounttype")
	private Set<BankAccount> bankaccounts = new HashSet<BankAccount>(0);
	
	//	Constructors
	public RefAccountType() {
	}

	public RefAccountType(String accountTypeDescription, Set<BankAccount> bankaccounts) {
		this.accountTypeDescription = accountTypeDescription;
		this.bankaccounts = bankaccounts;
	}
	
	//	Getter-Setter Methods for entity object
	public Integer getRefAccountTypeId() {
		return this.refAccountTypeId;
	}

	public void setRefAccountTypeId(Integer refAccountTypeId) {
		this.refAccountTypeId = refAccountTypeId;
	}

	public String getAccountTypeDescription() {
		return this.accountTypeDescription;
	}

	public void setAccountTypeDescription(String accountTypeDescription) {
		this.accountTypeDescription = accountTypeDescription;
	}

	public Set<BankAccount> getBankaccounts() {
		return this.bankaccounts;
	}

	public void setBankaccounts(Set<BankAccount> bankaccounts) {
		this.bankaccounts = bankaccounts;
	}
	
	@Override
	public String toString() {
		return "Modify toString() method for RefAccountType";
	}
}
