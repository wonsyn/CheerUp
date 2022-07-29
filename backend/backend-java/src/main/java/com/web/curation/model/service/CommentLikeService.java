package com.web.curation.model.service;

import java.util.List;

import com.web.curation.model.dto.CommentLikeDto;

public interface CommentLikeService {
	// 좋아요 추가
	void addLike(CommentLikeDto likeDto);
	
	// 좋아요 취소
	void cancelLike(CommentLikeDto likeDto);
	
	// 댓글 별 좋아요 수 counting
	List<CommentLikeDto> getCommentLikeByCommentId(int commentId);
}
