package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    List<Participant> findByTournament_Id(UUID tournamentId);

    List<Participant> findByPlayer_Id(UUID id);

    List<Participant> findByClassement(Short classement);

    List<Participant> findByDeckFaction_Id(UUID deckFactionId);

    List<Participant> findByDeckFaction_IdIn(List<UUID> deckFactionIds);

    List<Participant> findByDeckHero_Id(UUID deckHeroId);

    List<Participant> findByDeckHero_IdIn(List<UUID> deckHeroId);

    List<Participant> findByDeckTags_Id(UUID deckTagId);

    List<Participant> findByDeckTags_IdIn(List<UUID> deckTagIds);

    void deleteByPlayer_Id(UUID id);

}
