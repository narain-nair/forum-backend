package com.forumsite.forum_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        assertEquals("Hello world", post.getContent());
        assertEquals(thread.getId(), post.getThread().getId());
    }

    @Test
    void createPost_InvalidThreadId_ShouldThrowException() {
        CreatePostRequest req = new CreatePostRequest();
        req.setThreadId(9999L); // Assuming this ID does not exist
        req.setContent("Test will fail");

        Exception exception = null;
        try {
            postService.createPost(req);
        } catch (Exception ex) {
            exception = ex;
        }

        assertNotNull(exception);
        assertTrue(exception instanceof IllegalArgumentException);
        assertEquals("Thread with ID 9999 does not exist", exception.getMessage());
    }

    @Test
    void getPostsByThread_ShouldReturnPosts() {
        Thread thread = threadRepository.save(new Thread("Another Thread", "Description"));
        CreatePostRequest req = new CreatePostRequest();
        req.setThreadId(thread.getId());
        req.setContent("Test Post");
        postService.createPost(req);

        var posts = postService.getPostsByThreadId(thread.getId());

        assertFalse(posts.isEmpty());
        assertEquals("Test Post", posts.get(0).getContent());
    }
}
