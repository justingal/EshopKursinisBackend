package com.coursework.eshopkursinisbackend.repos;

import com.coursework.eshopkursinisbackend.model.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardGameRepository extends JpaRepository<BoardGame, Integer> {
}