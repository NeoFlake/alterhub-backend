package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Card;
import com.alterhub.alterhubbackend.repository.projection.CardDeckCountProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {

    @Query(value = """
            SELECT DISTINCT card FROM Card card
            LEFT JOIN FETCH card.faction fac
            LEFT JOIN FETCH card.sets cSet
            LEFT JOIN FETCH card.element elem
            LEFT JOIN FETCH card.rarity rare
            LEFT JOIN FETCH card.type type
            WHERE type.reference NOT IN ('HERO', 'TOKEN', 'TOKEN_LANDMARK_PERMANENT', 'FOILER', 'TOKEN_MANA')
            ORDER BY
            fac.reference ASC,
            CASE cSet.reference
                WHEN 'COREKS' THEN 1
                WHEN 'CORE' THEN 2
                WHEN 'ALIZE' THEN 3
                WHEN 'BISE' THEN 4
                WHEN 'CYCLONE' THEN 5
                WHEN 'DUSTER' THEN 6
                ELSE 7
               END,
            cSet.reference ASC,
            type.reference ASC,
            CASE elem.mainCost
                WHEN '#1#' THEN 1
                WHEN '1' THEN 1
                WHEN '#2#' THEN 2
                WHEN '2' THEN 2
                WHEN '#3#' THEN 3
                WHEN '3' THEN 3
                WHEN '#4#' THEN 4
                WHEN '4' THEN 4
                WHEN '#5#' THEN 5
                WHEN '5' THEN 5
                WHEN '#6#' THEN 6
                WHEN '6' THEN 6
                WHEN '#7#' THEN 7
                WHEN '7' THEN 7
                WHEN '#8#' THEN 8
                WHEN '8' THEN 8
                WHEN '#9#' THEN 9
                WHEN '9' THEN 9
                END,
            elem.mainCost ASC,
            card.name ASC,
            rare.reference ASC,
            card.reference
            """)
    Page<Card> findAll(Pageable pageable);

    Optional<Card> findByAlteredId(String alteredId);

    Page<Card> findByTypeId(UUID typeId, Pageable pageable);

    Page<Card> findBySubtypes_Id(UUID subTypeId, Pageable pageable);

    @Query(value = """
            SELECT DISTINCT card FROM Card card
            LEFT JOIN FETCH card.faction fac
            LEFT JOIN FETCH card.sets cSet
            LEFT JOIN FETCH card.element elem
            LEFT JOIN FETCH card.rarity rare
            LEFT JOIN FETCH card.type type
            WHERE type.reference NOT IN ('HERO', 'TOKEN', 'TOKEN_LANDMARK_PERMANENT', 'FOILER', 'TOKEN_MANA')
            AND fac.id = :factionId
            ORDER BY
            CASE cSet.reference
                WHEN 'COREKS' THEN 1
                WHEN 'CORE' THEN 2
                WHEN 'ALIZE' THEN 3
                WHEN 'BISE' THEN 4
                WHEN 'CYCLONE' THEN 5
                WHEN 'DUSTER' THEN 6
                ELSE 7
               END,
            cSet.reference ASC,
            type.reference ASC,
            CASE elem.mainCost
            WHEN '#1#' THEN 1
            WHEN '1' THEN 1
            WHEN '#2#' THEN 2
            WHEN '2' THEN 2
            WHEN '#3#' THEN 3
            WHEN '3' THEN 3
            WHEN '#4#' THEN 4
            WHEN '4' THEN 4
            WHEN '#5#' THEN 5
            WHEN '5' THEN 5
            WHEN '#6#' THEN 6
            WHEN '6' THEN 6
            WHEN '#7#' THEN 7
            WHEN '7' THEN 7
            WHEN '#8#' THEN 8
            WHEN '8' THEN 8
            WHEN '#9#' THEN 9
            WHEN '9' THEN 9
            ELSE 10
            END,
            elem.mainCost ASC,
            card.name ASC,
            rare.reference ASC
            """)
    Page<Card> findByFactionId(UUID factionId, Pageable pageable);

//
//
//    card.reference

    Page<Card> findByRarityId(UUID rarityId, Pageable pageable);

    @Query("SELECT COUNT(d) FROM Deck d JOIN d.cards c WHERE c.id = :cardId")
    Integer countDecksContainingCard(@Param("cardId") UUID cardId);

    @Query("SELECT c.id AS cardId, COUNT(d) AS deckCount " + "FROM Deck d JOIN d.cards c " + "WHERE c.id IN :cardIds " + "GROUP BY c.id")
    List<CardDeckCountProjection> countDecksForCards(@Param("cardIds") List<UUID> cardIds);
}
