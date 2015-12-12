package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.PkiVerificationDAO;
import com.spring.model.PkiVerification;
import com.spring.model.UserVerification;

@Service("pkiVerificationService")
public class PkiVerificationService {
	
	private PkiVerificationDAO pkiVerificationDAO;

	public PkiVerificationDAO getPkiVerificationDAO() {
		return pkiVerificationDAO;
	}

	@Autowired
	public void setPkiVerificationDAO(PkiVerificationDAO pkiVerificationDAO) {
		this.pkiVerificationDAO = pkiVerificationDAO;
	}
	
	public PkiVerification findUserVerificationDetailsByUserName(String userName) {
		return pkiVerificationDAO.findUserVerificationDetailsByUserName(userName);
	}

	public void setPkiVerificationDetails(PkiVerification pkiVerification) {
		pkiVerificationDAO.setPkiVerificationDetails(pkiVerification);
	}
	public void updatePkiVerificationDetails(PkiVerification pkiVerification) {
		pkiVerificationDAO.updatePkiVerificationDetails(pkiVerification);
	}
	public void clearPkiVerificationDetails(PkiVerification pkiVerification) {
		pkiVerificationDAO.clearPkiVerificationDetails(pkiVerification);
	}
	

}
