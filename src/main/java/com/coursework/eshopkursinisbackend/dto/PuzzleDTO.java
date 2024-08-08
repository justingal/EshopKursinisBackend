package com.coursework.eshopkursinisbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PuzzleDTO extends ProductDTO {
    @NotBlank(message = "Pieces quantity is required")
    private int piecesQuantity;

    @NotBlank(message = "Puzzle size is required")
    private String puzzleSize;

    @NotBlank(message = "Puzzle material is required")
    private String puzzleMaterial;

}
