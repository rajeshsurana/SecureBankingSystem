package com.spring.util;
/**
 * 
 * @author Rajesh
 *
 */
public enum TransactionType {
	
	DEBIT("DEBIT"), 
	CREDIT("CREDIT"), 
	TRANSFERTOCHECKING("TRANSFERTOCHECKING"), 
	TRANSFERTOSAVING("TRANSFERTOSAVING"), 
	TRANSFEREXTERNAL("TRANSFEREXTERNAL");
	
	private final String value;

	private TransactionType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
};
