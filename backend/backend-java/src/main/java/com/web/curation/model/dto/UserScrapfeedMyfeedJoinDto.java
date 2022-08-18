package com.web.curation.model.dto;

import lombok.Data;

@Data
public class UserScrapfeedMyfeedJoinDto {

	private int myfeedId;
	private int userId;
	private int feedId;
	private String myfeedDate;
	private int boardId;
	private int scrapfeedType;
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
	public int getFeedId() {
		return feedId;
	}
	public void setFeedId(int feedId) {
		this.feedId = feedId;
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
	public int getFeedType() {
		return feedType;
	}
	public void setFeedType(int feedType) {
		this.feedType = feedType;
	}
	public String getFeedUrl() {
		return feedUrl;
	}
	public void setFeedUrl(String feedUrl) {
		this.feedUrl = feedUrl;
	}
	public String getFeedSource() {
		return feedSource;
	}
	public void setFeedSource(String feedSource) {
		this.feedSource = feedSource;
	}
	public String getFeedTitle() {
		return feedTitle;
	}
	public void setFeedTitle(String feedTitle) {
		this.feedTitle = feedTitle;
	}
	public String getFeedContent() {
		return feedContent;
	}
	public void setFeedContent(String feedContent) {
		this.feedContent = feedContent;
	}
	public String getFeedAuthor() {
		return feedAuthor;
	}
	public void setFeedAuthor(String feedAuthor) {
		this.feedAuthor = feedAuthor;
	}
	public String getFeedDate() {
		return feedDate;
	}
	public void setFeedDate(String feedDate) {
		this.feedDate = feedDate;
	}
	public String getFeedCategory() {
		return feedCategory;
	}
	public void setFeedCategory(String feedCategory) {
		this.feedCategory = feedCategory;
	}
	public String getFeedImgName() {
		return feedImgName;
	}
	public void setFeedImgName(String feedImgName) {
		this.feedImgName = feedImgName;
	}
	public String getFeedImgUrl() {
		return feedImgUrl;
	}
	public void setFeedImgUrl(String feedImgUrl) {
		this.feedImgUrl = feedImgUrl;
	}
	@Override
	public String toString() {
		return "UserScrapfeedMyfeedJoinDto [myfeedId=" + myfeedId + ", userId=" + userId + ", feedId=" + feedId
				+ ", myfeedDate=" + myfeedDate + ", boardId=" + boardId + ", scrapfeedType=" + scrapfeedType
				+ ", feedType=" + feedType + ", feedUrl=" + feedUrl + ", feedSource=" + feedSource + ", feedTitle="
				+ feedTitle + ", feedContent=" + feedContent + ", feedAuthor=" + feedAuthor + ", feedDate=" + feedDate
				+ ", feedCategory=" + feedCategory + ", feedImgName=" + feedImgName + ", feedImgUrl=" + feedImgUrl
				+ "]";
	}
	
	
	
}
