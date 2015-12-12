package com.spring.util;
/**
 * 
 * @author Rajesh
 *
 */
public enum TransactionStatus {
	SUBMITTED(0),
	VALIDATED(1),
	NONVALIDATED(2),
	APPROVED(3), 
	DENIED(4),
	SUBMITTEDBYMERCHANT(5), //Merchant has submitted transaction for customer
	VALIDATEDBYMERCHANT(6); //Merchant has correctly entered OTP	
    private final int value;

    private TransactionStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }};
