package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.CardDTO;
import com.alterhub.alterhubbackend.dto.DeckDTO;
import com.alterhub.alterhubbackend.entity.Deck;
import com.alterhub.alterhubbackend.entity.Player;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class DeckMapper {

    public DeckDTO toDTO(Deck deck, List<CardDTO> cards) {
        if (deck == null) return null;

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

    public Deck toEntity(DeckDTO deckDTO, Player player) {
        if (deckDTO == null) return null;

        return Deck.builder()
                .id(deckDTO.getId())
                .name(deckDTO.getName())
                .player(player)
                .description(deckDTO.getDescription())
                .faction(FactionMapper.toEntity(deckDTO.getFaction()))
                .hero(HeroMapper.toEntity(deckDTO.getHero()))
                .dateOfCreation(deckDTO.getDateOfCreation())
                .lastModification(deckDTO.getLastModification())
                .cards(deckDTO.getCards()
                        .stream()
                        .map(CardMapper::toEntity)
                        .toList())
                .build();

    }

}
