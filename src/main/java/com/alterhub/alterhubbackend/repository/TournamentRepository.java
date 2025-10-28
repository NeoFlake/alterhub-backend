package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TournamentRepository extends JpaRepository<Tournament, UUID> {

    Tournament findByName(String name);

    List<Tournament> findByLocation(String location);

    List<Tournament> findByNumberOfPlayersIsLessThanEqual(Integer numberOfPlayersIsLessThan);

    List<Tournament> findByNumberOfPlayersIsGreaterThanEqual(Integer numberOfPlayersGreaterThan);

    List<Tournament> findByNumberOfPlayersIsBetween(Integer numberOfPlayersAfter, Integer numberOfPlayersBefore);

    List<Tournament> findByDate(LocalDate date);

    List<Tournament> findByDateIsBefore(LocalDate dateBefore);

    List<Tournament> findByDateIsAfter(LocalDate dateAfter);

    List<Tournament> findByDateIsBetween(LocalDate dateAfter, LocalDate dateBefore);

    List<Tournament> findByParticipantsPlayer_Id(UUID playerId);

    List<Tournament> findByParticipantsDeckFaction_Id(UUID factionId);

    List<Tournament> findByParticipantsDeckFaction_IdIn(List<UUID> factionIds);

    List<Tournament> findByParticipantsDeckHero_Id(UUID heroId);

    List<Tournament> findByParticipantsDeckHero_IdIn(List<UUID> heroIds);
}
