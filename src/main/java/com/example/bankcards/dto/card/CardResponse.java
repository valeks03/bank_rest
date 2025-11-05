package com.example.bankcards.dto.card;

import java.math.BigDecimal;

public record CardResponse(
    String cardMask,
    BigDecimal balance
) {
}
