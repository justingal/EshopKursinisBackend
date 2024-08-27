package com.coursework.eshopkursinisbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class Dice extends Product {

    int diceNumber;
    @OneToMany(mappedBy = "dice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference("dice-review")
    private List<Review> reviews;
    @ManyToOne
    @JsonBackReference("order-dice")
    private CustomerOrder customerOrder;

    public Dice(String title, String description, String author, Warehouse warehouse, double price) {
        super(title, description, author, warehouse, price);
    }

    public Dice(String title, String description, String author, Warehouse warehouse, double price, int id, int diceNumber, List<Review> reviews) {
        super(title, description, author, warehouse, price);
        this.id = id;
        this.diceNumber = diceNumber;
        this.reviews = reviews;
    }

    public Dice() {

    }

    public Dice(String title, String description, String author, Warehouse warehouse, double price, int diceNumber) {
        super(title, description, author, warehouse, price);
        this.diceNumber = diceNumber;
    }

    public Dice(int id, String title, String description, String author, double price, int diceNumber) {
        super(id, title, description, author, price);
        this.diceNumber = diceNumber;
    }

    public Dice(String title, String description, String author, double price, int diceNumber) {
        super(title, description, author, price);
        this.diceNumber = diceNumber;
    }

}

