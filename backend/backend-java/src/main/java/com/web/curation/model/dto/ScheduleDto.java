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
}
