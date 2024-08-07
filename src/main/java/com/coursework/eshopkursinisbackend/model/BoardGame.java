package com.coursework.eshopkursinisbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
public class BoardGame extends Product {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_gen")
    private int id;
    @ManyToOne
    private CustomerOrder customerOrder;

    @OneToMany(mappedBy = "boardGame", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Review> reviews;
    @Getter
    private String playersQuantity;
    private String gameDuration;

    public BoardGame(String title, String description, String author, Warehouse warehouse, double price) {
        super(title, description, author, warehouse, price);
    }

    public BoardGame() {

    }
    public BoardGame(String title, String description, String author, Warehouse warehouse, double price, String playersQuantity, int id, String gameDuration, List<Review> reviews) {
        super(title, description, author, warehouse, price);
        this.playersQuantity = playersQuantity;
        this.id = id;
        this.gameDuration = gameDuration;
        this.reviews = reviews;
    }


    public BoardGame(int id, String title, String description, String author, int price, String playersQuantity, String gameDuration) {
        super(id, title, description, author, price);
        this.playersQuantity = playersQuantity;
        this.gameDuration = gameDuration;
    }

    public BoardGame(String title, String description, String author, double price, String playersQuantity, String gameDuration) {
        super(title, description, author, price);
        this.playersQuantity = playersQuantity;
        this.gameDuration = gameDuration;
    }

    public BoardGame(String title, String description, String author, Warehouse warehouse, double price, String playersQuantity, String gameDuration) {
        super(title, description, author, warehouse, price);
        this.playersQuantity = playersQuantity;
        this.gameDuration = gameDuration;
    }

}