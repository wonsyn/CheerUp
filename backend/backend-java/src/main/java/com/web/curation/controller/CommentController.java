package com.web.curation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.model.dto.CommentDto;
import com.web.curation.model.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	// 아직 api가 정해재지 않아서 고유 주소를 인식하는 규칙에 따라서 url바귈수도 => commentUrl을 나중에는 고유 id로 바꿀 예정
	@GetMapping("/read/{commentUrl}")
	public ResponseEntity<List<CommentDto>> readCommentByUrl(@PathVariable String commentUrl){
		System.out.println(commentService.readCommentByUrl(commentUrl));
		return new ResponseEntity<List<CommentDto>>(commentService.readCommentByUrl(commentUrl), HttpStatus.OK);
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
	
	
}
