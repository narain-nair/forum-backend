package com.forumsite.forum_backend.service;

import com.forumsite.forum_backend.model.Thread;
import com.forumsite.forum_backend.dto.CreateThreadRequest;
import com.forumsite.forum_backend.repository.ThreadRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ThreadService {
    private final ThreadRepository threadRepository;

    public ThreadService(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public Thread createThread(CreateThreadRequest request) {
        Thread thread = new Thread();
        thread.setTitle(request.getTitle());
        return threadRepository.save(thread);
    }

    public List<Thread> getAllThreads() {
        return threadRepository.findAll();
    }
}
