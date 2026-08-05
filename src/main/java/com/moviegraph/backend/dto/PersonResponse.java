package com.moviegraph.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse {

    private String personId;

    private String name;

    private Integer birthYear;

    private String nationality;

    private String role;

}