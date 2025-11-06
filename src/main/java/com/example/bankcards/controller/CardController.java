package com.example.bankcards.controller;

import com.example.bankcards.dto.card.CardRequest;
import com.example.bankcards.dto.card.CardResponse;
import com.example.bankcards.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private static final Logger log = LoggerFactory.getLogger(CardController.class);
    private final CardService cardService;

    @Autowired
    public CardController (CardService cardService) {
        this.cardService = cardService;
    }

    /// Реализация CRUD

    @PostMapping("/newcard")
    public ResponseEntity<CardResponse> createCard(@RequestBody CardRequest request) {
        log.info("Receive request to create a new card");
        CardResponse cardResponse = cardService.createCard(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardResponse);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getCardById(@PathVariable("id") Long id) {
        log.info("Called getCardById: id = {}", id);
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(cardService.getCard(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
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


    @GetMapping
    public ResponseEntity<List<CardResponse>> getAllByOwner(
            @RequestParam String owner
    ) {
        log.info("Called getAllCards: owner = {}", owner);
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(cardService.getCardsByOwner(owner));
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


}
