package com.web.curation.model.service;

import java.sql.SQLException;
import java.util.List;

import com.web.curation.model.dto.BoardDto;

public interface BoardService {
	// 보드 가져오기
	List<BoardDto> getBoardByUserId(int userId);
	
	// 보드 추가
	int createBoard(BoardDto boardDto);
	
	// 보드 수정
	int editBoard(BoardDto boardDto);
	
	// 보드 삭제
	int deleteBoard(int boardId);
	
	// 보드 이름 중복 확인
	BoardDto isExistSameBoardName(BoardDto boardDto) throws SQLException;
}
