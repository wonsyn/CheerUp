package com.web.curation.model.dao;

import java.util.List;

import com.web.curation.model.dto.CommentDto;

public interface CommentDao {
	// comment_id로 조회
	CommentDto selectCommentById(int commentId);
	
	// 해당 url 댓글 조회
	List<CommentDto> selectCommentByUrl(String commentUrl);
	
	// 댓글 작성
	void createComment(CommentDto commentDto);
	
	// 댓글 삭제
	void deleteComment(int commentId);
	
	// 댓글 수정
	void updateComment(CommentDto commentDto);
}
