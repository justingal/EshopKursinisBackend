package com.coursework.eshopkursinisbackend.controllers;

import com.coursework.eshopkursinisbackend.dto.CredentialsDTO;
import com.coursework.eshopkursinisbackend.exceptions.UserNotFoundException;
import com.coursework.eshopkursinisbackend.model.User;
import com.coursework.eshopkursinisbackend.repos.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Validator;

import java.util.List;

@RestController
public class UserRest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Validator validator;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping(value = "/user/{id}")
    public EntityModel<User> getUserById(@PathVariable(name = "id") int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return EntityModel.of(user,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserRest.class).getUserById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserRest.class).getAllUsers()).withRel("Users"));
    }

    @GetMapping(value = "/getUserByUsername/{username}")
    public EntityModel<User> getUserByUsername(@PathVariable(name = "username") String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return EntityModel.of(user,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserRest.class).getUserByUsername(username)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserRest.class).getAllUsers()).withRel("Users"));
    }

    @PostMapping(value = "/getUserByCredentials")
    public ResponseEntity<?> getUserByCredentials(@Valid @RequestBody CredentialsDTO credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        user.setPassword(null);

        return ResponseEntity.ok(user);
    }
}
