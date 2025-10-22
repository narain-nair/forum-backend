package com.forumsite.forum_backend.dto;

public class CreatePostRequest {
    private long threadId;
    private String content;

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
}
