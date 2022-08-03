package com.web.curation.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.ScheduleDto;
import com.web.curation.model.service.JwtService;
import com.web.curation.model.service.ScheduleService;
import com.web.curation.model.service.UserService;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<Void> createSchedule(@RequestBody ScheduleDto scheduleDto, HttpServletRequest request) throws SQLException{
		
		scheduleDto.setUserId(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))));
		System.out.println(scheduleDto);
		if(scheduleService.createSchedule(scheduleDto) == 1) return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
