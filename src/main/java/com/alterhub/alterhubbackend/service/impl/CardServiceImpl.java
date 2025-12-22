package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.CardDTO;
import com.alterhub.alterhubbackend.entity.Card;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.CardMapper;
import com.alterhub.alterhubbackend.mapping.MappingService;
import com.alterhub.alterhubbackend.repository.CardRepository;
import com.alterhub.alterhubbackend.service.DeckCountService;
import com.alterhub.alterhubbackend.service.interfaces.*;
import com.alterhub.alterhubbackend.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final ValidationService validationService;
    private final MappingService mappingService;
    private final DeckCountService deckCountService;

    public Page<CardDTO> getAllCards(Pageable pageable) {
        Page<Card> cardPage = cardRepository.findAll(pageable);
        return new PageImpl<>(
                mappingService.mapCardsWithDeckCount(cardPage.getContent(), deckCountService.mapCardsWithDeckCount(cardPage.getContent())),
                pageable,
                cardPage.getTotalElements()
        );
    }

    public CardDTO getCardById(UUID id) {
        Card card = cardRepository.findById(id).orElseThrow(NoResultByIdException::new);
        Integer deckCount = cardRepository.countDecksContainingCard(card.getId());
        return mappingService.mapWithDeckCount(card, deckCount);
    }

    public CardDTO getCardByAlteredId(String alteredId) {
        if (alteredId != null && !alteredId.isEmpty()) {
            Card card = cardRepository.findByAlteredId(alteredId).orElseThrow(NoResultByIdException::new);
            Integer deckCount = cardRepository.countDecksContainingCard(card.getId());
            return mappingService.mapWithDeckCount(card, deckCount);
        } else {
            throw new BadRequestException();
        }
    }

    public Page<CardDTO> getCardsByTypeId(UUID typeId, Pageable pageable) {
        if (typeId == null) {
            throw new BadRequestException();
        }
        Page<Card> cardPage = cardRepository.findByTypeId(typeId, pageable);
        return new PageImpl<>(
                mappingService.mapCardsWithDeckCount(cardPage.getContent(), deckCountService.mapCardsWithDeckCount(cardPage.getContent())),
                pageable,
                cardPage.getTotalElements()
        );
    }

    public Page<CardDTO> getCardsBySubTypeId(UUID subTypeId, Pageable pageable) {
        if (subTypeId == null) {
            throw new BadRequestException();
        }
        Page<Card> cardPage = cardRepository.findBySubtypes_Id(subTypeId, pageable);
        return new PageImpl<>(mappingService.mapCardsWithDeckCount(
                cardPage.getContent(), deckCountService.mapCardsWithDeckCount(cardPage.getContent())),
                pageable,
                cardPage.getTotalElements()
        );
    }

    public Page<CardDTO> getCardsByRarityId(UUID rarityId, Pageable pageable) {
        if (rarityId == null) {
            throw new BadRequestException();
        }
        Page<Card> cardPage = cardRepository.findByRarityId(rarityId, pageable);
        return new PageImpl<>(mappingService.mapCardsWithDeckCount(
                cardPage.getContent(), deckCountService.mapCardsWithDeckCount(cardPage.getContent())),
                pageable,
                cardPage.getTotalElements()
        );
    }

    public Page<CardDTO> getCardsByFactionId(UUID factionId, Pageable pageable) {
        if (factionId == null) {
            throw new BadRequestException();
        }
        Page<Card> cardPage = cardRepository.findByFactionId(factionId, pageable);
        return new PageImpl<>(mappingService.mapCardsWithDeckCount(
                cardPage.getContent(), deckCountService.mapCardsWithDeckCount(cardPage.getContent())),
                pageable,
                cardPage.getTotalElements()
        );
    }

    public CardDTO addCard(CardDTO cardDTO) {
        validationService.verifyCardIntegrity(cardDTO);
        Integer deckCount = cardRepository.countDecksContainingCard(cardDTO.getId());
        return mappingService.mapWithDeckCount(cardRepository.save(CardMapper.toEntity(cardDTO)), deckCount);
    }

    public CardDTO updateCardById(UUID id, CardDTO cardDTO) {
        if (cardDTO.getId().equals(id)) {
            validationService.verifyCardIntegrity(cardDTO);
            validationService.validateCardSubObject(cardDTO);

            Card cardToUpdate = cardRepository.findById(id).orElseThrow(NoResultByIdException::new);
            Card cardUpdated = CardMapper.toEntity(cardDTO);

            cardToUpdate.setId(cardUpdated.getId());
            cardToUpdate.setAlteredId(cardUpdated.getAlteredId());
            cardToUpdate.setName(cardUpdated.getName());
            cardToUpdate.setImage(cardUpdated.getImage());
            cardToUpdate.setFaction(cardUpdated.getFaction());
            cardToUpdate.setType(cardUpdated.getType());
            cardToUpdate.setSubtypes(cardUpdated.getSubtypes());
            cardToUpdate.setSets(cardUpdated.getSets());
            cardToUpdate.setRarity(cardUpdated.getRarity());
            cardToUpdate.setElement(cardUpdated.getElement());
            cardToUpdate.setIsSuspended(cardUpdated.getIsSuspended());
            cardToUpdate.setIsBanned(cardUpdated.getIsBanned());
            cardToUpdate.setIsErrated(cardUpdated.getIsErrated());

            Card card = cardRepository.save(cardToUpdate);
            Integer deckCount = cardRepository.countDecksContainingCard(card.getId());
            return mappingService.mapWithDeckCount(card, deckCount);
        } else {
            throw new IdNotMatchException();
        }
    }

    public void deleteCardById(UUID id) {
        if (!cardRepository.existsById(id)) {
            throw new NoResultByIdException();
        }
        cardRepository.deleteById(id);
    }

}
