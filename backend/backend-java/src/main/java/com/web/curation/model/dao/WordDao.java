package com.web.curation.model.dao;

import java.util.List;

import com.web.curation.model.dto.WordDto;

public interface WordDao {
	// 기본 단어장 불러오기 : DB연결 확인
	List<WordDto> selectWords();
	
	// id로 단어 조회
	WordDto selectWordById(int word_id);
}
