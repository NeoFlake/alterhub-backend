package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.TournamentDTO;
import com.alterhub.alterhubbackend.entity.Tournament;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.TournamentMapper;
import com.alterhub.alterhubbackend.repository.TournamentRepository;
import com.alterhub.alterhubbackend.service.interfaces.ParticipantService;
import com.alterhub.alterhubbackend.service.interfaces.TournamentService;
import lombok.RequiredArgsConstructor;
import org.owasp.encoder.Encode;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final ParticipantService participantService;

    private final LocalDate startOfWeekLocalDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    private final LocalDate endOfWeekLocalDate = LocalDate.now();
    private final LocalDate startOfMonthLocalDate = LocalDate.now().withDayOfMonth(1);
    private final LocalDate endOfMonthLocalDate = LocalDate.now();

    public List<TournamentDTO> getAllTournaments() {
        return mapTournamentsDTOWithSubObject(tournamentRepository.findAll());
    }

    public TournamentDTO getTournamentById(UUID tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(NoResultByIdException::new);
        return mapTournamentDTOWithSubObject(tournament);
    }

    public TournamentDTO getTournamentByName(String tournamentName) {
        if (tournamentName == null || tournamentName.isEmpty()) {
            throw new BadRequestException();
        }
        return mapTournamentDTOWithSubObject(tournamentRepository.findByName(Encode.forHtml(tournamentName)));
    }

    public List<TournamentDTO> getTournamentByLessOfNumberOfPlayer(Integer numberOfPlayers) {
        if (numberOfPlayers == null || numberOfPlayers <= 0) {
            throw new BadRequestException();
        }
        return mapTournamentsDTOWithSubObject(tournamentRepository.findByNumberOfPlayersIsLessThanEqual((numberOfPlayers)));
    }

    public List<TournamentDTO> getTournamentByGreaterOfNumberOfPlayer(Integer numberOfPlayers) {
        if (numberOfPlayers == null || numberOfPlayers <= 0) {
            throw new BadRequestException();
        }
        return mapTournamentsDTOWithSubObject(tournamentRepository.findByNumberOfPlayersIsGreaterThanEqual((numberOfPlayers)));
    }

    public List<TournamentDTO> getTournamentBetweenARangeOfNumberOfPlayers(Integer minimalNumberOfPlayers, Integer maximalNumberOfPlayers) {
        if (minimalNumberOfPlayers == null || maximalNumberOfPlayers == null || minimalNumberOfPlayers <= 0 || maximalNumberOfPlayers <= 0) {
            throw new BadRequestException();
        }
        return mapTournamentsDTOWithSubObject(tournamentRepository.findByNumberOfPlayersIsBetween(minimalNumberOfPlayers, maximalNumberOfPlayers));
    }

    public List<TournamentDTO> getTournamentByLocation(String location) {
        if (location == null || location.isEmpty()) {
            throw new BadRequestException();
        }
        return mapTournamentsDTOWithSubObject(tournamentRepository.findByLocation(Encode.forHtml(location)));
    }

    public List<TournamentDTO> getTournamentByDate(LocalDate date) {
        if (date == null || date.isAfter(LocalDate.now())) {
            throw new BadRequestException();
        }
        return mapTournamentsDTOWithSubObject(tournamentRepository.findByDate(date));
    }

    public List<TournamentDTO> getTournamentPlayedBeforeADate(LocalDate date) {
        if (date == null || date.isAfter(LocalDate.now())) {
            throw new BadRequestException();
        }
        return mapTournamentsDTOWithSubObject(tournamentRepository.findByDateIsBefore(date));
    }

    public List<TournamentDTO> getTournamentPlayedAfterADate(LocalDate date) {
        if (date == null || date.isAfter(LocalDate.now().minusDays(2))) {
            throw new BadRequestException();
        }
        return mapTournamentsDTOWithSubObject(tournamentRepository.findByDateIsAfter(date));
    }

    public List<TournamentDTO> getTournamentPlayedBetweenARangeOfDate(LocalDate startingDate, LocalDate endingDate) {
        if (startingDate == null || endingDate == null || endingDate.isBefore(startingDate) || startingDate.isAfter(LocalDate.now().minusDays(2)) || endingDate.isAfter(LocalDate.now())) {
            throw new BadRequestException();
        }
        return mapTournamentsDTOWithSubObject(tournamentRepository.findByDateIsBetween(startingDate, endingDate));
    }

    public List<TournamentDTO> getTournamentPlayedThisWeek() {
        return mapTournamentsDTOWithSubObject(tournamentRepository.findByDateIsBetween(startOfWeekLocalDate, endOfWeekLocalDate));
    }

    public List<TournamentDTO> getTournamentPlayedThisMonth() {
        return mapTournamentsDTOWithSubObject(tournamentRepository.findByDateIsBetween(startOfMonthLocalDate, endOfMonthLocalDate));
    }

    public List<TournamentDTO> getTournamentByPlayerId(UUID playerId) {
        if (playerId == null) {
            throw new BadRequestException();
        }
        return mapTournamentsDTOWithSubObject(tournamentRepository.findByParticipantsPlayer_Id(playerId));
    }

    public List<TournamentDTO> getTournamentsByFactionId(UUID factionId) {
        if (factionId == null) {
            throw new BadRequestException();
        }
        return mapTournamentsDTOWithSubObject(tournamentRepository.findByParticipantsDeckFaction_Id(factionId));
    }

    public List<TournamentDTO> getTournamentsByFactionIdIn(List<UUID> factionIds) {
        if (factionIds == null || factionIds.isEmpty()) {
            throw new BadRequestException();
        }
        return mapTournamentsDTOWithSubObject(tournamentRepository.findByParticipantsDeckFaction_IdIn(factionIds));
    }

    public List<TournamentDTO> getTournamentsByHeroId(UUID heroId) {
        if (heroId == null) {
            throw new BadRequestException();
        }
        return mapTournamentsDTOWithSubObject(tournamentRepository.findByParticipantsDeckHero_Id(heroId));
    }

    public List<TournamentDTO> getTournamentsByHeroIdIn(List<UUID> heroIds) {
        if (heroIds == null || heroIds.isEmpty()) {
            throw new BadRequestException();
        }
        return mapTournamentsDTOWithSubObject(tournamentRepository.findByParticipantsDeckHero_IdIn(heroIds));
    }

    public TournamentDTO addTournament(TournamentDTO tournamentDTO) {
        verifyTournamentIntegrity(tournamentDTO);
        return mapTournamentDTOWithSubObject(tournamentRepository.save(mapTournamentWithSubObject(tournamentDTO)));
    }

    public TournamentDTO updateTournamentById(UUID id, TournamentDTO tournamentDTO) {
        if(tournamentDTO.getId().equals(id)) {
            verifyTournamentIntegrity(tournamentDTO);

            Tournament tournamentToUpdate = tournamentRepository.findById(tournamentDTO.getId()).orElseThrow(NoResultByIdException::new);
            Tournament tournamentUpdated = mapTournamentWithSubObject(tournamentDTO);

            tournamentToUpdate.setName(Encode.forHtml(tournamentUpdated.getName()));
            tournamentToUpdate.setNumberOfPlayers(tournamentUpdated.getNumberOfPlayers());
            tournamentToUpdate.setLocation(Encode.forHtml(tournamentUpdated.getLocation()));
            tournamentToUpdate.setDate(tournamentUpdated.getDate());
            tournamentToUpdate.setParticipants(tournamentUpdated.getParticipants());

            return mapTournamentDTOWithSubObject(tournamentRepository.save(tournamentToUpdate));
        } else {
            throw new IdNotMatchException();
        }
    }

    public void deleteTournamentById(UUID id) {
        if (!tournamentRepository.existsById(id)) {
            throw new BadRequestException();
        }
        participantService.deleteParticipantsByTournamentId(id);
        tournamentRepository.deleteById(id);
    }

    public void verifyTournamentIntegrity(TournamentDTO tournamentDTO) {

        if(tournamentDTO.getName() == null || tournamentDTO.getName().isEmpty()
        || tournamentDTO.getNumberOfPlayers() == null || tournamentDTO.getNumberOfPlayers() <= 0
        || tournamentDTO.getLocation() == null || tournamentDTO.getLocation().isEmpty()
            || tournamentDTO.getDate() == null || tournamentDTO.getDate().isAfter(LocalDate.now())){
            throw new BadRequestException();
        }

        tournamentDTO.getParticipants().forEach(participantService::verifyParticipantIntegrity);

    }

    public Boolean existsById(UUID tournamentId) {
        return tournamentRepository.existsById(tournamentId);
    }

    public TournamentDTO mapTournamentDTOWithSubObject(Tournament tournament) {
        return TournamentMapper.toDto(tournament, participantService.mapParticipantsDTOWithSubObjects(tournament.getParticipants()));
    }

    public List<TournamentDTO> mapTournamentsDTOWithSubObject(List<Tournament> tournaments) {
        return tournaments.stream().map(this::mapTournamentDTOWithSubObject).toList();
    }

    public Tournament mapTournamentWithSubObject(TournamentDTO tournamentDTO) {
        return TournamentMapper.toEntity(tournamentDTO, participantService.mapParticipantsWithSubObjects(tournamentDTO.getParticipants()));
    }

    public List<Tournament> mapTournamentsWithSubObject(List<TournamentDTO> tournamentsDTO) {
        return tournamentsDTO.stream().map(this::mapTournamentWithSubObject).toList();
    }

}
