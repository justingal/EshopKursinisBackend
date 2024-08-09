package com.coursework.eshopkursinisbackend.exceptions;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(Integer id) {super("Could not find product "+ id);}

    public ProductNotFoundException(String title) {super("Could not find product "+ title);}

}
