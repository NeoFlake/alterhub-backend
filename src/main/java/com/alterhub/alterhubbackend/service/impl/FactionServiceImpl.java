package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.FactionDTO;
import com.alterhub.alterhubbackend.entity.Faction;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.FactionMapper;
import com.alterhub.alterhubbackend.repository.FactionRepository;
import com.alterhub.alterhubbackend.service.interfaces.FactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FactionServiceImpl implements FactionService {

    private final FactionRepository factionRepository;

    public List<FactionDTO> findAllFactions() {
        return factionRepository.findAll()
                .stream()
                .map(FactionMapper::toDTO)
                .toList();
    }

    public FactionDTO findFactionById(UUID id) {
        Faction faction = factionRepository.findById(id).orElseThrow(NoResultByIdException::new);
        return FactionMapper.toDTO(faction);
    }

    public FactionDTO createFaction(FactionDTO factionDTO) {
        verifyFactionIntegrity(factionDTO);
        Faction faction = FactionMapper.toEntity(factionDTO);
        return FactionMapper.toDTO(factionRepository.save(faction));
    }

    public FactionDTO updateFactionById(UUID id, FactionDTO factionDTO) {
        if(factionDTO.getId().equals(id)) {
            verifyFactionIntegrity(factionDTO);
            Faction factionToUpdate = factionRepository.findById(id).orElseThrow(NoResultByIdException::new);
            Faction factionUpdated = FactionMapper.toEntity(factionDTO);

            factionToUpdate.setId(factionUpdated.getId());
            factionToUpdate.setFactionId(factionUpdated.getFactionId());
            factionToUpdate.setName(factionUpdated.getName());
            factionToUpdate.setColor(factionUpdated.getColor());

            return FactionMapper.toDTO(factionRepository.save(factionToUpdate));
        } else {
            throw new IdNotMatchException();
        }
    }

    public void deleteFactionById(UUID id) {
        if(!factionRepository.existsById(id)) {
            throw new NoResultByIdException();
        }
        factionRepository.deleteById(id);
    }

    public void verifyFactionIntegrity(FactionDTO factionDTO) {
        if(factionDTO.getFactionId() == null || factionDTO.getFactionId().isEmpty()
        || factionDTO.getName() == null || factionDTO.getName().isEmpty()
        || factionDTO.getReference() == null || factionDTO.getReference().isEmpty()
        || factionDTO.getColor() == null || factionDTO.getColor().isEmpty()){
            throw new BadRequestException();
        }
    }

}
