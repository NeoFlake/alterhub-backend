package com.alterhub.alterhubbackend.controller;

import com.alterhub.alterhubbackend.constant.ApiRoutes;
import com.alterhub.alterhubbackend.constant.ReturnMessages;
import com.alterhub.alterhubbackend.dto.ParticipantDTO;
import com.alterhub.alterhubbackend.service.interfaces.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.Participants.ROOT)
public class ParticipantController {

    private final ParticipantService participantService;

    @GetMapping
    public ResponseEntity<Page<ParticipantDTO>> getAllParticipants(Pageable pageable) {
        return ResponseEntity.ok(participantService.getAllParticipants(pageable));
    }

    @GetMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<ParticipantDTO> getParticipantById(@PathVariable UUID id) {
        return ResponseEntity.ok(participantService.getParticipantById(id));
    }

    @GetMapping(ApiRoutes.Participants.BY_PLAYER_ID)
    public ResponseEntity<List<ParticipantDTO>> getParticipantsByPlayerId(@PathVariable UUID id) {
        return ResponseEntity.ok(participantService.getParticipantsByPlayerId(id));
    }

    @GetMapping(ApiRoutes.Participants.BY_TOURNAMENT_ID)
    public ResponseEntity<List<ParticipantDTO>> getParticipantsByTournamentId(@PathVariable UUID id) {
        return ResponseEntity.ok(participantService.getParticipantsByTournamentId(id));
    }

    @GetMapping(ApiRoutes.Participants.BY_CLASSEMENT)
    public ResponseEntity<List<ParticipantDTO>> getParticipantsByClassement(@PathVariable Short classement) {
        return ResponseEntity.ok(participantService.getParticipantsByClassement(classement));
    }

    @GetMapping(ApiRoutes.Participants.BY_DECK_FACTION_ID)
    public ResponseEntity<Page<ParticipantDTO>> getParticipantsByDeckFactionId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(participantService.getParticipantsByDeckFactionId(id, pageable));
    }

    @PostMapping(ApiRoutes.Participants.BY_DECK_FACTION_ID_IN)
    public ResponseEntity<Page<ParticipantDTO>> getParticipantsByDeckFactionIdIn(@RequestBody List<UUID> deckFactionIds, Pageable pageable) {
        return ResponseEntity.ok(participantService.getParticipantsByDeckFactionIdIn(deckFactionIds, pageable));
    }

    @GetMapping(ApiRoutes.Participants.BY_DECK_HERO_ID)
    public ResponseEntity<Page<ParticipantDTO>> getParticipantsByDeckHeroId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(participantService.getParticipantsByDeckHeroId(id, pageable));
    }

    @PostMapping(ApiRoutes.Participants.BY_DECK_HERO_ID_IN)
    public ResponseEntity<Page<ParticipantDTO>> getParticipantsByDeckHeroIdIn(@RequestBody List<UUID> deckHeroIds, Pageable pageable) {
        return ResponseEntity.ok(participantService.getParticipantsByDeckHeroIdIn(deckHeroIds, pageable));
    }

    @GetMapping(ApiRoutes.Participants.BY_DECK_TAG_ID)
    public ResponseEntity<Page<ParticipantDTO>> getParticipantsByDeckTagId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(participantService.getParticipantsByDeckTagId(id, pageable));
    }

    @PostMapping(ApiRoutes.Participants.BY_DECK_TAG_ID_IN)
    public ResponseEntity<Page<ParticipantDTO>> getParticipantsByDeckTagIdIn(@RequestBody List<UUID> deckTagIds, Pageable pageable) {
        return ResponseEntity.ok(participantService.getParticipantsByDeckTagIdIn(deckTagIds, pageable));
    }

    @PostMapping
    public ResponseEntity<ParticipantDTO> addParticipant(@RequestBody ParticipantDTO deckDTO) {
        return ResponseEntity.ok(participantService.addParticipant(deckDTO));
    }

    @PutMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<ParticipantDTO> updateParticipantById(@PathVariable UUID id, @RequestBody ParticipantDTO participantDTO) {
        return ResponseEntity.ok(participantService.updateParticipantById(id, participantDTO));
    }

    @DeleteMapping(ApiRoutes.Participants.BY_TOURNAMENT_ID)
    public ResponseEntity<Map<String, String>> deleteParticipantsByTournamentId(@PathVariable UUID id) {
        participantService.deleteParticipantsByTournamentId(id);
        Map<String, String> response = Map.of("message", ReturnMessages.SUPPRESSION_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<Map<String, String>> deleteDeckById(@PathVariable UUID id) {
        participantService.deleteParticipantById(id);
        Map<String, String> response = Map.of("message", ReturnMessages.SUPPRESSION_SUCCESS);
        return ResponseEntity.ok(response);
    }

}
