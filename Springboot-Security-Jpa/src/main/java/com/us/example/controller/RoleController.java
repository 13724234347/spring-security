package com.us.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.us.example.model.Page;
import com.us.example.model.Role;
import com.us.example.service.RoleService;

@Controller("roleController")
public class RoleController
{
    @Autowired
    private RoleService roleService;
    
    /**
     * 赋权
     * String
     * 2018年8月20日下午7:55:18
     */
    @RequestMapping(value = "/role/roleEmpowerment2", method = RequestMethod.PUT)
    public String role_Empowerment2(Integer id1, HttpServletRequest request) {
        String[] idstr = request.getParameterValues("id");
        
        roleService.role_Resource(id1, idstr);
        
        return "redirect:/role/b";
    }
    
    /**
     * 授权查询所有角色
     * List<Role>
     * 2018年8月20日下午3:09:45
     */
    @RequestMapping(value = "/role/userAuthorized1", method = RequestMethod.GET)
    @ResponseBody
    public List<Role> findAll() {
        List<Role> list = new ArrayList<Role>();
        
        list = roleService.findAll();
        
        return list;
    }
    
    /**
     * 授权显值(根据用户ID获得所有角色ID)
     * List<Integer>
     * 2018年8月20日下午3:21:15
     */
    @RequestMapping(value = "/role/userAuthorized", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> user_Authorized(Integer id) {
        return roleService.findAllRoleId(id);
    }
    
    /**
     * 进入index页面
     * String
     * 2018年8月9日下午8:02:19
     */
    @RequestMapping(value = "/role/b", method = RequestMethod.GET)
    public String getList1(){
        System.out.println("进入roleIndex页面");
        return "role/index";
    }
    
    /**
     * 查询所有角色
     * String
     * 2018年8月9日下午8:02:19
     */
    @RequestMapping(value = "/role/a", method = RequestMethod.GET)
    @ResponseBody
    public Page<Role> getList(Page<Role> pages){
        
        Page<Role> page = null;
        page = roleService.findAllByPage(pages);
        
        return page;
    }
    
    /**
     * 进入添加页面
     * String
     * 2018年8月14日下午2:51:18
     */
    @RequestMapping(value = "/role/adds", method = RequestMethod.GET)
    public String add(Map<String, Object> map) {
        map.put("role", new Role());
        return "role/add";
    }

    /**
     * 添加角色
     * String
     * 2018年8月14日下午3:05:02
     */
    @RequestMapping(value = "/role/a", method = RequestMethod.POST)
    public String save(Role role){
        Integer a = 0;
        a = roleService.add_Role(role);
        
        if (0 == a) {
            System.out.println("添加失败");
        } else {
            System.out.println("添加成功");
        }
        
        return "redirect:/role/b";
    }
    
    /**
     * 进入修改页面
     * String
     * 2018年8月14日下午5:37:01
     */
    @RequestMapping(value = "/role/updates/{id}", method = RequestMethod.GET)
    public String updates(@PathVariable("id")Integer id, Map<String, Object> map) {
        
        Role role = new Role();
        role = roleService.findByRoleId(id);
        map.put("role", role);
        return "role/update";
    }


    /**
     * 修改角色信息
     * String
     * 2018年8月14日下午10:30:18
     */
    @RequestMapping(value = "/role/a", method = RequestMethod.PUT)
    public String update(Role role, Map<String, Object> map){
        Integer a = 0;
        a = roleService.update_Role(role);
        if (0 == a) {
            System.out.println("修改失败");
        } else {
            System.out.println("修改成功");
        }
        return "redirect:/role/b";
    }
    
    /**
     * 删除角色
     * String
     * 2018年8月14日下午3:09:59
     */
    @RequestMapping(value = "/role/a", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(Integer id){
        Integer a = 0;
        String b = "";
        a = roleService.delete_Role(id);
        
        if (0 == a) {
            b = "no";
        } else {
            b = "yes";
        }
        
        return b;
    }
    
}
