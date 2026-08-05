package com.moviegraph.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {

    private String movieId;

    private String title;

    private Integer year;

    private Double rating;

    private String language;

}
