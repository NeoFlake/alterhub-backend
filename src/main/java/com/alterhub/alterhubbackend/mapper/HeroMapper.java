package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.HeroDTO;
import com.alterhub.alterhubbackend.entity.Hero;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HeroMapper {

    public HeroDTO toDTO(Hero hero) {
        if (hero == null) return null;

        return HeroDTO.builder()
                .id(hero.getId())
                .name(hero.getName())
                .faction(FactionMapper.toDTO(hero.getFaction()))
                .reserveSlot(hero.getReserveSlot())
                .landmarkSlot(hero.getLandmarkSlot())
                .effect(hero.getEffect())
                .build();
    }

    public Hero toEntity(HeroDTO heroDTO) {
        if (heroDTO == null) return null;

        return Hero.builder()
                .id(heroDTO.getId())
                .name(heroDTO.getName())
                .faction(FactionMapper.toEntity(heroDTO.getFaction()))
                .reserveSlot(heroDTO.getReserveSlot())
                .landmarkSlot(heroDTO.getLandmarkSlot())
                .effect(heroDTO.getEffect())
                .build();
    }

}
