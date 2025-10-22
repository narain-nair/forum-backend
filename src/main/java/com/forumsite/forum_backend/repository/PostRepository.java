package com.forumsite.forum_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.forumsite.forum_backend.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Get all non-deleted posts in a thread
    List<Post> findByThreadId(Long threadId);
    List<Post> findByThreadIdAndDeletedFalse(Long threadId);

    // Optionally fetch a specific post within a thread
    Optional<Post> findByIdAndThreadId(Long postId, Long threadId);
}