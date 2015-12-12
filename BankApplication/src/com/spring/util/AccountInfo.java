package com.spring.util;

public class AccountInfo {
	int accountNumber;
	String checkingAccountNo;
	String savingsAccountNo;
	String lastVisitDate;
	int checkingAccBal;
	int savingsAccBal;
	boolean savingsAccExists;
	
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getCheckingAccountNo() {
		return checkingAccountNo;
	}
	public void setCheckingAccountNo(String checkingAccountNo) {
		this.checkingAccountNo = checkingAccountNo;
	}
	public String getSavingsAccountNo() {
		return savingsAccountNo;
	}
	public void setSavingsAccountNo(String savingsAccountNo) {
		this.savingsAccountNo = savingsAccountNo;
	}
	public String getLastVisitDate() {
		return lastVisitDate;
	}
	public void setLastVisitDate(String lastVisitDate) {
		this.lastVisitDate = lastVisitDate;
	}
	public int getCheckingAccBal() {
		return checkingAccBal;
	}
	public void setCheckingAccBal(int checkingAccBal) {
		this.checkingAccBal = checkingAccBal;
	}
	public int getSavingsAccBal() {
		return savingsAccBal;
	}
	public void setSavingsAccBal(int savingsAccBal) {
		this.savingsAccBal = savingsAccBal;
	}
	public boolean isSavingsAccExists() {
		return savingsAccExists;
	}
	public void setSavingsAccExists(boolean savingsAccExists) {
		this.savingsAccExists = savingsAccExists;
	}
	public AccountInfo(int accountNumber, String checkingAccountNo,
			String savingsAccountNo, String lastVisitDate, int checkingAccBal,
			int savingsAccBal, boolean savingsAccExists) {
		super();
		this.accountNumber = accountNumber;
		this.checkingAccountNo = checkingAccountNo;
		this.savingsAccountNo = savingsAccountNo;
		this.lastVisitDate = lastVisitDate;
		this.checkingAccBal = checkingAccBal;
		this.savingsAccBal = savingsAccBal;
		this.savingsAccExists = savingsAccExists;
	}
	public AccountInfo(int accountNumber, String checkingAccountNo,
			String lastVisitDate, int checkingAccBal, boolean savingsAccExists) {
		super();
		this.accountNumber = accountNumber;
		this.checkingAccountNo = checkingAccountNo;
		this.lastVisitDate = lastVisitDate;
		this.checkingAccBal = checkingAccBal;
		this.savingsAccExists = false;
	}
	public AccountInfo() {
	}
	
	
	
	

}
