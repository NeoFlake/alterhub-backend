package com.alterhub.alterhubbackend.validation;

import com.alterhub.alterhubbackend.dto.*;
import com.alterhub.alterhubbackend.entity.*;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.InvalidDeckSizeException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.CardMapper;
import com.alterhub.alterhubbackend.mapper.DeckMapper;
import com.alterhub.alterhubbackend.mapper.FactionMapper;
import com.alterhub.alterhubbackend.mapper.HeroMapper;
import com.alterhub.alterhubbackend.repository.*;
import com.alterhub.alterhubbackend.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ValidationService {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;
    private final PlayerRepository playerRepository;
    private final FactionRepository factionRepository;
    private final HeroRepository heroRepository;
    private final ParticipantRepository participantRepository;

    private final TypeService typeService;
    private final SubTypeService subTypeService;
    private final SetService setService;
    private final RarityService rarityService;
    private final ValidationService  validationService;
    private final TournamentRepository tournamentRepository;


    public void validateCard(CardDTO cardDTO) {
        Card cardReceived = CardMapper.toEntity(cardDTO);
        Card cardOnBase = cardRepository.findById(cardDTO.getId()).orElseThrow(NoResultByIdException::new);
        if (!cardOnBase.getAlteredId().equals(cardReceived.getAlteredId())
                || !cardOnBase.getReference().equals(cardReceived.getReference())
                || !cardOnBase.getName().equals(cardReceived.getName())
                || !cardOnBase.getImage().equals(cardReceived.getImage())
                || !cardOnBase.getIsSuspended().equals(cardReceived.getIsSuspended())
                || !cardOnBase.getIsErrated().equals(cardReceived.getIsErrated())
                || !cardOnBase.getIsBanned().equals(cardReceived.getIsBanned())) {
            throw new BadRequestException();
        }

        validateCardSubObject(cardDTO);

    }

    public void validateCardSubObject(CardDTO cardDTO) {
        validateFaction(cardDTO.getFaction());
        typeService.validateType(cardDTO.getType());

        if(cardDTO.getSubTypes() != null && !cardDTO.getSubTypes().isEmpty()) {
            cardDTO.getSubTypes().forEach(subTypeService::validateSubType);
        }
        cardDTO.getSets().forEach(setService::validateSet);
        rarityService.validateRarity(cardDTO.getRarity());
    }

    public void validateDeck(DeckDTO deckDTO) {

        Player playerOnBase = playerRepository.findById(deckDTO.getPlayerId()).orElseThrow(NoResultByIdException::new);

        Deck deckReceived = DeckMapper.toEntity(deckDTO, playerOnBase);
        Deck deckOnBase = deckRepository.findById(deckDTO.getId()).orElseThrow(NoResultByIdException::new);

        if (!deckOnBase.getName().equals(deckReceived.getName())
                || !deckOnBase.getDescription().equals(deckReceived.getDescription())
                || !deckOnBase.getDateOfCreation().equals(deckReceived.getDateOfCreation())
                || !deckOnBase.getLastModification().equals(deckReceived.getLastModification())
                || !deckOnBase.getPlayer().getId().equals(playerOnBase.getId())) {
            throw new BadRequestException();
        }

        validateFaction(deckDTO.getFaction());
        validateHero(deckDTO.getHero());

        if (deckOnBase.getCards().size() == deckReceived.getCards().size()) {
            deckDTO.getCards().forEach(validationService::validateCard);
        }

    }

    public void validateFaction(FactionDTO factionReceived) {
        Faction faction = FactionMapper.toEntity(factionReceived);
        Faction factionOnBase = factionRepository.findById(faction.getId()).orElseThrow(NoResultByIdException::new);
        if (!factionOnBase.getFactionId().equals(faction.getFactionId())
                || !factionOnBase.getName().equals(faction.getName())
                || !factionOnBase.getReference().equals(faction.getReference())
                || !factionOnBase.getColor().equals(faction.getColor())) {
            throw new BadRequestException();
        }
    }

    public void validateHero(HeroDTO heroDTO){
        Hero heroReceived = HeroMapper.toEntity(heroDTO);
        Hero heroOnBase = heroRepository.findById(heroDTO.getId()).orElseThrow(NoResultByIdException::new);
        if (!heroOnBase.getName().equals(heroReceived.getName())
                || !heroOnBase.getReserveSlot().equals(heroReceived.getReserveSlot())
                || !heroOnBase.getLandmarkSlot().equals(heroReceived.getLandmarkSlot())
                || !heroOnBase.getEffect().equals(heroReceived.getEffect())) {
            throw new BadRequestException();
        }

        validationService.validateFaction(heroDTO.getFaction());
    }

    public void validateParticipant(ParticipantDTO participantDTO) {
        Participant participantOnBase = participantRepository.findById(participantDTO.getId()).orElseThrow(NoResultByIdException::new);

        if(!participantOnBase.getScore().equals(participantDTO.getScore())
                || !participantOnBase.getClassement().equals(participantDTO.getClassement())
                || !participantOnBase.getPlayer().getName().equals(participantDTO.getPlayerName())
                || !participantOnBase.getTournament().getId().equals(participantDTO.getTournamentId())) {
            throw new BadRequestException();
        }

        validationService.validateDeck(participantDTO.getDeck());
    }

    public void verifyDeckIntegrity(DeckDTO deckDTO) {
        if (deckDTO.getName() == null || deckDTO.getName().isEmpty()
                || deckDTO.getDescription() == null || deckDTO.getDescription().isEmpty()
                || deckDTO.getPlayerId() == null
                || deckDTO.getDateOfCreation() == null
                || deckDTO.getLastModification() == null) {
            throw new BadRequestException();
        }

        verifyFactionIntegrity(deckDTO.getFaction());
        verifyHeroIntegrity(deckDTO.getHero());

        if (deckDTO.getCards() == null || deckDTO.getCards().isEmpty()) {
            throw new BadRequestException();
        } else if (deckDTO.getCards().size() < 39 || deckDTO.getCards().size() > 59) {
            throw new InvalidDeckSizeException();
        } else {
            deckDTO.getCards().forEach(validationService::verifyCardIntegrity);
        }

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
        verifyFactionIntegrity(cardDTO.getFaction());
        typeService.verifyTypeIntegrity(cardDTO.getType());
        if (cardDTO.getSubTypes() != null && !cardDTO.getSubTypes().isEmpty()) {
            cardDTO.getSubTypes().forEach(subTypeService::verifySubTypeIntegrity);
        }
        cardDTO.getSets().forEach(setService::verifySetIntegrity);
        rarityService.verifyRarityIntegrity(cardDTO.getRarity());
        verifyElementIntegrity(cardDTO.getElement());
    }

    public void verifyElementIntegrity(ElementDTO elementDTO) {
        if ((elementDTO.getMainCost() != null && elementDTO.getMainCost().isEmpty())
                || (elementDTO.getRecallCost() != null && elementDTO.getRecallCost().isEmpty())
                || (elementDTO.getOceanPower() != null && elementDTO.getOceanPower().isEmpty())
                || (elementDTO.getMountainPower() != null && elementDTO.getMountainPower().isEmpty())
                || (elementDTO.getForestPower() != null && elementDTO.getForestPower().isEmpty())
                || (elementDTO.getMainEffect() != null && elementDTO.getMainEffect().isEmpty())
                || (elementDTO.getEchoEffect() != null && elementDTO.getEchoEffect().isEmpty())) {
            throw new BadRequestException();
        }
    }

    public void verifyFactionIntegrity(FactionDTO factionDTO) {
        if (factionDTO.getFactionId() == null || factionDTO.getFactionId().isEmpty()
                || factionDTO.getName() == null || factionDTO.getName().isEmpty()
                || factionDTO.getReference() == null || factionDTO.getReference().isEmpty()
                || factionDTO.getColor() == null || factionDTO.getColor().isEmpty()) {
            throw new BadRequestException();
        }
    }

    public void verifyHeroIntegrity(HeroDTO heroDTO) {
        if(heroDTO.getName() == null || heroDTO.getName().isEmpty()
                || heroDTO.getReserveSlot() == null || heroDTO.getReserveSlot() < 0
                || heroDTO.getLandmarkSlot() == null || heroDTO.getLandmarkSlot() < 0
                || heroDTO.getEffect() == null || heroDTO.getEffect().isEmpty()){
            throw new BadRequestException();
        }
        validationService.verifyFactionIntegrity(heroDTO.getFaction());
    }

    public void verifyParticipantIntegrity(ParticipantDTO participantDTO) {
        if(participantDTO.getScore() == null || participantDTO.getScore().isEmpty()
                || participantDTO.getClassement() == null || participantDTO.getClassement() <= 1
                || !playerRepository.existsByName(participantDTO.getPlayerName())
                || !tournamentRepository.existsById(participantDTO.getTournamentId())) {
            throw new BadRequestException();
        }

        validationService.verifyDeckIntegrity(participantDTO.getDeck());

    }

}
