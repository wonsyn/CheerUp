package com.web.curation.model.dao;

import com.web.curation.model.dto.CommentLikeDto;

public interface CommentLikeDao {
	// 댓글 좋아요 추가
	int insertLike(CommentLikeDto likeDto);
	
	// 댓글 좋아요 취소
	int deleteLike(CommentLikeDto likeDto);
	
	// 댓글 좋아요 여부 체크
	CommentLikeDto chkCommentLike(int commentId, int userId);
}
