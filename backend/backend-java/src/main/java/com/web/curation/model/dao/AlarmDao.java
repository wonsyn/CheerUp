package com.web.curation.model.dao;

import java.sql.SQLException;

import com.web.curation.model.dto.AlarmDto;

public interface AlarmDao {

	public int createAlarm(AlarmDto alarmDto) throws SQLException;
	public int updateAlarm(AlarmDto alarmDto) throws SQLException;
	public int deleteAlarm(int alarmdId) throws SQLException;
	
	
	
	// 알람 번호를 통한 유저 번호 가져오기
	public int getUserIdByAlarmId(int alarmId) throws SQLException;
}
