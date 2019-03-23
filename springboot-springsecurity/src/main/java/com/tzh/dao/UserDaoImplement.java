package com.tzh.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.tzh.entity.Page;
import com.tzh.entity.Role;
import com.tzh.entity.User;

@Repository
public class UserDaoImplement implements UserDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Page<User> query(Page<User> page) {
		//      10            5
		//  开始下标                         每页多少条记录                      第几页
		int startIndex = page.getPageSize() * (page.getPage()-1);
		int pageSize = page.getPageSize();
		Query q = entityManager.createQuery("from User").setFirstResult(startIndex).setMaxResults(pageSize);
		//查询总数
		Query q1 = entityManager.createNativeQuery("select count(*) from User");
		BigInteger count = (BigInteger) q1.getSingleResult();
		page.setPageSizeCount(count.intValue());
		List<User> listResource = q.getResultList();
		page.setT(listResource);
		return page;
	}
	
	@Override
	public User findUserByName(String userName) {
		User user = null;
		try{
			String jpql="from User where userAccount=:username";
			Query query =  entityManager.createQuery(jpql).setParameter("username",userName);
			user = (User) query.getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Integer addUser(User user) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encode = passwordEncoder.encode(user.getUserPassword());
		user.setUserPassword(encode);
		user.setEnable(1);//设置该账号是否启用，默认是启动
		entityManager.persist(user);
		return user.getId();
	}
	
	//给用户添加角色
	@Override
	public void addRole_Add(Integer result) {
		String sql = "insert into role_user values(?,?)";
		entityManager.createNativeQuery(sql).setParameter(1,2).setParameter(2,result).executeUpdate();
	}

	@Override
	public User queryUserById(Integer id) {
		String jpql = "from User where id=:id";
		Query query =  entityManager.createQuery(jpql).setParameter("id",id);
		User user = (User) query.getSingleResult();
		return user;
	}

	@Override
	public String deleteUser(Integer id) {
		String sql = "delete from User where id=:id";
		Query query=entityManager.createQuery(sql).setParameter("id",id);
		Integer result=query.executeUpdate();
		return String.valueOf(result);
	}

	@Override
	public Integer deleteRole_User(Integer id) {
		String sql = "delete from role_user where user_id=?";
		Integer value = entityManager.createNativeQuery(sql).setParameter(1,id).executeUpdate();
		return value;
	}

	@Override
	public void updateUser(User user) {
		User byIdUser = entityManager.find(User.class,user.getId());
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encode = passwordEncoder.encode(user.getUserPassword());
		user.setUserPassword(encode);
		byIdUser.setUserAccount(user.getUserAccount());
		byIdUser.setUserPassword(user.getUserPassword());
		byIdUser.setEnable(user.getEnable());
		
	}

	@Override
	public List<Role> queryAllRole() {
		String sql = "from Role";
		List<Role> roleList = entityManager.createQuery(sql).getResultList();
		return roleList;
	}

	@Override
	public List<Role> queryRoleById(Integer id) {
		String sql = "select * from role where id in(select role_id from role_user where user_id=:id)";
		Query query = entityManager.createNativeQuery(sql).setParameter("id",id);
		List<Object> list = null;
		List<Role> roleList = new ArrayList<Role>();
		list = query.getResultList();
		for (int i = 0; i < list.size(); i++) {
			Role role = new Role();
			Object[] obj = (Object[])list.get(i);
			role.setId(Integer.valueOf(obj[0].toString()));
			role.setRoleName(obj[1].toString());
			role.setRoleKey(obj[2].toString());
			roleList.add(role);
		}
		return roleList;
	}

	@Override
	public String addRole(Integer userId, Long[] roleId) {
		for (int i = 0; i < roleId.length; i++) {
			String sql = "insert into role_user values(?,?)";
			entityManager.createNativeQuery(sql).setParameter(1, roleId[i]).setParameter(2,userId).executeUpdate();
		}
		return "success";
	}
}
