package com.web.curation.model.service;

import java.util.HashMap;
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
	public int createPersonalWord(PersonalWordDto personalWordDto) {
		return personalWordDao.insertPersonalWord(personalWordDto);
	}

	@Override
	public int deletePersonalWord(int personalWordId) {
		return personalWordDao.deletePersonalWord(personalWordId);
	}

	@Override
	public List<PersonalWordDto> getPersonalWordList(HashMap params) {
		return personalWordDao.selectPersonalWordList(params);
	}

	@Override
	public List<PersonalWordDto> getPersonalFavWordList(HashMap params) {
		return personalWordDao.selectPersonalFavWordList(params);
	}

	@Override
	public int addPersonalFavWord(int personalWordId) {
//		PersonalWordDto dto = personalWordDao.selectPersonalWordById(personalWordId);
//		System.out.println(dto);
		return personalWordDao.addPersonalFavWord(personalWordId);
	}

	@Override
	public int cancelPersonalFavWord(int personalWordId) {
		return personalWordDao.cancelPersonalFavWord(personalWordId);
	}

	@Override
	public int editPersonalWord(PersonalWordDto personalWordDto) {
		PersonalWordDto dto = personalWordDao.selectPersonalWordById(personalWordDto.getPersonalWordId());
		
		dto.setPersonalWordExp(personalWordDto.getPersonalWordExp());
		System.out.println("dto : " + dto);
		return personalWordDao.updatePersonalWord(dto);
		
	}

}
