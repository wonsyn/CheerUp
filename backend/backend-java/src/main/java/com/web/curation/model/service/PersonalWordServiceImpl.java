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

	@Override
	public void addPersonalFavWord(int personalWordId) {
//		PersonalWordDto dto = personalWordDao.selectPersonalWordById(personalWordId);
//		System.out.println(dto);
		personalWordDao.addPersonalFavWord(personalWordId);
	}

	@Override
	public void cancelPersonalFavWord(int personalWordId) {
		personalWordDao.cancelPersonalFavWord(personalWordId);
	}

	@Override
	public void editPersonalWord(PersonalWordDto personalWordDto) {
		PersonalWordDto dto = personalWordDao.selectPersonalWordById(personalWordDto.getPersonalWordId());
		dto.setPersonalWordId(personalWordDto.getPersonalWordId());
		dto.setUserId(personalWordDto.getUserId());
		dto.setPersonalWord(personalWordDto.getPersonalWord());
		dto.setPersonalWordExp(personalWordDto.getPersonalWordExp());
		dto.setPersonalFavWord(personalWordDto.getPersonalFavWord());
		dto.setPersonalWordDate(personalWordDto.getPersonalWordDate());
		System.out.println("dto : " + dto);
		personalWordDao.updatePersonalWord(dto);
		
	}

}
