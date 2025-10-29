package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.ParticipantDTO;
import com.alterhub.alterhubbackend.entity.Deck;
import com.alterhub.alterhubbackend.entity.Participant;
import com.alterhub.alterhubbackend.entity.Player;
import com.alterhub.alterhubbackend.entity.Tournament;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.ParticipantMapper;
import com.alterhub.alterhubbackend.repository.ParticipantRepository;
import com.alterhub.alterhubbackend.service.interfaces.DeckService;
import com.alterhub.alterhubbackend.service.interfaces.ParticipantService;
import com.alterhub.alterhubbackend.service.interfaces.PlayerService;
import com.alterhub.alterhubbackend.service.interfaces.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final DeckService deckService;
    private final PlayerService playerService;
    private final TournamentService tournamentService;

    public Page<ParticipantDTO> getAllParticipants(Pageable pageable){
        Page<Participant> participantPage = participantRepository.findAll(pageable);
        return new PageImpl<>(mapParticipantsDTOWithSubObjects(participantPage.getContent()), pageable, participantPage.getTotalElements());
    }

    private List<Participant> getParticipantsByPlayerIdInternalUsage(UUID playerId) {
        if(playerId == null){
            throw new BadRequestException();
        }

        return participantRepository.findByPlayer_Id(playerId);
    }

    public List<ParticipantDTO> getParticipantsByPlayerId(UUID playerId) {
        return mapParticipantsDTOWithSubObjects(getParticipantsByPlayerIdInternalUsage(playerId));
    }

    public List<ParticipantDTO> getParticipantsByTournamentId(UUID tournamentId) {
        if(tournamentId == null){
            throw new BadRequestException();
        }
        return mapParticipantsDTOWithSubObjects(participantRepository.findByTournament_Id(tournamentId));
    }

    public List<ParticipantDTO> getParticipantsByClassement(Short classement) {
        if(classement==null || classement < 1){
            throw new BadRequestException();
        }
        return mapParticipantsDTOWithSubObjects(participantRepository.findByClassement(classement));
    }

    public Page<ParticipantDTO> getParticipantsByDeckFactionId(UUID deckFactionId, Pageable pageable) {
        if(deckFactionId == null){
            throw new BadRequestException();
        }
        Page<Participant> participantPage = participantRepository.findByDeckFaction_Id(deckFactionId, pageable);
        return new PageImpl<>(mapParticipantsDTOWithSubObjects(participantPage.getContent()), pageable, participantPage.getTotalElements());
    }

    public Page<ParticipantDTO> getParticipantsByDeckFactionIdIn(List<UUID> deckFactionIds, Pageable pageable) {
        if(deckFactionIds == null ||  deckFactionIds.isEmpty()){
            throw new BadRequestException();
        }
        Page<Participant> participantPage = participantRepository.findByDeckFaction_IdIn(deckFactionIds, pageable);
        return new PageImpl<>(mapParticipantsDTOWithSubObjects(participantPage.getContent()), pageable, participantPage.getTotalElements());
    }

    public Page<ParticipantDTO> getParticipantsByDeckHeroId(UUID deckHeroId, Pageable pageable) {
        if(deckHeroId == null){
            throw new BadRequestException();
        }
        Page<Participant> participantPage = participantRepository.findByDeckHero_Id(deckHeroId, pageable);
        return new PageImpl<>(mapParticipantsDTOWithSubObjects(participantPage.getContent()), pageable, participantPage.getTotalElements());
    }

    public Page<ParticipantDTO> getParticipantsByDeckHeroIdIn(List<UUID> deckHeroIds, Pageable pageable) {
        if(deckHeroIds == null || deckHeroIds.isEmpty()) {
            throw new BadRequestException();
        }
        Page<Participant> participantPage = participantRepository.findByDeckHero_IdIn(deckHeroIds, pageable);
        return new PageImpl<>(mapParticipantsDTOWithSubObjects(participantPage.getContent()), pageable, participantPage.getTotalElements());
    }

    public Page<ParticipantDTO> getParticipantsByDeckTagId(UUID deckTagId, Pageable pageable) {
        if(deckTagId == null){
            throw new BadRequestException();
        }
        Page<Participant> participantPage = participantRepository.findByDeckTags_Id(deckTagId, pageable);
        return new PageImpl<>(mapParticipantsDTOWithSubObjects(participantPage.getContent()), pageable, participantPage.getTotalElements());
    }

    public Page<ParticipantDTO> getParticipantsByDeckTagIdIn(List<UUID> deckTagIds, Pageable pageable) {
        if(deckTagIds == null || deckTagIds.isEmpty()) {
            throw new BadRequestException();
        }
        Page<Participant> participantPage = participantRepository.findByDeckTags_IdIn(deckTagIds, pageable);
        return new PageImpl<>(mapParticipantsDTOWithSubObjects(participantPage.getContent()), pageable, participantPage.getTotalElements());
    }

    public ParticipantDTO addParticipant(ParticipantDTO participantDTO) {
        verifyParticipantIntegrity(participantDTO);
        participantDTO.getDeck().setIsParticipant(true);
        return mapParticipantDTOWithSubObjects(participantRepository.save(mapParticipantWithSubObjects(participantDTO)));
    }

    public ParticipantDTO updateParticipantById(UUID id, ParticipantDTO participantDTO) {
        if (participantDTO.getId().equals(id)) {
            verifyParticipantIntegrity(participantDTO);

            Participant participantToUpdate = participantRepository.findById(id).orElseThrow(NoResultByIdException::new);
            Participant participantUpdated = mapParticipantWithSubObjects(participantDTO);

            participantUpdated.setPlayer(participantToUpdate.getPlayer());
            participantUpdated.setDeck(participantToUpdate.getDeck());
            participantUpdated.setTournament(participantToUpdate.getTournament());

            return mapParticipantDTOWithSubObjects(participantRepository.save(participantToUpdate));
        } else {
            throw new IdNotMatchException();
        }
    }

    public void deleteParticipantById(UUID id) {
        if(!participantRepository.existsById(id)){
            throw new NoResultByIdException();
        }

        UUID deckId = participantRepository.findById(id).orElseThrow(NoResultByIdException::new).getDeck().getId();

        participantRepository.deleteById(id);
        // Si le deck n'a plus aucune participation à un tournoi active, alors il faut lui supprimer
        // Son statut de deck participant pour permettre sa modification/suppression par son utilisateur.
        if(!participantRepository.existsByPlayer_Id(deckId)){
            deckService.patchIsParticipantByDeckId(deckId, false);
        }
    }

    public void deleteParticipantsByTournamentId(UUID tournamentId){

        if(!participantRepository.existsByTournamentId(tournamentId)){
            throw new NoResultByIdException();
        }
        List<Participant> participants = participantRepository.findByTournament_Id(tournamentId);
        List<UUID> deckIds = new ArrayList<>();

        if(!participants.isEmpty()){
            deckIds = participants.stream().map(participant -> participant.getDeck().getId()).toList();
        }
        participantRepository.deleteByTournamentId(tournamentId);

        // Vérification groupée de l'ensemble des decks impactés par la suppression du tournoi
        // et savoir s'ils nécessitent le switch de leurs paramètres isParticipant
        if(!participants.isEmpty()){
            Set<UUID> decksIdWhoStayOnParticipant = new HashSet<>(participantRepository.findExistingDeckIds(deckIds));
            List<UUID> decksIdNotPresentOnParticipant = deckIds.stream()
                    .filter(id -> !decksIdWhoStayOnParticipant.contains(id))
                    .toList();
            deckService.patchIsParticipantByDeckIdIn(decksIdNotPresentOnParticipant, false);
        }
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
        Participant participantReceived = mapParticipantWithSubObjects(participantDTO);
        Participant participantOnBase = participantRepository.findById(participantDTO.getId()).orElseThrow(NoResultByIdException::new);

        if(!participantOnBase.getScore().equals(participantReceived.getScore())
        || !participantOnBase.getClassement().equals(participantReceived.getClassement())
        || !participantOnBase.getPlayer().getName().equals(participantDTO.getPlayerName())
        || !participantOnBase.getTournament().getId().equals(participantDTO.getTournamentId())) {
            throw new BadRequestException();
        }

        deckService.validateDeck(participantDTO.getDeck());
    }

    public ParticipantDTO mapParticipantDTOWithSubObjects(Participant participant) {
        return  ParticipantMapper.toDto(participant, deckService.mapDeckDTOWithSubObjects(participant.getDeck()));
    }

    public List<ParticipantDTO> mapParticipantsDTOWithSubObjects(List<Participant> participants) {
        return participants.stream().map(this::mapParticipantDTOWithSubObjects).toList();
    }

    public Participant mapParticipantWithSubObjects(ParticipantDTO participantDTO) {

        Player player = playerService.mapPlayerWithSubObject(playerService.getPlayerById(participantDTO.getId()));
        Tournament tournament = tournamentService.mapTournamentWithSubObject(tournamentService.getTournamentById(participantDTO.getId()));
        Deck deck = deckService.mapDeckWithSubObjects(participantDTO.getDeck());

        return  ParticipantMapper.toEntity(participantDTO, player, tournament, deck);
    }

    public List<Participant> mapParticipantsWithSubObjects(List<ParticipantDTO> participantsDTO) {
        return participantsDTO.stream().map(this::mapParticipantWithSubObjects).toList();
    }

}
