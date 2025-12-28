package com.alterhub.alterhubbackend.controller;

import com.alterhub.alterhubbackend.constant.ApiRoutes;
import com.alterhub.alterhubbackend.constant.ReturnMessages;
import com.alterhub.alterhubbackend.dto.DeckDTO;
import com.alterhub.alterhubbackend.service.interfaces.DeckService;
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
@RequestMapping(ApiRoutes.Decks.ROOT)
public class DeckController {

    private final DeckService deckService;

    @GetMapping
    public ResponseEntity<Page<DeckDTO>> getAllDecks(Pageable pageable) {
        return ResponseEntity.ok(deckService.getAllDecks(pageable));
    }

    @GetMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<DeckDTO> getDeckById(@PathVariable UUID id) {
        return ResponseEntity.ok(deckService.getDeckById(id));
    }

    @GetMapping(ApiRoutes.Decks.BY_NAME)
    public ResponseEntity<DeckDTO> getDeckByName(@PathVariable String name) {
        return ResponseEntity.ok(deckService.getDeckByName(name));
    }

    @GetMapping(ApiRoutes.Decks.BY_LIKE_BY_NAME)
    public ResponseEntity<Page<DeckDTO>> getDeckByName(@PathVariable String name, Pageable pageable) {
        return ResponseEntity.ok(deckService.getDecksLikeByName(name, pageable));
    }

    @GetMapping(ApiRoutes.Decks.EXIST_BY_NAME)
    public ResponseEntity<Boolean> existDeckByName(@PathVariable String name) {
        return ResponseEntity.ok(deckService.existDeckByName(name));
    }

