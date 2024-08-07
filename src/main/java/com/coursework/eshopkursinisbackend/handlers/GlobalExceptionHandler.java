package com.coursework.eshopkursinisbackend.handlers;

import com.coursework.eshopkursinisbackend.exceptions.WarehouseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WarehouseNotFoundException.class)
    public ResponseEntity<?> handleWarehouseNotFoundException(WarehouseNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>("Failed to delete the warehouse: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}