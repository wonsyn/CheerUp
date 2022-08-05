package com.web.curation.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.FollowDto;
import com.web.curation.model.dto.UserDto;
import com.web.curation.model.service.FollowService;
import com.web.curation.model.service.JwtService;
import com.web.curation.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@Api(tags = "팔로우")
@RestController
@RequestMapping("/follow")
public class FollowController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired 
	FollowService followService;
	
	@ApiOperation(value="내가 팔로우하고 있는 유저 리스트", 
			  notes="경로 예시) http://localhost:8080/cheerup/follow/followingList?id=tes77"
			  		+ "\n return 예시) json {followList : 리스트}")
	@GetMapping("/followinglist")
	public ResponseEntity<Map<String, Object>> getMyFollowList(@RequestParam String id, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			resultMap.put("message", SUCCESS);
			resultMap.put("followList", followService.getMyFollowingList(userService.getUserIdById(id))); 
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;	
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value="나를 팔로우하고 있는 유저 리스트", 
			  notes="경로 예시) http://localhost:8080/cheerup/follow/followerList?id=aaa"
			  		+ "\nreturn 예시) json {followList : 리스트}")
	@GetMapping("/followerlist")
	public ResponseEntity<Map<String, Object>> getFollowMeList(@RequestParam String id, HttpServletRequest request) {
				
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			resultMap.put("message", SUCCESS);
			resultMap.put("followList", followService.getMyFollowerList(userService.getUserIdById(id))); 
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value="특정 유저에 대한 나의 팔로우 상태 조회", 
			  notes="경로 예시) http://localhost:8080/cheerup/follow/status/aaa"
			  		+ "\nhttp method : get"
			  		+ "\nreturn 예시) json {follow : true,false}")
	@GetMapping("/status/{id}")
	public ResponseEntity<Map<String, Object>> getFollowStatus(@PathVariable String id, HttpServletRequest request){
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			int followerUserId = userService.getUserIdById(id);
			FollowDto result = followService.getFollowStatus(new FollowDto(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))),followerUserId));
			if(result != null) {
				resultMap.put("message", SUCCESS);
				resultMap.put("follow", true);
			}
			else {
				resultMap.put("message", SUCCESS);
				resultMap.put("follow", false);				
			}
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value="팔로우하기", 
			  notes="경로 예시) http://localhost:8080/cheerup/follow"
			  		+ "\n데이터 예시) json { 'id' : '유저id' }"
			  		+ "\nhttp method : post"
			  		+ "\n return 예시) json {followList : 팔로우리스트}")
	@PostMapping("")
	public ResponseEntity<Map<String, Object>> follow(@RequestBody UserDto userDto, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			int followUserId = userService.getUserIdById(userDto.getId());
			int loginUserId = userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")));
			int result = followService.followUser(new FollowDto(loginUserId, followUserId));
			
			if(result == 1) {
				resultMap.put("message", SUCCESS);
				resultMap.put("followList", followService.getMyFollowingList(loginUserId));
			}
			else {
				resultMap.put("message", FAIL);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value="언팔로우 하기", 
			  notes="경로 예시) http://localhost:8080/cheerup/follow/delete/{id}"
			  		+ "\n http method : delete"
			  		+ "\n return 예시) json {followList : 팔로우리스트}")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, Object>> unfollow(@PathVariable String id, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			int followUserId = userService.getUserIdById(id);
			int loginUserId = userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")));
			int result = followService.unFollowUser(new FollowDto(loginUserId, followUserId));
			
			if(result == 1) {
				resultMap.put("message", SUCCESS);
				resultMap.put("followList", followService.getMyFollowingList(loginUserId));
			}
			else {
				resultMap.put("message", FAIL);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
}
