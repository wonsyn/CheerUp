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
	
	public int getFeedId() {
		return feedId;
	}
	public void setFeedId(int feedId) {
		this.feedId = feedId;
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
		return "FeedDto [feedId=" + feedId + ", feedType=" + feedType + ", feedUrl=" + feedUrl + ", feedSource="
				+ feedSource + ", feedTitle=" + feedTitle + ", feedContent=" + feedContent + ", feedAuthor="
				+ feedAuthor + ", feedDate=" + feedDate + ", feedCategory=" + feedCategory + ", feedImgName="
				+ feedImgName + ", feedImgUrl=" + feedImgUrl + "]";
	}
	
	
}
