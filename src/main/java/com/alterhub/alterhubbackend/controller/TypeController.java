package com.alterhub.alterhubbackend.controller;

import com.alterhub.alterhubbackend.constant.ApiRoutes;
import com.alterhub.alterhubbackend.dto.TypeDTO;
import com.alterhub.alterhubbackend.service.interfaces.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.Types.ROOT)
public class TypeController {

    private final TypeService typeService;

}

//@RestController
//@RequestMapping("/api/decks")
//public class DeckController {
//
//    private final DeckService deckService;
//
//    public DeckController(DeckService deckService) {
//        this.deckService = deckService;
//    }
//
//    // 🔹 GET paginé : /api/decks?page=0&size=10&sort=name,asc
//    @GetMapping
//    public ResponseEntity<Page<DeckDTO>> getAllDecks(Pageable pageable) {
//        Page<DeckDTO> decks = deckService.findAll(pageable);
//        return ResponseEntity.ok(decks);
//    }
//
//    // 🔹 GET by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<DeckDTO> getDeckById(@PathVariable Long id) {
//        return ResponseEntity.of(deckService.findById(id));
//    }
//
//    // 🔹 POST (création)
//    @PostMapping
//    public ResponseEntity<DeckDTO> createDeck(@RequestBody DeckDTO deckDTO) {
//        DeckDTO created = deckService.save(deckDTO);
//        return ResponseEntity.ok(created);
//    }
//
//    // 🔹 PUT (mise à jour)
//    @PutMapping("/{id}")
//    public ResponseEntity<DeckDTO> updateDeck(@PathVariable Long id, @RequestBody DeckDTO deckDTO) {
//        DeckDTO updated = deckService.update(id, deckDTO);
//        return ResponseEntity.ok(updated);
//    }
//
//    // 🔹 DELETE
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteDeck(@PathVariable Long id) {
//        deckService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//}
