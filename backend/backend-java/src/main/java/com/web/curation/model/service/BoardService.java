package com.web.curation.model.service;

import java.util.List;

import com.web.curation.model.dto.BoardDto;

public interface BoardService {
	// 보드 가져오기
	List<BoardDto> getBoardByUserId(int userId);
	
	// 보드 추가
	void createBoard(BoardDto boardDto);
	
	// 보드 수정
	void editBoard(BoardDto boardDto);
	
	// 보드 삭제
	void deleteBoard(int boardId);
}
