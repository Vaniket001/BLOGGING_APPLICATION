package com.lcwd.blog.service;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lcwd.blog.entity.Category;
import com.lcwd.blog.entity.Post;
import com.lcwd.blog.entity.User;
import com.lcwd.blog.exception.ResourceNotFoundException;
import com.lcwd.blog.payloads.PostDto;
import com.lcwd.blog.payloads.PostResponse;
import com.lcwd.blog.repository.CategoryRepository;
import com.lcwd.blog.repository.PostRepository;
import com.lcwd.blog.repository.UserRepository;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		Post post=this.modelMapper.map(postDto, Post.class);
		
		User user=this.userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		
		Category category=this.categoryRepository.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category","categoryId", categoryId));
		
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post savedPost=this.postRepository.save(post);
		
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=this.postRepository.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("post","postId", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost=this.postRepository.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepository.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("post","postId", postId));
		this.postRepository.delete(post);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepository.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("post","postId", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

//	@Override
//	public List<PostDto> getAllPosts(Integer pageNo,Integer pageSize) {
//		
//		Pageable pageable= PageRequest.of(pageNo, pageSize);
//		
//		Page<Post> postOfPage=this.postRepository.findAll(pageable);
//		
//		List<Post> posts=postOfPage.getContent();
//		
//		List<PostDto> postDtos=posts.stream().map(post->this.modelMapper.map(post, PostDto.class))
//				.collect(Collectors.toList());
//		return postDtos;
//	}
	
	@Override
	public PostResponse getAllPosts(Integer pageNo,Integer pageSize,String sortBy, String sortDir) {
		
//		Pageable pageable= PageRequest.of(pageNo, pageSize);
		
		Sort sort= sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pageable= PageRequest.of(pageNo, pageSize,sort);
		
		Page<Post> postOfPage=this.postRepository.findAll(pageable);
		
		List<Post> posts=postOfPage.getContent();
		
		List<PostDto> postDtos=posts.stream().map(post->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		PostResponse postResponse= new PostResponse();
		postResponse.setContents(postDtos);
		postResponse.setPageNo(postOfPage.getNumber());
		postResponse.setPageSize(postOfPage.getSize());
		postResponse.setTotalPages(postOfPage.getTotalPages());
		postResponse.setTotalElements(postOfPage.getNumberOfElements());
		postResponse.setLastPage(postOfPage.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category=this.categoryRepository.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category","categoryId", categoryId));
		List<Post> posts=this.postRepository.findPostByCategory(category);
		List<PostDto> postDtos=posts.stream().map(post->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user=this.userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		List<Post> posts=this.postRepository.findPostByUser(user);
		List<PostDto> postDtos=posts.stream().map(post->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<Post> searchPost(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
