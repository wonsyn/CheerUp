package com.web.curation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@PostMapping("/create")
	public ResponseEntity<String> createComment(@RequestBody CommentDto commentDto) {
		commentService.writeComment(commentDto);
		System.out.println(commentDto);
		
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
}
