package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CardServiceImpl {

    @Autowired
    private CardRepository cardRepository;

    public Integer getDeckCount(UUID cardId) {
        return cardRepository.countDecksContainingCard(cardId);
    }

}
