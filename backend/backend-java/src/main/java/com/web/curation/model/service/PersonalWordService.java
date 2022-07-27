package com.web.curation.model.service;

import java.util.List;

import com.web.curation.model.dto.PersonalWordDto;

public interface PersonalWordService {
	// 개인 단어장에 추가
	void createPersonalWord(PersonalWordDto personalWordDto);
	
	// 개인 단어장에서 삭제
	void deletePersonalWord(int personalWordId);
	
	// 개인 단어장 전체 조회
	List<PersonalWordDto> getPersonalWordList(int userId);
	
	// 개인 단어장 중 즐겨찾기 조회
	List<PersonalWordDto> getPersonalFavWordList(int userId);
	
	// 단어 즐겨찾기 설정 gg
	void addPersonalFavWord(int personalWordId);
	
	// 단어 즐겨찾기 해제
	void cancelPersonalFavWord(int personalWordId);
	
	// 개인 단어장에서 수정
	void editPersonalWord(PersonalWordDto personalWordDto);
}
