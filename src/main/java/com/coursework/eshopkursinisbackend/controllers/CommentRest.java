package com.coursework.eshopkursinisbackend.controllers;

import com.coursework.eshopkursinisbackend.dto.CommentDTO;
import com.coursework.eshopkursinisbackend.model.Comment;
import com.coursework.eshopkursinisbackend.repos.CommentRepository;
import com.coursework.eshopkursinisbackend.util.CommentMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Set;

@RestController
public class CommentRest {

    private final CommentRepository commentRepository;
    @Autowired
    private Validator validator;
    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper objectMapper;

    public CommentRest(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping(value = "/addComment")
    public ResponseEntity<CommentDTO> addComment(@RequestBody @Valid String commentJson) throws IOException {
        Comment comment = objectMapper.readValue(commentJson, Comment.class);
        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        Comment savedComment = commentRepository.saveAndFlush(comment);
        return ResponseEntity.ok(CommentMapper.toCommentDTO(savedComment));
    }

}
