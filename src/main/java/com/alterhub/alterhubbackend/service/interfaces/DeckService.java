package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.DeckDTO;
import com.alterhub.alterhubbackend.entity.Deck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface DeckService {

    Page<DeckDTO> getAllDecks(Pageable pageable);

    DeckDTO getDeckById(UUID deckId);

    DeckDTO getDeckByName(String name);

    Page<DeckDTO> getDecksLikeByName(String name, Pageable pageable);

    Page<DeckDTO> getDecksByPlayerId(UUID playerId, Pageable pageable);

    Page<DeckDTO> getDecksByFactionId(UUID factionId, Pageable pageable);

    Page<DeckDTO> getDecksByHeroId(UUID heroId, Pageable pageable);

    DeckDTO getLastDeckCreatedByFactionId(UUID factionId);

    Page<DeckDTO> getLast5DecksCreatedByFactionId(UUID factionId, Pageable pageable);

    DeckDTO getLastDeckCreatedByHeroId(UUID heroId);

    Page<DeckDTO> getLast5DecksCreatedByHeroId(UUID heroId, Pageable pageable);

    Page<DeckDTO> getDecksCreatedThisDay(Pageable pageable);

    Page<DeckDTO> getDecksCreatedOThisWeek(Pageable pageable);

    Page<DeckDTO> getDecksCreatedThisMonth(Pageable pageable);

    DeckDTO getLastDeckModifiedByFactionId(UUID factionId);

    Page<DeckDTO> getLast5DecksModifiedByFactionId(UUID factionId, Pageable pageable);

    DeckDTO getLastDeckModifiedByHeroId(UUID heroId, Pageable pageable);

    Page<DeckDTO> getLast5DecksModifiedByHeroId(UUID heroId, Pageable pageable);

    Page<DeckDTO> getDecksModifiedThisDay(Pageable pageable);

    Page<DeckDTO> getDecksModifiedOThisWeek(Pageable pageable);

    Page<DeckDTO> getDecksModifiedThisMonth(Pageable pageable);

    Page<DeckDTO> getDecksByTagId(UUID tagId, Pageable pageable);

    Page<DeckDTO> getDecksByTagIdIn(List<UUID> tagIds, Pageable pageable);

    DeckDTO addDeck(DeckDTO deckDTO);

    DeckDTO updateDeckById(UUID id, DeckDTO deckDTO);

    void patchIsParticipantByDeckId(UUID id, Boolean isParticipant);

    void patchIsParticipantByDeckIdIn(List<UUID> deckIds, Boolean isParticipant);

    void deleteDeckById(UUID id);

    void deleteDeckNonParticipantByPlayerId(UUID playerId);

    void validateDeck(DeckDTO deckDTO);

    void verifyDeckIntegrity(DeckDTO deckDTO);

    DeckDTO mapDeckDTOWithSubObjects(Deck deck);

    List<DeckDTO> mapDecksDTOWithSubObjects(List<Deck> decks);

    Deck mapDeckWithSubObjects(DeckDTO deckDTO);

    List<Deck> mapDecksWithSubObjects(List<DeckDTO> deckDTO);
}
