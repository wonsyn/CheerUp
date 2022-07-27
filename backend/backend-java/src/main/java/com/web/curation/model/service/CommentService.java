package com.web.curation.model.service;

import com.web.curation.model.dto.CommentDto;

public interface CommentService {
	// 댓글 작성
	void writeComment(CommentDto commentDto);
	
	// 댓글 삭제
	void deleteComment(int commentId);

}
