package com.tzh.tag;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tzh.entity.Resources;
import com.tzh.service.ResourcesService;

/**
 * 自定义标签  用于前台判断按钮权限 
 * ALL权限指的是---拥有相应的所有权限
 * @author A
 *
 */
@Component
public class AuthorizeTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;  
	
	@Autowired
	private ResourcesService resourcesService;
	
	private static AuthorizeTag  serverHandler ;
    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {  // 初始化调用获取Service实例
        serverHandler = this;  
        serverHandler.resourcesService = this.resourcesService;        
        // 初使化时将已静态化的testService实例化
    }
    /**请求路径*/
	private String buttonUrl;  
	/**请求方式*/
	private String buttonMethod;
	
    public String getButtonUrl() {
		return buttonUrl;
	}


	public void setButtonUrl(String buttonUrl) {
		this.buttonUrl = buttonUrl;
	}

	public String getButtonMethod()
    {
        return buttonMethod;
    }


    public void setButtonMethod(String buttonMethod)
    {
        this.buttonMethod = buttonMethod;
    }


    @SuppressWarnings("static-access")
	@Override  
    public int doStartTag() throws JspException {  
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
				.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		/**获取当前登录名*/
		String name = securityContextImpl.getAuthentication().getName();
		
		System.out.println(name);
		/**如果数据库里有该链接，并且该用户的权限拥有该链接，则显示 。如果数据库没有该链接则不显示
		 * 查询所有权限
		 */
		List<Resources> queryAll = serverHandler.resourcesService.queryAllResource();
		boolean flag = true;
		/**根据字符串提取最后一个"/"之前的所有字符串*/
		buttonUrl = StringUtils.substringBeforeLast(buttonUrl, "/");
		/**根据","分隔获取多个请求方式(可单个)*/
        String date[]=buttonMethod.split(",");
        if (1 < date.length) {
            for (int i = 0;i < date.length;i++) {
                for (Resources resources : queryAll) {
                    /**判断，如果有相应的权限对应，则会进入验证*/
                    if(StringUtils.substringBeforeLast(resources.getResUrl(), "/").equals(buttonUrl) && (resources.getMethod().equalsIgnoreCase(date[i]) || resources.getMethod().equalsIgnoreCase("ALL")))
                        flag = false;
                }
            }
        } else {
            for (Resources resources : queryAll) {
                /**判断，如果有相应的权限对应，则会进入验证*/
                if(StringUtils.substringBeforeLast(resources.getResUrl(), "/").equals(buttonUrl) && (resources.getMethod().equalsIgnoreCase(buttonMethod) || resources.getMethod().equalsIgnoreCase("ALL")))
                    flag = false;
            }
        }
        
        if(flag) //数据库中没有该链接，直接显示
        	return EVAL_BODY_INCLUDE;
        else{
        	Resources resources = new Resources();
        	resources.setUserName(name);
        	/**url适用于模糊查询*/
        	buttonUrl = buttonUrl + "/%";
        	resources.setResUrl(buttonUrl);
        	if (1 < date.length) {
        	    for (int i = 0;i < date.length;i++) {
        	        /**获取每个请求方式*/
        	        resources.setMethod(date[i]);
        	        List<Resources> resourcesList = serverHandler.resourcesService.queryResourc(resources);//loadMenu(resources);
        	        if(resourcesList.size()>0)  return EVAL_BODY_INCLUDE;//数据库中有该链接，并且该用户拥有该角色，显示
        	    }
        	} else {
        	    resources.setMethod(buttonMethod);
        	    /**根据用户名+url+method查询用户是否有权限显示*/
        	    List<Resources> resourcesList = serverHandler.resourcesService.queryResourc(resources);//loadMenu(resources);
        	    if(resourcesList.size()>0)	return EVAL_BODY_INCLUDE;//数据库中有该链接，并且该用户拥有该角色，显示
        	}
        }
		return this.SKIP_BODY;  //不显示
    }  
}
