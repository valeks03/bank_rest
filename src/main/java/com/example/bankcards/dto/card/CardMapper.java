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

    public static CardResponse toResponse(Card card) {

        String mask = "*** **** **** " + card.getNumber().substring(card.getNumber().length() - 4);

        return new CardResponse(
                card.getId(),           //id
                mask,                   //cardMask
                card.getOwner(),        //owner
                card.getBalance(),      //balance
                card.getExpiryDate(),   //expiryDate
                card.getStatus()        //status
        );
    }


}
