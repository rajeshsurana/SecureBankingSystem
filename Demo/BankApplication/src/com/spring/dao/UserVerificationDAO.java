package com.spring.dao;

import java.util.List;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import org.springframework.stereotype.Repository;

import com.spring.model.BankUser;
import com.spring.model.UserVerification;
import com.spring.util.CustomHibernateDAOSupport;

/**
 * @author mahathi
 *
 */
@Repository("userVerificationDao")
public class UserVerificationDAO extends CustomHibernateDAOSupport {

	public UserVerification findUserVerificationDetailsByUserName(UserVerification bankuser) {
		List list = getHibernateTemplate().find("from UserVerification where verifyingUserName = ?",
				bankuser.getBankuser());
		if (list.size() != 0)
			return (UserVerification) list.get(0);
		else
			return null;
	}

	public void setUserVerificationDetails(UserVerification userVerification) {
		getHibernateTemplate().save(userVerification);
	}

	public void clearUserVerificationDetails(UserVerification userVerification) {
		getHibernateTemplate().delete(userVerification);
	}

}
