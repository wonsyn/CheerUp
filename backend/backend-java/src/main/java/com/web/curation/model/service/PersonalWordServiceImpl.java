package com.web.curation.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.model.dao.PersonalWordDao;
import com.web.curation.model.dto.PersonalWordDto;

@Service
public class PersonalWordServiceImpl implements PersonalWordService{
	
	@Autowired
	PersonalWordDao personalWordDao;

	@Override
	public void createPersonalWord(PersonalWordDto personalWordDto) {
		personalWordDao.insertPersonalWord(personalWordDto);
	}

	@Override
	public void deletePersonalWord(int personalWordId) {
		personalWordDao.deletePersonalWord(personalWordId);
	}

	@Override
	public List<PersonalWordDto> getPersonalWordList(int userId) {
		return personalWordDao.selectPersonalWordList(userId);
	}

	@Override
	public List<PersonalWordDto> getPersonalFavWordList(int userId) {
		return personalWordDao.selectPersonalFavWordList(userId);
	}

}
