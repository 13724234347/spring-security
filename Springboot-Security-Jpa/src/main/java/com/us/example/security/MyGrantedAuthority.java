package com.us.example.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by yangyibo on 17/2/15.
 */
public class MyGrantedAuthority implements GrantedAuthority {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String url;
    private String method;

    public String getPermissionUrl() {
        return url;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.url = permissionUrl;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public MyGrantedAuthority(String url, String method) {
        this.url = url;
        this.method = method;
    }

    @Override
    public String getAuthority() {
        return this.url + ";" + this.method;
    }
}
