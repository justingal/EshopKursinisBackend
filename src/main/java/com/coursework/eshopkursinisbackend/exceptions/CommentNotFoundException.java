package com.coursework.eshopkursinisbackend.exceptions;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Integer id) {super("Could not find comment "+ id);}
}
