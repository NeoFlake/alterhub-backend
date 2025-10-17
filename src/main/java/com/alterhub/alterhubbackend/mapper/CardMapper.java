package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.CardDTO;
import com.alterhub.alterhubbackend.entity.Card;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@UtilityClass
public class CardMapper {

    public CardDTO toDTO(Card card, Integer deckCount) {
        if (card == null) return null;

        return CardDTO.builder()
                .id(card.getId())
                .alteredId(card.getAlteredId())
                .reference(card.getReference())
                .name(card.getName())
                .image(card.getImage())
                .faction(FactionMapper.toDTO(card.getFaction()))
                .type(TypeMapper.toDTO(card.getType()))
                .subTypes(card.getSubtypes()
                        .stream()
                        .map(SubTypeMapper::toDTO)
                        .collect(Collectors.toList()))
                .rarity(RarityMapper.toDTO(card.getRarity()))
                .element(ElementMapper.toDTO(card.getElement()))
                .isSuspended(card.getIsSuspended())
                .isBanned(card.getIsBanned())
                .isErrated(card.getIsErrated())
                .deckCount(deckCount)
                .build();
    }

    public Card toEntity(CardDTO cardDTO) {
        if (cardDTO == null) return null;

        return Card.builder()
                .id(cardDTO.getId())
                .alteredId(cardDTO.getAlteredId())
                .reference(cardDTO.getReference())
                .name(cardDTO.getName())
                .image(cardDTO.getImage())
                .faction(FactionMapper.toEntity(cardDTO.getFaction()))
                .type(TypeMapper.toEntity(cardDTO.getType()))
                .subtypes(
                        cardDTO.getSubTypes() == null
                                ? new ArrayList<>()
                                : cardDTO.getSubTypes().stream()
                                .map(SubTypeMapper::toEntity)
                                .collect(Collectors.toList()))
                .rarity(RarityMapper.toEntity(cardDTO.getRarity()))
                .element(ElementMapper.toEntity(cardDTO.getElement()))
                .isSuspended(cardDTO.getIsSuspended())
                .isBanned(cardDTO.getIsBanned())
                .isErrated(cardDTO.getIsErrated())
                .build();
    }

}
