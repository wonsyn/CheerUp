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
	// 알람 종류 기준
	public List<AlarmDto> getAlarmListByType(AlarmDto alarmDto) throws SQLException;
	// 알람 내용 기준
	public List<AlarmDto> getAlarmListByContent(AlarmDto alarmDto) throws SQLException;
	// 알람 생성 범위 기준
	public List<AlarmDto> getAlarmListByDate(int receiverId) throws SQLException;
	// 확인 알람
	public List<AlarmDto> getCheckedAlarm(int userId) throws SQLException;
	// 미확인 알람
	public List<AlarmDto> getUnCheckedAlarm(int userId) throws SQLException;
	// 알람 번호를 통한 유저 번호 가져오기
	public int getUserIdByAlarmId(int alarmId) throws SQLException;
	// 알람 체크 시 상태 변경
	public int checkAlarm(int alarmId) throws SQLException;
	// receiverId, senderId, content, url 을 이용한 알람 존재 여부 체크
	public AlarmDto alreadyExist(AlarmDto alarmDto) throws SQLException;
	// 일주일 내 가장 빠른 알람 조회
	public AlarmDto getLatestAlarm(int userId) throws SQLException;
	
}
