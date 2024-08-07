package com.coursework.eshopkursinisbackend.repos;

import com.coursework.eshopkursinisbackend.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Integer> {
}
