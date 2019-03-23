package com.tzh.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzh.dao.UserDao;
import com.tzh.entity.Page;
import com.tzh.entity.Role;
import com.tzh.entity.User;

@Service
public class UserServiceImplement implements UserService{
	
	@Resource
	private UserDao userDao;
	
	@Override
	public User findUserByName(String username) {
		return userDao.findUserByName(username);
	}

	@Override
	public Page<User> query(Page<User> page) {
		return userDao.query(page);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)//提交事务
	@Override
	public void addUser(User user) {
		Integer result = userDao.addUser(user);
		if(result != null){
			userDao.addRole_Add(result);
		} else {
			return;
		}
	}

	@Override
	public User queryUserById(Integer id) {
		User user = userDao.queryUserById(id);
		return user;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)//提交事务
	@Override
	public String deleteUser(Integer id) {
		String result = userDao.deleteUser(id);
		if(result !=null){
			userDao.deleteRole_User(id);
		} else {
			System.out.println("删除失败");
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)//提交事务
	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	public List<Role> queryAllRole(Integer id) {
		List<Role> list = userDao.queryAllRole();
		List<Role> roleList = new ArrayList<Role>();
		for (Role role : list) {
			Integer is = obtainId(id,role.getId());
			if(is==1){
				role.setSentence("1");
			}else if(is==0){
				role.setSentence("0");
			}
			roleList.add(role);
		}
		return roleList;
	}
	
	
	public Integer obtainId(Integer id,Integer listId){
		Integer value = 0;
		List<Role> roleListByid = userDao.queryRoleById(id);
		for (Role role : roleListByid) {
			if(listId.equals(role.getId())){
				value=1;
			}else{
				System.out.println("");
			}
		}
		return value;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)//提交事务
	@Override
	public String addRole(Integer userId, Long[] roleId) {
		Integer value = userDao.deleteRole_User(userId);
		String result= "";
		if(value !=null){
			result = userDao.addRole(userId,roleId);
		}
		return result;
	}
	
}
