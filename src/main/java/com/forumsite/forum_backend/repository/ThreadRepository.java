package com.forumsite.forum_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.forumsite.forum_backend.model.Thread;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
}
