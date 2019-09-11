package com.dev.app.ws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.app.ws.entities.Post;

@Repository("postRepository")
public interface PostRepository extends JpaRepository<Post, Integer> {

	@Query("SELECT p FROM Post p ORDER BY p.postedDate DESC")
	List<Post> findAll();
	
	@Query("SELECT p FROM Post p WHERE p.username=:username ORDER BY p.postedDate DESC")
	List<Post> findByUsername(@Param(value = "username") String username);
	
	@Query("SELECT p FROM Post p WHERE p.id=:x")
	Post findPostById(@Param(value = "x") Integer id);
	
	@Modifying
	@Query("DELETE Post WHERE id=:x")
	void deletePostById(@Param(value = "x") Integer id);
}
