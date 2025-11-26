package com.example.bankcards.controller;

import com.example.bankcards.dto.Transfer;
import com.example.bankcards.dto.card.CardRequest;
import com.example.bankcards.dto.card.CardResponse;
import com.example.bankcards.entity.User;
import com.example.bankcards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Controller for card control for users", description = "Operations for common users")
@RestController
@RequestMapping("/cards")
public class CardController {

    private static final Logger log = LoggerFactory.getLogger(CardController.class);
    private final CardService cardService;

    public CardController (CardService cardService) {
        this.cardService = cardService;
    }


    /// Этот метод вероятно тоже отправится к admin controller



    @Operation(summary = "Transfer between user's own cards")
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody Transfer transfer,
                                         @AuthenticationPrincipal User user) {
        try {
            cardService.transferBetweenOwnCards(transfer, user.getUsername());
            return ResponseEntity.ok("Transfer is successful");
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    @Operation(summary = "Get all the user's cards")
    @GetMapping("/my")
    public ResponseEntity<List<CardResponse>> getMyCards(@AuthenticationPrincipal User user) {
        log.info("Called getAllCards: owner = {}", user.getUsername());
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(cardService.getCardsByOwner(user.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @Operation(summary = "Get particular user's card by id")
    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getMyCard(@PathVariable Long id,
                                             @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cardService.getCardByOwnerAndId(id, user.getUsername()));
    }
}
