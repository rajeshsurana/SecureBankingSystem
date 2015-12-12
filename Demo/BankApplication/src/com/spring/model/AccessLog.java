package com.spring.model;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

@Entity
@Table(name="accesslog", catalog="southwesttech")
public class AccessLog implements Serializable {
	//	TODO - Add comparator
	
	//	Mapping column rules
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "accessLogId", unique = true, nullable = false)
	private Integer accessLogId;
	
	@Column(name = "userName", length = 100)
	private String userName;
	
	@Column(name = "IPAddress", length = 100)
	private String IPAddress;
	
	@Column(name = "deviceId", length = 2000)
	private String deviceId;
	
	@Column(name = "accessedOn")
	private Date accessedOn;
	
	@Column(name = "suspiciousActivity")
	private Integer suspiciousActivity;

	//	Constructors
	public AccessLog() {
	}

	public AccessLog(String userName, String ipaddress, String deviceId, Date accessedOn, Integer suspiciousActivity) {
		this.userName = userName;
		this.IPAddress = ipaddress;
		this.deviceId = deviceId;
		this.accessedOn = accessedOn;
		this.suspiciousActivity = suspiciousActivity;
	}

	//	Getter-Setter Methods for entity object
	public Integer getAccessLogId() {
		return this.accessLogId;
	}

	public void setAccessLogId(Integer accessLogId) {
		this.accessLogId = accessLogId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIPAddress() {
		return this.IPAddress;
	}

	public void setIPAddress(String IPAddress) {
		this.IPAddress = IPAddress;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Date getAccessedOn() {
		return this.accessedOn;
	}

	public void setAccessedOn(Date accessedOn) {
		this.accessedOn = accessedOn;
	}

	public Integer getSuspiciousActivity() {
		return this.suspiciousActivity;
	}

	public void setSuspiciousActivity(Integer suspiciousActivity) {
		this.suspiciousActivity = suspiciousActivity;
	}
	
	//	Modify toString() method as needed
	@Override
	public String toString() {
		return "Modify toString() method for AccessLog";
	}
}
