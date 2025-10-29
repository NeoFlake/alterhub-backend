package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TournamentRepository extends JpaRepository<Tournament, UUID> {

    Tournament findByName(String name);

    Page<Tournament> findByLocation(String location, Pageable pageable);

    Page<Tournament> findByNumberOfPlayersIsLessThanEqual(Integer numberOfPlayersIsLessThan, Pageable pageable);

    Page<Tournament> findByNumberOfPlayersIsGreaterThanEqual(Integer numberOfPlayersGreaterThan, Pageable pageable);

    Page<Tournament> findByNumberOfPlayersIsBetween(Integer numberOfPlayersAfter, Integer numberOfPlayersBefore, Pageable pageable);

    Page<Tournament> findByDate(LocalDate date, Pageable pageable);

    Page<Tournament> findByDateIsBefore(LocalDate dateBefore, Pageable pageable);

    Page<Tournament> findByDateIsAfter(LocalDate dateAfter, Pageable pageable);

    Page<Tournament> findByDateIsBetween(LocalDate dateAfter, LocalDate dateBefore, Pageable pageable);

    Page<Tournament> findByParticipantsPlayer_Id(UUID playerId, Pageable pageable);

    Page<Tournament> findByParticipantsDeckFaction_Id(UUID factionId, Pageable pageable);

    Page<Tournament> findByParticipantsDeckFaction_IdIn(List<UUID> factionIds, Pageable pageable);

    Page<Tournament> findByParticipantsDeckHero_Id(UUID heroId, Pageable pageable);

    Page<Tournament> findByParticipantsDeckHero_IdIn(List<UUID> heroIds, Pageable pageable);
}
