package com.lcwd.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lcwd.blog.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
