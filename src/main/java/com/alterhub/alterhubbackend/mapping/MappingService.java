package com.alterhub.alterhubbackend.mapping;

import com.alterhub.alterhubbackend.dto.*;
import com.alterhub.alterhubbackend.entity.*;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.exception.NotFindByArgumentException;
import com.alterhub.alterhubbackend.mapper.*;
import com.alterhub.alterhubbackend.repository.PlayerRepository;
import com.alterhub.alterhubbackend.repository.TournamentRepository;
import com.alterhub.alterhubbackend.service.DeckCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MappingService {

    private final DeckCountService deckCountService;

    public CardDTO mapWithDeckCount(Card card, Integer count) {
        return CardMapper.toDTO(card, count);
    }

    public List<CardDTO> mapCardsWithDeckCount(List<Card> cards, Map<UUID, Integer> deckCountMap) {
        return cards.stream()
                .map(card -> mapWithDeckCount(card, deckCountMap.getOrDefault(card.getId(), 0)))
                .toList();
    }

    public DeckDTO mapDeckDTOWithSubObjects(Deck deck, Map<UUID, Integer> deckCountMap) {
        return DeckMapper.toDTO(deck, mapCardsWithDeckCount(deck.getCards(), deckCountMap));
    }

    public List<DeckDTO> mapDecksDTOWithSubObjects(List<Deck> decks, Map<UUID, Map<UUID, Integer>> decksCountMap) {
        return decks.stream()
                .map(deck -> mapDeckDTOWithSubObjects(deck, decksCountMap.getOrDefault(deck.getId(), Map.of())))
                .toList();
    }

    public Deck mapDeckWithSubObjects(DeckDTO deckDTO, Player player) {
        return DeckMapper.toEntity(deckDTO, player);
    }

    public ParticipantDTO mapParticipantDTOWithSubObjects(Participant participant, Map<UUID, Integer> deckCountMap) {
        return ParticipantMapper.toDto(participant, mapDeckDTOWithSubObjects(participant.getDeck(), deckCountMap));
    }

    public List<ParticipantDTO> mapParticipantsDTOWithSubObjects(List<Participant> participants, Map<UUID, Map<UUID, Integer>> participantDeckCountMap) {
        return participants.stream()
                .map(participant -> mapParticipantDTOWithSubObjects(
                        participant,
                        participantDeckCountMap.getOrDefault(participant.getId(), Map.of())
                ))
                .toList();
    }

    public Participant mapParticipantWithSubObjects(ParticipantDTO participantDTO, Player player, Tournament tournament) {
        Deck deck = mapDeckWithSubObjects(participantDTO.getDeck(), player);
        return ParticipantMapper.toEntity(participantDTO, player, tournament, deck);
    }

    public List<Participant> mapParticipantsWithSubObjects(List<ParticipantDTO> participantsDTO, List<Player> players, List<Tournament> tournaments) {
        return participantsDTO.stream().map(participantDTO -> {

            Player playerParticipant = players
                    .stream()
                    .filter(player -> participantDTO.getPlayerName().equals(player.getName()))
                    .findFirst()
                    .orElseThrow(NotFindByArgumentException::new);

            Tournament tournamentParticipant = tournaments
                    .stream()
                    .filter(tournament -> participantDTO.getTournamentId().equals(tournament.getId()))
                    .findFirst()
                    .orElseThrow(NotFindByArgumentException::new);

            return mapParticipantWithSubObjects(participantDTO, playerParticipant, tournamentParticipant);
        }).toList();
    }

    public PlayerDTO mapPlayerDTOWithSubObject(Player player) {
        List<DeckDTO> decksDTO = mapDecksDTOWithSubObjects(player.getDecks(), deckCountService.mapDecksObjectWithCardsDeckCounted(player.getDecks()));
        List<ParticipantDTO> participantsDTO = mapParticipantsDTOWithSubObjects(
                player.getParticipants(),
                deckCountService.mapParticipantsObjectWithCardsDeckCounted(player.getParticipants())
        );

        return PlayerMapper.toDTO(player, decksDTO, participantsDTO);
    }

    public List<PlayerDTO> mapPlayersDTOWithSubObject(List<Player> players) {
        return players.stream().map(this::mapPlayerDTOWithSubObject).toList();
    }

    public Player mapPlayerWithSubObject(PlayerDTO playerDTO, User user) {
        List<Deck> decks = mapDecksFromPlayer(playerDTO, user);
        List<Participant> participants = mapParticipantsWithSubObjects(playerDTO.getParticipants());

        return PlayerMapper.toEntity(playerDTO, decks, participants, user);
    }

    private List<Deck> mapDecksFromPlayer(PlayerDTO playerDTO, User user) {
        return playerDTO.getDecks().stream().map(deckDTO -> DeckMapper.toEntity(deckDTO, mapPlayerWithSubObject(playerDTO, user))).toList();
    }

    public TournamentDTO mapTournamentDTOWithSubObject(Tournament tournament) {
        return TournamentMapper.toDto(
                tournament,
                mapParticipantsDTOWithSubObjects(
                        tournament.getParticipants(),
                        deckCountService.mapParticipantsObjectWithCardsDeckCounted(tournament.getParticipants())
                )
        );
    }

    public List<TournamentDTO> mapTournamentsDTOWithSubObject(List<Tournament> tournaments) {
        return tournaments.stream().map(this::mapTournamentDTOWithSubObject).toList();
    }

    public Tournament mapTournamentWithSubObject(TournamentDTO tournamentDTO) {
        return TournamentMapper.toEntity(tournamentDTO, mapParticipantsWithSubObjects(tournamentDTO.getParticipants()));
    }

    public List<Tournament> mapTournamentsWithSubObject(List<TournamentDTO> tournamentsDTO) {
        return tournamentsDTO.stream().map(this::mapTournamentWithSubObject).toList();
    }

}
