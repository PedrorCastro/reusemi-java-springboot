package com.reusemi.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/health")
    public String healthCheck() {
        try {
            String dbStatus = jdbcTemplate.queryForObject("SELECT 'MySQL CONNECTED'", String.class);
            return "APPLICATION OK | " + dbStatus + " | " + new java.util.Date();
        } catch (Exception e) {
            return "DATABASE ERROR: " + e.getMessage();
        }
    }
}