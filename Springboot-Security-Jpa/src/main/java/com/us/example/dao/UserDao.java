package com.us.example.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.us.example.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer>
{
    /**
     * 根据用户名查询用户
     * User
     * 2018年8月6日下午8:17:20
     */
    User findByUsername(String username);
    
    /**
     * 根据用户id查询用户
     * User
     * 2018年8月10日上午11:24:02
     */
    @Query(value = "select * from t_user u where u.id = :id", nativeQuery = true)
    User findByUserId(@Param("id")Integer id);
    
    /**
     * 根据username和Enable查询
     * Page<User>
     * 2018年8月7日下午3:45:42
     */
    @Query(value = "select * from t_user u where u.username like :uName and u.enable = :uEnable order by u.up_time desc limit :start,:end", nativeQuery = true)
    List<User> findSearchC2(@Param("uName")String uName, @Param("uEnable")Integer uEnable, @Param("start")Integer start, @Param("end")Integer end);
    
    /**
     * 根据username和Enable查询分页总记录数
     * Integer
     * 2018年8月11日上午11:44:56
     */
    @Query(value = "select count(*) from t_user u where u.username like :uName and u.enable = :uEnable", nativeQuery = true)
    Integer findSearchC2Count(@Param("uName")String uName, @Param("uEnable")Integer uEnable);
    
    /**
     * 根据id查询
     * Page<User>
     * 2018年8月7日下午3:48:09
     */
    @Query(value = "select * from t_user u where u.id = :id order by u.up_time desc limit :start,:end", nativeQuery = true)
    List<User> findSearchA1(@Param("id")Integer id, @Param("start")Integer start, @Param("end")Integer end);
    
    /**
     * 根据id查询分页总记录数
     * Integer
     * 2018年8月11日上午11:47:01
     */
    @Query(value = "select count(*) from t_user u where u.id = :id", nativeQuery = true)
    Integer findSearchA1Count(@Param("id")Integer id);
    
    /**
     * 根据username查询
     * Page<User>
     * 2018年8月7日下午3:49:49
     */
    @Query(value = "select * from t_user u where u.username like :uName order by u.up_time desc limit :start,:end", nativeQuery = true)
    List<User> findSearchB1(@Param("uName")String uName, @Param("start")Integer start, @Param("end")Integer end);
    
    /**
     * 根据username查询分页总记录数
     * Integer
     * 2018年8月11日上午11:47:59
     */
    @Query(value = "select count(*) from t_user u where u.username like :uName", nativeQuery = true)
    Integer findSearchB1Count(@Param("uName")String uName);
    
    /**
     * 根据enable查询
     * Page<User>
     * 2018年8月7日下午3:50:27
     */
    @Query(value = "select * from t_user u where u.enable = :uEnable order by u.up_time desc limit :start,:end", nativeQuery = true)
    List<User> findSearchC1(@Param("uEnable")Integer uEnable, @Param("start")Integer start, @Param("end")Integer end);
    
    /**
     * 根据enable查询总记录数
     * Integer
     * 2018年8月11日上午11:51:33
     */
    @Query(value = "select count(*) from t_user u where u.enable = :uEnable", nativeQuery = true)
    Integer findSearchC1Count(@Param("uEnable")Integer uEnable);
    
    /**
     * 分页查询
     * List<User>
     * 2018年8月11日上午9:20:14
     */
    @Query(value = "select * from t_user order by up_time desc limit :start,:end", nativeQuery = true)
    List<User> findAll1(@Param("start")Integer start, @Param("end")Integer end);
    
    /**
     * 分页查询总记录数
     * Integer
     * 2018年8月11日上午11:52:30
     */
    @Query(value = "select count(*) from t_user", nativeQuery = true)
    Integer findAll1Count();
    
    /**
     * 改密
     * Integer
     * 2018年8月14日下午5:48:58
     */
    @Modifying
    @Query(value = "update t_user set password = :pass,up_time = :up_time where id = :id", nativeQuery = true)
    Integer updatePass(@Param("id")Integer id, @Param("pass")String pass, @Param("up_time")Date up_time);
    
    /**
     * 启用/禁用
     * Integer
     * 2018年8月14日下午5:51:15
     */
    @Modifying
    @Query(value = "update t_user set enable = :enable,up_time = :up_time where id = :id", nativeQuery = true)
    Integer updateEnable(@Param("id")Integer id, @Param("enable")Integer enable, @Param("up_time")Date up_time);
    
    /**
     * 修改更新时间
     * Integer
     * 2018年8月20日下午3:53:08
     */
    @Modifying
    @Query(value = "update t_user set up_time = :up_time where id = :id", nativeQuery = true)
    Integer UP_TIME(@Param("id")Integer id, @Param("up_time")Date up_time);
    
    /**
     * 删除用户前，解除用户与角色的关联关系
     * Integer
     * 2018年8月10日上午11:49:08
     */
    @Modifying
    @Query(value = "delete from t_user_role where user_id = :id", nativeQuery = true)
    Integer deleteAll(@Param("id")Integer id);
    
    /**
     * 删除用户
     * Integer
     * 2018年8月10日上午11:51:12
     */
    @Modifying
    @Query(value = "delete from t_user where id = :id", nativeQuery = true)
    Integer deleteUser(@Param("id")Integer id);
    
    /**
     * 为用户添加角色
     * Integer
     * 2018年8月20日下午3:45:07
     */
    @Modifying
    @Query(value = "insert into t_user_role(user_id, role_id) values(:id, :role_id)", nativeQuery = true)
    Integer user_Role(@Param("id")Integer id, @Param("role_id")Integer role_id);
}
