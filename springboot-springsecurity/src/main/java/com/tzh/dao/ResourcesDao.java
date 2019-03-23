package com.tzh.dao;

import java.util.List;

import com.tzh.entity.Page;
import com.tzh.entity.Resources;

public interface ResourcesDao {
	 List<Resources> queryResourceToUserName(String userName);
	 
	 List<Resources> queryAllResource();
	 
	 List<Resources> queryResourc(Resources resources);
	 
	 List<Resources> queryMenu();
	 
	 
	 Page<Resources> query(Page<Resources> page);
	 
	 Resources queryResourcesById(Integer id);
	 
	 void addResources(Resources resources);
	 
	 void updateResources(Resources resources);
	 
	 String deleteResources(Integer id);
	 
	 Integer deleteResources_Role(Integer id);
}
