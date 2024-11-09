package com.devInnovators.Whatchdog.Command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devInnovators.Whatchdog.Command.domain.model.Comment;

public interface CommandCommentRepository extends JpaRepository<Comment, String> {
    
}
