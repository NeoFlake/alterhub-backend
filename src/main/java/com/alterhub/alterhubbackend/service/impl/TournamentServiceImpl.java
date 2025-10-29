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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<TournamentDTO> getAllTournaments(Pageable pageable) {
        Page<Tournament> tournamentPage = tournamentRepository.findAll(pageable);
        return new PageImpl<>(mapTournamentsDTOWithSubObject(tournamentPage.getContent()), pageable, tournamentPage.getTotalElements());
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

    public Page<TournamentDTO> getTournamentByLessOfNumberOfPlayer(Integer numberOfPlayers, Pageable pageable) {
        if (numberOfPlayers == null || numberOfPlayers <= 0) {
            throw new BadRequestException();
        }
        Page<Tournament> tournamentPage = tournamentRepository.findByNumberOfPlayersIsLessThanEqual(numberOfPlayers, pageable);
        return new PageImpl<>(mapTournamentsDTOWithSubObject(tournamentPage.getContent()), pageable, tournamentPage.getTotalElements());
    }

    public Page<TournamentDTO> getTournamentByGreaterOfNumberOfPlayer(Integer numberOfPlayers, Pageable pageable) {
        if (numberOfPlayers == null || numberOfPlayers <= 0) {
            throw new BadRequestException();
        }
        Page<Tournament> tournamentPage = tournamentRepository.findByNumberOfPlayersIsGreaterThanEqual(numberOfPlayers, pageable);
        return new PageImpl<>(mapTournamentsDTOWithSubObject(tournamentPage.getContent()), pageable, tournamentPage.getTotalElements());
    }

    public Page<TournamentDTO> getTournamentBetweenARangeOfNumberOfPlayers(Integer minimalNumberOfPlayers, Integer maximalNumberOfPlayers, Pageable pageable) {
        if (minimalNumberOfPlayers == null || maximalNumberOfPlayers == null || minimalNumberOfPlayers <= 0 || maximalNumberOfPlayers <= 0) {
            throw new BadRequestException();
        }
        Page<Tournament> tournamentPage = tournamentRepository.findByNumberOfPlayersIsBetween(minimalNumberOfPlayers, maximalNumberOfPlayers, pageable);
        return new PageImpl<>(mapTournamentsDTOWithSubObject(tournamentPage.getContent()), pageable, tournamentPage.getTotalElements());
    }

    public Page<TournamentDTO> getTournamentByLocation(String location, Pageable pageable) {
        if (location == null || location.isEmpty()) {
            throw new BadRequestException();
        }
        Page<Tournament> tournamentPage = tournamentRepository.findByLocation(Encode.forHtml(location), pageable);
        return new PageImpl<>(mapTournamentsDTOWithSubObject(tournamentPage.getContent()), pageable, tournamentPage.getTotalElements());
    }

    public Page<TournamentDTO> getTournamentByDate(LocalDate date, Pageable pageable) {
        if (date == null || date.isAfter(LocalDate.now())) {
            throw new BadRequestException();
        }
        Page<Tournament> tournamentPage = tournamentRepository.findByDate(date, pageable);
        return new PageImpl<>(mapTournamentsDTOWithSubObject(tournamentPage.getContent()), pageable, tournamentPage.getTotalElements());
    }

    public Page<TournamentDTO> getTournamentPlayedBeforeADate(LocalDate date, Pageable pageable) {
        if (date == null || date.isAfter(LocalDate.now())) {
            throw new BadRequestException();
        }
        Page<Tournament> tournamentPage = tournamentRepository.findByDateIsBefore(date, pageable);
        return new PageImpl<>(mapTournamentsDTOWithSubObject(tournamentPage.getContent()), pageable, tournamentPage.getTotalElements());
    }

    public Page<TournamentDTO> getTournamentPlayedAfterADate(LocalDate date, Pageable pageable) {
        if (date == null || date.isAfter(LocalDate.now().minusDays(2))) {
            throw new BadRequestException();
        }
        Page<Tournament> tournamentPage = tournamentRepository.findByDateIsAfter(date, pageable);
        return new PageImpl<>(mapTournamentsDTOWithSubObject(tournamentPage.getContent()), pageable, tournamentPage.getTotalElements());
    }

    public Page<TournamentDTO> getTournamentPlayedBetweenARangeOfDate(LocalDate startingDate, LocalDate endingDate, Pageable pageable) {
        if (startingDate == null || endingDate == null || endingDate.isBefore(startingDate) || startingDate.isAfter(LocalDate.now().minusDays(2)) || endingDate.isAfter(LocalDate.now())) {
            throw new BadRequestException();
        }
        Page<Tournament> tournamentPage = tournamentRepository.findByDateIsBetween(startingDate, endingDate, pageable);
        return new PageImpl<>(mapTournamentsDTOWithSubObject(tournamentPage.getContent()), pageable, tournamentPage.getTotalElements());
    }

    public Page<TournamentDTO> getTournamentPlayedThisWeek(Pageable pageable) {
        Page<Tournament> tournamentPage = tournamentRepository.findByDateIsBetween(startOfWeekLocalDate, endOfWeekLocalDate, pageable);
        return new PageImpl<>(mapTournamentsDTOWithSubObject(tournamentPage.getContent()), pageable, tournamentPage.getTotalElements());
    }

    public Page<TournamentDTO> getTournamentPlayedThisMonth(Pageable pageable) {
        Page<Tournament> tournamentPage = tournamentRepository.findByDateIsBetween(startOfMonthLocalDate, endOfMonthLocalDate, pageable);
        return new PageImpl<>(mapTournamentsDTOWithSubObject(tournamentPage.getContent()), pageable, tournamentPage.getTotalElements());
    }

    public Page<TournamentDTO> getTournamentByPlayerId(UUID playerId, Pageable pageable) {
        if (playerId == null) {
            throw new BadRequestException();
        }
        Page<Tournament> tournamentPage = tournamentRepository.findByParticipantsPlayer_Id(playerId, pageable);
        return new PageImpl<>(mapTournamentsDTOWithSubObject(tournamentPage.getContent()), pageable, tournamentPage.getTotalElements());
    }

    public Page<TournamentDTO> getTournamentsByFactionId(UUID factionId, Pageable pageable) {
        if (factionId == null) {
            throw new BadRequestException();
        }
        Page<Tournament> tournamentPage = tournamentRepository.findByParticipantsDeckFaction_Id(factionId, pageable);
        return new PageImpl<>(mapTournamentsDTOWithSubObject(tournamentPage.getContent()), pageable, tournamentPage.getTotalElements());
    }

    public Page<TournamentDTO> getTournamentsByFactionIdIn(List<UUID> factionIds, Pageable pageable) {
        if (factionIds == null || factionIds.isEmpty()) {
            throw new BadRequestException();
        }
        Page<Tournament> tournamentPage = tournamentRepository.findByParticipantsDeckFaction_IdIn(factionIds, pageable);
        return new PageImpl<>(mapTournamentsDTOWithSubObject(tournamentPage.getContent()), pageable, tournamentPage.getTotalElements());
    }

    public Page<TournamentDTO> getTournamentsByHeroId(UUID heroId, Pageable pageable) {
        if (heroId == null) {
            throw new BadRequestException();
        }
        Page<Tournament> tournamentPage = tournamentRepository.findByParticipantsDeckHero_Id(heroId, pageable);
        return new PageImpl<>(mapTournamentsDTOWithSubObject(tournamentPage.getContent()), pageable, tournamentPage.getTotalElements());
    }

    public Page<TournamentDTO> getTournamentsByHeroIdIn(List<UUID> heroIds, Pageable pageable) {
        if (heroIds == null || heroIds.isEmpty()) {
            throw new BadRequestException();
        }
        Page<Tournament> tournamentPage = tournamentRepository.findByParticipantsDeckHero_IdIn(heroIds, pageable);
        return new PageImpl<>(mapTournamentsDTOWithSubObject(tournamentPage.getContent()), pageable, tournamentPage.getTotalElements());
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
