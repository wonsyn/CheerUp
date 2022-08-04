package com.web.curation.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.FeedDao;
import com.web.curation.model.dto.FeedDto;

@Service
public class FeedServiceImpl implements FeedService{

	@Autowired
	private FeedDao feedDao;
	
	@Override
	public List<FeedDto> searchFeed(HashMap<String, String> params) {
		return feedDao.selectFeed(params);
	}

	@Override
	public FeedDto readFeedById(int feedId) {
		return feedDao.selectFeedById(feedId);
	}
	
	

}
