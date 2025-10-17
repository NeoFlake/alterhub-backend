package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.HeroDTO;
import com.alterhub.alterhubbackend.entity.Hero;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.exception.NotFindByArgumentException;
import com.alterhub.alterhubbackend.mapper.FactionMapper;
import com.alterhub.alterhubbackend.mapper.HeroMapper;
import com.alterhub.alterhubbackend.repository.HeroRepository;
import com.alterhub.alterhubbackend.service.interfaces.FactionService;
import com.alterhub.alterhubbackend.service.interfaces.HeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;
    private final FactionService factionService;

    public List<HeroDTO> getAllHeroes(){
        return heroRepository.findAll()
                .stream()
                .map(HeroMapper::toDTO)
                .toList();
    }

    public HeroDTO getHeroById(UUID id){
        Hero hero = heroRepository.findById(id).orElseThrow(NoResultByIdException::new);
        return HeroMapper.toDTO(hero);
    }

    public HeroDTO getHeroByName(String name){
        if(name != null && !name.isEmpty()){
            Hero hero = heroRepository.findByName(name).orElseThrow(NotFindByArgumentException::new);
            return HeroMapper.toDTO(hero);
        } else {
            throw new BadRequestException();
        }
    }

    public List<HeroDTO> getAllHeroesByFaction(UUID factionId){
        if(factionId != null){
            return heroRepository.findByFactionId(factionId).orElseThrow(NotFindByArgumentException::new)
                    .stream()
                    .map(HeroMapper::toDTO)
                    .toList();
        } else {
            throw new BadRequestException();
        }
    }

    public HeroDTO createHero(HeroDTO heroDTO){
        verifyHeroIntegrity(heroDTO);
        factionService.validateFaction(heroDTO.getFaction());
        Hero hero = HeroMapper.toEntity(heroDTO);
        return  HeroMapper.toDTO(heroRepository.save(hero));
    }

    public HeroDTO updateHeroById(UUID id, HeroDTO heroDTO){
        if(heroDTO.getId().equals(id)){
            verifyHeroIntegrity(heroDTO);
            factionService.validateFaction(heroDTO.getFaction());

            Hero heroToUpdate = heroRepository.findById(id).orElseThrow(NoResultByIdException::new);
            Hero heroUpdated = HeroMapper.toEntity(heroDTO);

            heroToUpdate.setId(heroUpdated.getId());
            heroToUpdate.setName(heroUpdated.getName());
            heroToUpdate.setFaction(heroUpdated.getFaction());
            heroToUpdate.setReserveSlot(heroUpdated.getReserveSlot());
            heroToUpdate.setLandmarkSlot(heroUpdated.getLandmarkSlot());
            heroToUpdate.setEffect(heroUpdated.getEffect());

            return HeroMapper.toDTO(heroRepository.save(heroToUpdate));
        } else {
            throw new IdNotMatchException();
        }
    }

    public void  deleteHeroById(UUID id){
        if(!heroRepository.existsById(id)){
            throw new NoResultByIdException();
        }
        heroRepository.deleteById(id);
    }

    public void verifyHeroIntegrity(HeroDTO heroDTO) {
        if(heroDTO.getName() == null || heroDTO.getName().isEmpty()
        || heroDTO.getReserveSlot() == null || heroDTO.getReserveSlot() < 0
                || heroDTO.getLandmarkSlot() == null || heroDTO.getLandmarkSlot() < 0
        || heroDTO.getEffect() == null || heroDTO.getEffect().isEmpty()){
            throw new BadRequestException();
        }
        // On vérifie l'intégrité du sous-objet faction par son propre service
        factionService.verifyFactionIntegrity(heroDTO.getFaction());
    }

}
