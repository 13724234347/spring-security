package com.us.example.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.us.example.dao.UserDao;
import com.us.example.model.Page;
import com.us.example.model.Parameter;
import com.us.example.model.User;

@Service
@Transactional
public class UserService
{
    @Autowired
    private UserDao userDao;
    
    /**
     * 根据用户名称查询用户
     * User
     * 2018年8月7日上午10:58:51
     */
    public User findByUsername(String username) {
        User user = new User();
        user = userDao.findByUsername(username);
        return user;
    }
    
    /**
     * 根据用户id查询用户
     * User
     * 2018年8月10日上午11:24:20
     */
    public User findByUserId(Integer id) {
        User user = new User();
        user = userDao.findByUserId(id);
        return user;
    }
    
    /**
     * 分页查询用户，带参数
     * Page<User>
     * 2018年8月7日下午2:35:07
     */
    public Page<User> findAllByPage(Page<Parameter> page) {
        Page<User> pages = new Page<User>();
        List<User> list = new ArrayList<>();
        
        if (0 == page.getPage()) {
            page.setPage(1);
        }
        
        Integer start = (page.getPage()-1) * page.getSize();
        Integer end = page.getSize();
//        String sorts = null;
//        if (page.getSort() == 0) {
//            sorts = "asc";
//        } else {
//            sorts = "desc";
//        }
        /**应用于模糊查询*/
        if (!"".equals(page.getT().getuName()) && null != page.getT().getuName()) {
            page.getT().setuName("%" + page.getT().getuName() + "%");
        }
        Integer a = 0;
        /**根据ID进行精确查询*/
        if (null != page.getT().getuId() && 0 != page.getT().getuId()) {
            list = userDao.findSearchA1(page.getT().getuId(), start, end);
            a = userDao.findSearchA1Count(page.getT().getuId());
            /**根据name+enable进行查询*/
        } else if (null != page.getT().getuName() && !"".equals(page.getT().getuName()) && null != page.getT().getuEnable()) {
            list = userDao.findSearchC2(page.getT().getuName(), page.getT().getuEnable(), start, end);
            /**根据条件获取所有相关数据的总数*/
            a = userDao.findSearchC2Count(page.getT().getuName(), page.getT().getuEnable());
            /**根据name进行模糊查询*/
        } else if (!"".equals(page.getT().getuName()) && null != page.getT().getuName()) {
            list = userDao.findSearchB1(page.getT().getuName(), start, end);
            /**根据条件获取所有相关数据的总数*/
            a = userDao.findSearchB1Count(page.getT().getuName());
            /**根据enable进行查询*/
        } else if (null != page.getT().getuEnable()) {
            list = userDao.findSearchC1(page.getT().getuEnable(), start, end);
            /**根据条件获取所有相关数据的总数*/
            a = userDao.findSearchC1Count(page.getT().getuEnable());
            /**无条件分页查询*/
        } else {
            list = userDao.findAll1(start, end);
            /**根据条件获取所有相关数据的总数*/
            a = userDao.findAll1Count();
        }
        pages.setPt(list);
        pages.setPage(page.getPage());
        pages.setSize(page.getSize());
        pages.setPageSizeCount(a);
        pages.setPageCount(pages.getPageCount());
        pages.setSort(page.getSort());
        return pages;
    }
    
    /**
     * 添加用户 返回用户id
     * Integer
     * 2018年8月10日上午11:38:08
     */
    public Integer add_User(User user) {
        User user1 = new User();
        /**判断用户是否存在*/
        user1 = userDao.findByUsername(user.getUsername());
        Integer a = null;
        if (null == user1) {
            /**security5.x默认提供的加密方式*/
            BCryptPasswordEncoder md5=new BCryptPasswordEncoder();
            String encodePassword = md5.encode(user.getPassword());
            user.setPassword(encodePassword);
            user.setAdd_time(new Date());
            user.setUp_time(new Date());
            User us = new User();
            us = userDao.saveAndFlush(user);
            a = us.getId();
        } else {
            a = 0;
        }
        return a;
    }
    
    /**
     * 改密或启用/禁用
     * Integer
     * 2018年8月14日下午6:00:41
     */
    public Integer update_User(User user) {
        user.setUp_time(new Date());
        Integer a = 0;
        Integer b = 0;
        User users = userDao.findByUserId(user.getId());
        if (b == users.getEnable()) {
            b = 1;
        } else {
            b = 0;
        }
        if (null == user.getPassword()) {
            a = userDao.updateEnable(user.getId(), b, user.getUp_time());
        } else {
            BCryptPasswordEncoder md5=new BCryptPasswordEncoder();
            String encodePassword = md5.encode(user.getPassword());
            user.setPassword(encodePassword);
            a = userDao.updatePass(user.getId(), user.getPassword(), user.getUp_time());
        }
        
        
        return a;
    }
    
    /**
     * 根据用户id删除用户
     * void
     * 2018年8月10日上午11:53:19
     */
    public Integer delete_User(Integer id) {
        Integer a = 0;
        /**删除用户前，解除与角色之间的关联*/
        a = userDao.deleteAll(id);
        /**删除用户*/
        a = userDao.deleteUser(id);
        if (null != a && 0 != a) {
            System.out.println("删除成功");
        } else {
            a = 0;
        }
        return a;
    }
    
    /**
     * 为用户授权
     * Integer
     * 2018年8月20日下午3:32:37
     */
    public Integer user_Role(Integer id, String[] idstr) {
        Integer a = 0;
        /**用户授权前，先解除所有角色*/
        a = userDao.deleteAll(id);
        if (null != idstr) {
            for (int i = 0; i < idstr.length; i++) {
                /**为用户添加角色*/
                a = userDao.user_Role(id, Integer.valueOf(idstr[i]));
            }
        }
        
        if (0 == a) {
            System.out.println("授权失败");
        } else {
            /**修改时间*/
            userDao.UP_TIME(id, new Date());
            System.out.println("授权成功");
        }
        
        return a;
    }
}
