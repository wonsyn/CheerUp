package com.web.curation.model.dao;

import com.web.curation.model.dto.PersonalWordDto;

public interface PersonalWordDao {
	// 내 단어장에 추가
	void insertPersonalWord(PersonalWordDto personalWordDto);
	
	// 내 단어장에서 삭제
	void deletePersonalWord(int personalWordId);
}
