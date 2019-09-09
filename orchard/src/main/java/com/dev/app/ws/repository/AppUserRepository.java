package com.dev.app.ws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.app.ws.entities.AppUser;

@Repository("appUserRepository")
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

	AppUser findByUsername(String username);
	
	AppUser findByEmail(String email);
	
	AppUser findUserById(Integer id);
	
	List<AppUser> findByUsernameContaining(String username);
}
