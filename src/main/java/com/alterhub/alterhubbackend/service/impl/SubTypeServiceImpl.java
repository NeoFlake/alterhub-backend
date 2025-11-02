package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.SubTypeDTO;
import com.alterhub.alterhubbackend.entity.SubType;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.SubTypeMapper;
import com.alterhub.alterhubbackend.repository.SubTypeRepository;
import com.alterhub.alterhubbackend.service.interfaces.SubTypeService;
import com.alterhub.alterhubbackend.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubTypeServiceImpl implements SubTypeService {

    private final SubTypeRepository subTypeRepository;

    private final ValidationService validationService;

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
        validationService.verifySubTypeIntegrity(subTypeDTO);
        SubType subType = SubTypeMapper.toEntity(subTypeDTO);
        return SubTypeMapper.toDTO(subTypeRepository.save(subType));
    }

    public SubTypeDTO updateSubTypeById(UUID id, SubTypeDTO subTypeDTO) {
        if (subTypeDTO.getId().equals(id)) {
            validationService.verifySubTypeIntegrity(subTypeDTO);
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

}
