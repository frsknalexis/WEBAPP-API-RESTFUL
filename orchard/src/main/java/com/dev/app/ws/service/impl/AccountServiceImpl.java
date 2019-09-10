package com.dev.app.ws.service.impl;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dev.app.ws.entities.AppUser;
import com.dev.app.ws.entities.Role;
import com.dev.app.ws.repository.AppUserRepository;
import com.dev.app.ws.repository.RoleRepository;
import com.dev.app.ws.service.AccountService;
import com.dev.app.ws.util.EmailConstructor;

@Service("accountService")
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
	public void saveUser(AppUser appUser) {
		
		String password = RandomStringUtils.randomAlphanumeric(10);
		String encryptedPassword = passwordEncoder.encode(password);
		appUser.setPassword(encryptedPassword);
		appUserRepository.save(appUser);
		
		mailSender.send(emailConstructor.constructNewUserEmail(appUser, password));
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
	public void updateUser(AppUser appUser) {
		
		String password = appUser.getPassword();
		String encryptedPassword = passwordEncoder.encode(password);
		appUser.setPassword(encryptedPassword);
		
		appUserRepository.save(appUser);
		
		mailSender.send(emailConstructor.constructUpdateUserProfileEmail(appUser));
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
}
