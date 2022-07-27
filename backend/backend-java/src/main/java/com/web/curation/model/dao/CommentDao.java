package com.web.curation.model.dao;

import java.util.List;

import com.web.curation.model.dto.CommentDto;

public interface CommentDao {
	// 해당 url 댓글 조회
	List<CommentDto> selectCommentByUrl(String url);
	
	// 댓글 작성
	void createComment(CommentDto commentDto);
	
	// 댓글 삭제
	void deleteComment(int commentId);
}
