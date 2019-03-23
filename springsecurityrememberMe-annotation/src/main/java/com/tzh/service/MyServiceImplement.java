package com.tzh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzh.dao.MyDaoInterface;
import com.tzh.model.User;

@Service
@Transactional
public class MyServiceImplement implements MyServiceInterface{

	@Autowired
	private MyDaoInterface dao;

	public User findById(int id) {
		return dao.findById(id);
	}

	public User findBySso(String sso) {
		return dao.findBySSO(sso);
	}


}
