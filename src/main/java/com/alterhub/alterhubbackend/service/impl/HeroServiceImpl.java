package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.HeroDTO;
import com.alterhub.alterhubbackend.entity.Hero;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.exception.NotFindByArgumentException;
import com.alterhub.alterhubbackend.mapper.HeroMapper;
import com.alterhub.alterhubbackend.repository.HeroRepository;
import com.alterhub.alterhubbackend.service.interfaces.FactionService;
import com.alterhub.alterhubbackend.service.interfaces.HeroService;
import com.alterhub.alterhubbackend.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;
    private final ValidationService validationService;

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
        validationService.verifyHeroIntegrity(heroDTO);
        validationService.validateFaction(heroDTO.getFaction());
        Hero hero = HeroMapper.toEntity(heroDTO);
        return  HeroMapper.toDTO(heroRepository.save(hero));
    }

    public HeroDTO updateHeroById(UUID id, HeroDTO heroDTO){
        if(heroDTO.getId().equals(id)){
            validationService.verifyHeroIntegrity(heroDTO);
            validationService.validateFaction(heroDTO.getFaction());

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

    public void deleteHeroById(UUID id){
        if(!heroRepository.existsById(id)){
            throw new NoResultByIdException();
        }
        heroRepository.deleteById(id);
    }

}
