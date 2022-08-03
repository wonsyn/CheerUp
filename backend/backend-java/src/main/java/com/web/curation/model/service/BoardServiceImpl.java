package com.web.curation.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.BoardDao;
import com.web.curation.model.dto.BoardDto;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDao boardDao;
	
	@Override
	public List<BoardDto> getBoardByUserId(int userId) {
		return boardDao.selectBoardByUserId(userId);
	}

	@Override
	public void createBoard(BoardDto boardDto) {
		boardDao.insertBoard(boardDto);
	}

	@Override
	public void editBoard(BoardDto boardDto) {
		BoardDto dto = boardDao.selectBoardByBoardId(boardDto.getBoardId());
		dto.setBoardId(boardDto.getBoardId());
		dto.setUserId(boardDto.getUserId());
		dto.setBoardName(boardDto.getBoardName());
		boardDao.updateBoard(dto);
	}

	@Override
	public void deleteBoard(int boardId) {
		boardDao.deleteBoard(boardId);
	}

	@Override
	public BoardDto isExistSameBoardName(BoardDto boardDto) throws SQLException {
		return boardDao.isExistSameBoardName(boardDto);
	}

}
