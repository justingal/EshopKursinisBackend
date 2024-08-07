package com.coursework.eshopkursinisbackend.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer id) {
        super("Could not find user " + id);
    }

    public UserNotFoundException(String username) {
        super("Could not find user " + username);
    }
}
