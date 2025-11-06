package com.example.bankcards.dto.card;

import com.example.bankcards.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CardResponse(
    Long id,
    String cardMask,
    String owner,
    BigDecimal balance,
    LocalDate expiryDate,
    Status status
) {
}
