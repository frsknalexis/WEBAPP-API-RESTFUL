package com.dev.app.ws.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.dev.app.ws.entities.AppUser;
import com.dev.app.ws.entities.Post;
import com.dev.app.ws.repository.PostRepository;
import com.dev.app.ws.service.PostService;

@Service("postService")
public class PostServiceImpl implements PostService {

	@Autowired
	@Qualifier("postRepository")
	private PostRepository postRepository;
	
	@Override
	public Post savePost(AppUser appUser, HashMap<String, String> request, String postImageName) {
		
		String caption = request.get("caption");
		String location = request.get("location");
		
		Post post = new Post();
		post.setCaption(caption);
		post.setLocation(location);
		post.setUsername(appUser.getUsername());
		post.setPostedDate(new Date());
		post.setUserImageId(appUser.getId());
		appUser.setPost(post);
		postRepository.save(post);
		return post;
	}

	@Override
	public List<Post> postList() {
		return postRepository.findAll();
	}

	@Override
	public Post getPostById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> findPostByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post deletePost(Post post) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String savePostImage(HttpServletRequest request, String filename) {
		// TODO Auto-generated method stub
		return null;
	}

}
