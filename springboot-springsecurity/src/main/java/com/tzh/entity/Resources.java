package com.tzh.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.tzh.entity.Role;
/**
 * 资源实体类
 * @author Administrator
 *
 */
@Entity
public class Resources implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;

	private String name;//资源名称
	
	private String resUrl;//资源链接
	
	private String resKey;//资源key
	
	private Integer type;//资源类型   1:菜单    0：按钮
	
	private String sentence; //判断该角色是否有这个权限
	
	private String userName; //临时存储用户名
	
	@Transient
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Transient
	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	private Integer sort;//排序
	
	private String method; //请求方式
	
	private Set<Role>  roleSet=new HashSet<Role>();//存放该角色已有权限

	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//生成一个唯一标识的主键，可以用于主键生成策略
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public String getResKey() {
		return resKey;
	}

	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)//生成一个唯一标识的主键，可以用于主键生成策略
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "resources_role",//创建一个关联两个表的关联表,表名teacher_student ,用这两个id把他们关联起来
				joinColumns = {@JoinColumn(name = "resources_id") },//这里是teacher表中的id 
				  inverseJoinColumns = {@JoinColumn(name = "role_id") }//这里是student表中的id
			   )
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	@Override
	public String toString() {
		return "Resources [id=" + id + ", name=" + name + ", resUrl=" + resUrl + ", resKey=" + resKey + ", type=" + type
				+ ", sentence=" + sentence + ", sort=" + sort + ", method=" + method + "]";
	}
	
}
