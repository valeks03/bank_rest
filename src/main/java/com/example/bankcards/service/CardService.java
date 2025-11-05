package com.example.bankcards.service;

import com.example.bankcards.dto.card.CardMapper;
import com.example.bankcards.dto.card.CardRequest;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import com.example.bankcards.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CardService {

    private final CardRepository repository;

    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    public boolean createCard(CardRequest cardRequest) {
        Card card = CardMapper.toEntity(cardRequest);
        card.setStatus(Status.ACTIVE);
        card.setExpiryDate(LocalDate.now().plusYears(5));
        repository.save(card);
        return true;
    }

}
