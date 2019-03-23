package com.tzh.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tzh.entity.Page;
import com.tzh.entity.Resources;
import com.tzh.entity.Role;
import com.tzh.entity.User;

@Repository
public class RoleDaoImplement implements RoleDao{
		
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Role> query(Page page) {
//      10            5
		//  开始下标                         每页多少条记录                      第几页
		int startIndex = page.getPageSize() * (page.getPage()-1);
		int pageSize = page.getPageSize();
		Query q = entityManager.createQuery("from Role").setFirstResult(startIndex).setMaxResults(pageSize);
		//查询总数
		Query q1 = entityManager.createNativeQuery("select count(*) from Role");
		BigInteger count = (BigInteger) q1.getSingleResult();
		page.setPageSizeCount(count.intValue());
		List<Role> listResource = q.getResultList();
		page.setT(listResource);
		return page;
	}

	@Override
	public Integer addRole(Role role) {
		entityManager.persist(role);
		return role.getId();
	}

	@Override
	public void addResources_role(Integer roleId) {
		Integer[] resourcesId = resourcesId();
		String sql = "insert into resources_role values(?,?)";
		for (int i = 0; i < resourcesId.length; i++) {
			entityManager.createNativeQuery(sql).setParameter(1, resourcesId[i]).setParameter(2,roleId).executeUpdate();
		}
	}
	
	public Integer[] resourcesId(){
		String sql = "from Resources where method=:method";
		List<Resources> resourcesList = entityManager.createQuery(sql).setParameter("method","get").getResultList();
		Integer[] resourcesId = new Integer[resourcesList.size()];
		for (int i = 0; i < resourcesList.size(); i++) {
			Integer id =  resourcesList.get(i).getId();
			resourcesId[i] = id;
		}
		return resourcesId;
	}

	@Override
	public Role queryRoleById(Integer id) {
		String sql = "from Role where id=:id";
		Role role = (Role) entityManager.createQuery(sql).setParameter("id",id).getSingleResult();
		return role;
	}

	@Override
	public void updateRole(Role role) {
		Role byIdRole = entityManager.find(Role.class,role.getId());
		byIdRole.setRoleName(role.getRoleName());
		byIdRole.setRoleKey(role.getRoleKey());
	}

	@Override
	public String deleteRole(Integer id) {
		String sql = "delete from Role where id=:id";
		Query query=entityManager.createQuery(sql).setParameter("id",id);
		Integer result=query.executeUpdate();
		return String.valueOf(result);
	}

	@Override
	public Integer deleteResources_Role(Integer id) {
		String sql = "delete from resources_role where role_id=?";
		Integer value = entityManager.createNativeQuery(sql).setParameter(1,id).executeUpdate();
		return value;
	}

	@Override
	public List<Resources> queryAllresources() {
		String sql = "from Resources";
		List<Resources> roleList = entityManager.createQuery(sql).getResultList();
		return roleList;
	}

	@Override
	public List<Resources> queryResourcesById(Integer id) {
		String sql = "select * from resources where id in(select resources_id from resources_role where role_id=:id)";
		Query query = entityManager.createNativeQuery(sql).setParameter("id",id);
		List<Object> list = null;
		List<Resources> resourcesList = new ArrayList<Resources>();
		list = query.getResultList();
		for (int i = 0; i < list.size(); i++) {
			Resources resources = new Resources();
			Object[] obj = (Object[])list.get(i);
			resources.setId(Integer.valueOf(obj[0].toString()));
			resources.setMethod(obj[1].toString());
			resources.setName(obj[2].toString());
			resources.setResKey(obj[3].toString());
			resources.setResUrl(obj[4].toString());
			resources.setType(Integer.valueOf(obj[5].toString()));
			resources.setSort(Integer.valueOf(obj[6].toString()));
			resourcesList.add(resources);
		}
		return resourcesList;
	}

	@Override
	public String addResources(Integer roleId, Long[] resourcesId) {
		for (int i = 0; i < resourcesId.length; i++) {
			String sql = "insert into resources_role values(?,?)";
			entityManager.createNativeQuery(sql).setParameter(1, resourcesId[i]).setParameter(2,roleId).executeUpdate();
		}
		return "success";
	}
}
