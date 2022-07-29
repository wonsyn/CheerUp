package com.web.curation.model.dto;

import lombok.Data;

@Data
public class UserScrapfeedMyfeedDto {

	private int myfeedId;
	private int userId;
	private int feedId;
	private String myfeedDate;
	private int boardId;
	private int scrapfeedType;
	
	public int getMyfeedId() {
		return myfeedId;
	}
	public void setMyfeedId(int myfeedId) {
		this.myfeedId = myfeedId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getfeedId() {
		return feedId;
	}
	public void setfeedId(int scrapfeedId) {
		this.feedId = scrapfeedId;
	}
	public String getMyfeedDate() {
		return myfeedDate;
	}
	public void setMyfeedDate(String myfeedDate) {
		this.myfeedDate = myfeedDate;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public int getScrapfeedType() {
		return scrapfeedType;
	}
	public void setScrapfeedType(int scrapfeedType) {
		this.scrapfeedType = scrapfeedType;
	}
	@Override
	public String toString() {
		return "UserScrapfeedMyfeedDto [myfeedId=" + myfeedId + ", userId=" + userId + ", feedId=" + feedId
				+ ", myfeedDate=" + myfeedDate + ", boardId=" + boardId + ", scrapfeedType=" + scrapfeedType + "]";
	}
	
	
	
}
