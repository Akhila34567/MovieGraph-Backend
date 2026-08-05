package com.moviegraph.backend.controller;

import com.moviegraph.backend.dto.RelationshipRequest;
import com.moviegraph.backend.model.Genre;
import com.moviegraph.backend.model.Movie;
import com.moviegraph.backend.model.Person;
import com.moviegraph.backend.service.RelationshipService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RelationshipController {

    private final RelationshipService relationshipService;

    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }


    @PostMapping("/relationships/acted-in")
    public ResponseEntity<String> createActedInRelationship(
            @Valid @RequestBody RelationshipRequest request) {

        relationshipService.createActedInRelationship(request);
        return ResponseEntity.ok("ACTED_IN relationship created successfully.");
    }

    @PostMapping("/relationships/directed")
    public ResponseEntity<String> createDirectedRelationship(
            @Valid @RequestBody RelationshipRequest request) {

        relationshipService.createDirectedRelationship(request);
        return ResponseEntity.ok("DIRECTED relationship created successfully.");
    }

    @PostMapping("/relationships/has-genre")
    public ResponseEntity<String> createHasGenreRelationship(
            @Valid @RequestBody RelationshipRequest request) {

        relationshipService.createHasGenreRelationship(request);
        return ResponseEntity.ok("HAS_GENRE relationship created successfully.");
    }


    @GetMapping("/movies/{movieId}/actors")
    public ResponseEntity<List<Person>> getActorsByMovie(@PathVariable String movieId) {
        return ResponseEntity.ok(relationshipService.getActorsByMovie(movieId));
    }

    @GetMapping("/persons/{personId}/movies")
    public ResponseEntity<List<Movie>> getMoviesByPerson(@PathVariable String personId) {
        return ResponseEntity.ok(relationshipService.getMoviesByPerson(personId));
    }

    @GetMapping("/movies/{movieId}/directors")
    public ResponseEntity<List<Person>> getDirectorsByMovie(@PathVariable String movieId) {
        return ResponseEntity.ok(relationshipService.getDirectorsByMovie(movieId));
    }

    @GetMapping("/persons/{personId}/directed-movies")
    public ResponseEntity<List<Movie>> getMoviesDirectedByPerson(@PathVariable String personId) {
        return ResponseEntity.ok(relationshipService.getMoviesDirectedByPerson(personId));
    }

    @GetMapping("/movies/{movieId}/genres")
    public ResponseEntity<List<Genre>> getGenresByMovie(@PathVariable String movieId) {
        return ResponseEntity.ok(relationshipService.getGenresByMovie(movieId));
    }

    @GetMapping("/genres/{genreId}/movies")
    public ResponseEntity<List<Movie>> getMoviesByGenre(@PathVariable String genreId) {
        return ResponseEntity.ok(relationshipService.getMoviesByGenre(genreId));
    }

    @GetMapping("/recommendations/{movieId}")
    public ResponseEntity<List<Movie>> recommendMovies(@PathVariable String movieId) {
        return ResponseEntity.ok(
                relationshipService.recommendMovies(movieId)
        );
    }
}