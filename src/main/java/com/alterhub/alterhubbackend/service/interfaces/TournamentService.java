package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.TournamentDTO;
import com.alterhub.alterhubbackend.entity.Tournament;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TournamentService {

    List<TournamentDTO> getAllTournaments();

    TournamentDTO getTournamentById(UUID tournamentId);

    List<TournamentDTO> getTournamentByLessOfNumberOfPlayer(Integer numberOfPlayers);

    List<TournamentDTO> getTournamentByGreaterOfNumberOfPlayer(Integer numberOfPlayers);

    List<TournamentDTO> getTournamentBetweenARangeOfNumberOfPlayers(Integer minimalNumberOfPlayers, Integer maximalNumberOfPlayers);

    List<TournamentDTO> getTournamentByDate(LocalDate date);

    List<TournamentDTO> getTournamentPlayedBeforeADate(LocalDate date);

    List<TournamentDTO> getTournamentPlayedAfterADate(LocalDate date);

    List<TournamentDTO> getTournamentPlayedBetweenARangeOfDate(LocalDate startingDate, LocalDate endingDate);

    List<TournamentDTO> getTournamentByPlayerId(UUID playerId);

    List<TournamentDTO> getTournamentsByFactionId(UUID factionId);

    List<TournamentDTO> getTournamentsByHeroId(UUID heroId);

    List<TournamentDTO> getTournamentsByFactionIdIn(List<UUID> factionIds);

    List<TournamentDTO> getTournamentsByHeroIdIn(List<UUID> heroIds);

    List<TournamentDTO> getTournamentPlayedThisWeek();

    List<TournamentDTO> getTournamentPlayedThisMonth();

    Boolean existsById(UUID tournamentId);

    TournamentDTO getTournamentByName(String tournamentName);

    List<TournamentDTO> getTournamentByLocation(String location);

    void verifyTournamentIntegrity(TournamentDTO tournamentDTO);

    TournamentDTO addTournament(TournamentDTO tournamentDTO);

    TournamentDTO updateTournamentById(UUID id, TournamentDTO tournamentDTO);

    void deleteTournamentById(UUID id);

    TournamentDTO mapTournamentDTOWithSubObject(Tournament tournament);

    List<TournamentDTO> mapTournamentsDTOWithSubObject(List<Tournament> tournaments);

    Tournament mapTournamentWithSubObject(TournamentDTO tournamentDTO);

    List<Tournament> mapTournamentsWithSubObject(List<TournamentDTO> tournamentsDTO);

}
