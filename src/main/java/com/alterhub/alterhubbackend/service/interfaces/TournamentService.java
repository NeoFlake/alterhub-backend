package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.TournamentDTO;
import com.alterhub.alterhubbackend.entity.Tournament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TournamentService {

    Page<TournamentDTO> getAllTournaments(Pageable pageable);

    TournamentDTO getTournamentById(UUID tournamentId);

    Page<TournamentDTO> getTournamentByLessOfNumberOfPlayer(Integer numberOfPlayers, Pageable pageable);

    Page<TournamentDTO> getTournamentByGreaterOfNumberOfPlayer(Integer numberOfPlayers, Pageable pageable);

    Page<TournamentDTO> getTournamentBetweenARangeOfNumberOfPlayers(Integer minimalNumberOfPlayers, Integer maximalNumberOfPlayers, Pageable pageable);

    Page<TournamentDTO> getTournamentByDate(LocalDate date, Pageable pageable);

    Page<TournamentDTO> getTournamentPlayedBeforeADate(LocalDate date, Pageable pageable);

    Page<TournamentDTO> getTournamentPlayedAfterADate(LocalDate date, Pageable pageable);

    Page<TournamentDTO> getTournamentPlayedBetweenARangeOfDate(LocalDate startingDate, LocalDate endingDate, Pageable pageable);

    Page<TournamentDTO> getTournamentByPlayerId(UUID playerId, Pageable pageable);

    Page<TournamentDTO> getTournamentsByFactionId(UUID factionId, Pageable pageable);

    Page<TournamentDTO> getTournamentsByHeroId(UUID heroId, Pageable pageable);

    Page<TournamentDTO> getTournamentsByFactionIdIn(List<UUID> factionIds, Pageable pageable);

    Page<TournamentDTO> getTournamentsByHeroIdIn(List<UUID> heroIds, Pageable pageable);

    Page<TournamentDTO> getTournamentPlayedThisWeek(Pageable pageable);

    Page<TournamentDTO> getTournamentPlayedThisMonth(Pageable pageable);

    Boolean existsById(UUID tournamentId);

    TournamentDTO getTournamentByName(String tournamentName);

    Page<TournamentDTO> getTournamentByLocation(String location, Pageable pageable);

    void verifyTournamentIntegrity(TournamentDTO tournamentDTO);

    TournamentDTO addTournament(TournamentDTO tournamentDTO);

    TournamentDTO updateTournamentById(UUID id, TournamentDTO tournamentDTO);

    void deleteTournamentById(UUID id);

    TournamentDTO mapTournamentDTOWithSubObject(Tournament tournament);

    List<TournamentDTO> mapTournamentsDTOWithSubObject(List<Tournament> tournaments);

    Tournament mapTournamentWithSubObject(TournamentDTO tournamentDTO);

    List<Tournament> mapTournamentsWithSubObject(List<TournamentDTO> tournamentsDTO);

}
