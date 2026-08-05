package com.moviegraph.backend.service;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private final Driver driver;

    public TestService(Driver driver) {
        this.driver = driver;
    }

    public String testConnection() {

        try (Session session = driver.session()) {

            Result result = session.run(
                    "RETURN 'Connected to CognoDB' AS message"
            );

            Record record = result.single();

            return record.get("message").asString();
        }
    }
}
