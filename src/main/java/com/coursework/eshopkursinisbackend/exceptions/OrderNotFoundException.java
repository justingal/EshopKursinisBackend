package com.coursework.eshopkursinisbackend.exceptions;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(Integer id) {super("Could not find order "+ id);}

}
