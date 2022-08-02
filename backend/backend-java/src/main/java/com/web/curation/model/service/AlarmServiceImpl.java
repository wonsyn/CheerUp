package com.web.curation.model.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.AlarmDao;
import com.web.curation.model.dto.AlarmDto;

@Service
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	private AlarmDao alarmMapper;
	
	@Override
	public int createAlarm(AlarmDto alarmDto) throws SQLException {
		return alarmMapper.createAlarm(alarmDto);
	}
}
