package com.tzh.security;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tzh.entity.Resources;
import com.tzh.entity.User;
import com.tzh.service.ResourcesService;
import com.tzh.service.UserService;

@Service
public class CustomUserService implements UserDetailsService { //自定义UserDetailsService 接口

	@Autowired
	private UserService userService;
	
	@Autowired
	private ResourcesService resourcesService;

    public UserDetails loadUserByUsername(String username){
    	User user = userService.findUserByName(username);
        if (user != null) {
        	List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        	List<Resources> list = resourcesService.queryResourceToUserName(username);
            for(Resources resources : list) {
                if (resources != null && resources.getName() != null) {
                    GrantedAuthority grantedAuthority = new MyGrantedAuthority(resources.getResUrl(), resources.getMethod());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new org.springframework.security.core.userdetails.User(user.getUserAccount(), user.getUserPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException(username + "do not exist!");
        }
    }
}