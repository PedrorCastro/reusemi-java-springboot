package com.reusemi.desktop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/api/status")
    public String getStatus() {
        return "{\"status\": \"Server is running with JavaFX Desktop\"}";
    }
}