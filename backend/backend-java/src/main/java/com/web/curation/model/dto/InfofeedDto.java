package com.web.curation.model.dto;

import lombok.Data;

@Data
public class InfofeedDto {

	private int infofeedId;
	private String infofeedUrl;
	private String infofeedSource;
	private String infofeedImg;
	private String infofeedTitle;
	private String infofeedContent;
	private String infofeedAuthor;
	private String infofeedDate;
	private String infofeedCategory;
}
