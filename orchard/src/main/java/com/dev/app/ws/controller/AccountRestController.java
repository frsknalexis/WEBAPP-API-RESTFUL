package com.dev.app.ws.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.app.ws.entities.AppUser;
import com.dev.app.ws.service.AccountService;

@RestController
@RequestMapping("/user")
public class AccountRestController {

	private Integer userImageId;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier("accountService")
	private AccountService accountService;
	
	@GetMapping("/list")
	public ResponseEntity<?> getUsersList() {
		
		List<AppUser> usersList = accountService.userList();
		if(usersList.isEmpty()) {
			return new ResponseEntity<>("No Users Found", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(usersList, HttpStatus.OK);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<?> getUserInfo(@PathVariable(value = "username") String username) {
		
		AppUser user = accountService.findByUsername(username);
		if(user == null) {
			return new ResponseEntity<>("Not User Found", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping("/findByUsername/{username}")
	public ResponseEntity<?> getUsersListByUsername(@PathVariable(value = "username") String username) {
		
		List<AppUser> usersList = accountService.getUserListByUsername(username);
		if(usersList.isEmpty()) {
			return new ResponseEntity<>("Not Users Found", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(usersList, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody HashMap<String, String> request) {
		
		String username = request.get("username");
		if(accountService.findByUsername(username) != null) {
			return new ResponseEntity<>("Username Exists", HttpStatus.CONFLICT);
		}
		
		String email = request.get("email");
		if(accountService.findByEmail(email) != null) {
			return new ResponseEntity<>("Email Exists", HttpStatus.CONFLICT);
		}
		
		String name = request.get("name");
		
		try {
			
			AppUser appUser = accountService.saveUser(name, username, email);
			return new ResponseEntity<>(appUser, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("An Error Occurred", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> updateProfile(@RequestBody HashMap<String, String> request) {
		
		String id = request.get("id");
		AppUser user = accountService.findById(Integer.valueOf(id));
		if(user == null) {
			return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
		}
		
		try {
			
			accountService.updateUser(user, request);
			userImageId = user.getId();
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("An Error Occurred", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/photo/upload")
	public ResponseEntity<String> fileUpload(HttpServletRequest request) {
		
		try {
			
			accountService.saveUserImage(request, userImageId);
			return new ResponseEntity<String>("User Picture Saved!", HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("User Picture Not Saved", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody HashMap<String, String> request) {
		
		String username = request.get("username");
		AppUser appUser = accountService.findByUsername(username);
		if(appUser == null) {
			return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
		}
		
		String currentPassword = request.get("currentPassword");
		String newPassword = request.get("newPassword");
		String confirmPassword = request.get("confirmPassword");
		if(!newPassword.equals(confirmPassword)) {
			return new ResponseEntity<String>("Password Not Matched", HttpStatus.BAD_REQUEST);
		}
		
		String userPassword = appUser.getPassword();
		
		try {
			
			if(newPassword != null && !newPassword.isEmpty() && !StringUtils.isEmpty(newPassword)) {
				
				if(passwordEncoder.matches(currentPassword, userPassword)) {
					accountService.updateUserPassword(appUser, newPassword);
				}
			}
			else {
				return new ResponseEntity<String>("Incorrect Current Password", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<String>("Password Changed Successfully", HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("An Error Occurred", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/resetPassword/{email}")
	public ResponseEntity<String> resetPassword(@PathVariable(value = "email") String email) {
		
		AppUser appUser = accountService.findByEmail(email);
		if(appUser == null) {
			return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
		}
		accountService.resetPassword(appUser);
		return new ResponseEntity<String>("Email Send", HttpStatus.OK);
	}
}
