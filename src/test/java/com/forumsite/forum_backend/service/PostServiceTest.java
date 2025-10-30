package com.forumsite.forum_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.forumsite.forum_backend.dto.CreatePostRequest;
import com.forumsite.forum_backend.model.Post;
import com.forumsite.forum_backend.repository.PostRepository;
import com.forumsite.forum_backend.repository.ThreadRepository;
import com.forumsite.forum_backend.repository.UserRepository;

import jakarta.transaction.Transactional;

import com.forumsite.forum_backend.model.Thread;
import com.forumsite.forum_backend.model.User;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired private PostService postService;
    @Autowired private PostRepository postRepository;
    @Autowired private ThreadRepository threadRepository;
    @Autowired private UserRepository userRepository;

    @BeforeEach
    void cleanDatabase() {
        postRepository.deleteAll();
        threadRepository.deleteAll();
        userRepository.deleteAll();
    }

   @Test
    void createPost_ShouldSaveSuccessfully() {
        User user = new User("user" + UUID.randomUUID(), "email" + UUID.randomUUID() + "@test.com", "password");
        userRepository.save(user);

        Thread thread = new Thread("Another Thread", "Description");
        thread.setAuthor(user);
        thread = threadRepository.save(thread);

        CreatePostRequest req = new CreatePostRequest();
        req.setThreadId(thread.getId());
        req.setAuthor(user);
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
        assertEquals("Thread not found", exception.getMessage());
    }

    @Test
    void getPostsByThread_ShouldReturnPosts() {
        User user = new User("username2", "email2", "password2");
        user = userRepository.save(user);

        Thread thread = new Thread("Another Thread", "Description");
        thread.setAuthor(user);
        thread = threadRepository.save(thread);
        
        CreatePostRequest req = new CreatePostRequest();
        req.setThreadId(thread.getId());
        req.setContent("Test Post");
        req.setAuthor(user);
        postService.createPost(req);

        var posts = postService.getPostsByThreadId(thread.getId());

        assertFalse(posts.isEmpty());
        assertEquals("Test Post", posts.get(0).getContent());
    }
}
