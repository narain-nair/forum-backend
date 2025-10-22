package com.forumsite.forum_backend.service;

import com.forumsite.forum_backend.model.Post;
import com.forumsite.forum_backend.model.Thread;
import com.forumsite.forum_backend.model.User;
import com.forumsite.forum_backend.dto.CreatePostRequest;
import com.forumsite.forum_backend.repository.ThreadRepository;
import com.forumsite.forum_backend.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final ThreadRepository threadRepository;

    public PostService(PostRepository postRepository, ThreadRepository threadRepository) {
        this.postRepository = postRepository;
        this.threadRepository = threadRepository;
    }

    public List<Post> getPostsByThreadId(Long threadId) {
        return postRepository.findByThreadIdAndDeletedFalse(threadId);
    }

    public Post createPost(CreatePostRequest request) {
        Thread thread = threadRepository.findById(request.getThreadId())
                .orElseThrow(() -> new IllegalArgumentException("Thread not found"));

        Post post = new Post();
        post.setContent(request.getContent());
        post.setThread(thread);
        post.setDeleted(false);
        post.setCreatedAt(java.time.LocalDateTime.now());
        post.setAuthor(request.getAuthor() != null ? request.getAuthor() : null);

        return postRepository.save(post);
    }

    public void softDeletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        post.setDeleted(true);
        postRepository.save(post);
    }
}
