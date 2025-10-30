package com.alterhub.alterhubbackend.controller;

import com.alterhub.alterhubbackend.constant.ApiRoutes;
import com.alterhub.alterhubbackend.dto.TypeDTO;
import com.alterhub.alterhubbackend.service.interfaces.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.Types.ROOT)
public class TypeController {

    private final TypeService typeService;

    @GetMapping
    public ResponseEntity<List<TypeDTO>> getAllTypes() {
        return ResponseEntity.ok(typeService.getAllTypes());
    }

    @GetMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<TypeDTO> getTypeById(@PathVariable UUID id) {
        return ResponseEntity.ok(typeService.getTypeById(id));
    }

    @PostMapping
    public ResponseEntity<TypeDTO> createType(@RequestBody TypeDTO typeDTO) {
        return ResponseEntity.ok(typeService.createType(typeDTO));
    }

    @PutMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<TypeDTO> updateType(@PathVariable UUID id,@RequestBody TypeDTO typeDTO) {
        return ResponseEntity.ok(typeService.updateTypeById(id, typeDTO));
    }

    @DeleteMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<TypeDTO> deleteType(@PathVariable UUID id) {
        typeService.deleteTypeById(id);
        return ResponseEntity.noContent().build();
    }

}
