package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.TournamentDTO;
import com.alterhub.alterhubbackend.entity.Tournament;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.TournamentMapper;

import java.util.List;
import java.util.UUID;

public interface TournamentService {

    TournamentDTO getTournamentById(UUID tournamentId);

    TournamentDTO mapTournamentDTOWithSubObjet(Tournament tournament);

    List<TournamentDTO> mapTournamentsDTOWithSubObjet(List<Tournament> tournaments);

    Tournament mapTournamentWithSubObjet(TournamentDTO tournamentDTO);

    List<Tournament> mapTournamentsWithSubObjet(List<TournamentDTO> tournamentsDTO);

}
