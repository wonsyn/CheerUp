package com.web.curation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.CommentLikeDto;
import com.web.curation.model.dto.PersonalWordDto;
import com.web.curation.model.dto.WordDto;
import com.web.curation.model.service.CommentLikeService;
import com.web.curation.model.service.PersonalWordService;
import com.web.curation.model.service.WordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "단어")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/word")
public class WordController {
	
	@Autowired
	private WordService wordService;
	
	@Autowired
	private PersonalWordService personalWordService;
	
	@ApiOperation(value="DB 내장 단어들 불러오기", 
			  notes="DB에 있는 기본 내장 단어들 불러오기 / 추후 기사 상세페이지에 사용할 수 있을 듯")
	@GetMapping("/listtest")
	public ResponseEntity<List<WordDto>> getWordList() {
		System.out.println(wordService.getWords());
		return new ResponseEntity<List<WordDto>>(wordService.getWords(), HttpStatus.OK);
	}
	
	@ApiOperation(value="단어 ID로 조회", 
			  notes="DB에 있는 기본 내장 단어들 ID로 하나 조회하기")
	@GetMapping("/{word_id}")
	public ResponseEntity<WordDto> getWordById(@PathVariable int word_id) {
		
		return new ResponseEntity<WordDto>(wordService.getWordById(word_id), HttpStatus.OK);
	}
	
	@ApiOperation(value="내 단어장에 추가", 
			  notes="기본 단어를 내 단어장에 추가 / 그냥 기본 단어 추가뿐만 아니라 수정 후 추가도 이 메서드 사용")
	@PostMapping("/create")
	public ResponseEntity<String> putWord(PersonalWordDto personalWordDto){
		System.out.println(personalWordDto);
		personalWordService.createPersonalWord(personalWordDto);
		return new ResponseEntity<String>("success", HttpStatus.CREATED);
	}
	
	@ApiOperation(value="내 단어장에서 삭제", 
			  notes="개인 단어장 고유 번호로 내 단어장에서 삭제")
	@DeleteMapping("/delete/{personalWordId}")
	public ResponseEntity<String> deleteWord(@PathVariable int personalWordId){
		personalWordService.deletePersonalWord(personalWordId);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@ApiOperation(value="내 단어장 조회", 
			  notes="회원 번호로 내가 추가한 단어들 모두 조회")
	@GetMapping("/myword/{userId}")
	public ResponseEntity<List<PersonalWordDto>> getPersonalWordList(@PathVariable int userId){
		return new ResponseEntity<List<PersonalWordDto>>(personalWordService.getPersonalWordList(userId), HttpStatus.OK);
	}
	
	@ApiOperation(value="단어 즐겨찾기 리스트 조회", 
			  notes="회원 번호로 내가 추가했던 단어들 중 즐겨찾기 된 단어들 조회")
	@GetMapping("/myword/fav/{userId}")
	public ResponseEntity<List<PersonalWordDto>> getPersonalFavWordList(@PathVariable int userId){
		return new ResponseEntity<List<PersonalWordDto>>(personalWordService.getPersonalFavWordList(userId), HttpStatus.OK);
	}
	
	@ApiOperation(value="단어 즐겨찾기 추가", 
			  notes="내가 추가했던 단어들 중 즐거찾기 추가")
	@PutMapping("/fav/add/{personalWordId}")
	public ResponseEntity<String> addPersonalFavWord(@PathVariable int personalWordId){
		personalWordService.addPersonalFavWord(personalWordId);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@ApiOperation(value="단어 즐겨찾기 해제", 
			  notes="내가 추가했던 단어들 중 즐거찾기 해제")
	@PutMapping("/fav/cancel/{personalWordId}")
	public ResponseEntity<String> calcelPersonalFavWord(@PathVariable int personalWordId){
		personalWordService.cancelPersonalFavWord(personalWordId);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@ApiOperation(value="내 단어장 안에서 수정", 
			  notes="추가할 때 수정하는 것이 아니고 내 개인 단어장에서 단어 수정")
	@PutMapping("/update")
	public ResponseEntity<String> editPersonalWord(@RequestBody PersonalWordDto personalWordDto){
		System.out.println(personalWordDto);
		System.out.println("before service");
		personalWordService.editPersonalWord(personalWordDto);
		return new ResponseEntity<String>("success", HttpStatus.CREATED);
	}

}
