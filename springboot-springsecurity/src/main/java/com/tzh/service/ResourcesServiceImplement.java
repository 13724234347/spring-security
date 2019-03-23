package com.tzh.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzh.dao.ResourcesDao;
import com.tzh.entity.Page;
import com.tzh.entity.Resources;

@Service("resourcesService")
public class ResourcesServiceImplement implements ResourcesService{

	@Resource
	private ResourcesDao resourcesDao;

	@Override
	public List<Resources> queryResourceToUserName(String userName) {
		return resourcesDao.queryResourceToUserName(userName);
	}
	
	public List<Resources> queryAllResource(){
		return resourcesDao.queryAllResource();
	}
	
	public List<Resources> queryResourc(Resources resources){
		return resourcesDao.queryResourc(resources);
	}

	@Override
	public List<Resources> queryMenu() {
		return resourcesDao.queryMenu();
	}

	@Override
	public Page<Resources> query(Page<Resources> page) {
		return resourcesDao.query(page);
	}

	@Override
	public Resources queryResourcesById(Integer id) {
		return resourcesDao.queryResourcesById(id);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)//提交事务
	@Override
	public void addResources(Resources resources) {
		resourcesDao.addResources(resources);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)//提交事务
	@Override
	public void updateResources(Resources resources) {
		resourcesDao.updateResources(resources);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)//提交事务
	@Override
	public String deleteResources(Integer id) {
		String result = resourcesDao.deleteResources(id);
		if(result !=null){
			resourcesDao.deleteResources_Role(id);
		} else {
			System.out.println("删除失败");
		}
		return result;
	}

}
