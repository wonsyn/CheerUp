package com.web.curation.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

import com.web.curation.model.dto.BoardDto;
import com.web.curation.model.dto.FeedDto;
import com.web.curation.model.dto.UserScrapfeedMyfeedDto;
import com.web.curation.model.dto.UserScrapfeedMyfeedJoinDto;
import com.web.curation.model.service.BoardService;
import com.web.curation.model.service.FeedService;
import com.web.curation.model.service.UserScrapfeedMyfeedService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags = "메인피드/개인피드/보드")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
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
	public ResponseEntity<Map<String, Object>> createBoard(@RequestBody BoardDto boardDto) throws SQLException{
		Map<String, Object> resultMap = new HashMap<>();
		try {
			BoardDto isSame = boardService.isExistSameBoardName(boardDto);
			if(isSame != null) { // 중복이면
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			// 중복 아니면
			int result = boardService.createBoard(boardDto); 
			if(result == 1) {
				resultMap.put("message", "success");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			}else {
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch(Exception e) {
			resultMap.put("message", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value="board 수정", 
			  notes="유저 board 이름 수정")
	@PutMapping("/board/update")
	public ResponseEntity<Map<String, Object>> editBoard(@RequestBody BoardDto boardDto) {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			BoardDto isSame = boardService.isExistSameBoardName(boardDto);
			if(isSame != null) { // 중복이면
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			// 중복 아니면
			int result = boardService.editBoard(boardDto);
			if(result == 1) {
				resultMap.put("message", "success");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			}else {
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
				
		}catch(Exception e) {
			resultMap.put("message", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value="board 삭제", 
			  notes="유저 board 삭제")
	@DeleteMapping("/board/delete/{boardId}")
	public ResponseEntity<Map<String, Object>> deleteBoard(@PathVariable int boardId){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int result = boardService.deleteBoard(boardId);
			if(result == 1) {
				resultMap.put("message", "success");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			}else {
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}catch(Exception e) {
			resultMap.put("message", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value="스크랩하기", 
			  notes="피드 스크랩하기")
	@PostMapping("/scrap/create")
	public ResponseEntity<Map<String, Object>> createScrap(@RequestBody UserScrapfeedMyfeedDto dto){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int result = userScrapfeedMyfeedService.addScrap(dto);
			if(result == 1) {
				resultMap.put("message", "success");
				return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
			}else {
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e) {
			System.out.println("tyr");
			e.printStackTrace();
			resultMap.put("message", "fail");
			return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value="스크랩취소", 
			  notes="피드 스크랩 취소")
	@DeleteMapping("/scrap/delete/{myfeedId}")
	public ResponseEntity<Map<String, Object>> cancelScrap(@PathVariable int myfeedId){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int result = userScrapfeedMyfeedService.cancelScrap(myfeedId);
			if(result == 1) {
				resultMap.put("message", "success");
				return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
			}else {
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value="스크랩한 피드 board 수정", 
			  notes="/scrap/update/{myfeedId}/{boardId} \n "
			  		+ "boardId 수정")
	@PutMapping("/scrap/update/{myfeedId}/{boardId}")
	public ResponseEntity<Map<String, Object>> editScrapBoard(@PathVariable int myfeedId, @PathVariable int boardId){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int result = userScrapfeedMyfeedService.editScrapBoard(myfeedId, boardId);
			if(result == 1) {
				resultMap.put("message", "success");
				return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
			}else {
				resultMap.put("message", "faile");
				return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e) {
			resultMap.put("message", "fail");
			return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value="내가 스크랩 한 피드 보기", 
			  notes="userId로 내가 스크랩 한 피드 모두 보기")
	@GetMapping("/scrap/{userId}")
	public ResponseEntity<List<UserScrapfeedMyfeedJoinDto>> getMyFeed(@PathVariable int userId){
		return new ResponseEntity<List<UserScrapfeedMyfeedJoinDto>>(userScrapfeedMyfeedService.getMyScrap(userId), HttpStatus.OK);
	}
	
	// -------------------------
	@ApiOperation(value="보드 별 내가 스크랩 한 피드 보기", 
			  notes="userId와 boardId로 내가 스크랩 한 피드 보기")
	@GetMapping("/scrap/{userId}/{boardId}")
	public ResponseEntity<List<UserScrapfeedMyfeedJoinDto>> getMyFeedEachBoard(@PathVariable int userId, @PathVariable int boardId){
		System.out.println(userId);
		System.out.println(userScrapfeedMyfeedService.getMyScrapEachBoard(7, 2));
		return new ResponseEntity<List<UserScrapfeedMyfeedJoinDto>>(userScrapfeedMyfeedService.getMyScrapEachBoard(userId, boardId), HttpStatus.OK);
	}
	// -------------------------
	
	@ApiOperation(value="피드 타입 및 산업군 별로 조회", 
			  notes="main 피드의 cheerup/feed/main?type='타입코드'&industry='산업코드' -> 전체 조회 시 parameter 안 줘도 됨")
	@GetMapping("/main")
	public ResponseEntity<List<FeedDto>> searchFeed(
			@RequestParam(defaultValue = "0") String type,
			@RequestParam(defaultValue = "0") String industry){
		
		HashMap<String, String> params = new HashMap<>();
		params.put("type", type);
		params.put("industry", industry);
		
		
		return new ResponseEntity<List<FeedDto>>(feedService.searchFeed(params), HttpStatus.OK);
	}
	
	@ApiOperation(value="피드 상세", 
			  notes="feedId로 피드 상세페이지 조회")
	@GetMapping("/detail/{feedId}")
	public ResponseEntity<FeedDto> feedDetail(@PathVariable int feedId){
		System.out.println(feedId);
		return new ResponseEntity<FeedDto>(feedService.readFeedById(feedId), HttpStatus.OK);
	}
	
	@ApiOperation(value="뉴스 기사 랜덤 추천", 
			  notes="지금 보고 있는 뉴스 기사와 같은 산업군의 기사 3개 랜덤 추천 -> 중복 제거, 해당 기사와 같은 feedId 추천 안함 => 변수 넣을 때 그냥 feedId만 넣으면 알아서 같은 산업군 찾아줌")
	@GetMapping("/recomm/{feedId}")
	public ResponseEntity<List<FeedDto>> feedRecommend(@PathVariable int feedId){
		FeedDto feed = feedService.readFeedById(feedId);
		String feedCategory = feed.getFeedCategory();
		HashMap<String, String> params = new HashMap<>();
		params.put("type", "1");
		params.put("industry", feedCategory);
		
		// 해당 피드와 같은 산업군의 기사 리스트
		List<FeedDto> list = feedService.searchFeed(params);
		
		// 랜덤
		System.out.println("list size : " + list.size());
		int[] ran = new int[3];
		Random r = new Random();
		for(int i=0; i<3; i++) {
			ran[i] = r.nextInt(list.size());
			for(int j=0; j<i; j++) {
				if(ran[i] == ran[j] || list.get(ran[i]).getFeedId() == feedId) {
					i--;
				}
			}
		}
		System.out.println(Arrays.toString(ran));
		
		// 랜덤 3개 담은 list
		List<FeedDto> recommList = new ArrayList<>();
 		for(int i=0; i<3; i++) {
			recommList.add(list.get(ran[i]));
		}
		
//		for(int i=0; i<recommList.size(); i++) {
//			System.out.println(recommList.get(i));
//		}
		return new ResponseEntity<List<FeedDto>>(recommList, HttpStatus.OK);
	}
}
