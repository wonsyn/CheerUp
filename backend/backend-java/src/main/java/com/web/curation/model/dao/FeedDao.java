package com.web.curation.model.dao;

import java.util.HashMap;
import java.util.List;

import com.web.curation.model.dto.FeedDto;

public interface FeedDao {
	
	// feed 검색
	List<FeedDto> selectFeed(HashMap<String, String> params);
	
	// feed 상세
	FeedDto selectFeedById(int feedId);
}
