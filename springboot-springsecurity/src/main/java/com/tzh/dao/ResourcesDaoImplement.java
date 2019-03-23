package com.tzh.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

//http://www.147xs.com/book/6563/937486.html

import org.springframework.stereotype.Repository;

import com.tzh.entity.Page;
import com.tzh.entity.Resources;
import com.tzh.entity.Role;
import com.tzh.entity.User;

@Repository
public class ResourcesDaoImplement implements ResourcesDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Resources> queryAllResource() {
		String sql="from Resources ORDER BY sort ASC";
		List<Resources> list = entityManager.createQuery(sql).getResultList();
		return list;
	}


	@Override
	public List<Resources> queryResourceToUserName(String userName) {
		List<Object[]> list = new ArrayList<>();
		String sql="";
			sql += "select r.* from user u,role_user ru,resources_role rr,resources r "
					+ " where r.id = rr.resources_id "
					+ " and rr.role_id = ru.role_id "
					+ " and ru.user_id = u.id "
				    + " and u.username=? order by r.sort asc";
			list = entityManager.createNativeQuery(sql).setParameter(1,userName).getResultList();
			List<Resources> resourcesList = new ArrayList<>();
			for (Object[] obj : list) {
				Resources resources = new Resources();
				resources.setId((Integer)obj[0]);
				resources.setMethod((String)obj[1]);
				resources.setName((String)obj[2]);
				resources.setResKey((String)obj[3]);
				resources.setResUrl((String)obj[4]);
				resources.setSort((Integer)obj[5]);
				resources.setType((Integer)obj[6]);
				resourcesList.add(resources);
			}
			return resourcesList;
	}
	
	
	public List<Resources> queryResourc(Resources resources) {
		List<Object[]> list = new ArrayList<>();
		String sql="";
			sql += "select r.* from user u,role_user ru,resources_role rr,resources r "
					+ " where r.id = rr.resources_id "
					+ " and rr.role_id = ru.role_id "
					+ " and ru.user_id = u.id "
				    + " and u.username=? "
				    + " and r.res_url like ?"
				    + " and r.method=?"
				    + "order by r.sort asc";
			list = entityManager.createNativeQuery(sql).setParameter(1,resources.getUserName())
					.setParameter(2,resources.getResUrl()).setParameter(3,resources.getMethod()).getResultList();
			List<Resources> resourcesList = new ArrayList<>();
			for (Object[] obj : list) {
				Resources resources1 = new Resources();
				resources1.setId((Integer)obj[0]);
				resources1.setMethod((String)obj[1]);
				resources1.setName((String)obj[2]);
				resources1.setResKey((String)obj[3]);
				resources1.setResUrl((String)obj[4]);
				resources1.setSort((Integer)obj[5]);
				resources1.setType((Integer)obj[6]);
				resourcesList.add(resources1);
			}
			return resourcesList;
	}
	

	@Override
	public List<Resources> queryMenu() {
		String jqpl = "from Resources where type=:type";
		List<Resources> resourcesList = entityManager.createQuery(jqpl).setParameter("type",1).getResultList();
		return resourcesList;
	}
	@Override
	public Page<Resources> query(Page<Resources> page) {
//      10            5
		//  开始下标                         每页多少条记录                      第几页
		int startIndex = page.getPageSize() * (page.getPage()-1);
		int pageSize = page.getPageSize();
		Query q = entityManager.createQuery("from Resources").setFirstResult(startIndex).setMaxResults(pageSize);
		//查询总数
		Query q1 = entityManager.createNativeQuery("select count(*) from Resources");
		BigInteger count = (BigInteger) q1.getSingleResult();
		page.setPageSizeCount(count.intValue()); 
		List<Resources> listResource = q.getResultList();
		page.setT(listResource);
		return page;
	}
	@Override
	public Resources queryResourcesById(Integer id){
		String jpql = "from Resources where id=:id";
		Query query =  entityManager.createQuery(jpql).setParameter("id",id);
		Resources resources = (Resources) query.getSingleResult();
		return resources;
	}
	@Override
	public void addResources(Resources resources) {
		entityManager.persist(resources);
	}
	@Override
	public void updateResources(Resources resources) {
		Resources byIdResources = entityManager.find(Resources.class,resources.getId());
		byIdResources.setMethod(resources.getMethod());
		byIdResources.setName(resources.getName());
		byIdResources.setResKey(resources.getResKey());
		byIdResources.setResUrl(resources.getResUrl());
		byIdResources.setType(resources.getType());
	}
	@Override
	public String deleteResources(Integer id) {
		String sql = "delete from Resources where id=:id";
		Query query=entityManager.createQuery(sql).setParameter("id",id);
		Integer result=query.executeUpdate();
		return String.valueOf(result);
	}
	@Override
	public Integer deleteResources_Role(Integer id) {
		String sql = "delete from resources_role where resources_id=?"; 
		Integer value = entityManager.createNativeQuery(sql).setParameter(1,id).executeUpdate();
		return value;	
	}
}