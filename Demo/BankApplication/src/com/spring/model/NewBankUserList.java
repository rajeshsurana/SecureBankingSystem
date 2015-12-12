package com.spring.model;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class NewBankUserList {
	private List<NewBankUser> newBankUsers;

	public List<NewBankUser> getUsers() {
		return newBankUsers;
	}

	public void setUsers(List<NewBankUser> newBankUsers) {
		this.newBankUsers = newBankUsers;
	}

}
