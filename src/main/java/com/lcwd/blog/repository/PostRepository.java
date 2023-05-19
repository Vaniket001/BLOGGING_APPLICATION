package com.lcwd.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lcwd.blog.entity.Category;
import com.lcwd.blog.entity.Post;
import com.lcwd.blog.entity.User;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	List<Post> findPostByCategory(Category category);
	List<Post> findPostByUser(User user);
	List<Post> findByTitleContaining(String title);

}
