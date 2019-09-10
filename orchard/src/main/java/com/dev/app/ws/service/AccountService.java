package com.dev.app.ws.service;

import java.util.List;

import com.dev.app.ws.entities.AppUser;
import com.dev.app.ws.entities.Role;

public interface AccountService {

	void saveUser(AppUser appUser);
	
	AppUser findByUsername(String username);
	
	AppUser findByEmail(String email);
	
	List<AppUser> userList();
	
	Role findUserRoleByName(String role);
	
	Role saveRole(Role role);
	
	void updateUser(AppUser appUser);
	
	AppUser findById(Integer id);
	
	void deleteUser(AppUser appUser);
	
	void resetPassword(AppUser appUser);
	
	List<AppUser> getUserListByUsername(String username);
	
	AppUser simpleSave(AppUser appUser);
}
