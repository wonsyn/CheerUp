package com.web.curation.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.UserScrapfeedMyfeedDao;
import com.web.curation.model.dto.UserScrapfeedMyfeedDto;

@Service
public class UserScrapfeedMyfeedServiceImpl implements UserScrapfeedMyfeedService{

	@Autowired
	private UserScrapfeedMyfeedDao userScrapfeedMyfeedDao;
	
	@Override
	public void addScrap(UserScrapfeedMyfeedDto userScrapfeedMyFeedDto) {
		userScrapfeedMyfeedDao.insertScrap(userScrapfeedMyFeedDto);
	}

	@Override
	public List<UserScrapfeedMyfeedDto> getMyScrap(int user_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
