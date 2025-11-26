package com.example.bankcards.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Endpoint for checking service availability")
@RestController
public class PingController {

    @GetMapping("/ping")
    public ResponseEntity<Void> ping() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
