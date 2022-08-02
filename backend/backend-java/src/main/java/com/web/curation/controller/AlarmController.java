package com.web.curation.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
