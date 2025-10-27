package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.ParticipantDTO;
import com.alterhub.alterhubbackend.entity.Participant;

import java.util.List;

public interface ParticipantService {

    ParticipantDTO mapParticipantDTOWithSubObjets(Participant participant);

    List<ParticipantDTO> mapParticipantsDTOWithSubObjets(List<Participant> participants);

    Participant mapParticipantWithSubObjets(ParticipantDTO participantDTO);

    List<Participant> mapParticipantsWithSubObjets(List<ParticipantDTO> participantsDTO);

}
