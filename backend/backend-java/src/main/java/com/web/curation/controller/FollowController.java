package com.web.curation.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.FollowDto;
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
		List<UserDto> followList = followService.getMyFollowingList(userId);
		return new ResponseEntity<List<UserDto>>(followList,HttpStatus.OK);
	}
	
	@GetMapping("/followerList")
	public ResponseEntity<List<UserDto>> getFollowMeList(HttpServletRequest request) throws SQLException{
		String loginUserId = jwtService.getUserIdByJwt(request.getHeader("access-token"));
		int userId = userService.getUserIdById(loginUserId);
		List<UserDto> followList = followService.getMyFollowerList(userId);
		return new ResponseEntity<List<UserDto>>(followList,HttpStatus.OK);
	}
	
	@GetMapping("/status/{id}")
	public ResponseEntity<Void> getFollowStatus(@PathVariable String id, HttpServletRequest request) throws SQLException{
		String loginUserId = jwtService.getUserIdByJwt(request.getHeader("access-token"));
		int userId = userService.getUserIdById(loginUserId);
		int followerUserId = userService.getUserIdById(id);
		FollowDto result = followService.getFollowStatus(new FollowDto(userId,followerUserId));
		if(result != null)	return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Void> follow(@PathVariable String id, HttpServletRequest request) throws SQLException{
		String loginId = jwtService.getUserIdByJwt(request.getHeader("access-token"));
		int userId = userService.getUserIdById(loginId);
		int followUserId = userService.getUserIdById(id);
		System.out.println("[사용자,팔로우]: [" + userId +","+followUserId+"]");
		int result = followService.followUser(new FollowDto(userId, followUserId));
		
		if(result != 0)	return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> unfollow(@PathVariable String id, HttpServletRequest request) throws SQLException{
		String loginId = jwtService.getUserIdByJwt(request.getHeader("access-token"));
		int userId = userService.getUserIdById(loginId);
		int followUserId = userService.getUserIdById(id);
		int result = followService.unFollowUser(new FollowDto(userId, followUserId));
		
		if(result != 0)	return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
