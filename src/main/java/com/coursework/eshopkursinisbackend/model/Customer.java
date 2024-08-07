package com.coursework.eshopkursinisbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
public class Customer extends User {

    private String address;
    private String cardNo;
    @OneToMany(mappedBy = "customer")
    private List<CustomerOrder> userCustomerOrder;

    public Customer() {

    }


    public Customer(String login, String password, LocalDate birthDate, String name, String surname) {
        super(login, password, birthDate, name, surname);
    }

    public Customer(String login, String password, LocalDate birthDate, String name, String surname, String address, String cardNo, List<CustomerOrder> userCustomerOrder) {
        super(login, password, birthDate, name, surname);
        this.address = address;
        this.cardNo = cardNo;
        this.userCustomerOrder = userCustomerOrder;
    }


    public Customer(String login, String password, LocalDate birthDate, String name, String surname, String address, String cardNo) {
        super(login, password, birthDate, name, surname);
        this.address = address;
        this.cardNo = cardNo;
    }

}