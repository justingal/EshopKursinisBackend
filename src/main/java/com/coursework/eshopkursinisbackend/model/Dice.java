package com.coursework.eshopkursinisbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class Dice extends Product {

    int diceNumber;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_gen")
    private int id;
    @OneToMany(mappedBy = "dice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Review> reviews;
    @ManyToOne
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

