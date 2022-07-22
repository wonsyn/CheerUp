package com.web.curation.model.dto;

import lombok.Data;

@Data
public class PersonalWordDto {
	private int personalWordId;
	private int userId;
	private String personalWord;
	private String personalWordExp;
	private String personalFavWord;
	private String personalWordDate;
	
}
