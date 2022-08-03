package com.web.curation.model.service;

import java.sql.SQLException;
import java.util.List;

import com.web.curation.model.dto.ScheduleDto;

public interface ScheduleService {

	public int createSchedule(ScheduleDto scheduleDto) throws SQLException;
	public int updateSchedule(ScheduleDto scheduleDto) throws SQLException;
	public int deleteSchedule(int scheduleId) throws SQLException;
	public ScheduleDto getScheduleDetail(int scheduleId) throws SQLException;
	public List<ScheduleDto> getScheduleList(ScheduleDto scheduleDto) throws SQLException;
	public List<ScheduleDto> getScheduleListByTitle(ScheduleDto scheduleDto) throws SQLException;
}
