package com.moviegraph.backend.dto;

import lombok.Data;

@Data
public class RelationshipRequest {

    private String personId;

    private String movieId;

    private String genreId;

}