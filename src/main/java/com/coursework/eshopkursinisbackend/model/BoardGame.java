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
public class BoardGame extends Product {
    @ManyToOne
    @JsonBackReference
    private CustomerOrder customerOrder;

    @OneToMany(mappedBy = "boardGame", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
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