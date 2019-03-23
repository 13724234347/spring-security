package com.us.example.model;

import java.io.Serializable;

/**
 * 树形结构类型实体类
 * @作者 林水桥
 * 2018年8月18日下午9:13:35
 */
public class Types implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**类型ID*/
    private Integer id;
    /**类型名称*/
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
        return "Types [id=" + id + ", name=" + name + "]";
    }
    

}
