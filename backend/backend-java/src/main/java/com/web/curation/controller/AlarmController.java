package com.web.curation.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.AlarmDto;
import com.web.curation.model.service.AlarmService;
import com.web.curation.model.service.JwtService;
import com.web.curation.model.service.UserService;

@RestController
@RequestMapping("/alarm")
public class AlarmController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AlarmService alarmService;
	
	@PostMapping("/create")
	public ResponseEntity<Void> createAlarm(@RequestBody AlarmDto alarmDto, HttpServletRequest request) throws SQLException{
		
		alarmDto.setAlarmReceiverId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
		System.out.println("create: "+ alarmDto);
		
		if(alarmService.createAlarm(alarmDto) == 1) return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
	@PutMapping("/update")
	public ResponseEntity<Void> updateAlarm(@RequestBody AlarmDto alarmDto, HttpServletRequest request) throws SQLException {
		
		int loginUser = userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")));
		if(userService.isSameLoginUserAndRequestId(loginUser, alarmDto.getAlarmReceiverId())) return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		alarmDto.setAlarmReceiverId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
		System.out.println("update: "+ alarmDto);
		// 수정
		if(alarmService.updateAlarm(alarmDto) == 1) return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteAlarm(@PathVariable int id, HttpServletRequest request) throws SQLException{
		
		int loginUser = userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")));
		int owner = alarmService.getUserIdByAlarmId(id);
		if(userService.isSameLoginUserAndRequestId(loginUser, owner)) return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		System.out.println("delete: "+ id);
		if(alarmService.deleteAlarm(id) == 1) return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<Map<String, Object>> getDetailAlarm(@PathVariable int alarmId, HttpServletRequest request) {	 
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		try {
			AlarmDto result = alarmService.getAlarmDetail(alarmId);
			if(result != null) {
				resultMap.put("alarmInfo", result);
				resultMap.put("message", SUCCESS);				
			}
			else {
				resultMap.put("message", FAIL);								
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(resultMap,status);
	}
	
	@GetMapping("/listInThisMonth")
	public ResponseEntity<List<AlarmDto>> getAlarmListInThisMonth(@RequestParam String thisMonth, HttpServletRequest request) throws SQLException{
		
		AlarmDto alarm = new AlarmDto();
		alarm.setAlarmReceiverId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
		alarm.setAlarmDateTime(thisMonth);
		return new ResponseEntity<List<AlarmDto>>(alarmService.getAlarmListInThisMonth(alarm),HttpStatus.OK); 
	}
	
	@GetMapping("/listBySenderId")
	public ResponseEntity<List<AlarmDto>> getAlarmListBySenderId(@RequestParam int senderId, HttpServletRequest request) throws SQLException{
		
		AlarmDto alarm = new AlarmDto();
		alarm.setAlarmReceiverId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
		alarm.setAlarmSenderId(senderId);
		return new ResponseEntity<List<AlarmDto>>(alarmService.getAlarmListBySenderId(alarm),HttpStatus.OK); 
	}
	
	
}
