package com.web.curation.model.service;

import java.sql.SQLException;

import com.web.curation.model.dto.AlarmDto;

public interface AlarmService {

	public int createAlarm(AlarmDto alarmDto) throws SQLException;
	public int updateAlarm(AlarmDto alarmDto) throws SQLException;
}
