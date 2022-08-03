package com.web.curation.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.ScheduleDao;
import com.web.curation.model.dto.ScheduleDto;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	ScheduleDao scheduleMapper;
	
	@Override
	public int createSchedule(ScheduleDto scheduleDto) throws SQLException {
		return scheduleMapper.createSchedule(scheduleDto);
	}
	
	@Override
	public int updateSchedule(ScheduleDto scheduleDto) throws SQLException {
		return scheduleMapper.updateSchedule(scheduleDto);
	}

	@Override
	public int deleteSchedule(int scheduleId) throws SQLException {
		return scheduleMapper.deleteSchedule(scheduleId);
	}
	
	@Override
	public ScheduleDto getScheduleDetail(int scheduleId) throws SQLException {
		return scheduleMapper.getScheduleDetail(scheduleId);
	}
	
	@Override
	public List<ScheduleDto> getScheduleList(ScheduleDto scheduleDto) throws SQLException {
		return scheduleMapper.getScheduleList(scheduleDto);
	}
	
	@Override
	public List<ScheduleDto> getScheduleListByTitle(ScheduleDto scheduleDto) throws SQLException {
		return scheduleMapper.getScheduleListByTitle(scheduleDto);
	}
}
