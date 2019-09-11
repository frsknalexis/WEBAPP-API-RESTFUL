package com.dev.app.ws.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dev.app.ws.entities.AppUser;
import com.dev.app.ws.entities.Role;

public interface AccountService {

	AppUser saveUser(String name, String username, String email);
	
	AppUser findByUsername(String username);
	
	AppUser findByEmail(String email);
	
	List<AppUser> userList();
	
	Role findUserRoleByName(String role);
	
	Role saveRole(Role role);
	
	void updateUserPassword(AppUser appUser, String newPassword);
	
	AppUser updateUser(AppUser appUser, HashMap<String, String> request);
	
	AppUser findById(Integer id);
	
	void deleteUser(AppUser appUser);
	
	void resetPassword(AppUser appUser);
	
	List<AppUser> getUserListByUsername(String username);
	
	AppUser simpleSave(AppUser appUser);
	
	String saveUserImage(HttpServletRequest request, Integer userImageId);
}
