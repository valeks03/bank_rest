package com.example.bankcards.dto;

import java.math.BigDecimal;

public record Transfer(
        Long fromCardId,
        Long toCardId,
        BigDecimal amount
) {
}
