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
	public int createBoard(BoardDto boardDto) {
		return boardDao.insertBoard(boardDto);
	}

	@Override
	public int editBoard(BoardDto boardDto) {
		BoardDto dto = boardDao.selectBoardByBoardId(boardDto.getBoardId());
		dto.setBoardName(boardDto.getBoardName());
		return boardDao.updateBoard(dto);
	}

	@Override
	public int deleteBoard(int boardId) {
		return boardDao.deleteBoard(boardId);
	}

	@Override
	public BoardDto isExistSameBoardName(BoardDto boardDto) throws SQLException {
		return boardDao.isExistSameBoardName(boardDto);
	}

}
