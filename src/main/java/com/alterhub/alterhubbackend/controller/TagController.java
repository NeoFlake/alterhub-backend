package com.alterhub.alterhubbackend.controller;

import com.alterhub.alterhubbackend.constant.ApiRoutes;
import com.alterhub.alterhubbackend.constant.ReturnMessages;
import com.alterhub.alterhubbackend.dto.TagDTO;
import com.alterhub.alterhubbackend.service.interfaces.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.Tags.ROOT)
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDTO>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<TagDTO> getTagById(@PathVariable UUID id) {
        return ResponseEntity.ok(tagService.getTagById(id));
    }

    @GetMapping(ApiRoutes.Tags.BY_NAME)
    public ResponseEntity<TagDTO> getTagByName(@PathVariable String name) {
        return ResponseEntity.ok(tagService.getTagByName(name));
    }

    @PostMapping
    public ResponseEntity<TagDTO> createTag(@RequestBody TagDTO tagDTO) {
        return ResponseEntity.ok(tagService.createTag(tagDTO));
    }

    @PutMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<TagDTO> updateTagById(@PathVariable UUID id, @RequestBody TagDTO tagDTO) {
        return ResponseEntity.ok(tagService.updateTagById(id, tagDTO));
    }

    @DeleteMapping(ApiRoutes.SEARCH_BY_ID)
    public ResponseEntity<Map<String, String>> deleteTagById(@PathVariable UUID id) {
        tagService.deleteTagById(id);
        Map<String, String> response = Map.of("message", ReturnMessages.SUPPRESSION_SUCCESS);
        return ResponseEntity.ok(response);
    }

}