    @GetMapping(ApiRoutes.Decks.BY_PLAYER_ID)
    public ResponseEntity<Page<DeckDTO>> getDecksByPlayerId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(deckService.getDecksByPlayerId(id, pageable));
    }

    @GetMapping(ApiRoutes.Decks.BY_FACTION_ID)
    public ResponseEntity<Page<DeckDTO>> getDecksByFactionId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(deckService.getDecksByFactionId(id, pageable));
    }

    @GetMapping(ApiRoutes.Decks.BY_HERO_ID)
    public ResponseEntity<Page<DeckDTO>> getDecksByHeroId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(deckService.getDecksByHeroId(id, pageable));
    }

    @GetMapping(ApiRoutes.Decks.LATEST_CREATED_BY_FACTION_ID)
    public ResponseEntity<DeckDTO> getLastDeckCreatedByFactionId(@PathVariable UUID id) {
        return ResponseEntity.ok(deckService.getLastDeckCreatedByFactionId(id));
    }

    @GetMapping(ApiRoutes.Decks.FIVE_LATEST_CREATED_BY_FACTION_ID)
    public ResponseEntity<Page<DeckDTO>> getLast5DecksCreatedByFactionId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(deckService.getLast5DecksCreatedByFactionId(id, pageable));
    }

    @GetMapping(ApiRoutes.Decks.LATEST_CREATED_BY_HERO_ID)
    public ResponseEntity<DeckDTO> getLastDeckCreatedByHeroId(@PathVariable UUID id) {
        return ResponseEntity.ok(deckService.getLastDeckCreatedByHeroId(id));
    }

    @GetMapping(ApiRoutes.Decks.FIVE_LATEST_CREATED_BY_HERO_ID)
    public ResponseEntity<Page<DeckDTO>> getLast5DecksCreatedByHeroId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(deckService.getLast5DecksCreatedByHeroId(id, pageable));
    }

    @GetMapping(ApiRoutes.Decks.CREATED_TODAY)
    public ResponseEntity<Page<DeckDTO>> getDecksCreatedThisDay(Pageable pageable) {
        return ResponseEntity.ok(deckService.getDecksCreatedThisDay(pageable));
    }

    @GetMapping(ApiRoutes.Decks.CREATED_THIS_WEEK)
    public ResponseEntity<Page<DeckDTO>> getDecksCreatedOThisWeek(Pageable pageable) {
        return ResponseEntity.ok(deckService.getDecksCreatedThisWeek(pageable));
    }

    @GetMapping(ApiRoutes.Decks.CREATED_THIS_MONTH)
    public ResponseEntity<Page<DeckDTO>> getDecksCreatedOThisMonth(Pageable pageable) {
        return ResponseEntity.ok(deckService.getDecksCreatedThisMonth(pageable));
    }

    @GetMapping(ApiRoutes.Decks.LATEST_MODIFIED_BY_FACTION_ID)
    public ResponseEntity<DeckDTO> getLastDeckModifiedByFactionId(@PathVariable UUID id) {
        return ResponseEntity.ok(deckService.getLastDeckModifiedByFactionId(id));
    }

    @GetMapping(ApiRoutes.Decks.FIVE_LATEST_MODIFIED_BY_FACTION_ID)
    public ResponseEntity<Page<DeckDTO>> getLast5DecksModifiedByFactionId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(deckService.getLast5DecksModifiedByFactionId(id, pageable));
    }

    @GetMapping(ApiRoutes.Decks.LATEST_MODIFIED_BY_HERO_ID)
    public ResponseEntity<DeckDTO> getLastDeckModifiedByHeroId(@PathVariable UUID id) {
        return ResponseEntity.ok(deckService.getLastDeckModifiedByHeroId(id));
    }

    @GetMapping(ApiRoutes.Decks.FIVE_LATEST_MODIFIED_BY_HERO_ID)
    public ResponseEntity<Page<DeckDTO>> getLast5DecksModifiedByHeroId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(deckService.getLast5DecksModifiedByHeroId(id, pageable));
    }

    @GetMapping(ApiRoutes.Decks.MODIFIED_TODAY)
    public ResponseEntity<Page<DeckDTO>> getDecksModifiedThisDay(Pageable pageable) {
        return ResponseEntity.ok(deckService.getDecksModifiedThisDay(pageable));
    }

    @GetMapping(ApiRoutes.Decks.MODIFIED_THIS_WEEK)
    public ResponseEntity<Page<DeckDTO>> getDecksModifiedThisWeek(Pageable pageable) {
        return ResponseEntity.ok(deckService.getDecksModifiedThisWeek(pageable));
    }

    @GetMapping(ApiRoutes.Decks.MODIFIED_THIS_MONTH)
    public ResponseEntity<Page<DeckDTO>> getDecksModifiedOThisMonth(Pageable pageable) {
        return ResponseEntity.ok(deckService.getDecksModifiedThisMonth(pageable));
    }

    @GetMapping(ApiRoutes.Decks.BY_TAG_ID)
    public ResponseEntity<Page<DeckDTO>> getDecksByTagId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(deckService.getDecksByTagId(id, pageable));
    }

    @PostMapping(ApiRoutes.Decks.BY_TAG_ID_IN)
    public ResponseEntity<Page<DeckDTO>> getDecksByTagIdIn(@RequestBody List<UUID> tagIds, Pageable pageable) {
        return ResponseEntity.ok(deckService.getDecksByTagIdIn(tagIds, pageable));
    }

    @PostMapping
    public ResponseEntity<DeckDTO> addDeck(@RequestBody DeckDTO deckDTO) {
        return ResponseEntity.ok(deckService.addDeck(deckDTO));
    }

    @PutMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<DeckDTO> updateDeckById(@PathVariable UUID id, @RequestBody DeckDTO deckDTO) {
        return ResponseEntity.ok(deckService.updateDeckById(id, deckDTO));
    }

    @DeleteMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<Map<String, String>> deleteDeckById(@PathVariable UUID id) {
        deckService.deleteDeckById(id);
        Map<String, String> response = Map.of("message", ReturnMessages.SUPPRESSION_SUCCESS);
        return ResponseEntity.ok(response);
    }

}
