package com.web.curation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.curation.model.dto.PersonalWordDto;
import com.web.curation.model.dto.WordDto;
import com.web.curation.model.service.PersonalWordService;
import com.web.curation.model.service.WordService;

@RestController
@RequestMapping("/word")
public class WordController {
	
	@Autowired
	private WordService wordService;
	
	@Autowired
	private PersonalWordService personalWordService;
	
	@GetMapping("/listtest")
	public ResponseEntity<List<WordDto>> getWordList() {
		System.out.println(wordService.getWords());
		return new ResponseEntity<List<WordDto>>(wordService.getWords(), HttpStatus.OK);
	}
	
	@GetMapping("/{word_id}")
	public ResponseEntity<WordDto> getWordById(@PathVariable int word_id) {
		
		return new ResponseEntity<WordDto>(wordService.getWordById(word_id), HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<String> putWord(PersonalWordDto personalWordDto){
		System.out.println(personalWordDto);
		personalWordService.createPersonalWord(personalWordDto);
		return new ResponseEntity<String>("success", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{personalWordId}")
	public ResponseEntity<String> deleteWord(@PathVariable int personalWordId){
		personalWordService.deletePersonalWord(personalWordId);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@GetMapping("/myword/{userId}")
	public ResponseEntity<List<PersonalWordDto>> getPersonalWordList(@PathVariable int userId){
		return new ResponseEntity<List<PersonalWordDto>>(personalWordService.getPersonalWordList(userId), HttpStatus.OK);
	}
	
	@GetMapping("/myword/fav/{userId}")
	public ResponseEntity<List<PersonalWordDto>> getPersonalFavWordList(@PathVariable int userId){
		return new ResponseEntity<List<PersonalWordDto>>(personalWordService.getPersonalFavWordList(userId), HttpStatus.OK);
	}
	
	@PutMapping("/fav/add/{personalWordId}")
	public ResponseEntity<String> addPersonalFavWord(@PathVariable int personalWordId){
		personalWordService.addPersonalFavWord(personalWordId);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@PutMapping("/fav/cancel/{personalWordId}")
	public ResponseEntity<String> calcelPersonalFavWord(@PathVariable int personalWordId){
		personalWordService.cancelPersonalFavWord(personalWordId);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> editPersonalWord(@RequestBody PersonalWordDto personalWordDto){
		System.out.println(personalWordDto);
		System.out.println("before service");
		personalWordService.editPersonalWord(personalWordDto);
		return new ResponseEntity<String>("success", HttpStatus.CREATED);
	}
}
