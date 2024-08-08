package com.coursework.eshopkursinisbackend.exceptions;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(Integer id) {super("Could not find user "+ id);}

    public ProductNotFoundException(String username) {super("Could not find user "+ username);}

}
