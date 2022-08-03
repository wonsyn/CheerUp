package com.web.curation.model.service;

import java.sql.SQLException;

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

}
