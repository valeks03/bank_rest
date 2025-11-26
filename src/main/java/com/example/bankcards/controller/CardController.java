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

    public CardController (CardService cardService) {
        this.cardService = cardService;
    }


    /// Этот метод вероятно тоже отправится к admin controller
//    @GetMapping("/{id}")
//    public ResponseEntity<CardResponse> getCardById(@PathVariable("id") Long id) {
//        log.info("Called getCardById: id = {}", id);
//        try {
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(cardService.getCard(id));
//        } catch (Exception e) {
//            return ResponseEntity.status(404).build();
//        }
//    }




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
