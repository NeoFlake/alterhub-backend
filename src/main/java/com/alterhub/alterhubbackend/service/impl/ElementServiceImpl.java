package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.ElementDTO;
import com.alterhub.alterhubbackend.entity.Element;
import com.alterhub.alterhubbackend.entity.Rarity;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.ElementMapper;
import com.alterhub.alterhubbackend.mapper.RarityMapper;
import com.alterhub.alterhubbackend.repository.ElementRepository;
import com.alterhub.alterhubbackend.service.interfaces.ElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ElementServiceImpl implements ElementService {

    private final ElementRepository elementRepository;

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
        verifyElementIntegrity(elementDTO);
        Element element = ElementMapper.toEntity(elementDTO);
        return ElementMapper.toDTO(elementRepository.save(element));
    }

    public ElementDTO updateElementById(UUID id, ElementDTO elementDTO) {
        if(elementDTO.getId().equals(id)) {
            verifyElementIntegrity(elementDTO);
            Element elementToUpdate = elementRepository.findById(id).orElseThrow(NoResultByIdException::new);;
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
        if(!elementRepository.existsById(id)) {
            throw new NoResultByIdException();
        }
        elementRepository.deleteById(id);
    }

    public void verifyElementIntegrity(ElementDTO elementDTO) {
        if ((elementDTO.getMainCost() != null && elementDTO.getMainCost().isEmpty())
                || (elementDTO.getRecallCost() != null && elementDTO.getRecallCost().isEmpty())
                || (elementDTO.getOceanPower() != null && elementDTO.getOceanPower().isEmpty())
                || (elementDTO.getMountainPower() != null && elementDTO.getMountainPower().isEmpty())
        || (elementDTO.getForestPower() != null && elementDTO.getForestPower().isEmpty())
        || (elementDTO.getMainEffect() != null && elementDTO.getMainEffect().isEmpty())
        || (elementDTO.getEchoEffect() != null && elementDTO.getEchoEffect().isEmpty())){
            throw new BadRequestException();
        }
    }

}
