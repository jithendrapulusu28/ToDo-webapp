package com.entity;

import java.sql.Date;

public class TodoDetails {

	private int id;
	private String title;
	private String description;
	private Date targetDate;
	private String status;
	private String userEmail;

	public TodoDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TodoDetails(int id, String title, String description, Date targetDate, String status) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.targetDate = targetDate;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String UserEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public String toString() {
		return "TodoDetails [id=" + id + ", title=" + title + ", description=" + description + ", targetDate="
				+ targetDate + ", status=" + status + ", userEmail=" + userEmail + "]";
	}

}
