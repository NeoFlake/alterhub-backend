package com.alterhub.alterhubbackend.controller;

import com.alterhub.alterhubbackend.constant.ApiRoutes;
import com.alterhub.alterhubbackend.constant.ReturnMessages;
import com.alterhub.alterhubbackend.dto.SetDTO;
import com.alterhub.alterhubbackend.service.interfaces.SetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.Sets.ROOT)
public class SetController {

    private final SetService setService;

    @GetMapping
    public ResponseEntity<List<SetDTO>> getAllSets() {
        return ResponseEntity.ok(setService.getAllSets());
    }

    @GetMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<SetDTO> getSetById(@PathVariable UUID id) {
        return ResponseEntity.ok(setService.getSetById(id));
    }

    @PostMapping
    public ResponseEntity<SetDTO> createSet(@RequestBody SetDTO setDTO) {
        return ResponseEntity.ok(setService.createSet(setDTO));
    }

    @PutMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<SetDTO> updateSetById(@PathVariable UUID id, @RequestBody SetDTO setDTO) {
        return ResponseEntity.ok(setService.updateSetById(id, setDTO));
    }

    @DeleteMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<Map<String, String>> deleteSetById(@PathVariable UUID id) {
        setService.deleteSetById(id);
        Map<String, String> response = Map.of("message", ReturnMessages.SUPPRESSION_SUCCESS);
        return ResponseEntity.ok(response);
    }

}
