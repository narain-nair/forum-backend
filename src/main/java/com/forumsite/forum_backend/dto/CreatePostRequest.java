package com.forumsite.forum_backend.dto;

import com.forumsite.forum_backend.model.User;

public class CreatePostRequest {
    private long threadId;
    private String content;
    private User author;

    public long getThreadId() {
        return threadId;
    }
    
    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
