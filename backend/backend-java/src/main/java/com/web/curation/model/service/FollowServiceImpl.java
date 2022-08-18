package com.web.curation.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.FollowDao;
import com.web.curation.model.dto.FollowDto;
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
	
	@Override
	public FollowDto getFollowStatus(FollowDto followDto) throws SQLException {
		return followMapper.getFollowStatus(followDto);
	}
	
	@Override
	public int followUser(FollowDto followDto) throws SQLException {
		return followMapper.followUser(followDto);
	}
	
	@Override
	public int unFollowUser(FollowDto followDto) throws SQLException {
		return followMapper.unFollowUser(followDto);
	}
}
