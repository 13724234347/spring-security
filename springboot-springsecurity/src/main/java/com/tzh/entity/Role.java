package com.tzh.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

@Entity
public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;//角色id
	
	private String roleName;//角色名称
	
	private String roleKey; //角色key
	
	private String sentence; //判断该用户是否有这个角色
	
	private Set<User> userSet=new HashSet<User>();
	
	private Set<Resources>  resourcesSet=new HashSet<Resources>();//存放该角色已有权限
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//生成一个唯一标识的主键，可以用于主键生成策略
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	@Transient
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", roleKey=" + roleKey + ", sentence=" + sentence + "]";
	}
	public String getRoleKey() {
		return roleKey;
	}
	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "role_user",
				joinColumns = {@JoinColumn(name = "role_id") },
				  inverseJoinColumns = {@JoinColumn(name = "user_id") }
			   )
	public Set<User> getUserSet() {
		return userSet;
	}
	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}
	@ManyToMany(mappedBy="roleSet", fetch = FetchType.EAGER)
	public Set<Resources> getResourcesSet() {
		return resourcesSet;
	}
	public void setResourcesSet(Set<Resources> resourcesSet) {
		this.resourcesSet = resourcesSet;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
