package com.coursework.eshopkursinisbackend.repos;

import com.coursework.eshopkursinisbackend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}