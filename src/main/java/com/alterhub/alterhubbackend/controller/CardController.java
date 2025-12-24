package com.alterhub.alterhubbackend.controller;

import com.alterhub.alterhubbackend.constant.ApiRoutes;
import com.alterhub.alterhubbackend.constant.ReturnMessages;
import com.alterhub.alterhubbackend.dto.CardDTO;
import com.alterhub.alterhubbackend.service.interfaces.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.Cards.ROOT)
public class CardController {

    private final CardService cardService;

    @GetMapping
    public ResponseEntity<Page<CardDTO>> getAllCards(Pageable pageable) {
        System.out.println(cardService.getAllCards(pageable));
        return ResponseEntity.ok(cardService.getAllCards(pageable));
    }

    @GetMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<CardDTO> getTagById(@PathVariable UUID id) {
        return ResponseEntity.ok(cardService.getCardById(id));
    }

    @GetMapping(ApiRoutes.Cards.BY_ALTERED_ID)
    public ResponseEntity<CardDTO> getCardByAlteredId(@PathVariable String id) {
        return ResponseEntity.ok(cardService.getCardByAlteredId(id));
    }

    @GetMapping(ApiRoutes.Cards.BY_TYPE_ID)
    public ResponseEntity<Page<CardDTO>> getCardsByTypeId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(cardService.getCardsByTypeId(id, pageable));
    }

    @GetMapping(ApiRoutes.Cards.BY_SUBTYPE_ID)
    public ResponseEntity<Page<CardDTO>> getCardsBySubTypeId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(cardService.getCardsBySubTypeId(id, pageable));
    }

    @GetMapping(ApiRoutes.Cards.BY_FACTION_ID)
    public ResponseEntity<Page<CardDTO>> getCardsByFactionId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(cardService.getCardsByFactionId(id, pageable));
    }

    @GetMapping(ApiRoutes.Cards.BY_RARITY_ID)
    public ResponseEntity<Page<CardDTO>> getCardsByRarityId(@PathVariable UUID id, Pageable pageable) {
        return ResponseEntity.ok(cardService.getCardsByRarityId(id, pageable));
    }

    @PostMapping
    public ResponseEntity<CardDTO> addCard(@RequestBody CardDTO cardDTO) {
        return ResponseEntity.ok(cardService.addCard(cardDTO));
    }

    @PutMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<CardDTO> updateCardById(@PathVariable UUID id, @RequestBody CardDTO cardDTO) {
        return ResponseEntity.ok(cardService.updateCardById(id, cardDTO));
    }

    @DeleteMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<Map<String, String>> deleteCardById(@PathVariable UUID id) {
        cardService.deleteCardById(id);
        Map<String, String> response = Map.of("message", ReturnMessages.SUPPRESSION_SUCCESS);
        return ResponseEntity.ok(response);
    }

}
