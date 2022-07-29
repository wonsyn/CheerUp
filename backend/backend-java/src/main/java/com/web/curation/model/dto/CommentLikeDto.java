package com.web.curation.model.dto;

import lombok.Data;

@Data
public class CommentLikeDto {

	private int likeId;
	private int userId;
	private int commentId;
	public int getLikeId() {
		return likeId;
	}
	public void setLikeId(int likeId) {
		this.likeId = likeId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	@Override
	public String toString() {
		return "LikeDto [likeId=" + likeId + ", userId=" + userId + ", commentId=" + commentId + "]";
	}
	
	
	
}
