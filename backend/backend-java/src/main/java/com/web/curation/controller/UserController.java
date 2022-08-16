package com.web.curation.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@Api(tags = "유저")
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
	
	
	@ApiOperation(value = "로그인", 
			notes = "경로 예시) http://localhost:8080/cheerup/user/login"
					+ "\n http method : post, 입력 데이터 : json {유저 정보}"
					+ "\n return 예시) json { 'access-token' : '토큰정보', 'refresh-token' : '토큰정보'}")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody UserDto userDto){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
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
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원가입", 
			notes = "경로 예시) http://localhost:8080/cheerup/user/signup"
					+ "\n http method : post, 입력 데이터 : json { 유저 정보}"
					+ "\n return 예시) json { 'message' : 'success, fail'}")
	@PostMapping("/signup")
	public ResponseEntity<Map<String, Object>> registUser(@RequestBody UserDto userDto) {
		logger.debug("입력 회원 정보 : {}" ,userDto);
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			// 중복 아이디 검사
			if(userService.userInfo(userDto.getId()) == null) {
				// 회원 등록
				if(userService.registUser(userDto) == 1) {
					resultMap.put("message", SUCCESS);
				}
				// 회원 등록 실패
				else {
					resultMap.put("message", FAIL);
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
			}
			// 중복 아이디 존재
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
	
	@ApiOperation(value = "개인 상세 정보 조회", 
			notes = "경로 예시) http://localhost:8080/cheerup/user/detail/aaa"
					+ "\n aaa는 유저 아이디, http method : get"
					+ "\n return 예시) json { 'userDetail' : '유저 정보'}")
	@GetMapping("/detail/{id}")
//	public ResponseEntity<Map<String, Object>> userInfo(@PathVariable("id") String id, HttpServletRequest request) {
	public ResponseEntity<Map<String, Object>> userInfo(@PathVariable("id") String id) {
		logger.debug("id : {}", id);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			UserDto userInfo = userService.userInfo(id);
			// 유저 정보가 있다면
			if(userInfo != null) {
				resultMap.put("userDetail", userInfo);
				resultMap.put("message", SUCCESS);
			}
			// 유저 정보가 없다면
			else {				
				resultMap.put("message", FAIL);
				status = HttpStatus.INTERNAL_SERVER_ERROR;
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
	
	@ApiOperation(value = "유저 정보 수정", 
			notes = "경로 예시) http://localhost:8080/cheerup/user/update"
					+ "\n http method : put, 입력 데이터 : {json : 수정 유저 정보}"
					+ "\n return 예시) json { 'userDetail' : '유저 정보'}")
	@PutMapping("/update")
	public ResponseEntity<Map<String,Object>> updateUser(@RequestBody UserDto userDto, HttpServletRequest request)  {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		int loginUser;
		try {
			loginUser = userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")));
			if(!userService.isSameLoginUserAndRequestId(loginUser, userDto.getUserId())) {
				status = HttpStatus.INTERNAL_SERVER_ERROR; 
				resultMap.put("message", FAIL);			
			}
			else {
				if(userService.updateUser(userDto) == 1) {
					resultMap.put("message", SUCCESS);
					resultMap.put("userDetail", userService.userInfo(loginUser));
				}
				else {
					status = HttpStatus.INTERNAL_SERVER_ERROR; 
					resultMap.put("message", FAIL);			
				}
				
			}
		} catch (SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR; 
			resultMap.put("message", e.getMessage());
		}
		
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원 탈퇴", 
			notes = "경로 예시) http://localhost:8080/cheerup/user/delete/{id}"
					+ "\n http method : delete, 입력 데이터 : pathvariable : 유저id(String)"
					+ "\n return 예시) json { 'message' : 'success, fail'}")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Map<String,Object>> deleteUser(@PathVariable String id, HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		int loginUser;
		try {
			loginUser = userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")));
			if(userService.isSameLoginUserAndRequestId(loginUser, userService.getUserIdById(id))) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				resultMap.put("message", FAIL);
			}
			else {
				if(userService.deleteUser(id) == 1) {
					// 유저 액세스, 리프레시 토큰 유효기간 소멸시켜야됨
					// 블랙 리스트에 추가??
					resultMap.put("message", SUCCESS);
				}
				else {
					status = HttpStatus.INTERNAL_SERVER_ERROR;
					resultMap.put("message", FAIL);
				}				
			}
		} catch (SQLException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			resultMap.put("message", e.getMessage());
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "패스워드 확인(개인 정보 수정, 탈퇴 시 이용)", 
			notes = "경로 예시) http://localhost:8080/cheerup/user/checkPassWord"
					+ "\n http method : post, 입력 데이터 : json { 'password' : 유저 비밀번호}"
					+ "\n return 예시) json { 'message' : 'success, fail'}")
	@PostMapping("/checkPassWord")
	public ResponseEntity<Map<String,Object>> checkPassword(@RequestBody UserDto userDto, HttpServletRequest request){
		
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
	    try {
	    	System.out.println(userService.userInfo(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")))).getPassword());
			if(!userService.userInfo(userService.getUserIdById(jwtService.getUserIdByJwt(request.getHeader("access-token")))).getPassword().equals(userDto.getPassword())) {
//				status = HttpStatus.INTERNAL_SERVER_ERROR;
				resultMap.put("message", FAIL);
			} else {
				resultMap.put("message", SUCCESS);
			}
		} catch (SQLException e) {
			logger.debug("패스워드 오류 : {}", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			resultMap.put("message", e.getMessage());
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "아이디 중복 확인(개인 정보 등록, 수정 시 이용)", 
			notes = "경로 예시) http://localhost:8080/cheerup/user/checkId?id=d"
					+ "\n http method : get"
					+ "\n return 예시) json { 'message' : 'success, fail'}")
	@GetMapping("/checkid")
	public ResponseEntity<Map<String,Object>> checkId(@RequestParam String id){
		System.out.println("checkid");
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
	    try {
			if(userService.userInfo(id) != null) {
//				status = HttpStatus.INTERNAL_SERVER_ERROR;
				resultMap.put("message", FAIL);
			} else {
				resultMap.put("message", SUCCESS);
			}
		} catch (SQLException e) {
			logger.debug("아이디 중복 에러 : {}", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			resultMap.put("message", e.getMessage());
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원 ID(String) 값으로 회원 리스트 검색", 
			notes = "경로 예시) http://localhost:8080/cheerup/user/searchById?id=d"
					+ "\n http method : get"
					+ "\n return 예시) json { 'userList' : 유저리스트}")
	@GetMapping("/searchById")
	public ResponseEntity<Map<String, Object>> getUserListById(@RequestParam String id) {
		System.out.println("id: "+id);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			resultMap.put("userList", userService.getUserListById(id));
			resultMap.put("message", SUCCESS);
		} catch (SQLException e) {			
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>( resultMap, status);
	}
	
	@ApiOperation(value = "회원 닉네임(String) 값으로 회원 리스트 검색", 
			notes = "경로 예시) http://localhost:8080/cheerup/user/searchByNickName?nickname=d"
					+ "\n http method : get"
					+ "\n return 예시) json { 'userList' : 유저리스트}")
	@GetMapping("/searchByNickName")
	public ResponseEntity<Map<String, Object>> getUserListByNickName(@RequestParam String nickname) {
		System.out.println("nickname: "+nickname);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			resultMap.put("userList", userService.getUserListByNickName(nickname));
			resultMap.put("message", SUCCESS);
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>( resultMap, status);
	}
	
	@ApiOperation(value = "회원 이메일(String) 값으로 회원 리스트 검색", 
			notes = "경로 예시) http://localhost:8080/cheerup/user/searchByEmail?email=d"
					+ "\n http method : get"
					+ "\n return 예시) json { 'userList' : 유저리스트}")
	@GetMapping("/searchByEmail")
	public ResponseEntity<Map<String, Object>> getUserListByEmail(@RequestParam String email) {
		System.out.println("email: "+email);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		
		try {
			resultMap.put("userList", userService.getUserListByEmail(email));
			resultMap.put("message", SUCCESS);
		} catch (SQLException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>( resultMap, status);
	}
}
