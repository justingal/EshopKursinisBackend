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
public class Puzzle extends Product {

    private int piecesQuantity;
    private String puzzleSize;
    private String puzzleMaterial;

    @OneToMany(mappedBy = "puzzle", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference("puzzle-review")
    private List<Review> reviews;
    @ManyToOne
    @JsonBackReference("order-puzzle")
    private CustomerOrder customerOrder;

    public Puzzle() {

    }

    public Puzzle(String title, String description, String author, Warehouse warehouse, double price) {
        super(title, description, author, warehouse, price);
    }

    public Puzzle(String title, String description, String author, Warehouse warehouse, double price, int id, int piecesQuantity, String puzzleSize, String puzzleMaterial, List<Review> reviews) {
        super(title, description, author, warehouse, price);
        this.id = id;
        this.piecesQuantity = piecesQuantity;
        this.puzzleSize = puzzleSize;
        this.puzzleMaterial = puzzleMaterial;
        this.reviews = reviews;
    }


    public Puzzle(int id, String title, String description, String author, double price, int piecesQuantity, String puzzleSize, String puzzleMaterial) {
        super(id, title, description, author, price);
        this.piecesQuantity = piecesQuantity;
        this.puzzleSize = puzzleSize;
        this.puzzleMaterial = puzzleMaterial;
    }

    public Puzzle(String title, String description, String author, double price, int piecesQuantity, String puzzleSize, String puzzleMaterial) {
        super(title, description, author, price);
        this.piecesQuantity = piecesQuantity;
        this.puzzleSize = puzzleSize;
        this.puzzleMaterial = puzzleMaterial;
    }

    public Puzzle(String title, String description, String author, Warehouse warehouse, double price, int piecesQuantity, String puzzleSize, String puzzleMaterial) {
        super(title, description, author, warehouse, price);
        this.piecesQuantity = piecesQuantity;
        this.puzzleSize = puzzleSize;
        this.puzzleMaterial = puzzleMaterial;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }


}