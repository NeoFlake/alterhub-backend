package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.RarityDTO;
import com.alterhub.alterhubbackend.entity.Rarity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RarityMapper {

    public RarityDTO toDTO(Rarity rarity) {
        if (rarity == null) return null;

        return RarityDTO.builder()
                .id(rarity.getId())
                .rarityId(rarity.getRarityId())
                .reference(rarity.getReference())
                .build();
    }

    public Rarity toEntity(RarityDTO rarityDTO) {
        if (rarityDTO == null) return null;

        return Rarity.builder()
                .id(rarityDTO.getId())
                .rarityId(rarityDTO.getRarityId())
                .reference(rarityDTO.getReference())
                .build();

    }

}
