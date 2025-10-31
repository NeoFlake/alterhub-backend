package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.ElementDTO;
import com.alterhub.alterhubbackend.entity.Element;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.ElementMapper;
import com.alterhub.alterhubbackend.repository.ElementRepository;
import com.alterhub.alterhubbackend.service.interfaces.ElementService;
import com.alterhub.alterhubbackend.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ElementServiceImpl implements ElementService {

    private final ElementRepository elementRepository;

    private final ValidationService validationService;

    public List<ElementDTO> getAllElements() {
        return elementRepository.findAll()
                .stream()
                .map(ElementMapper::toDTO)
                .toList();
    }

    public ElementDTO getElementById(UUID id) {
        Element element = elementRepository.findById(id).orElseThrow(NoResultByIdException::new);
        return ElementMapper.toDTO(element);
    }

    public ElementDTO createElement(ElementDTO elementDTO) {
        validationService.verifyElementIntegrity(elementDTO);
        Element element = ElementMapper.toEntity(elementDTO);
        return ElementMapper.toDTO(elementRepository.save(element));
    }

    public ElementDTO updateElementById(UUID id, ElementDTO elementDTO) {
        if (elementDTO.getId().equals(id)) {
            validationService.verifyElementIntegrity(elementDTO);
            Element elementToUpdate = elementRepository.findById(id).orElseThrow(NoResultByIdException::new);
            ;
            Element elementUpdated = ElementMapper.toEntity(elementDTO);

            elementUpdated.setId(elementToUpdate.getId());
            elementUpdated.setMainCost(elementToUpdate.getMainCost());
            elementUpdated.setRecallCost(elementToUpdate.getRecallCost());
            elementUpdated.setOceanPower(elementToUpdate.getOceanPower());
            elementUpdated.setMountainPower(elementToUpdate.getMountainPower());
            elementUpdated.setForestPower(elementToUpdate.getForestPower());
            elementUpdated.setMainEffect(elementToUpdate.getMainEffect());
            elementUpdated.setEchoEffect(elementToUpdate.getEchoEffect());

            return ElementMapper.toDTO(elementRepository.save(elementUpdated));
        } else {
            throw new IdNotMatchException();
        }
    }

    public void deleteElementById(UUID id) {
        if (!elementRepository.existsById(id)) {
            throw new NoResultByIdException();
        }
        elementRepository.deleteById(id);
    }

}
