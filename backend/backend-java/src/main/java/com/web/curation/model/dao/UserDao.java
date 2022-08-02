package com.web.curation.model.dao;

import java.sql.SQLException;

import com.web.curation.model.dto.UserDto;

public interface UserDao {

	public int registUser(UserDto userDto) throws SQLException;
}
