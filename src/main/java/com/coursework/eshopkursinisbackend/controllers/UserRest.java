package com.coursework.eshopkursinisbackend.controllers;

import com.coursework.eshopkursinisbackend.dto.BaseUserDTO;
import com.coursework.eshopkursinisbackend.dto.CredentialsDTO;
import com.coursework.eshopkursinisbackend.exceptions.UserNotFoundException;
import com.coursework.eshopkursinisbackend.exceptions.WarehouseNotFoundException;
import com.coursework.eshopkursinisbackend.model.*;
import com.coursework.eshopkursinisbackend.repos.UserRepository;
import com.coursework.eshopkursinisbackend.util.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;
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

    @PostMapping(value = "/createUser")
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
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getConstraintViolations().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") int id) {
        try {
            userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
            userRepository.deleteById(id);

            return new ResponseEntity<>("User with id = " + id + " was successfully deleted", HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getConstraintViolations().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") int id, @Valid @RequestBody String userInfo) {
        try {
            User currentUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));


            if (currentUser instanceof Customer) {

                Customer newCustomerInfo = objectMapper.readValue(userInfo, Customer.class);

                if ( newCustomerInfo.getName() != null) {
                    currentUser.setName( newCustomerInfo.getName());
                }
                if ( newCustomerInfo.getSurname() != null) {
                    currentUser.setSurname( newCustomerInfo.getSurname());
                }
                if ( newCustomerInfo.getBirthDate() != null) {
                    currentUser.setBirthDate( newCustomerInfo.getBirthDate());
                }
                if (newCustomerInfo.getAddress() != null) {
                    ((Customer) currentUser).setAddress(newCustomerInfo.getAddress());
                }
                if (newCustomerInfo.getCardNo() != null) {
                    ((Customer) currentUser).setCardNo(newCustomerInfo.getCardNo());
                }
            } else if (currentUser instanceof Admin) {
                Admin newAdminInfo = objectMapper.readValue(userInfo, Admin.class);
                if ( newAdminInfo.getName() != null) {
                    currentUser.setName( newAdminInfo.getName());
                }
                if ( newAdminInfo.getSurname() != null) {
                    currentUser.setSurname( newAdminInfo.getSurname());
                }
                if ( newAdminInfo.getBirthDate() != null) {
                    currentUser.setBirthDate( newAdminInfo.getBirthDate());
                }
                if (newAdminInfo.getEmployeeId() != null) {
                    ((Admin) currentUser).setEmployeeId(newAdminInfo.getEmployeeId());
                }
                if (newAdminInfo.getMedCertificate() != null) {
                    ((Admin) currentUser).setMedCertificate(newAdminInfo.getMedCertificate());
                }
                if (newAdminInfo.getEmploymentDate() != null) {
                    ((Admin) currentUser).setEmploymentDate(newAdminInfo.getEmploymentDate());
                }
            } else if (currentUser instanceof Manager) {
                Manager newManagerInfo = objectMapper.readValue(userInfo, Manager.class);
                if ( newManagerInfo.getName() != null) {
                    currentUser.setName( newManagerInfo.getName());
                }
                if ( newManagerInfo.getSurname() != null) {
                    currentUser.setSurname( newManagerInfo.getSurname());
                }
                if ( newManagerInfo.getBirthDate() != null) {
                    currentUser.setBirthDate( newManagerInfo.getBirthDate());
                }
                if (newManagerInfo.getEmployeeId() != null) {
                    ((Manager) currentUser).setEmployeeId(newManagerInfo.getEmployeeId());
                }
                if (newManagerInfo.getMedCertificate() != null) {
                    ((Manager) currentUser).setMedCertificate(newManagerInfo.getMedCertificate());
                }
                if (newManagerInfo.getEmploymentDate() != null) {
                    ((Manager) currentUser).setEmploymentDate(newManagerInfo.getEmploymentDate());
                }
            }

            Set<ConstraintViolation<User>> violations = validator.validate(currentUser);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }

            User savedUser = userRepository.saveAndFlush(currentUser);
            BaseUserDTO userDTO = UserMapper.toDTO(savedUser);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getConstraintViolations().toString(), HttpStatus.BAD_REQUEST);
        }
    }
}