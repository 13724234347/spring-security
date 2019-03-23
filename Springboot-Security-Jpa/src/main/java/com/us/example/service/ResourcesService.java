package com.us.example.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.us.example.dao.ResourcesDao;
import com.us.example.model.Page;
import com.us.example.model.ResourceParameter;
import com.us.example.model.Resources;

@Service
@Transactional
public class ResourcesService
{
    @Autowired
    private ResourcesDao resourcesDao;
    
    /**
     * 根据用户名+请求路径+请求方式判断页面图标显现
     * List<Resources>
     * 2018年8月23日下午8:13:51
     */
    public List<Resources> AuthorTag(Resources resources) {
        return resourcesDao.AuthorTag(resources.getUsername(), resources.getResUrl(), resources.getResMethod());
    }
    
    /**
     * 查询所有权限
     * List<Resources>
     * 2018年8月7日上午11:02:24
     */
    public List<Resources> findAll() {
        List<Resources> list = new ArrayList<>();
        list = resourcesDao.findAll();
        return list;
    }
    
    /**
     * 根据用户ID查询所有权限
     * List<Resources>
     * 2018年8月7日上午11:03:50
     */
    public List<Resources> findAllById(Integer userId) {
        List<Resources> list = new ArrayList<>();
        list = resourcesDao.findAllById(userId);
        return list;
    }
    
    /**
     * 分页查询权限
     * Page<Resources>
     * 2018年8月10日上午11:04:16
     */
    public Page<Resources> findAllByPage(Page<ResourceParameter> page) {
        Page<Resources> pages = new Page<Resources>();
        List<Resources> list = new ArrayList<>();
        
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
        if (null != page.getT().getrName() && !"".equals(page.getT().getrName())) {
            page.getT().setrName("%" + page.getT().getrName() + "%");
        }
        Integer a = 0;
        /**根据name+method进行分页查询*/
        if (null != page.getT().getrName() && !"".equals(page.getT().getrName()) && null != page.getT().getrMethod() && !"".equals(page.getT().getrMethod())) {
            list = resourcesDao.findSearchB3(page.getT().getrName(), page.getT().getrMethod(), start, end);
            a = resourcesDao.findSearchCount3(page.getT().getrName(), page.getT().getrMethod());
            /**根据name分页查询*/
        } else if (null != page.getT().getrName() && !"".equals(page.getT().getrName())) {
            list = resourcesDao.findSearchB1(page.getT().getrName(), start, end);
            a = resourcesDao.findSearchCount(page.getT().getrName());
            /**根据method进行查询*/
        } else if (null != page.getT().getrMethod() && !"".equals(page.getT().getrMethod())) {
            list = resourcesDao.findSearchB2(page.getT().getrMethod(), start, end);
            a = resourcesDao.findSearchCount2(page.getT().getrMethod());
            /**分页查询*/
        } else {
            list = resourcesDao.findAll1(start, end);
            a = resourcesDao.findAll1Count();
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
     * 根据角色Id查询权限ID
     * List<Integer>
     * 2018年8月10日下午5:37:01
     */
    public List<Integer> role_Resources(Integer id) {
        return resourcesDao.role_Resources(id);
    }
    
    /**
     * 根据权限ID查询权限
     * Resources
     * 2018年8月10日下午5:47:27
     */
    public Resources queryResources(Integer id) {
        return resourcesDao.queryResources(id);
    }
    
    /**
     * 添加权限，返回权限ID
     * Integer
     * 2018年8月10日下午5:59:52
     */
    public Integer add_Resources(Resources resources) {
        resources.setAdd_time(new Date());
        resources.setUp_time(new Date());
        Resources resources1 = new Resources();
        Integer a = 0;
        Integer b = 0;
        /**判断权限是否存在*/
        b = resourcesDao.queryResKey(resources.getResKey());
        if (0 == b) {
            resources1 = resourcesDao.saveAndFlush(resources);
        } else {
            resources1 = null;
        }
        if (null != resources1) {
            a = resources1.getId();
        } else {
            a = 0;
        }
        return a;
    }
    
    /**
     * 根据权限id修改权限信息
     * Integer
     * 2018年8月20日上午10:36:42
     */
    public Integer update_Resources(Resources resources) {
        resources.setUp_time(new Date());
        Integer a = 0;
        Integer b = 0;
        b = resourcesDao.queryResKey(resources.getResKey());
        if (0 == b) {
            /**如果修改后的key不存在，则进行修改*/
            a = resourcesDao.updateResources(resources.getId(), resources.getName(), resources.getParentId(), resources.getResUrl(), resources.getResMethod(), resources.getResKey(), resources.getType(), resources.getSort(), resources.getUp_time());
        } else {
            Resources re = new Resources();
            re = resourcesDao.ResKeys(resources.getResKey());
            /**如果修改的key的ID与数据库key的ID相等，就进行修改*/
            if (re.getId().equals(resources.getId())) {
                a = resourcesDao.updateResources(resources.getId(), resources.getName(), resources.getParentId(), resources.getResUrl(), resources.getResMethod(), resources.getType(), resources.getSort(), resources.getUp_time());
            } else {
                a = 0;
            }
        }
        return a;
    }
    
    /**
     * 根据ID删除权限
     * void
     * 2018年8月10日下午7:58:39
     */
    public void delete_Resources(Integer id) {
        Integer a = 0;
        /**判断权限是否被使用*/
        a = resourcesDao.queryRes(id);
        if (0 != a) {
            a = 0;
        } else {
            a = resourcesDao.deleteResources(id);
        }
        
        if (0 == a) {
            System.out.println("删除失败");
        } else {
            System.out.println("删除成功");
        }
    }
    
}
