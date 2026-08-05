package com.moviegraph.backend.repository;

import com.moviegraph.backend.dto.RelationshipRequest;
import com.moviegraph.backend.model.Movie;
import com.moviegraph.backend.model.Person;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import org.springframework.stereotype.Repository;
import com.moviegraph.backend.model.Genre;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RelationshipRepository {

    private final Driver driver;

    public RelationshipRepository(Driver driver) {
        this.driver = driver;
    }


    public void createActedInRelationship(RelationshipRequest request) {

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (p:Person {personId: $personId})
                    MATCH (m:Movie {movieId: $movieId})
                    MERGE (p)-[:ACTED_IN]->(m)
                    """;

            session.run(
                    cypher,
                    Values.parameters(
                            "personId", request.getPersonId(),
                            "movieId", request.getMovieId()
                    )
            );
        }
    }

    public void createDirectedRelationship(RelationshipRequest request) {

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (p:Person {personId: $personId})
                    MATCH (m:Movie {movieId: $movieId})
                    MERGE (p)-[:DIRECTED]->(m)
                    """;

            session.run(
                    cypher,
                    Values.parameters(
                            "personId", request.getPersonId(),
                            "movieId", request.getMovieId()
                    )
            );
        }
    }

    public List<Person> getActorsByMovie(String movieId) {

        List<Person> actors = new ArrayList<>();

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (p:Person)-[:ACTED_IN]->(m:Movie {movieId:$movieId})
                    RETURN p
                    ORDER BY p.name
                    """;

            Result result = session.run(
                    cypher,
                    Values.parameters("movieId", movieId)
            );

            while (result.hasNext()) {

                Record record = result.next();

                var node = record.get("p").asNode();

                actors.add(new Person(
                        node.get("personId").asString(),
                        node.get("name").asString(),
                        node.get("birthYear").asInt(),
                        node.get("nationality").asString(),
                        node.get("role").asString()
                ));
            }
        }

        return actors;
    }

    
    public List<Movie> getMoviesByPerson(String personId) {

        List<Movie> movies = new ArrayList<>();

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (p:Person {personId:$personId})-[:ACTED_IN]->(m:Movie)
                    RETURN m
                    ORDER BY m.title
                    """;

            Result result = session.run(
                    cypher,
                    Values.parameters("personId", personId)
            );

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

    
    public List<Person> getDirectorsByMovie(String movieId) {

        List<Person> directors = new ArrayList<>();

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (p:Person)-[:DIRECTED]->(m:Movie {movieId:$movieId})
                    RETURN p
                    ORDER BY p.name
                    """;

            Result result = session.run(
                    cypher,
                    Values.parameters("movieId", movieId)
            );

            while (result.hasNext()) {

                Record record = result.next();

                var node = record.get("p").asNode();

                directors.add(new Person(
                        node.get("personId").asString(),
                        node.get("name").asString(),
                        node.get("birthYear").asInt(),
                        node.get("nationality").asString(),
                        node.get("role").asString()
                ));
            }
        }

        return directors;
    }

    
    public List<Movie> getMoviesDirectedByPerson(String personId) {

        List<Movie> movies = new ArrayList<>();

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (p:Person {personId:$personId})-[:DIRECTED]->(m:Movie)
                    RETURN m
                    ORDER BY m.title
                    """;

            Result result = session.run(
                    cypher,
                    Values.parameters("personId", personId)
            );

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
     public void createHasGenreRelationship(RelationshipRequest request) {

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (m:Movie {movieId:$movieId})
                    MATCH (g:Genre {genreId:$genreId})
                    MERGE (m)-[:HAS_GENRE]->(g)
                    """;

            session.run(
                    cypher,
                    Values.parameters(
                            "movieId", request.getMovieId(),
                            "genreId", request.getGenreId()
                    )
            );
        }
    }
    public List<Genre> getGenresByMovie(String movieId) {

        List<Genre> genres = new ArrayList<>();

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (m:Movie {movieId:$movieId})-[:HAS_GENRE]->(g:Genre)
                    RETURN g
                    ORDER BY g.name
                    """;

            Result result = session.run(
                    cypher,
                    Values.parameters("movieId", movieId)
            );

            while (result.hasNext()) {

                Record record = result.next();

                var node = record.get("g").asNode();

                genres.add(new Genre(
                        node.get("genreId").asString(),
                        node.get("name").asString()
                ));
            }
        }

        return genres;
    }

    public List<Movie> getMoviesByGenre(String genreId) {

        List<Movie> movies = new ArrayList<>();

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (m:Movie)-[:HAS_GENRE]->(g:Genre {genreId:$genreId})
                    RETURN m
                    ORDER BY m.title
                    """;

            Result result = session.run(
                    cypher,
                    Values.parameters("genreId", genreId)
            );

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
    // ==========================
// RECOMMEND MOVIES
// ==========================

public List<Movie> recommendMovies(String movieId) {

    List<Movie> movies = new ArrayList<>();

    try (Session session = driver.session()) {

        String cypher = """
                MATCH (m:Movie {movieId:$movieId})-[:HAS_GENRE]->(g:Genre)
                MATCH (recommended:Movie)-[:HAS_GENRE]->(g)
                WHERE recommended.movieId <> $movieId
                RETURN DISTINCT recommended
                ORDER BY recommended.rating DESC
                """;

        Result result = session.run(
                cypher,
                Values.parameters("movieId", movieId)
        );

        while (result.hasNext()) {

            Record record = result.next();

            var node = record.get("recommended").asNode();

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

}
