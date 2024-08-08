package com.coursework.eshopkursinisbackend.repos;

import com.coursework.eshopkursinisbackend.model.Puzzle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuzzleRepository extends JpaRepository<Puzzle, Integer> {
}