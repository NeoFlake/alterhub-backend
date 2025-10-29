package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.DeckDTO;
import com.alterhub.alterhubbackend.dto.ParticipantDTO;
import com.alterhub.alterhubbackend.dto.PlayerDTO;
import com.alterhub.alterhubbackend.entity.Deck;
import com.alterhub.alterhubbackend.entity.Participant;
import com.alterhub.alterhubbackend.entity.Player;
import com.alterhub.alterhubbackend.entity.User;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.*;
import com.alterhub.alterhubbackend.repository.PlayerRepository;
import com.alterhub.alterhubbackend.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.owasp.encoder.Encode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    private final DeckService deckService;
    private final ParticipantService participantService;
    private final UserService userService;

    public List<PlayerDTO> getAllPlayers() {
        return mapPlayersDTOWithSubObject(playerRepository.findAll());
    }

    public PlayerDTO getPlayerById(UUID id) {
        return mapPlayerDTOWithSubObject(playerRepository.findById(id).orElseThrow(NoResultByIdException::new));
    }

    public Player getPlayerByNameInternalUsage(String name) {
        if(name == null || name.isEmpty()){
            throw new BadRequestException();
        }
        name = Encode.forHtml(name);

        return playerRepository.findByName(name).orElseThrow(NoResultByIdException::new);
    }

    public PlayerDTO getPlayerByName(String name) {
        return mapPlayerDTOWithSubObject(getPlayerByNameInternalUsage(name));
    }

    public PlayerDTO getPlayerByUserId(UUID id) {
        return mapPlayerDTOWithSubObject(playerRepository.findByUser_Id(id).orElseThrow(NoResultByIdException::new));
    }

    public PlayerDTO addPlayer(PlayerDTO playerDTO) {
        verifyPlayerIntegrity(playerDTO);
        return mapPlayerDTOWithSubObject(playerRepository.save(mapPlayerWithSubObject(playerDTO)));
    }

    public PlayerDTO setLinkBetweenUserAndPlayer(User user, UUID playerId) {
        Player playerToLink = playerRepository.findById(playerId).orElseThrow(NoResultByIdException::new);
        playerToLink.setUser(user);
        return mapPlayerDTOWithSubObject(playerRepository.save(playerToLink));
    }

    public PlayerDTO unsetUserFromPlayer(UUID userId) {
        Player playerToUnlink = playerRepository.findByUser_Id(userId).orElseThrow(NoResultByIdException::new);
        playerToUnlink.setUser(null);
        return mapPlayerDTOWithSubObject(playerRepository.save(playerToUnlink));
    }

    public void verifyPlayerIntegrity(PlayerDTO playerDTO){
        if(playerDTO.getName() == null || playerDTO.getName().isEmpty()){
            throw new BadRequestException();
        }

        if(playerDTO.getDecks() != null && !playerDTO.getDecks().isEmpty()){
            playerDTO.getDecks().forEach(deckService::validateDeck);
        }

        if(playerDTO.getParticipants() != null && !playerDTO.getParticipants().isEmpty()){
            playerDTO.getParticipants().forEach(participantService::validateParticipant);
        }

    }

    public Boolean existsByName(String name) {
        return playerRepository.existsByName(name);
    }

    public void validatePlayer(PlayerDTO playerDTO) {
        Player playerReceived = mapPlayerWithSubObject(playerDTO);
        Player playerOnBase = playerRepository.findById(playerDTO.getId()).orElseThrow(NoResultByIdException::new);

        if (!playerOnBase.getName().equals(playerReceived.getName())) {
            throw new BadRequestException();
        }

        validatePlayerSubObject(playerDTO);
    }

    private void validatePlayerSubObject(PlayerDTO playerDTO) {
        if(playerDTO.getDecks() != null && !playerDTO.getDecks().isEmpty()){
            playerDTO.getDecks().forEach(deckService::validateDeck);
        }
        if(playerDTO.getParticipants() != null && !playerDTO.getParticipants().isEmpty()){
            playerDTO.getParticipants().forEach(participantService::validateParticipant);
        }
    }

    public PlayerDTO mapPlayerDTOWithSubObject(Player player) {
        List<DeckDTO> decksDTO = deckService.mapDecksDTOWithSubObjects(player.getDecks());
        List<ParticipantDTO> participantsDTO = participantService.mapParticipantsDTOWithSubObjects(player.getParticipants());

        return PlayerMapper.toDTO(player, decksDTO, participantsDTO);
    }

    public List<PlayerDTO> mapPlayersDTOWithSubObject(List<Player> players) {
        return players.stream().map(this::mapPlayerDTOWithSubObject).toList();
    }

    public Player mapPlayerWithSubObject(PlayerDTO playerDTO) {
        List<Deck> decks = deckService.mapDecksWithSubObjects(playerDTO.getDecks());
        List<Participant> participants = participantService.mapParticipantsWithSubObjects(playerDTO.getParticipants());
        User user = playerDTO.getUserId() != null
                ? userService.getUserByIdInternalUsage(playerDTO.getUserId())
                : null;

        return PlayerMapper.toEntity(playerDTO, decks, participants, user);
    }

    public List<Player> mapPlayersWithSubObject(List<PlayerDTO> playersDTO) {
        return playersDTO.stream().map(this::mapPlayerWithSubObject).toList();
    }

}
