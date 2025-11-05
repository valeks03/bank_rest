package com.example.bankcards.dto.card;

import java.math.BigDecimal;

public record CardRequest(
    BigDecimal balance,
    String ownerName,
    String number
) {
}
