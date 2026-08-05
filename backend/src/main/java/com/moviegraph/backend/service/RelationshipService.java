package com.moviegraph.backend.service;

import com.moviegraph.backend.dto.RelationshipRequest;
import com.moviegraph.backend.model.Genre;
import com.moviegraph.backend.model.Movie;
import com.moviegraph.backend.model.Person;
import com.moviegraph.backend.repository.RelationshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationshipService {

    private final RelationshipRepository relationshipRepository;

    public RelationshipService(RelationshipRepository relationshipRepository) {
        this.relationshipRepository = relationshipRepository;
    }


    public void createActedInRelationship(RelationshipRequest request) {
        relationshipRepository.createActedInRelationship(request);
    }

    public void createDirectedRelationship(RelationshipRequest request) {
        relationshipRepository.createDirectedRelationship(request);
    }

    public void createHasGenreRelationship(RelationshipRequest request) {
        relationshipRepository.createHasGenreRelationship(request);
    }


    public List<Person> getActorsByMovie(String movieId) {
        return relationshipRepository.getActorsByMovie(movieId);
    }

    public List<Movie> getMoviesByPerson(String personId) {
        return relationshipRepository.getMoviesByPerson(personId);
    }

    public List<Person> getDirectorsByMovie(String movieId) {
        return relationshipRepository.getDirectorsByMovie(movieId);
    }

    public List<Movie> getMoviesDirectedByPerson(String personId) {
        return relationshipRepository.getMoviesDirectedByPerson(personId);
    }

    public List<Genre> getGenresByMovie(String movieId) {
        return relationshipRepository.getGenresByMovie(movieId);
    }

    public List<Movie> getMoviesByGenre(String genreId) {
        return relationshipRepository.getMoviesByGenre(genreId);
    }

    public List<Movie> recommendMovies(String movieId) {
        return relationshipRepository.recommendMovies(movieId);
    }
}