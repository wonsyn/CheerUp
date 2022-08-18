package com.web.curation.model.dto;

import lombok.Data;

@Data
public class AlarmDto {
	
	private int alarmId;
	private int alarmReceiverId;
	private int alarmSenderId;
	private String alarmType;
	private String alarmContent;
	private String alarmUrl;
	private String alarmDateTime;
	private String alarmCheckDateTime;
	private String scheduleDateTime;
	
	// 검색 조건을 위한 필드
	private String endDate;

	public AlarmDto() {
		super();
	}

	public AlarmDto(int alarmReceiverId, int alarmSenderId, String alarmType, String alarmContent, String alarmUrl, String alarmDateTime) {
		super();
		this.alarmReceiverId = alarmReceiverId;
		this.alarmSenderId = alarmSenderId;
		this.alarmType = alarmType;
		this.alarmContent = alarmContent;
		this.alarmUrl = alarmUrl;
		this.alarmDateTime = alarmDateTime;
	}

	public AlarmDto(int alarmReceiverId, int alarmSenderId, String alarmType, String alarmContent, String alarmUrl,
			String alarmDateTime, String scheduleDateTime) {
		super();
		this.alarmReceiverId = alarmReceiverId;
		this.alarmSenderId = alarmSenderId;
		this.alarmType = alarmType;
		this.alarmContent = alarmContent;
		this.alarmUrl = alarmUrl;
		this.alarmDateTime = alarmDateTime;
		this.scheduleDateTime = scheduleDateTime;
	}

	public AlarmDto(int alarmId, int alarmReceiverId, int alarmSenderId, String alarmType, String alarmContent,
			String alarmUrl, String alarmDateTime, String alarmCheckDateTime, String endDate) {
		super();
		this.alarmId = alarmId;
		this.alarmReceiverId = alarmReceiverId;
		this.alarmSenderId = alarmSenderId;
		this.alarmType = alarmType;
		this.alarmContent = alarmContent;
		this.alarmUrl = alarmUrl;
		this.alarmDateTime = alarmDateTime;
		this.alarmCheckDateTime = alarmCheckDateTime;
		this.endDate = endDate;
	}

	public int getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(int alarmId) {
		this.alarmId = alarmId;
	}

	public int getAlarmReceiverId() {
		return alarmReceiverId;
	}

	public void setAlarmReceiverId(int alarmReceiverId) {
		this.alarmReceiverId = alarmReceiverId;
	}

	public int getAlarmSenderId() {
		return alarmSenderId;
	}

	public void setAlarmSenderId(int alarmSenderId) {
		this.alarmSenderId = alarmSenderId;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

	public String getAlarmUrl() {
		return alarmUrl;
	}

	public void setAlarmUrl(String alarmUrl) {
		this.alarmUrl = alarmUrl;
	}

	public String getAlarmDateTime() {
		return alarmDateTime;
	}

	public void setAlarmDateTime(String alarmDateTime) {
		this.alarmDateTime = alarmDateTime;
	}

	public String getAlarmCheckDateTime() {
		return alarmCheckDateTime;
	}

	public void setAlarmCheckDateTime(String alarmCheckDateTime) {
		this.alarmCheckDateTime = alarmCheckDateTime;
	}
	
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getScheduleDateTime() {
		return scheduleDateTime;
	}

	public void setScheduleDateTime(String scheduleDateTime) {
		this.scheduleDateTime = scheduleDateTime;
	}

	@Override
	public String toString() {
		return "AlarmController [alarmId=" + alarmId + ", alarmReceiverId=" + alarmReceiverId + ", alarmSenderId="
				+ alarmSenderId + ", alarmType=" + alarmType + ", alarmContent=" + alarmContent + ", alarmUrl="
				+ alarmUrl + ", alarmDateTime=" + alarmDateTime + ", alarmCheckDateTime=" + alarmCheckDateTime + "]";
	}
}
