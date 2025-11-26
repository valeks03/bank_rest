package com.example.bankcards.controller;

import com.example.bankcards.dto.card.CardRequest;
import com.example.bankcards.dto.card.CardResponse;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Tag(name = "Сontroller for card control by admin", description = "Only admin can do crud operations")
@RequestMapping("/admin")
public class AdminController {

    private final CardService cardService;
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);


    public AdminController(CardService cardService) {
        this.cardService = cardService;
    }



    /// CRUD для управления картами
    @PostMapping("/newcard")
    @Operation(summary = "Create new card for user")
    public ResponseEntity<CardResponse> createCard(@RequestBody CardRequest request) {
        log.info("Receive request to create a new card");
        CardResponse cardResponse = cardService.createCard(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardResponse);
    }

    @GetMapping("all")
    @Operation(summary = "Get all existed cards")
    public ResponseEntity<List<CardResponse>> getAllCards() {
        log.info("Called getAllCards");
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(cardService.getAllCards());
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @Operation(summary = "Get any card by id")
    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getCardById(@PathVariable("id") Long id) {
        log.info("Called getCardById: id = {}", id);
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(cardService.getCardById(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @Operation(summary = "Block card by id")
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

    @Operation(summary = "Update existed card for user")
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

    @Operation(summary = "Get all the cards of a specific user ")
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
}
