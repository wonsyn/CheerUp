package com.web.curation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.BoardDto;
import com.web.curation.model.dto.FeedDto;
import com.web.curation.model.dto.UserScrapfeedMyfeedDto;
import com.web.curation.model.service.BoardService;
import com.web.curation.model.service.FeedService;
import com.web.curation.model.service.UserScrapfeedMyfeedService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags = "메인피드/개인피드/보드")
@RestController
@RequestMapping("/feed")
public class FeedController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UserScrapfeedMyfeedService userScrapfeedMyfeedService;
	
	@Autowired
	private FeedService feedService;
	
	@ApiOperation(value="유저별 생성 board 불러오기", 
			  notes="userId로 유저가 생성한 board들 불러오기")
	@GetMapping("/board/list/{userId}")
	public ResponseEntity<List<BoardDto>> getBoardByUserId(@PathVariable int userId){
		
		return new ResponseEntity<List<BoardDto>>(boardService.getBoardByUserId(userId), HttpStatus.OK);
	}
	
	@ApiOperation(value="board 생성", 
			  notes="유저 board 생성")
	@PostMapping("/board/create")
	public ResponseEntity<String> createBoard(@RequestBody BoardDto boardDto){
		boardService.createBoard(boardDto);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@ApiOperation(value="board 수정", 
			  notes="유저 board 이름 수정")
	@PutMapping("/board/update")
	public ResponseEntity<String> editBoard(@RequestBody BoardDto boardDto){
		System.out.println(boardDto);
		boardService.editBoard(boardDto);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@ApiOperation(value="board 삭제", 
			  notes="유저 board 삭제")
	@DeleteMapping("/board/delete/{boardId}")
	public ResponseEntity<String> deleteBoard(@PathVariable int boardId){
		boardService.deleteBoard(boardId);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@ApiOperation(value="스크랩하기", 
			  notes="피드 스크랩하기")
	@PostMapping("/scrap/create")
	public ResponseEntity<String> createScrap(@RequestBody UserScrapfeedMyfeedDto dto){
		System.out.println(dto);
		userScrapfeedMyfeedService.addScrap(dto);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@ApiOperation(value="내가 스크랩 한 피드 보기", 
			  notes="userId로 내가 스크랩 한 피드 모두 보기")
	@GetMapping("/scrap/{userId}")
	public ResponseEntity<List<UserScrapfeedMyfeedDto>> getMyFeed(@PathVariable int userId){
		return new ResponseEntity<List<UserScrapfeedMyfeedDto>>(userScrapfeedMyfeedService.getMyScrap(userId), HttpStatus.OK);
	}
	
	@ApiOperation(value="보드 별 내가 스크랩 한 피드 보기", 
			  notes="userId와 boardId로 내가 스크랩 한 피드 보기")
	@GetMapping("/scrap/{userId}/{boardId}")
	public ResponseEntity<List<UserScrapfeedMyfeedDto>> getMyFeedEachBoard(@PathVariable int userId, @PathVariable int boardId){
		System.out.println(userId);
		return new ResponseEntity<List<UserScrapfeedMyfeedDto>>(userScrapfeedMyfeedService.getMyScrapEachBoard(userId, boardId), HttpStatus.OK);
	}
	
	@ApiOperation(value="피드 타입 별로 조회", 
			  notes="main 피드의 타입(뉴스, 정보글 등)별로 피드 정렬")
	@GetMapping("/{feedType}")
	public ResponseEntity<List<FeedDto>> searchFeed(@PathVariable int feedType){
		System.out.println(feedType);
		return new ResponseEntity<List<FeedDto>>(feedService.searchFeed(feedType), HttpStatus.OK);
	}
}
