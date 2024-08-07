package com.coursework.eshopkursinisbackend.repos;

import com.coursework.eshopkursinisbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
