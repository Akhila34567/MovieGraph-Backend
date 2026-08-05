package com.moviegraph.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private String personId;

    private String name;

    private Integer birthYear;

    private String nationality;

    private String role;

}