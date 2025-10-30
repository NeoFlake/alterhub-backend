package com.alterhub.alterhubbackend.controller;

import com.alterhub.alterhubbackend.constant.ApiRoutes;
import com.alterhub.alterhubbackend.constant.ReturnMessages;
import com.alterhub.alterhubbackend.dto.TournamentDTO;
import com.alterhub.alterhubbackend.service.interfaces.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.Tournaments.ROOT)
public class TournamentController {

    private final TournamentService tournamentService;

    @GetMapping
    public ResponseEntity<Page<TournamentDTO>> getAllTournaments(Pageable pageable) {
        return ResponseEntity.ok(tournamentService.getAllTournaments(pageable));
    }

    @GetMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<TournamentDTO> getParticipantById(@PathVariable UUID id) {
        return ResponseEntity.ok(tournamentService.getTournamentById(id));
    }

    @GetMapping(ApiRoutes.Tournaments.LESS_THAN_NUMBER_OF_PLAYER)
    public ResponseEntity<Page<TournamentDTO>> getTournamentByLessOfNumberOfPlayer(@PathVariable Integer numberofplayers, Pageable pageable) {
        return ResponseEntity.ok(tournamentService.getTournamentByLessOfNumberOfPlayer(numberofplayers, pageable));
    }

    @GetMapping(ApiRoutes.Tournaments.GREATER_THAN_NUMBER_OF_PLAYER)
    public ResponseEntity<Page<TournamentDTO>> getTournamentByGreaterOfNumberOfPlayer(@PathVariable Integer numberofplayers, Pageable pageable) {
        return ResponseEntity.ok(tournamentService.getTournamentByGreaterOfNumberOfPlayer(numberofplayers, pageable));
    }

    @GetMapping(ApiRoutes.Tournaments.BETWEEN_A_RANGE_OF_PLAYER)
    public ResponseEntity<Page<TournamentDTO>> getTournamentBetweenARangeOfNumberOfPlayers(
            @RequestParam Integer minimalNumberOfPlayers,
            @RequestParam Integer maximalNumberOfPlayers,
            Pageable pageable
    ) {
        return ResponseEntity.ok(tournamentService.getTournamentBetweenARangeOfNumberOfPlayers(minimalNumberOfPlayers, maximalNumberOfPlayers, pageable));
    }

    @GetMapping(ApiRoutes.Tournaments.BY_DATE)
    public ResponseEntity<Page<TournamentDTO>> getTournamentByDate(@PathVariable LocalDate date, Pageable pageable) {
        return ResponseEntity.ok(tournamentService.getTournamentByDate(date, pageable));
    }

    @GetMapping(ApiRoutes.Tournaments.BEFORE_A_DATE)
    public ResponseEntity<Page<TournamentDTO>> getTournamentPlayedBeforeADate(@PathVariable LocalDate date, Pageable pageable) {
        return ResponseEntity.ok(tournamentService.getTournamentPlayedBeforeADate(date, pageable));
    }

    @GetMapping(ApiRoutes.Tournaments.AFTER_A_DATE)
    public ResponseEntity<Page<TournamentDTO>> getTournamentPlayedAfterADate(@PathVariable LocalDate date, Pageable pageable) {
        return ResponseEntity.ok(tournamentService.getTournamentPlayedAfterADate(date, pageable));
    }

    @GetMapping(ApiRoutes.Tournaments.BETWEEN_A_RANGE_OF_DATE)
    public ResponseEntity<Page<TournamentDTO>> getTournamentPlayedBetweenARangeOfDate(
            @RequestParam LocalDate startingDate,
            @RequestParam LocalDate endingDate,
            Pageable pageable
    ) {
        return ResponseEntity.ok(tournamentService.getTournamentPlayedBetweenARangeOfDate(startingDate, endingDate, pageable));
    }

    @GetMapping(ApiRoutes.Tournaments.BY_PLAYER_ID)
    public ResponseEntity<Page<TournamentDTO>> getTournamentByPlayerId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(tournamentService.getTournamentByPlayerId(id, pageable));
    }

    @GetMapping(ApiRoutes.Tournaments.BY_FACTION_ID)
    public ResponseEntity<Page<TournamentDTO>> getTournamentsByFactionId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(tournamentService.getTournamentsByFactionId(id, pageable));
    }

    @PostMapping(ApiRoutes.Tournaments.BY_FACTION_ID_IN)
    public ResponseEntity<Page<TournamentDTO>> getTournamentsByFactionIdIn(@RequestBody List<UUID> factionIds, Pageable pageable) {
        return ResponseEntity.ok(tournamentService.getTournamentsByFactionIdIn(factionIds, pageable));
    }

    @GetMapping(ApiRoutes.Tournaments.BY_HERO_ID)
    public ResponseEntity<Page<TournamentDTO>> getTournamentsByHeroId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(tournamentService.getTournamentsByHeroId(id, pageable));
    }

    @PostMapping(ApiRoutes.Tournaments.BY_HERO_ID_IN)
    public ResponseEntity<Page<TournamentDTO>> getTournamentsByHeroIdIn(@RequestBody List<UUID> heroIds, Pageable pageable) {
        return ResponseEntity.ok(tournamentService.getTournamentsByHeroIdIn(heroIds, pageable));
    }

    @GetMapping(ApiRoutes.Tournaments.PLAYED_THIS_WEEK)
    public ResponseEntity<Page<TournamentDTO>> getTournamentPlayedThisWeek(Pageable pageable) {
        return ResponseEntity.ok(tournamentService.getTournamentPlayedThisWeek(pageable));
    }

    @GetMapping(ApiRoutes.Tournaments.PLAYED_THIS_MONTH)
    public ResponseEntity<Page<TournamentDTO>> getTournamentPlayedThisMonth(Pageable pageable) {
        return ResponseEntity.ok(tournamentService.getTournamentPlayedThisMonth(pageable));
    }

    @GetMapping(ApiRoutes.Tournaments.BY_NAME)
    public ResponseEntity<TournamentDTO> getTournamentByName(@PathVariable String name) {
        return ResponseEntity.ok(tournamentService.getTournamentByName(name));
    }

    @GetMapping(ApiRoutes.Tournaments.BY_LOCATION)
    public ResponseEntity<Page<TournamentDTO>> getTournamentByLocation(@PathVariable String location, Pageable pageable) {
        return ResponseEntity.ok(tournamentService.getTournamentByLocation(location, pageable));
    }

    @PostMapping
    public ResponseEntity<TournamentDTO> addTournament(@RequestBody TournamentDTO tournamentDTO) {
        return ResponseEntity.ok(tournamentService.addTournament(tournamentDTO));
    }

    @PutMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<TournamentDTO> updateTournamentById(@PathVariable UUID id, @RequestBody TournamentDTO tournamentDTO) {
        return ResponseEntity.ok(tournamentService.updateTournamentById(id, tournamentDTO));
    }

    @DeleteMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<Map<String, String>> deleteTournamentById(@PathVariable UUID id) {
        tournamentService.deleteTournamentById(id);
        Map<String, String> response = Map.of("message", ReturnMessages.SUPPRESSION_SUCCESS);
        return ResponseEntity.ok(response);
    }

}
