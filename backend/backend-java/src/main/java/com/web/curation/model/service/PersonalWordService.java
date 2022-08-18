package com.web.curation.model.service;

import java.util.HashMap;
import java.util.List;

import com.web.curation.model.dto.PersonalWordDto;

public interface PersonalWordService {
	// 개인 단어장에 추가
	int createPersonalWord(PersonalWordDto personalWordDto);
	
	// 개인 단어장에서 삭제
	int deletePersonalWord(int personalWordId);
	
	// 개인 단어장 전체 조회
	List<PersonalWordDto> getPersonalWordList(HashMap params);
	
	// 개인 단어장 중 즐겨찾기 조회
	List<PersonalWordDto> getPersonalFavWordList(HashMap params);
	
	// 단어 즐겨찾기 설정
	// 단어 즐겨찾기 설정 gg
	int addPersonalFavWord(int personalWordId);
	
	// 단어 즐겨찾기 해제
	int cancelPersonalFavWord(int personalWordId);
	
	// 개인 단어장에서 수정
	int editPersonalWord(PersonalWordDto personalWordDto);
}
