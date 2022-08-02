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
	private String alarmDatetime;
	private String alarmCheckDatetime;
}
