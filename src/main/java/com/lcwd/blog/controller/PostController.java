package com.lcwd.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lcwd.blog.configuration.AppConstants;
import com.lcwd.blog.payloads.ApiResponse;
import com.lcwd.blog.payloads.PostDto;
import com.lcwd.blog.payloads.PostResponse;
import com.lcwd.blog.service.FileService;
import com.lcwd.blog.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {//getPostsByCategory
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			@PathVariable("userId") Integer uid,
			@PathVariable("categoryId") Integer cid){
		return new ResponseEntity<PostDto>(this.postService.createPost(postDto, uid, cid),HttpStatus.CREATED);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") Integer cid){
		return new ResponseEntity<List<PostDto>>(this.postService.getPostsByCategory(cid),HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") Integer uid){
		return new ResponseEntity<List<PostDto>>(this.postService.getPostsByUser(uid),HttpStatus.OK);
	}
	
//	@GetMapping("/posts")
//	public ResponseEntity<List<PostDto>> getAllPosts(@RequestParam(value="pageNo", defaultValue="0",required=false) Integer pageNo,@RequestParam(value="pageSize", defaultValue="5",required=false) Integer pageSize){
//		return new ResponseEntity<List<PostDto>>(this.postService.getAllPosts(pageNo,pageSize),HttpStatus.OK);
//	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNo", defaultValue= AppConstants.PAGE_NUMBER,required=false) Integer pageNo,
			@RequestParam(value="pageSize", defaultValue= AppConstants.PAGE_SIZE,required=false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue= AppConstants.SORT_BY,required=false) String sortBy,
			@RequestParam(value="sortDir", defaultValue= AppConstants.SORT_DIR,required=false) String sortDir){
		return new ResponseEntity<PostResponse>(this.postService.getAllPosts(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer pid){
		return new ResponseEntity<PostDto>(this.postService.getPostById(pid),HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("postId") Integer pid){
		return new ResponseEntity<PostDto>(this.postService.updatePost(postDto, pid),HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer pid){
		postService.deletePost(pid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post with postId : "+ pid + " is deleted successfully !!",true),HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable("keywords") String keywords){
		return new ResponseEntity<List<PostDto>>(this.postService.searchPost(keywords),HttpStatus.OK);
	}
	
	@PostMapping("/posts/uploadImage/{postId}")
	public ResponseEntity<PostDto> uploadFile(@RequestParam("image") MultipartFile image
			, @PathVariable("postId") Integer pid) throws IOException {
		
		PostDto postDto= this.postService.getPostById(pid);
		String fileName=this.fileService.uploadFile(path, image);
		postDto.setImageName(fileName);
		PostDto updatedPost=this.postService.updatePost(postDto, pid);
	
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
	
	@GetMapping(value="/posts/serveImage/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveFile(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
		InputStream resource=this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	@GetMapping(value="/posts/getImage/{postId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveImageByPostId(@PathVariable("postId") Integer postId, HttpServletResponse response)
			throws IOException {
		PostDto postDto=this.postService.getPostById(postId);
		InputStream resource=this.fileService.getResource(path, postDto.getImageName());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
