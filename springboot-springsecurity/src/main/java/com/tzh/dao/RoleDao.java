package com.tzh.dao;

import java.util.List;

import com.tzh.entity.Page;
import com.tzh.entity.Resources;
import com.tzh.entity.Role;

public interface RoleDao {
	
	Page<Role> query(Page<Role> page);
	
	Integer addRole(Role role);
	
	void addResources_role(Integer roleId);
	
	Role queryRoleById(Integer id);
	
	void updateRole(Role role);
	
	String deleteRole(Integer id);
	
	Integer deleteResources_Role(Integer id);
	
	List<Resources> queryAllresources();
	
	List<Resources> queryResourcesById(Integer id);
	
	String addResources(Integer roleId,Long[] resourcesId);
}
