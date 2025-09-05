package com.reusemi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/foster")
    public String foster() {
        return "foster";
    }

    // favicon.png and other static assets are served automatically from /static
}
