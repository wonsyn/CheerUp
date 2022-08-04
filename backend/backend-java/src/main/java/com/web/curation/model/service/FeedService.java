package com.web.curation.model.service;

import java.util.HashMap;
import java.util.List;

import com.web.curation.model.dto.FeedDto;

public interface FeedService {
	// feed 검색
	// ㅇㅇㅇㅇㅇㅇㅇ
	List<FeedDto> searchFeed(HashMap<String, String> params);
	
	FeedDto readFeedById(int feedId);
}
