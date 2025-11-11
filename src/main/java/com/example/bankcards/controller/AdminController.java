package com.example.bankcards.controller;

import com.example.bankcards.dto.card.CardRequest;
import com.example.bankcards.dto.card.CardResponse;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private final CardService cardService;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);


    public AdminController(CardService cardService, UserRepository userRepository) {
        this.cardService = cardService;
        this.userRepository = userRepository;
    }


    @PostMapping("/newcard")
    public ResponseEntity<CardResponse> createCard(@RequestBody CardRequest request) {
        log.info("Receive request to create a new card");
        CardResponse cardResponse = cardService.createCard(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardResponse);
    }
}
