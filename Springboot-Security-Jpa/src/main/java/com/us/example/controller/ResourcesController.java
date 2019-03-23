package com.us.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.us.example.model.Page;
import com.us.example.model.ResourceParameter;
import com.us.example.model.Resources;
import com.us.example.model.ResourcesMethod;
import com.us.example.model.Sorts;
import com.us.example.model.Types;
import com.us.example.service.ResourcesService;

@Controller
public class ResourcesController
{
    @Autowired
    private ResourcesService resourcesService;
    
    /**
     * 获取所有请求方式
     * ResourcesMethod
     * 2018年8月18日下午8:45:25
     */
    public List<ResourcesMethod> getMethods() {
        List<ResourcesMethod> list = new ArrayList<>();
        ResourcesMethod method = new ResourcesMethod();
        method.setId(1);
        method.setName("GET");
        list.add(method);
        ResourcesMethod method1 = new ResourcesMethod();
        method1.setId(2);
        method1.setName("POST");
        list.add(method1);
        ResourcesMethod method2 = new ResourcesMethod();
        method2.setId(3);
        method2.setName("PUT");
        list.add(method2);
        ResourcesMethod method3 = new ResourcesMethod();
        method3.setId(4);
        method3.setName("DELETE");
        list.add(method3);
        ResourcesMethod method4 = new ResourcesMethod();
        method4.setId(5);
        method4.setName("ALL");
        list.add(method4);
        return list;
    }
    /**
     * 获取所有树形类型
     * List<Types>
     * 2018年8月18日下午9:22:30
     */
    public List<Types> getTypes() {
        List<Types> list = new ArrayList<>();
        Types type = new Types();
        type.setId(0);
        type.setName("菜单");
        list.add(type);
        Types type1 = new Types();
        type1.setId(1);
        type1.setName("按钮");
        list.add(type1);
        return list;
    }
    /**
     * 获取所有排序
     * List<Sorts>
     * 2018年8月18日下午9:30:18
     */
    public List<Sorts> getSorts() {
        List<Sorts> list = new ArrayList<>();
        Sorts sorts = new Sorts();
        sorts.setId(0);
        sorts.setName("升序");
        list.add(sorts);
        Sorts sorts1 = new Sorts();
        sorts1.setId(1);
        sorts1.setName("降序");
        list.add(sorts1);
        return list;
    }
    
    /**
     * 返回所有请求方式选择
     * List<ResourcesMethod>
     * 2018年8月24日下午9:25:40
     */
    @RequestMapping(value = "/resources/method", method = RequestMethod.GET)
    @ResponseBody
    public List<ResourcesMethod> Method() {
        List<ResourcesMethod> list = getMethods();
        ResourcesMethod methods = new ResourcesMethod();
        methods.setId(null);
        methods.setName("NULL");
        list.add(methods);
        return list;
    }
    
    /**
     * 进入index页面
     * String
     * 2018年8月9日下午8:02:19
     */
    @RequestMapping(value = "/resources/b", method = RequestMethod.GET)
    public String getList1(){
        System.out.println("进入index页面");
        return "resources/index";
    }
    
    /**
     * 查询所有用户
     * String
     * 2018年8月9日下午8:02:19
     */
    @RequestMapping(value = "/resources/a", method = RequestMethod.GET)
    @ResponseBody
    public Page<Resources> getList(Page<ResourceParameter> pages, ResourceParameter par){
        
        pages.setT(par);
        
        Page<Resources> page = null;
        page = resourcesService.findAllByPage(pages);
        
        return page;
    }
    
    /**
     * 进入权限添加页面
     * String
     * 2018年8月14日下午2:51:18
     */
    @RequestMapping(value = "/resources/adds", method = RequestMethod.GET)
    public String add(Map<String, Object> map) {
        map.put("sorts", getSorts());
        map.put("types", getTypes());
        map.put("resourcesMethod", getMethods());
        map.put("resources", new Resources());
        return "resources/add";
    }

    /**
     * 添加权限
     * String
     * 2018年8月14日下午3:05:02
     */
    @RequestMapping(value = "/resources/a", method = RequestMethod.POST)
    public String save(Resources resources){
        Integer a = 0;
        a = resourcesService.add_Resources(resources);
        
        if (0 == a) {
            System.out.println("添加失败");
        } else {
            System.out.println("添加成功");
        }
        
        return "redirect:/resources/b";
    }
    
    /**
     * 进入权限修改页面
     * String
     * 2018年8月14日下午5:37:01
     */
    @RequestMapping(value = "/resources/updates/{id}", method = RequestMethod.GET)
    public String updates(Page<ResourceParameter> pages, ResourceParameter par, 
            @PathVariable("id")Integer id, Map<String, Object> map) {
        pages.setT(par);
        Resources resources = new Resources();
        resources = resourcesService.queryResources(id);
        map.put("sorts", getSorts());
        map.put("types", getTypes());
        map.put("resourcesMethod", getMethods());
        map.put("resources", resources);
        map.put("page", pages);
        return "resources/update";
    }


    /**
     * 修改权限信息
     * String
     * 2018年8月14日下午10:30:18
     */
    @RequestMapping(value = "/resources/a", method = RequestMethod.PUT)
    public String update(Resources resources, Page<ResourceParameter> pages, ResourceParameter par, 
            Map<String, Object> map){
        pages.setT(par);
        resourcesService.update_Resources(resources);
        map.put("page", pages);
        return "resources/index";
    }
    
    /**
     * 删除权限
     * String
     * 2018年8月14日下午3:09:59
     */
    @RequestMapping(value = "/resources/a", method = RequestMethod.DELETE)
    @ResponseBody
    public Integer delete(Integer id){
        Integer a = 0;
        
        resourcesService.delete_Resources(id);
        
        
        return a;
    }
    
    /**
     * 赋权查询所有权限(树形结构+复选框)
     * List<Resources>
     * 2018年8月20日下午7:32:43
     */
    @RequestMapping(value = "/resources/roleEmpowerments", method = RequestMethod.GET)
    @ResponseBody
    public String role_Empowerments() {
        List<Resources> list = new ArrayList<Resources>();
        String a = "";
        list = resourcesService.findAll();
        a += "[";
        for (int i = 0;i < list.size();i++)
        {
            a += "{\"id\": " + list.get(i).getId() + 
                    ", \"pid\": " + list.get(i).getParentId() + 
                    ", \"name\": "+'"' + list.get(i).getName() +'"';
            if ("0".equals(list.get(i).getParentId())) {
                a += ", \"open\": true";
            }
            
            a += ", \"attr\": \"id"+list.get(i).getId()+"\"}";
            if (i < (list.size() - 1)) {
                a += ",";
            }
        }
        a += "]";
        System.out.println(a);
        return a;
    }
    
    /**
     * 赋权查询所有权限
     * List<Resources>
     * 2018年8月20日下午7:32:43
     */
    @RequestMapping(value = "/resources/roleEmpowerment1", method = RequestMethod.GET)
    @ResponseBody
    public List<Resources> role_Empowerment1() {
        List<Resources> list = new ArrayList<Resources>();
        
        list = resourcesService.findAll();
        
        return list;
    }
    
    /**
     * 赋权显值(根据角色ID，查询对应的所有权限ID)
     * List<Integer>
     * 2018年8月20日下午7:37:49
     */
    @RequestMapping(value = "/resources/roleEmpowerment", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> role_Empowerment(Integer id) {
        List<Integer> list = new ArrayList<Integer>();
        
        list = resourcesService.role_Resources(id);
        
        return list;
    }
    
}
