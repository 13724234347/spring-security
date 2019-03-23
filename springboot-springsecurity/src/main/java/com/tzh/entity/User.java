package com.tzh.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;//用户id
	
	private String userAccount;//用户账号,用于登陆
	
	private String userPassword;//用户密码,用于登陆
	
	private Integer enable; //是否启用该账号 1代表启动
	
	private Set<Role> role=new HashSet<Role>();//存放用户角色
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//生成一个唯一标识的主键，可以用于主键生成策略
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "username")
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	@Column(name = "userpassword")
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	@ManyToMany(mappedBy="userSet",fetch = FetchType.EAGER)
	public Set<Role> getRole() {
		return role;
	}
	public void setRole(Set<Role> role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userAccount=" + userAccount + ", userPassword=" + userPassword + ", enable="
				+ enable + "]";
	}
}
