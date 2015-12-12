package com.spring.model;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "bankuser", catalog = "southwesttech", uniqueConstraints = { @UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "userName") })
public class BankUser implements Serializable {
	// TODO - Add comparator

	// Mapping column rules
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "bankUserId", unique = true, nullable = false)
	private Integer bankUserId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "refUserRoleId")
	private RefUserRole refuserrole;

	@Column(name = "email", unique = true, nullable = false, length = 100)
	private String email;

	@Column(name = "phoneNumber", length = 100)
	private String phoneNumber;

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

	@Column(name = "lastLoginOn")
	private Date lastLoginOn;

	@Column(name = "accountStatus", nullable = false)
	private String accountStatus;
	
	@Column(name ="personalBanker")
	private String personalBanker;
	
	@Column(name = "piiAuthorization", nullable = false, length = 45)
	private String piiAuthorization;


	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bankuser")
	private Set<BankAccount> bankaccounts = new HashSet<BankAccount>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipient")
	private Set<BankTransaction> recipients = new HashSet<BankTransaction>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "benefactor")
	private Set<BankTransaction> benefactors = new HashSet<BankTransaction>(0);

	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "verifyinguser") private
	 * Set<UserVerification> userverifications = new
	 * HashSet<UserVerification>(0);
	 */

	// Constructors
	public BankUser() {
	}

	public BankUser(String email, String userName) {
		this.email = email;
		this.userName = userName;
	}

	public BankUser(RefUserRole refuserrole, String email, String phoneNumber, String userName, String userPassword,
			String firstName, String lastName, Date dateOfBirth, String ssn, String residentialAddress,
			String mailingAddress, Date userCreatedOn, Date lastLoginOn,String accountStatus,String piiAuthorization, Set<BankTransaction> recipients,
			Set<BankAccount> bankaccounts, Set<BankTransaction> benefactors, Set<UserVerification> userverifications) {
		this.refuserrole = refuserrole;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.userPassword = userPassword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.ssn = ssn;
		this.residentialAddress = residentialAddress;
		this.mailingAddress = mailingAddress;
		this.userCreatedOn = userCreatedOn;
		this.lastLoginOn = lastLoginOn;
		this.recipients = recipients;
		this.bankaccounts = bankaccounts;
		this.benefactors = benefactors;
		this.accountStatus = accountStatus;
		this.piiAuthorization = piiAuthorization;
		// this.userverifications = userverifications;
	}

	// Getter-Setter Methods for entity object
	public Integer getBankUserId() {
		return this.bankUserId;
	}

	public void setBankUserId(Integer bankUserId) {
		this.bankUserId = bankUserId;
	}

	public RefUserRole getRefUserRole() {
		return this.refuserrole;
	}

	public void setRefUserRole(RefUserRole refuserrole) {
		this.refuserrole = refuserrole;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getSsn() {
		return this.ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getResidentialAddress() {
		return this.residentialAddress;
	}

	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public String getMailingAddress() {
		return this.mailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public Date getUserCreatedOn() {
		return this.userCreatedOn;
	}

	public void setUserCreatedOn(Date userCreatedOn) {
		this.userCreatedOn = userCreatedOn;
	}

	public Date getLastLoginOn() {
		return this.lastLoginOn;
	}

	public void setLastLoginOn(Date lastLoginOn) {
		this.lastLoginOn = lastLoginOn;
	}

	public Set<BankAccount> getBankaccounts() {
		return this.bankaccounts;
	}

	public void setBankaccounts(Set<BankAccount> bankaccounts) {
		this.bankaccounts = bankaccounts;
	}

	public Set<BankTransaction> getRecipients() {
		return this.recipients;
	}

	public void setRecipients(Set<BankTransaction> recipients) {
		this.recipients = recipients;
	}

	public Set<BankTransaction> getBenefactors() {
		return this.benefactors;
	}

	public void setBenefactors(Set<BankTransaction> benefactors) {
		this.benefactors = benefactors;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	public String getPersonalBanker() {
		return personalBanker;
	}

	public void setPersonalBanker(String personalBanker) {
		this.personalBanker = personalBanker;
	}
	
	public String getPiiAuthorization() {
		return piiAuthorization;
	}

	public void setPiiAuthorization(String piiAuthorization) {
		this.piiAuthorization = piiAuthorization;
	}


	/*
	 * public Set<UserVerification> getUserverifications() { return
	 * this.userverifications; }
	 * 
	 * public void setUserverifications(Set<UserVerification> userverifications)
	 * { this.userverifications = userverifications; }
	 */

	// Modify toString() method as needed
	@Override
	public String toString() {
		return "UserName : " +this.userName +"bankID : "+ this.bankUserId +"AccountStatus : " + this.accountStatus;
	}
}
