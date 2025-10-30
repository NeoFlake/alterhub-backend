package com.alterhub.alterhubbackend.controller;

import com.alterhub.alterhubbackend.constant.ApiRoutes;
import com.alterhub.alterhubbackend.constant.ReturnMessages;
import com.alterhub.alterhubbackend.dto.SubTypeDTO;
import com.alterhub.alterhubbackend.service.interfaces.SubTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.SubTypes.ROOT)
public class SubTypeController {

    private final SubTypeService subTypeService;

    @GetMapping
    public ResponseEntity<List<SubTypeDTO>> getAllTypes() {
        return ResponseEntity.ok(subTypeService.getAllSubTypes());
    }

    @GetMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<SubTypeDTO> getTypeById(@PathVariable UUID id) {
        return ResponseEntity.ok(subTypeService.getSubTypeById(id));
    }

    @PostMapping
    public ResponseEntity<SubTypeDTO> createType(@RequestBody SubTypeDTO subtypeDTO) {
        return ResponseEntity.ok(subTypeService.createSubType(subtypeDTO));
    }

    @PutMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<SubTypeDTO> updateType(@PathVariable UUID id, @RequestBody SubTypeDTO subtypeDTO) {
        return ResponseEntity.ok(subTypeService.updateSubTypeById(id, subtypeDTO));
    }

    @DeleteMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<Map<String, String>> deleteType(@PathVariable UUID id) {
        subTypeService.deleteSubTypeById(id);
        Map<String, String> response = Map.of("message", ReturnMessages.SUPPRESSION_SUCCESS);
        return ResponseEntity.ok(response);
    }

}
