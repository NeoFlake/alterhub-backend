package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.TournamentDTO;
import com.alterhub.alterhubbackend.entity.Tournament;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.TournamentMapper;
import com.alterhub.alterhubbackend.repository.TournamentRepository;
import com.alterhub.alterhubbackend.service.interfaces.ParticipantService;
import com.alterhub.alterhubbackend.service.interfaces.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final ParticipantService participantService;

    public TournamentDTO getTournamentById(UUID tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(NoResultByIdException::new);
        return mapTournamentDTOWithSubObjet(tournament);
    }

    public TournamentDTO mapTournamentDTOWithSubObjet(Tournament tournament) {
        return TournamentMapper.toDto(tournament, participantService.mapParticipantsDTOWithSubObjets(tournament.getParticipants()));
    }

    public List<TournamentDTO> mapTournamentsDTOWithSubObjet(List<Tournament> tournaments) {
        return tournaments.stream().map(this::mapTournamentDTOWithSubObjet).toList();
    }

    public Tournament mapTournamentWithSubObjet(TournamentDTO tournamentDTO) {
        return TournamentMapper.toEntity(tournamentDTO, participantService.mapParticipantsWithSubObjets(tournamentDTO.getParticipants()));
    }

    public List<Tournament> mapTournamentsWithSubObjet(List<TournamentDTO> tournamentsDTO) {
        return tournamentsDTO.stream().map(this::mapTournamentWithSubObjet).toList();
    }

}
