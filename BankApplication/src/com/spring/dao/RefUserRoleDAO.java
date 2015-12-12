package com.spring.dao;

/**
 * @author Sagar Sangani
 * @date Oct 8, 2015
 */

import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.model.RefUserRole;
import com.spring.util.CustomHibernateDAOSupport;

@Repository("refUserRoleDao")
public class RefUserRoleDAO extends CustomHibernateDAOSupport {
	public RefUserRole findByName(String roleName){
		List list = getHibernateTemplate().find("from RefUserRole where bankUserRoleName = ?", roleName);
		return (RefUserRole)list.get(0);
	}
}
