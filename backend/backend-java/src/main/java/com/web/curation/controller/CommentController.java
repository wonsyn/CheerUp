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

import com.web.curation.model.dto.CommentDto;
import com.web.curation.model.dto.CommentLikeDto;
import com.web.curation.model.service.CommentLikeService;
import com.web.curation.model.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CommentLikeService commentLikeServie;
	
	// 아직 api가 정해재지 않아서 고유 주소를 인식하는 규칙에 따라서 url바귈수도 => commentUrl을 나중에는 고유 id로 바꿀 예정
	// commentUrl -> feedId로 변경
	@GetMapping("/read/{feedId}")
	public ResponseEntity<List<CommentDto>> readCommentByUrl(@PathVariable int feedId){
		System.out.println(commentService.readCommentByFeedId(feedId));
		return new ResponseEntity<List<CommentDto>>(commentService.readCommentByFeedId(feedId), HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<String> createComment(@RequestBody CommentDto commentDto) {
		commentService.writeComment(commentDto);
		System.out.println(commentDto);
		
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable int commentId){
		commentService.deleteComment(commentId);
		
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> editComment(@RequestBody CommentDto commentDto){
		commentService.editComment(commentDto);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@PostMapping("/like")
	public ResponseEntity<String> addLike(@RequestBody CommentLikeDto commentLikeDto){
		System.out.println(commentLikeDto);
		commentLikeServie.addLike(commentLikeDto);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@DeleteMapping("/like")
	public ResponseEntity<String> cancelLike(@RequestBody CommentLikeDto commentLikeDto){
		commentLikeServie.cancelLike(commentLikeDto);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@GetMapping("/like/{commentId}")
	public ResponseEntity<List<CommentLikeDto>> getCommentLikeByCommentId(@PathVariable int commentId){
		System.out.println(commentId);
		System.out.println(commentLikeServie.getCommentLikeByCommentId(commentId));
		
		return new ResponseEntity<List<CommentLikeDto>>(commentLikeServie.getCommentLikeByCommentId(commentId), HttpStatus.OK);
	}
	
}
