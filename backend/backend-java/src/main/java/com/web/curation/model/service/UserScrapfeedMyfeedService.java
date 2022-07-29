package com.web.curation.model.service;

import java.util.List;

import com.web.curation.model.dto.UserScrapfeedMyfeedDto;

public interface UserScrapfeedMyfeedService {
	// 스크랩하기
	void addScrap(UserScrapfeedMyfeedDto userScrapfeedMyFeedDto);
	
	// 내가 스크랩한 것 보여주기
	List<UserScrapfeedMyfeedDto> getMyScrap(int user_id);
	
	// 내가 스크랩한 것 중 보드 별로 보여주기
	List<UserScrapfeedMyfeedDto> getMyScrapEachBoard(int user_id, int boardId);
}
