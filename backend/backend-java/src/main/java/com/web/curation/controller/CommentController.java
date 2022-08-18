package com.web.curation.controller;

import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.CommentDto;
import com.web.curation.model.dto.CommentLikeDto;
import com.web.curation.model.service.CommentLikeService;
import com.web.curation.model.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "댓글")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CommentLikeService commentLikeServie;
	
	@ApiOperation(value="feedId로 댓글 모두 불러오기", 
				  notes="피드 고유 id로 해당 피드에 달린 댓글들 모두 불러오는 메서드")
//    @ApiImplicitParam(
//            name = "feedId"
//            , value = "피드 고유 아이디"
//            , required = true
//            , dataType = "int"
//            , paramType = "path"
//            , defaultValue = "None"
//        )
	@GetMapping("/read/{feedId}")
	public ResponseEntity<List<CommentDto>> readCommentByFeedId(@PathVariable int feedId){
		System.out.println("댓글 컨트롤러 피드 id: "+feedId);
		System.out.println(commentService.readCommentByFeedId(feedId));
		for(CommentDto cur : commentService.readCommentByFeedId(feedId)) System.out.println(cur);
		return new ResponseEntity<List<CommentDto>>(commentService.readCommentByFeedId(feedId), HttpStatus.OK);
	}
	
	@ApiOperation(value="댓글 작성", 
			  notes="댓글 작성하는 메서드")
	@PostMapping("/create")
	public ResponseEntity<Map<String, Object>> createComment(@RequestBody CommentDto commentDto){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int result = commentService.writeComment(commentDto);
			if(result == 1) {
				resultMap.put("message", "success");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			}else {
				resultMap.put("message", "fail");
				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e) {
			resultMap.put("message", e.getMessage());
			return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value="댓글 삭제", 
			  notes="댓글 삭제하는 메서드")
	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable int commentId){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int result = commentService.deleteComment(commentId);
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
	
	@ApiOperation(value="댓글 수정하는 메서드", 
			  notes="commentId와 commentContent만 입력하여 수정 가능")
	@PutMapping("/update")
	public ResponseEntity<Map<String, Object>> editComment(@RequestBody CommentDto commentDto){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			System.out.println(commentDto);
			int result = commentService.editComment(commentDto);
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
	
	@ApiOperation(value="댓글 좋아요 추가", 
			  notes="댓글 좋아요 추가 / 프론트와 협의 후 수정 여지 있으면 수정 예정")
	@PostMapping("/like")
	public ResponseEntity<Map<String, Object>> addLike(@RequestBody CommentLikeDto commentLikeDto){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int result = commentLikeServie.addLike(commentLikeDto);
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
	
	@ApiOperation(value="댓글 좋아요 취소", 
			  notes="댓글 좋아요 취소 / 프론트와 협의 후 수정 여지 있으면 수정 예정")
	@DeleteMapping("/like")
	public ResponseEntity<Map<String, Object>> cancelLike(@RequestBody CommentLikeDto commentLikeDto){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			int result = commentLikeServie.cancelLike(commentLikeDto);
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
	
	@ApiOperation(value="댓글 좋아요 체크", 
			  notes="userId와 commentId로 좋아요 여부 체크 -> 이미 좋아요 되어있으면 not null이므로 좋아요 중복 방지")	
	@GetMapping("/like/chk/{commentId}/{userId}")
	public ResponseEntity<Map<String, Object>> chkLike(@PathVariable int commentId, @PathVariable int userId){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			boolean flag = false;
			CommentLikeDto likeDto = commentLikeServie.chkCommentLike(commentId, userId);
			System.out.println(likeDto);
			if(likeDto != null) {
				flag = true;
			}
			System.out.println(flag);
			resultMap.put("message", flag);
			return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
		}catch(Exception e) {
			resultMap.put("message", "fail");
			System.out.println(e.getMessage());
			return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
