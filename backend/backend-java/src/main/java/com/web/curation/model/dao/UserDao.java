package com.web.curation.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.web.curation.model.dto.UserDto;

public interface UserDao {

	public int registUser(UserDto userDto) throws SQLException;
	public UserDto login(UserDto userDto) throws SQLException;
	public UserDto getUserInfoById(String id) throws SQLException;
	public UserDto getUserInfoByUserId(int userId) throws SQLException;
	public int updateUser(UserDto userDto) throws SQLException;
	public int deleteUser(String id) throws SQLException;
	public int getUserIdById(String id) throws SQLException;
	public List<UserDto> getUserListById(String id) throws SQLException;
	public List<UserDto> getUserListByNickName(String nickname) throws SQLException;
	public List<UserDto> getUserListByEmail(String email) throws SQLException;
}
