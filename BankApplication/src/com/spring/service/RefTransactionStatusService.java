package com.spring.service;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.RefTransactionStatusDAO;
import com.spring.model.RefTransactionStatus;

@Service("refTransactionStatusService")
public class RefTransactionStatusService {
	@Autowired
	RefTransactionStatusDAO refTransactionStatusDao;
	
	public RefTransactionStatusDAO getRefUserRoleDao () {
		return this.refTransactionStatusDao;
	}
	
	public void setRefUserRoleDao (RefTransactionStatusDAO refTransactionStatusDAO) {
		this.refTransactionStatusDao = refTransactionStatusDAO;
	}

	public RefTransactionStatus getRefTransactionStatusById(int status) {
		return this.refTransactionStatusDao.getRefTransactionStatusById(status);
	}
}
