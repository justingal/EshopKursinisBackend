package com.coursework.eshopkursinisbackend.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiceDTO extends ProductDTO{
    @NotBlank(message = "Dice number is required")
    private int diceNumber;


}
