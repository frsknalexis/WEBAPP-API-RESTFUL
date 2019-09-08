package com.dev.app.ws.entities;

import java.io.Serializable;

public class UserRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1511230489801974938L;

	private long userRoleId;
	
	private AppUser appUser;
	
	private Role role;

	public UserRole() {
		
	}

	public UserRole(long userRoleId, AppUser appUser, Role role) {
		super();
		this.userRoleId = userRoleId;
		this.appUser = appUser;
		this.role = role;
	}

	public long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
