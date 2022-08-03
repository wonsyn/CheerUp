package com.web.curation.model.dao;

import java.sql.SQLException;

import com.web.curation.model.dto.ScheduleDto;

public interface ScheduleDao {

	public int createSchedule(ScheduleDto scheduleDto) throws SQLException;
	public int updateSchedule(ScheduleDto scheduleDto) throws SQLException;
}
