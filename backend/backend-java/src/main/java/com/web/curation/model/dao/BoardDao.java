package com.web.curation.model.dao;

import java.util.List;

import com.web.curation.model.dto.BoardDto;

public interface BoardDao {
	// boardId로 불러오기
	BoardDto selectBoardByBoardId(int boardId);
	
	// 회원의 보드 모두 불러오기
	List<BoardDto> selectBoardByUserId(int userId);
	
	// 보드 추가
	void insertBoard(BoardDto boardDto);
	
	// 보드 수정
	void updateBoard(BoardDto boardDto);
	
	// 보드 삭제
	void deleteBoard(int boardId);
}
