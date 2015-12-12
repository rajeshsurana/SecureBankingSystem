package com.spring.util;
/**
 * 
 * @author Rajesh
 *
 */
public enum SuspiciousActivityType {
	NONSUSPICIOUS(0),
	WRONGCAPTCHA(1),
	WRONGUSERNAME(2), 
	WRONGPASSWORD(3),
	INACTIVEACCOUNT(4),
	REQUIREACTIVATION(5),
	SECURITYALERTUSER(6),
	SECURITYALERTDEVICE(7);
	
	private final Integer value;

	private SuspiciousActivityType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
