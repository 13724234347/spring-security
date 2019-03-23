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
 * 用户实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="T_USER")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**用户ID*/
	private Integer id;
	/**用户名*/
	private String username;
	/**用户密码*/
	private String password;
	/**用户状态*/
	private Integer enable;
	/**添加时间戳*/
    private Date add_time;
    /**修改时间戳*/
    private Date up_time;
	/**用户角色*/
	private Set<Role> roles = new HashSet<>();
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@NotEmpty
    @Column(name="USERNAME", unique=true, nullable=false)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@NotEmpty
    @Column(name="PASSWORD", nullable=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    @Column(name="ENABLE", nullable=false)
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
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
    @JoinTable(name = "T_USER_ROLE", 
             joinColumns = { @JoinColumn(name = "USER_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	@Override
    public String toString()
    {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", enable=" + enable
                + ", add_time=" + add_time + ", up_time=" + up_time + ", roles=" + roles + "]";
    }
	
	
}
