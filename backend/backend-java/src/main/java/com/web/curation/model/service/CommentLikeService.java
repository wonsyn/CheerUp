package com.web.curation.model.service;

import com.web.curation.model.dto.CommentLikeDto;

public interface CommentLikeService {
	// 좋아요 추가
	void addLike(CommentLikeDto likeDto);
}
