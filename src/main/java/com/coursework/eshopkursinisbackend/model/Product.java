package com.coursework.eshopkursinisbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Setter
@Getter
@MappedSuperclass
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
    @SequenceGenerator(name = "product_seq_gen", sequenceName = "product_sequence", allocationSize = 1)
    int id;
    String title;
    String description;
    String author;
    double price;
    @ManyToOne
    @JsonBackReference
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