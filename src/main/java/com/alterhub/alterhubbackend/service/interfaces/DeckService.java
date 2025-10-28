package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.DeckDTO;
import com.alterhub.alterhubbackend.entity.Deck;

import java.util.List;
import java.util.UUID;

public interface DeckService {

    List<DeckDTO> getAllDecks();

    DeckDTO getDeckById(UUID deckId);

    DeckDTO getDeckByName(String name);

    List<DeckDTO> getDecksLikeByName(String name);

    List<DeckDTO> getDecksByPlayerId(UUID playerId);

    List<DeckDTO> getDecksByFactionId(UUID factionId);

    List<DeckDTO> getDecksByHeroId(UUID heroId);

    DeckDTO getLastDeckCreatedByFactionId(UUID factionId);

    List<DeckDTO> getLast5DecksCreatedByFactionId(UUID factionId);

    DeckDTO getLastDeckCreatedByHeroId(UUID heroId);

    List<DeckDTO> getLast5DecksCreatedByHeroId(UUID heroId);

    List<DeckDTO> getDecksCreatedThisDay();

    List<DeckDTO> getDecksCreatedOThisWeek();

    List<DeckDTO> getDecksCreatedThisMonth();

    DeckDTO getLastDeckModifiedByFactionId(UUID factionId);

    List<DeckDTO> getLast5DecksModifiedByFactionId(UUID factionId);

    DeckDTO getLastDeckModifiedByHeroId(UUID heroId);

    List<DeckDTO> getLast5DecksModifiedByHeroId(UUID heroId);

    List<DeckDTO> getDecksModifiedThisDay();

    List<DeckDTO> getDecksModifiedOThisWeek();

    List<DeckDTO> getDecksModifiedThisMonth();

    List<DeckDTO> getDecksByTagId(UUID tagId);

    List<DeckDTO> getDecksByTagIdIn(List<UUID> tagIds);

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
