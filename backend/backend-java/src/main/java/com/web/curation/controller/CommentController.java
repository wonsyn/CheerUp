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
		System.out.println(commentService.readCommentByFeedId(feedId));
		return new ResponseEntity<List<CommentDto>>(commentService.readCommentByFeedId(feedId), HttpStatus.OK);
	}
	
	@ApiOperation(value="댓글 작성", 
			  notes="댓글 작성하는 메서드")
	@PostMapping("/create")
	public ResponseEntity<Map<String, Object>> createComment(@RequestBody CommentDto commentDto)  throws SQLException{
		Map<String, Object> resultMap = new HashMap<>();
		commentService.writeComment(commentDto);
//		System.out.println("댓글 작성 : " + commentDto);
		
		if(commentService.writeComment(commentDto) == 1) {
			System.out.println("덧글 작성 성공");
			resultMap.put("message", "success");
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
			
		} else {
			resultMap.put("message", "fail");
			System.out.println("덧글 작성 실패");
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
//		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@ApiOperation(value="댓글 삭제", 
			  notes="댓글 삭제하는 메서드")
	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable int commentId){
		commentService.deleteComment(commentId);
		
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@ApiOperation(value="댓글 수정하는 메서드", 
			  notes="댓글 작성자에게만 수정/삭제 보여주는 기능은 Front와 협의 후 수정 여지 있으면 수정 예정")
	@PutMapping("/update")
	public ResponseEntity<String> editComment(@RequestBody CommentDto commentDto){
		commentService.editComment(commentDto);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@ApiOperation(value="댓글 좋아요 추가", 
			  notes="댓글 좋아요 추가 / 프론트와 협의 후 수정 여지 있으면 수정 예정")
	@PostMapping("/like")
	public ResponseEntity<String> addLike(@RequestBody CommentLikeDto commentLikeDto){
		System.out.println(commentLikeDto);
		commentLikeServie.addLike(commentLikeDto);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@ApiOperation(value="댓글 좋아요 취소", 
			  notes="댓글 좋아요 취소 / 프론트와 협의 후 수정 여지 있으면 수정 예정")
	@DeleteMapping("/like")
	public ResponseEntity<String> cancelLike(@RequestBody CommentLikeDto commentLikeDto){
		commentLikeServie.cancelLike(commentLikeDto);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
}
