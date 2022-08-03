package com.web.curation.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.JwtDto;
import com.web.curation.model.dto.UserDto;
import com.web.curation.model.service.JwtServiceImpl;
import com.web.curation.model.service.UserService;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/user")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private JwtServiceImpl jwtService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody UserDto userDto){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		try {
			UserDto loginUser = userService.login(userDto);
			// 로그인 정보가 있다면
			if(loginUser != null) {

				String accessToken = jwtService.createAccessToken("id", loginUser.getId(), "access-token");
				String refreshToken = null;
				logger.debug("로그인 access 토큰정보 : {}", accessToken);
				
				// refresh-token 생성
				// token table에서 refresh-token-period 값 가져오기
				JwtDto jwt = jwtService.tokenInfo(loginUser.getUserId());
				// 이미 refresh-token이 DB에 존재한다면
				if(jwt != null) {
					// 리프레시 재발급 기준 날짜 설정 ( 오늘 + 3일 )
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");					
					cal.add(Calendar.DATE, 3);
					
					// DB 저장 날짜 < 재발급 기준 날짜 => 리프레시 토큰 발급, 음수 나오면 발급
					if(jwt.getRefreshTokenPeriod().compareTo(sdf.format(cal.getTime())) < 0) {
						JwtDto newRefreshToken = new JwtDto(jwt.getTokenId(), loginUser.getUserId(), ""); 
						jwtService.updateRefreshToken(newRefreshToken);
						refreshToken = jwtService.createAccessToken("id", loginUser.getId(), "refresh-token");
						logger.debug("로그인 refresh 토큰정보 : {}", newRefreshToken);
					}
					// 리프레시 토큰의 유효 기간이 충분하다면 DB 정보를 꺼내와 할당
					else {
						refreshToken = jwtService.getRefreshToken("id", loginUser.getUserId(), "refresh-token", sdf.parse(jwt.getRefreshTokenPeriod()));
					}
				}				
				// DB에 refresh-token 정보가 존재하지 않다면 새로 생성
				else {
					jwtService.createRefreshToken(new JwtDto(loginUser.getUserId(), ""));
					refreshToken = jwtService.createAccessToken("id", loginUser.getId(), "refresh-token");
				}
				// return 값 생성
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
			} 
			// 로그인 실패
			else {
				resultMap.put("message", FAIL);
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
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
	
	@GetMapping("/detail/{id}")
//	public ResponseEntity<Map<String, Object>> userInfo(@PathVariable("id") String id, HttpServletRequest request) {
	public ResponseEntity<Map<String, Object>> userInfo(@PathVariable("id") String id) {
		logger.debug("id : {}", id);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		try {
			UserDto userInfo = userService.userInfo(id);
			// 유저 정보가 있다면
			if(userInfo != null) {
				resultMap.put("userInfo", userInfo);
				resultMap.put("message", SUCCESS);
			}
			// 유저 정보가 없다면
			else {				
				resultMap.put("message", FAIL);
			}
		} catch (Exception e) {
			logger.error("정보 조회 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		/*
		// access-token 유효 확인
		if(jwtService.isUsableAccessToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰");
		} else {
			
			// db refresh-token 유효기간 확인 후 재발급 or 로그인
			
			logger.error("사용 불가능한 액세스 토큰");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		*/
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto, HttpServletRequest request) throws SQLException {
		
		int loginUser = userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")));
		if(userService.isSameLoginUserAndRequestId(loginUser, userDto.getUserId())) return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR); 
		
		int cnt = userService.updateUser(userDto);
		System.out.println(userDto);
		
		if(cnt == 1) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		else return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id, HttpServletRequest request) throws SQLException {
		
		int loginUser = userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")));
		if(userService.isSameLoginUserAndRequestId(loginUser, userService.getUserIdById(id))) return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		int cnt = userService.deleteUser(id);
		
		if(cnt == 1) {
			// 유저 액세스, 리프레시 토큰 유효기간 소멸시켜야됨
			// 블랙 리스트에 추가??
			
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		else return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/checkPassWord")
	public ResponseEntity<Void> checkPassword(@RequestBody UserDto userDto){
		
	    try {
			if(!userService.userInfo(userDto.getId()).getPassword().equals(userDto.getPassword())) return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (SQLException e) {
			logger.debug("패스워드 오류 : {}", e.getMessage());
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/searchById")
	public ResponseEntity<List<UserDto>> getUserListById(@RequestParam String id) throws SQLException{
		System.out.println("id: "+id);
		return new ResponseEntity<List<UserDto>>( userService.getUserListById(id), HttpStatus.OK);
	}
	
	@GetMapping("/searchByNickName")
	public ResponseEntity<List<UserDto>> getUserListByNickName(@RequestParam String nickname) throws SQLException{
		System.out.println("nickname: "+nickname);
		return new ResponseEntity<List<UserDto>>( userService.getUserListByNickName(nickname), HttpStatus.OK);
	}
	
	@GetMapping("/searchByEmail")
	public ResponseEntity<List<UserDto>> getUserListByEmail(@RequestParam String email) throws SQLException{
		System.out.println("email: "+email);
		return new ResponseEntity<List<UserDto>>( userService.getUserListByEmail(email), HttpStatus.OK);
	}
}
