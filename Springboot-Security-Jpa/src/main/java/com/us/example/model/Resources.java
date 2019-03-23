package com.us.example.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;


/**
 * 资源实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="T_RESOURCES")
public class Resources implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7749594385945332343L;
	/**资源ID*/
	private Integer id;
	/**资源名称*/
	private String name;
	/**父资源*/
	private String parentId;
	/**资源链接*/
	private String resUrl;
	/**资源请求方式*/
	private String resMethod; 
	/**资源key*/
	private String resKey;
	/**资源类型   1:菜单    2：按钮*/
	private Integer type;
	/**排序*/
	private Integer sort;
	/**添加时间戳*/
    private Date add_time;
    /**修改时间戳*/
    private Date up_time;
	
	private String username;//只用于临时存储数据
	@Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@NotEmpty
    @Column(name="NAME", nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@NotEmpty
    @Column(name="PARENTID", nullable=false)
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	@NotEmpty
    @Column(name="RESURL", nullable=false)
	public String getResUrl() {
		return resUrl;
	}
	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}
	@NotEmpty
    @Column(name="RESMETHOD", nullable=false)
	public String getResMethod()
    {
        return resMethod;
    }
    public void setResMethod(String resMethod)
    {
        this.resMethod = resMethod;
    }
    @Column(name="TYPE", nullable=false)
    public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
    @Column(name="SORT", nullable=false)
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Transient
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getResKey() {
		return resKey;
	}
	public void setResKey(String resKey) {
		this.resKey = resKey;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getAdd_time()
    {
        return add_time;
    }
    public void setAdd_time(Date add_time)
    {
        this.add_time = add_time;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUp_time()
    {
        return up_time;
    }
    public void setUp_time(Date up_time)
    {
        this.up_time = up_time;
    }
    @Override
    public String toString()
    {
        return "Resources [id=" + id + ", name=" + name + ", parentId=" + parentId + ", resUrl=" + resUrl
                + ", resMethod=" + resMethod + ", resKey=" + resKey + ", type=" + type + ", sort=" + sort
                + ", add_time=" + add_time + ", up_time=" + up_time + ", username=" + username
                + "]";
    }
	 
	
	
}
