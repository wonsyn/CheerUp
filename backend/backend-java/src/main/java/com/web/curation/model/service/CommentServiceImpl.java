package com.web.curation.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.CommentDao;
import com.web.curation.model.dto.CommentDto;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentDao commentDao;

	@Override
	public void writeComment(CommentDto commentDto) {
		commentDao.createComment(commentDto);
	}
}
