package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {

    @Query("SELECT COUNT(d) FROM Deck d JOIN d.cards c WHERE c.id = :cardId")
    Integer countDecksContainingCard(@Param("cardId") UUID cardId);
}
