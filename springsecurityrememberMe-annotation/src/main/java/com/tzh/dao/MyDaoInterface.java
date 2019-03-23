package com.tzh.dao;

import com.tzh.model.User;

public interface MyDaoInterface {
	User findById(int id);
	
	User findBySSO(String sso);
}
