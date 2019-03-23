package com.us.example.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.us.example.model.Resources;

@Repository
public interface ResourcesDao extends JpaRepository<Resources, Integer>
{
    /**
     * 查询所有权限
     */
    List<Resources> findAll();
    
    /**
     * 根据用户名+请求路径+请求方式判断页面图标显现
     * List<Resources>
     * 2018年8月23日下午8:18:28
     */
    @Query(value = "SELECT tr.* FROM `t_resources` tr,`t_role` tro,`t_role_resources` trr"
            + ",`t_user` tu,`t_user_role` tur where tu.id = tur.user_id and "
            + " tro.id = tur.role_id and tro.id = trr.role_id and tr.id = trr.resources_id "
            + " and tu.username = :username and tr.resurl like :url "
            + " and tr.resmethod = :method", nativeQuery = true)
    List<Resources> AuthorTag(@Param("username")String username, @Param("url")String url
            , @Param("method")String method);
    
    /**
     * 根据用户ID查询所有权限
     * List<Resources>
     * 2018年8月6日下午8:33:44
     */
//    @Modifying
    @Query(value = "select p.* from t_user u" + 
        " LEFT JOIN t_user_role sru on u.id= sru.user_id" +
        " LEFT JOIN t_role r on sru.role_id=r.id" +
        " LEFT JOIN t_role_resources spr on spr.role_id=r.id" +
        " LEFT JOIN t_resources p on p.id =spr.resources_id" +
        " where u.id = :userId", nativeQuery = true)
    List<Resources> findAllById(@Param("userId")Integer userId);//@Param("userId")
    
    /**
     * 根据权限名称查询分页
     * Page<Resources>
     * 2018年8月10日上午11:01:23
     */
    @Query(value = "select * from t_resources r where r.name like :rName order by "
            + " up_time desc limit :start,:end", nativeQuery = true)
    List<Resources> findSearchB1(@Param("rName")String rName, @Param("start")Integer start, 
            @Param("end")Integer end);
    
    /**
     * 查询权限名称分页总数量
     * Integer
     * 2018年8月11日上午10:51:11
     */
    @Query(value = "select count(*) from t_resources r "
            + " where r.name like :rName", nativeQuery =true)
    Integer findSearchCount(@Param("rName")String rName);
    
    /**
     * 根据权限请求方式查询分页
     * Page<Resources>
     * 2018年8月10日上午11:01:23
     */
    @Query(value = "select * from t_resources r where r.resmethod = :rMethod order by "
            + " up_time desc limit :start,:end", nativeQuery = true)
    List<Resources> findSearchB2(@Param("rMethod")String rMethod, @Param("start")Integer start, 
            @Param("end")Integer end);
    
    /**
     * 查询请求方式分页总数量
     * Integer
     * 2018年8月11日上午10:51:11
     */
    @Query(value = "select count(*) from t_resources r "
            + " where r.resmethod = :rMethod", nativeQuery =true)
    Integer findSearchCount2(@Param("rMethod")String rMethod);
    
    /**
     * 根据权限名称和请求方式查询分页
     * Page<Resources>
     * 2018年8月10日上午11:01:23
     */
    @Query(value = "select * from t_resources r where r.name like :rName "
            + " and r.resmethod = :rMethod order by "
            + " up_time desc limit :start,:end", nativeQuery = true)
    List<Resources> findSearchB3(@Param("rName")String rName, @Param("rMethod")String rMethod, 
            @Param("start")Integer start, @Param("end")Integer end);
    
    /**
     * 查询权限名称+请求方式分页总数量
     * Integer
     * 2018年8月11日上午10:51:11
     */
    @Query(value = "select count(*) from t_resources r where r.name like :rName "
            + " and r.resmethod = :rMethod", nativeQuery =true)
    Integer findSearchCount3(@Param("rName")String rName, @Param("rMethod")String rMethod);
    
    /**
     * 分页查询
     * List<Resources>
     * 2018年8月11日上午10:41:32
     */
    @Query(value = "select * from t_resources order by "
            + " up_time desc limit :start,:end", nativeQuery = true)
    List<Resources> findAll1(@Param("start")Integer start, @Param("end")Integer end);
    
    /**
     * 分页总条数
     * Integer
     * 2018年8月11日上午10:52:29
     */
    @Query(value = "select count(*) from t_resources", nativeQuery = true)
    Integer findAll1Count();
    
    /**
     * 根据角色Id查询权限ID
     * List<Integer>
     * 2018年8月10日下午5:37:01
     */
    @Query(value = "select resources_id from t_role_resources "
            + " where role_id = :id", nativeQuery = true)
    List<Integer> role_Resources(@Param("id")Integer id);
    
    /**
     * 根据权限ID查询权限
     * Resources
     * 2018年8月10日下午5:47:27
     */
    @Query(value = "select * from t_resources where id = :id", nativeQuery = true)
    Resources queryResources(@Param("id")Integer id);
    
    /**
     * 根据权限key判断权限是否存在
     * Integer
     * 2018年8月20日上午11:02:29
     */
    @Query(value = "select count(*) from t_resources where res_key = :resKey", nativeQuery = true)
    Integer queryResKey(@Param("resKey")String resKey);
    
    /**
     * 根据权限key,查询权限对象
     * Integer
     * 2018年8月20日上午11:02:29
     */
    @Query(value = "select * from t_resources where res_key = :resKey", nativeQuery = true)
    Resources ResKeys(@Param("resKey")String resKey);
    
    /**
     * 根据权限ID修改权限
     * Integer
     * 2018年8月20日上午10:56:11
     */
    @Modifying
    @Query(value = "update t_resources set name = :name, parentid = :parentId, "
            + " resurl = :resUrl, resmethod = :resMethod, res_key = :resKey, type = :type, "
            + " sort = :sorts, up_time = :up_time where id = :id", nativeQuery = true)
    Integer updateResources(@Param("id")Integer id, @Param("name")String name, 
            @Param("parentId")String parentId, @Param("resUrl")String resUrl, 
            @Param("resMethod")String resMethod, @Param("resKey")String resKey, 
            @Param("type")Integer type, @Param("sorts")Integer sorts, @Param("up_time")Date up_time);
    /**
     * 根据权限ID修改权限(不包含res_key)
     * Integer
     * 2018年8月20日上午10:56:11
     */
    @Modifying
    @Query(value = "update t_resources set name = :name, parentid = :parentId, "
            + " resurl = :resUrl, resmethod = :resMethod, type = :type, "
            + " sort = :sorts, up_time = :up_time where id = :id", nativeQuery = true)
    Integer updateResources(@Param("id")Integer id, @Param("name")String name, 
            @Param("parentId")String parentId, @Param("resUrl")String resUrl, 
            @Param("resMethod")String resMethod, 
            @Param("type")Integer type, @Param("sorts")Integer sorts, @Param("up_time")Date up_time);
    
    
    /**
     * 判断是否有角色使用权限
     * Integer
     * 2018年8月10日下午7:53:27
     */
    @Query(value = "select count(r.id) from t_resources r "
            + " , t_role_resources rr where rr.resources_id = r.id "
            + " and r.id = :id", nativeQuery = true)
    Integer queryRes(@Param("id")Integer id);
    
    /**
     * 根据权限ID删除权限
     * Integer
     * 2018年8月10日下午7:57:06
     */
    @Modifying
    @Query(value = "delete from t_resources where id = :id", nativeQuery = true)
    Integer deleteResources(@Param("id")Integer id);
}
