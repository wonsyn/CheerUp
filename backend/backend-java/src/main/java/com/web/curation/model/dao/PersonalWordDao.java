package com.web.curation.model.dao;

import java.util.List;

import com.web.curation.model.dto.PersonalWordDto;

public interface PersonalWordDao {
	// id로 단어 찾기
	PersonalWordDto selectPersonalWordById(int personalWordId);
	
	// 내 단어장에 추가 + 기사 상세 페이지에서 수정 후 추가
	void insertPersonalWord(PersonalWordDto personalWordDto);
	
	// 내 단어장에서 삭제
	void deletePersonalWord(int personalWordId);
	
	// 내 단어장 조회
	List<PersonalWordDto> selectPersonalWordList(int userId);
	
	// 즐겨찾기 된 단어들만 조회
	List<PersonalWordDto> selectPersonalFavWordList(int userId);
	
	// 단어 즐겨찾기 설정
	void addPersonalFavWord(int personalWordId);
	
	// 단어 즐겨찾기 해제
	void cancelPersonalFavWord(int personalWordId);
	
	// 내 단어장안에서 수정
	void updatePersonalWord(PersonalWordDto personalWordDto);
	
	
}
