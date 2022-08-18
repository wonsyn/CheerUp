package com.web.curation.model.service;

import java.sql.SQLException;
import java.util.List;

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
		return userMapper.getUserInfoById(id);
	}
	
	@Override
	public UserDto userInfo(int userId) throws SQLException {
		return userMapper.getUserInfoByUserId(userId);
	}
	
	@Override
	public int updateUser(UserDto userDto) throws SQLException {
		return userMapper.updateUser(userDto);
	}
	
	@Override
	public int deleteUser(String id) throws SQLException {
		return userMapper.deleteUser(id);
	}
	
	@Override
	public List<UserDto> getUserListById(String id) throws SQLException {
		return userMapper.getUserListById(id);
	}
	
	@Override
	public List<UserDto> getUserListByNickName(String nickname) throws SQLException {
		return userMapper.getUserListByNickName(nickname);
	}
	
	@Override
	public List<UserDto> getUserListByEmail(String email) throws SQLException {
		return userMapper.getUserListByEmail(email);
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
