package com.coursework.eshopkursinisbackend.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Embeddable

public class ShoppingCart {

    @ElementCollection
    private List<CartItem> items = new ArrayList<>();


    public ShoppingCart() {
    }

    public ShoppingCart(List<CartItem> items) {
        this.items = items;
    }

    public void addItem(CartItem newItem) {
        for (CartItem item : items) {
            if (item.getProductId() == newItem.getProductId()) {
                return;
            }
        }
        items.add(newItem);
    }

    public void removeItem(int productId) {
        items.removeIf(item -> item.getProductId() == productId);
    }


}