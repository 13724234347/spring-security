package com.us.example.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

/**
 * 角色实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="T_ROLE")
public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2322564038632517975L;
	/**角色ID*/
	private Integer id;
	/**角色KEY*/
	private String roleKey; //角色key
	/**角色名称*/
	private String roleDesc;//角色名称
	/**添加时间戳*/
    private Date add_time;
    /**修改时间戳*/
    private Date up_time;
	/**角色权限*/
	private Set<Resources> resources  = new HashSet<>();
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@NotEmpty
    @Column(name="ROLEKEY", nullable=false)
	public String getRoleKey() {
		return roleKey;
	}
	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}
	@NotEmpty
    @Column(name="ROLEDESC", nullable=false)
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "T_ROLE_RESOURCES", 
             joinColumns = { @JoinColumn(name = "ROLE_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "RESOURCES_ID") })
	public Set<Resources> getResources() {
		return resources;
	}
	public void setResources(Set<Resources> resources) {
		this.resources = resources;
	}
	
	@Override
    public String toString()
    {
        return "Role [id=" + id + ", roleKey=" + roleKey + ", roleDesc=" + roleDesc + ", add_time=" + add_time
                + ", up_time=" + up_time + ", resources=" + resources + "]";
    }
	 
	 
	
	
}
