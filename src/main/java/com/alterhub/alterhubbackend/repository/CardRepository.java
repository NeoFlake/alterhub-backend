package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {

    Optional<Card> findByAlteredId(String alteredId);

    List<Card> findByTypeId(UUID typeId);

    List<Card> findBySubtypes_Id(UUID subTypeId);

    List<Card> findByFactionId(UUID factionId);

    List<Card> findByRarityId(UUID rarityId);

    @Query("SELECT COUNT(d) FROM Deck d JOIN d.cards c WHERE c.id = :cardId")
    Integer countDecksContainingCard(@Param("cardId") UUID cardId);
}
