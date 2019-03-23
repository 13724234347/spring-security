package com.us.example.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.us.example.model.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer>
{
    /**
     * 查询所有角色
     */
    List<Role> findAll();
    
    /**
     * 分页查询
     * List<Role>
     * 2018年8月11日上午10:34:35
     */
    @Query(value = "select * from t_role order by up_time desc limit :start,:end", nativeQuery = true)
    List<Role> findAll1(@Param("start")Integer start, @Param("end")Integer end);
    
    /**
     * 分页查询总记录数
     * Integer
     * 2018年8月11日上午11:35:53
     */
    @Query(value = "select count(*) from t_role", nativeQuery = true)
    Integer findAll1Count();
    
    /**
     * 根据用户id查询所有角色Key
     * List<String>
     * 2018年8月10日下午3:51:03
     */
    @Query(value = "SELECT r.rolekey FROM `t_user` u "
            + " LEFT JOIN t_user_role ur on u.id = ur.user_id "
            + " LEFT JOIN t_role r on r.id = ur.role_id "
            + " where u.id = :id", nativeQuery = true)
    List<String> findAllRoleKey(@Param("id")Integer id);
    
    /**
     * 根据用户id查询所有角色Id
     * List<Integer>
     * 2018年8月10日下午3:53:56
     */
    @Query(value = "SELECT r.id FROM `t_user` u "
            + " LEFT JOIN t_user_role ur on u.id = ur.user_id "
            + " LEFT JOIN t_role r on r.id = ur.role_id "
            + " where u.id = :id", nativeQuery = true)
    List<Integer> findAllRoleId(@Param("id")Integer id);
    
    /**
     * 根据角色id查询角色
     * Role
     * 2018年8月10日下午4:23:12
     */
    @Query(value = "select * from t_role where id = :id", nativeQuery = true)
    Role findByRoleId(@Param("id")Integer id);
    
    /**
     * 根据角色key判断角色是否存在
     * Integer
     * 2018年8月18日下午3:58:42
     */
    @Query(value = "select count(*) from t_role where roleKey = :key", nativeQuery = true)
    Integer findByRoleKey(@Param("key")String roleKey);
    
    /**
     * 修改角色信息
     * Integer
     * 2018年8月18日下午4:09:01
     */
    @Modifying
    @Query(value = "update t_role set roleKey = :roleKey,roleDesc = :roleDesc,up_time = :up_time where id = :id", nativeQuery = true)
    Integer update_Role(@Param("id")Integer id, @Param("roleKey")String roleKey, @Param("roleDesc")String roleDesc, @Param("up_time")Date up_time);
    
    /**
     * 修改角色信息(不包含roleKey)
     * Integer
     * 2018年8月18日下午4:09:01
     */
    @Modifying
    @Query(value = "update t_role set roleDesc = :roleDesc,up_time = :up_time where id = :id", nativeQuery = true)
    Integer update_Role(@Param("id")Integer id, @Param("roleDesc")String roleDesc, @Param("up_time")Date up_time);
    
    /**
     * 更新修改时间
     * Integer
     * 2018年8月20日下午8:11:59
     */
    @Modifying
    @Query(value = "update t_role set up_time = :up_time where id = :id", nativeQuery = true)
    Integer UP_TIME(@Param("id")Integer id, @Param("up_time")Date up_time);
    
    /**
     * 删除角色要删除相关的权限
     * Integer
     * 2018年8月10日下午4:43:06
     */
    @Modifying
    @Query(value = "delete from t_role_resources where role_id = :id", nativeQuery = true)
    Integer deleteAll(@Param("id")Integer id);
    
    /**
     * 根据角色id判断角色是否被使用
     * Integer
     * 2018年8月10日下午5:04:11
     */
    @Query(value = "select count(ur.role_id) from t_role r "
            + " ,t_user_role ur where ur.role_id = r.id "
            + " and r.id = :id", nativeQuery = true)
    Integer queryRole(@Param("id")Integer id);
    
    /**
     * 删除角色
     * Integer
     * 2018年8月10日下午5:07:48
     */
    @Modifying
    @Query(value = "delete from t_role where id = :id", nativeQuery = true)
    Integer deleteRole(@Param("id")Integer id);
    
    /**
     * 赋权
     * Integer
     * 2018年8月20日下午8:09:18
     */
    @Modifying
    @Query(value = "insert t_role_resources(role_id, resources_id) values(:id, :resources_id)", nativeQuery = true)
    Integer role_Resource(@Param("id")Integer id, @Param("resources_id")Integer idstr);
}
