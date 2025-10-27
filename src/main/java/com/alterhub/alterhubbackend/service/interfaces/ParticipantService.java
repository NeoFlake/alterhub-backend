package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.ParticipantDTO;
import com.alterhub.alterhubbackend.entity.Participant;

import java.util.List;
import java.util.UUID;

public interface ParticipantService {

    List<ParticipantDTO> getParticipantsByPlayerId(UUID playerId);

    ParticipantDTO mapParticipantDTOWithSubObjets(Participant participant);

    List<ParticipantDTO> mapParticipantsDTOWithSubObjets(List<Participant> participants);

    Participant mapParticipantWithSubObjets(ParticipantDTO participantDTO);

    List<Participant> mapParticipantsWithSubObjets(List<ParticipantDTO> participantsDTO);

    void validateParticipant(ParticipantDTO participantDTO);
}
