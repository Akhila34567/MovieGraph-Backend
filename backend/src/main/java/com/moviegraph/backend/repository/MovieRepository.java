package com.moviegraph.backend.repository;

import com.moviegraph.backend.model.Movie;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class MovieRepository {

    private final Driver driver;

    public MovieRepository(Driver driver) {
        this.driver = driver;
    }

    // Create Movie
    public Movie create(Movie movie) {

        String movieId = UUID.randomUUID().toString();

        try (Session session = driver.session()) {

            String cypher = """
                    CREATE (m:Movie {
                        movieId: $movieId,
                        title: $title,
                        year: $year,
                        rating: $rating,
                        language: $language
                    })
                    RETURN m
                    """;

            Result result = session.run(
                    cypher,
                    Values.parameters(
                            "movieId", movieId,
                            "title", movie.getTitle(),
                            "year", movie.getYear(),
                            "rating", movie.getRating(),
                            "language", movie.getLanguage()
                    )
            );

            Record record = result.single();

            var node = record.get("m").asNode();

            return new Movie(
                    node.get("movieId").asString(),
                    node.get("title").asString(),
                    node.get("year").asInt(),
                    node.get("rating").asDouble(),
                    node.get("language").asString()
            );
        }
    }

    // Get All Movies
    public List<Movie> findAll() {

        List<Movie> movies = new ArrayList<>();

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (m:Movie)
                    RETURN m
                    ORDER BY m.title
                    """;

            Result result = session.run(cypher);

            while (result.hasNext()) {

                Record record = result.next();

                var node = record.get("m").asNode();

                movies.add(new Movie(
                        node.get("movieId").asString(),
                        node.get("title").asString(),
                        node.get("year").asInt(),
                        node.get("rating").asDouble(),
                        node.get("language").asString()
                ));
            }
        }

        return movies;
    }

    // Get Movie By ID
    public Movie findById(String movieId) {

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (m:Movie {movieId: $movieId})
                    RETURN m
                    """;

            Result result = session.run(
                    cypher,
                    Values.parameters("movieId", movieId)
            );

            if (!result.hasNext()) {
                return null;
            }

            Record record = result.single();

            var node = record.get("m").asNode();

            return new Movie(
                    node.get("movieId").asString(),
                    node.get("title").asString(),
                    node.get("year").asInt(),
                    node.get("rating").asDouble(),
                    node.get("language").asString()
            );
        }
    }

    // Update Movie
    public Movie update(String movieId, Movie movie) {

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (m:Movie {movieId: $movieId})
                    SET
                        m.title = $title,
                        m.year = $year,
                        m.rating = $rating,
                        m.language = $language
                    RETURN m
                    """;

            Result result = session.run(
                    cypher,
                    Values.parameters(
                            "movieId", movieId,
                            "title", movie.getTitle(),
                            "year", movie.getYear(),
                            "rating", movie.getRating(),
                            "language", movie.getLanguage()
                    )
            );

            if (!result.hasNext()) {
                return null;
            }

            Record record = result.single();

            var node = record.get("m").asNode();

            return new Movie(
                    node.get("movieId").asString(),
                    node.get("title").asString(),
                    node.get("year").asInt(),
                    node.get("rating").asDouble(),
                    node.get("language").asString()
            );
        }
    }

    // Delete Movie
    public boolean delete(String movieId) {

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (m:Movie {movieId: $movieId})
                    WITH m
                    LIMIT 1
                    DELETE m
                    RETURN 1 AS deleted
                    """;

            Result result = session.run(
                    cypher,
                    Values.parameters("movieId", movieId)
            );

            return result.hasNext();
        }
    }
}