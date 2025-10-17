package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.HeroDTO;

import java.util.List;
import java.util.UUID;

public interface HeroService {

    List<HeroDTO> getAllHeroes();

    HeroDTO getHeroById(UUID id);

    HeroDTO getHeroByName(String name);

    List<HeroDTO> getAllHeroesByFaction(UUID factionId);

    HeroDTO createHero(HeroDTO heroDTO);

    HeroDTO updateHeroById(UUID id, HeroDTO heroDTO);

    void  deleteHeroById(UUID id);

    void verifyHeroIntegrity(HeroDTO heroDTO);

}
