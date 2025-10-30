package com.alterhub.alterhubbackend.controller;

import com.alterhub.alterhubbackend.constant.ApiRoutes;
import com.alterhub.alterhubbackend.constant.ReturnMessages;
import com.alterhub.alterhubbackend.dto.RarityDTO;
import com.alterhub.alterhubbackend.service.interfaces.RarityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.Rarities.ROOT)
public class RarityController {

    private final RarityService  rarityService;

    @GetMapping
    public ResponseEntity<List<RarityDTO>> getAllTypes() {
        return ResponseEntity.ok(rarityService.getAllRarities());
    }

    @GetMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<RarityDTO> getTypeById(@PathVariable UUID id) {
        return ResponseEntity.ok(rarityService.getRarityById(id));
    }

    @PostMapping
    public ResponseEntity<RarityDTO> createType(@RequestBody RarityDTO rarityDTO) {
        return ResponseEntity.ok(rarityService.createRarity(rarityDTO));
    }

    @PutMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<RarityDTO> updateType(@PathVariable UUID id, @RequestBody RarityDTO rarityDTO) {
        return ResponseEntity.ok(rarityService.updateRarityById(id, rarityDTO));
    }

    @DeleteMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<Map<String, String>> deleteType(@PathVariable UUID id) {
        rarityService.deleteRarityById(id);
        Map<String, String> response = Map.of("message", ReturnMessages.SUPPRESSION_SUCCESS);
        return ResponseEntity.ok(response);
    }

}
