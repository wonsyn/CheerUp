package com.web.curation.model.dao;

import com.web.curation.model.dto.PersonalWordDto;

public interface PersonalWordDao {
	// 내 단어장에 추가
	void insertPersonalWord(PersonalWordDto personalWordDto);
	
}
