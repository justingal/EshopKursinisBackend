package com.coursework.eshopkursinisbackend.controllers;

import com.coursework.eshopkursinisbackend.dto.CommentDTO;
import com.coursework.eshopkursinisbackend.dto.ProductDTO;
import com.coursework.eshopkursinisbackend.model.Comment;
import com.coursework.eshopkursinisbackend.model.Product;
import com.coursework.eshopkursinisbackend.repos.CommentRepository;
import com.coursework.eshopkursinisbackend.util.CommentMapper;
import com.coursework.eshopkursinisbackend.util.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Set;

@RestController
public class CommentRest {

    @Autowired
    private Validator validator;

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper objectMapper;

    private final CommentRepository commentRepository;

    public CommentRest(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping(value = "/createComment")
    public ResponseEntity<?> createComment(@Valid @RequestBody String commentInfo) {
        try {
            Comment comment = objectMapper.readValue(commentInfo, Comment.class);

            Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }

            Comment savedComment = commentRepository.saveAndFlush(comment);
            CommentDTO commentDTO = CommentMapper.toDTO(savedComment);
            return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getConstraintViolations().toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
