package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.FactionDTO;
import com.alterhub.alterhubbackend.entity.Faction;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.FactionMapper;
import com.alterhub.alterhubbackend.repository.FactionRepository;
import com.alterhub.alterhubbackend.service.interfaces.FactionService;
import com.alterhub.alterhubbackend.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FactionServiceImpl implements FactionService {

    private final FactionRepository factionRepository;

    private final ValidationService validationService;

    public List<FactionDTO> getAllFactions() {
        return factionRepository.findAllByOrderByReference()
                .stream()
                .map(FactionMapper::toDTO)
                .toList();
    }

    public FactionDTO getFactionById(UUID id) {
        Faction faction = factionRepository.findById(id).orElseThrow(NoResultByIdException::new);
        return FactionMapper.toDTO(faction);
    }

    public FactionDTO createFaction(FactionDTO factionDTO) {
        validationService.verifyFactionIntegrity(factionDTO);
        Faction faction = FactionMapper.toEntity(factionDTO);
        return FactionMapper.toDTO(factionRepository.save(faction));
    }

    public FactionDTO updateFactionById(UUID id, FactionDTO factionDTO) {
        if (factionDTO.getId().equals(id)) {
            validationService.verifyFactionIntegrity(factionDTO);
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
        if (!factionRepository.existsById(id)) {
            throw new NoResultByIdException();
        }
        factionRepository.deleteById(id);
    }

}
