package com.web.curation.model.dao;

import java.util.HashMap;
import java.util.List;

import com.web.curation.model.dto.PersonalWordDto;

public interface PersonalWordDao {
	// 내 단어장에 추가
	// id로 단어 찾기
	PersonalWordDto selectPersonalWordById(int personalWordId);
	
	// 내 단어장에 추가 + 기사 상세 페이지에서 수정 후 추가
	int insertPersonalWord(PersonalWordDto personalWordDto);
	
	// 내 단어장에서 삭제
	int deletePersonalWord(int personalWordId);
	
	// 내 단어장 조회
	List<PersonalWordDto> selectPersonalWordList(HashMap params);
	
	// 즐겨찾기
	List<PersonalWordDto> selectPersonalFavWordList(HashMap params);
//	
//	// 즐겨찾기 된 단어들만 조회
//	List<PersonalWordDto> selectPersonalFavWordList(int userId);
	
	// 단어 즐겨찾기 설정
	int addPersonalFavWord(int personalWordId);
	
	// 단어 즐겨찾기 해제
	int cancelPersonalFavWord(int personalWordId);
	
	// 내 단어장안에서 수정
	int updatePersonalWord(PersonalWordDto personalWordDto);
	
	
}
