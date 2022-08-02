package com.web.curation.model.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.UserDao;
import com.web.curation.model.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userMapper;
	
	@Override
	public int registUser(UserDto userDto) throws SQLException {
		return userMapper.registUser(userDto);
	}
	
	@Override
	public UserDto login(UserDto userDto) throws SQLException {
		if(userDto.getId() == null || userDto.getPassword() == null)
			return null;
		return userMapper.login(userDto);
	}
	
	@Override
	public UserDto userInfo(String id) throws SQLException {
		return userMapper.userInfo(id);
	}
	
	@Override
	public int updateUser(UserDto userDto) throws SQLException {
		return userMapper.updateUser(userDto);
	}
	
	@Override
	public int getUserIdById(String id) throws SQLException {
		return userMapper.getUserIdById(id);
	}
	
	@Override
	public boolean isSameLoginUserAndRequestId(int userId, int inputId) {
		return userId == inputId;
	}
}
