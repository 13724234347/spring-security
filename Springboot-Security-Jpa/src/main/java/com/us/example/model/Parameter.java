package com.us.example.model;

import java.io.Serializable;
/***
 * 用户参数实体
 * @作者 林水桥
 * 2018年8月6日下午4:58:42
 */
public class Parameter implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**用户ID*/
    private Integer uId;
    /**用户名*/
    private String uName;
    /**用户状态 0为关闭,1为启用*/
    private Integer uEnable;

    public Integer getuId()
    {
        return uId;
    }

    public void setuId(Integer uId)
    {
        this.uId = uId;
    }

    public String getuName()
    {
        return uName;
    }

    public void setuName(String uName)
    {
        this.uName = uName;
    }

    public Integer getuEnable()
    {
        return uEnable;
    }

    public void setuEnable(Integer uEnable)
    {
        this.uEnable = uEnable;
    }

    @Override
    public String toString()
    {
        return "Parameter [uId=" + uId + ", uName=" + uName + ", uEnable=" + uEnable + "]";
    }
    
    
}
