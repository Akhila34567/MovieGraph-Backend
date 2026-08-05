package com.moviegraph.backend.controller;

import com.moviegraph.backend.dto.GenreRequest;
import com.moviegraph.backend.dto.GenreResponse;
import com.moviegraph.backend.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@CrossOrigin(origins = "*")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    // Create Genre
    @PostMapping
    public ResponseEntity<GenreResponse> createGenre(
            @Valid @RequestBody GenreRequest request) {

        return ResponseEntity.ok(
                genreService.createGenre(request)
        );
    }

    // Get All Genres
    @GetMapping
    public ResponseEntity<List<GenreResponse>> getAllGenres() {

        return ResponseEntity.ok(
                genreService.getAllGenres()
        );
    }

    // Get Genre By ID
    @GetMapping("/{genreId}")
    public ResponseEntity<GenreResponse> getGenreById(
            @PathVariable String genreId) {

        return ResponseEntity.ok(
                genreService.getGenreById(genreId)
        );
    }

    // Update Genre
    @PutMapping("/{genreId}")
    public ResponseEntity<GenreResponse> updateGenre(
            @PathVariable String genreId,
            @Valid @RequestBody GenreRequest request) {

        return ResponseEntity.ok(
                genreService.updateGenre(genreId, request)
        );
    }

    // Delete Genre
    @DeleteMapping("/{genreId}")
    public ResponseEntity<String> deleteGenre(
            @PathVariable String genreId) {

        genreService.deleteGenre(genreId);

        return ResponseEntity.ok(
                "Genre deleted successfully."
        );
    }
}