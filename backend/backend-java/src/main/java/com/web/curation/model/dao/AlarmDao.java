package com.web.curation.model.dao;

import java.sql.SQLException;

import com.web.curation.model.dto.AlarmDto;

public interface AlarmDao {

	public int createAlarm(AlarmDto alarmDto) throws SQLException;
	public int updateAlarm(AlarmDto alarmDto) throws SQLException;
}
