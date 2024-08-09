package com.coursework.eshopkursinisbackend.controllers;

import com.coursework.eshopkursinisbackend.model.CustomerOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
import com.coursework.eshopkursinisbackend.repos.OrderRepository;

import java.io.IOException;
import java.util.Set;

@RestController
public class OrderRest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private Validator validator;

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper objectMapper;

    @PostMapping(value = "/createOrder")
    public ResponseEntity<?> createOrder(@Valid @RequestBody String orderInfo) {
        try {
            CustomerOrder order = objectMapper.readValue(orderInfo, CustomerOrder.class);

            Set<ConstraintViolation<CustomerOrder>> violations = validator.validate(order);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }

            orderRepository.saveAndFlush(order);

            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getConstraintViolations().toString(), HttpStatus.BAD_REQUEST);
        }
    }


}
