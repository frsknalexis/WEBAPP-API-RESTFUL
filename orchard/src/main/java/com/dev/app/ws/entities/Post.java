package com.dev.app.ws.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Post implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5679195250383684051L;

	private Integer id;
	
	private String name;
	
	private String caption;
	
	private String location;
	
	private Integer likes;
	
	private Date postedDate;
	
	private Integer userImageId;
	
	private List<Comment> commentList;

	public Post() {
		
	}

	public Post(Integer id, String name, String caption, String location, Integer likes, Date postedDate,
			Integer userImageId, List<Comment> commentList) {
		super();
		this.id = id;
		this.name = name;
		this.caption = caption;
		this.location = location;
		this.likes = likes;
		this.postedDate = postedDate;
		this.userImageId = userImageId;
		this.commentList = commentList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public Integer getUserImageId() {
		return userImageId;
	}

	public void setUserImageId(Integer userImageId) {
		this.userImageId = userImageId;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
}
