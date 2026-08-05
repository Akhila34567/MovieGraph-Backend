package com.moviegraph.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenreResponse {

    private String genreId;
    private String name;
}