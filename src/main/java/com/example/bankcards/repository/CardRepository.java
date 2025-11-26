package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByOwner(String owner);
    boolean existsByNumber(String number);

    Optional<Card> findByIdAndOwner(Long id, String owner);

    Card findCardById(Long id);
}
