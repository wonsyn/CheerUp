package com.web.curation.controller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.UserDto;
import com.web.curation.model.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/signup")
	public ResponseEntity<Void> registUser(@RequestBody UserDto userDto) {
		logger.debug("입력 회원 정보 : {}" ,userDto);
		
		// 중복 아이디 검사
		UserDto loginUser;
		try {
//			loginUser = userService.login(userDto);
//			if(loginUser != null) return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
			int cnt = userService.registUser(userDto);
			if(cnt == 1) {
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
}
