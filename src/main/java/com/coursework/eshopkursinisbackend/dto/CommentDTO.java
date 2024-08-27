package com.coursework.eshopkursinisbackend.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentDTO {

    @NotBlank(message = "ID is required")
    private int id;

    @NotBlank(message = "commentBody is required")
    private String commentBody;

    @NotBlank(message = "commentTitle is required")
    private String commentTitle;

    @NotBlank(message = "dateCreated is required")
    private LocalDate dateCreated;


}
