package com.web.curation.model.service;

import java.sql.SQLException;

import com.web.curation.model.dto.UserDto;

public interface UserService {

	public int registUser(UserDto userDto) throws SQLException;
	public UserDto login(UserDto userDto) throws SQLException;
	public UserDto userInfo(String id) throws SQLException;
	public int updateUser(UserDto userDto) throws SQLException;
	
	public int getUserIdById(String id) throws SQLException;
	
	public boolean isSameLoginUserAndRequestId(int userId, int inputId);
}
