package com.dev.app.ws.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2392960351408513254L;

	private Integer roleId;
	
	private String name;
	
	private Set<UserRole> userRoles = new HashSet<>();

	public Role() {
		
	}

	public Role(Integer roleId, String name, Set<UserRole> userRoles) {
		super();
		this.roleId = roleId;
		this.name = name;
		this.userRoles = userRoles;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
}
