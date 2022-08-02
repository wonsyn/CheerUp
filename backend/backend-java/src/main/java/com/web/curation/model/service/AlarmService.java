package com.web.curation.model.service;

import java.sql.SQLException;

import com.web.curation.model.dto.AlarmDto;

public interface AlarmService {

	public int createAlarm(AlarmDto alarmDto) throws SQLException;
	public int updateAlarm(AlarmDto alarmDto) throws SQLException;
	public int deleteAlarm(int alarmId) throws SQLException;
	
	
	// 알람 번호를 통한 유저 번호 가져오기
	public int getUserIdByAlarmId(int alarmId) throws SQLException;
}
