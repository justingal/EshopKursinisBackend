package com.coursework.eshopkursinisbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardGameDTO extends ProductDTO {
    @NotBlank(message = "Players Quantity is required")
    private String playersQuantity;

    @NotBlank(message = "GameDuration is required")
    private String gameDuration;
}
