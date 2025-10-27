package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.HeroDTO;
import com.alterhub.alterhubbackend.dto.SetDTO;
import com.alterhub.alterhubbackend.entity.Hero;
import com.alterhub.alterhubbackend.entity.Set;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class HeroMapper {

    public HeroDTO toDTO(Hero hero) {
        if (hero == null) return null;

        return HeroDTO.builder()
                .id(hero.getId())
                .name(hero.getName())
                .faction(FactionMapper.toDTO(hero.getFaction()))
                .sets(hero.getSets().stream().map(SetMapper::toDTO).toList())
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
                .sets(heroDTO.getSets().stream().map(SetMapper::toEntity).toList())
                .reserveSlot(heroDTO.getReserveSlot())
                .landmarkSlot(heroDTO.getLandmarkSlot())
                .effect(heroDTO.getEffect())
                .build();
    }

}
