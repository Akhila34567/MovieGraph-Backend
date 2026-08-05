package com.moviegraph.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PersonRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 1900, message = "Invalid birth year")
    @Max(value = 2100, message = "Invalid birth year")
    private Integer birthYear;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    @NotBlank(message = "Role is required")
    private String role;

}