package com.dev.app.ws.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.app.ws.entities.Comment;
import com.dev.app.ws.repository.CommentRepository;
import com.dev.app.ws.service.CommentService;

@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	@Qualifier("commentRepository")
	private CommentRepository commentRepository;
	
	@Override
	public void saveComment(Comment comment) {
		
		commentRepository.save(comment);
	}
}
