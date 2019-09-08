package com.dev.app.ws.entities;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7024591812763315018L;

	private Integer id;
	
	private String username;
	
	private String content;
	
	private Date postedDate;

	public Comment() {
		
	}

	public Comment(Integer id, String username, String content, Date postedDate) {
		super();
		this.id = id;
		this.username = username;
		this.content = content;
		this.postedDate = postedDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
}
