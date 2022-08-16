package com.web.curation.model.dao;

import java.util.List;

import com.web.curation.model.dto.UserScrapfeedMyfeedDto;
import com.web.curation.model.dto.UserScrapfeedMyfeedJoinDto;

public interface UserScrapfeedMyfeedDao {
	// 스크랩하기
	int insertScrap(UserScrapfeedMyfeedDto userScrapfeedMyFeedDto);
	
	// 내가 스크랩한 것 보여주기
	List<UserScrapfeedMyfeedJoinDto> selectMyScrap(int user_id);
	
	// 내가 스크랩한 것 중 보드 별로 보여주기
	List<UserScrapfeedMyfeedJoinDto> selectMyScrapEachBoard(int userId, int boardId);
	
	// 스크랩 보드 수정
	int updateScrap(int myfeedId, int boardId);
	
	// 스크랩 취소
	int deleteScrap(int myfeedId);
}
