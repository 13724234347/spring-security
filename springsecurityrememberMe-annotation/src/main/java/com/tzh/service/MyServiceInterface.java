package com.tzh.service;

import com.tzh.model.User;

public interface MyServiceInterface {
	User findById(int id);
	
	User findBySso(String sso);
}
