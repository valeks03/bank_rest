package com.example.bankcards.dto.card;

import com.example.bankcards.entity.Card;

import java.math.BigDecimal;

public class CardMapper {

    public static Card toEntity(CardRequest request) {
        Card card = new Card();
        card.setBalance(request.balance());
        card.setNumber(request.number());
        card.setOwner(request.ownerName());
        return card;
    }

}
