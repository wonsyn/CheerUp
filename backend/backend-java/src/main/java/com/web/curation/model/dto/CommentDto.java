package com.web.curation.model.dto;

import lombok.Data;

@Data
public class CommentDto {

	private int commentId;
	private String commentUrl;
	private String commentContent;
	private int userId;
	private String commentDate;
}
