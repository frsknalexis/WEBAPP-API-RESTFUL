package com.dev.app.ws.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_posts", schema = "public")
public class Post implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5679195250383684051L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "post_id", nullable = false, unique = true, updatable = false)
	private Integer id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "caption", columnDefinition = "text")
	private String caption;
	
	@Column(name = "location", nullable = false)
	private String location;
	
	@Column(name = "likes", nullable = false)
	private Integer likes;
	
	@Column(name = "posted_date")
	private Date postedDate;
	
	@Column(name = "user_image_id", nullable = false)
	private Integer userImageId;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "post_id")
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
