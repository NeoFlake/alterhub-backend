package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeckRepository extends JpaRepository<Deck, UUID> {

    Optional<Deck> findByName(String name);

    List<Deck> findByNameContaining(String name);

    List<Deck> findByPlayer_Id(UUID playerId);

    List<Deck> findByFaction_Id(UUID factionId);

    List<Deck> findByHero_id(UUID heroId);

    Optional<Deck> findFirstByFaction_IdOrderByDateOfCreationDesc(UUID factionId);

    List<Deck> findTop5ByFaction_IdOrderByDateOfCreationDesc(UUID factionId);

    Optional<Deck> findFirstByHero_IdOrderByDateOfCreationDesc(UUID factionId);

    List<Deck> findTop5ByHero_IdOrderByDateOfCreationDesc(UUID factionId);

    Optional<Deck> findFirstByFaction_IdOrderByLastModificationDesc(UUID factionId);

    List<Deck> findTop5ByFaction_IdOrderByLastModificationDesc(UUID factionId);

    Optional<Deck> findFirstByHero_IdOrderByLastModificationDesc(UUID factionId);

    List<Deck> findTop5ByHero_IdOrderByLastModificationDesc(UUID factionId);

    List<Deck> findByDateOfCreationOrderByDateOfCreationDesc(LocalDate date);

    List<Deck> findByDateOfCreationBetweenOrderByDateOfCreationDesc(LocalDate start, LocalDate end);

    List<Deck> findByLastModificationBetweenOrderByLastModificationDesc(LocalDateTime start, LocalDateTime end);

}
