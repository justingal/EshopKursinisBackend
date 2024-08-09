package com.coursework.eshopkursinisbackend.dto;


import com.coursework.eshopkursinisbackend.model.Customer;
import com.coursework.eshopkursinisbackend.model.Manager;
import com.coursework.eshopkursinisbackend.model.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
public class OrderDTO {

    @NotBlank(message = "ID is required")
    private int id;

    @NotBlank(message = "dateCreated is required")
    private LocalDate dateCreated;

    @NotBlank(message = "totalPrice is required")
    private double totalPrice;

    @NotBlank(message = "customer is required")
    private Customer customer;

    @NotBlank(message = "manager is required")
    private Manager manager;

    @NotBlank(message = "orderStatus is required")
    private OrderStatus orderStatus;

    public OrderDTO(int id, LocalDate dateCreated, OrderStatus orderStatus, double totalPrice) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }
}
