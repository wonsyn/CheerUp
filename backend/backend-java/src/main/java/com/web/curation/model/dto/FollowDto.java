package com.web.curation.model.dto;

import lombok.Data;

@Data
public class FollowDto {

	private int followId;
	private int userId;
	private int followUserId;
	
	public FollowDto() {
		super();
	}
	
	public FollowDto(int userId, int followUserId) {
		super();
		this.userId = userId;
		this.followUserId = followUserId;
	}

	public FollowDto(int followId, int userId, int followUserId) {
		super();
		this.followId = followId;
		this.userId = userId;
		this.followUserId = followUserId;
	}

	public int getFollowId() {
		return followId;
	}

	public void setFollowId(int followId) {
		this.followId = followId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFollowUserId() {
		return followUserId;
	}

	public void setFollowUserId(int followUserId) {
		this.followUserId = followUserId;
	}

	@Override
	public String toString() {
		return "FollowDto [followId=" + followId + ", userId=" + userId + ", followUserId=" + followUserId + "]";
	}
}
