package com.forumsite.forum_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.forumsite.forum_backend.service.ThreadService;
import com.forumsite.forum_backend.model.Thread;
import com.forumsite.forum_backend.dto.CreateThreadRequest;

import java.util.List;

@RestController
@RequestMapping("/api/threads")
public class ThreadController {
    private final ThreadService threadService;

    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping
    public List<Thread> getAllThreads() {
        return threadService.getAllThreads();
    }

    @PostMapping
    public Thread createThread(@RequestBody CreateThreadRequest request) {
        return threadService.createThread(request);
    }
}
