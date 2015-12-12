package com.spring.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pkiverification", catalog="southwesttech")
public class PkiVerification {
//	Mapping column rules
	@Id
	@Column(name = "username", unique = true, nullable = false)
	private String username;
  
	
	@Column(name = "userPublicKeyModulus")
	private String userPublicKeyModulus;
	
	@Column(name = "userPublicKeyExponent")
	private String userPublicKeyExponent;
	
	@Column(name = "userVerificationCode")
	private String userVerificationCode;

	public String getUserVerificationCode() {
		return userVerificationCode;
	}

	public void setUserVerificationCode(String userVerificationCode) {
		this.userVerificationCode = userVerificationCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserPublicKeyModulus() {
		return userPublicKeyModulus;
	}

	public void setUserPublicKeyModulus(String userPublicKeyModulus) {
		this.userPublicKeyModulus = userPublicKeyModulus;
	}

	public String getUserPublicKeyExponent() {
		return userPublicKeyExponent;
	}

	public void setUserPublicKeyExponent(String userPublicKeyExponent) {
		this.userPublicKeyExponent = userPublicKeyExponent;
	}

}
