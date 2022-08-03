package com.web.curation.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.FollowDao;
import com.web.curation.model.dto.UserDto;

@Service
public class FollowServiceImpl implements FollowService {

	@Autowired
	FollowDao followMapper;
	
	@Override
	public List<UserDto> getMyFollowingList(int userId) throws SQLException {
		return followMapper.getMyFollowingList(userId);
	}

	@Override
	public List<UserDto> getMyFollowerList(int userId) throws SQLException {
		return followMapper.getMyFollowerList(userId);
	}
}
