package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.RarityDTO;

import java.util.List;
import java.util.UUID;

public interface RarityService {

    List<RarityDTO> getAllRarities();

    RarityDTO getRarityById(UUID id);

    RarityDTO createRarity(RarityDTO rarityDTO);

    RarityDTO updateRarityById(UUID id, RarityDTO rarityDTO);

    void deleteRarityById(UUID id);

    void verifyRarityIntegrity (RarityDTO rarityDTO);

    void validateRarity(RarityDTO rarityDTO);

}
