package com.spring.model;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="reftransactionstatus", catalog="southwesttech")
public class RefTransactionStatus implements Serializable{
	//	TODO - Add comparator
	
	//	Mapping column rules
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "refTransactionStatusId", unique = true, nullable = false)
	private Integer refTransactionStatusId;
	
	@Column(name = "transactionStatusDescription", length = 100)
	private String transactionStatusDescription;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reftransactionstatus")
	private Set<BankTransaction> banktransactions = new HashSet<BankTransaction>(0);
	
	//	Cosntructors
	public RefTransactionStatus() {
	}

	public RefTransactionStatus(String transactionStatusDescription, Set<BankTransaction> banktransactions) {
		this.transactionStatusDescription = transactionStatusDescription;
		this.banktransactions = banktransactions;
	}
	
	//	Getter-Setter Methods for entity object
	public Integer getRefTransactionStatusId() {
		return this.refTransactionStatusId;
	}

	public void setRefTransactionStatusId(Integer refTransactionStatusId) {
		this.refTransactionStatusId = refTransactionStatusId;
	}

	public String getTransactionStatusDescription() {
		return this.transactionStatusDescription;
	}

	public void setTransactionStatusDescription(String transactionStatusDescription) {
		this.transactionStatusDescription = transactionStatusDescription;
	}

	public Set<BankTransaction> getBanktransactions() {
		return this.banktransactions;
	}

	public void setBanktransactions(Set<BankTransaction> banktransactions) {
		this.banktransactions = banktransactions;
	}
	
	//	Modify toString() method as needed
	@Override
	public String toString() {
		return "Modify toString() method for RefTransactionStatus";
	}
}
