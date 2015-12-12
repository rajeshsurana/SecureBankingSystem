package com.spring.model;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class AccessLogList {
	private List<AccessLog> accessLog;

	public List<AccessLog> getAccessLog() {
		return accessLog;
	}

	public void setAccessLog(List<AccessLog> accessLog) {
		this.accessLog = accessLog;
	}

}
