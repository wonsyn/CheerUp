package com.web.curation.model.service;

import java.sql.SQLException;

import com.web.curation.model.dto.ScheduleDto;

public interface ScheduleService {

	public int createSchedule(ScheduleDto scheduleDto) throws SQLException;
	public int updateSchedule(ScheduleDto scheduleDto) throws SQLException;
	public int deleteSchedule(int scheduleId) throws SQLException;
}
