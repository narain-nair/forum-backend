package com.forumsite.forum_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forumsite.forum_backend.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
