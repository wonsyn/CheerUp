package com.web.curation.model.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class CommentDto {
	private int commentId;
	private int feedId;
	private String commentContent;
	private String id;
	private String commentDate;
	private int countLike;
	
	
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getFeedId() {
		return feedId;
	}
	public void setFeedId(int feedId) {
		this.feedId = feedId;
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
	public int getCountLike() {
		return countLike;
	}
	public void setCountLike(int countLike) {
		this.countLike = countLike;
	}
	@Override
	public String toString() {
		return "CommentDto [commentId=" + commentId + ", feedId=" + feedId + ", commentContent=" + commentContent
				+ ", id=" + id + ", commentDate=" + commentDate + ", countLike=" + countLike + "]";
	}
}
