package com.web.curation.model.dto;

import lombok.Data;

@Data
public class FeedDto {

	private int feedId;
	private int feedType;
	private String feedUrl;
	private String feedSource;
	private String feedTitle;
	private String feedContent;
	private String feedAuthor;
	private String feedDate;
	private String feedCategory;
	private String feedImgName;
	private String feedImgUrl;
}
