package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.SubTypeDTO;
import com.alterhub.alterhubbackend.entity.SubType;
import com.alterhub.alterhubbackend.entity.Type;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.SubTypeMapper;
import com.alterhub.alterhubbackend.mapper.TypeMapper;
import com.alterhub.alterhubbackend.repository.SubTypeRepository;
import com.alterhub.alterhubbackend.service.interfaces.SubTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubTypeServiceImpl implements SubTypeService {

    private final SubTypeRepository subTypeRepository;

    public List<SubTypeDTO> getAllSubTypes() {
        return subTypeRepository.findAll()
                .stream()
                .map(SubTypeMapper::toDTO)
                .toList();
    }

    public SubTypeDTO getSubTypeById(UUID id) {
        SubType subType = subTypeRepository.findById(id).orElseThrow(NoResultByIdException::new);
        return SubTypeMapper.toDTO(subType);
    }

    public SubTypeDTO createSubType(SubTypeDTO subTypeDTO) {
        verifySubTypeIntegrity(subTypeDTO);
        SubType subType = SubTypeMapper.toEntity(subTypeDTO);
        return SubTypeMapper.toDTO(subTypeRepository.save(subType));
    }

    public SubTypeDTO updateSubTypeById(UUID id, SubTypeDTO subTypeDTO) {
        if (subTypeDTO.getId().equals(id)) {
            verifySubTypeIntegrity(subTypeDTO);
            SubType subTypeToUpdate = subTypeRepository.findById(id)
                    .orElseThrow(NoResultByIdException::new);
            SubType subTypeUpdated = SubTypeMapper.toEntity(subTypeDTO);

            subTypeToUpdate.setId(subTypeUpdated.getId());
            subTypeToUpdate.setSubTypeId(subTypeUpdated.getSubTypeId());
            subTypeToUpdate.setName(subTypeUpdated.getName());
            subTypeToUpdate.setReference(subTypeUpdated.getReference());

            return SubTypeMapper.toDTO(subTypeRepository.save(subTypeToUpdate));
        } else {
            throw new IdNotMatchException();
        }
    }

    public void deleteSubTypeById(UUID id) {
        if (!subTypeRepository.existsById(id)) {
            throw new NoResultByIdException();
        }
        subTypeRepository.deleteById(id);
    }

    public void verifySubTypeIntegrity(SubTypeDTO subTypeDTO) {
        if (subTypeDTO.getSubTypeId() == null || subTypeDTO.getSubTypeId().isEmpty()
                || subTypeDTO.getName() == null || subTypeDTO.getName().isEmpty()
                || subTypeDTO.getReference() == null || subTypeDTO.getReference().isEmpty()) {
            throw new BadRequestException();
        }
    }

}
