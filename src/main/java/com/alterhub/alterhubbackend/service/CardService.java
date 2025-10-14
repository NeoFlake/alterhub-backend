package com.alterhub.alterhubbackend.service;

import com.alterhub.alterhubbackend.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Integer getDeckCount(UUID cardId) {
        return cardRepository.countDecksContainingCard(cardId);
    }

}
