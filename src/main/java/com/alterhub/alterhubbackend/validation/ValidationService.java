package com.alterhub.alterhubbackend.validation;

import com.alterhub.alterhubbackend.dto.*;
import com.alterhub.alterhubbackend.entity.*;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.InvalidDeckSizeException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.exception.PasswordWeaknessException;
import com.alterhub.alterhubbackend.mapper.*;
import com.alterhub.alterhubbackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class ValidationService {

    private final PlayerRepository playerRepository;
    private final FactionRepository factionRepository;
    private final SetRepository setRepository;
    private final RarityRepository rarityRepository;
    private final TypeRepository typeRepository;
    private final TournamentRepository tournamentRepository;
    private final SubTypeRepository subTypeRepository;
    private final UserRepository userRepository;

    private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.{12,}$)(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^\\w\\s]).*$");

    public void verifyRarityIntegrity (RarityDTO rarityDTO){
        if (rarityDTO.getRarityId() == null || rarityDTO.getRarityId().isEmpty()
                || rarityDTO.getName() == null || rarityDTO.getName().isEmpty()
                || rarityDTO.getReference() == null || rarityDTO.getReference().isEmpty()) {
            throw new BadRequestException();
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

        verifyFactionIntegrity(deckDTO.getFaction());
        verifyHeroIntegrity(deckDTO.getHero());

        if (deckDTO.getCards() == null || deckDTO.getCards().isEmpty()) {
            throw new BadRequestException();
        } else if (deckDTO.getCards().size() < 39 || deckDTO.getCards().size() > 59) {
            throw new InvalidDeckSizeException();
        } else {
            deckDTO.getCards().forEach(this::verifyCardIntegrity);
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
        verifyTypeIntegrity(cardDTO.getType());
        if (cardDTO.getSubTypes() != null && !cardDTO.getSubTypes().isEmpty()) {
            cardDTO.getSubTypes().forEach(this::verifySubTypeIntegrity);
        }
        cardDTO.getSets().forEach(this::verifySetIntegrity);
        verifyRarityIntegrity(cardDTO.getRarity());
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
        verifyFactionIntegrity(heroDTO.getFaction());
    }

    public void verifyParticipantIntegrity(ParticipantDTO participantDTO) {
        if(participantDTO.getScore() == null || participantDTO.getScore().isEmpty()
                || participantDTO.getClassement() == null || participantDTO.getClassement() <= 1
                || !playerRepository.existsByName(participantDTO.getPlayerName())
                || !tournamentRepository.existsById(participantDTO.getTournamentId())) {
            throw new BadRequestException();
        }

        verifyDeckIntegrity(participantDTO.getDeck());

    }

    public void verifyTournamentIntegrity(TournamentDTO tournamentDTO) {

        if(tournamentDTO.getName() == null || tournamentDTO.getName().isEmpty()
                || tournamentDTO.getNumberOfPlayers() == null || tournamentDTO.getNumberOfPlayers() <= 0
                || tournamentDTO.getLocation() == null || tournamentDTO.getLocation().isEmpty()
                || tournamentDTO.getDate() == null || tournamentDTO.getDate().isAfter(LocalDate.now())){
            throw new BadRequestException();
        }

        if(!tournamentDTO.getParticipants().isEmpty()){
            tournamentDTO.getParticipants().forEach(this::verifyParticipantIntegrity);
        }

    }

    public void verifySetIntegrity(SetDTO setDTO) {
        if (setDTO.getSetId() == null || setDTO.getSetId().isEmpty()
                || setDTO.getName() == null || setDTO.getName().isEmpty()
                || setDTO.getReference() == null || setDTO.getReference().isEmpty()) {
            throw new BadRequestException();
        }
    }

    public void verifySubTypeIntegrity(SubTypeDTO subTypeDTO) {
        if (subTypeDTO.getSubTypeId() == null || subTypeDTO.getSubTypeId().isEmpty()
                || subTypeDTO.getName() == null || subTypeDTO.getName().isEmpty()
                || subTypeDTO.getReference() == null || subTypeDTO.getReference().isEmpty()) {
            throw new BadRequestException();
        }
    }

    public void verifyTagIntegrity(TagDTO tagDTO) {
        if (tagDTO.getName() == null || tagDTO.getName().isEmpty()) {
            throw new BadRequestException();
        }
    }

    public void verifyTypeIntegrity(TypeDTO typeDTO) {
        if (typeDTO.getTypeId() == null || typeDTO.getTypeId().isEmpty()
                || typeDTO.getName() == null || typeDTO.getName().isEmpty()
                || typeDTO.getReference() == null || typeDTO.getReference().isEmpty()) {
            throw new BadRequestException();
        }
    }

    public void verifyUserIntegrity(UserDTO userDTO) {
        if (userDTO.getLastName() == null || userDTO.getLastName().isEmpty()
                || userDTO.getFirstName() == null || userDTO.getFirstName().isEmpty()
                || userDTO.getEmail() == null || userDTO.getEmail().isEmpty()
                || userDTO.getPlayerName() == null || userDTO.getPlayerName().isEmpty()
                || userDTO.getDateOfCreation() == null || userDTO.getLastModification() == null) {
            throw new BadRequestException();
        }
    }

    public void verifyUserRequestIntegrity(UserRequestDTO userRequestDTO) {
        if (userRequestDTO.getLastName() == null || userRequestDTO.getLastName().isEmpty()
                || userRequestDTO.getFirstName() == null || userRequestDTO.getFirstName().isEmpty()
                || userRequestDTO.getEmail() == null || userRequestDTO.getEmail().isEmpty()
                || userRequestDTO.getPlayerName() == null || userRequestDTO.getPlayerName().isEmpty()
                || userRequestDTO.getDateOfCreation() == null || userRequestDTO.getLastModification() == null
                || userRequestDTO.getPassword() == null || userRequestDTO.getPassword().isEmpty()
                || (userRequestDTO.getNewPassword() != null && userRequestDTO.getNewPassword().isEmpty())) {
            throw new BadRequestException();
        }
    }

    public void verifyUserAuthenticationIntegrity(UserAuthenticationDTO userAuthenticationDTO) {
        if (userAuthenticationDTO.getEmail() == null || userAuthenticationDTO.getEmail().isEmpty()
                || userAuthenticationDTO.getPassword() == null || userAuthenticationDTO.getPassword().isEmpty()) {
            throw new BadRequestException();
        }
    }

    public void validateCardSubObject(CardDTO cardDTO) {
        validateFaction(cardDTO.getFaction());
        validateType(cardDTO.getType());

        if(cardDTO.getSubTypes() != null && !cardDTO.getSubTypes().isEmpty()) {
            cardDTO.getSubTypes().forEach(this::validateSubType);
        }
        cardDTO.getSets().forEach(this::validateSet);
        validateRarity(cardDTO.getRarity());
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

    public void validateRarity(RarityDTO rarityDTO) {
        Rarity rarityReceived = RarityMapper.toEntity(rarityDTO);
        Rarity rarityOnBase = rarityRepository.findById(rarityReceived.getId()).orElseThrow(NoResultByIdException::new);
        if(!rarityOnBase.getRarityId().equals(rarityReceived.getRarityId())
                || !rarityOnBase.getName().equals(rarityReceived.getName())
                || !rarityOnBase.getReference().equals(rarityReceived.getReference())) {
            throw new BadRequestException();
        }
    }

    public void validateSet(SetDTO setDTO) {
        Set setReceived = SetMapper.toEntity(setDTO);
        Set setOnBase = setRepository.findById(setReceived.getId()).orElseThrow(NoResultByIdException::new);
        if(!setOnBase.getSetId().equals(setReceived.getSetId())
                || !setOnBase.getName().equals(setReceived.getName())
                || !setOnBase.getReference().equals(setReceived.getReference())) {
            throw new BadRequestException();
        }
    }

    public void validateSubType(SubTypeDTO subTypeDTO) {
        SubType subTypeReceived = SubTypeMapper.toEntity(subTypeDTO);
        SubType subTypeOnBase = subTypeRepository.findById(subTypeReceived.getId()).orElseThrow(NoResultByIdException::new);
        if(!subTypeOnBase.getSubTypeId().equals(subTypeReceived.getSubTypeId())
                || !subTypeOnBase.getName().equals(subTypeReceived.getName())
                || !subTypeOnBase.getReference().equals(subTypeReceived.getReference())) {
            throw new BadRequestException();
        }
    }

    public void validateType(TypeDTO typeDTO) {
        Type typeReceived = TypeMapper.toEntity(typeDTO);
        Type typeOnBase = typeRepository.findById(typeReceived.getId()).orElseThrow(NoResultByIdException::new);
        if(!typeOnBase.getTypeId().equals(typeReceived.getTypeId())
                || !typeOnBase.getName().equals(typeReceived.getName())
                || !typeOnBase.getReference().equals(typeReceived.getReference())) {
            throw new BadRequestException();
        }
    }

    public void validateRequestUser(UserDTO userDTO) {
        Player player = playerRepository.findByName(userDTO.getPlayerName()).orElseThrow(NoResultByIdException::new);
        User userReceived = UserMapper.toEntityFromDTO(userDTO, player);
        validateUser(userReceived, userDTO.getId());
    }

    public void validateUser(User userReceived, UUID id) {
        User userOnBase = userRepository.findById(id).orElseThrow(NoResultByIdException::new);

        if (!userReceived.getLastname().equals(userOnBase.getLastname())
                || !userReceived.getFirstname().equals(userOnBase.getFirstname())
                || !userReceived.getEmail().equals(userOnBase.getEmail())
                || !userReceived.getDateOfCreation().equals(userOnBase.getDateOfCreation())
                || !userReceived.getLastModification().equals(userOnBase.getLastModification())) {
            throw new BadRequestException();
        }
    }

    public void validatePasswordStrength(String password) {
        if (password == null || !PASSWORD_REGEX.matcher(password).matches()) {
            throw new PasswordWeaknessException();
        }
    }

}
