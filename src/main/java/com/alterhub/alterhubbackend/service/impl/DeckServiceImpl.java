package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.CardDTO;
import com.alterhub.alterhubbackend.dto.DeckDTO;
import com.alterhub.alterhubbackend.entity.Card;
import com.alterhub.alterhubbackend.entity.Deck;
import com.alterhub.alterhubbackend.entity.Player;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.InvalidDeckSizeException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.exception.NotFindByArgumentException;
import com.alterhub.alterhubbackend.mapper.CardMapper;
import com.alterhub.alterhubbackend.mapper.DeckMapper;
import com.alterhub.alterhubbackend.repository.DeckRepository;
import com.alterhub.alterhubbackend.repository.PlayerRepository;
import com.alterhub.alterhubbackend.service.interfaces.CardService;
import com.alterhub.alterhubbackend.service.interfaces.DeckService;
import com.alterhub.alterhubbackend.service.interfaces.FactionService;
import com.alterhub.alterhubbackend.service.interfaces.HeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {

    private final DeckRepository deckRepository;
    private final PlayerRepository playerRepository;

    private final FactionService factionService;
    private final HeroService heroService;
    private final CardService cardService;

    public List<DeckDTO> getAllDecks() {
        return deckRepository.findAll()
                .stream()
                .map(deck -> DeckMapper.toDTO(deck, deck.getCards().stream().map(cardService::mapWithDeckCount).toList()))
                .toList();
    }

    public DeckDTO getDeckById(UUID id) {
        Deck deck =  deckRepository.findById(id).orElseThrow(NoResultByIdException::new);
        return DeckMapper.toDTO(deck, deck.getCards().stream().map(cardService::mapWithDeckCount).toList());
    }

    public DeckDTO getDeckByName(String name) {
        if(name == null || name.isEmpty()){
          throw new BadRequestException();
        }
        Deck deck = deckRepository.findByName(name).orElseThrow(NotFindByArgumentException::new);
        return DeckMapper.toDTO(deck, deck.getCards().stream().map(cardService::mapWithDeckCount).toList());
    }

    public List<DeckDTO> getDecksLikeByName(String name) {
        if(name == null || name.isEmpty()){
          throw new BadRequestException();
        }
        return deckRepository.findByNameContaining(name)
                .stream()
                .map(deck -> DeckMapper.toDTO(deck, deck.getCards().stream().map(cardService::mapWithDeckCount).toList()))
                .toList();
    }

    public void validateDeck(DeckDTO deckDTO) {

        Player playerOnBase = playerRepository.findById(deckDTO.getPlayerId()).orElseThrow(NoResultByIdException::new);

        Deck deckReceived = DeckMapper.toEntity(deckDTO, playerOnBase);
        Deck deckOnBase = deckRepository.findById(deckDTO.getId()).orElseThrow(NoResultByIdException::new);

        if (!deckOnBase.getName().equals(deckReceived.getName())
            ||!deckOnBase.getDescription().equals(deckReceived.getDescription())
                    || !deckOnBase.getDateOfCreation().equals(deckReceived.getDateOfCreation())
                    || ! deckOnBase.getLastModification().equals(deckReceived.getLastModification())){
            throw new BadRequestException();
        }

        factionService.validateFaction(deckDTO.getFaction());
        heroService.validateHero(deckDTO.getHero());

        // TODO: mettre en place la validation de l'égalité du joueur

        if(deckOnBase.getCards().size() == deckReceived.getCards().size()){
            deckDTO.getCards().forEach(cardService::validateCard);
        }

    }

    public void verifyDeckIntegrity(DeckDTO deckDTO) {
        if (deckDTO.getName() == null || deckDTO.getName().isEmpty()
                || deckDTO.getDescription() == null || deckDTO.getDescription().isEmpty()
                || deckDTO.getPlayerId() == null
                || deckDTO.getDateOfCreation() == null
                || deckDTO.getLastModification() == null) {
            throw new BadRequestException();
        }

        factionService.verifyFactionIntegrity(deckDTO.getFaction());
        heroService.verifyHeroIntegrity(deckDTO.getHero());

        if (deckDTO.getCards() == null || deckDTO.getCards().isEmpty()) {
            throw new BadRequestException();
        } else if (deckDTO.getCards().size() < 39 || deckDTO.getCards().size() > 59) {
            throw new InvalidDeckSizeException();
        } else {
            deckDTO.getCards().forEach(cardService::verifyCardIntegrity);
        }

    }

}
