package com.forumsite.forum_backend.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.forumsite.forum_backend.service.ThreadService;
import com.forumsite.forum_backend.model.Thread;

@WebMvcTest(ThreadController.class)
class ThreadControllerTest {
    private ThreadService threadService;
    private ThreadController threadController;

    @BeforeEach
    void setUp() {
        threadService = Mockito.mock(ThreadService.class);
        threadController = new ThreadController(threadService);
    }

    @Test
    void testCreateThread() throws Exception {
        Thread thread = new Thread();
        thread.setId(1L);
        thread.setTitle("Test Thread");

        Mockito.when(threadService.createThread(Mockito.any())).thenReturn(thread);

        var request = new com.forumsite.forum_backend.dto.CreateThreadRequest();
        request.setTitle("Test Thread");

        Thread createdThread = threadController.createThread(request);
        assertNotNull(createdThread);
        assertNotNull(createdThread.getId());
        assertTrue(createdThread.getTitle().equals("Test Thread"));
    }
}
