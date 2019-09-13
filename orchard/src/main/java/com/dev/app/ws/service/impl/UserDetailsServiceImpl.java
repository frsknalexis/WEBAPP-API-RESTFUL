package com.dev.app.ws.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dev.app.ws.entities.AppUser;
import com.dev.app.ws.entities.UserRole;
import com.dev.app.ws.service.AccountService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	@Qualifier("accountService")
	private AccountService accountService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		AppUser appUser = accountService.findByUsername(username);
		
		if(appUser == null) {
			throw new UsernameNotFoundException("Username " + username + " was not found");
		}
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		Set<UserRole> userRoles = appUser.getUserRoles();
		userRoles.forEach(userRole -> {
			authorities.add(new SimpleGrantedAuthority(userRole.toString()));
		});
		return new User(appUser.getUsername(), appUser.getPassword(), authorities);
	}

}
