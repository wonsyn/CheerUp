package com.web.curation.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.CommentLikeDao;
import com.web.curation.model.dto.CommentLikeDto;

@Service
public class CommentLikeServiceImpl implements CommentLikeService{
	
	@Autowired
	private CommentLikeDao likeDao;

	@Override
	public void addLike(CommentLikeDto likeDto) {
		likeDao.insertLike(likeDto);
	}

	@Override
	public void cancelLike(CommentLikeDto likeDto) {
		likeDao.deleteLike(likeDto);
	}
	
	
}
