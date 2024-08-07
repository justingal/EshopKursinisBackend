package com.coursework.eshopkursinisbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Setter
@Getter
@MappedSuperclass
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_gen")
    int id;
    String title;
    String description;
    String author;
    double price;
    @ManyToOne
    private Warehouse warehouse;

    public Product() {
    }

    public Product(int id, String title, String description, String author, double price, Warehouse warehouse) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
        this.warehouse = warehouse;
    }

    public Product(String title, String description, String author, double price) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
    }

    public Product(int id, String title, String description, String author, double price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
    }

    public Product(String title, String description, String author, Warehouse warehouse, double price) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.warehouse = warehouse;
        this.price = price;
    }


    @Override
    public String toString() {
        return title + " " + price + "€";
    }

}