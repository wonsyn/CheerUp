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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.FollowDto;
import com.web.curation.model.service.FollowService;
import com.web.curation.model.service.JwtService;
import com.web.curation.model.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
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
			  notes="{followList : 리스트}")
	@GetMapping("/followingList")
	public ResponseEntity<Map<String, Object>> getMyFollowList(HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			resultMap.put("message", SUCCESS);
			resultMap.put("followList", followService.getMyFollowingList(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))))); 
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;	
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value="나를 팔로우하고 있는 유저 리스트", 
			  notes="{followList : 리스트}")
	@GetMapping("/followerList")
	public ResponseEntity<Map<String, Object>> getFollowMeList(HttpServletRequest request) {
				
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			resultMap.put("message", SUCCESS);
			resultMap.put("followList", followService.getMyFollowerList(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))))); 
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value="특정 유저에 대한 나의 팔로우 상태 조회", 
			  notes="{follow : true,false}")
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
			  notes="{followList : 리스트}\n나의 프로필에서 팔로우 시 리스트 생성 가능\n상대 프로필에서 팔로우 시 기능 추가 필요}")
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> follow(@PathVariable String id, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			int followUserId = userService.getUserIdById(id);
			int result = followService.followUser(new FollowDto(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))), followUserId));
			
			if(result == 1) {
				resultMap.put("message", SUCCESS);
				resultMap.put("followList", followService.getMyFollowingList(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")))));
			}
			else {
				resultMap.put("message", "(FollowController Line 122)");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value="언팔로우 하기", 
			  notes="{followList : 리스트}\n나의 프로필에서 언팔로우 시 리스트 생성 가능\n상대 프로필에서 언팔로우 시 기능 추가 필요")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, Object>> unfollow(@PathVariable String id, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			int followUserId = userService.getUserIdById(id);
			int result = followService.unFollowUser(new FollowDto(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token"))), followUserId));
			
			if(result == 1) {
				resultMap.put("message", SUCCESS);
				resultMap.put("followList", followService.getMyFollowingList(followUserId));
			}
			else {
				resultMap.put("message", "(FollowController Line 149)");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
}
