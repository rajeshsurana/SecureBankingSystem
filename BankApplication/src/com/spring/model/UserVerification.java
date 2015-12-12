package com.spring.model;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 * Modified : Mahathi
 */

import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="userverification", catalog="southwesttech")
public class UserVerification implements Serializable{	
	//	Mapping column rules
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "userVerificationId", unique = true, nullable = false)
	private Integer userVerificationId;
	
   /*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "verifyinguser", nullable = false)
	private BankUser verifyinguser;	*/
	
	@Column(name = "verifyingUserName")
	private String verifyingUserName;
	
	@Column(name = "verificationCode", length = 20)
	private String verificationCode;
	
	@Column(name = "codeExpiresOn")
	private Date codeExpiresOn;

	//	Constructors
	public UserVerification() {
	}

	public UserVerification(String verifyingUser, String verificationCode, Date codeExpiresOn) {
		this.verifyingUserName = verifyingUser;
		this.verificationCode = verificationCode;
		this.codeExpiresOn = codeExpiresOn;
	}

	//	Getter-Setter Methods for entity object
	public Integer getUserVerificationId() {
		return this.userVerificationId;
	}

	public void setUserVerificationId(Integer userVerificationId) {
		this.userVerificationId = userVerificationId;
	}

	public String getBankuser() {
		return this.verifyingUserName;
	}

	public void setBankuser(String verifyingUser) {
		this.verifyingUserName = verifyingUser;
	}

	public String getVerificationCode() {
		return this.verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Date getCodeExpiresOn() {
		return this.codeExpiresOn;
	}

	public void setCodeExpiresOn(Date codeExpiresOn) {
		this.codeExpiresOn = codeExpiresOn;
	}
	
	//	Modify toString() method as needed
	@Override
	public String toString() {
		return "UserVerification" + this.getUserVerificationId() +":"+ this.getBankuser()+" :" + this.getVerificationCode() + " :" + this.getCodeExpiresOn().toString() ;
	}
}
