package com.lcwd.blog.service;

import java.util.List;

import com.lcwd.blog.entity.Post;
import com.lcwd.blog.payloads.PostDto;
import com.lcwd.blog.payloads.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	void deletePost(Integer postId);
	
	PostDto getPostById(Integer postId);
	
//	List<PostDto> getAllPosts(Integer pageNo,Integer pazeSize);
	
	PostResponse getAllPosts(Integer pageNo,Integer pazeSize,String sortBy, String sortDir);
	
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	List<PostDto> getPostsByUser(Integer userId);
	
	List<Post> searchPost(String keyword);

}
