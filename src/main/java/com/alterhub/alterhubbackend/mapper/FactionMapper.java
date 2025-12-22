package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.FactionDTO;
import com.alterhub.alterhubbackend.entity.Faction;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FactionMapper {

    public FactionDTO toDTO(Faction faction) {
        if (faction == null) return null;

        return FactionDTO.builder()
                .id(faction.getId())
                .name(faction.getName())
                .factionId(faction.getFactionId())
                .reference(faction.getReference())
                .color(faction.getColor())
                .build();
    }

    public Faction toEntity(FactionDTO factionDTO) {
        if (factionDTO == null) return null;

        return Faction.builder()
                .id(factionDTO.getId())
                .name(factionDTO.getName())
                .factionId(factionDTO.getFactionId())
                .reference(factionDTO.getReference())
                .color(factionDTO.getColor())
                .build();

    }

}
