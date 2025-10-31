package com.reusemi.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class ImageController {

    @GetMapping(value = "web/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String name) throws IOException {
        InputStream in = getClass().getResourceAsStream("/static/img/web/" + name);
        if (in == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] bytes = in.readAllBytes();
        return ResponseEntity.ok(bytes);
    }
}
