package com.us.example.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.us.example.dao.ResourcesDao;
import com.us.example.dao.UserDao;
import com.us.example.model.Resources;
import com.us.example.model.User;

@Service("customUserService")
public class CustomUserService implements UserDetailsService
{

    @SuppressWarnings("unused")
    @Autowired  
    private HttpSession session; 
    @SuppressWarnings("unused")
    @Autowired 
    private HttpServletRequest request;
    
    @Autowired
    UserDao userDao;
    @Autowired
    ResourcesDao ResourcesDao;
    
    private UserCache userCache = new NullUserCache();   

    /**
     * 加载权限
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {

HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        User user = userDao.findByUsername(username);
        if (user != null) {
            List<Resources> permissions = ResourcesDao.findAllById(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Resources permission : permissions) {
                if (permission != null && permission.getName() != null) {

                    GrantedAuthority grantedAuthority = new MyGrantedAuthority(permission.getResUrl(), permission.getResMethod());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            request.getSession().setAttribute("names",username);//将用户名保存在整个会话期间  
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }

    /**
     * 修改密码
     * void
     * 2018年8月21日上午11:51:42
     */
    @Transactional  
    public void changePassword(String username,String newpassword,String oldpassword)  
    {  
        Authentication currentuser=SecurityContextHolder.getContext().getAuthentication();  
          
        if(currentuser==null)  
        {  
            // This would indicate bad coding somewhere  
            throw new AccessDeniedException("Can't change password as no Authentication object found in context " +  
                    "for current user.");  
        }  
          
          
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();  
          
        String username2=((UserDetails)principal).getUsername();  
          
        User users = userDao.findByUsername(username2);  
          
        if(users.getPassword().equals(oldpassword))  
        {  
            users.setPassword(newpassword);  
            
            users.setUp_time(new Date());
            
            userDao.updatePass(users.getId(), users.getPassword(), users.getUp_time());
              
            SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(currentuser, newpassword));  
              
            userCache.removeUserFromCache(username);  
              
        }  
      
          
    }  
    
    /**
     * 重置密码
     * void
     * 2018年8月21日下午12:31:23
     */
    @Transactional  
    public void changePassword1(String username,String newpassword)  
    {  
        Authentication currentuser=SecurityContextHolder.getContext().getAuthentication();  
          
        if(currentuser==null)  
        {  
            // This would indicate bad coding somewhere  
            throw new AccessDeniedException("Can't change password as no Authentication object found in context " +  
                    "for current user.");  
        }  
          
          
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();  
//          
//        String username2=((UserDetails)principal).getUsername();  
          
        User users = userDao.findByUsername(username);  
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        users.setPassword(passwordEncoder.encode(newpassword));  
        
        users.setUp_time(new Date());
        
        userDao.updatePass(users.getId(), users.getPassword(), users.getUp_time());
          
        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(currentuser, newpassword));  
          
        userCache.removeUserFromCache(username);  
          
      
          
    }  
    
    /**
     * 获取登录用户名
     * Authentication
     * 2018年8月21日上午11:35:24
     */
    public Authentication createNewAuthentication(Authentication currentAuth, String newPassword)  
    {  
        UserDetails user = loadUserByUsername(currentAuth.getName());  
  
        UsernamePasswordAuthenticationToken newAuthentication =  
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());  
        newAuthentication.setDetails(currentAuth.getDetails());  
  
        return newAuthentication;  
    }  
    
}
