package com.web.curation.model.dto;

import lombok.Data;

@Data
public class PersonalWordDto {
	private int personalWordId;
	private int userId;
	private String personalWord;
	private String personalWordExp;
	private int personalFavWord;
	private String personalWordDate;
	public int getPersonalWordId() {
		return personalWordId;
	}
	public void setPersonalWordId(int personalWordId) {
		this.personalWordId = personalWordId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPersonalWord() {
		return personalWord;
	}
	public void setPersonalWord(String personalWord) {
		this.personalWord = personalWord;
	}
	public String getPersonalWordExp() {
		return personalWordExp;
	}
	public void setPersonalWordExp(String personalWordExp) {
		this.personalWordExp = personalWordExp;
	}
	public int getPersonalFavWord() {
		return personalFavWord;
	}
	public void setPersonalFavWord(int personalFavWord) {
		this.personalFavWord = personalFavWord;
	}
	public String getPersonalWordDate() {
		return personalWordDate;
	}
	public void setPersonalWordDate(String personalWordDate) {
		this.personalWordDate = personalWordDate;
	}
	@Override
	public String toString() {
		return "PersonalWordDto [personalWordId=" + personalWordId + ", userId=" + userId + ", personalWord="
				+ personalWord + ", personalWordExp=" + personalWordExp + ", personalFavWord=" + personalFavWord
				+ ", personalWordDate=" + personalWordDate + "]";
	}
	
	
	
}
