package com.moviegraph.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MovieRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @Min(value = 1888, message = "Invalid movie year")
    private Integer year;

    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 10, message = "Rating must be at most 10")
    private Double rating;

    @NotBlank(message = "Language is required")
    private String language;

}
