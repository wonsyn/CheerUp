package com.web.curation.model.service;

import com.web.curation.model.dto.PersonalWordDto;

public interface PersonalWordService {
	// 개인 단어장에 추가
	void createPersonalWord(PersonalWordDto personalWordDto);
	
	// 개인 단어장에서 삭제
	void deletePersonalWord(int personalWordId);
}
