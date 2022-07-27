package com.web.curation.model.dto;

import lombok.Data;

@Data
public class CommentDto {
	private int commentId;
	private String commentUrl;
	private String commentContent;
	private String id;
	private String commentDate;
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getCommentUrl() {
		return commentUrl;
	}
	public void setCommentUrl(String commentUrl) {
		this.commentUrl = commentUrl;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	@Override
	public String toString() {
		return "CommentDto [commentId=" + commentId + ", commentUrl=" + commentUrl + ", commentContent="
				+ commentContent + ", id=" + id + ", commentDate=" + commentDate + "]";
	}
	
	
	
	

}
