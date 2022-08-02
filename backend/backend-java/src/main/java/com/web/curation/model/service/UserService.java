package com.web.curation.model.service;

import java.sql.SQLException;

import com.web.curation.model.dto.UserDto;

public interface UserService {

	public int registUser(UserDto userDto) throws SQLException;
	public UserDto login(UserDto userDto) throws SQLException;
}
