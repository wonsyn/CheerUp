package com.web.curation.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.web.curation.model.dto.UserDto;

public interface FollowDao {

	public List<UserDto> getMyFollowList(int userId) throws SQLException;
}
