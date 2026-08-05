package com.moviegraph.backend.service;

import com.moviegraph.backend.dto.GenreRequest;
import com.moviegraph.backend.dto.GenreResponse;
import com.moviegraph.backend.exception.ResourceNotFoundException;
import com.moviegraph.backend.model.Genre;
import com.moviegraph.backend.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    // Create Genre
    public GenreResponse createGenre(GenreRequest request) {

        Genre genre = new Genre(
                null,
                request.getName()
        );

        Genre savedGenre = genreRepository.create(genre);

        return convertToResponse(savedGenre);
    }

    // Get All Genres
    public List<GenreResponse> getAllGenres() {

        return genreRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Get Genre By ID
    public GenreResponse getGenreById(String genreId) {

        Genre genre = genreRepository.findById(genreId);

        if (genre == null) {
            throw new ResourceNotFoundException(
                    "Genre not found with ID: " + genreId
            );
        }

        return convertToResponse(genre);
    }

    // Update Genre
    public GenreResponse updateGenre(String genreId, GenreRequest request) {

        Genre genre = new Genre(
                genreId,
                request.getName()
        );

        Genre updatedGenre = genreRepository.update(genreId, genre);

        if (updatedGenre == null) {
            throw new ResourceNotFoundException(
                    "Genre not found with ID: " + genreId
            );
        }

        return convertToResponse(updatedGenre);
    }

    // Delete Genre
    public void deleteGenre(String genreId) {

        boolean deleted = genreRepository.delete(genreId);

        if (!deleted) {
            throw new ResourceNotFoundException(
                    "Genre not found with ID: " + genreId
            );
        }
    }

    // Convert Entity to DTO
    private GenreResponse convertToResponse(Genre genre) {

        return new GenreResponse(
                genre.getGenreId(),
                genre.getName()
        );
    }
}
