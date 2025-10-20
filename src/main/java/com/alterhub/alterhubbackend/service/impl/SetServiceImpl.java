package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.SetDTO;
import com.alterhub.alterhubbackend.dto.SubTypeDTO;
import com.alterhub.alterhubbackend.entity.Set;
import com.alterhub.alterhubbackend.entity.SubType;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.SetMapper;
import com.alterhub.alterhubbackend.mapper.SubTypeMapper;
import com.alterhub.alterhubbackend.repository.SetRepository;
import com.alterhub.alterhubbackend.service.interfaces.SetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SetServiceImpl implements SetService {

    private final SetRepository setRepository;

    public List<SetDTO> getAllSets() {
        return setRepository.findAll()
                .stream()
                .map(SetMapper::toDTO)
                .toList();
    }

    public SetDTO getSetById(UUID id) {
        Set set = setRepository.findById(id).orElseThrow(NoResultByIdException::new);
        return SetMapper.toDTO(set);
    }

    public SetDTO createSet(SetDTO setDTO) {
        verifySetIntegrity(setDTO);
        Set set = SetMapper.toEntity(setDTO);
        return SetMapper.toDTO(setRepository.save(set));
    }

    public SetDTO updateSetById(UUID id, SetDTO setDTO) {
        if (setDTO.getId().equals(id)) {
            verifySetIntegrity(setDTO);
            Set setToUpdate = setRepository.findById(id)
                    .orElseThrow(NoResultByIdException::new);
            Set setUpdated = SetMapper.toEntity(setDTO);

            setToUpdate.setId(setUpdated.getId());
            setToUpdate.setSetId(setUpdated.getSetId());
            setToUpdate.setName(setUpdated.getName());
            setToUpdate.setReference(setUpdated.getReference());

            return SetMapper.toDTO(setRepository.save(setToUpdate));
        } else {
            throw new IdNotMatchException();
        }
    }

    public void deleteSetById(UUID id) {
        if (!setRepository.existsById(id)) {
            throw new NoResultByIdException();
        }
        setRepository.deleteById(id);
    }

    public void verifySetIntegrity(SetDTO setDTO) {
        if (setDTO.getSetId() == null || setDTO.getSetId().isEmpty()
                || setDTO.getName() == null || setDTO.getName().isEmpty()
                || setDTO.getReference() == null || setDTO.getReference().isEmpty()) {
            throw new BadRequestException();
        }
    }

    public void validateSet(SetDTO setDTO) {
        Set setReceived = SetMapper.toEntity(setDTO);
        Set setOnBase = setRepository.findById(setReceived.getId()).orElseThrow(NoResultByIdException::new);
        if(!setOnBase.getSetId().equals(setReceived.getSetId())
                || !setOnBase.getName().equals(setReceived.getName())
                || !setOnBase.getReference().equals(setReceived.getReference())) {
            throw new BadRequestException();
        }
    }

}
