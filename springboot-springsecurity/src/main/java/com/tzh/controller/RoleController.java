package com.tzh.controller;


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
import com.tzh.entity.Resources;
import com.tzh.entity.Role;
import com.tzh.entity.User;
import com.tzh.service.RoleService;
import com.tzh.util.JsonUtils;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Resource
	private RoleService roleService;
	
	@RequestMapping("/roleManagement")
	public String  roleListPage(){
		System.out.println("欢迎进入角色管理页面");
		return "role/roleList";
	}
	//查询角色
	@ResponseBody
	@RequestMapping(value="/role",method={RequestMethod.GET})
	public String query(HttpServletRequest request){
		Page<Role> page = new Page<Role>();
		String pageNow = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		if(pageNow != null && !"".equals(pageNow))
		{
			page.setPage(Integer.valueOf(pageNow));
			page.setPageSize(Integer.valueOf(pageSize));
		}
		page = roleService.query(page);
		String rolejson = "";
		try {
			// false表示数组中的属性不需要转成json,如果是true代表只将数组中的属性转成json格式
			rolejson = JsonUtils.beanToJson(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rolejson;
	}
	
	
	//添加角色
	@RequestMapping(value="/role",method={RequestMethod.POST})
	public String addRole(Role role){
		roleService.addRole(role);
		return "redirect:/role/roleManagement";
	}
	//修改角色
	@RequestMapping(value="/role",method={RequestMethod.PUT})
	public String updateRole(Role role){
		roleService.updateRole(role);
		return "redirect:/role/roleManagement";
	}
	
	
	//删除角色
	@ResponseBody
	@RequestMapping(value="/role",method={RequestMethod.DELETE})
	public String deleteRole(Integer id){
		String result = roleService.deleteRole(id);
		return result;
	}
	
	//跳入添加页面或修改页面
		@RequestMapping(value="/addOrUpdatePage/{value}",method={RequestMethod.GET})
		public String addOrUpdatePage(Model model,@PathVariable("value") String value){
			if ("add".equals(value)) {
				System.out.println("进入添加回显页面");
				model.addAttribute("value",value);
				model.addAttribute("role", new Role());
			} else {
				System.out.println("进入修改页面");
				Role role = roleService.queryRoleById(Integer.valueOf(value));
				model.addAttribute("value","update");
				model.addAttribute("role",role);
			}
			return "role/addOrUpdatePage";
		}
	
		
		//权限回显
		@ResponseBody
		@RequestMapping(value="/addEcho",method={RequestMethod.GET})
		public String addEcho(Integer id){
			List<Resources> resourcesList = roleService.queryAllresources(id);
			String rolejson = "";
			try {
				// false表示数组中的属性不需要转成json,如果是true代表只将数组中的属性转成json格式
				rolejson = JsonUtils.beanListToJson(resourcesList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return rolejson;
		}
		
		
		//添加权限
		@ResponseBody
		@RequestMapping(value="/addResources",method={RequestMethod.POST})
		public String addResources(Integer roleId,Long[] resourcesId){
			String value = roleService.addResources(roleId,resourcesId);
			return value;
		}
		
	
}
