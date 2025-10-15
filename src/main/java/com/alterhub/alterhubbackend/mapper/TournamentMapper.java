package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.ParticipantDTO;
import com.alterhub.alterhubbackend.dto.TournamentDTO;
import com.alterhub.alterhubbackend.entity.Participant;
import com.alterhub.alterhubbackend.entity.Tournament;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TournamentMapper {

    public TournamentDTO toDto(Tournament tournament, List<ParticipantDTO> participantsDTO) {
        if (tournament == null) return null;

        return TournamentDTO.builder()
                .id(tournament.getId())
                .name(tournament.getName())
                .numberOfPlayers(tournament.getNumberOfPlayers())
                .location(tournament.getLocation())
                .participants(participantsDTO)
                .build();
    }

    public Tournament toEntity(TournamentDTO tournamentDTO, List<Participant> participants){
        if (tournamentDTO == null) return null;

        return Tournament.builder()
                .id(tournamentDTO.getId())
                .name(tournamentDTO.getName())
                .numberOfPlayers(tournamentDTO.getNumberOfPlayers())
                .location(tournamentDTO.getLocation())
                .participants(participants)
                .build();
    }

}
