package com.spring.dao;

import java.util.List;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import org.springframework.stereotype.Repository;

import com.spring.model.RefAccountType;
import com.spring.util.CustomHibernateDAOSupport;

@Repository("refAccountTypeDao")
public class RefAccountTypeDAO extends CustomHibernateDAOSupport {
	public RefAccountType findByName(String accountType){
		List list = getHibernateTemplate().find("from RefAccountType where accountTypeDescription = ?", accountType);
		return (RefAccountType)list.get(0);
	}
}
