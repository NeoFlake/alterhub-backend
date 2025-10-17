package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.DeckDTO;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.InvalidDeckSizeException;
import com.alterhub.alterhubbackend.repository.DeckRepository;
import com.alterhub.alterhubbackend.service.interfaces.CardService;
import com.alterhub.alterhubbackend.service.interfaces.DeckService;
import com.alterhub.alterhubbackend.service.interfaces.FactionService;
import com.alterhub.alterhubbackend.service.interfaces.HeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {

    private final DeckRepository deckRepository;

    private final FactionService factionService;
    private final HeroService heroService;
    private final CardService cardService;

    public void validateDeck(DeckDTO deckDTO) {

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

        if(deckDTO.getCards() == null || deckDTO.getCards().isEmpty()){
            throw new BadRequestException();
        } else if(deckDTO.getCards().size() < 39 || deckDTO.getCards().size() > 59){
            throw new InvalidDeckSizeException();
        } else {
           deckDTO.getCards().forEach(cardService::verifyCardIntegrity);
        }

    }

}
