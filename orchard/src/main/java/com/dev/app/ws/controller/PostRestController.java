package com.dev.app.ws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
