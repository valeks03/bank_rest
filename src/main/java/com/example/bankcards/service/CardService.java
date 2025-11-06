package com.example.bankcards.service;

import com.example.bankcards.dto.card.CardMapper;
import com.example.bankcards.dto.card.CardRequest;
import com.example.bankcards.dto.card.CardResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import com.example.bankcards.repository.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CardService {

    private static final Logger log = LoggerFactory.getLogger(CardService.class);
    private final CardRepository repository;
    private final CardRepository cardRepository;

    public CardService(CardRepository repository, CardRepository cardRepository) {
        this.repository = repository;
        this.cardRepository = cardRepository;
    }

    public CardResponse createCard(CardRequest cardRequest) {
        Card card = CardMapper.toEntity(cardRequest);
        card.setStatus(Status.ACTIVE);
        card.setExpiryDate(LocalDate.now().plusYears(5));
        log.info("Create new card");
        repository.save(card);
        log.info("Saved new card in database");

        return CardMapper.toResponse(card);
    }


    public CardResponse getCard(Long id) {
        //TODO
        //убрать null и добавить проверку для есть ли доступ у user (возможно в Controller придется делать)
        Card card = null;
        try {
            card = repository.getReferenceById(id);
        } catch (Exception e) {
            throw new NoSuchElementException(e);
        }
        return CardMapper.toResponse(card);
    }

    public List<CardResponse> getAllCards() {
        return cardRepository.findAll().stream()
                .map(CardMapper::toResponse)
                .toList();
    }

    public List<CardResponse> getCardsByOwner(String owner) {
        return cardRepository.findAllByOwner(owner)
                .stream()
                .map(CardMapper::toResponse)
                .toList();
    }


    public CardResponse updateCard(Long id, CardRequest request) {
        Card existing = cardRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Card not found"));

        existing.setNumber(request.number());
        if (request.number() != null && !request.number().equals(existing.getNumber())) {

            if (cardRepository.existsByNumber(request.number())) {
                throw new IllegalStateException("Card number already exists");
            }
            existing.setNumber(request.number());
        }

        existing.setBalance(request.balance());
        existing.setOwner(request.ownerName());
        cardRepository.save(existing);
        return CardMapper.toResponse(existing);
    }

    public CardResponse deleteCard(Long id) {
        Card existing = cardRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Card not found"));

        existing.setStatus(Status.BLOCKED);
        cardRepository.save(existing);
        return CardMapper.toResponse(existing);
    }
}
