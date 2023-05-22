package com.lcwd.blog.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcwd.blog.entity.Comment;
import com.lcwd.blog.entity.Post;
import com.lcwd.blog.exception.ResourceNotFoundException;
import com.lcwd.blog.payloads.CommentDto;
import com.lcwd.blog.payloads.PostDto;
import com.lcwd.blog.repository.CommentRepository;
import com.lcwd.blog.repository.PostRepository;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post=this.postRepository.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("post","postId", postId));

		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		
	    Comment savedComment=this.commentRepository.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
		
	}

	@Override
	public void deleteComment(Integer commentId) {

		Comment comment=this.commentRepository.findById(commentId)
		.orElseThrow(()->new ResourceNotFoundException("Comment","commentId", commentId));
		
		this.commentRepository.delete(comment);
	}

}
