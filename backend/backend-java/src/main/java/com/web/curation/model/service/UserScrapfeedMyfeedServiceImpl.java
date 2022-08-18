package com.web.curation.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.UserScrapfeedMyfeedDao;
import com.web.curation.model.dto.UserScrapfeedMyfeedDto;
import com.web.curation.model.dto.UserScrapfeedMyfeedJoinDto;

@Service
public class UserScrapfeedMyfeedServiceImpl implements UserScrapfeedMyfeedService{

	@Autowired
	private UserScrapfeedMyfeedDao userScrapfeedMyfeedDao;
	
	@Override
	public int addScrap(UserScrapfeedMyfeedDto userScrapfeedMyFeedDto) {
		return userScrapfeedMyfeedDao.insertScrap(userScrapfeedMyFeedDto);
	}

	@Override
	public List<UserScrapfeedMyfeedJoinDto> getMyScrap(int user_id) {
		return userScrapfeedMyfeedDao.selectMyScrap(user_id);
	}

	@Override
	public List<UserScrapfeedMyfeedJoinDto> getMyScrapEachBoard(int userId, int boardId) {
		return userScrapfeedMyfeedDao.selectMyScrapEachBoard(userId, boardId);
	}

	@Override
	public int editScrapBoard(int myfeedId, int boardId) {
		return userScrapfeedMyfeedDao.updateScrap(myfeedId, boardId);
	}

	@Override
	public int cancelScrap(int myfeedId) {
		return userScrapfeedMyfeedDao.deleteScrap(myfeedId);
	}
}
