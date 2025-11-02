package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.SetDTO;
import com.alterhub.alterhubbackend.entity.Set;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.SetMapper;
import com.alterhub.alterhubbackend.repository.SetRepository;
import com.alterhub.alterhubbackend.service.interfaces.SetService;
import com.alterhub.alterhubbackend.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SetServiceImpl implements SetService {

    private final SetRepository setRepository;

    private final ValidationService validationService;

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
        validationService.verifySetIntegrity(setDTO);
        Set set = SetMapper.toEntity(setDTO);
        return SetMapper.toDTO(setRepository.save(set));
    }

    public SetDTO updateSetById(UUID id, SetDTO setDTO) {
        if (setDTO.getId().equals(id)) {
            validationService.verifySetIntegrity(setDTO);
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

}
