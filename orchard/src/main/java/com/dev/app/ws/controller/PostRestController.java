package com.dev.app.ws.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.app.ws.entities.AppUser;
import com.dev.app.ws.entities.Comment;
import com.dev.app.ws.entities.Post;
import com.dev.app.ws.service.AccountService;
import com.dev.app.ws.service.CommentService;
import com.dev.app.ws.service.PostService;

@RestController
@RequestMapping("/post")
public class PostRestController {

	private String postImageName;
	
	@Autowired
	@Qualifier("postService")
	private PostService postService;
	
	@Autowired
	@Qualifier("accountService")
	private AccountService accountService;
	
	@Autowired
	@Qualifier("commentService")
	private CommentService commentService;
	
	@GetMapping("/list")
	public List<Post> getPostList() {
		return postService.postList();
	}
	
	@GetMapping("/getPostById/{postId}")
	public ResponseEntity<?> getOnePostById(@PathVariable(value = "postId") Integer postId) {
		
		Post post = postService.getPostById(postId);
		
		if(post == null) {
			return new ResponseEntity<>("Post Not Found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(post, HttpStatus.OK);
	}
	
	@GetMapping("/getPostByUsername/{username}")
	public ResponseEntity<?> getPostByUsername(@PathVariable("username") String username) {
		
		AppUser appUser = accountService.findByUsername(username);
		
		if(appUser == null) {
			return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
		}
		
		try {
			
			List<Post> posts = postService.findPostByUsername(username);
			return new ResponseEntity<>(posts, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("An Error Occurred", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> savePost(@RequestBody HashMap<String, String> request) {
		
		String username = request.get("username");
		AppUser appUser = accountService.findByUsername(username);
		
		if(appUser == null) {
			return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
		}
		
		postImageName = RandomStringUtils.randomAlphabetic(10);
		
		try {
			
			Post post = postService.savePost(appUser, request, postImageName);
			return new ResponseEntity<>(post, HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity<>("An Error Occurred", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deletePost(@PathVariable(value = "id") Integer id) {
		
		Post post = postService.getPostById(id);
		if(post == null) {
			return new ResponseEntity<>("Post Not Found", HttpStatus.NOT_FOUND);
		}
		
		try {
			
			Post postDelete = postService.deletePost(post);
			return new ResponseEntity<>(postDelete, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("An Error Occurred", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/like")
	public ResponseEntity<String> likePost(@RequestBody HashMap<String, String> request) {
		
		String postId = request.get("postId");
		Post post = postService.getPostById(Integer.valueOf(postId));
		
		if(post == null) {
			return new ResponseEntity<String>("Post Not Found", HttpStatus.NOT_FOUND);
		}
		
		String username = request.get("username");
		AppUser appUser = accountService.findByUsername(username);
		
		if(appUser == null) {
			return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
		}
		
		try {
			
			post.setLikes(1);
			appUser.setLikedPost(post);
			accountService.simpleSave(appUser);
			return new ResponseEntity<String>("Post was Liked", HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Can't Like the Post", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/unLike")
	public ResponseEntity<String> unLikePost(@RequestBody HashMap<String, String> request) {
		
		String postId = request.get("postId");
		Post post = postService.getPostById(Integer.valueOf(postId));
		
		if(post == null) {
			return new ResponseEntity<String>("Post Not Found", HttpStatus.NOT_FOUND);
		}
		
		String username = request.get("username");
		AppUser appUser = accountService.findByUsername(username);
		
		if(appUser == null) {
			return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
		}
		
		try {
			
			post.setLikes(-1);
			appUser.getLikedPost().remove(post);
			accountService.simpleSave(appUser);
			return new ResponseEntity<String>("Post was Unliked", HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Can't Unlike the Post", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/comment/add")
	public ResponseEntity<?> addComment(@RequestBody HashMap<String, String> request) {
		
		String postId = request.get("postId");
		Post post = postService.getPostById(Integer.valueOf(postId));
		
		if(post == null) {
			return new ResponseEntity<>("Post Not Found", HttpStatus.NOT_FOUND);
		}
		
		String username = request.get("username");
		AppUser appUser = accountService.findByUsername(username);
		
		if(appUser == null) {
			return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
		}
		
		String content = request.get("content");
		
		try {
			
			Comment comment = new Comment();
			comment.setContent(content);
			comment.setUsername(username);
			comment.setPostedDate(new Date());
			post.setCommentList(comment);
			commentService.saveComment(comment);
			return new ResponseEntity<>(comment, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("Comment Not Added", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/photo/upload")
	public ResponseEntity<String> fileUpload(HttpServletRequest request) {
		
		try {
			
			postService.savePostImage(request, postImageName);
			return new ResponseEntity<String>("Picture Saved !", HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Picture Not Saved !", HttpStatus.BAD_REQUEST);
		}
	}
}
