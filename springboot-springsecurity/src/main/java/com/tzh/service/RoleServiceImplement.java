package com.tzh.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzh.dao.RoleDao;
import com.tzh.entity.Page;
import com.tzh.entity.Resources;
import com.tzh.entity.Role;

@Service
public class RoleServiceImplement implements RoleService{
	
	@Resource
	private RoleDao roleDao;

	@Override
	public Page<Role> query(Page<Role> page) {
		return roleDao.query(page);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)//提交事务
	@Override
	public void addRole(Role role) {
		Integer roleId = roleDao.addRole(role);
		if(roleId != null){
			roleDao.addResources_role(roleId);
		} else {
			return;
		}
	}

	
	@Transactional(propagation = Propagation.REQUIRED)//提交事务
	@Override
	public void updateRole(Role role) {
		roleDao.updateRole(role);
	}
	
	
	@Override
	public Role queryRoleById(Integer id) {
		return roleDao.queryRoleById(id);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)//提交事务
	@Override
	public String deleteRole(Integer id) {
		String result = roleDao.deleteRole(id);
		if(result !=null){
			roleDao.deleteResources_Role(id);
		}
		return result;
	}

	@Override
	public List<Resources> queryAllresources(Integer id) {
		List<Resources> list = roleDao.queryAllresources();
		List<Resources> resourcesList = new ArrayList<Resources>();
		for (Resources resources : list) {
			Integer is = obtainId(id,resources.getId());
			if(is==1){
				resources.setSentence("1");
			}else if(is==0){
				resources.setSentence("0");
			}
			resourcesList.add(resources);
		}
		return resourcesList;
	}
	
	public Integer obtainId(Integer id,Integer listId){
		Integer value = 0;
		List<Resources> resourcesListByid = roleDao.queryResourcesById(id);
		for (Resources resources : resourcesListByid) {
			if(listId.equals(resources.getId())){
				value=1;
			}else{
				System.out.println("");
			}
		}
		return value;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)//提交事务
	@Override
	public String addResources(Integer roleId, Long[] resourcesId) {
		Integer value = roleDao.deleteResources_Role(roleId);
		String result= "";
		if(value !=null){
			result = roleDao.addResources(roleId,resourcesId);
		}
		return result;
	}
	
}
