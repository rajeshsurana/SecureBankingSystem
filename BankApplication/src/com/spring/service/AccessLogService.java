package com.spring.service;

import java.util.List;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.AccessLogDAO;
import com.spring.model.AccessLog;

@Service("accessLogService")
public class AccessLogService {
	@Autowired
	AccessLogDAO accessLogDao;
	
	public AccessLogDAO getRefUserRoleDao () {
		return this.accessLogDao;
	}
	
	public void setRefUserRoleDao (AccessLogDAO accessLogDAO) {
		this.accessLogDao = accessLogDAO;
	}
	
	public List<AccessLog> selectAllRecords(){
		return accessLogDao.selectAllRecords();
	}

	public int addAccessLog(AccessLog accessLog) {
		return this.accessLogDao.addAccessLog(accessLog);
	}

	public void updateLogSuspiciousActivity(int accessLogId, Integer suspiciousActivity) {
		this.accessLogDao.updateLogSuspiciousActivity(accessLogId, suspiciousActivity);
		
	}

	public int checkFailedAttemptsLastHourByUsername(String userName) {
		return this.accessLogDao.checkFailedAttemptsLastHourByUsername(userName);
	}

	public int checkFailedAttemptsLastHourByDeviceId(String deviceId) {
		return this.accessLogDao.checkFailedAttemptsLastHourByDeviceId(deviceId);
	}
}
