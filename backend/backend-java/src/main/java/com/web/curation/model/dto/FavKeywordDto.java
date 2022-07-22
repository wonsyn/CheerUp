package com.web.curation.model.dto;

import lombok.Data;

@Data
public class FavKeywordDto {

	private int favKeywordId;
	private int keywordId;
	private int userId;
	private String keyword;
}
