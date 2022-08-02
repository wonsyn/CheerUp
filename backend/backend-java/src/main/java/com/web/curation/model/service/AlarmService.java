package com.web.curation.model.service;

import java.sql.SQLException;
import java.util.List;

import com.web.curation.model.dto.AlarmDto;

public interface AlarmService {

	public int createAlarm(AlarmDto alarmDto) throws SQLException;
	public int updateAlarm(AlarmDto alarmDto) throws SQLException;
	public int deleteAlarm(int alarmId) throws SQLException;
	// 알람 1개 세부
	public AlarmDto getAlarmDetail(int alarmId) throws SQLException;
	// 이번달 알람 리스트
	public List<AlarmDto> getAlarmListInThisMonth(AlarmDto alarmDto) throws SQLException;
	// 송신자 기준
	public List<AlarmDto> getAlarmListBySenderId(AlarmDto alarmDto) throws SQLException;
		
	// 알람 번호를 통한 유저 번호 가져오기
	public int getUserIdByAlarmId(int alarmId) throws SQLException;
}
