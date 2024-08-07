package com.coursework.eshopkursinisbackend.exceptions;

public class WarehouseNotFoundException extends RuntimeException {
    public WarehouseNotFoundException(Integer id) {
        super("Could not find warehouse " + id);
    }
}