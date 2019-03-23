package com.tzh.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tzh.model.User;

@Repository
public class MyDaoImplement implements MyDaoInterface{

	public User findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public User findBySSO(String sso) {
		// TODO Auto-generated method stub
		return null;
	}

//	public User findById(int id) {
//		return getByKey(id);
//	}
//
//	public User findBySSO(String sso) {
//		Criteria crit = createEntityCriteria();
//		crit.add(Restrictions.eq("ssoId", sso));
//		return (User) crit.uniqueResult();
//	}

}
