package com.dev.app.ws.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dev.app.ws.entities.AppUser;
import com.dev.app.ws.entities.Post;

public interface PostService {

	Post savePost(AppUser appUser, HashMap<String, String> request, String postImageName);
	
	List<Post> postList();
	
	Post getPostById(Integer id);
	
	List<Post> findPostByUsername(String username);
	
	Post deletePost(Post post);
	
	String savePostImage(HttpServletRequest request, String filename);
}
