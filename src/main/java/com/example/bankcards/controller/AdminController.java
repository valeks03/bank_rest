package com.example.bankcards.controller;

import com.example.bankcards.dto.card.CardRequest;
import com.example.bankcards.dto.card.CardResponse;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    /// CRUD для пользователей (Админ может управлять пользователями)






    /// CRUD для управления картами

    @PostMapping("/newcard")
    public ResponseEntity<CardResponse> createCard(@RequestBody CardRequest request) {
        log.info("Receive request to create a new card");
        CardResponse cardResponse = cardService.createCard(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardResponse);
    }

    @GetMapping("all")
    public ResponseEntity<List<CardResponse>> getAllCards() {
        log.info("Called getAllCards");
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(cardService.getAllCards());
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CardResponse> deleteCard (
            @PathVariable Long id
    ) {
        log.info("Called delete card: id = {}", id);
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(cardService.deleteCard(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<CardResponse> updateCard (
            @PathVariable Long id,
            @RequestBody CardRequest request
    ) {
        log.info("Called update Card: id = {}", id);
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(cardService.updateCard(id, request));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

}
