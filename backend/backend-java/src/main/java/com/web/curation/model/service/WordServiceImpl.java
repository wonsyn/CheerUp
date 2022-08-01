package com.web.curation.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.WordDao;
import com.web.curation.model.dto.WordDto;

@Service
public class WordServiceImpl implements WordService {
	
	@Autowired
	private WordDao wordDao;
	
	// 기본 전체 단어장 가져오기
	@Override
	public List<WordDto> getWords() {
		return wordDao.selectWords();
	}
	
	// 기본 단어장 id로 불러오기
	@Override
	public WordDto getWordById(int word_id) {
		return wordDao.selectWordById(word_id);
	}
}
