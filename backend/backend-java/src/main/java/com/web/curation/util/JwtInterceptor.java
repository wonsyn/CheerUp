package com.web.curation.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

import com.web.curation.model.dto.JwtDto;
import com.web.curation.model.service.JwtService;
import com.web.curation.model.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.swagger.models.HttpMethod;

//import com.web.curation.ssafit.util.JWTUtil;


@Component
public class JwtInterceptor implements HandlerInterceptor{
	/*
    private static final String HEADER_AUTH = "access-token"; // header에 넣을 때 저거 토큰을 넣어야 인증 됨
    
    @Autowired
    private JwtUtil jwtUtil; // 토큰 검증용

    //preHandle : 이동하기 전에
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        
        // 사용자가 요청시 헤더에 보내준 토큰 가져오기
        final String token = request.getHeader(HEADER_AUTH);
        System.out.println("token : " + token);
        if(token != null) { // null이면 잘못된 토큰
            jwtUtil.valid(token); // 검증 - 예외 없이 실행되면 정상
            return true; // 유효하면 다음
        }
        throw new Exception("토큰이 유효하지 않습니다.");
    }
    */
	
	public static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
	
	private static final String HEADER_AUTH = "auth-token";
	private static final String SALT = "ssafySecret";
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService ;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
//		System.out.println("prehandle");
//		System.out.println(request.getMethod().equals("OPTIONS"));
		if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
		
		
		request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		
		// 1. access-token -> 요청
		String accessToken = request.getHeader("access-token");
		// 2. access-token + refresh-token -> 갱신
		String refreshToken = request.getHeader("refresh-token");
		
		
//		System.out.println("access: "+ accessToken);
//		System.out.println("refresh: "+ refreshToken);
		
		String jwt;
		
		Jws<Claims> claims = null;
		
		if(accessToken != null && refreshToken == null) {
//			System.out.println("기능 수행 요청");

//			jwt = jwtService.getUserIdByJwt(accessToken);
//			System.out.println("jwt: "+ jwt);
//			if(jwtService.isUsableToken(jwtService.getUserIdByJwt(accessToken))) return true;
//			return false;
			
			
			try {
							
				claims = Jwts.parser().setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(accessToken);
				
//				System.out.println("claims: "+claims);
				Map<String, Object> value = claims.getBody();
				logger.info("value : {}", value);
//				System.out.println("value: "+value);
				return true;
			} catch (Exception e) {
//				if (logger.isInfoEnabled()) {
//					e.printStackTrace();
//				} else {
				logger.error(e.getMessage());
//				}
//				throw new UnauthorizedException();
				response.getWriter().write("{\"message\" : \"fail\"}");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setStatus(401);
				return false;
//				개발환경
//				Map<String,Object> testMap = new HashMap<>();
//				testMap.put("userid", userid);
			}
			
		}
		else if(accessToken != null && refreshToken != null) {
//			System.out.println("access 갱신");
			/*
			jwt = jwtService.getUserIdByJwt(refreshToken);
			System.out.println("jwt: "+ jwt);
			// TODO 리프레시 갱신 추가 필요
			int userId = userService.getUserIdById(jwt);
			System.out.println("userId: "+userId);
			
			
			// 리프레시 유효
			if(jwtService.isUsableToken(jwtService.getUserIdByJwt(refreshToken))) {
				
				JwtDto token = jwtService.tokenInfo(userId);
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");					
				cal.add(Calendar.DATE, 3);
				System.out.println("DB 날짜: "+sdf.parse(token.getRefreshTokenPeriod()));
				
				// 저장 날짜 < 기준 날짜 => 리프레시 토큰 발급, 음수 나오면 발급
				if(token.getRefreshTokenPeriod().compareTo(sdf.format(cal.getTime())) < 0) {
					JwtDto newRefreshToken = new JwtDto(token.getTokenId(), userId, ""); 
					jwtService.updateRefreshToken(newRefreshToken);
					refreshToken = jwtService.createAccessToken("id", userId, "refresh-token");
					System.out.println("리프레시 토큰 업데이트: " + newRefreshToken);
					logger.debug("로그인 refresh 토큰정보 : {}", newRefreshToken);
				}
				// 리프레시 토큰의 유효 기간이 충분하다면
				else {
					refreshToken = jwtService.getRefreshToken("id", userId, "refresh-token", sdf.parse(token.getRefreshTokenPeriod()));
				}
				
				return true;
			}
			// 리프레시 만료
			else {
				response.getWriter().write("{\"message\" : \"fail\"}");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setStatus(401);
				return false;
			}
			*/
			
			try {
				claims = Jwts.parser().setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(refreshToken);
				Map<String, Object> value = claims.getBody();
				logger.info("value : {}", value);
				
				accessToken = jwtService.createAccessToken("id", claims.getBody().getId(), "access-token");
				request.setAttribute("accessToken", accessToken);
				int userId = (int)claims.getBody().get("id");
				JwtDto token = jwtService.tokenInfo(userId);
				
				// 리프레시 재발급 기준 날짜 설정 ( 오늘 + 3일 )
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");					
				cal.add(Calendar.DATE, 3);
				
				// 저장 날짜 < 기준 날짜 => 리프레시 토큰 발급, 음수 나오면 발급
				if(token.getRefreshTokenPeriod().compareTo(sdf.format(cal.getTime())) < 0) {
					JwtDto newRefreshToken = new JwtDto(token.getTokenId(), userId, ""); 
					jwtService.updateRefreshToken(newRefreshToken);
					refreshToken = jwtService.createAccessToken("id", userId, "refresh-token");
//					System.out.println("리프레시 토큰 업데이트: " + newRefreshToken);
					logger.debug("로그인 refresh 토큰정보 : {}", newRefreshToken);
				}
				// 리프레시 토큰의 유효 기간이 충분하다면
				else {
					refreshToken = jwtService.getRefreshToken("id", userId, "refresh-token", sdf.parse(token.getRefreshTokenPeriod()));
				}
				
				request.setAttribute("refreshToken", refreshToken);
				
				return true;
			} catch (Exception e) {
//				if (logger.isInfoEnabled()) {
//				e.printStackTrace();
//			} else {
				logger.error(e.getMessage());
				response.getWriter().write("{\"message\" : \"fail\"}");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.setStatus(401);
//			}
				return false;
//				throw new UnauthorizedException();
//			개발환경
//			Map<String,Object> testMap = new HashMap<>();
//			testMap.put("userid", userid);
			}
			
			
//		}
		
//		Map<String, Object> value = claims.getBody();
//		logger.info("value : {}", value);
//		System.out.println("value: "+value);
//		return true;

	}
	response.getWriter().write("{\"message\" : \"fail\"}");
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setStatus(401);		
	return false;
	
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		final String token = request.getHeader(HEADER_AUTH);
//		System.out.println("jwtinterceptor");
//		if(token != null && jwtService.isUsableAccessToken(token)){
//			logger.info("토큰 사용 가능 : {}", token);
//			return true;
//		}else{
//			logger.info("토큰 사용 불가능 : {}", token);
//			throw new UnauthorizedException();
//		}
//
//	}
	}
}
