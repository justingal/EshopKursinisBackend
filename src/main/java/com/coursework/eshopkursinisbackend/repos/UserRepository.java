package com.coursework.eshopkursinisbackend.repos;

import com.coursework.eshopkursinisbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
