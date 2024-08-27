package com.coursework.eshopkursinisbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "customer_order")
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dateCreated;
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager responsibleManager;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Embedded
    private ShoppingCart cart = new ShoppingCart();

    @OneToMany
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "order_id")
    private List<BoardGame> inOrderBoardGames;
    @OneToMany
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "order_id")
    private List<Puzzle> inOrderPuzzles;
    @OneToMany
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "order_id")
    private List<Dice> inOrderDices;

    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> orderChat;

    public CustomerOrder() {
    }

    public CustomerOrder(int id, LocalDate dateCreated, double totalPrice, Customer customer, OrderStatus orderStatus, ShoppingCart cart, List<BoardGame> inOrderBoardGames, List<Puzzle> inOrderPuzzles, List<Dice> inOrderDices) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.totalPrice = totalPrice;
        this.customer = customer;
        this.orderStatus = orderStatus;
        this.cart = cart;
        this.inOrderBoardGames = inOrderBoardGames;
        this.inOrderPuzzles = inOrderPuzzles;
        this.inOrderDices = inOrderDices;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        if (inOrderPuzzles != null) products.addAll(inOrderPuzzles);
        if (inOrderBoardGames != null) products.addAll(inOrderBoardGames);
        if (inOrderDices != null) products.addAll(inOrderDices);
        return products;
    }

}
