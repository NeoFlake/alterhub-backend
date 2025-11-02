package com.alterhub.alterhubbackend.mapping;

import com.alterhub.alterhubbackend.dto.*;
import com.alterhub.alterhubbackend.entity.*;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.*;
import com.alterhub.alterhubbackend.repository.PlayerRepository;
import com.alterhub.alterhubbackend.service.DeckCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MappingService {

    private final DeckCountService deckCountService;

    private final PlayerRepository playerRepository;

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

        Tournament tournament = TournamentMapper.toEntity(tournamentDTO, new ArrayList<>());

        if(!tournamentDTO.getParticipants().isEmpty()){
            List<Participant> participants = tournamentDTO.getParticipants().stream().map(participantDTO -> {
                // Malheureusement je n'ai pas trouvé de meilleure solution pour ce problème
                Player player = playerRepository.findByName(participantDTO.getPlayerName()).orElseThrow(NoResultByIdException::new);
                return mapParticipantWithSubObjects(participantDTO, player, tournament);
            }).toList();

            tournament.setParticipants(participants);
        }

        return tournament;
    }

}
