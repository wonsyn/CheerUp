package com.web.curation.model.service;

import java.util.List;

import com.web.curation.model.dto.CommentLikeDto;

public interface CommentLikeService {
	// 좋아요 추가
	int addLike(CommentLikeDto likeDto);
	
	// 좋아요 취소
	int cancelLike(CommentLikeDto likeDto);
	
	// 좋아요 체크
	CommentLikeDto chkCommentLike(int commentId, int userId);
}
