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

@RestController
@RequestMapping("/feed")
public class FeedController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UserScrapfeedMyfeedService userScrapfeedMyfeedService;
	
	@Autowired
	private FeedService feedService;
	
	@GetMapping("/board/list/{userId}")
	public ResponseEntity<List<BoardDto>> getBoardByUserId(@PathVariable int userId){
		
		return new ResponseEntity<List<BoardDto>>(boardService.getBoardByUserId(userId), HttpStatus.OK);
	}
	
	@PostMapping("/board/create")
	public ResponseEntity<String> createBoard(@RequestBody BoardDto boardDto){
		boardService.createBoard(boardDto);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@PutMapping("/board/update")
	public ResponseEntity<String> editBoard(@RequestBody BoardDto boardDto){
		System.out.println(boardDto);
		boardService.editBoard(boardDto);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@DeleteMapping("/board/delete/{boardId}")
	public ResponseEntity<String> deleteBoard(@PathVariable int boardId){
		boardService.deleteBoard(boardId);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@PostMapping("/scrap/create")
	public ResponseEntity<String> createScrap(@RequestBody UserScrapfeedMyfeedDto dto){
		System.out.println(dto);
		userScrapfeedMyfeedService.addScrap(dto);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@GetMapping("/scrap/{userId}")
	public ResponseEntity<List<UserScrapfeedMyfeedDto>> getMyFeed(@PathVariable int userId){
		return new ResponseEntity<List<UserScrapfeedMyfeedDto>>(userScrapfeedMyfeedService.getMyScrap(userId), HttpStatus.OK);
	}
	
	@GetMapping("/scrap/{userId}/{boardId}")
	public ResponseEntity<List<UserScrapfeedMyfeedDto>> getMyFeedEachBoard(@PathVariable int userId, @PathVariable int boardId){
		System.out.println(userId);
		return new ResponseEntity<List<UserScrapfeedMyfeedDto>>(userScrapfeedMyfeedService.getMyScrapEachBoard(userId, boardId), HttpStatus.OK);
	}
	
	@GetMapping("/{feedType}")
	public ResponseEntity<List<FeedDto>> searchFeed(@PathVariable int feedType){
		System.out.println(feedType);
		return new ResponseEntity<List<FeedDto>>(feedService.searchFeed(feedType), HttpStatus.OK);
	}
}
