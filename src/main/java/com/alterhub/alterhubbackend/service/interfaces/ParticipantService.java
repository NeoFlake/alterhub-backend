package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.ParticipantDTO;
import com.alterhub.alterhubbackend.entity.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ParticipantService {

    Page<ParticipantDTO> getAllParticipants(Pageable pageable);

    ParticipantDTO getParticipantById(UUID id);

    List<ParticipantDTO> getParticipantsByPlayerId(UUID playerId);

    List<ParticipantDTO> getParticipantsByTournamentId(UUID tournamentId);

    List<ParticipantDTO> getParticipantsByClassement(Short classement);

    Page<ParticipantDTO> getParticipantsByDeckFactionId(UUID deckFactionId, Pageable pageable);

    Page<ParticipantDTO> getParticipantsByDeckFactionIdIn(List<UUID> deckFactionIds, Pageable pageable);

    Page<ParticipantDTO> getParticipantsByDeckHeroId(UUID deckHeroId, Pageable pageable);

    Page<ParticipantDTO> getParticipantsByDeckHeroIdIn(List<UUID> deckHeroIds, Pageable pageable);

    Page<ParticipantDTO> getParticipantsByDeckTagId(UUID deckTagId, Pageable pageable);

    Page<ParticipantDTO> getParticipantsByDeckTagIdIn(List<UUID> deckTagIds, Pageable pageable);

    ParticipantDTO addParticipant(ParticipantDTO participantDTO);

    ParticipantDTO updateParticipantById(UUID id, ParticipantDTO participantDTO);

    void deleteParticipantById(UUID id);

    void deleteParticipantsByTournamentId(UUID tournamentId);

    ParticipantDTO mapParticipantDTOWithSubObjects(Participant participant);

    List<ParticipantDTO> mapParticipantsDTOWithSubObjects(List<Participant> participants);

    Participant mapParticipantWithSubObjects(ParticipantDTO participantDTO);

    List<Participant> mapParticipantsWithSubObjects(List<ParticipantDTO> participantsDTO);

    void validateParticipant(ParticipantDTO participantDTO);

    void verifyParticipantIntegrity(ParticipantDTO participantDTO);
}
