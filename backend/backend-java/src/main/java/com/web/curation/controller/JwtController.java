package com.web.curation.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.service.JwtService;

import io.swagger.annotations.Api;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@Api(tags = "토큰")
@RestController
@RequestMapping("/token")
public class JwtController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	JwtService jwtService;
	
	
	@GetMapping("/requestNewAccessToken")
	public ResponseEntity<Map<String, Object>> requestNewAccessToken(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
//		HttpStatus status = HttpStatus.ACCEPTED;
		
//		String id =  jwtService.getUserIdByJwt(request.getHeader("refresh-token"));		
//		String accessToken = jwtService.createAccessToken("id", id, "access-token");
		resultMap.put("access-token", request.getAttribute("accessToken"));
		resultMap.put("refresh-token", request.getAttribute("refreshToken"));
//		resultMap.put("access-token", accessToken);
		resultMap.put("message", SUCCESS);
		logger.debug("로그인 access 토큰정보 : {}", request.getAttribute("accessToken"));
		
		// refresh-token 유효 확인
		/*
		if(jwtService.isUsableRefreshToken(request.getHeader("refresh-token"))) {
			logger.info("사용 가능한 리프레시 토큰");
			try {
				
				// access-token 생성
				String id = jwtService.getUserId(request.getHeader("refresh-token"));
				String accessToken = jwtService.createAccessToken("id", id, "access-token");
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				logger.debug("로그인 access 토큰정보 : {}", accessToken);
			} catch (Exception e) {
				logger.error("정보 조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			
			// db refresh-token 유효기간 확인 후 재발급 or 로그인
			
			logger.error("사용 불가능한 리프레시 토큰");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		*/
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
}
