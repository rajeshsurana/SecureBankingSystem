package com.spring.service;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.RefUserRoleDAO;
import com.spring.model.RefUserRole;

@Service("refUserRoleService")
public class RefUserRoleService {
	@Autowired
	RefUserRoleDAO refUserRoleDao;
	
	public RefUserRoleDAO getRefUserRoleDao () {
		return this.refUserRoleDao;
	}
	
	public void setRefUserRoleDao (RefUserRoleDAO refUserRoleDAO) {
		this.refUserRoleDao = refUserRoleDAO;
	}
	
	public RefUserRole findRoleByName (String roleName) {
		return refUserRoleDao.findByName(roleName);
	}
}
