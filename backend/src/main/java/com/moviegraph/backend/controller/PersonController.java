package com.moviegraph.backend.controller;

import com.moviegraph.backend.dto.PersonRequest;
import com.moviegraph.backend.dto.PersonResponse;
import com.moviegraph.backend.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@CrossOrigin(origins = "*")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // Create Person
    @PostMapping
    public ResponseEntity<PersonResponse> createPerson(
            @Valid @RequestBody PersonRequest request) {

        return ResponseEntity.ok(
                personService.createPerson(request)
        );
    }

    // Get All Persons
    @GetMapping
    public ResponseEntity<List<PersonResponse>> getAllPersons() {

        return ResponseEntity.ok(
                personService.getAllPersons()
        );
    }

    // Get Person By ID
    @GetMapping("/{personId}")
    public ResponseEntity<PersonResponse> getPersonById(
            @PathVariable String personId) {

        return ResponseEntity.ok(
                personService.getPersonById(personId)
        );
    }

    // Update Person
    @PutMapping("/{personId}")
    public ResponseEntity<PersonResponse> updatePerson(
            @PathVariable String personId,
            @Valid @RequestBody PersonRequest request) {

        return ResponseEntity.ok(
                personService.updatePerson(personId, request)
        );
    }

    // Delete Person
    @DeleteMapping("/{personId}")
    public ResponseEntity<String> deletePerson(
            @PathVariable String personId) {

        personService.deletePerson(personId);

        return ResponseEntity.ok(
                "Person deleted successfully."
        );
    }
}
