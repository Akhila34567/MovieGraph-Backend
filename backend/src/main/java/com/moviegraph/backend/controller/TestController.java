package com.moviegraph.backend.controller;

import com.moviegraph.backend.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/api/test")
    public Map<String, String> test() {

        return Map.of(
                "status", "SUCCESS",
                "message", testService.testConnection()
        );
    }
}