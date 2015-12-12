package com.spring.service;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.RefAccountTypeDAO;
import com.spring.model.RefAccountType;

@Service("refAccountTypeService")
public class RefAccountTypeService {
	@Autowired
	RefAccountTypeDAO refAccountTypeDao;
	
	public RefAccountTypeDAO getRefUserRoleDao () {
		return this.refAccountTypeDao;
	}
	
	public void setRefUserRoleDao (RefAccountTypeDAO refAccountTypeDAO) {
		this.refAccountTypeDao = refAccountTypeDAO;
	}
	
	public RefAccountType findByName(String accountType){
		return refAccountTypeDao.findByName(accountType);
	}
}
