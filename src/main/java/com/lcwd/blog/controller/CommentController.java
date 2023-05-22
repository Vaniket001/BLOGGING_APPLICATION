package com.lcwd.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.blog.payloads.ApiResponse;
import com.lcwd.blog.payloads.CommentDto;
import com.lcwd.blog.payloads.UserDto;
import com.lcwd.blog.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable("postId") Integer pid){
		CommentDto createdCommentDto=this.commentService.createComment(commentDto, pid);
		return new ResponseEntity<CommentDto>(createdCommentDto, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer cid){
		this.commentService.deleteComment(cid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment with commentId : "+ cid + " is deleted successfully !!",true),HttpStatus.OK);
	}

}
