package com.web.curation.model.service;

import java.sql.SQLException;
import java.util.List;

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
	
	@Override
	public int updateAlarm(AlarmDto alarmDto) throws SQLException {
		return alarmMapper.updateAlarm(alarmDto);
	}
	
	@Override
	public int deleteAlarm(int alarmId) throws SQLException {
		return alarmMapper.deleteAlarm(alarmId);
	}
	
	@Override
	public int getUserIdByAlarmId(int alarmId) throws SQLException {
		return alarmMapper.getUserIdByAlarmId(alarmId);
	}
	
	@Override
	public AlarmDto getAlarmDetail(int alarmId) throws SQLException {
		return alarmMapper.getAlarmDetail(alarmId);
	}
	
	@Override
	public List<AlarmDto> getAlarmListInThisMonth(AlarmDto alarmDto) throws SQLException {
		return alarmMapper.getAlarmListInThisMonth(alarmDto);
	}
	
	@Override
	public List<AlarmDto> getAlarmListBySenderId(AlarmDto alarmDto) throws SQLException {
		return alarmMapper.getAlarmListBySenderId(alarmDto);
	}
	
	@Override
	public List<AlarmDto> getAlarmListByType(AlarmDto alarmDto) throws SQLException {
		return alarmMapper.getAlarmListByType(alarmDto);
	}
	
	@Override
	public List<AlarmDto> getAlarmListByContent(AlarmDto alarmDto) throws SQLException {
		return alarmMapper.getAlarmListByContent(alarmDto);
	}
	
	@Override
	public List<AlarmDto> getAlarmListByDate(int receiverId) throws SQLException {
		return alarmMapper.getAlarmListByDate(receiverId);
	}
	
	@Override
	public List<AlarmDto> getCheckedAlarm(int userId) throws SQLException {
		return alarmMapper.getCheckedAlarm(userId);
	}
	
	@Override
	public List<AlarmDto> getUnCheckedAlarm(int userId) throws SQLException {
		return alarmMapper.getUnCheckedAlarm(userId);
	}
	
	@Override
	public int checkAlarm(int alarmId) throws SQLException {
		return alarmMapper.checkAlarm(alarmId);
	}

	@Override
	public AlarmDto alreadyExist(AlarmDto alarmDto) throws SQLException {
		return alarmMapper.alreadyExist(alarmDto);
	}

	@Override
	public AlarmDto getLatestAlarm(int userId) throws SQLException {
		return alarmMapper.getLatestAlarm(userId);
	}
}
