package com.web.curation.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.CommentDao;
import com.web.curation.model.dto.CommentDto;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentDao commentDao;
	
	@Override
	public List<CommentDto> readCommentByFeedId(int feedId) {
		return commentDao.selectCommentByFeedId(feedId);
	}

	@Override
	public int writeComment(CommentDto commentDto)  throws SQLException{
		return commentDao.createComment(commentDto);
	}

	@Override
	public void deleteComment(int commentId) {
		commentDao.deleteComment(commentId);
	}

	@Override
	public void editComment(CommentDto commentDto) {
		System.out.println(commentDto.getCommentId());
		CommentDto dto = commentDao.selectCommentById(commentDto.getCommentId());
		dto.setCommentId(commentDto.getCommentId());
		dto.setFeedId(commentDto.getFeedId());
		dto.setCommentContent(commentDto.getCommentContent());
		dto.setId(commentDto.getId());
		dto.setCommentDate(commentDto.getCommentDate());
		
		commentDao.updateComment(dto);
	}
}
