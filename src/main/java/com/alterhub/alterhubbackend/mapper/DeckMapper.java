package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.CardDTO;
import com.alterhub.alterhubbackend.dto.DeckDTO;
import com.alterhub.alterhubbackend.entity.Deck;
import com.alterhub.alterhubbackend.service.CardService;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Service;

import java.util.List;

@UtilityClass
public class DeckMapper {

    public DeckDTO toDTO(Deck deck, CardService cardService) {
        if (deck == null) return null;

        List<CardDTO> cards = deck.getCards()
                .stream()
                .map(card -> {
                    int deckCount = cardService.getDeckCount(card.getId());
                    return CardMapper.toDTO(card, deckCount);
                })
                .toList();

        return DeckDTO.builder()
                .id(deck.getId())
                .name(deck.getName())
                .playerId(deck.getPlayer().getId())
                .description(deck.getDescription())
                .faction(FactionMapper.toDTO(deck.getFaction()))
                .hero(HeroMapper.toDTO(deck.getHero()))
                .dateOfCreation(deck.getDateOfCreation())
                .lastModification(deck.getLastModification())
                .cards(cards)
                .build();

    }

    public Deck toEntity(DeckDTO deckDTO) {
        if (deckDTO == null) return null;

        return Deck.builder()
                .id(deckDTO.getId())
                .name(deckDTO.getName())
                .player(deckDTO.getPlayer().getId())
                .description(deckDTO.getDescription())
                .faction(FactionMapper.toDTO(deckDTO.getFaction()))
                .hero(HeroMapper.toDTO(deckDTO.getHero()))
                .dateOfCreation(deckDTO.getDateOfCreation())
                .lastModification(deckDTO.getLastModification())
                .cards(cards)
                .build();

    }

}
