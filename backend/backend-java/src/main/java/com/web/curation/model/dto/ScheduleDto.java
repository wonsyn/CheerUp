package com.web.curation.model.dto;

import lombok.Data;

@Data
public class ScheduleDto {

	private int scheduleId;
	private int userId;
	private String scheduleTitle;
	private String scheduleCompany;
	private String scheduleDate;
	private String scheduleMemo;
	private String scheduleCategory;
	private String scheduleStatus;
	
	// 검색을 위한 필드
	private String startDate;
	private String endDate;
	
	public ScheduleDto() {
		super();
	}
	
	public ScheduleDto(int userId, String scheduleTitle, String scheduleCompany,
			String scheduleMemo, String scheduleCategory, String scheduleStatus) {
		super();
		this.userId = userId;
		this.scheduleTitle = scheduleTitle;
		this.scheduleCompany = scheduleCompany;
		this.scheduleMemo = scheduleMemo;
		this.scheduleCategory = scheduleCategory;
		this.scheduleStatus = scheduleStatus;
	}

	public ScheduleDto(int scheduleId, int userId, String scheduleTitle, String scheduleCompany, String scheduleDate,
			String scheduleMemo, String scheduleCategory, String scheduleStatus) {
		super();
		this.scheduleId = scheduleId;
		this.userId = userId;
		this.scheduleTitle = scheduleTitle;
		this.scheduleCompany = scheduleCompany;
		this.scheduleDate = scheduleDate;
		this.scheduleMemo = scheduleMemo;
		this.scheduleCategory = scheduleCategory;
		this.scheduleStatus = scheduleStatus;
	}

	public ScheduleDto(int userId, String scheduleTitle, String scheduleCompany, String scheduleDate,
			String scheduleMemo, String scheduleCategory, String scheduleStatus, String startDate, String endDate) {
		super();
		this.userId = userId;
		this.scheduleTitle = scheduleTitle;
		this.scheduleCompany = scheduleCompany;
		this.scheduleDate = scheduleDate;
		this.scheduleMemo = scheduleMemo;
		this.scheduleCategory = scheduleCategory;
		this.scheduleStatus = scheduleStatus;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getScheduleTitle() {
		return scheduleTitle;
	}

	public void setScheduleTitle(String scheduleTitle) {
		this.scheduleTitle = scheduleTitle;
	}

	public String getScheduleCompany() {
		return scheduleCompany;
	}

	public void setScheduleCompany(String scheduleCompany) {
		this.scheduleCompany = scheduleCompany;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getScheduleMemo() {
		return scheduleMemo;
	}

	public void setScheduleMemo(String scheduleMemo) {
		this.scheduleMemo = scheduleMemo;
	}

	public String getScheduleCategory() {
		return scheduleCategory;
	}

	public void setScheduleCategory(String scheduleCategory) {
		this.scheduleCategory = scheduleCategory;
	}
	
	public String getScheduleStatus() {
		return scheduleStatus;
	}

	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "ScheduleDto [scheduleId=" + scheduleId + ", userId=" + userId + ", scheduleTitle=" + scheduleTitle
				+ ", scheduleCompany=" + scheduleCompany + ", scheduleDate=" + scheduleDate + ", scheduleMemo="
				+ scheduleMemo + ", scheduleCategory=" + scheduleCategory + ", scheduleStatus=" + scheduleStatus
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
}
