package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.ParticipantDTO;
import com.alterhub.alterhubbackend.entity.Participant;

import java.util.List;
import java.util.UUID;

public interface ParticipantService {

    List<ParticipantDTO> getAllParticipants();

    List<ParticipantDTO> getParticipantsByPlayerId(UUID playerId);

    List<ParticipantDTO> getParticipantsByTournamentId(UUID tournamentId);

    List<ParticipantDTO> getParticipantsByClassement(Short classement);

    List<ParticipantDTO> getParticipantsByDeckFactionId(UUID deckFactionId);

    List<ParticipantDTO> getParticipantsByDeckFactionIdIn(List<UUID> deckFactionIds);

    List<ParticipantDTO> getParticipantsByDeckHeroId(UUID deckHeroId);

    List<ParticipantDTO> getParticipantsByDeckHeroIdIn(List<UUID> deckHeroIds);

    List<ParticipantDTO> getParticipantsByDeckTagId(UUID deckTagId);

    List<ParticipantDTO> getParticipantsByDeckTagIdIn(List<UUID> deckTagIds);

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
