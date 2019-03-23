package com.tzh.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzh.entity.Page;
import com.tzh.entity.Role;
import com.tzh.entity.User;
import com.tzh.service.UserService;
import com.tzh.util.JsonUtils;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/userManagement")
	public String  userListPage(){
		System.out.println("欢迎进入用户管理页面");
		return "user/userList";
	}
	
	//查询用户
	@ResponseBody
	@RequestMapping(value="/user",method={RequestMethod.GET})
	public String query(HttpServletRequest request){
		Page<User> page = new Page<User>();
		String pageNow = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		if(pageNow != null && !"".equals(pageNow))
		{
			page.setPage(Integer.valueOf(pageNow));
			page.setPageSize(Integer.valueOf(pageSize));
		}
		page = userService.query(page);
		String userjson = "";
		try {
			// false表示数组中的属性不需要转成json,如果是true代表只将数组中的属性转成json格式
			userjson = JsonUtils.beanToJson(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userjson;
	}
		
		
	//添加用户
	@RequestMapping(value="/user",method={RequestMethod.POST})
	public String addUser(User user){
		userService.addUser(user);
		return "forward:/user/userManagement";
	}
	
	//删除用户
	@ResponseBody
	@RequestMapping(value="/user",method={RequestMethod.DELETE})
	public String deleteUser(Integer id){
		String result = userService.deleteUser(id);
		return result;
	}
	
	//修改用户
	@RequestMapping(value="/user",method={RequestMethod.PUT})
	public String updateUser(User user){
		userService.updateUser(user);
		return "redirect:/user/userManagement";
	}
	 
	//跳入添加页面或修改页面
	@RequestMapping(value="/addOrUpdatePage/{value}",method={RequestMethod.GET})
	public String addOrUpdatePage(Model model,@PathVariable("value") String value){
		if ("add".equals(value)) {
			System.out.println("进入添加回显页面");
			model.addAttribute("value","add");
			model.addAttribute("user", new User());
		} else {
			System.out.println("进入修改页面");
			HashMap<String, String> enable = new HashMap<String, String>();
			enable.put("0", "停用");
			enable.put("1", "启用");
			User user = userService.queryUserById(Integer.valueOf(value));
			model.addAttribute("enable",enable);
			model.addAttribute("value","update");
			model.addAttribute("user",user);
		}
		return "user/addOrUpdatePage";
	}
	//角色回显
	@ResponseBody
	@RequestMapping(value="/addEcho",method={RequestMethod.GET})
	public String addEcho(Integer id){
		List<Role> roleList = userService.queryAllRole(id);
		String rolejson = "";
		try {
			// false表示数组中的属性不需要转成json,如果是true代表只将数组中的属性转成json格式
			rolejson = JsonUtils.beanListToJson(roleList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rolejson;
	}
	//添加角色
	@ResponseBody
	@RequestMapping(value="/addRole",method={RequestMethod.POST})
	public String addRole(Integer userId,Long[] roleId){
		String value = userService.addRole(userId,roleId);
		return value;
	}
	
}
