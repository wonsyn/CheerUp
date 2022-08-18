package com.web.curation.model.service;

import java.util.List;

import com.web.curation.model.dto.UserScrapfeedMyfeedDto;
import com.web.curation.model.dto.UserScrapfeedMyfeedJoinDto;

public interface UserScrapfeedMyfeedService {
	// 스크랩하기
	int addScrap(UserScrapfeedMyfeedDto userScrapfeedMyFeedDto);
	
	// 내가 스크랩한 것 보여주기
	List<UserScrapfeedMyfeedJoinDto> getMyScrap(int user_id);
	
	// 내가 스크랩한 것 중 보드 별로 보여주기
	List<UserScrapfeedMyfeedJoinDto> getMyScrapEachBoard(int userId, int boardId);
	
	// 스크랩 된 것 board수정
	int editScrapBoard(int myfeedId, int boardId);
	
	// 스크랩 취소
	int cancelScrap(int myfeedId);
}
