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
import com.tzh.entity.Resources;
import com.tzh.entity.Role;
import com.tzh.entity.User;
import com.tzh.service.ResourcesService;
import com.tzh.util.JsonUtils;

@Controller
@RequestMapping("/resources")
public class ResourcesController {
	
	@Resource
	private ResourcesService resourcesService;
	
	@RequestMapping("/resourcesManagement")
	public String  userQuery(){
		System.out.println("欢迎进入权限管理页面");
		return "/resources/resourcesList";
	}
	
	@ResponseBody
	@RequestMapping("/queryMenu")
	public String queryMenu(){
		List<Resources> resourcesList = resourcesService.queryMenu();
		String userjson = "";
		try {
			// false表示数组中的属性不需要转成json,如果是true代表只将数组中的属性转成json格式
			userjson = JsonUtils.beanListToJson(resourcesList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userjson;
	}
	//权限查询
	@ResponseBody
	@RequestMapping(value="/resources",method={RequestMethod.GET})
	public String query(HttpServletRequest request){
		Page<Resources> page = new Page<Resources>();
		String pageNow = request.getParameter("page");
		String pageSize = request.getParameter("pageSize");
		if(pageNow != null && !"".equals(pageNow))
		{
			page.setPage(Integer.valueOf(pageNow));
			page.setPageSize(Integer.valueOf(pageSize));
		}
		page = resourcesService.query(page);
		String resourcesjson = "";
		try {
			// false表示数组中的属性不需要转成json,如果是true代表只将数组中的属性转成json格式
			resourcesjson = JsonUtils.beanToJson(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resourcesjson;
	}
	
	//添加权限
	@RequestMapping(value="/resources",method={RequestMethod.POST})
	public String addResources(Resources resources){
		resourcesService.addResources(resources);
		
		return "forward:/resources/resourcesManagement";
	}
	
	//修改权限
	@RequestMapping(value="/resources",method={RequestMethod.PUT})
	public String updateResources(Resources resources){
		resourcesService.updateResources(resources);
		return "redirect:/resources/resourcesManagement";
	}
	
	//删除权限
	@ResponseBody
	@RequestMapping(value="/resources",method={RequestMethod.DELETE})
	public String deleteResources(Integer id){
		String result = resourcesService.deleteResources(id);
		return result;
	}
	
	//跳入添加页面或修改页面
	@RequestMapping(value="/addOrUpdatePage/{value}",method={RequestMethod.GET})
	public String addOrUpdatePage(Model model,@PathVariable("value") String value){
		if ("add".equals(value)) {
			System.out.println("进入添加回显页面");
			HashMap<String, String> type = new HashMap<String, String>();
			type.put("1", "菜单");
			type.put("0", "按钮");
			model.addAttribute("type",type);
			model.addAttribute("value","add");
			model.addAttribute("resources", new Resources());
		} else {
			System.out.println("进入修改页面");
			HashMap<String, String> type = new HashMap<String, String>();
			type.put("1", "菜单");
			type.put("0", "按钮");
			Resources resources = resourcesService.queryResourcesById(Integer.valueOf(value));
			model.addAttribute("type",type);
			model.addAttribute("value","update");
			model.addAttribute("resources",resources);
		}
		return "resources/addOrUpdatePage";
	}
}
