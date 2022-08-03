package com.web.curation.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.web.curation.model.dto.CommentDto;

public interface CommentDao {
	// feed_id로 조회
	CommentDto selectCommentById(int commentId);
	
	// 해당 url 댓글 조회
	List<CommentDto> selectCommentByFeedId(int feedId);
	
	// 댓글 작성
	int createComment(CommentDto commentDto);
	
	// 댓글 삭제
	int deleteComment(int commentId);
	
	// 댓글 수정
	int updateComment(CommentDto commentDto);
}
