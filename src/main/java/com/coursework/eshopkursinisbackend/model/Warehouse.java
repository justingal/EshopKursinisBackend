package com.coursework.eshopkursinisbackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.util.List;


@Setter
@Getter
@Entity
public class Warehouse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String address;
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<Puzzle> inStockPuzzles;
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<BoardGame> inStockBoardGames;
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<Dice> inStockDices;

    public Warehouse() {
    }

    public Warehouse(int id, String title, String address, List<Puzzle> inStockPuzzles, List<BoardGame> inStockBoardGames, List<Dice> inStockDices) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.inStockPuzzles = inStockPuzzles;
        this.inStockBoardGames = inStockBoardGames;
        this.inStockDices = inStockDices;
    }


    public Warehouse(String title, String address) {
        this.title = title;
        this.address = address;
    }

    @Override
    public String toString() {
        return title;
    }


}