package com.web.curation.model.service;

import java.util.List;

import com.web.curation.model.dto.CommentDto;

public interface CommentService {
	// url별 댓글 조회
	List<CommentDto> readCommentByUrl(String commentUrl);
	
	// 댓글 작성
	void writeComment(CommentDto commentDto);
	
	// 댓글 삭제
	void deleteComment(int commentId);
	
	// 댓글 수정
	void editComment(CommentDto commentDto);

}
