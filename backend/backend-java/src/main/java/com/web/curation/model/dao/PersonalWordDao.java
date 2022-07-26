package com.web.curation.model.dao;

import java.util.List;

import com.web.curation.model.dto.PersonalWordDto;

public interface PersonalWordDao {
	// 내 단어장에 추가
	void insertPersonalWord(PersonalWordDto personalWordDto);
	
	// 내 단어장에서 삭제
	void deletePersonalWord(int personalWordId);
	
	// 내 단어장 조회
	List<PersonalWordDto> selectPersonalWordList(int userId);
	
	// 즐겨찾기
	List<PersonalWordDto> selectPersonalFavWordList(int userId);
}
