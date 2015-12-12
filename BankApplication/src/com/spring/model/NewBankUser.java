package com.spring.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author mahathi
 *
 */
@Entity
@Table(name = "newbankuser", catalog = "southwesttech")
public class NewBankUser implements Serializable {
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "refUserRoleId", nullable = false)
	private RefUserRole refuserrole;

	@Column(name = "email", unique = true, nullable = false, length = 100)
	private String email;

	@Column(name = "phoneNumber", length = 100)
	private String phoneNumber;

	@Id
	@Column(name = "userName", unique = true, nullable = false, length = 100)
	private String userName;

	@Column(name = "userPassword", length = 100)
	private String userPassword;

	@Column(name = "firstName", length = 200)
	private String firstName;

	@Column(name = "lastName", length = 200)
	private String lastName;

	@Column(name = "dateOfBirth")
	private Date dateOfBirth;

	@Column(name = "SSN", length = 12)
	private String ssn;

	@Column(name = "residentialAddress", length = 1000)
	private String residentialAddress;

	@Column(name = "mailingAddress", length = 1000)
	private String mailingAddress;

	@Column(name = "userCreatedOn")
	private Date userCreatedOn;

	@Column(name = "accountStatus")
	private String accountStatus;
	
	@Column(name = "assignedToManager")
	private String assignedToManager;

	
	public String getAssignedToManager() {
		return assignedToManager;
	}

	public void setAssignedToManager(String assignedToManager) {
		this.assignedToManager = assignedToManager;
	}

	public RefUserRole getRefuserrole() {
		return refuserrole;
	}

	public void setRefuserrole(RefUserRole refuserrole) {
		this.refuserrole = refuserrole;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public String getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public Date getUserCreatedOn() {
		return userCreatedOn;
	}

	public void setUserCreatedOn(Date userCreatedOn) {
		this.userCreatedOn = userCreatedOn;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}


}
