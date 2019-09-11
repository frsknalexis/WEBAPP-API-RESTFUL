package com.dev.app.ws.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dev.app.ws.entities.AppUser;
import com.dev.app.ws.entities.Role;
import com.dev.app.ws.entities.UserRole;
import com.dev.app.ws.repository.AppUserRepository;
import com.dev.app.ws.repository.RoleRepository;
import com.dev.app.ws.service.AccountService;
import com.dev.app.ws.util.Constants;
import com.dev.app.ws.util.EmailConstructor;

@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier("appUserRepository")
	private AppUserRepository appUserRepository;
	
	@Autowired
	@Qualifier("roleRepository")
	private RoleRepository roleRepository;
	
	@Autowired
	private EmailConstructor emailConstructor;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public AppUser saveUser(String name, String username, String email) {
		
		String password = RandomStringUtils.randomAlphanumeric(10);
		String encryptedPassword = passwordEncoder.encode(password);
		
		AppUser appUser = new AppUser();
		appUser.setPassword(encryptedPassword);
		appUser.setName(name);
		appUser.setUsername(username);
		appUser.setEmail(email);
		Set<UserRole> userRoles = new HashSet<UserRole>();
		userRoles.add(new UserRole(appUser, findUserRoleByName("USER")));
		appUser.setUserRoles(userRoles);
		appUserRepository.save(appUser);
		
		byte[] bytes;
		
		try {
			
			bytes = Files.readAllBytes(Constants.TEMP_USER.toPath());
			String filename = appUser.getId() + ".png";
			Path path = Paths.get(Constants.USER_FOLDER + filename);
			Files.write(path, bytes);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		mailSender.send(emailConstructor.constructNewUserEmail(appUser, password));
		return appUser;
	}

	@Override
	public AppUser findByUsername(String username) {
		return appUserRepository.findByUsername(username);
	}

	@Override
	public AppUser findByEmail(String email) {
		return appUserRepository.findByEmail(email);
	}

	@Override
	public List<AppUser> userList() {
		return appUserRepository.findAll();
	}

	@Override
	public Role findUserRoleByName(String role) {
		return roleRepository.findRoleByName(role);
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}
	
	@Override
	public void updateUserPassword(AppUser appUser, String newPassword) {
		
		String encryptedPassword = passwordEncoder.encode(newPassword);
		appUser.setPassword(encryptedPassword);
		appUserRepository.save(appUser);
		mailSender.send(emailConstructor.constructResetPasswordEmail(appUser, newPassword));
	}

	@Override
	public AppUser updateUser(AppUser appUser, HashMap<String, String> request) {
		
		String name = request.get("name");
		String email = request.get("email");
		String bio = request.get("bio");
		appUser.setName(name);
		appUser.setEmail(email);
		appUser.setBio(bio);
		appUserRepository.save(appUser);
		mailSender.send(emailConstructor.constructUpdateUserProfileEmail(appUser));
		return appUser;
	}

	@Override
	public AppUser findById(Integer id) {
		return appUserRepository.findUserById(id);
	}

	@Override
	public void deleteUser(AppUser appUser) {
		
		appUserRepository.delete(appUser);
	}

	@Override
	public void resetPassword(AppUser appUser) {
		
		String password = RandomStringUtils.randomAlphanumeric(10);
		String encryptedPassword = passwordEncoder.encode(password);
		appUser.setPassword(encryptedPassword);
		
		appUserRepository.save(appUser);
		
		mailSender.send(emailConstructor.constructResetPasswordEmail(appUser, password));
	}

	@Override
	public List<AppUser> getUserListByUsername(String username) {
		return appUserRepository.findByUsernameContaining(username);
	}

	@Override
	public AppUser simpleSave(AppUser appUser) {
		
		appUserRepository.save(appUser);
		mailSender.send(emailConstructor.constructUpdateUserProfileEmail(appUser));
		return appUser;
	}
	
	@Override
	public String saveUserImage(HttpServletRequest request, Integer userImageId) {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartRequest.getFileNames();
		MultipartFile multipartFile = multipartRequest.getFile(iterator.next());
		byte[] bytes;
		
		try {
			
			Files.deleteIfExists(Paths.get(Constants.USER_FOLDER + "/" + userImageId + ".png"));
			bytes = multipartFile.getBytes();
			Path path = Paths.get(Constants.USER_FOLDER + userImageId + ".png");
			Files.write(path, bytes);
			return "User picture saved to server";
		}
		catch(Exception e) {
			return "Error occurred. Photo not saved";
		}
	}
}
