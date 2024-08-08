package com.coursework.eshopkursinisbackend.controllers;

import com.coursework.eshopkursinisbackend.dto.BaseUserDTO;
import com.coursework.eshopkursinisbackend.dto.CredentialsDTO;
import com.coursework.eshopkursinisbackend.exceptions.UserNotFoundException;
import com.coursework.eshopkursinisbackend.model.User;
import com.coursework.eshopkursinisbackend.repos.UserRepository;
import com.coursework.eshopkursinisbackend.util.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
public class UserRest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Validator validator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper objectMapper;

    @GetMapping(value = "/users")
    public ResponseEntity<List<BaseUserDTO>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<BaseUserDTO> userDTOs = users.stream().map(UserMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<EntityModel<BaseUserDTO>> getUserById(@PathVariable(name = "id") int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        BaseUserDTO userDTO = UserMapper.toDTO(user);
        return ResponseEntity.ok(EntityModel.of(userDTO,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserRest.class).getUserById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserRest.class).getAllUsers()).withRel("Users")));
    }


    @GetMapping(value = "/getUserByUsername/{username}")
    public ResponseEntity<EntityModel<BaseUserDTO>> getUserByUsername(@PathVariable(name = "username") String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        BaseUserDTO userDTO = UserMapper.toDTO(user);
        return ResponseEntity.ok(EntityModel.of(userDTO,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserRest.class).getUserByUsername(username)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserRest.class).getAllUsers()).withRel("Users")));
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

        BaseUserDTO userDTO = UserMapper.toDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody String userInfo) {
        try {
            User user = objectMapper.readValue(userInfo, User.class);

            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.saveAndFlush(user);
            BaseUserDTO userDTO = UserMapper.toDTO(savedUser);
            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Invalid user type or JSON format", HttpStatus.BAD_REQUEST);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getConstraintViolations().toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
