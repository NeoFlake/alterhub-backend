package com.alterhub.alterhubbackend.controller;

import com.alterhub.alterhubbackend.constant.ApiRoutes;
import com.alterhub.alterhubbackend.constant.ReturnMessages;
import com.alterhub.alterhubbackend.dto.FactionDTO;
import com.alterhub.alterhubbackend.service.interfaces.FactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.Factions.ROOT)
public class FactionController {

    private final FactionService factionService;

    @GetMapping
    public ResponseEntity<List<FactionDTO>> getAllFactions() {
        return ResponseEntity.ok(factionService.getAllFactions());
    }

    @GetMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<FactionDTO> getFactionById(@PathVariable UUID id) {
        return ResponseEntity.ok(factionService.getFactionById(id));
    }

    @PostMapping
    public ResponseEntity<FactionDTO> createFaction(@RequestBody FactionDTO factionDTO) {
        return ResponseEntity.ok(factionService.createFaction(factionDTO));
    }

    @PutMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<FactionDTO> updateFactionById(@PathVariable UUID id, @RequestBody FactionDTO factionDTO) {
        return ResponseEntity.ok(factionService.updateFactionById(id, factionDTO));
    }

    @DeleteMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<Map<String, String>> deleteFactionById(@PathVariable UUID id) {
        factionService.deleteFactionById(id);
        Map<String, String> response = Map.of("message", ReturnMessages.SUPPRESSION_SUCCESS);
        return ResponseEntity.ok(response);
    }

}
