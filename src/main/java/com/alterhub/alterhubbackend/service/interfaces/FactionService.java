package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.FactionDTO;

import java.util.List;
import java.util.UUID;

public interface FactionService {

    List<FactionDTO> getAllFactions();

    FactionDTO getFactionById(UUID id);

    FactionDTO createFaction(FactionDTO factionDTO);

    FactionDTO updateFactionById(UUID id, FactionDTO factionDTO);

    void deleteFactionById(UUID id);

}
