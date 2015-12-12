package com.spring.dao;

import java.util.Date;
import java.util.List;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import org.springframework.stereotype.Repository;

import com.spring.model.AccessLog;
import com.spring.model.BankTransaction;
import com.spring.util.CustomHibernateDAOSupport;
import com.spring.util.SuspiciousActivityType;

@Repository("accessLogDao")
public class AccessLogDAO extends CustomHibernateDAOSupport  {
	
	public int addAccessLog(AccessLog accessLog) {
		return (int) getHibernateTemplate().save(accessLog);
	}

	public void updateLogSuspiciousActivity(int accessLogId, Integer suspiciousActivity) {
		List list = getHibernateTemplate().find("from AccessLog where accessLogId = ?", accessLogId);
		if (list.size()>0){
			AccessLog accessLog = (AccessLog)list.get(0);
			accessLog.setSuspiciousActivity(suspiciousActivity);;
			getHibernateTemplate().update(accessLog);
		}
		
		
	}
	
	public List<AccessLog> selectAllRecords() {
		List list = getHibernateTemplate().find("from AccessLog");	
		return list;		
	}

	public int checkFailedAttemptsLastHourByUsername(String userName) {
		List list = getHibernateTemplate().find("from AccessLog where userName = ?", userName);
		if(list.size() >= 3){
			Date date = new Date();
			int count = 0;
			long oneHour = 3600000;
			for(Object accessLogObj:list){
				AccessLog accessLog = (AccessLog)accessLogObj;				 
				if(((accessLog.getAccessedOn().getTime() - date.getTime()) < oneHour) && (accessLog.getSuspiciousActivity() == SuspiciousActivityType.WRONGPASSWORD.getValue())){
					count++;
				}				
			}
			return count;
		}else
			return 0;
	}

	public int checkFailedAttemptsLastHourByDeviceId(String deviceId) {
		List list = getHibernateTemplate().find("from AccessLog where deviceId = ?", deviceId);
		if(list.size() >= 9){
			Date date = new Date();
			int count = 0;
			long oneHour = 3600000;
			for(Object accessLogObj:list){
				AccessLog accessLog = (AccessLog)accessLogObj;				 
				if(((accessLog.getAccessedOn().getTime() - date.getTime()) < oneHour) && 
						((accessLog.getSuspiciousActivity() == SuspiciousActivityType.WRONGPASSWORD.getValue()) || 
								(accessLog.getSuspiciousActivity() == SuspiciousActivityType.WRONGUSERNAME.getValue()) )){
					count++;
				}				
			}
			return count;
		}else
			return 0;
	}
}
