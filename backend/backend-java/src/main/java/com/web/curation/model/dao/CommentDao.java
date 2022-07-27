package com.web.curation.model.dao;

import com.web.curation.model.dto.CommentDto;

public interface CommentDao {
	// 댓글 작성
	void createComment(CommentDto commentDto);
	
	// 댓글 삭제
	void deleteComment(int commentId);
}
