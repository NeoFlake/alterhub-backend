package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.CardDTO;
import com.alterhub.alterhubbackend.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CardService {

    Page<CardDTO> getAllCards(Pageable pageable);

    CardDTO getCardById(UUID id);

    CardDTO getCardByAlteredId(String alteredId);

    Page<CardDTO> getCardsByTypeId(UUID typeId, Pageable pageable);

    Page<CardDTO> getCardsByFactionId(UUID factionId, Pageable pageable);

    Page<CardDTO> getCardsBySubTypeId(UUID subTypeId, Pageable pageable);

    Page<CardDTO> getCardsByRarityId(UUID rarityId, Pageable pageable);

    CardDTO updateCardById(UUID id, CardDTO cardDTO);

    void deleteCardById(UUID id);

    CardDTO addCard(CardDTO cardDTO);

    Integer getDeckCount(UUID cardId);

    CardDTO mapWithDeckCount(Card card);

    List<CardDTO> mapCardsWithDeckCount(List<Card> cards);

    void verifyCardIntegrity(CardDTO cardDTO);

    void validateCard(CardDTO cardDTO);

}
