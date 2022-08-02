package com.web.curation.model.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.UserDao;
import com.web.curation.model.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public int registUser(UserDto userDto) throws SQLException {
		return userDao.registUser(userDto);
	}
}
