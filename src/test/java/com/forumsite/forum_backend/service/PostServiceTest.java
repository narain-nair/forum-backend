package com.forumsite.forum_backend.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.forumsite.forum_backend.dto.CreatePostRequest;
import com.forumsite.forum_backend.model.Post;
import com.forumsite.forum_backend.repository.ThreadRepository;
import com.forumsite.forum_backend.model.Thread;

@SpringBootTest
class PostServiceTest {
    @Autowired private PostService postService;
    @Autowired private ThreadRepository threadRepository;

    @Test
    void createPost_ShouldSaveSuccessfully() {
        Thread thread = threadRepository.save(new Thread("Test Thread", "This is a test thread."));
        CreatePostRequest req = new CreatePostRequest();
        req.setThreadId(thread.getId());
        req.setContent("Hello world");
        Post post = postService.createPost(req);
        assertNotNull(post.getId());
    }
}
