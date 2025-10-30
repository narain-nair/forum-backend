package com.forumsite.forum_backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.forumsite.forum_backend.service.ThreadService;
import com.forumsite.forum_backend.dto.CreateThreadRequest;
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
    void testCreateThread_Success() {
        Thread thread = new Thread();
        thread.setId(1L);
        thread.setTitle("Test Thread");

        when(threadService.createThread(any())).thenReturn(thread);

        CreateThreadRequest request = new CreateThreadRequest();
        request.setTitle("Test Thread");

        Thread created = threadController.createThread(request);

        assertNotNull(created);
        assertEquals(1L, created.getId());
        assertEquals("Test Thread", created.getTitle());
        verify(threadService, times(1)).createThread(any());
    }


    @Test
    void testCreateThread_EmptyTitle_ShouldThrowException() {
        CreateThreadRequest request = new CreateThreadRequest();
        request.setTitle("");

        when(threadService.createThread(any())).thenThrow(new IllegalArgumentException("Thread title cannot be empty"));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            threadController.createThread(request);
        });

        assertEquals("Thread title cannot be empty", ex.getMessage());
    }


    @Test
    void testGetAllThreads() {
        Thread t1 = new Thread();
        t1.setId(1L);
        t1.setTitle("Thread A");

        Thread t2 = new Thread();
        t2.setId(2L);
        t2.setTitle("Thread B");

        when(threadService.getAllThreads()).thenReturn(List.of(t1, t2));

        List<Thread> threads = threadController.getAllThreads();

        assertEquals(2, threads.size());
        assertEquals("Thread A", threads.get(0).getTitle());
        verify(threadService, times(1)).getAllThreads();
    }
}
