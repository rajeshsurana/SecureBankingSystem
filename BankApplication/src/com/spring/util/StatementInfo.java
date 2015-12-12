package com.spring.util;

public class StatementInfo {
	String date;
	String activity;
	String userId;
	public StatementInfo(String date, String activity, String userId) {
		super();
		this.date = date;
		this.activity = activity;
		this.userId = userId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
