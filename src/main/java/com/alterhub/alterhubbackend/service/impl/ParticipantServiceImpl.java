package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.ParticipantDTO;
import com.alterhub.alterhubbackend.entity.Deck;
import com.alterhub.alterhubbackend.entity.Participant;
import com.alterhub.alterhubbackend.entity.Player;
import com.alterhub.alterhubbackend.entity.Tournament;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.ParticipantMapper;
import com.alterhub.alterhubbackend.repository.ParticipantRepository;
import com.alterhub.alterhubbackend.service.interfaces.DeckService;
import com.alterhub.alterhubbackend.service.interfaces.ParticipantService;
import com.alterhub.alterhubbackend.service.interfaces.PlayerService;
import com.alterhub.alterhubbackend.service.interfaces.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final DeckService deckService;
    private final PlayerService playerService;
    private final TournamentService tournamentService;

    public List<ParticipantDTO> getAllParticipants(){
        return mapParticipantsDTOWithSubObjets(participantRepository.findAll());
    }

    private List<Participant> getParticipantsByPlayerIdInternalUsage(UUID playerId) {
        if(playerId == null){
            throw new BadRequestException();
        }

        return participantRepository.findByPlayer_Id(playerId);
    }

    public List<ParticipantDTO> getParticipantsByPlayerId(UUID playerId) {
        return mapParticipantsDTOWithSubObjets(getParticipantsByPlayerIdInternalUsage(playerId));
    }

    public List<ParticipantDTO> getParticipantsByTournamentId(UUID tournamentId) {
        if(tournamentId == null){
            throw new BadRequestException();
        }
        return mapParticipantsDTOWithSubObjets(participantRepository.findByTournament_Id(tournamentId));
    }

    public List<ParticipantDTO> getParticipantsByClassement(Short classement) {
        if(classement==null || classement < 1){
            throw new BadRequestException();
        }
        return mapParticipantsDTOWithSubObjets(participantRepository.findByClassement(classement));
    }

    public List<ParticipantDTO> getParticipantsByDeckFactionId(UUID deckFactionId) {
        if(deckFactionId == null){
            throw new BadRequestException();
        }
        return mapParticipantsDTOWithSubObjets(participantRepository.findByDeckFaction_Id(deckFactionId));
    }

    public List<ParticipantDTO> getParticipantsByDeckFactionIdIn(List<UUID> deckFactionIds) {
        if(deckFactionIds == null ||  deckFactionIds.isEmpty()){
            throw new BadRequestException();
        }
        return mapParticipantsDTOWithSubObjets(participantRepository.findByDeckFaction_IdIn(deckFactionIds));
    }

    public List<ParticipantDTO> getParticipantsByDeckHeroId(UUID deckHeroId) {
        if(deckHeroId == null){
            throw new BadRequestException();
        }
        return mapParticipantsDTOWithSubObjets(participantRepository.findByDeckHero_Id(deckHeroId));
    }

    public List<ParticipantDTO> getParticipantsByDeckHeroIdIn(List<UUID> deckHeroIds) {
        if(deckHeroIds == null || deckHeroIds.isEmpty()) {
            throw new BadRequestException();
        }
        return mapParticipantsDTOWithSubObjets(participantRepository.findByDeckHero_IdIn(deckHeroIds));
    }

    public List<ParticipantDTO> getParticipantsByDeckTagId(UUID deckTagId) {
        if(deckTagId == null){
            throw new BadRequestException();
        }
        return mapParticipantsDTOWithSubObjets(participantRepository.findByDeckTags_Id(deckTagId));
    }

    public List<ParticipantDTO> getParticipantsByDeckTagIdIn(List<UUID> deckTagIds) {
        if(deckTagIds == null || deckTagIds.isEmpty()) {
            throw new BadRequestException();
        }
        return mapParticipantsDTOWithSubObjets(participantRepository.findByDeckTags_IdIn(deckTagIds));
    }

    public void verifyParticipantIntegrity(ParticipantDTO participantDTO) {
        if(participantDTO.getScore() == null || participantDTO.getScore().isEmpty()
        || participantDTO.getClassement() == null || participantDTO.getClassement() <= 1
        || !playerService.existsByName(participantDTO.getPlayerName())
        || !tournamentService.existsById(participantDTO.getTournamentId())) {
            throw new BadRequestException();
        }

        deckService.verifyDeckIntegrity(participantDTO.getDeck());

    }

    public void validateParticipant(ParticipantDTO participantDTO) {
        Participant participantReceived = mapParticipantWithSubObjets(participantDTO);
        Participant participantOnBase = participantRepository.findById(participantDTO.getId()).orElseThrow(NoResultByIdException::new);

        if(!participantOnBase.getScore().equals(participantReceived.getScore())
        || !participantOnBase.getClassement().equals(participantReceived.getClassement())
        || !participantOnBase.getPlayer().getName().equals(participantDTO.getPlayerName())
        || !participantOnBase.getTournament().getId().equals(participantDTO.getTournamentId())) {
            throw new BadRequestException();
        }

        deckService.validateDeck(participantDTO.getDeck());
    }

    public ParticipantDTO mapParticipantDTOWithSubObjets(Participant participant) {
        return  ParticipantMapper.toDto(participant, deckService.mapDeckDTOWithSubObjects(participant.getDeck()));
    }

    public List<ParticipantDTO> mapParticipantsDTOWithSubObjets(List<Participant> participants) {
        return participants.stream().map(this::mapParticipantDTOWithSubObjets).toList();
    }

    public Participant mapParticipantWithSubObjets(ParticipantDTO participantDTO) {

        Player player = playerService.mapPlayerWithSubObject(playerService.getPlayerById(participantDTO.getId()));
        Tournament tournament = tournamentService.mapTournamentWithSubObjet(tournamentService.getTournamentById(participantDTO.getId()));
        Deck deck = deckService.mapDeckWithSubObjects(participantDTO.getDeck());

        return  ParticipantMapper.toEntity(participantDTO, player, tournament, deck);
    }

    public List<Participant> mapParticipantsWithSubObjets(List<ParticipantDTO> participantsDTO) {
        return participantsDTO.stream().map(this::mapParticipantWithSubObjets).toList();
    }

}
