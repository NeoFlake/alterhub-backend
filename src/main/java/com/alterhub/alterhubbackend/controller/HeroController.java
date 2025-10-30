package com.alterhub.alterhubbackend.controller;

import com.alterhub.alterhubbackend.constant.ApiRoutes;
import com.alterhub.alterhubbackend.constant.ReturnMessages;
import com.alterhub.alterhubbackend.dto.HeroDTO;
import com.alterhub.alterhubbackend.service.interfaces.HeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.Heros.ROOT)
public class HeroController {

    private final HeroService heroService;

    @GetMapping
    public ResponseEntity<List<HeroDTO>> getAllHeroes() {
        return ResponseEntity.ok(heroService.getAllHeroes());
    }

    @GetMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<HeroDTO> getHeroById(@PathVariable UUID id) {
        return ResponseEntity.ok(heroService.getHeroById(id));
    }

    @GetMapping(ApiRoutes.Heros.BY_NAME)
    public ResponseEntity<HeroDTO> getHeroByName(@PathVariable String name) {
        return ResponseEntity.ok(heroService.getHeroByName(name));
    }

    @GetMapping(ApiRoutes.Heros.BY_FACTION_ID)
    public ResponseEntity<List<HeroDTO>> getAllHeroesByFaction(@PathVariable UUID id) {
        return ResponseEntity.ok(heroService.getAllHeroesByFaction(id));
    }

    @PostMapping
    public ResponseEntity<HeroDTO> createHero(@RequestBody HeroDTO heroDTO) {
        return ResponseEntity.ok(heroService.createHero(heroDTO));
    }

    @PutMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<HeroDTO> updateHeroById(@PathVariable UUID id, @RequestBody HeroDTO heroDTO) {
        return ResponseEntity.ok(heroService.updateHeroById(id, heroDTO));
    }

    @DeleteMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<Map<String, String>> deleteHeroById(@PathVariable UUID id) {
        heroService.deleteHeroById(id);
        Map<String, String> response = Map.of("message", ReturnMessages.SUPPRESSION_SUCCESS);
        return ResponseEntity.ok(response);
    }

}
