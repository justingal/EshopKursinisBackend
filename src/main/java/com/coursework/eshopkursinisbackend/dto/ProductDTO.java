package com.coursework.eshopkursinisbackend.dto;

import com.coursework.eshopkursinisbackend.model.Warehouse;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    @NotBlank(message = "ID is required")
    private int id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "Price is required")
    private double price;

    @NotBlank(message = "Warehouse is required")
    private Warehouse warehouse;
}
