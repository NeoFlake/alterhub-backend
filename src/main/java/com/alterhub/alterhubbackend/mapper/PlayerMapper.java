package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.DeckDTO;
import com.alterhub.alterhubbackend.dto.ParticipantDTO;
import com.alterhub.alterhubbackend.dto.PlayerDTO;
import com.alterhub.alterhubbackend.entity.Deck;
import com.alterhub.alterhubbackend.entity.Participant;
import com.alterhub.alterhubbackend.entity.Player;
import com.alterhub.alterhubbackend.entity.User;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class PlayerMapper {

    public PlayerDTO toDTO(Player player, List<DeckDTO> decksDTO, List<ParticipantDTO> participantsDTO){
        if(player ==  null) return null;

        return PlayerDTO.builder()
                .id(player.getId())
                .name(player.getName())
                .userId(player.getUser().getId())
                .decks(decksDTO)
                .participants(participantsDTO)
                .build();
    }

    public Player toEntity(PlayerDTO playerDTO, List<Deck> decks, List<Participant> participants, User user) {
        if(playerDTO == null) return null;

        return Player.builder()
                .id(playerDTO.getId())
                .name(playerDTO.getName())
                .user(user)
                .decks(decks)
                .participants(participants)
                .build();
    }

}
