package com.web.curation.model.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.JwtDao;
//import com.web.curation.model.dao.JwtDao;
import com.web.curation.model.dto.JwtDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {

	public static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

	private static final String SALT = "ssafySecret";
	private static final int EXPIRE_MINUTES = 60;
	
	@Autowired
	JwtDao jwtMapper;

	@Override
	public <T> String createAccessToken(String key, T data, String subject) {
		String jwt = Jwts.builder().
						setHeaderParam("typ", "JWT").
						setHeaderParam("regDate", System.currentTimeMillis())
//						.setExpiration(new Date(System.currentTimeMillis() + 1000 * 20 * EXPIRE_MINUTES))
						.setExpiration(new Date(System.currentTimeMillis() + 1000 * 120 * EXPIRE_MINUTES))
						.setSubject(subject)
						.claim(key, data).signWith(SignatureAlgorithm.HS256, this.generateKey())
						.compact();
		return jwt;
	}
	
	@Override
	public <T> String createRefreshToken(String key, T data, String subject) {
		String jwt = Jwts.builder()
//				.setHeaderParam("typ", "JWT")
//				.setHeaderParam("regDate", System.currentTimeMillis())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 1800 * EXPIRE_MINUTES))
				.setSubject(subject)
				.claim(key, data).signWith(SignatureAlgorithm.HS256, this.generateKey())
				.compact();
		
		return jwt;
	}
	
	@Override
	public<T> String getRefreshToken(String key, T data, String subject, Date peroid) {
		
		String jwt = Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setHeaderParam("regDate", System.currentTimeMillis())
				.setExpiration(peroid)
				.setSubject(subject)
				.claim(key, data).signWith(SignatureAlgorithm.HS256, this.generateKey())
				.compact();
		
		return jwt;
	}
	

	private byte[] generateKey() {
		byte[] key = null;
		try {
			key = SALT.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if (logger.isInfoEnabled()) {
				e.printStackTrace();
			} else {
				logger.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}

		return key;
	}

	
//	전달 받은 토큰이 제대로 생성된것인지 확인 하고 문제가 있다면 UnauthorizedException을 발생.
	/*
	@Override
	public boolean isUsableToken(String jwt) {
		try {
			System.out.println(jwt);
			Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
			
			System.out.println("jwtserviceimpl, isUsableToken");
			System.out.println(claims);
			return true;
		} catch (Exception e) {
//			if (logger.isInfoEnabled()) {
//				e.printStackTrace();
//			} else {
			logger.error(e.getMessage());
//			}
//			throw new UnauthorizedException();
//			개발환경
			return false;
		}
	}
	*/
	/*
	@Override
	public boolean isUsableRefreshToken(String jwt) {
		try {
			System.out.println(jwt);
			Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
			
			System.out.println("jwtserviceimpl, isUsableRefreshToken");
			System.out.println(claims);
//			System.out.println(getUserId());
			return true;
		} catch (Exception e) {
//			if (logger.isInfoEnabled()) {
//				e.printStackTrace();
//			} else {
			logger.error(e.getMessage());
//			}
//			throw new UnauthorizedException();
//			개발환경
			return false;
		}
	}
*/
/*
	@Override
	public Map<String, Object> get(String key) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String jwt = request.getHeader("access-token");
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(jwt);
		} catch (Exception e) {
//			if (logger.isInfoEnabled()) {
//				e.printStackTrace();
//			} else {
			logger.error(e.getMessage());
//			}
			throw new UnauthorizedException();
//			개발환경
//			Map<String,Object> testMap = new HashMap<>();
//			testMap.put("userid", userid);
//			return testMap;
		}
		Map<String, Object> value = claims.getBody();
		logger.info("value : {}", value);
		return value;
	}
	*/
	
	@Override
	public String getUserIdByJwt(String jwt) {
		return (String) Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt).getBody().get("id");
	}

	@Override
	public int createRefreshToken(JwtDto tokenDto) throws SQLException {
		return jwtMapper.createRefreshToken(tokenDto);
	}
	
	@Override
	public int updateRefreshToken(JwtDto tokenDto) throws SQLException {
		return jwtMapper.updateRefreshToken(tokenDto);
	}

	@Override
	public JwtDto tokenInfo(int userId) throws SQLException {
		return jwtMapper.tokenInfo(userId);
	}



}
