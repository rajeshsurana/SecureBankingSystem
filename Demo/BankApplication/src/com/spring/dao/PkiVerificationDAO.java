package com.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.model.PkiVerification;
import com.spring.model.UserVerification;
import com.spring.util.CustomHibernateDAOSupport;

@Repository("PkiVerificationDao")
public class PkiVerificationDAO extends CustomHibernateDAOSupport  {

	public PkiVerification findUserVerificationDetailsByUserName(String userName) {
		List list = getHibernateTemplate().find("from PkiVerification where username = ?",userName);
		if (list.size() != 0)
			return (PkiVerification) list.get(0);
		else{			
			return null;
		}
	}

	public void setPkiVerificationDetails(PkiVerification pkiVerification) {
		getHibernateTemplate().save(pkiVerification);
	}

	public void updatePkiVerificationDetails(PkiVerification pkiVerification){
		getHibernateTemplate().update(pkiVerification);
	}
	public void clearPkiVerificationDetails(PkiVerification pkiVerification) {
		getHibernateTemplate().delete(pkiVerification);
	}
}
