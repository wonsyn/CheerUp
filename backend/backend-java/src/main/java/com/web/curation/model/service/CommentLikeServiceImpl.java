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
	public void addLike(CommentLikeDto likeDto) {
		likeDao.insertLike(likeDto);
	}

	@Override
	public void cancelLike(CommentLikeDto likeDto) {
		likeDao.deleteLike(likeDto);
	}

	@Override
	public List<CommentLikeDto> getCommentLikeByCommentId(int commentId) {
		return likeDao.selectCommentLikeList(commentId);
	}
	
	
}
