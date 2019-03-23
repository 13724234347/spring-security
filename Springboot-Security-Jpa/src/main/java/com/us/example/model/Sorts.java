package com.us.example.model;

import java.io.Serializable;

/**
 * 排序实体类
 * @作者 林水桥
 * 2018年8月18日下午9:27:01
 */
public class Sorts implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**排序ID*/
    private Integer id;
    /**名称*/
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
        return "Sorts [id=" + id + ", name=" + name + "]";
    }
    
}
