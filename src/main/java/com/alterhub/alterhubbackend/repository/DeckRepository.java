package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Deck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeckRepository extends JpaRepository<Deck, UUID> {

    Optional<Deck> findByName(String name);

    Page<Deck> findByNameContaining(String name, Pageable pageable);

    Boolean existsByName(String name);

    Page<Deck> findByPlayer_Id(UUID playerId, Pageable pageable);

    Page<Deck> findByFaction_Id(UUID factionId, Pageable pageable);

    Page<Deck> findByHero_id(UUID heroId, Pageable pageable);

    Page<Deck> findTop5ByOrderByDateOfCreationDesc(Pageable pageable);

    Optional<Deck> findFirstByFaction_IdOrderByDateOfCreationDesc(UUID factionId);

    Page<Deck> findTop5ByFaction_IdOrderByDateOfCreationDesc(UUID factionId, Pageable pageable);

    Optional<Deck> findFirstByHero_IdOrderByDateOfCreationDesc(UUID factionId);

    Page<Deck> findTop5ByHero_IdOrderByDateOfCreationDesc(UUID factionId, Pageable pageable);

    Page<Deck> findTop5ByOrderByLastModificationDesc(Pageable pageable);

    Optional<Deck> findFirstByFaction_IdOrderByLastModificationDesc(UUID factionId);

    Page<Deck> findTop5ByFaction_IdOrderByLastModificationDesc(UUID factionId, Pageable pageable);

    Optional<Deck> findFirstByHero_IdOrderByLastModificationDesc(UUID factionId);

    Page<Deck> findTop5ByHero_IdOrderByLastModificationDesc(UUID factionId, Pageable pageable);

    Page<Deck> findByDateOfCreationOrderByDateOfCreationDesc(LocalDate date, Pageable pageable);

    Page<Deck> findByDateOfCreationBetweenOrderByDateOfCreationDesc(LocalDate start, LocalDate end, Pageable pageable);

    Page<Deck> findByLastModificationBetweenOrderByLastModificationDesc(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<Deck> findByTags_Id(UUID tagId, Pageable pageable);

    Page<Deck> findByTags_IdIn(List<UUID> tagIds, Pageable pageable);

    List<Deck> findByIdIn(List<UUID> deckIds);

    void deleteByPlayer_IdAndIsParticipantFalse(UUID playerId);

    Boolean existsByPlayer_Id(UUID playerId);
}
