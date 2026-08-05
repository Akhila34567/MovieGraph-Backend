package com.moviegraph.backend.repository;

import com.moviegraph.backend.model.Person;
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
public class PersonRepository {

    private final Driver driver;

    public PersonRepository(Driver driver) {
        this.driver = driver;
    }

    // Create Person
    public Person create(Person person) {

        String personId = UUID.randomUUID().toString();

        try (Session session = driver.session()) {

            String cypher = """
                    CREATE (p:Person {
                        personId: $personId,
                        name: $name,
                        birthYear: $birthYear,
                        nationality: $nationality,
                        role: $role
                    })
                    RETURN p
                    """;

            Result result = session.run(
                    cypher,
                    Values.parameters(
                            "personId", personId,
                            "name", person.getName(),
                            "birthYear", person.getBirthYear(),
                            "nationality", person.getNationality(),
                            "role", person.getRole()
                    )
            );

            Record record = result.single();

            var node = record.get("p").asNode();

            return new Person(
                    node.get("personId").asString(),
                    node.get("name").asString(),
                    node.get("birthYear").asInt(),
                    node.get("nationality").asString(),
                    node.get("role").asString()
            );
        }
    }

    // Get All Persons
    public List<Person> findAll() {

        List<Person> persons = new ArrayList<>();

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (p:Person)
                    RETURN p
                    ORDER BY p.name
                    """;

            Result result = session.run(cypher);

            while (result.hasNext()) {

                Record record = result.next();

                var node = record.get("p").asNode();

                persons.add(new Person(
                        node.get("personId").asString(),
                        node.get("name").asString(),
                        node.get("birthYear").asInt(),
                        node.get("nationality").asString(),
                        node.get("role").asString()
                ));
            }
        }

        return persons;
    }

    // Get Person By ID
    public Person findById(String personId) {

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (p:Person {personId: $personId})
                    RETURN p
                    """;

            Result result = session.run(
                    cypher,
                    Values.parameters("personId", personId)
            );

            if (!result.hasNext()) {
                return null;
            }

            Record record = result.single();

            var node = record.get("p").asNode();

            return new Person(
                    node.get("personId").asString(),
                    node.get("name").asString(),
                    node.get("birthYear").asInt(),
                    node.get("nationality").asString(),
                    node.get("role").asString()
            );
        }
    }

    // Update Person
    public Person update(String personId, Person person) {

        try (Session session = driver.session()) {

            String cypher = """
                    MATCH (p:Person {personId: $personId})
                    SET
                        p.name = $name,
                        p.birthYear = $birthYear,
                        p.nationality = $nationality,
                        p.role = $role
                    RETURN p
                    """;

            Result result = session.run(
                    cypher,
                    Values.parameters(
                            "personId", personId,
                            "name", person.getName(),
                            "birthYear", person.getBirthYear(),
                            "nationality", person.getNationality(),
                            "role", person.getRole()
                    )
            );

            if (!result.hasNext()) {
                return null;
            }

            Record record = result.single();

            var node = record.get("p").asNode();

            return new Person(
                    node.get("personId").asString(),
                    node.get("name").asString(),
                    node.get("birthYear").asInt(),
                    node.get("nationality").asString(),
                    node.get("role").asString()
            );
        }
    }

   // Delete Person
public boolean delete(String personId) {

    try (Session session = driver.session()) {

        String cypher = """
            MATCH (p:Person {personId: $personId})
            OPTIONAL MATCH (p)-[r]-()
            WITH p, collect(r) AS rels
            FOREACH (rel IN rels | DELETE rel)
            DELETE p
            RETURN true AS deleted
            """;

        Result result = session.run(
                cypher,
                Values.parameters("personId", personId)
        );

        return result.hasNext() && result.single().get("deleted").asBoolean();
    }
}
}