package com.spring.model;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 * 
 * Modified : Mahathi
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
@Table(name="refuserrole", catalog="southwesttech")
public class RefUserRole implements Serializable{
	//	TODO - Add comparator
	
	//	Mapping column rules
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "refUserRoleId", unique = true, nullable = false)
	private Integer refUserRoleId;
	
	@Column(name = "bankUserRoleName", length = 100)
	private String bankUserRoleName;
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "refuserrole")
	private Set<BankUser> bankusers = new HashSet<BankUser>(0);*/
	
	//	Constructors
	public RefUserRole () {
	}

	public RefUserRole(Integer refUserRoleId, String bankUserRoleName) {
		super();
		this.refUserRoleId = refUserRoleId;
		this.bankUserRoleName = bankUserRoleName;
	}

	/*public RefUserRole(String bankUserRoleName, Set<BankUser> bankusers) {
		this.bankUserRoleName = bankUserRoleName;
		this.bankusers = bankusers;
	}*/
	
	//	Getter-Setter Methods for entity object
	public Integer getRefUserRoleId() {
		return this.refUserRoleId;
	}
	
	public void setRefUserRoleId (int roleId) {
		this.refUserRoleId = roleId;
	}
	
	
	public String getBankUserRoleName() {
		return this.bankUserRoleName;
	}
	
	public void setBankUserRoleName (String roleName) {
		this.bankUserRoleName = roleName;
	}
	
/*	public Set<BankUser> getBankUsers() {
		return this.bankusers;
	}

	public void setBankUsers(Set<BankUser> bankusers) {
		this.bankusers = bankusers;
	}*/
	
	//	Modify toString() method as needed
	@Override
	public String toString() {
		return "Modify toString() method for RefUserRole";
	}
	
}
