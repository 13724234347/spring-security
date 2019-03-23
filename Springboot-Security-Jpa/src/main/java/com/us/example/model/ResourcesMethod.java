package com.us.example.model;

import java.io.Serializable;

/**
 * 请求方式实体
 * @作者 林水桥
 * 2018年8月18日下午8:51:14
 */
public class ResourcesMethod implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**请求方式ID*/
    private Integer id;
    /**请求方式*/
    private String name;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ResourcesMethod [id=" + id + ", name=" + name + "]";
    }

}
