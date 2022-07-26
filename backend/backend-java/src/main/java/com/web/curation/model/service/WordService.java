package com.web.curation.model.service;

import java.util.List;

import com.web.curation.model.dto.WordDto;

public interface WordService {
	// 기본 단어장 불러오기 : DB연결 확인
	List<WordDto> getWords();
	
	WordDto getWordById(int word_id);
}
