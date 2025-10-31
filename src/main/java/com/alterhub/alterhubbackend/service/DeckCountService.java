package com.alterhub.alterhubbackend.service;

import com.alterhub.alterhubbackend.entity.Card;
import com.alterhub.alterhubbackend.entity.Deck;
import com.alterhub.alterhubbackend.entity.Participant;
import com.alterhub.alterhubbackend.repository.CardRepository;
import com.alterhub.alterhubbackend.repository.projection.CardDeckCountProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeckCountService {

    private final CardRepository cardRepository;

    public Map<UUID, Integer> mapCardsWithDeckCount(List<Card> cards) {
        return cardRepository.countDecksForCards(
                        cards.stream().map(Card::getId).toList()
                ).stream()
                .collect(Collectors.toMap(
                        CardDeckCountProjection::getCardId,
                        CardDeckCountProjection::getDeckCount
                ));
    }

    public Map<UUID, Map<UUID, Integer>> mapDecksObjectWithCardsDeckCounted(List<Deck> decks){
        return decks.stream()
                .collect(Collectors.toMap(
                        Deck::getId,
                        deck -> mapCardsWithDeckCount(deck.getCards())
                ));
    }

    public Map<UUID, Map<UUID, Integer>> mapParticipantsObjectWithCardsDeckCounted(List<Participant> participants){
        return participants.stream()
                .collect(Collectors.toMap(
                        Participant::getId,
                        participant -> mapCardsWithDeckCount(participant.getDeck().getCards())
                ));
    }

}
