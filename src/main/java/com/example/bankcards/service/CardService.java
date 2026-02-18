package com.example.bankcards.service;

import com.example.bankcards.dto.Transfer;
import com.example.bankcards.dto.card.CardMapper;
import com.example.bankcards.dto.card.CardRequest;
import com.example.bankcards.dto.card.CardResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import com.example.bankcards.repository.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CardService {

    private static final Logger log = LoggerFactory.getLogger(CardService.class);
    private final CardRepository cardRepository;

    public CardService(CardRepository repository, CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public CardResponse createCard(CardRequest cardRequest) {
        Card card = CardMapper.toEntity(cardRequest);
        card.setStatus(Status.ACTIVE);
        card.setExpiryDate(LocalDate.now().plusYears(5));
        log.info("Create new card");
        cardRepository.save(card);
        log.info("Saved new card in database");

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

    public CardResponse getCardById(Long id) {
        Card card  = cardRepository.findCardById(id);
        return CardMapper.toResponse(card);
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

    @Transactional
    public void transferBetweenOwnCards(Transfer transfer, String username) {
        if (transfer.fromCardId().equals(transfer.toCardId())) {
            throw new IllegalArgumentException("It is forbidden to transfer betwwen one card");
        }

        Card from = cardRepository.findByIdAndOwner(transfer.fromCardId(), username)
                .orElseThrow( () -> new NoSuchElementException("Source card not found or not yours"));

        Card to = cardRepository.findByIdAndOwner(transfer.toCardId(), username)
                .orElseThrow( () -> new NoSuchElementException("Target card not found or not yours"));
        System.out.println("from before: " + from.getBalance());
        System.out.println("to before: " + to.getBalance());

        if (from.getBalance().compareTo(transfer.amount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        from.setBalance(from.getBalance().subtract(transfer.amount()));
        to.setBalance(to.getBalance().add(transfer.amount()));
    }



    public CardResponse getCardByOwnerAndId(Long cardId, String username) {
        Card card = cardRepository.findByIdAndOwner(cardId, username)
                .orElseThrow(() -> new RuntimeException("Card not found or not yours"));
        return CardMapper.toResponse(card);
    }
}
