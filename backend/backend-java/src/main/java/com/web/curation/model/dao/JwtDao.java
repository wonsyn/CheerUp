package com.web.curation.model.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.web.curation.model.dto.JwtDto;

@Mapper
public interface JwtDao {
	public int createRefreshToken(JwtDto tokenDto) throws SQLException;
	public int updateRefreshToken(JwtDto tokenDto) throws SQLException;
	public JwtDto tokenInfo(int userId) throws SQLException;
}
