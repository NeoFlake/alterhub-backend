package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.CardDTO;

import java.util.List;
import java.util.UUID;

public interface CardService {

    List<CardDTO> getAllCards();

    CardDTO getCardById(UUID id);

    CardDTO getCardsByAlteredId(String alteredId);

    List<CardDTO> getCardsByTypeId(UUID typeId);

    List<CardDTO> getCardsByFactionId(UUID factionId);

    List<CardDTO> getCardsBySubTypeId(UUID subTypeId);

    List<CardDTO> getCardsByRarityId(UUID rarityId);

    CardDTO updateCardById(UUID id, CardDTO cardDTO);

    void deleteCardById(UUID id);

    CardDTO addCard(CardDTO cardDTO);

    Integer getDeckCount(UUID cardId);

    void verifyCardIntegrity(CardDTO cardDTO);

    void validateCard(CardDTO cardDTO);

}
