package com.coursework.eshopkursinisbackend.repos;

import com.coursework.eshopkursinisbackend.model.Dice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiceRepository extends JpaRepository<Dice, Integer> {
}