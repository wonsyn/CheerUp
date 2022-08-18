package com.web.curation.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.web.curation.model.dto.ScheduleDto;

public interface ScheduleDao {

	public int createSchedule(ScheduleDto scheduleDto) throws SQLException;
	public int updateSchedule(ScheduleDto scheduleDto) throws SQLException;
	public int deleteSchedule(int scheduleId) throws SQLException;
	public ScheduleDto getScheduleDetail(int scheduleId) throws SQLException;
	public List<ScheduleDto> getScheduleList(ScheduleDto scheduleDto) throws SQLException;
	public List<ScheduleDto> getScheduleListByTitle(ScheduleDto scheduleDto) throws SQLException;
	public List<ScheduleDto> getScheduleListByCompany(ScheduleDto scheduleDto) throws SQLException;
	public List<ScheduleDto> getScheduleListByDate(ScheduleDto scheduleDto) throws SQLException;
	public List<ScheduleDto> getScheduleListByMemo(ScheduleDto scheduleDto) throws SQLException;
	public List<ScheduleDto> getScheduleListByCategory(ScheduleDto scheduleDto) throws SQLException;
	public List<ScheduleDto> getScheduleListByStatus(ScheduleDto scheduleDto) throws SQLException;
	public List<ScheduleDto> getScheduleListByConditions(ScheduleDto scheduleDto) throws SQLException;
	public int getUserIdByScheduleId(int scheduleId) throws SQLException;
}
