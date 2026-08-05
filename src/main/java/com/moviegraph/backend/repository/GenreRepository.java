package com.moviegraph.backend.repository;

import com.moviegraph.backend.model.Genre;
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
public class GenreRepository {

    private final Driver driver;

    public GenreRepository(Driver driver) {
        this.driver = driver;
    }

    // Create Genre
    public Genre create(Genre genre) {

        String genreId = UUID.randomUUID().toString();

        try (Session session = driver.session()) {

            String cypher = """
                    CREATE (g:Genre {
                        genreId: $genreId,
                        name: $name
                    })
                    RETURN g
                    """;

            Result result = session.run(
                    cypher,
                    Values.parameters(
                            "genreId", genreId,
                            "name", genre.getName()
                    )
            );

            Record record = result.single();
            var node = record.get("g").asNode();

            return new Genre(
                    node.get("genreId").asString(),
                    node.get("name").asString()
            );
        }
    }

    // Get All Genres
    public List<Genre> findAll() {

        List<Genre> genres = new ArrayList<>();

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (g:Genre)
                    RETURN g
                    ORDER BY g.name
                    """;

            Result result = session.run(cypher);

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

    // Get Genre By ID
    public Genre findById(String genreId) {

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (g:Genre {genreId:$genreId})
                    RETURN g
                    """;

            Result result = session.run(
                    cypher,
                    Values.parameters("genreId", genreId)
            );

            if (!result.hasNext()) {
                return null;
            }

            Record record = result.single();
            var node = record.get("g").asNode();

            return new Genre(
                    node.get("genreId").asString(),
                    node.get("name").asString()
            );
        }
    }

    // Update Genre
    public Genre update(String genreId, Genre genre) {

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (g:Genre {genreId:$genreId})
                    SET g.name = $name
                    RETURN g
                    """;

            Result result = session.run(
                    cypher,
                    Values.parameters(
                            "genreId", genreId,
                            "name", genre.getName()
                    )
            );

            if (!result.hasNext()) {
                return null;
            }

            Record record = result.single();
            var node = record.get("g").asNode();

            return new Genre(
                    node.get("genreId").asString(),
                    node.get("name").asString()
            );
        }
    }

    // Delete Genre
public boolean delete(String genreId) {

    try (Session session = driver.session()) {

        String cypher = """
                MATCH (g:Genre {genreId:$genreId})
                DETACH DELETE g
                RETURN true AS deleted
                """;

        Result result = session.run(
                cypher,
                Values.parameters("genreId", genreId)
        );

        return result.hasNext();
    }
}
}
