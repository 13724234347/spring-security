package com.tzh.service;

import java.util.List;

import com.tzh.entity.Page;
import com.tzh.entity.Resources;
import com.tzh.entity.Role;

public interface RoleService {
	
	Page<Role> query(Page<Role> page);
	
	void addRole(Role role);
	
	void updateRole(Role role);
	
	String deleteRole(Integer id);

	Role queryRoleById(Integer id);
	
	List<Resources> queryAllresources(Integer id);
	
	String addResources(Integer roleId,Long[] resourcesId);
	
}
