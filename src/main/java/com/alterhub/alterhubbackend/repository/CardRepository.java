package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {

    Optional<Card> findByAlteredId(String alteredId);

    Page<Card> findByTypeId(UUID typeId, Pageable pageable);

    Page<Card> findBySubtypes_Id(UUID subTypeId, Pageable pageable);

    Page<Card> findByFactionId(UUID factionId, Pageable pageable);

    Page<Card> findByRarityId(UUID rarityId, Pageable pageable);

    @Query("SELECT COUNT(d) FROM Deck d JOIN d.cards c WHERE c.id = :cardId")
    Integer countDecksContainingCard(@Param("cardId") UUID cardId);
}
