package com.dev.app.ws.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dev.app.ws.entities.AppUser;
import com.dev.app.ws.entities.Post;
import com.dev.app.ws.repository.PostRepository;
import com.dev.app.ws.service.PostService;
import com.dev.app.ws.util.Constants;

@Service("postService")
@Transactional
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
		return postRepository.findPostById(id);
	}

	@Override
	public List<Post> findPostByUsername(String username) {
		return postRepository.findByUsername(username);
	}

	@Override
	public Post deletePost(Post post) {
		
		try {
			
			Files.deleteIfExists(Paths.get(Constants.POST_FOLDER + "/" + post.getName() + ".png"));
			postRepository.deletePostById(post.getId());
			return post;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String savePostImage(HttpServletRequest request, String filename) {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartRequest.getFileNames();
		MultipartFile multipartFile = multipartRequest.getFile(iterator.next());
		
		try {
			
			byte[] bytes = multipartFile.getBytes();
			Path path = Paths.get(Constants.POST_FOLDER + filename + ".png");
			Files.write(path, bytes, StandardOpenOption.CREATE);
		}
		catch(Exception e) {
			return "Error occurred. Photo not saved";
		}
		return "Photo saved successfully";
	}
}
