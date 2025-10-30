package com.alterhub.alterhubbackend.controller;

import com.alterhub.alterhubbackend.constant.ApiRoutes;
import com.alterhub.alterhubbackend.dto.PlayerDTO;
import com.alterhub.alterhubbackend.service.interfaces.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.Players.ROOT)
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<Page<PlayerDTO>> getAllTournaments(Pageable pageable) {
        return ResponseEntity.ok(playerService.getAllPlayers(pageable));
    }

    @GetMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<PlayerDTO> getParticipantById(@PathVariable UUID id) {
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

    @GetMapping(ApiRoutes.Players.BY_USER_ID)
    public ResponseEntity<PlayerDTO> getPlayerByUserId(@PathVariable UUID id) {
        return ResponseEntity.ok(playerService.getPlayerByUserId(id));
    }

    @GetMapping(ApiRoutes.Players.BY_NAME)
    public ResponseEntity<PlayerDTO> getPlayerByName(@PathVariable String name) {
        return ResponseEntity.ok(playerService.getPlayerByName(name));
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> addPlayer(@RequestBody PlayerDTO playerDTO) {
        return ResponseEntity.ok(playerService.addPlayer(playerDTO));
    }

}
