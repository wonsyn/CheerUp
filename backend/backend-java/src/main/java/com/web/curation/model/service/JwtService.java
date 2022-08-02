package com.web.curation.model.service;

import java.sql.SQLException;
import java.util.Date;

import com.web.curation.model.dto.JwtDto;

public interface JwtService {

	<T> String createAccessToken(String key, T data, String subject);
	<T> String createRefreshToken(String key, T data, String subject);
//	Map<String, Object> get(String key);
//	boolean isUsableToken(String jwt);
//	boolean isUsableRefreshToken(String jwt);
	
	public int createRefreshToken(JwtDto tokenDto) throws SQLException;
	public int updateRefreshToken(JwtDto tokenDto) throws SQLException;
	public JwtDto tokenInfo(int userId) throws SQLException;
	public<T> String getRefreshToken(String key, T data, String subject, Date peroid);
	public String getUserIdByJwt(String jwt);
}
