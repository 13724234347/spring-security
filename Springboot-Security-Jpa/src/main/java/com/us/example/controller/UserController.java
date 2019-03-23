package com.us.example.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.us.example.model.Page;
import com.us.example.model.Parameter;
import com.us.example.model.User;
import com.us.example.security.CustomUserService;
import com.us.example.service.UserService;

@Controller("userController")
public class UserController
{
    @Autowired
    private UserService userService;
    
    @Autowired
    private CustomUserService cus;
    
    /**
     * 无权限进入无权限页面
     * String
     * 2018年8月14日上午11:36:13
     */
    @RequestMapping("/failure")
    public String a(Model model){
        System.out.println("无权限-------");
        model.addAttribute("loggedinuser", getPrincipal());
        return "failure";
    }


    @RequestMapping("/admin")
    @ResponseBody
    public String hello(){
        return "hello admin";
    }

    /**
     * 准备进入登录页面
     * String
     * 2018年8月14日上午11:41:54
     */
    @RequestMapping("/loginPage")
    public String login(){
        System.out.println("准备进入login");
        return "login";
    }

    
    
    /**
     * 进入index页面
     * String
     * 2018年8月9日下午8:02:19
     */
    @RequestMapping(value = "/user/b", method = RequestMethod.GET)
    public String getList1(Map<String, Object> map){
        return "user/index";
    }
    
    /**
     * 查询所有用户
     * String
     * 2018年8月9日下午8:02:19
     */
    @RequestMapping(value = "/user/a", method = RequestMethod.GET)
    @ResponseBody
    public Page<User> getList(Page<Parameter> pages, Parameter par){
        pages.setT(par);
        
        Page<User> page = null;
        page = userService.findAllByPage(pages);
        
        return page;
    }

    /**
     * 进入添加页面
     * String
     * 2018年8月14日下午2:51:18
     */
    @RequestMapping(value = "/user/adds", method = RequestMethod.GET)
    public String add(Map<String, Object> map) {
        map.put("user", new User());
        return "user/add";
    }

    /**
     * 添加用户
     * String
     * 2018年8月14日下午3:05:02
     */
    @RequestMapping(value = "/user/a", method = RequestMethod.POST)
    public String save(User user){
        Integer a = 0;
        user.setEnable(0);
        a = userService.add_User(user);
        
        if (0 == a) {
            System.out.println("添加失败");
        } else {
            System.out.println("添加成功");
        }
        
        return "redirect:/user/b";
    }
    
    /**
     * 进入改密页面
     * String
     * 2018年8月14日下午5:37:01
     */
    @RequestMapping(value = "/user/updates/{id}", method = RequestMethod.GET)
    public String updates(Page<Parameter> pages, Parameter par, @PathVariable("id")Integer id,
            Map<String, Object> map) {
        pages.setT(par);
        User user = new User();
        user = userService.findByUserId(id);
        user.setPassword("");
        map.put("user", user);
        map.put("page", pages);
        return "user/update";
    }


    /**
     * 修改密码
     * String
     * 2018年8月14日下午10:30:18
     */
    @RequestMapping(value = "/user/a", method = RequestMethod.PUT)
    public String update(User user, Page<Parameter> pages, Parameter par, String pass,
            Map<String, Object> map){
        String d = "";
        pages.setT(par);
            
        cus.changePassword(user.getUsername(), user.getPassword(), pass);
            
        map.put("page", pages);
        d = "user/index";
        return d;
    }
    
    /**
     * 重置密码
     * String
     * 2018年8月14日下午10:30:18
     */
    @RequestMapping(value = "/user/reset", method = RequestMethod.PUT)
    @ResponseBody
    public String resets(Integer id){
        String d = "";
        System.out.println("进入重置密码配置");
        User user = userService.findByUserId(id);
        
        cus.changePassword1(user.getUsername(), "00000000");
            
        d = "yes";
        return d;
    }
    
    /**
     * 启用/禁用 用户
     * String
     * 2018年8月14日下午10:30:18
     */
    @RequestMapping(value = "/user/c", method = RequestMethod.PUT)
    @ResponseBody
    public String update1(Integer id, Map<String, Object> map){
        String d = "";
        User users = new User();
        users.setId(id);
        userService.update_User(users);
        d = "yes";
        return d;
    }
        
    /**
     * 删除用户
     * String
     * 2018年8月14日下午3:09:59
     */
    @RequestMapping(value = "/user/a", method = RequestMethod.DELETE)
    @ResponseBody
    public Integer delete(Integer id){
        Integer a = 0;
        
        a = userService.delete_User(id);
        
        
        return a;
    }
    
    /**
     * 授权
     * String
     * 2018年8月20日下午4:39:20
     */
    @RequestMapping(value = "/user/userAuthorized2", method = RequestMethod.PUT)
    public String user_Authorized2(Integer id, HttpServletRequest request) {
        String[] idstr = request.getParameterValues("sel");
        
        userService.user_Role(id, idstr);
        
        return "redirect:/user/b";
    }
    
    /**
     * 获得登录用户名(即账号)
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
    
}
