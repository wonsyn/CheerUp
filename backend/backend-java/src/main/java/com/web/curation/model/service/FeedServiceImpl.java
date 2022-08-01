package com.web.curation.model.service;

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
	public List<FeedDto> searchFeed(int feedType) {
		return feedDao.selectFeed(feedType);
	}
	
	

}
