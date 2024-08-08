package com.coursework.eshopkursinisbackend.controllers;

import com.coursework.eshopkursinisbackend.dto.BaseUserDTO;
import com.coursework.eshopkursinisbackend.dto.ProductDTO;
import com.coursework.eshopkursinisbackend.exceptions.WarehouseNotFoundException;
import com.coursework.eshopkursinisbackend.model.Product;
import com.coursework.eshopkursinisbackend.model.User;
import com.coursework.eshopkursinisbackend.repos.ProductRepository;
import com.coursework.eshopkursinisbackend.util.ProductMapper;
import com.coursework.eshopkursinisbackend.util.UserMapper;
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
public class ProductRest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Validator validator;

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper objectMapper;

    @PostMapping(value = "/createProduct")
    public ResponseEntity<?> createProduct(@Valid @RequestBody String productInfo) {
        try {
            Product product = objectMapper.readValue(productInfo, Product.class);

            Set<ConstraintViolation<Product>> violations = validator.validate(product);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }

            Product savedProduct = productRepository.saveAndFlush(product);
            ProductDTO productDTO = ProductMapper.toDTO(savedProduct);
            return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getConstraintViolations().toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
