package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.RarityDTO;
import com.alterhub.alterhubbackend.entity.Rarity;
import com.alterhub.alterhubbackend.entity.SubType;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.RarityMapper;
import com.alterhub.alterhubbackend.mapper.SubTypeMapper;
import com.alterhub.alterhubbackend.repository.RarityRepository;
import com.alterhub.alterhubbackend.service.interfaces.RarityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RarityServiceImpl implements RarityService {

    private final RarityRepository rarityRepository;

    public List<RarityDTO> getAllRarities() {
        return rarityRepository.findAll()
                .stream()
                .map(RarityMapper::toDTO)
                .toList();
    }

    public RarityDTO getRarityById(UUID id) {
        Rarity rarity = rarityRepository.findById(id).orElseThrow(NoResultByIdException::new);
        return RarityMapper.toDTO(rarity);
    }

    public RarityDTO createRarity(RarityDTO rarityDTO) {
        verifyRarityIntegrity(rarityDTO);
        Rarity rarity = RarityMapper.toEntity(rarityDTO);
        return RarityMapper.toDTO(rarityRepository.save(rarity));
    }

    public RarityDTO updateRarityById(UUID id, RarityDTO rarityDTO) {
        if(rarityDTO.getId().equals(id)) {
            verifyRarityIntegrity(rarityDTO);
            Rarity rarityToUpdate = rarityRepository.findById(id)
                    .orElseThrow(NoResultByIdException::new);
            Rarity subTypeUpdated = RarityMapper.toEntity(rarityDTO);

            rarityToUpdate.setId(subTypeUpdated.getId());
            rarityToUpdate.setRarityId(subTypeUpdated.getRarityId());
            rarityToUpdate.setName(subTypeUpdated.getName());
            rarityToUpdate.setReference(subTypeUpdated.getReference());

            return RarityMapper.toDTO(rarityRepository.save(rarityToUpdate));
        } else {
            throw new IdNotMatchException();
        }
    }

    public void deleteRarityById(UUID id) {
        if (!rarityRepository.existsById(id)) {
            throw new NoResultByIdException();
        }
        rarityRepository.deleteById(id);
    }

    public void verifyRarityIntegrity (RarityDTO rarityDTO){
        if (rarityDTO.getRarityId() == null || rarityDTO.getRarityId().isEmpty()
                || rarityDTO.getName() == null || rarityDTO.getName().isEmpty()
                || rarityDTO.getReference() == null || rarityDTO.getReference().isEmpty()) {
            throw new BadRequestException();
        }
    }

}
