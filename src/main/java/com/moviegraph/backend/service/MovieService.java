package com.moviegraph.backend.service;

import com.moviegraph.backend.dto.MovieRequest;
import com.moviegraph.backend.dto.MovieResponse;
import com.moviegraph.backend.exception.ResourceNotFoundException;
import com.moviegraph.backend.model.Movie;
import com.moviegraph.backend.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Create Movie
    public MovieResponse createMovie(MovieRequest request) {

        Movie movie = new Movie(
                null,
                request.getTitle(),
                request.getYear(),
                request.getRating(),
                request.getLanguage()
        );

        Movie savedMovie = movieRepository.create(movie);

        return convertToResponse(savedMovie);
    }

    // Get All Movies
    public List<MovieResponse> getAllMovies() {

        return movieRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Get Movie By ID
    public MovieResponse getMovieById(String movieId) {

        Movie movie = movieRepository.findById(movieId);

        if (movie == null) {
            throw new ResourceNotFoundException(
                    "Movie not found with ID: " + movieId
            );
        }

        return convertToResponse(movie);
    }

    // Update Movie
    public MovieResponse updateMovie(String movieId, MovieRequest request) {

        Movie movie = new Movie(
                movieId,
                request.getTitle(),
                request.getYear(),
                request.getRating(),
                request.getLanguage()
        );

        Movie updatedMovie = movieRepository.update(movieId, movie);

        if (updatedMovie == null) {
            throw new ResourceNotFoundException(
                    "Movie not found with ID: " + movieId
            );
        }

        return convertToResponse(updatedMovie);
    }

    // Delete Movie
    public void deleteMovie(String movieId) {

        boolean deleted = movieRepository.delete(movieId);

        if (!deleted) {
            throw new ResourceNotFoundException(
                    "Movie not found with ID: " + movieId
            );
        }
    }

    // Convert Entity to DTO
    private MovieResponse convertToResponse(Movie movie) {

        return new MovieResponse(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getYear(),
                movie.getRating(),
                movie.getLanguage()
        );
    }
}