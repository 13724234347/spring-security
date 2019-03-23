package com.us.example.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.us.example.dao.RoleDao;
import com.us.example.model.Page;
import com.us.example.model.Role;

@Service
@Transactional
public class RoleService 
{
    @Autowired
    private RoleDao roleDao;
    
    
    /**
     * 查询所有角色
     * List<Role>
     * 2018年8月7日上午10:53:05
     */
    public List<Role> findAll(){
        List<Role> list = new ArrayList<>();
        list = roleDao.findAll();
        return list;
    }
    
    /**
     * 角色分页查询
     * Page<Role>
     * 2018年8月10日上午10:40:39
     */
    public Page<Role> findAllByPage(Page<Role> page) {
        Page<Role> pages = new Page<Role>();
        List<Role> list = new ArrayList<>();
        
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
        Integer a = 0;
        list = roleDao.findAll1(start, end);
        a = roleDao.findAll1Count();
        pages.setPt(list);
        pages.setPage(page.getPage());
        pages.setSize(page.getSize());
        pages.setPageSizeCount(a);
        pages.setPageCount(pages.getPageCount());
        pages.setSort(page.getSort());
        
        return pages;
    }
    /**
     * 根据用户id查询所有角色Key
     * List<String>
     * 2018年8月10日下午3:51:03
     */
    public List<String> findAllRoleKey(Integer id) {
        return roleDao.findAllRoleKey(id);
    }
    
    /**
     * 根据用户id查询所有角色Id
     * List<Integer>
     * 2018年8月10日下午3:53:56
     */
    public List<Integer> findAllRoleId(Integer id) {
        List<Integer> list = new ArrayList<Integer>();
        
        list = roleDao.findAllRoleId(id);
        
        return list;
    }
    
    /**
     * 根据角色id查询角色
     * Role
     * 2018年8月10日下午4:23:12
     */
    public Role findByRoleId(Integer id) {
        Role role = new Role();
        role = roleDao.findByRoleId(id);
        return role;
    }
    
    /**
     * 添加角色返回角色ID
     * Integer
     * 2018年8月10日下午4:32:00
     */
    public Integer add_Role(Role role) {
        
        Integer a = 0;
        Integer b = 0;
        /**判断角色是否存在*/
        b = roleDao.findByRoleKey(role.getRoleKey());
        if (0 != b) {
            a = 0;
        } else {
            role.setAdd_time(new Date());
            role.setUp_time(new Date());
            Role roles = new Role();
            
            roles = roleDao.saveAndFlush(role);
            
            a = roles.getId();
        }
        
        return a;
    }
    
    /**
     * 修改角色信息
     * Integer
     * 2018年8月18日下午4:04:05
     */
    public Integer update_Role(Role role) {
        role.setUp_time(new Date());
        Integer a = 0;
        Integer b = 0;
        /**判断角色是否唯一*/
        b = roleDao.findByRoleKey(role.getRoleKey());
        if (0 != b) {
            a = roleDao.update_Role(role.getId(), role.getRoleDesc(), role.getUp_time());
        } else {
            a = roleDao.update_Role(role.getId(), role.getRoleKey(), role.getRoleDesc(), role.getUp_time());
        }
        return a;
    }
    
    /**
     * 删除角色
     * void
     * 2018年8月10日下午5:15:16
     */
    public Integer delete_Role(Integer id) {
        Integer a = null;
        /**查询角色是否被使用，没被使用则可被删除*/
        a = roleDao.queryRole(id);
        if (0 == a) {
            /**角色未被使用,先删除与权限的关联*/
            roleDao.deleteAll(id);
        }
        if (0 == a) {
            /**角色未被使用，删除角色*/
            a = roleDao.deleteRole(id);
        } else {
            a = 0;
        }
        if (0 == a) {
            System.out.println("删除失败");
        } else {
            System.out.println("删除成功");
        }
        return a;
    }
    
    /**
     * 赋权
     * Integer
     * 2018年8月20日下午7:58:21
     */
    public Integer role_Resource(Integer id, String[] idstr) {
        Integer a = 0;
        /**赋权前，先解除与权限之间的关联*/
        a = roleDao.deleteAll(id);
        if (null != idstr) {
            for (int i = 0; i < idstr.length; i++) {
                /**与权限建立关联*/
                roleDao.role_Resource(id, Integer.valueOf(idstr[i]));
            }
        }
        
        if (0 == a) {
            System.out.println("赋权失败");
        } else {
            roleDao.UP_TIME(id, new Date());
            System.out.println("赋权成功");
        }
        
        return a;
    }
    
}
