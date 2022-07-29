package com.web.curation.model.dao;

import java.util.List;

import com.web.curation.model.dto.UserScrapfeedMyfeedDto;

public interface UserScrapfeedMyfeedDao {
	// 스크랩하기
	void insertScrap(UserScrapfeedMyfeedDto userScrapfeedMyFeedDto);
	
	// 내가 스크랩한 것 보여주기
	List<UserScrapfeedMyfeedDto> selectMyScrap(int user_id);
	
	// 내가 스크랩한 것 중 보드 별로 보여주기
	List<UserScrapfeedMyfeedDto> selectMyScrapEachBoard(int userId, int boardId);
}
