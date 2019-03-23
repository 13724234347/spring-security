package com.tzh.service;

import java.util.List;

import com.tzh.entity.Page;
import com.tzh.entity.Role;
import com.tzh.entity.User;

public interface UserService {
	User findUserByName(String username);
	
	Page<User> query(Page<User> page);
	
	void addUser(User user);
	
	User queryUserById(Integer id);
	
	String deleteUser(Integer id);
	
	void updateUser(User user);
	
	List<Role> queryAllRole(Integer id);
	
	String addRole(Integer userId,Long[] roleId);
}
