package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.CardDTO;
import com.alterhub.alterhubbackend.entity.Card;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.CardMapper;
import com.alterhub.alterhubbackend.repository.CardRepository;
import com.alterhub.alterhubbackend.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final FactionService factionService;
    private final TypeService typeService;
    private final SubTypeService subTypeService;
    private final RarityService  rarityService;
    private final ElementService elementService;

    public List<CardDTO> getAllCards() {
        return cardRepository.findAll()
                .stream()
                .map(card -> {
                    Integer deckCount = getDeckCount(card.getId());
                    return CardMapper.toDTO(card, deckCount);
                })
                .toList();
    }

    public CardDTO getCardById(UUID id) {
        Card card = cardRepository.findById(id).orElseThrow(NoResultByIdException::new);
        return mapWithDeckCount(card);
    }

    public CardDTO getCardsByAlteredId(String alteredId) {
        if(alteredId != null && !alteredId.isEmpty()){
            Card card = cardRepository.findByAlteredId(alteredId).orElseThrow(NoResultByIdException::new);
            return mapWithDeckCount(card);
        } else {
            throw new BadRequestException();
        }
    }

    public List<CardDTO> getCardsByTypeId(UUID typeId) {
        if (typeId == null) {
            throw new BadRequestException();
        }
        return cardRepository.findByTypeId(typeId)
                .stream()
                .map(this::mapWithDeckCount)
                .toList();
    }

    public List<CardDTO> getCardsBySubTypeId(UUID subTypeId) {
        if (subTypeId == null) {
            throw new BadRequestException();
        }
        return cardRepository.findBySubtypes_Id(subTypeId)
                .stream()
                .map(this::mapWithDeckCount)
                .toList();
    }

    public List<CardDTO> getCardsByRarityId(UUID rarityId) {
        if (rarityId == null) {
            throw new BadRequestException();
        }
        return cardRepository.findByRarityId(rarityId)
                .stream()
                .map(this::mapWithDeckCount)
                .toList();
    }

    public List<CardDTO> getCardsByFactionId(UUID factionId) {
        if (factionId == null) {
            throw new BadRequestException();
        }
        return cardRepository.findByFactionId(factionId)
                .stream()
                .map(this::mapWithDeckCount)
                .toList();
    }

    public CardDTO addCard(CardDTO cardDTO) {
        verifyCardIntegrity(cardDTO);
        Card card = cardRepository.save(CardMapper.toEntity(cardDTO));
        return mapWithDeckCount(card);
    }

    public CardDTO updateCardById(UUID id, CardDTO cardDTO) {
        if (cardDTO.getId().equals(id)) {
            verifyCardIntegrity(cardDTO);
            factionService.validateFaction(cardDTO.getFaction());
            typeService.validateType(cardDTO.getType());
            if (cardDTO.getSubTypes() != null && !cardDTO.getSubTypes().isEmpty()) {
                cardDTO.getSubTypes().forEach(subTypeService::validateSubType);
            }
            rarityService.validateRarity(cardDTO.getRarity());
            elementService.validateElement(cardDTO.getElement());

            Card cardToUpdate = cardRepository.findById(id).orElseThrow(NoResultByIdException::new);
            Card cardUpdated = CardMapper.toEntity(cardDTO);

            cardToUpdate.setId(cardUpdated.getId());
            cardToUpdate.setAlteredId(cardUpdated.getAlteredId());
            cardToUpdate.setName(cardUpdated.getName());
            cardToUpdate.setImage(cardUpdated.getImage());
            cardToUpdate.setFaction(cardUpdated.getFaction());
            cardToUpdate.setType(cardUpdated.getType());
            cardToUpdate.setSubtypes(cardUpdated.getSubtypes());
            cardToUpdate.setRarity(cardUpdated.getRarity());
            cardToUpdate.setElement(cardUpdated.getElement());
            cardToUpdate.setIsSuspended(cardUpdated.getIsSuspended());
            cardToUpdate.setIsBanned(cardUpdated.getIsBanned());
            cardToUpdate.setIsErrated(cardUpdated.getIsErrated());

            Card card = cardRepository.save(cardToUpdate);

            return mapWithDeckCount(card);
        } else {
            throw new IdNotMatchException();
        }
    }

    public void deleteCardById(UUID id) {
        if(!cardRepository.existsById(id)){
            throw new NoResultByIdException();
        }
        cardRepository.deleteById(id);
    }

    public Integer getDeckCount(UUID cardId) {
        return cardRepository.countDecksContainingCard(cardId);
    }

    public void verifyCardIntegrity(CardDTO cardDTO) {
        if (cardDTO.getAlteredId() == null || cardDTO.getAlteredId().isEmpty()
                || cardDTO.getReference() == null || cardDTO.getReference().isEmpty()
                || cardDTO.getName() == null || cardDTO.getName().isEmpty()
                || cardDTO.getImage() == null || cardDTO.getImage().isEmpty()
                || cardDTO.getIsSuspended() == null
                || cardDTO.getIsBanned() == null
                || cardDTO.getIsErrated() == null
        ) {
            throw new BadRequestException();
        }
        factionService.verifyFactionIntegrity(cardDTO.getFaction());
        typeService.verifyTypeIntegrity(cardDTO.getType());
        if(cardDTO.getSubTypes() != null && !cardDTO.getSubTypes().isEmpty()) {
            cardDTO.getSubTypes().forEach(subTypeService::verifySubTypeIntegrity);
        }
        rarityService.verifyRarityIntegrity(cardDTO.getRarity());
        elementService.verifyElementIntegrity(cardDTO.getElement());
    }

    private CardDTO mapWithDeckCount(Card card){
        Integer deckCount = getDeckCount(card.getId());
        return CardMapper.toDTO(card, deckCount);
    }

}
