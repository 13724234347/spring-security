package com.tzh.dao;


import java.util.List;

import com.tzh.entity.Page;
import com.tzh.entity.Role;
import com.tzh.entity.User;

public interface UserDao {
	User findUserByName(String userName);
	
	Page<User> query(Page<User> page);
	
	Integer addUser(User user);
	
	void addRole_Add(Integer result);
	
	User queryUserById(Integer id);
	
	String deleteUser(Integer id);
	
	Integer deleteRole_User(Integer id);
	
	void updateUser(User user);
	
	List<Role> queryAllRole();
	
	List<Role> queryRoleById(Integer id);
	
	String addRole(Integer userId,Long[] roleId);
}
