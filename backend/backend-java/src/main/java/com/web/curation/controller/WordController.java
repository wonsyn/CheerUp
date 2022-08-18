package com.web.curation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.PersonalWordDto;
import com.web.curation.model.dto.WordDto;
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
//		System.out.println(wordService.getWords());
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
	public ResponseEntity<Map<String, Object>> putWord(@RequestBody PersonalWordDto personalWordDto){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int result = personalWordService.createPersonalWord(personalWordDto);
			if(result == 1) {
				resultMap.put("message", "success");
				return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
			}else {
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e) {
			resultMap.put("message", "fail");
			return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value="내 단어장에서 삭제", 
			  notes="개인 단어장 고유 번호로 내 단어장에서 삭제")
	@DeleteMapping("/delete/{personalWordId}")
	public ResponseEntity<Map<String, Object>> deleteWord(@PathVariable int personalWordId){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int result = personalWordService.deletePersonalWord(personalWordId);
			if(result == 1) {
				resultMap.put("message", "success");
				return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
			}else {
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e) {
			resultMap.put("message", "fail");
			return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value="내 단어장 조회", 
			  notes="회원 번호로 내가 추가한 단어들 모두 조회 \n requestParam으로 검색 값 입력 \n ex) /myword/{userId}?keyword={검색값} - 검색값 없으면 기본 GET")
	@GetMapping("/myword/{userId}")
	public ResponseEntity<List<PersonalWordDto>> getPersonalWordList(@PathVariable int userId, 
																	 @RequestParam(defaultValue = "") String keyword){
		HashMap<String, String> params = new HashMap<>();
		params.put("userId", Integer.toString(userId));
		params.put("keyword", keyword);
		System.out.println(keyword);
		return new ResponseEntity<List<PersonalWordDto>>(personalWordService.getPersonalWordList(params), HttpStatus.OK);
	}
	
	@ApiOperation(value="단어 즐겨찾기 리스트 조회", 
			  notes="회원 번호로 내가 추가했던 단어들 중 즐겨찾기 된 단어들 조회 \\n requestParam으로 검색 값 입력 \\n ex) /myword/{userId}?keyword={검색값} - 검색값 없으면 기본 GET")
	@GetMapping("/myword/fav/{userId}")
	public ResponseEntity<List<PersonalWordDto>> getPersonalFavWordList(@PathVariable int userId,
			 															@RequestParam(defaultValue = "") String keyword){
		
		HashMap<String, String> params = new HashMap<>();
		params.put("userId", Integer.toString(userId));
		params.put("keyword", keyword);
		return new ResponseEntity<List<PersonalWordDto>>(personalWordService.getPersonalFavWordList(params), HttpStatus.OK);
	}
	
	@ApiOperation(value="단어 즐겨찾기 추가", 
			  notes="내가 추가했던 단어들 중 즐거찾기 추가")
	@PutMapping("/fav/add/{personalWordId}")
	public ResponseEntity<Map<String, Object>> addPersonalFavWord(@PathVariable int personalWordId){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int result = personalWordService.addPersonalFavWord(personalWordId);
			if(result == 1) {
				resultMap.put("message", "success");
				return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
			}else {
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e) {
			resultMap.put("message", "fail");
			return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value="단어 즐겨찾기 해제", 
			  notes="내가 추가했던 단어들 중 즐거찾기 해제")
	@PutMapping("/fav/cancel/{personalWordId}")
	public ResponseEntity<Map<String, Object>> cancelPersonalFavWord(@PathVariable int personalWordId){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int result = personalWordService.cancelPersonalFavWord(personalWordId);
			if(result == 1) {
				resultMap.put("message", "success");
				return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
			}else {
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e) {
			resultMap.put("message", "fail");
			return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value="내 단어장 안에서 수정", 
			  notes="추가할 때 수정하는 것이 아니고 내 개인 단어장에서 단어 수정 \n personalWordDtoId와 personalWordExp만 주면 됨")
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> editPersonalWord(@RequestBody PersonalWordDto personalWordDto){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			System.out.println(personalWordDto);
			int result = personalWordService.editPersonalWord(personalWordDto);
			if(result == 1) {
				resultMap.put("message", "success");
				return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
			}else {
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e) {
			resultMap.put("message", "fail");
			return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
