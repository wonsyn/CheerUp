package com.web.curation.model.service;

import java.sql.SQLException;
import java.util.List;

import com.web.curation.model.dto.CommentDto;

public interface CommentService {
	// feedId로 댓글 조회
	List<CommentDto> readCommentByFeedId(int feedId);
	
	// 댓글 작성
	int writeComment(CommentDto commentDto) throws SQLException; 
	
	// 댓글 삭제
	void deleteComment(int commentId);
	
	// 댓글 수정
	void editComment(CommentDto commentDto);

}
