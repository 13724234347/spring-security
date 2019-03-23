package com.us.example.model;

import java.io.Serializable;

/**
 * 资源参数条件实体
 * @作者 林水桥
 * 2018年8月6日下午4:57:52
 */
public class ResourceParameter implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    /**资源名称*/
    private String rName;
    /**请求方式*/
    private String rMethod;

    public String getrName()
    {
        return rName;
    }

    public void setrName(String rName)
    {
        this.rName = rName;
    }

    public String getrMethod()
    {
        return rMethod;
    }

    public void setrMethod(String rMethod)
    {
        this.rMethod = rMethod;
    }

    @Override
    public String toString()
    {
        return "ResourceParameter [rName=" + rName + ", rMethod=" + rMethod + "]";
    }
    
    
}
