package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.CardDTO;
import com.alterhub.alterhubbackend.dto.DeckDTO;
import com.alterhub.alterhubbackend.dto.ParticipantDTO;
import com.alterhub.alterhubbackend.entity.Deck;
import com.alterhub.alterhubbackend.entity.Participant;
import com.alterhub.alterhubbackend.entity.Player;
import com.alterhub.alterhubbackend.entity.Tournament;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ParticipantMapper {

    public ParticipantDTO toDto(Participant participant,  DeckDTO deckDTO){
        if(participant == null) return null;

        return ParticipantDTO.builder()
                .id(participant.getId())
                .playerName(participant.getPlayer().getName())
                .tournamentId(participant.getTournament().getId())
                .score(participant.getScore())
                .classement(participant.getClassement())
                .deck(deckDTO)
                .build();
    }

    public Participant toEntity(ParticipantDTO participantDTO, Player player, Tournament tournament,  Deck deck){
        if(participantDTO == null) return null;

        return Participant.builder()
                .id(participantDTO.getId())
                .player(player)
                .tournament(tournament)
                .score(participantDTO.getScore())
                .classement(participantDTO.getClassement())
                .deck(deck)
                .build();
    }

}
