package com.spring.dao;

import java.util.List;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import org.springframework.stereotype.Repository;

import com.spring.model.RefTransactionStatus;
import com.spring.util.CustomHibernateDAOSupport;

@Repository("refTransactionStatusDao")
public class RefTransactionStatusDAO extends CustomHibernateDAOSupport {

	public RefTransactionStatus getRefTransactionStatusById(int status) {
		List list = getHibernateTemplate().find("from RefTransactionStatus where refTransactionStatusId = ?", status);
		if (list.size()>0){
			return (RefTransactionStatus)list.get(0);
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RefTransactionStatus> getRefTransactionStatusByName(String statusName) {
		System.out.println("Finding statuses with name - " + statusName);
		return (List<RefTransactionStatus>) getHibernateTemplate().find("FROM RefTransactionStatus WHERE transactionStatusDescription = ?", statusName);
	}

}
