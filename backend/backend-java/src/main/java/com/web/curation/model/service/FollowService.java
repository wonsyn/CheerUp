package com.web.curation.model.service;

import java.sql.SQLException;
import java.util.List;

import com.web.curation.model.dto.UserDto;

public interface FollowService {

	public List<UserDto> getMyFollowList(int userId) throws SQLException;
}
