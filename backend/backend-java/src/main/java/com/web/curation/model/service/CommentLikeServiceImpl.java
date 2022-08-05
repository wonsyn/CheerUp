package com.web.curation.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.CommentLikeDao;
import com.web.curation.model.dto.CommentLikeDto;

@Service
public class CommentLikeServiceImpl implements CommentLikeService{
	
	@Autowired
	private CommentLikeDao likeDao;

	@Override
	public int addLike(CommentLikeDto likeDto) {
		return likeDao.insertLike(likeDto);
	}

	@Override
	public int cancelLike(CommentLikeDto likeDto) {
		return likeDao.deleteLike(likeDto);
	}

	@Override
	public CommentLikeDto chkCommentLike(int commentId, int userId) {
		return likeDao.chkCommentLike(commentId, userId);
	}
}
