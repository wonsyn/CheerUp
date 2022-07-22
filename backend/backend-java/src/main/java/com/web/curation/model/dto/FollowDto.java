package com.web.curation.model.dto;

import lombok.Data;

@Data
public class FollowDto {

	private int followId;
	private int userId;
	private int followUserId;
}
