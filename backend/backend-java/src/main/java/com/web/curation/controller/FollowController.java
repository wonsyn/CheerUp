package com.web.curation.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.UserDto;
import com.web.curation.model.service.FollowService;
import com.web.curation.model.service.JwtService;
import com.web.curation.model.service.UserService;

@RestController
@RequestMapping("/follow")
public class FollowController {

	@Autowired
	UserService userService;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired 
	FollowService followService;
	
	@GetMapping("/followingList")
	public ResponseEntity<List<UserDto>> getMyFollowList(HttpServletRequest request) throws SQLException{
		String loginUserId = jwtService.getUserIdByJwt(request.getHeader("access-token"));
		int userId = userService.getUserIdById(loginUserId);
		List<UserDto> followList = followService.getMyFollowList(userId);
		return new ResponseEntity<List<UserDto>>(followList,HttpStatus.OK);
	}
	
	@GetMapping("/followerList")
	public ResponseEntity<List<UserDto>> getFollowMeList(HttpServletRequest request) throws SQLException{
		String loginUserId = jwtService.getUserIdByJwt(request.getHeader("access-token"));
		int userId = userService.getUserIdById(loginUserId);
		List<UserDto> followList = followService.getFollowedMeList(userId);
		return new ResponseEntity<List<UserDto>>(followList,HttpStatus.OK);
	}
}
