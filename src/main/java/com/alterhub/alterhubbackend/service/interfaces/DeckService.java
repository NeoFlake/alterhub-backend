package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.DeckDTO;

import java.util.List;
import java.util.UUID;

public interface DeckService {

    List<DeckDTO> getAllDecks();

    DeckDTO getDeckById(UUID deckId);

    List<DeckDTO> getDecksLikeByName(String name);

    void validateDeck(DeckDTO deckDTO);

    void verifyDeckIntegrity(DeckDTO deckDTO);

}
