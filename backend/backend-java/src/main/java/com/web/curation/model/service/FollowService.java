package com.web.curation.model.service;

import java.sql.SQLException;
import java.util.List;

import com.web.curation.model.dto.UserDto;

public interface FollowService {

	public List<UserDto> getMyFollowingList(int userId) throws SQLException;
	public List<UserDto> getMyFollowerList(int userId) throws SQLException;
}
