package com.dev.app.ws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.app.ws.entities.AppUser;

@Repository("appUserRepository")
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

	AppUser findByUsername(String username);
	
	AppUser findByEmail(String email);
	
	@Query("SELECT appUser FROM AppUser appUser WHERE appUser.id=:id")
	AppUser findUserById(@Param(value = "id")Integer id);
	
	List<AppUser> findByUsernameContaining(String username);
}
 