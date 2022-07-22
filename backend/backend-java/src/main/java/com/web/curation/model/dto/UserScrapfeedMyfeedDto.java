package com.web.curation.model.dto;

import lombok.Data;

@Data
public class UserScrapfeedMyfeedDto {

	private int myfeedId;
//	private int userId;
	private int scrapfeedId;
	private String myfeedDate;
	private int boardId;
	private String scrapfeedUrl;
	private String scrapfeedType;
	private String scrapfeedCategory;
	private String scrapfeedSource;
	private String scrapfeedImg;
}
