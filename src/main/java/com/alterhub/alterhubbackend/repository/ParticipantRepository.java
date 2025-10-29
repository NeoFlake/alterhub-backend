package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    List<Participant> findByTournament_Id(UUID tournamentId);

    List<Participant> findByPlayer_Id(UUID id);

    List<Participant> findByClassement(Short classement);

    Page<Participant> findByDeckFaction_Id(UUID deckFactionId, Pageable pageable);

    Page<Participant> findByDeckFaction_IdIn(List<UUID> deckFactionIds, Pageable pageable);

    Page<Participant> findByDeckHero_Id(UUID deckHeroId, Pageable pageable);

    Page<Participant> findByDeckHero_IdIn(List<UUID> deckHeroId, Pageable pageable);

    Page<Participant> findByDeckTags_Id(UUID deckTagId, Pageable pageable);

    Page<Participant> findByDeckTags_IdIn(List<UUID> deckTagIds, Pageable pageable);

    void deleteByPlayer_Id(UUID id);

    void deleteByTournamentId(UUID tournamentId);

    Boolean existsByTournamentId(UUID tournamentId);

    Boolean existsByPlayer_Id(UUID id);

    // Permet de savoir quels decks passé en paramètre d'entrée existe encore dans la table Participation
    @Query("""
                SELECT p.deck.id
                FROM Participant p
                WHERE p.deck.id IN :deckIds
            """)
    List<UUID> findExistingDeckIds(@Param("deckIds") List<UUID> deckIds);

}
