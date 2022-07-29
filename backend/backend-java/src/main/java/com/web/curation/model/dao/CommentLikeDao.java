package com.web.curation.model.dao;

import java.util.List;

import com.web.curation.model.dto.CommentLikeDto;

public interface CommentLikeDao {
	// 댓글 좋아요 추가
	void insertLike(CommentLikeDto likeDto);
	
	// 댓글 좋아요 취소
	void deleteLike(CommentLikeDto likeDto);
	
	// 댓글 별 좋아요 수 counting => 댓글 id로 필터링 후 모두 보내서 front에서 카운팅 해야할듯?
	List<CommentLikeDto> selectCommentLikeList(int commentId);

}
