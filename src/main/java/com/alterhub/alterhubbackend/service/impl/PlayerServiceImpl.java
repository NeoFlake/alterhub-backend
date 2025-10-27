package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.DeckDTO;
import com.alterhub.alterhubbackend.dto.ParticipantDTO;
import com.alterhub.alterhubbackend.dto.PlayerDTO;
import com.alterhub.alterhubbackend.entity.Deck;
import com.alterhub.alterhubbackend.entity.Participant;
import com.alterhub.alterhubbackend.entity.Player;
import com.alterhub.alterhubbackend.entity.User;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.*;
import com.alterhub.alterhubbackend.repository.PlayerRepository;
import com.alterhub.alterhubbackend.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final CardService cardService;
    private final PlayerRepository playerRepository;

    private final DeckService deckService;
    private final ParticipantService participantService;
    private final UserService userService;

    @Override
    public PlayerDTO getPlayerById(UUID id) {
        Player player =  playerRepository.findById(id).orElseThrow(NoResultByIdException::new);
        return mapPlayerDTOWithSubObject(player);
    }

    public void validatePlayer(Player player) {

    }

    public PlayerDTO mapPlayerDTOWithSubObject(Player player) {
        List<DeckDTO> decksDTO = deckService.mapDecksDTOWithSubObjects(player.getDecks());
        List<ParticipantDTO> participantsDTO = participantService.mapParticipantsDTOWithSubObjets(player.getParticipants());

        return PlayerMapper.toDTO(player, decksDTO, participantsDTO);
    }

    public List<PlayerDTO> mapPlayersDTOWithSubObject(List<Player> players){
        return players.stream().map(this::mapPlayerDTOWithSubObject).toList();
    }

    public Player mapPlayerWithSubObject(PlayerDTO playerDTO) {
        List<Deck> decks = deckService.mapDecksWithSubObjects(playerDTO.getDecks());
        List<Participant> participants = participantService.mapParticipantsWithSubObjets(playerDTO.getParticipants());
        User user = userService.getUserByIdInternalUsage(playerDTO.getUserId());

        return PlayerMapper.toEntity(playerDTO, decks, participants, user);
    }

    public List<Player> mapPlayersWithSubObject(List<PlayerDTO> playersDTO){
        return playersDTO.stream().map(this::mapPlayerWithSubObject).toList();
    }

}
