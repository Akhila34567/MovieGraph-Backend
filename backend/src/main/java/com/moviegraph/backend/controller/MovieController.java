package com.moviegraph.backend.controller;

import com.moviegraph.backend.dto.MovieRequest;
import com.moviegraph.backend.dto.MovieResponse;
import com.moviegraph.backend.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // Create Movie
    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(
            @Valid @RequestBody MovieRequest request) {

        return ResponseEntity.ok(movieService.createMovie(request));
    }

    // Get All Movies
    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {

        return ResponseEntity.ok(movieService.getAllMovies());
    }

    // Get Movie By ID
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieResponse> getMovieById(
            @PathVariable String movieId) {

        return ResponseEntity.ok(movieService.getMovieById(movieId));
    }

    // Update Movie
    @PutMapping("/{movieId}")
    public ResponseEntity<MovieResponse> updateMovie(
            @PathVariable String movieId,
            @Valid @RequestBody MovieRequest request) {

        return ResponseEntity.ok(
                movieService.updateMovie(movieId, request)
        );
    }

    // Delete Movie
    @DeleteMapping("/{movieId}")
    public ResponseEntity<String> deleteMovie(
            @PathVariable String movieId) {

        movieService.deleteMovie(movieId);

        return ResponseEntity.ok("Movie deleted successfully.");
    }

}